package com.example.elevatorhelper.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.elevatorhelper.R;

import static com.example.elevatorhelper.Constant.SHARED_PREFERENCE_NAME;

/**
 * @author yanghaijia
 */
public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    TableRow tr0,tr1,tr2,tr3;
    SharedPreferences sp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);


        sp=root.getContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        tr0=root.findViewById(R.id.more_page_row0);
        tr1=root.findViewById(R.id.more_page_row1);
        tr2=root.findViewById(R.id.more_page_row2);
        tr3=root.findViewById(R.id.more_page_row3);

        tr3.setVisibility(sp.getBoolean("isAdmin",false)==true?View.VISIBLE:View.INVISIBLE);

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}