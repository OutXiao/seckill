/*
* OrderInfo.java
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

@Table(name = "order_info")
public class OrderInfo implements Serializable {
    /**
     * 订单id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 商品id
     */
    @Column(name = "item_id")
    private Integer itemId;

    /**
     * 商品单价
     */
    @Column(name = "item_price")
    private Double itemPrice;

    /**
     * 商品数量
     */
    private Integer amount;

    /**
     * 订单总价
     */
    @Column(name = "order_price")
    private Double orderPrice;

    /**
     * 促销id
     */
    @Column(name = "promote_id")
    private Integer promoteId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取订单id
     *
     * @return id - 订单id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置订单id
     *
     * @param id 订单id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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

    /**
     * 获取商品单价
     *
     * @return item_price - 商品单价
     */
    public Double getItemPrice() {
        return itemPrice;
    }

    /**
     * 设置商品单价
     *
     * @param itemPrice 商品单价
     */
    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * 获取商品数量
     *
     * @return amount - 商品数量
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * 设置商品数量
     *
     * @param amount 商品数量
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 获取订单总价
     *
     * @return order_price - 订单总价
     */
    public Double getOrderPrice() {
        return orderPrice;
    }

    /**
     * 设置订单总价
     *
     * @param orderPrice 订单总价
     */
    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     * 获取促销id
     *
     * @return promote_id - 促销id
     */
    public Integer getPromoteId() {
        return promoteId;
    }

    /**
     * 设置促销id
     *
     * @param promoteId 促销id
     */
    public void setPromoteId(Integer promoteId) {
        this.promoteId = promoteId;
    }
}