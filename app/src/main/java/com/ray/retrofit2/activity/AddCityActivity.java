package com.ray.retrofit2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ray.retrofit2.R;
import com.ray.retrofit2.adapter.DailyWeatherAdapter;
import com.ray.retrofit2.api.BaseApi;
import com.ray.retrofit2.api.IServiceApi;
import com.ray.retrofit2.db.CityManager;
import com.ray.retrofit2.model.DailyWeatherBean;
import com.ray.retrofit2.model.HeWeatherNow;
import com.ray.retrofit2.model.SoJosnWeatherForecast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 夏子青 on 2018/4/9.
 */

public class AddCityActivity extends AppCompatActivity {
    @BindView(R.id.edt_add_city)
    EditText edtAddCity;
    @BindView(R.id.btn_add_city)
    Button btnAddCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        ButterKnife.bind(this);
    }
    @OnClick (R.id.btn_add_city)
    public void addCity(View view){
        final String city= String.valueOf(edtAddCity.getText());
        //加载实况天气
        new BaseApi("https://free-api.heweather.com/s6/weather/").request(BaseApi.createApi(IServiceApi.class).getNowWeatherInfo(city, "6358f2af8304488fbd04d4216a715077"),    //传递的是被观察者，和接口的实现。
                new BaseApi.IResponseListener<HeWeatherNow>() {
                    @Override
                    public void onSuccess(HeWeatherNow data) {
                        if(data.HeWeather6.get(0).status.equals("ok")) {
                            List<CityManager> cityManagers= DataSupport.where("city_name=?",city).find(CityManager.class);
                            if(cityManagers.size()==0){
                                CityManager model = new CityManager();
                                model.setCity_name(city);
                                model.setCond_txt(data.HeWeather6.get(0).now.cond_txt);
                                model.setTmp(data.HeWeather6.get(0).now.tmp);
                                model.save();
                                Toast.makeText(AddCityActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent();
                                intent.putExtra("city_name",city);
                                setResult(2,intent);
                                onBackPressed();
                            }
                            else{
                                Toast.makeText(AddCityActivity.this,"重复的城市！",Toast.LENGTH_SHORT).show();
                            }


                        }
                        else if(data.HeWeather6.get(0).status.equals("unknown city")){
                            Toast.makeText(AddCityActivity.this,"请输入正确的城市名称",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }
}
