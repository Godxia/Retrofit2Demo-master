package com.ray.retrofit2.api;

import android.util.Log;

import com.google.gson.Gson;
import com.ray.retrofit2.MyApplication;
import com.ray.retrofit2.model.HeWeatherForecast;
import com.ray.retrofit2.model.HeWeatherNow;
import com.ray.retrofit2.utils.NetUtils;
import com.ray.retrofit2.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Azure on 2016/9/5.
 */
public class BaseApi {

    private static String LOCAL_SERVER_URL ="www.baidu.com";
    public BaseApi(String url){
        LOCAL_SERVER_URL=url;
    }
    public static <T> T createApi(Class<T> service) {
        Log.d("网址",LOCAL_SERVER_URL);
        final String url = LOCAL_SERVER_URL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(MyApplication.getApplication().genericClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

    public static <T> void request(Observable<T> observable,
                                   final IResponseListener<T> listener) {

        if (!NetUtils.isConnected(MyApplication.getApplication())) {   //判断是否联网
            ToastUtils.getInstance().showToast("网络不可用,请检查网络");
            if (listener != null) {
                listener.onFail();
            }
            return;
        }
        observable.subscribeOn(Schedulers.io())                       //用于指定发射者的线程，如果多次指定，只有第一次有效，其他的被忽略
                .observeOn(AndroidSchedulers.mainThread())            //用于指定subscribe发生在主线程，也就是订阅者接受事件的线程
                .subscribe(new Observer<T>() {

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                                   Log.d("onError", e.getMessage());
                                   Log.d("天气获取失败","重试");
                                   if (listener != null) {
                                       listener.onFail();
                                   }
                               }

                               @Override
                               public void onComplete() {

                               }

                               @Override
                               public void onSubscribe(Disposable disposable) {

                               }

                               @Override
                               public void onNext(T data) {
                                   if (listener != null) {
                                       listener.onSuccess(data);
                                   }
                               }
                           }
                );
    }

    public interface IResponseListener<T> {

        void onSuccess(T data);

        void onFail();
    }

}
