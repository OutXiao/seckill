package com.wenfan.seckill.vo;

import java.math.BigDecimal;

/**
 * Created by wenfan on 2020/1/26 15:33
 */
public class ItemVO {

    private Integer id;
    private String title;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private Integer sales;
    private String imgUrl;


    // 秒杀活动id
    private Integer promoteId;

    //记录商品是否在秒杀活动中，0：没有秒杀活动 1：秒杀活动未开始 2：秒杀进行中
    private Integer promoteStatus;

    // 秒杀价格
    private BigDecimal promotePrice;

    // 秒杀开启时间
    private String promoteStartDate;

    private String promoteEndDate;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getPromoteId() {
        return promoteId;
    }

    public void setPromoteId(Integer promoteId) {
        this.promoteId = promoteId;
    }

    public Integer getPromoteStatus() {
        return promoteStatus;
    }

    public void setPromoteStatus(Integer promoteStatus) {
        this.promoteStatus = promoteStatus;
    }

    public BigDecimal getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(BigDecimal promotePrice) {
        this.promotePrice = promotePrice;
    }

    public String getPromoteStartDate() {
        return promoteStartDate;
    }

    public void setPromoteStartDate(String promoteStartDate) {
        this.promoteStartDate = promoteStartDate;
    }

    public String getPromoteEndDate() {
        return promoteEndDate;
    }

    public void setPromoteEndDate(String promoteEndDate) {
        this.promoteEndDate = promoteEndDate;
    }
}

