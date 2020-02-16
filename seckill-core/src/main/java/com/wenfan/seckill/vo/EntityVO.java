package com.wenfan.seckill.vo;

import java.util.List;

/**
 * @author:wenfan
 * @description:
 * @data: 2019/2/23 10:20
 */
public class EntityVO<T> {


    private int recordsTotal;

    private int recordsFiltered;

    private List<T> data;


    @SuppressWarnings("unchecked")
    public EntityVO(int recordsTotal, int recordsFiltered, List<T> data) {
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }


    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
