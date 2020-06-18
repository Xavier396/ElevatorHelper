package com.example.elevatorhelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elevatorhelper.Impl.DBMaker;
import com.example.elevatorhelper.databinding.ActivityElevatorInfoBinding;
import com.example.elevatorhelper.databinding.ActivityRegisterBinding;

import org.apache.commons.lang3.RandomStringUtils;

import static com.example.elevatorhelper.Constant.DB_NAME;
import static com.example.elevatorhelper.Constant.TABLE_NAME_USER;

public class RegisterActivity extends AppCompatActivity {

    EditText phone,password,repeatPassword;
    Button submit,reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding arb= ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(arb.getRoot());
        phone=arb.phoneNumber;
        password=arb.passwordForRegsister;
        repeatPassword=arb.repeatPassword;
        submit=arb.regsisterSubmit;
        reset=arb.regsisterReset;
        DBMaker dbmaker=new DBMaker(getApplicationContext(),DB_NAME,null,1);
        SQLiteDatabase db=dbmaker.getWritableDatabase();
        reset.setOnClickListener(v->{
            phone.setText("");
            password.setText("");
            repeatPassword.setText("");
        });
        submit.setOnClickListener(v->{
            if ("".equals(phone.getText().toString()))
            {
                AlertDialog.Builder ab = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_DayNight_Dialog_Alert);
                ab.setTitle("未填写电话");
                ab.setIcon(R.drawable.ic_warning);
                ab.setMessage("我们发现你未填写电话，请检查！");
                ab.setNegativeButton("取消", null);
                ab.setPositiveButton("确认", null);
                ab.create().show();
            }
            else if ("".equals(password.getText().toString()))
            {
                AlertDialog.Builder ab = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_DayNight_Dialog_Alert);
                ab.setTitle("未填写密码");
                ab.setIcon(R.drawable.ic_warning);
                ab.setMessage("我们发现你未填写密码，请检查！");
                ab.setNegativeButton("取消", null);
                ab.setPositiveButton("确认", null);
                ab.create().show();
            }
            else if(!password.getText().toString().equals(repeatPassword.getText().toString()))
            {
                AlertDialog.Builder ab = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_DayNight_Dialog_Alert);
                ab.setTitle("不一致的密码");
                ab.setIcon(R.drawable.ic_warning);
                ab.setMessage("我们发现你填写的密码不匹配，请检查！");
                ab.setNegativeButton("取消", null);
                ab.setPositiveButton("确认", null);
                ab.create().show();
            }
            else{
                String userName= RandomStringUtils.random(6,true,true);
                ContentValues cv=new ContentValues();
                cv.put("user_name",userName);
                cv.put("user_phone",phone.getText().toString());
                cv.put("user_password_hash",password.getText().toString());
                cv.put("icon_head",(int)(Math.random()*5+1));
                cv.put("user_head","User");
                db.insert(TABLE_NAME_USER,null,cv);
                AlertDialog.Builder ab = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_DayNight_Dialog_Alert);
                ab.setTitle("注册成功");
                ab.setIcon(R.drawable.ic_ok);
                ab.setMessage("你的唯一身份码是"+userName+",你也可以选择手机号登录,你想现在就登陆吗？");
                ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RegisterActivity.this, "您没有选择登录", Toast.LENGTH_SHORT).show();
                    }
                });
                ab.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                    }
                });
                ab.create().show();
            }

        });
        
    }
}