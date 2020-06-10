package com.example.elevatorhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.elevatorhelper.databinding.ActivityElevatorInfoBinding;

public class ElevatorInfoActivity extends AppCompatActivity {
 TextView numberOfList,elevatorNumber,elevatorStatus,otherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityElevatorInfoBinding aeb=ActivityElevatorInfoBinding.inflate(getLayoutInflater());
        setContentView(aeb.getRoot());
        Intent i=getIntent();
        /*接受来自点击事件的数据*/
        Integer po=i.getIntExtra("position",0);
        String title=i.getStringExtra("title");
        Integer status=i.getIntExtra("status",0);
        String comments=i.getStringExtra("comment");
        numberOfList=aeb.numberOfList;
        elevatorNumber=aeb.elevatorNumber;
        elevatorStatus=aeb.elevatorStatus;
        otherInfo=aeb.otherInfo;
        /*对信息的填充*/
        numberOfList.setText("第"+po+"条");
        elevatorNumber.setText(elevatorNumber.getText()+title);
//        elevatorStatus.setText(elevatorStatus.getText()+String.valueOf(status));
        switch (status)
        {
            case 0:
                elevatorStatus.setText(elevatorStatus.getText()+"正常");
                break;
            case 1:
                elevatorStatus.setText(elevatorStatus.getText()+"停止使用");
                break;
            case 2:
                elevatorStatus.setText(elevatorStatus.getText()+"故障停运");
                break;
            case 3:
                elevatorStatus.setText(elevatorStatus.getText()+"维护中");
                break;
        }
        if (comments==null||comments.trim().isEmpty())
        {
            otherInfo.setText(otherInfo.getText()+"无可显示的消息");
        }
        else if (comments.trim()!=null&&!comments.trim().isEmpty())
        {
            otherInfo.setText(otherInfo.getText()+comments);
        }







    }
}