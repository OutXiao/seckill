package com.wenfan.seckill.service;

import com.wenfan.seckill.entity.OrderInfo;
import com.wenfan.seckill.entity.StockLog;
import com.wenfan.seckill.exception.OrderException;
import com.wenfan.seckill.exception.SystemException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wenfan on 2020/1/28 22:18
 */
public interface OrderService {

    OrderInfo createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount,StockLog stockLog) throws SystemException, OrderException;

}
