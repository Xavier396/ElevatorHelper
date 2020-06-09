package com.example.elevatorhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elevatorhelper.pojo.Elevator;

import java.util.List;

/**
 * @author yanghaijia
 */
public class ElevatorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Elevator> source;
    private Context context;

    public ElevatorAdapter(List<Elevator> source, Context context) {
        this.source = source;
        this.context = context;
    }
    private OnItemClickListener onClickListener=null;

    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    //回调接口
    public interface OnItemClickListener{
        void onItemClick(View v,int position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        RecyclerView.ViewHolder holder=null;
        holder=new MyViewHolder(v,onClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //TODO:绑定页面
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, time, source;

        public MyViewHolder(View itemView, final OnItemClickListener onClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
//            source = itemView.findViewById(R.id.source);//FIXME：
            time = itemView.findViewById(R.id.time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        //确保position值有效
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.onItemClick(view, position);
                        }
                    }
                }
            });
        }
    }
}
