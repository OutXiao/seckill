/*
* ItemUserMapper.java
* http://www.wenfan.club
* Copyright © 2020 wenfan All Rights Reserved
* 作者：wenfan
* QQ：571696215
* E-Mail：guwenfan@qq.com
* 2020-03-04 13:40 Created
*/ 
package com.wenfan.seckill.mapper;

import com.wenfan.seckill.entity.ItemUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;


@Component
@org.apache.ibatis.annotations.Mapper
public interface ItemUserMapper extends Mapper<ItemUser> {

    ItemUser isBuy(@Param("userId") Integer userId, @Param("itemId")Integer itemId);

}