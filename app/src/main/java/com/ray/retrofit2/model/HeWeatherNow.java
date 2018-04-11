package com.ray.retrofit2.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 夏子青 on 2018/4/7.
 */

public class HeWeatherNow {
    public List<HeWeatherNow.HeWeather6> HeWeather6;
    public class HeWeather6 implements Serializable{
        public NowBean now;
        public String status;
        public class NowBean implements Serializable{
            public String cond_txt;
            public String tmp;
            public String hum;
            public String pres;
            public String wind_dir;
            public String wind_sc;
        }
    }
}
