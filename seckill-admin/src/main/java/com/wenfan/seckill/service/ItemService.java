package com.wenfan.seckill.service;

import com.wenfan.seckill.entity.Item;
import com.wenfan.seckill.entity.PageBean;
import com.wenfan.seckill.entity.StockLog;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.model.AdminItemModel;
import com.wenfan.seckill.vo.ItemVO;

import java.util.List;

/**
 * Created by wenfan on 2020/1/24 15:11
 */
public interface ItemService {

    //创建商品
    int createItem(Item item,Integer stockCount) throws SystemException;

    //admin-商品列表浏览
    PageBean<Item> listItemByPage(Integer pageIndex, Integer pageSize);

    //商品详情浏览
    ItemVO getItemById(Integer id);

    List<ItemVO> getAllIsEnabledItemAndStock();

    // 获取商品和活动 在缓存中
    ItemVO getItemByIdInCache(Integer id);


    //库存扣减
    boolean decreaseStock(Integer itemId, Integer amount) throws SystemException;

    // 库存回补
    boolean increaseStock(Integer itemId,Integer amount);


    //商品销量增加
    void increaseSales(Integer itemId, Integer amount) throws SystemException;

    void deleteItem(Integer id);

    StockLog initStockLog(Integer itemId, Integer amount);


    Item getAdminItemById(Integer itemId);

    int editItem(AdminItemModel adminItemModel);

}
