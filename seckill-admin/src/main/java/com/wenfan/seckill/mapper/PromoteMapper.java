/*
* PromoteMapper.java
* http://www.wenfan.club
* Copyright © 2020 wenfan All Rights Reserved
* 作者：wenfan
* QQ：571696215
* E-Mail：guwenfan@qq.com
* 2020-01-24 14:47 Created
*/ 
package com.wenfan.seckill.mapper;

import com.wenfan.seckill.entity.Promote;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
@org.apache.ibatis.annotations.Mapper
public interface PromoteMapper extends Mapper<Promote> {

    Promote getPromoteByItemId(Integer itemId);

    List<Promote> getAllPromotion();

    Promote getPromoteByPromotionId(Integer promotionId);

    int update(@Param("promote")Promote promote);

    int deletePromotion(Integer promotionId);
}