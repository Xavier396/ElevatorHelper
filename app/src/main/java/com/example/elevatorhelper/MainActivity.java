package com.example.elevatorhelper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elevatorhelper.databinding.ActivityMainBinding;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Intent i;
    int x = 5;
    TextView down_count;
    ImageView load, main_img;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding amb = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(amb.getRoot());
        /*初始化控件*/
        down_count = amb.downcount;
        load = amb.loader;
        main_img = amb.mainPic;
        ActionBar tbar = getSupportActionBar();
        tbar.hide();

        /*
         * 动画，在主页面右上角转圈圈的动画
         * */
        Animation am= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_rolling);
        LinearInterpolator linearInterpolator=new LinearInterpolator();
        am.setInterpolator(linearInterpolator);

        if (am!=null)
        {
            load.startAnimation(am);
        }
        else {
            load.setAnimation(am);
            load.startAnimation(am);
        }


        /*
         * 定时跳转，在指定事件后跳转到主页面（Alibaba代码规约建议使用ScheduledExecutorService替代Timer）
         * */
        Timer t = new Timer();

        CountDownTimer cd =new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {

                down_count.setText(String.valueOf(x));
                x--;
            }

            @Override
            public void onFinish() {
                down_count.setText("0");
            }
        };
        /*Intent，前往MenuActivity*/
        i = new Intent(getApplicationContext(), MenuActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        };
        cd.start();
        t.schedule(task, 5000);

    }
}