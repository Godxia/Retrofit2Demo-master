package com.ray.retrofit2.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ray.retrofit2.R;
import com.ray.retrofit2.activity.CityManagerActivity;
import com.ray.retrofit2.adapter.AqiAdapter;
import com.ray.retrofit2.adapter.DailyWeatherAdapter;
import com.ray.retrofit2.api.BaseApi;
import com.ray.retrofit2.api.IServiceApi;
import com.ray.retrofit2.model.DailyWeatherBean;
import com.ray.retrofit2.model.HeAirNow;
import com.ray.retrofit2.model.HeAirNowBean;
import com.ray.retrofit2.model.HeWeatherForecast;
import com.ray.retrofit2.model.HeWeatherNow;
import com.ray.retrofit2.model.SoJosnWeatherForecast;
import com.ray.retrofit2.utils.ThemeUtil;
import com.ray.retrofit2.widgets.AqiView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 夏子青 on 2018/4/6.
 */

public class WeatherFragment extends Fragment {
    @BindView(R.id.tv_basic_temp)
    TextView tvBasicTemp;
    @BindView(R.id.tv_basic_description)
    TextView tvBasicDescription;
    @BindView(R.id.tv_basic_temp_max)
    TextView tvBasicTempMax;
    @BindView(R.id.tv_basic_temp_min)
    TextView tvBasicTempMin;
    @BindView(R.id.tv_humid)
    TextView tvHumid;
    @BindView(R.id.tv_pres)
    TextView tvPres;
    @BindView(R.id.tv_wind_sc)
    TextView tvWindSc;
    @BindView(R.id.tv_wind_dir)
    TextView tvWindDir;
    @BindView(R.id.relay_basic)
    RelativeLayout relayBasic;
    @BindView(R.id.reclay_daily)
    RecyclerView reclayDaily;
    Unbinder unbinder;
    protected View mRootView;
    @BindView(R.id.aqiview)
    AqiView aqiview;
    @BindView(R.id.recyclerViewAqi)
    RecyclerView recyclerViewAqi;
    @BindView(R.id.tv_title_city)
    TextView tvTitleCity;
    private Toolbar mToolbar;
    private DailyWeatherAdapter adapter;
    private Rect localRect = new Rect();
    private boolean aqiViewLastVisible = false;//控制aqi动
    public HeAirNow currentData;
    private Toolbar parentToolbar;
    private AqiAdapter aqiAdapter;
    private String last_city = "北京";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);  // 这是关键的一句
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_weather, menu);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_weather, container, false);
        // mToolbar= (Toolbar) getActivity().findViewById(R.id.toolbar);
        unbinder = ButterKnife.bind(this, mRootView);
        initView(last_city);

        return mRootView;
    }

    /***
     * 加载Menu
     * @param
     */
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        Log.i("dayang","打开Menu时执行该方法");
//        return super.onPrepareOptionsMenu(menu);
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        Log.i("dayang","onCreateOptionsMenu");
//        //创建Menu菜单
//        MagetMenuInflater().inflate(R.menu.menu_weather,menu);
//        return true;
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("dayang", "选择列表项时执行------------");
        //对菜单项点击内容进行设置
        int id = item.getItemId();
        if (id == R.id.menu_city_manage) {
            Intent intent = new Intent(getActivity(), CityManagerActivity.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    public void initView(String city) {
        tvTitleCity.setText(city);
        Log.d("查询", city);
        //加载实况天气
        new BaseApi("https://free-api.heweather.com/s6/weather/").request(BaseApi.createApi(IServiceApi.class).getNowWeatherInfo(city, "6358f2af8304488fbd04d4216a715077"),    //传递的是被观察者，和接口的实现。
                new BaseApi.IResponseListener<HeWeatherNow>() {
                    @Override
                    public void onSuccess(HeWeatherNow data) {


                        System.out.print("呵呵\n");
                        tvBasicDescription.setText(data.HeWeather6.get(0).now.cond_txt);
                        tvBasicTemp.setText(data.HeWeather6.get(0).now.tmp + "°");
                        tvHumid.setText(data.HeWeather6.get(0).now.hum + "%");
                        tvPres.setText(data.HeWeather6.get(0).now.pres);
                        tvWindSc.setText(data.HeWeather6.get(0).now.wind_sc + "级");
                        tvWindDir.setText(data.HeWeather6.get(0).now.wind_dir);
                    }

                    @Override
                    public void onFail() {

                    }
                });
        //加载今天的高温低温

        new BaseApi("https://free-api.heweather.com/s6/weather/").request(BaseApi.createApi(IServiceApi.class).getForecastWeatherInfo(city, "6358f2af8304488fbd04d4216a715077"),    //传递的是被观察者，和接口的实现。
                new BaseApi.IResponseListener<HeWeatherForecast>() {
                    @Override
                    public void onSuccess(HeWeatherForecast data) {
                        tvBasicTempMax.setText(data.HeWeather6.get(0).daily_forecast.get(0).tmp_max + "℃");
                        tvBasicTempMin.setText(data.HeWeather6.get(0).daily_forecast.get(0).tmp_min + "℃");
                    }

                    @Override
                    public void onFail() {

                    }
                });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        if (reclayDaily == null) {
            Log.d("空指针", "异常");
        } else reclayDaily.setLayoutManager(layoutManager);
        //加载每日天气预报
        new BaseApi("https://www.sojson.com/open/api/weather/").request(BaseApi.createApi(IServiceApi.class).getSoForecastInfo(city),    //传递的是被观察者，和接口的实现。
                new BaseApi.IResponseListener<SoJosnWeatherForecast>() {
                    @Override
                    public void onSuccess(SoJosnWeatherForecast data) {
                        // Log.d("信息x",data.message);
                        if (data.message.equals("Success !")) {
                            ArrayList<DailyWeatherBean> datas = new ArrayList<>();
                            DailyWeatherBean model;
                            //     Log.d("信息x", String.valueOf(data.data.forecast.size()));
                            for (int i = 0; i < data.data.forecast.size(); i++) {
                                model = new DailyWeatherBean();
                                model.weekday = data.data.forecast.get(i).date.substring(3, 6);
                                model.cond_txt = data.data.forecast.get(i).type;
                                //      Log.d("信息x", model.weekday);
                                //    Log.d("信息x", model.cond_txt);
                                datas.add(model);
                            }
                            adapter = new DailyWeatherAdapter(R.layout.item_weather_daily, datas);

                            reclayDaily.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                });
        recyclerViewAqi.setLayoutManager(new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        aqiAdapter = new AqiAdapter(R.layout.item_weather_aqi, null);
        aqiAdapter.setDuration(1000);
        aqiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerViewAqi.setAdapter(aqiAdapter);
        //加载空气信息
        new BaseApi("https://free-api.heweather.com/s6/air/").request(BaseApi.createApi(IServiceApi.class).getAirNowInfo(city, "6358f2af8304488fbd04d4216a715077"),    //传递的是被观察者，和接口的实现。
                new BaseApi.IResponseListener<HeAirNow>() {
                    @Override
                    public void onSuccess(HeAirNow data) {
                        Log.d("信息", data.HeWeather6.get(0).air_now_city.aqi);
                        aqiview.setAqi(data);
                        currentData = data;
                        setAqiDetail(data);
                    }

                    @Override
                    public void onFail() {

                    }
                });
        ;


    }
//    public String DeleteChar(String str){
//        for(int i=0;i<str.length();i++){
//            if(i<=2) str.replace("str.charAt(i)","");
//        }
//        return str;
//    }

    /***
     * 加载详细的aqi信息
     * @param weather
     */
    private void setAqiDetail(HeAirNow weather) {
        List<HeAirNowBean> list = new ArrayList<>();
        HeAirNowBean pm25 = new HeAirNowBean("PM2.5", "细颗粒物", weather.HeWeather6.get(0).air_now_city.pm25);
        list.add(pm25);
        HeAirNowBean pm10 = new HeAirNowBean("PM10", "可吸入颗粒物", weather.HeWeather6.get(0).air_now_city.pm10);
        list.add(pm10);
        HeAirNowBean so2 = new HeAirNowBean("SO2", "二氧化硫", weather.HeWeather6.get(0).air_now_city.so2);
        list.add(so2);
        HeAirNowBean no2 = new HeAirNowBean("NO2", "二氧化氮", weather.HeWeather6.get(0).air_now_city.no2);
        list.add(no2);
        HeAirNowBean co = new HeAirNowBean("CO", "一氧化碳", weather.HeWeather6.get(0).air_now_city.co);
        list.add(co);
        HeAirNowBean o3 = new HeAirNowBean("O3", "臭氧", weather.HeWeather6.get(0).air_now_city.o3);
        list.add(o3);
        aqiAdapter.setNewData(list);
    }

    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //  parentToolbar= (Toolbar) getView().findViewById(R.id.toolbar);
        float heightPixels = getActivity().getResources().getDisplayMetrics().heightPixels;
        float fraction = (scrollY - heightPixels * 0.8f + parentToolbar.getHeight()) / parentToolbar.getHeight();

        if (fraction <= 0) {
            fraction = 0;
        }

        if (fraction >= 1) {
            fraction = 1;
        }

        // int newColor = ThemeUtil.changeAlpha(dynamicWeatherView.getColor(), fraction);
        int titleColor = ThemeUtil.changeAlpha(0xFFFFFFFF, fraction);
        // parentToolbar.setBackgroundColor(newColor);
        parentToolbar.setTitleTextColor(titleColor);

        if (aqiview.getLocalVisibleRect(localRect)) {
            if (!aqiViewLastVisible) {
                aqiview.setAqi(currentData);
            }
            aqiViewLastVisible = true;
        } else {
            aqiViewLastVisible = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String qcity = data.getStringExtra("city_name");
            initView(qcity);
        }
    }

    public void updateView(String city) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected <T extends View> T findView(@IdRes int id) {
        return (T) mRootView.findViewById(id);
    }
}
