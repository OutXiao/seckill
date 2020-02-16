/*
* SequenceInfo.java
* http://www.wenfan.club
* Copyright © 2020 wenfan All Rights Reserved
* 作者：wenfan
* QQ：571696215
* E-Mail：guwenfan@qq.com
* 2020-01-29 13:53 Created
*/ 
package com.wenfan.seckill.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sequence_info")
public class SequenceInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;

    @Column(name = "current_value")
    private Integer currentValue;

    private Integer step;

    private static final long serialVersionUID = 1L;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return current_value
     */
    public Integer getCurrentValue() {
        return currentValue;
    }

    /**
     * @param currentValue
     */
    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * @return step
     */
    public Integer getStep() {
        return step;
    }

    /**
     * @param step
     */
    public void setStep(Integer step) {
        this.step = step;
    }
}