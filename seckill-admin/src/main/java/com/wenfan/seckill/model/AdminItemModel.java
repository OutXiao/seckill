package com.wenfan.seckill.model;

import com.wenfan.seckill.entity.Item;

import java.util.Date;

/**
 * Created by wenfan on 2020/2/16 11:55
 */
public class AdminItemModel extends Item {

    private Integer stock;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
