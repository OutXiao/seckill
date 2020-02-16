package com.wenfan.seckill.service.impl;

import com.wenfan.seckill.entity.OrderInfo;
import com.wenfan.seckill.entity.SequenceInfo;
import com.wenfan.seckill.entity.StockLog;
import com.wenfan.seckill.entity.SysUser;
import com.wenfan.seckill.exception.OrderException;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.mapper.ItemMapper;
import com.wenfan.seckill.mapper.OrderInfoMapper;
import com.wenfan.seckill.mapper.SequenceInfoMapper;
import com.wenfan.seckill.mapper.StockLogMapper;
import com.wenfan.seckill.service.ItemService;
import com.wenfan.seckill.service.OrderService;
import com.wenfan.seckill.utils.SysUserUtil;
import com.wenfan.seckill.vo.ItemVO;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by wenfan on 2020/1/28 22:23
 */
@Service
public class OrderSeriviceImpl implements OrderService {


    @Autowired
    private SequenceInfoMapper sequenceInfoMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private StockLogMapper stockLogMapper;

    @Override
    @Transactional
    public OrderInfo createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount,StockLog stockLog) throws OrderException {

        // 1.校验下单的商品是否存在、用户是否合法、购买数量是否正确
        ItemVO itemVO = itemService.getItemByIdInCache(itemId);
        if (itemVO == null)
            throw new OrderException("商品不存在");
        SysUser user = SysUserUtil.getLoginUser();
        if (user == null)
            throw new OrderException("用户没有登录");
        if (amount < 0 | amount >99)
            throw new OrderException("商品数量无效");
        if (itemVO.getPromoteStatus() == null|| itemVO.getPromoteStatus() != 2  ){
            throw new OrderException("秒杀活动时间不在规定范围");
        }
        //校验秒杀活动是否适用于秒杀商品
        if (promoId != null){
            if (promoId.intValue() != itemVO.getPromoteId().intValue()){
                throw new OrderException("活动信息错误");
            }
        }



        //2.减库存  此时减库存被消息队列消费
        boolean result = itemService.decreaseStock(itemId,amount);
        if (!result)
            throw new OrderException("库存不足");

        // 3.订单入库
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setItemId(itemId);
        orderInfo.setAmount(amount);
        orderInfo.setId(generateOrderNo());
        orderInfo.setPromoteId(promoId);
        if(promoId != null)
            orderInfo.setItemPrice(itemVO.getPromotePrice().doubleValue());
        else
            orderInfo.setItemPrice(itemVO.getPrice().doubleValue());
        Double resultPrice;  // 订单总价
        resultPrice = (amount * orderInfo.getItemPrice());
        orderInfo.setOrderPrice(resultPrice);
        orderInfo.setUserId(userId);
        orderInfoMapper.insertSelective(orderInfo);


/*
        //spring在事务提交之后在执行
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                ////// 执行有业务
            }
        });

*/


        // 4. 增加销量
        itemService.increaseSales(itemId,amount);

        // 主业务数据操作完成以后，设置库存状态
        stockLog.setStatus(2);
        stockLogMapper.updateByPrimaryKeySelective(stockLog);
        return orderInfo;
    }


    /**
     *   产生16位的订单号
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateOrderNo(){
        StringBuffer buffer = new StringBuffer();

        //前8位为时间信息，年月日
        LocalDateTime now =LocalDateTime.now();
        String nowTime = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        buffer.append(nowTime);

        //中间6位为自增序列
        //获取当前sequence
        int sequence;
        SequenceInfo sequenceInfo = sequenceInfoMapper.geSequenceInfoByName("order_info");
        sequence = sequenceInfo.getCurrentValue();
        sequenceInfo.setCurrentValue(sequence+sequenceInfo.getStep());
        sequenceInfoMapper.updateByPrimaryKey(sequenceInfo);

        // 不足6位用0 代替
        String sequenceStr = String.valueOf(sequence);
        for(int i = 0 ;i <(6 -sequenceStr.length());i++ ){
            buffer.append("0");
        }
        buffer.append(sequenceStr);

        // 最后两位为分库分表位
        buffer.append("00");
        return buffer.toString();
    }

}
