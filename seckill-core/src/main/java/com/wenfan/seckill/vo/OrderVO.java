package com.wenfan.seckill.vo;

import java.math.BigDecimal;

/**
 * Created by wenfan on 2020/1/28 22:20
 */
public class OrderVO {

    private String id;

    //若非空，则表示以秒杀商品方式下单
    private Integer promoId;

    //购买时的价格，若promoId非空，则表示秒杀商品价格
    private BigDecimal itemPrice;

    //购买用户id
    private Integer userId;

    //购买商品id
    private Integer itemId;

    //购买数量
    private Integer amount;

    //订单总金额，若promoId非空，则表示秒杀商品价格
    private BigDecimal orderPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
