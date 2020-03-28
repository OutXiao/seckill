package com.wenfan.seckill.model;

import com.wenfan.seckill.entity.Item;
import com.wenfan.seckill.entity.Promote;

/**
 * Created by wenfan on 2020/2/16 19:56
 */
public class AdminPromotionModel extends Promote {

    /**
     * 商品价格
     */
    private Double price;

    private Integer sales;

    private String imgUrl;

    private Integer realStock;

    private Integer promoteStock;



    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Integer getRealStock() {
        return realStock;
    }

    public void setRealStock(Integer realStock) {
        this.realStock = realStock;
    }

    public Integer getPromoteStock() {
        return promoteStock;
    }

    public void setPromoteStock(Integer promoteStock) {
        this.promoteStock = promoteStock;
    }
}
