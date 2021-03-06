/*
* ItemStockMapper.java
* http://www.wenfan.club
* Copyright © 2020 wenfan All Rights Reserved
* 作者：wenfan
* QQ：571696215
* E-Mail：guwenfan@qq.com
* 2020-01-24 14:47 Created
*/ 
package com.wenfan.seckill.mapper;

import com.wenfan.seckill.entity.ItemStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
@org.apache.ibatis.annotations.Mapper
public interface ItemStockMapper extends Mapper<ItemStock> {
    ItemStock getItemStockByItemId(Integer itemId);

    int decreaseStock(@Param("itemId") Integer itemId,@Param("amount") Integer amount);

    int editStockByItemId(@Param("itemId") Integer itemId,@Param("stock") Integer stock);

}