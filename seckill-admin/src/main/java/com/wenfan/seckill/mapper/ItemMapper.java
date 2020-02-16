/*
* ItemMapper.java
* http://www.wenfan.club
* Copyright © 2020 wenfan All Rights Reserved
* 作者：wenfan
* QQ：571696215
* E-Mail：guwenfan@qq.com
* 2020-01-24 14:47 Created
*/ 
package com.wenfan.seckill.mapper;


import com.wenfan.seckill.entity.Item;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
@org.apache.ibatis.annotations.Mapper
public interface ItemMapper extends Mapper<Item> {

    List<Item> getAllEnabledItem();

    Item getSingleItemById(Integer itemId);

    int deleteSingleItemById(Integer itemId);

    int updateSingleItemBy();

    int increaseSales(@Param("itemId") Integer itemId, @Param("amount") Integer amount);

    int updateItem(@Param("item") Item item);
}