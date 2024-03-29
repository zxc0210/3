package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.util.SharedPreferencesUtil;

public class WelcomeActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            next();
        }
    };
    private SharedPreferencesUtil sp;

    private void next() {
        Intent intent = null;
        if (sp.isLogin()) {
            intent = new Intent(WelcomeActivity.this, DrawerLayoutActivity.class);
        } else {
            intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sp = SharedPreferencesUtil.getInstance(getApplicationContext());
        //去除欢迎界面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //2秒后进入登录界面
                handler.sendEmptyMessage(0);//进入登录界面
            }
        }, 2000);
    }
}
