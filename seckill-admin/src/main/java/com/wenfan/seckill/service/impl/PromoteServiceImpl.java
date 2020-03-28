package com.wenfan.seckill.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wenfan.seckill.entity.Item;
import com.wenfan.seckill.entity.ItemStock;
import com.wenfan.seckill.entity.PageBean;
import com.wenfan.seckill.entity.Promote;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.mapper.ItemMapper;
import com.wenfan.seckill.mapper.ItemStockMapper;
import com.wenfan.seckill.mapper.PromoteMapper;
import com.wenfan.seckill.model.AdminPromotionModel;
import com.wenfan.seckill.service.PromoteService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenfan on 2020/1/28 15:24
 */
@Service
public class PromoteServiceImpl implements PromoteService {

    @Autowired
    private PromoteMapper promoteMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemStockMapper itemStockMapper;

    @Autowired
    private RedisTemplate<String,Integer> redisTemplate;

    private Logger log = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void initItemPromotionStock(){
        List<Promote> promoteList = promoteMapper.getAllPromotion();
        log.info("系统正在初始化库存");
        for (Promote promote:promoteList){
            ItemStock itemStock = itemStockMapper.getItemStockByItemId(promote.getItemId());
            redisTemplate.opsForValue().set("stock:promote_item_stock_"+itemStock.getId(),itemStock.getStock());
        }
        log.info("系统正在初始化库存完成");
    }


    @Override
    public AdminPromotionModel getAdminPromotionModelByPromotionId(Integer promotionId) {
        Promote promote = promoteMapper.getPromoteByPromotionId(promotionId);
        Item item = itemMapper.getSingleItemById(promote.getItemId());
        ItemStock itemStock = itemStockMapper.getItemStockByItemId(promote.getItemId());
        Integer promoteStock =  redisTemplate.opsForValue().get("stock:promote_item_stock_"+promote.getItemId());
        if (promoteStock == null)
            throw new SystemException("活动库存不存在");
        AdminPromotionModel adminPromotionModel = new AdminPromotionModel();
        BeanUtils.copyProperties(promote,adminPromotionModel);
        adminPromotionModel.setPromoteStock(promoteStock);
        adminPromotionModel.setRealStock(itemStock.getStock());
        adminPromotionModel.setSales(item.getSales());
        adminPromotionModel.setImgUrl(item.getImgUrl());
        adminPromotionModel.setPrice(item.getPrice());
        return adminPromotionModel;
    }

    @Override
    public Promote getPromoteInfoByItemId(Integer id) {

        Promote promote = promoteMapper.getPromoteByItemId(id);
        if (promote == null){
            return null;
        }
        //判断当前时间是否秒杀活动即将开始或正在进行
        //  isAfterNow -- Now在（）的后面
        if (new DateTime(promote.getStartDate()).isAfterNow()){
            promote.setStatus(1);
        }else if(new DateTime(promote.getEndDate()).isBeforeNow()){
            promote.setStatus(3);
        }else promote.setStatus(2);

        return promote;
    }



    @Override
    public PageBean<AdminPromotionModel> listPromotionByPage(Integer pageIndex, Integer pageSize) {
        Page page = PageHelper.startPage(pageIndex, pageSize);
        List<Promote> promotes = promoteMapper.getAllPromotion();

        List<AdminPromotionModel> adminPromotionModels = new ArrayList<>();
        for (Promote promote : promotes){
            AdminPromotionModel adminPromotionModel = new AdminPromotionModel();
            Item item = itemMapper.getSingleItemById(promote.getItemId());
            ItemStock itemStock = itemStockMapper.getItemStockByItemId(promote.getItemId());
            BeanUtils.copyProperties(promote,adminPromotionModel);
            adminPromotionModel.setImgUrl(item.getImgUrl());
            adminPromotionModel.setPrice(item.getPrice());
            adminPromotionModel.setSales(item.getSales());
            adminPromotionModel.setRealStock(itemStock.getStock());

            // redis中取出promoteStock
            Integer promoteStock =  redisTemplate.opsForValue().get("stock:promote_item_stock_"+promote.getItemId());
            if (promoteStock == null)
                continue;
            adminPromotionModel.setPromoteStock(promoteStock);
            adminPromotionModels.add(adminPromotionModel);
        }

        int count = adminPromotionModels.size();
        PageBean<AdminPromotionModel> pageBean = new PageBean<>(pageIndex, pageSize, count);
        pageBean.setItems(adminPromotionModels);
        pageBean.setTotalNum((int) page.getTotal());
        return pageBean;
    }

    @Override
    @Transactional
    public int addPromotion(Promote promote, Integer promoteStock) {
        redisTemplate.opsForValue().set("stock:promote_item_stock_"+promote.getItemId(),promoteStock);
        return promoteMapper.insertSelective(promote);
    }

    @Override
    @Transactional
    public int editPromotion(Promote promote, Integer promoteStock) {
        redisTemplate.opsForValue().set("stock:promote_item_stock_"+promote.getItemId(),promoteStock);
        return promoteMapper.update(promote);
    }

    @Override
    public int delPromotion(Integer promotionId) {
        return promoteMapper.deletePromotion(promotionId);
    }
}
