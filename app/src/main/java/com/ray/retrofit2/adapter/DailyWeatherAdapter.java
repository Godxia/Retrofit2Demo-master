package com.ray.retrofit2.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ray.retrofit2.R;
import com.ray.retrofit2.model.DailyWeatherBean;

import java.util.List;

/**
 * Created by 夏子青 on 2018/4/7.
 */

public class DailyWeatherAdapter extends BaseQuickAdapter<DailyWeatherBean,BaseViewHolder> {

    public DailyWeatherAdapter(int layoutResId, List<DailyWeatherBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyWeatherBean item) {
        int img=getIcon(item);
       // Log.d("执行","了");
        helper.setText(R.id.tv_weekday,item.weekday)
                .setText(R.id.tv_cond_txt,item.cond_txt)
                .setImageResource(R.id.iv_daily_weather,img);
    }
    private int getIcon(DailyWeatherBean item){
        int img=1;
       // Log.d("执行","了");
        switch (item.cond_txt){
            case "晴":img=R.drawable.sunny;
                break;
            case "多云":img=R.drawable.cloudy;
                break;
            case "阴":img=R.drawable.overcast;
                break;
            case "扬沙":img=R.drawable.sand;
                break;
            default:img=R.drawable.sunny;
        }
        return img;
    }
}
