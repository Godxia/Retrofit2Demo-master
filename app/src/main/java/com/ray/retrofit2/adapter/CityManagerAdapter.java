package com.ray.retrofit2.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ray.retrofit2.R;
import com.ray.retrofit2.db.CityManager;

import java.util.List;

/**
 * Created by 夏子青 on 2018/4/9.
 */

public class CityManagerAdapter extends BaseItemDraggableAdapter<CityManager,BaseViewHolder> {
    OnItemClickListener onItemClickListener;
    public CityManagerAdapter(int layoutResId, List<CityManager> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityManager item) {
        Log.d("王八","蛋");
        Log.d("王八",item.getCond_txt());
        int img=getIcon(item);
        helper.setText(R.id.tv_card_city_name,item.getCity_name())
                .setText(R.id.tv_card_weather,item.getCond_txt())
                .setText(R.id.tv_card_temp,item.getTmp())
                .setImageResource(R.id.iv_card_weather,img);
    }
    private int getIcon(CityManager item){
        int img=1;
        switch (item.getCond_txt()){
            case "晴":img=R.drawable.sunny;
                break;
            case "多云":img=R.drawable.cloudy;
                break;
            case "阴":img=R.drawable.overcast;
                break;
            case "扬沙":img=R.drawable.sand;
                break;
        }
        return img;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int positions) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(CityManagerAdapter.this, v, positions);
                }
            }
        });
        super.onBindViewHolder(holder, positions);
    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        onItemClickListener = listener;
    }

}
