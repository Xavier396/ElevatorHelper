package com.example.elevatorhelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elevatorhelper.Impl.DBMaker;
import com.example.elevatorhelper.databinding.ActivityLoginBinding;
import com.example.elevatorhelper.pojo.User;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.elevatorhelper.Constant.*;


/**
 * @author yhjzs
 */
public class LoginActivity extends AppCompatActivity {

    ImageView iconHead;
    Button login, reset;
    EditText username, password;
    RadioGroup userRole;
    private String userNameValue;
    private String passwordContent;
    private int whichUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding alb = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(alb.getRoot());
        //数据库初始化
        DBMaker dbMaker = new DBMaker(getApplicationContext(), DB_NAME, null, 1);
//        SQLiteDatabase db = dbMaker.getReadableDatabase();
        //获取SharedPreferences
        SharedPreferences sp = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        List<User> userList = new ArrayList<>();

        //view初始化
        username = alb.userName;
        password = alb.passwordArea;
        iconHead = alb.headIcon;
        userRole = alb.headSelection;
//先读数据，再进行比较
        Cursor c = dbMaker.getReadableDatabase().query("User", null, null, null, null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                User u = new User();
                u.setId(c.getInt(c.getColumnIndex("id")));
                u.setIconHead(c.getInt(c.getColumnIndex("icon_head")));
                u.setUserHead(c.getString(c.getColumnIndex("user_head")));
                u.setUserName(c.getString(c.getColumnIndex("user_name")));
                u.setUserPasswordHash(c.getString(c.getColumnIndex("user_password_hash")));
                u.setUserPhone(c.getString(c.getColumnIndex("user_phone")));
                userList.add(u);
                c.moveToNext();
            }
        }


        /*
         *
         * 小的骚操作，在这里输入账号，如果账号在库里存在
         * 头像就会更换
         *
         * */
        username.setOnFocusChangeListener((v, t) -> {
            userNameValue = username.getText().toString();
            for (User u :
                    userList) {
                if (u.getUserName().equals(userNameValue)||u.getUserPhone().equals(userNameValue))
                {
                    setIcon(u.getIconHead(),iconHead);
                    whichUser=userList.indexOf(u);
                }
            }
        });
        //重置按钮，重置表单
        reset = alb.reset;
        reset.setOnClickListener(v -> {
            username.setText("");
            password.setText("");
            userRole.clearCheck();
        });

//登陆界面，这里用了Google的验证码库
        login = alb.login;
        login.setOnClickListener(v -> {

            userNameValue = username.getText().toString();
            passwordContent = password.getText().toString();


            //表单验证
            if (userNameValue == null || "".equals(userNameValue.trim())) {
                AlertDialog.Builder ab = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_DayNight_Dialog_Alert);
                ab.setTitle("未填写用户名");
                ab.setIcon(R.drawable.ic_warning);
                ab.setMessage("我们发现你未填写用户名，请检查！");
                ab.setNegativeButton("取消", null);
                ab.setPositiveButton("确认", null);
                ab.create().show();
            } else if (passwordContent == null || "".equals(passwordContent.trim())) {
                AlertDialog.Builder ab = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_DayNight_Dialog_Alert);
                ab.setTitle("未填写密码");
                ab.setIcon(R.drawable.ic_warning);
                ab.setMessage("我们发现你未填写密码，请检查！");
                ab.setNegativeButton("取消", null);
                ab.setPositiveButton("确认", null);
                ab.create().show();
            } else if (userRole.getCheckedRadioButtonId() == -1) {
                AlertDialog.Builder ab = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_DayNight_Dialog_Alert);
                ab.setTitle("未选择登录角色");
                ab.setIcon(R.drawable.ic_warning);
                ab.setMessage("我们发现你未选择登陆角色，请检查！");
                ab.setNegativeButton("取消", null);
                ab.setPositiveButton("确认", null);
                ab.create().show();
            }
            else if (!passwordContent.equals(userList.get(whichUser).getUserPasswordHash()))
            {
                AlertDialog.Builder ab = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_DayNight_Dialog_Alert);
                ab.setTitle("密码错误");
                ab.setIcon(R.drawable.ic_warning);
                ab.setMessage("您输入的密码错误，请检查！");
                ab.setNegativeButton("取消", null);
                ab.setPositiveButton("确认", null);
                ab.create().show();
            }
            else if(userRole.getCheckedRadioButtonId()==R.id.is_admin&& !"Admin".equals(userList.get(whichUser).getUserHead())||
            userRole.getCheckedRadioButtonId()==R.id.is_user&&!"User".equals(userList.get(whichUser).getIconHead())
            )
            {
                AlertDialog.Builder ab = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_DayNight_Dialog_Alert);
                ab.setTitle("用户角色错误");
                ab.setIcon(R.drawable.ic_warning);
                ab.setMessage("您选择了错误的用户角色，请检查！");
                ab.setNegativeButton("取消", null);
                ab.setPositiveButton("确认", null);
                ab.create().show();
            }
            else {
                //Google reCaptcha
                SafetyNet.getClient(this).verifyWithRecaptcha(API_CODE)
                        .addOnSuccessListener(
                                new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                                    @Override
                                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                        // Indicates communication with reCAPTCHA service was
                                        // 通过验证之后的内容
                                        Toast.makeText(getApplicationContext(), "欢迎您"+userList.get(whichUser).getUserName(), Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),MenuActivity.class);
                                        //确认登录
                                        editor.putBoolean(SHARED_PREFERENCE_VALUE_IS_LOGIN_NOW,true);
                                        //现实的用户名
                                        editor.putString(SHARED_PREFERENCE_VALUE_LOGIN_NAME,userList.get(whichUser).getUserName());
                                        //是不是管理员
                                        editor.putBoolean(SHARED_PREFERENCE_VALUE_IS_ADMIN,("Admin".equals(userList.get(whichUser).getUserHead())));
                                        //头像是哪个
                                        editor.putInt(SHARED_PREFERENCE_VALUE_ICON_HEAD,userList.get(whichUser).getIconHead());
                                        editor.apply();
                                       startActivity(intent);
                                        String userResponseToken = response.getTokenResult();
                                        if (!userResponseToken.isEmpty()) {
                                            // Validate the user response token using the
                                            // reCAPTCHA siteverify API.

                                        }
                                    }
                                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    // An error occurred when communicating with the
                                    // reCAPTCHA service. Refer to the status code to
                                    // handle the error appropriately.
                                    ApiException apiException = (ApiException) e;
                                    int statusCode = apiException.getStatusCode();
                                    Log.d(TAG, "Error: " + CommonStatusCodes
                                            .getStatusCodeString(statusCode));
                                } else {
                                    // A different, unknown type of error occurred.
                                    Log.d(TAG, "Error: " + e.getMessage());
                                }
                            }
                        });

            }
        });
    }
    /**
     * @param image  要填充的ImageView
     * @param which  选择要填充的图形编号
     *
     * */
    private boolean setIcon(int which,ImageView image)
    {
        switch (which)
        {
            case 1:
                image.setImageResource(R.drawable.ic_boy_1);
                return  true;

            case 2:
                image.setImageResource(R.drawable.ic_boy_2);
                return  true;
            case 3:
                image.setImageResource(R.drawable.ic_boy_3);
                return  true;
            case 4:
                image.setImageResource(R.drawable.ic_girl_1);
                return  true;
            case 5:
                image.setImageResource(R.drawable.ic_girl_2);
                return  true;
            case 6:
                image.setImageResource(R.drawable.ic_girl_3);
                return  true;
            default:
                image.setImageResource(R.drawable.ic_basic_icon);
                return  false;
        }

    }
}

