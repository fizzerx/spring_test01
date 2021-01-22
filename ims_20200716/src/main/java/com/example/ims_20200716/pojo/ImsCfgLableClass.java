package com.example.ims_20200716.pojo;

import lombok.Data;

@Data
public class ImsCfgLableClass {
    private String LABLE_CLASS_ID;
    private String LABLE_CLASS_NAME;
    private String ORDERS;

    public String getLableClassId() {
        return LABLE_CLASS_ID;
    }
    public void setLableClassId(String lableClassId) {
        this.LABLE_CLASS_ID = lableClassId;
    }
    public String getLableName() {
        return LABLE_CLASS_NAME;
    }
    public void setLableClassName(String lableClassName) {
        this.LABLE_CLASS_NAME = lableClassName;
    }
    public String getOrders() {
        return ORDERS;
    }
    public void setOrders(String orders) {
        this.ORDERS = orders;
    }
}
