package com.ray.retrofit2.model;

/**
 * Created by 夏子青 on 2018/4/8.
 */

public class HeAirNowBean {
    private String name;
    private String desc;
    private String value;
    public HeAirNowBean(String name, String desc, String value){
        this.name = name;
        this.desc = desc;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
