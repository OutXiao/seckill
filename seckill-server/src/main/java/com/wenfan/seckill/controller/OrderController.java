package com.wenfan.seckill.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.wenfan.seckill.entity.StockLog;
import com.wenfan.seckill.entity.SysUser;
import com.wenfan.seckill.exception.OrderException;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.mq.MqProducer;
import com.wenfan.seckill.service.ItemService;
import com.wenfan.seckill.service.OrderService;
import com.wenfan.seckill.service.UserService;
import com.wenfan.seckill.utils.SysUserUtil;
import com.wenfan.seckill.vo.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wenfan on 2020/1/28 22:17
 */
@RestController
@RequestMapping("/server/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private MqProducer mqProducer;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RedisTemplate redisTemplate;

    private RateLimiter rateLimiter;

    @PostConstruct
    public void init(){
        rateLimiter = RateLimiter.create(30);
    }

    @PostMapping("/createOrder")
    public ResponseInfo createOrder(@RequestParam(name = "itemId") Integer itemId,
                                    @RequestParam( name = "amount") Integer amount,
                                    @RequestParam(name = "promoteId" ,required = false)  Integer promoteId) throws OrderException {
        if (!rateLimiter.tryAcquire()){    // 活动限流
            ResponseInfo.success("活动太火爆");
        }

        SysUser sysUser = SysUserUtil.getLoginUser();
        if (sysUser == null)
            throw new SystemException("用户还未登录");
        SysUser user = userService.getSysUser(sysUser.getUsername());
        //orderService.createOrder(user.getId(),itemId,promoteId,amount);



        // 判断商品是否售罄
        if (redisTemplate.hasKey("sell_out:promote_item_stock_invalid_"+itemId))
            throw new SystemException("商品已经售罄");

        StockLog stockLog = itemService.initStockLog(itemId,amount);
        if (!mqProducer.transactionAsyncReduceStock(user.getId(),promoteId,itemId,amount,stockLog)){
            throw new SystemException("下单失败");
        }
        return ResponseInfo.success();
    }
}
