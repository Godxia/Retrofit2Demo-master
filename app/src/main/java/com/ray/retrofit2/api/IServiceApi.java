package com.ray.retrofit2.api;


import com.ray.retrofit2.model.HeAirNow;
import com.ray.retrofit2.model.HeWeatherForecast;
import com.ray.retrofit2.model.HeWeatherNow;
import com.ray.retrofit2.model.SoJosnWeatherForecast;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 接口
 */
public interface IServiceApi {
    /**
     * 获取图书信息
     */
//    @GET("v2/book/1220562")
    @GET("forecast")
    Observable<HeWeatherForecast> getForecastWeatherInfo(@Query("location") String location, @Query("key") String key);
    @GET("now")
    Observable<HeWeatherNow> getNowWeatherInfo(@Query("location") String location, @Query("key") String key);
    @GET("json.shtml")
    Observable<SoJosnWeatherForecast> getSoForecastInfo(@Query("city") String city);
    @GET("now")
    Observable<HeAirNow> getAirNowInfo(@Query("location") String location, @Query("key") String key);
}
