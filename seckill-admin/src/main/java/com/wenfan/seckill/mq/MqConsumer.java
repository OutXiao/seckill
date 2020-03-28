package com.wenfan.seckill.mq;

import com.alibaba.fastjson.JSON;
import com.wenfan.seckill.mapper.ItemStockMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by wenfan on 2020/2/5 19:30
 */

//@Component
public class MqConsumer {


    @Value("${rocketmq.nameserver.addr}")
    private String nameServerAddr;

    @Value("${rocketmq.topicname}")
    private String topicname;

    @Autowired
    private ItemStockMapper itemStockMapper;

    private DefaultMQPushConsumer consumer;

    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer("stock_consumer_group");
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(topicname,"*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                // 实现从数据库中真正扣减库存
                Message message = msgs.get(0);
                String jsonString = new String (message.getBody());
                Map<String,Object> map = JSON.parseObject(jsonString,Map.class);

                Integer itemId = (Integer) map.get("itemId");
                Integer amount = (Integer) map.get("amount");
                itemStockMapper.decreaseStock(itemId,amount);

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }


}
