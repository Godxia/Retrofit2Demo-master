package com.ray.retrofit2.model;

import java.util.List;

/**
 * Created by 夏子青 on 2018/4/8.
 */

public class HeAirNow {
    public List<HeWeather6Bean> HeWeather6;
    public class HeWeather6Bean{
        public AirNowBean air_now_city;
        public class AirNowBean{
            public String aqi;
            public String co;
            public String no2;
            public String o3;
            public String pm10;
            public String pm25;
            public String so2;
            public String qlty;
        }
    }
}
