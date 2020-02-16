/*
* StockLog.java
* http://www.wenfan.club
* Copyright © 2020 wenfan All Rights Reserved
* 作者：wenfan
* QQ：571696215
* E-Mail：guwenfan@qq.com
* 2020-02-08 14:53 Created
*/ 
package com.wenfan.seckill.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "stock_log")
public class StockLog implements Serializable {
    /**
     * 流水操作主键
     */
    @Id
    @Column(name = "stock_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String stockLogId;

    /**
     * 商品id
     */
    @Column(name = "item_id")
    private Integer itemId;

    /**
     * 商品数量
     */
    private Integer amount;

    // 1 初始状态 2，下单扣减成功，3 下单回滚
    private Integer status;

    @Column(name = "operating_time")
    private Date operatingTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取流水操作主键
     *
     * @return stock_log_id - 流水操作主键
     */
    public String getStockLogId() {
        return stockLogId;
    }

    /**
     * 设置流水操作主键
     *
     * @param stockLogId 流水操作主键
     */
    public void setStockLogId(String stockLogId) {
        this.stockLogId = stockLogId;
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


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(Date operatingTime) {
        this.operatingTime = operatingTime;
    }
}