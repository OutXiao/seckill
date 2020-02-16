/*
* Promote.java
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
import java.util.Date;

@Table(name = "promote")
public class Promote implements Serializable {
    /**
     * 促销商品id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 促销商品名称
     */
    @Column(name = "promote_name")
    private String promoteName;

    /**
     * 秒杀开始时间
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 秒杀结束时间
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 商品id
     */
    @Column(name = "item_id")
    private Integer itemId;

    /**
     * 秒杀价格
     */
    @Column(name = "promote_item_price")
    private Double promoteItemPrice;

    //秒杀活动状态  1：还未开始 2：进行中 3：已结束
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 获取促销商品id
     *
     * @return id - 促销商品id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置促销商品id
     *
     * @param id 促销商品id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取促销商品名称
     *
     * @return promote_name - 促销商品名称
     */
    public String getPromoteName() {
        return promoteName;
    }

    /**
     * 设置促销商品名称
     *
     * @param promoteName 促销商品名称
     */
    public void setPromoteName(String promoteName) {
        this.promoteName = promoteName;
    }

    /**
     * 获取秒杀开始时间
     *
     * @return start_date - 秒杀开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置秒杀开始时间
     *
     * @param startDate 秒杀开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取秒杀结束时间
     *
     * @return end_date - 秒杀结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置秒杀结束时间
     *
     * @param endDate 秒杀结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
     * 获取秒杀价格
     *
     * @return promote_item_price - 秒杀价格
     */
    public Double getPromoteItemPrice() {
        return promoteItemPrice;
    }

    /**
     * 设置秒杀价格
     *
     * @param promoteItemPrice 秒杀价格
     */
    public void setPromoteItemPrice(Double promoteItemPrice) {
        this.promoteItemPrice = promoteItemPrice;
    }
}