/*
* ItemStock.java
* http://www.wenfan.club
* Copyright © 2020 wenfan All Rights Reserved
* 作者：wenfan
* QQ：571696215
* E-Mail：guwenfan@qq.com
* 2020-01-24 14:47 Created
*/ 
package com.wenfan.seckill.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "item_stock")
public class ItemStock implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品id
     */
    @Column(name = "item_id")
    private Integer itemId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品库存
     *
     * @return stock - 商品库存
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置商品库存
     *
     * @param stock 商品库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

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
}