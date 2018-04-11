package com.ray.retrofit2.model;

import java.util.List;

/**
 * Created by 夏子青 on 2018/4/8.
 */

public class SoJosnWeatherForecast {
    public DataBean data;
    public String message;
    public class DataBean{
        public List<ForecastBean> forecast;
        public class ForecastBean{
            public String date;
            public String type;
          //  public float aqi;
        }
    }
}
