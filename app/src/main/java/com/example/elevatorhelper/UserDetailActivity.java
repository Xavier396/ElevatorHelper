package com.example.elevatorhelper;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elevatorhelper.Impl.DBMaker;
import com.example.elevatorhelper.databinding.ActivityUserDetailBinding;
import com.example.elevatorhelper.pojo.User;

import static com.example.elevatorhelper.Constant.DB_NAME;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_NAME;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_VALUE_FIRST_ADD;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_VALUE_ICON_HEAD;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_VALUE_LOGIN_NAME;
import static com.example.elevatorhelper.Constant.TABLE_NAME_USER;
import static com.example.elevatorhelper.Constant.TABLE_NAME_USER_ICON_HEAD;
import static com.example.elevatorhelper.Constant.TABLE_NAME_USER_USER_NAME;
import static com.example.elevatorhelper.Constant.TABLE_NAME_USER_USER_PASSWORD_HASH;
import static com.example.elevatorhelper.Constant.TABLE_NAME_USER_USER_PHONE;
import static com.example.elevatorhelper.Constant.TABLE_PUBLIC_ID;

public class UserDetailActivity extends AppCompatActivity {
    RelativeLayout icon, name, password, telephone;
    ImageView iconMain;
    TextView userName, userPassword, userPhone;
    CheckBox icon1, icon2, icon3, icon4, icon5, icon6;
    LinearLayout changeHead,changePassword,changePhone;
    int flag0,flag1,flag2;
    Button saveIcon,savePhone,savePassword;

    EditText oldPassword,newPassword,repeatNewPassword,oldPhone,newPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUserDetailBinding aud = ActivityUserDetailBinding.inflate(getLayoutInflater());
        setContentView(aud.getRoot());
        SharedPreferences sp = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        DBMaker dbMaker=new DBMaker(getApplicationContext(),DB_NAME,null,1);
        SQLiteDatabase db=dbMaker.getWritableDatabase();
        icon = aud.iconSetter;
        name = aud.userNameSetter;
        password = aud.passwordSetter;
        telephone = aud.telephoneSetter;
        User u=new User();
        Cursor c=db.query(TABLE_NAME_USER,null,"user_name=?",new String[]{sp.getString(SHARED_PREFERENCE_VALUE_LOGIN_NAME,null)},null,null,null,null);
        if (c.getCount()>0)
        {
            c.moveToFirst();
            for (int i=0;i<c.getCount();i++)
            {
                u.setId(c.getInt(c.getColumnIndex(TABLE_PUBLIC_ID)));
                u.setIconHead(c.getInt(c.getColumnIndex(TABLE_NAME_USER_ICON_HEAD)));
                u.setUserPasswordHash(c.getString(c.getColumnIndex(TABLE_NAME_USER_USER_PASSWORD_HASH)));
                u.setUserPhone(c.getString(c.getColumnIndex(TABLE_NAME_USER_USER_PHONE)));
                u.setUserName(c.getString(c.getColumnIndex(TABLE_NAME_USER_USER_NAME)));
                u.setUserHead(c.getString(c.getColumnIndex(TABLE_NAME_USER_ICON_HEAD)));
                c.moveToNext();
            }
        }


        iconMain = aud.iconSelf;
        userName = aud.userName;
        userPassword = aud.userPassword;
        userPhone = aud.userPhone;

        changeHead=aud.changeHead;
        savePassword=aud.submitPasswordChange;
        savePhone=aud.submitPhoneChange;

        icon1=aud.icon1;
        icon2=aud.icon2;
        icon3=aud.icon3;
        icon4=aud.icon4;
        icon5=aud.icon5;
        icon6=aud.icon6;

        oldPassword=aud.oldPassword;
        newPassword=aud.newPassword;
        repeatNewPassword=aud.repeatNewPassword;
        oldPhone=aud.oldPhone;
        newPhone=aud.newPhone;

        saveIcon=aud.comfirmIconButton;
        changePassword=aud.changePassword;
        changePhone=aud.changePhone;

