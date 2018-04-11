package com.ray.retrofit2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ray.retrofit2.R;
import com.ray.retrofit2.widgets.DeletableEditText;
import com.ray.retrofit2.widgets.KeywordsFlow;

import java.util.Random;

/**
 * Created by 夏子青 on 2018/4/11.
 */

public class AddCityActivity2 extends Activity implements View.OnClickListener {
    private static final int FEEDKEY_START = 1;
    private ImageView back_arrow;
    private ImageView search_button;
    private Animation shakeAnim;
    private DeletableEditText searchEdit;
    private KeywordsFlow keywordsFlow;
    private int STATE = 1;
    //页面滚动的数据源
    private static String[] keywords = new String[] { "弗拉基米尔", "希维尔", "蒙多",
            "茂凯", "潘森", "波比", "拉克丝", "索拉卡", "娑娜", "伊泽瑞尔", "费德提克", " 雷克顿",
            "古拉加斯", "卡萨丁", "迦娜", "奥莉安娜", "嘉文四世", " 莫德凯撒", " 崔丝塔娜", "布兰德",
            "卡尔玛", "塔里克", "莫甘娜", "凯南", " 兰博", "斯维因" ,"卡尔萨斯"};

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case FEEDKEY_START:
                    keywordsFlow.rubKeywords();
                    feedKeywordsFlow(keywordsFlow, keywords);
                    keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
                    sendEmptyMessageDelayed(FEEDKEY_START, 5000);
                    break;
            }
        };
    };

    private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
        Random random = new Random();
        for (int i = 0; i < KeywordsFlow.MAX; i++) {
            int ran = random.nextInt(arr.length);
            String tmp = arr[ran];
            keywordsFlow.feedKeyword(tmp);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city2);
        //加载动画效果
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_y);
        initView();
    }

    private void initView() {
        keywordsFlow = (KeywordsFlow) findViewById(R.id.keywordsflow);
        keywordsFlow.setDuration(1000l);
        keywordsFlow.setOnItemClickListener(this);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        back_arrow.setAnimation(shakeAnim);
        searchEdit = (DeletableEditText) findViewById(R.id.search_view);
        search_button=(ImageView)findViewById(R.id.search_button);
        search_button.setOnClickListener(this);
        feedKeywordsFlow(keywordsFlow, keywords);
        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
        handler.sendEmptyMessageDelayed(FEEDKEY_START, 5000);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            String keyword = ((TextView) v).getText().toString().trim();
            searchEdit.setText(keyword);
            searchEdit.setSelection(keyword.length());
        }
        if(v.getId()==R.id.search_button&&searchEdit.getText().toString().length()!=0){
            Toast.makeText(AddCityActivity2.this, "你搜索了 "+searchEdit.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        back_arrow.clearAnimation();
        handler.removeMessages(FEEDKEY_START);
        STATE = 0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeMessages(FEEDKEY_START);
        STATE = 0;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeMessages(FEEDKEY_START);
        STATE = 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (STATE == 0) {
            keywordsFlow.rubKeywords();
            handler.sendEmptyMessageDelayed(FEEDKEY_START, 3000);
        }
    }
}

