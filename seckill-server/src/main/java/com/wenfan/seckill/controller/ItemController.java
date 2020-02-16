package com.wenfan.seckill.controller;

import com.wenfan.seckill.entity.Item;
import com.wenfan.seckill.entity.ItemStock;
import com.wenfan.seckill.mapper.ItemStockMapper;
import com.wenfan.seckill.service.CacheService;
import com.wenfan.seckill.service.ItemService;
import com.wenfan.seckill.utils.StringUtils;
import com.wenfan.seckill.vo.ItemVO;
import com.wenfan.seckill.vo.ResponseInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by wenfan on 2020/1/24 10:55
 */
@RestController
@RequestMapping("/server/api/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemStockMapper stockMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheService cacheService;

    @GetMapping("/allItem")
    public ResponseInfo getAllItem(HttpServletRequest request){
        List<ItemVO> items = itemService.getAllIsEnabledItemAndStock();
        return ResponseInfo.success(items);
    }

    @GetMapping("/getItemById")
    public ResponseInfo<Item> getItemById(HttpServletRequest request){
        String id = request.getParameter("id");
        ItemVO itemVO ;
        if (!StringUtils.isAnEmpty(id+"")){
            itemVO = (ItemVO) cacheService.getObjectFromCache("item:item_"+id);

            if (itemVO == null) {
                itemVO = (ItemVO) redisTemplate.opsForValue().get("item:item_" + id);
                if(itemVO == null){
                    itemVO = itemService.getItemById(Integer.parseInt(id));
                    redisTemplate.opsForValue().set("item:item_" + id, itemVO);
                    redisTemplate.expire("item:item_" + id, 10, TimeUnit.MINUTES);
                }
                cacheService.setCommonCache("item:item_"+id,itemVO);
                return ResponseInfo.success(itemVO);
            }else
                return ResponseInfo.success(itemVO);

        }
        return ResponseInfo.fail();
    }

}
