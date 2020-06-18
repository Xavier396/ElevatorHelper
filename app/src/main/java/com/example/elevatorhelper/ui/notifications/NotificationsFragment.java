package com.example.elevatorhelper.ui.notifications;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.elevatorhelper.MenuActivity;
import com.example.elevatorhelper.R;

import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_NAME;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_VALUE_ICON_HEAD;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_VALUE_IS_ADMIN;
import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_VALUE_LOGIN_NAME;

/**
 * @author yanghaijia
 */
public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    LinearLayout tr0;
    TableRow tr1,tr2,tr3;
    ImageView icon;
    SharedPreferences sp;
    TextView userName;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);


        sp=root.getContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor=sp.edit();
        tr0=root.findViewById(R.id.more_page_row0);
        tr1=root.findViewById(R.id.more_page_row1);
        tr2=root.findViewById(R.id.more_page_row2);
        tr3=root.findViewById(R.id.more_page_row3);
        userName=root.findViewById(R.id.user_name);
        icon=root.findViewById(R.id.user_icon);

        userName.setText(sp.getString(SHARED_PREFERENCE_VALUE_LOGIN_NAME,"未登录"));
        setIcon(sp.getInt(SHARED_PREFERENCE_VALUE_ICON_HEAD,0),icon);

        tr2.setOnClickListener(v->{
            AlertDialog.Builder ab = new AlertDialog.Builder(root.getContext(), R.style.Theme_MaterialComponents_DayNight_Dialog_Alert);
            ab.setTitle("退出");
            ab.setIcon(R.drawable.ic_warning);
            ab.setMessage("您真的要退出吗？");
            ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            ab.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.clear();
                    editor.apply();
                    Intent i=new Intent(root.getContext(),MenuActivity.class);
                    startActivity(i);
                }
            });
            ab.create().show();
        });



        tr3.setVisibility(sp.getBoolean(SHARED_PREFERENCE_VALUE_IS_ADMIN,false)==true?View.VISIBLE:View.INVISIBLE);

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
    private boolean setIcon(int which,ImageView image)
    {
        switch (which)
        {
            case 1:
                image.setImageResource(R.drawable.ic_boy_1_64);
                return  true;

            case 2:
                image.setImageResource(R.drawable.ic_boy_2_64);
                return  true;
            case 3:
                image.setImageResource(R.drawable.ic_boy_3_64);
                return  true;
            case 4:
                image.setImageResource(R.drawable.ic_girl_1_64);
                return  true;
            case 5:
                image.setImageResource(R.drawable.ic_girl_2_64);
                return  true;
            case 6:
                image.setImageResource(R.drawable.ic_girl_3_64);
                return  true;
            default:
                image.setImageResource(R.drawable.ic_basic_icon_64);
                return  false;
        }

    }
}