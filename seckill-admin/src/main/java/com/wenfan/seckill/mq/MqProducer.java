package com.wenfan.seckill.mq;

import com.alibaba.fastjson.JSON;
import com.wenfan.seckill.entity.StockLog;
import com.wenfan.seckill.exception.OrderException;
import com.wenfan.seckill.mapper.StockLogMapper;
import com.wenfan.seckill.service.OrderService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenfan on 2020/2/5 19:17
 */
@Component
public class MqProducer {

    Logger log = LoggerFactory.getLogger(MqProducer.class);

    @Value("${rocketmq.nameserver.addr}")
    private String nameServerAddr;

    @Value("${rocketmq.topicname}")
    private String topicname;

    private DefaultMQProducer producer;

    private TransactionMQProducer transactionMQProducer;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockLogMapper stockLogMapper;

    @PostConstruct
    public void init() throws MQClientException {
        //mq proceducer 的初始化
        producer = new DefaultMQProducer("producer_group");
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();

        transactionMQProducer = new TransactionMQProducer("transaction_producer_group");
        transactionMQProducer.setNamesrvAddr(nameServerAddr);
        transactionMQProducer.start();

        transactionMQProducer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object args) {
                 // 创建订单
                Integer userId =(Integer) ((Map)args).get("userId");
                Integer amount =(Integer) ((Map)args).get("amount");
                Integer promoteId =(Integer) ((Map)args).get("promoteId");
                Integer itemId =(Integer) ((Map)args).get("itemId");
                StockLog stockLog = (StockLog) ((Map)args).get("stockLog");
                try {
                    orderService.createOrder(userId,itemId,promoteId,amount,stockLog);
                } catch (OrderException e) {
                    //e.printStackTrace();
                    log.error(e.getErrorMsg());
                    stockLog.setStatus(3);  // 设定回滚状态
                    stockLogMapper.updateByPrimaryKeySelective(stockLog);
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                // 根据返回扣减的成功，来判断要返回事务的RollBack 还是 Unknow

                String jsonString = new String (messageExt.getBody());
                Map<String,Object> map = JSON.parseObject(jsonString,Map.class);
                StockLog stockLog = (StockLog) map.get("stockLog");
                if (stockLog == null){
                    return LocalTransactionState.UNKNOW;
                }else if(stockLog.getStatus() == 2){
                    return LocalTransactionState.COMMIT_MESSAGE;
                }else if(stockLog.getStatus() == 1){
                    return LocalTransactionState.UNKNOW;
                }
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        });

    }


    // 事务性同步扣减库存
    public boolean transactionAsyncReduceStock(Integer userId, Integer promoteId, Integer itemId, Integer amount,StockLog stockLog){
        Map<String,Object> bodyMap = new HashMap<>();
        bodyMap.put("itemId",itemId);
        bodyMap.put("amount",amount);

        Map<String,Object> argsMap = new HashMap<>();
        argsMap.put("userId",userId);
        argsMap.put("promoteId",promoteId);
        argsMap.put("itemId",itemId);
        argsMap.put("amount",amount);
        argsMap.put("stockLog",stockLog);
        Message message = new Message(topicname,"increase",
                JSON.toJSON(bodyMap).toString().getBytes(Charset.forName("UTF-8")));
        TransactionSendResult sendResult;
        try {
            // 发送状态为prepared的消息，不会被consumers立即消费掉
            sendResult = transactionMQProducer.sendMessageInTransaction(message,argsMap);
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        }
        if (sendResult.getLocalTransactionState() == LocalTransactionState.ROLLBACK_MESSAGE){
            return false;
        }else if (sendResult.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE){
            return true;
        }
        return true;
    }



    // 同步库存减库存
    public boolean asyncReduceStock(Integer itemId,Integer amount) {
        Map<String,Object> bodyMap = new HashMap<>();
        bodyMap.put("itemId",itemId);
        bodyMap.put("amount",amount);
        Message message = new Message(topicname,"increase",
                JSON.toJSON(bodyMap).toString().getBytes(Charset.forName("UTF-8")));
        try {
            System.out.println("减库存");
            producer.send(message);
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        } catch (RemotingException e) {
            e.printStackTrace();
            return false;
        } catch (MQBrokerException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}
