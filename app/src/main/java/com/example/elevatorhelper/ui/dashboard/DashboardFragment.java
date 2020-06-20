package com.example.elevatorhelper.ui.dashboard;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.elevatorhelper.Impl.DBMaker;
import com.example.elevatorhelper.LoginActivity;
import com.example.elevatorhelper.R;
import com.example.elevatorhelper.pojo.Issue;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.elevatorhelper.Constant.DB_NAME;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_NAME;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_VALUE_IS_LOGIN_NOW;

/**
 * @author yanghaijia
 */
public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        DBMaker dbMaker=new DBMaker(root.getContext(),DB_NAME,null,1);
        SQLiteDatabase db=dbMaker.getWritableDatabase();
        SharedPreferences preferences = root.getContext().getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        Spinner spinner = root.findViewById(R.id.elevator_choice);
        EditText editText = root.findViewById(R.id.comment);
        Button confirm = root.findViewById(R.id.confirm_button);
        Button reset = root.findViewById(R.id.reset_button);
        spinner.setEnabled(preferences.getBoolean(SHARED_PREFERENCE_VALUE_IS_LOGIN_NOW,false));
        editText.setFocusable(preferences.getBoolean(SHARED_PREFERENCE_VALUE_IS_LOGIN_NOW,false));


        if (!preferences.getBoolean(SHARED_PREFERENCE_VALUE_IS_LOGIN_NOW, false)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(root.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            alert.setTitle(R.string.not_login);
            alert.setMessage(R.string.please_login_first);
            alert.setPositiveButton(R.string.go_login, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i=new Intent(root.getContext(), LoginActivity.class);
                    startActivity(i);
                }
            });
            alert.setNegativeButton(R.string.cancel, null);
            alert.create().show();
        }
        confirm.setOnClickListener(v -> {
            if (!preferences.getBoolean(SHARED_PREFERENCE_VALUE_IS_LOGIN_NOW, false)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(root.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                alert.setTitle(R.string.not_login);
                alert.setMessage(R.string.please_login_first);
                alert.setPositiveButton(R.string.go_login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i=new Intent(root.getContext(), LoginActivity.class);
                        startActivity(i);

                    }


                });
                alert.setNegativeButton(R.string.cancel, null);
                alert.create().show();
            }
            else if ("".equals(editText.getText().toString()))
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(root.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                alert.setTitle("未填写的字段！");
                alert.setMessage("还有未填写的字段，请检查！");
                alert.setPositiveButton("确定", null);
                alert.setNegativeButton(R.string.cancel, null);
                alert.create().show();
            }
            else{
                Issue newIssue=new Issue();
                //获取当前时间
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss");// HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                newIssue.setReportTime(simpleDateFormat.format(date));
                newIssue.setElevatorIssue(editText.getText().toString());
                newIssue.setElevatorLocation(spinner.getSelectedItem().toString());
                ContentValues cv=new ContentValues();
                cv.put("elevator_location",newIssue.getElevatorLocation());
                cv.put("elevator_issue",newIssue.getElevatorIssue());
                cv.put("elevator_time",newIssue.getReportTime());
                db.insert("Issue",null,cv);
                Toast.makeText(root.getContext(), "提交成功", Toast.LENGTH_SHORT).show();
                editText.setText("");
                spinner.setSelection(1);
            }
        });

        reset.setOnClickListener(v -> {
            if (!preferences.getBoolean(SHARED_PREFERENCE_VALUE_IS_LOGIN_NOW, false)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(root.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                alert.setTitle(R.string.not_login);
                alert.setMessage(R.string.please_login_first);
                alert.setPositiveButton(R.string.go_login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i=new Intent(root.getContext(), LoginActivity.class);
                        startActivity(i);

                    }
                });
                alert.setNegativeButton(R.string.cancel, null);
                alert.create().show();
            }
            else{
                editText.setText("");
                spinner.setSelection(1);
            }
        });

        List<String> listOfString = new ArrayList<>();
        /*从数据库中读数据*/

        Cursor cursor = dbMaker.getReadableDatabase().query("Elevator", null, "", null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String c;
                c = (cursor.getString(cursor.getColumnIndex("elevator_number")));

                listOfString.add(c);
                cursor.moveToNext();//指针移动到下一条
            }
        }
        cursor.close();
        

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_expandable_list_item_1, listOfString);
        spinner.setAdapter(adapter);


        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}