        saveIcon.setOnClickListener(v->{
            if (!(icon1.isChecked()||icon2.isChecked()||icon3.isChecked()||icon4.isChecked()||icon5.isChecked()||icon6.isChecked()))
            {
                Toast.makeText(this, "没有选择的头像，请先选择", Toast.LENGTH_SHORT).show();
            }
            else {
                if (icon1.isChecked())
                {
                    editor.putInt(SHARED_PREFERENCE_VALUE_ICON_HEAD,1);
                    editor.apply();

                    ContentValues cv=new ContentValues();
                    cv.put(TABLE_NAME_USER_ICON_HEAD,1);
                    setIcon(1,iconMain);
                    db.update(TABLE_NAME_USER,cv,"user_name=?",new String[]{sp.getString(SHARED_PREFERENCE_NAME,null)});
                }
                else if(icon2.isChecked())
                {

                    editor.putInt(SHARED_PREFERENCE_VALUE_ICON_HEAD,2);
                    editor.apply();
                    ContentValues cv=new ContentValues();
                    cv.put(TABLE_NAME_USER_ICON_HEAD,2);
                    db.update(TABLE_NAME_USER,cv,"user_name=?",new String[]{sp.getString(SHARED_PREFERENCE_NAME,null)});
                    setIcon(2,iconMain);
                }
                else if(icon3.isChecked())
                {
                    editor.putInt(SHARED_PREFERENCE_VALUE_ICON_HEAD,3);
                    editor.apply();
                    ContentValues cv=new ContentValues();
                    cv.put(TABLE_NAME_USER_ICON_HEAD,3);
                    db.update(TABLE_NAME_USER,cv,"user_name=?",new String[]{sp.getString(SHARED_PREFERENCE_NAME,null)});
                    setIcon(3,iconMain);
                }
                else if(icon4.isChecked())
                {
                    editor.putInt(SHARED_PREFERENCE_VALUE_ICON_HEAD,4);
                    editor.apply();
                    ContentValues cv=new ContentValues();
                    cv.put(TABLE_NAME_USER_ICON_HEAD,4);
                    db.update(TABLE_NAME_USER,cv,"user_name=?",new String[]{sp.getString(SHARED_PREFERENCE_NAME,null)});
                    setIcon(4,iconMain);
                }
                else if(icon5.isChecked())
                {
                    editor.putInt(SHARED_PREFERENCE_VALUE_ICON_HEAD,5);
                    editor.apply();
                    ContentValues cv=new ContentValues();
                    cv.put(TABLE_NAME_USER_ICON_HEAD,5);
                    db.update(TABLE_NAME_USER,cv,"user_name=?",new String[]{sp.getString(SHARED_PREFERENCE_NAME,null)});
                    setIcon(5,iconMain);
                }
                else if(icon6.isChecked())
                {
                    editor.putInt(SHARED_PREFERENCE_VALUE_ICON_HEAD,6);
                    editor.apply();
                    ContentValues cv=new ContentValues();
                    cv.put(TABLE_NAME_USER_ICON_HEAD,6);
                    db.update(TABLE_NAME_USER,cv,"user_name=?",new String[]{sp.getString(SHARED_PREFERENCE_NAME,null)});
                    setIcon(6,iconMain);
                }
                Toast.makeText(this, "头像已经更改", Toast.LENGTH_SHORT).show();
            }
        });
        savePassword.setOnClickListener(v->{
            if (oldPassword.getText().toString().trim().length()==0||oldPassword.getText().toString().trim().isEmpty()|| "".equals(oldPassword.getText().toString()))
            {
                Toast.makeText(this, "您未输入旧密码", Toast.LENGTH_SHORT).show();
            }
            else if(!oldPassword.getText().toString().equals(u.getUserPasswordHash()))
            {
                Toast.makeText(this, "您输入的密码有误:"+u.getUserPasswordHash()+"xx"+oldPassword.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            else if (newPassword.getText().toString().trim().length()==0||newPassword.getText().toString().trim().isEmpty()|| "".equals(newPassword.getText().toString()))
            {
                Toast.makeText(this, "您未输入新密码", Toast.LENGTH_SHORT).show();
            }
            else if (oldPassword.getText().toString().equals(newPassword.getText().toString())){
                Toast.makeText(this, "没有做修改", Toast.LENGTH_SHORT).show();
            }
            else if (!newPassword.getText().toString().equals(repeatNewPassword.getText().toString()))
            {
                Toast.makeText(this, "两次密码输入不匹配", Toast.LENGTH_SHORT).show();
            }
            else if (newPassword.getText().toString().length()>16||newPassword.getText().toString().length()<6)
            {
                Toast.makeText(this, "密码长度不正确，请保持在8-16位", Toast.LENGTH_SHORT).show();
            }
            else {
                ContentValues cv=new ContentValues();
                cv.put(TABLE_NAME_USER_USER_PASSWORD_HASH,newPassword.getText().toString());
                db.update(TABLE_NAME_USER,cv,"user_name=?",new String[]{SHARED_PREFERENCE_VALUE_LOGIN_NAME});
                Toast.makeText(this, "密码已修改，将重新登陆", Toast.LENGTH_SHORT).show();
                editor.clear();
                editor.putBoolean(SHARED_PREFERENCE_VALUE_FIRST_ADD,false);
                editor.apply();
                Intent i=new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(i);
            }

        });


        icon1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    icon2.setEnabled(false);
                    icon3.setEnabled(false);
                    icon4.setEnabled(false);
                    icon5.setEnabled(false);
                    icon6.setEnabled(false);
                }
                else if (!isChecked)
                {
                    icon2.setEnabled(true);
                    icon3.setEnabled(true);
                    icon4.setEnabled(true);
                    icon5.setEnabled(true);
                    icon6.setEnabled(true);
                }
            }
        });

        icon2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    icon1.setEnabled(false);
                    icon3.setEnabled(false);
                    icon4.setEnabled(false);
                    icon5.setEnabled(false);
                    icon6.setEnabled(false);
                }
                else if (!isChecked)
                {
                    icon1.setEnabled(true);
                    icon3.setEnabled(true);
                    icon4.setEnabled(true);
                    icon5.setEnabled(true);
                    icon6.setEnabled(true);
                }
            }
        });

        icon3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    icon2.setEnabled(false);
                    icon1.setEnabled(false);
                    icon4.setEnabled(false);
                    icon5.setEnabled(false);
                    icon6.setEnabled(false);
                }
                else if (!isChecked)
                {
                    icon2.setEnabled(true);
                    icon1.setEnabled(true);
                    icon4.setEnabled(true);
                    icon5.setEnabled(true);
                    icon6.setEnabled(true);
                }
            }
        });

        icon4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    icon2.setEnabled(false);
                    icon3.setEnabled(false);
                    icon1.setEnabled(false);
                    icon5.setEnabled(false);
                    icon6.setEnabled(false);
                }
                else if (!isChecked)
                {
                    icon2.setEnabled(true);
                    icon3.setEnabled(true);
                    icon1.setEnabled(true);
                    icon5.setEnabled(true);
                    icon6.setEnabled(true);
                }
            }
        });

        icon5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    icon2.setEnabled(false);
                    icon3.setEnabled(false);
                    icon4.setEnabled(false);
                    icon1.setEnabled(false);
                    icon6.setEnabled(false);
                }
                else if (!isChecked)
                {
                    icon2.setEnabled(true);
                    icon3.setEnabled(true);
                    icon4.setEnabled(true);
                    icon1.setEnabled(true);
                    icon6.setEnabled(true);
                }
            }
        });

        icon6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    icon2.setEnabled(false);
                    icon3.setEnabled(false);
                    icon4.setEnabled(false);
                    icon5.setEnabled(false);
                    icon1.setEnabled(false);
                }
                else if (!isChecked)
                {
                    icon2.setEnabled(true);
                    icon3.setEnabled(true);
                    icon4.setEnabled(true);
                    icon5.setEnabled(true);
                    icon1.setEnabled(true);
                }
            }
        });





        setIcon(sp.getInt(SHARED_PREFERENCE_VALUE_ICON_HEAD, 0), iconMain);
        userName.setText(sp.getString(SHARED_PREFERENCE_VALUE_LOGIN_NAME, "未登录"));


        icon.setOnClickListener(v -> {
            if (flag0%2==0)
            {
                changeHead.setVisibility(View.VISIBLE);
                flag0++;
            }
            else {
                changeHead.setVisibility(View.GONE);
                flag0++;
            }

        });
        password.setOnClickListener(v -> {

            if (flag1%2==0)
            {
                changePassword.setVisibility(View.VISIBLE);
                flag1++;
            }
            else {
                changePassword.setVisibility(View.GONE);
                flag1++;
            }
        });
        telephone.setOnClickListener(v -> {

            if (flag2%2==0)
            {
                changePhone.setVisibility(View.VISIBLE);
                flag2++;
            }
            else {
                changePhone.setVisibility(View.GONE);
                flag2++;
            }
        });




    }

    private boolean setIcon(int which, ImageView image) {
        switch (which) {
            case 1:
                image.setImageResource(R.drawable.ic_boy_1);
                return true;

            case 2:
                image.setImageResource(R.drawable.ic_boy_2);
                return true;
            case 3:
                image.setImageResource(R.drawable.ic_boy_3);
                return true;
            case 4:
                image.setImageResource(R.drawable.ic_girl_1);
                return true;
            case 5:
                image.setImageResource(R.drawable.ic_girl_2);
                return true;
            case 6:
                image.setImageResource(R.drawable.ic_girl_3);
                return true;
            default:
                image.setImageResource(R.drawable.ic_basic_icon);
                return false;
        }

    }
}