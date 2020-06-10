package com.example.elevatorhelper.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elevatorhelper.ElevatorAdapter;
import com.example.elevatorhelper.ElevatorInfoActivity;
import com.example.elevatorhelper.Impl.DBMaker;
import com.example.elevatorhelper.R;
import com.example.elevatorhelper.pojo.Elevator;

import java.util.ArrayList;

/**
 * @author yanghaijia
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ArrayList<Elevator> eList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*对首页电梯信息的RecycleView 进行配置*/
        RecyclerView rec = root.findViewById(R.id.all_list);
        RecyclerView.LayoutManager rec_layout=new LinearLayoutManager(root.getContext());
        rec.setLayoutManager(rec_layout);
        ((LinearLayoutManager)rec_layout).setOrientation(RecyclerView.VERTICAL);
        rec.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divide =new DividerItemDecoration(root.getContext(),DividerItemDecoration.VERTICAL);
        rec.addItemDecoration(divide);
        rec.setHasFixedSize(true);

        /*往列表之中填写数据*/
        eList=new ArrayList<>();
        DBMaker dbMaker=new DBMaker(root.getContext(),"appData.db",null,1);
        // 获取只读数据库
        Cursor cursor = dbMaker.getReadableDatabase().query("Elevator",null,null,null,null,null,null);
        if (cursor.getCount()>0)
        {
            cursor.moveToFirst();
            for (int i=0;i<cursor.getCount();i++)
            {
                Elevator e=new Elevator();
                e.setId(cursor.getInt(cursor.getColumnIndex("id")));
                e.setStatus(cursor.getInt(cursor.getColumnIndex("elevator_status")));
                e.setElevatorNumber(cursor.getString(cursor.getColumnIndex("elevator_number")));
                e.setComment(cursor.getString(cursor.getColumnIndex("elevator_comment")));
                eList.add(e);
                cursor.moveToNext();//指针移动到下一条
            }
        }
        ElevatorAdapter adapter=new ElevatorAdapter(eList,root.getContext());
        rec.setAdapter(adapter);
        adapter.setOnItemClickListener((v,po)->{
            po+=1;
            Intent i=new Intent(root.getContext(), ElevatorInfoActivity.class);
            i.putExtra("position",po);
            i.putExtra("title",eList.get(po-1).getElevatorNumber());
            i.putExtra("status",eList.get(po-1).getStatus());
            i.putExtra("comment",eList.get(po-1).getComment());
            startActivity(i);
        });



        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}