package com.wenfan.seckill.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wenfan.seckill.entity.*;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.mapper.ItemMapper;
import com.wenfan.seckill.mapper.ItemStockMapper;
import com.wenfan.seckill.mapper.StockLogMapper;
import com.wenfan.seckill.model.AdminItemModel;
import com.wenfan.seckill.mq.MqProducer;
import com.wenfan.seckill.service.ItemService;
import com.wenfan.seckill.service.PromoteService;
import com.wenfan.seckill.utils.DateUtils;
import com.wenfan.seckill.utils.StringUtils;
import com.wenfan.seckill.vo.ItemVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by wenfan on 2020/1/24 15:11
 */
@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemStockMapper itemStockMapper;

    @Autowired
    private PromoteService promoteService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StockLogMapper stockLogMapper;

    @Autowired
    private MqProducer mqProducer;

    @Override
    @Transactional
    public int createItem(Item item,Integer stockCount) throws SystemException {
        int i,j;
        item.setCreateTime(new Date());
        i = itemMapper.insertSelective(item);
        if (!StringUtils.isAnEmpty(stockCount+"")) {
            ItemStock itemStock = new ItemStock();
            itemStock.setItemId(item.getId());
            itemStock.setStock(stockCount);
            j = itemStockMapper.insertSelective(itemStock);
        }else
            throw new SystemException("库存不能为空");
        return i&j;
    }

    @Override
    public PageBean<Item> listItemByPage(Integer pageIndex, Integer pageSize) {
        Page page = PageHelper.startPage(pageIndex, pageSize);
        List<Item> allItems = itemMapper.getAllEnabledItem();
        int count = allItems.size();
        PageBean<Item> pageBean = new PageBean<>(pageIndex, pageSize, count);
        pageBean.setItems(allItems);
        pageBean.setTotalNum((int) page.getTotal());
        return pageBean;
    }

    @Override
    public ItemVO getItemById(Integer id) {
        Item item = itemMapper.getSingleItemById(id);
        ItemStock itemStock = itemStockMapper.getItemStockByItemId(id);
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(item,itemVO);
        itemVO.setPrice(new BigDecimal(item.getPrice()));
        itemVO.setStock(itemStock.getStock());
        // 获取商品活动信息
        Promote promote = promoteService.getPromoteInfoByItemId(id);
        getPromotion(itemVO, promote);
        return itemVO;


    }





    @Override
    public List<ItemVO> getAllIsEnabledItemAndStock() {

        List<Item> itemList = itemMapper.getAllEnabledItem();

        if (itemList == null){
            return null;
        }
        List<ItemVO> itemVOList = itemList.stream().map(item ->{
            ItemStock stock = itemStockMapper.getItemStockByItemId(item.getId());
            ItemVO itemVO = new ItemVO();
            BeanUtils.copyProperties(item,itemVO);
            itemVO.setPrice(BigDecimal.valueOf(item.getPrice()));
            itemVO.setStock(stock.getStock());
            // 获取商品活动信息
            Promote promote = promoteService.getPromoteInfoByItemId(item.getId());
            getPromotion(itemVO, promote);
            return itemVO;
        }).collect(Collectors.toList());
        return itemVOList;
    }



    private void getPromotion(ItemVO itemVO, Promote promote) {
        if (promote != null && promote.getStatus() != 3) {
            itemVO.setPromotePrice(new BigDecimal(promote.getPromoteItemPrice()));
            itemVO.setPromoteStatus(2);
            itemVO.setPromoteId(promote.getId());
            itemVO.setPromoteEndDate(DateUtils.formatDate(promote.getEndDate()));
            itemVO.setPromoteStartDate(DateUtils.formatDate(promote.getStartDate()));
        }
    }


    @Override
    public ItemVO getItemByIdInCache(Integer id) {

        ItemVO itemVO = (ItemVO) redisTemplate.opsForValue().get("item_validate_"+id);
        if (itemVO == null) {
            itemVO = this.getItemById(id);
            redisTemplate.opsForValue().set("item_validate_"+id,itemVO);
            redisTemplate.expire("item_validate_"+id,10,TimeUnit.MINUTES);
        }
        return itemVO;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws SystemException {
        //int result = itemStockMapper.decreaseStock(itemId,amount);
        long result = redisTemplate.opsForValue().increment("stock:promote_item_stock_"+itemId,amount.intValue()* -1);
        if (result >0){
            return true;  //Redis中减库存成功，并且消息投递成功
        }else if (result == 0){
            redisTemplate.opsForValue().set("sell_out:promote_item_stock_invalid_"+itemId,"true");
            return true;
        }else{
            // 扣减库存失败
            increaseStock(itemId,amount);
            return false;
        }
    }

    @Override
    public boolean increaseStock(Integer itemId, Integer amount) {
        redisTemplate.opsForValue().increment("stock:promote_item_stock_"+itemId,amount.intValue());
        return true;
    }



    @Override
    public void increaseSales(Integer itemId, Integer amount) throws SystemException {
        itemMapper.increaseSales(itemId,amount);
    }



    @Override
    public void deleteItem(Integer id) {

    }

    @Override
    @Transactional
    public StockLog initStockLog(Integer itemId, Integer amount){
        StockLog stockLog = new StockLog();
        stockLog.setAmount(amount);
        stockLog.setItemId(itemId);
        stockLog.setStatus(1);
        stockLog.setOperatingTime(new Date());
        stockLog.setStockLogId(UUID.randomUUID().toString().replace("-",""));
        stockLogMapper.insertSelective(stockLog);
        return stockLog;
    }

    @Override
    public Item getAdminItemById(Integer itemId) {
        return itemMapper.getSingleItemById(itemId);
    }

    @Override
    @Transactional
    public int editItem(AdminItemModel adminItemModel) {

        Item item = new Item();
        BeanUtils.copyProperties(adminItemModel,item);
        int i = itemMapper.updateItem(item);
        int j =itemStockMapper.editStockByItemId(adminItemModel.getId(),adminItemModel.getStock());
        return i&j;
    }

}
