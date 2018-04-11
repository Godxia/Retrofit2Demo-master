package com.ray.retrofit2.db;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by 夏子青 on 2018/4/9.
 */

public class CityManager extends DataSupport {
    @Column(unique = true)
    private String city_name;
    private String cond_txt;
    private String tmp;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCond_txt() {
        return cond_txt;
    }

    public void setCond_txt(String cond_txt) {
        this.cond_txt = cond_txt;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }
}
