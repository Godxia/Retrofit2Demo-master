package com.ray.retrofit2.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ray.retrofit2.R;
import com.ray.retrofit2.api.BaseApi;
import com.ray.retrofit2.api.IServiceApi;
import com.ray.retrofit2.db.CityManager;
import com.ray.retrofit2.fragment.WeatherFragment;
import com.ray.retrofit2.model.HeWeatherForecast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.flay_fragment)
    FrameLayout flayFragment;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private WeatherFragment weatherFragment;
    private FragmentManager fManager;
    private Toolbar mToolbar;
    private Context mContext;
    private String city;
    public Toolbar getmToolbar() {
        return mToolbar;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        super.onCreate(savedInstanceState);
        mContext=MainActivity.this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragment();
    }

    public void initFragment() {
        fManager=getFragmentManager();
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        weatherFragment = new WeatherFragment();
        fragmentTransaction.add(R.id.flay_fragment,weatherFragment);
        fragmentTransaction.commit();
    }
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        Log.i("dayang","打开Menu时执行该方法");
//        return super.onPrepareOptionsMenu(menu);
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        Log.i("dayang","onCreateOptionsMenu");
//        //创建Menu菜单
//        getMenuInflater().inflate(R.menu.menu_weather,menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.i("dayang","选择列表项时执行------------");
//        //对菜单项点击内容进行设置
//        int id = item.getItemId();
//        if (id == R.id.menu_city_manage) {
//            Intent intent=new Intent(mContext, CityManagerActivity.class);
//            startActivity(intent);
//        } else {
//            Toast.makeText(this,"关闭文件",Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }

//
//    public void initDrawer(Toolbar toolbar) {
//        if (toolbar != null) {
//            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
//                @Override
//                public void onDrawerOpened(View drawerView) {
//                    super.onDrawerOpened(drawerView);
//                }
//
//                @Override
//                public void onDrawerClosed(View drawerView) {
//                    super.onDrawerClosed(drawerView);
//                }
//            };
//            mDrawerToggle.syncState();
//            mDrawerLayout.addDrawerListener(mDrawerToggle);
//        }
//    }
}
//6358f2af8304488fbd04d4216a715077