package com.ray.retrofit2.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangleilei on 2017/6/28.
 */

public class HeWeatherForecast implements Serializable {
    //public HeWeather6 HeWeather6;
    public List<HeWeather6> HeWeather6;
    public class HeWeather6 implements Serializable{
        public BasicBean basic;
        public UpdateBean update;


        public class BasicBean implements Serializable{
            public String cid;
            public String location;
            public String parent_city;
            public String lat;
            public String lon;
            public String tz;
        }
        public class UpdateBean implements Serializable{
            public String loc;
            public String utc;
        }
        public  List<DailyForecast> daily_forecast;
        public class DailyForecast implements Serializable{
            public String cond_txt_d;
            public String hum;
            public String pres;
            public String wind_sc;
            public String wind_dir;
            public String tmp_max;
            public String tmp_min;
        }
    }

}
