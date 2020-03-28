/*
* ItemUser.java
* http://www.wenfan.club
* Copyright © 2020 wenfan All Rights Reserved
* 作者：wenfan
* QQ：571696215
* E-Mail：guwenfan@qq.com
* 2020-03-04 13:40 Created
*/ 
package com.wenfan.seckill.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "item_user")
public class ItemUser implements Serializable {
    /**
     * 商品id
     */
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    /**
     * 购买用户id
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取商品id
     *
     * @return item_id - 商品id
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * 设置商品id
     *
     * @param itemId 商品id
     */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取购买用户id
     *
     * @return user_id - 购买用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置购买用户id
     *
     * @param userId 购买用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}