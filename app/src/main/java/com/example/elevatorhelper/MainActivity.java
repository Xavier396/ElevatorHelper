package com.example.elevatorhelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elevatorhelper.Impl.DBMaker;
import com.example.elevatorhelper.databinding.ActivityMainBinding;
import com.example.elevatorhelper.pojo.Elevator;
import com.example.elevatorhelper.pojo.Issue;
import com.example.elevatorhelper.pojo.User;

import org.apache.commons.lang3.RandomStringUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.elevatorhelper.Constant.DB_NAME;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_NAME;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_VALUE_FIRST_ADD;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_VALUE_IS_ADMIN;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_VALUE_IS_LOGIN_NOW;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_VALUE_LOGIN_NAME;

public class MainActivity extends AppCompatActivity {
    private Intent i;
    private int x = 5;
    private TextView down_count;
    private ImageView load, main_img;
    private Handler handler;
    private SQLiteDatabase db;
    private char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private char[] numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

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

        /**
         * 数据操作：
         * 在这一部分进行对数据库和SharedPreferences的处理
         *
         * 1、添加一点假数据
         * 2、读取SharedPreference，判断
         *  2.0、建表
         *  2.1、true，写入假数据
         *  2.2、false，已经写过数据了，忽略掉
         * 3、写入SharedPreference,写入false
         *
         * */

        List<Elevator> e_list = new ArrayList<>();
        List<User> u_list = new ArrayList<>();
        List<Issue> i_list = new ArrayList<>();
        //写10条电梯数据进去
        for (int i = 0; i < 10; i++) {
            Elevator e = new Elevator();
            e.setElevatorNumber(RandomStringUtils.random(1, letters)
                    + "单元" + RandomStringUtils.random(3, numbers) + "号电梯");
            e.setStatus(i % 4);
            e.setComment("暂无显示消息");
            e_list.add(e);
        }
        //写9条用户消息和1条管理员消息
        for (int j = 0; j < 9; j++) {
            User u = new User();
//            Resources res=
            u.setUserHead("User");
            u.setUserName(RandomStringUtils.random(5, letters));
            u.setUserPasswordHash("123456");
            u.setUserPhone(RandomStringUtils.random(11, numbers));
            if (j%2==0)
            {
                u.setIconHead(1);
            }
            else
            {
                u.setIconHead(4);
            }
//            u.setUserHead();
            u_list.add(u);
        }
        User admin = new User();
        admin.setUserPhone("17858674587");
        admin.setUserPasswordHash("yhjzshhh!");
        admin.setUserName("yhjzsAdmin");
        admin.setUserHead("Admin");
        admin.setIconHead(3);
        u_list.add(admin);

        SharedPreferences appConfig = getSharedPreferences(SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor configEditor = appConfig.edit();
        DBMaker dbMaker = new DBMaker(getApplicationContext(), DB_NAME, null, 1);

        if (appConfig.getBoolean(SHARED_PREFERENCE_VALUE_FIRST_ADD, true)) {
            db = dbMaker.getWritableDatabase();
            for (Elevator e : e_list) {
//在这里添加SQL生成语句
                db.execSQL("INSERT INTO Elevator  values(NULL,?,?,?)", new Object[]{e.getElevatorNumber(), e.getStatus(), e.getComment()});
            }
            for (User u : u_list
            ) {
                db.execSQL("INSERT INTO User values(NULL,?,?,?,?,?)",new Object[]{u.getUserName(),u.getUserPhone(),u.getUserPasswordHash(),u.getUserHead(),u.getIconHead()});
            }
            db.close();
            //App是否初次运行？不是的话就不会再往里面写数据了
            configEditor.putBoolean(SHARED_PREFERENCE_VALUE_FIRST_ADD,false);
            //是否管理员
            configEditor.putBoolean(SHARED_PREFERENCE_VALUE_IS_ADMIN,false);
            //登录名称
            configEditor.putString(SHARED_PREFERENCE_VALUE_LOGIN_NAME,null);
            //是不是处在登陆状态
            configEditor.putBoolean(SHARED_PREFERENCE_VALUE_IS_LOGIN_NOW,false);

            configEditor.apply();
        }



        /*
         * 动画，在主页面右上角转圈圈的动画
         * */
        Animation am = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_rolling);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        am.setInterpolator(linearInterpolator);

        if (am != null) {
            load.startAnimation(am);
        } else {
            load.setAnimation(am);
            load.startAnimation(am);
        }


        /*
         * 定时跳转，在指定事件后跳转到主页面（Alibaba代码规约建议使用ScheduledExecutorService替代Timer）
         * */
        Timer t = new Timer();

        CountDownTimer cd = new CountDownTimer(5000, 1000) {
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