package com.example.elevatorhelper.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.elevatorhelper.Impl.DBMaker;
import com.example.elevatorhelper.LoginActivity;
import com.example.elevatorhelper.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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
        Spinner spinner = root.findViewById(R.id.elevator_choice);
        EditText editText = root.findViewById(R.id.comment);
        Button confirm = root.findViewById(R.id.confirm_button);
        Button reset = root.findViewById(R.id.reset_button);
        spinner.setEnabled(false);
        editText.setFocusable(false);

        SharedPreferences preferences = root.getContext().getSharedPreferences("appConfig", MODE_PRIVATE);
        if (!preferences.getBoolean("islogin", false)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(root.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            alert.setTitle("您未登录！");
            alert.setMessage("请您先登录在进行操作！");
            alert.setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i=new Intent(root.getContext(), LoginActivity.class);
                    startActivity(i);
                }
            });
            alert.setNegativeButton("取消", null);
            alert.create().show();
        }
        confirm.setOnClickListener(v -> {
            if (!preferences.getBoolean("islogin", false)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(root.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                alert.setTitle("您未登录！");
                alert.setMessage("请您先登录在进行操作！");
                alert.setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i=new Intent(root.getContext(), LoginActivity.class);
                        startActivity(i);

                    }
                });
                alert.setNegativeButton("取消", null);
                alert.create().show();
            }
        });

        reset.setOnClickListener(v -> {
            if (!preferences.getBoolean("islogin", false)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(root.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                alert.setTitle("您未登录！");
                alert.setMessage("请您先登录在进行操作！");
                alert.setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i=new Intent(root.getContext(), LoginActivity.class);
                        startActivity(i);

                    }
                });
                alert.setNegativeButton("取消", null);
                alert.create().show();
            }
        });

        List<String> listOfString = new ArrayList<>();
        /*从数据库中读数据*/
        DBMaker dbMaker = new DBMaker(root.getContext(), "appData.db", null, 1);
        Cursor cursor = dbMaker.getReadableDatabase().query("Elevator", null, "elevator_status=0", null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String c = new String();

                c = (cursor.getString(cursor.getColumnIndex("elevator_number")));

                listOfString.add(c);
                cursor.moveToNext();//指针移动到下一条
            }
        }

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