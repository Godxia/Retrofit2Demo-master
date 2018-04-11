package com.ray.retrofit2.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.ray.retrofit2.R;
import com.ray.retrofit2.adapter.CityManagerAdapter;
import com.ray.retrofit2.api.BaseApi;
import com.ray.retrofit2.api.IServiceApi;
import com.ray.retrofit2.db.CityManager;
import com.ray.retrofit2.model.HeWeatherNow;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by 夏子青 on 2018/4/9.
 */

public class CityManagerActivity extends AppCompatActivity {
    @BindView(R.id.rv_city_list)
    RecyclerView rvCityList;
    private CityManagerAdapter adapter;
    private int dragStartPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manage);
        ButterKnife.bind(this);
        initview();
    }

    private void initview(){
        final List<CityManager> citys= DataSupport.findAll(CityManager.class);
        //加载实况天气
      //  Log.d("城市",citys.get(0).getCity_name());
     //   citys.get(0).setCond_txt("晴");
      //  citys.get(0).setTmp("15");
        for (int i=0;i<citys.size();i++){
            Log.d("司马接口","执行");
            final int finalI = i;
            new BaseApi("https://free-api.heweather.com/s6/weather/").request(BaseApi.createApi(IServiceApi.class)
                            .getNowWeatherInfo(citys.get(i).getCity_name(), "6358f2af8304488fbd04d4216a715077"),    //传递的是被观察者，和接口的实现。
                    new BaseApi.IResponseListener<HeWeatherNow>() {
                        @Override
                        public void onSuccess(HeWeatherNow data) {
                           // Log.d("司马接口",data.HeWeather6.get(0).now.cond_txt);
                            citys.get(finalI).setCond_txt(data.HeWeather6.get(0).now.cond_txt);
                            citys.get(finalI).setTmp(data.HeWeather6.get(0).now.tmp);
                        }

                        @Override
                        public void onFail() {
                            Log.d("司马接口","失败");
                        }
                    });
        }

        adapter=new CityManagerAdapter(R.layout.item_card_weather,citys);
       // if(adapter.getData().isEmpty())Log.d("适配器","装在");
        Log.d("adapter有几个", String.valueOf(adapter.getData().size()));
       // Log.d(adapter.getData().get(0).getCond_txt(),adapter.getData().get(0).getCity_name());
        LinearLayoutManager layoutManager = new LinearLayoutManager(CityManagerActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCityList.setLayoutManager(layoutManager);
        rvCityList.setAdapter(adapter);
        swipe();
        //子项点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter badapter, View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("city_name",adapter.getData().get(position).getCity_name());
               // selectedItem = position;
                setResult(2,intent);
                onBackPressed();
            }
        });

    }
    /**
     * 滑动删除
     */
    private void swipe(){
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
                if (source.getAdapterPosition() == 0 || target.getAdapterPosition() == 0)
                    return false;
                else
                    return super.onMove(recyclerView, source, target);
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() == 0)
                    return makeMovementFlags(0, 0);
                else
                    return super.getMovementFlags(recyclerView, viewHolder);
            }


        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rvCityList);
        itemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);

        // 开启拖拽
        adapter.enableDragItem(itemTouchHelper, R.id.card_root, true);
        adapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                dragStartPosition = pos;
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                if (dragStartPosition != pos) {
             //       dataChanged = true;
                }
            }
        });

        // 开启滑动删除
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                DataSupport.deleteAll(CityManager.class, "city_name = ?", adapter.getItem(pos).getCity_name());
                Snackbar.make(getWindow().getDecorView().getRootView().findViewById(android.R.id.content), adapter.getItem(pos).getCity_name() + " 删除成功!",
                        Snackbar.LENGTH_LONG).setActionTextColor(getResources().getColor(R.color.actionColor)).show();
                //dataChanged = true;
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
    }



    /****
     * 更新adapter
     */
    public void updateCity(){
        adapter.notifyDataSetChanged();
    }
    /******
     * 创建菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("dayang","onCreateOptionsMenu");
        //创建Menu菜单
        getMenuInflater().inflate(R.menu.menu_add_city,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("dayang","选择列表项时执行------------");
        //对菜单项点击内容进行设置
        int id = item.getItemId();
        if (id == R.id.menu_add_city) {
            Intent intent=new Intent(CityManagerActivity.this, AddCityActivity.class);
            startActivityForResult(intent,1);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String city=data.getStringExtra("city_name");
            List<CityManager> cityManagers= DataSupport.where("city_name=?",city).find(CityManager.class);
            if (city!=null){
                adapter.addData(cityManagers);
                updateCity();
            }
        }
    }


}
