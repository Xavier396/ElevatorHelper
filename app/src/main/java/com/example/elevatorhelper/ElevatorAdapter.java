package com.example.elevatorhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
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
        ((MyViewHolder)holder).title.setText(source.get(position).getElevatorNumber());
        switch (source.get(position).getStatus()){
            case 0:
                ((MyViewHolder)holder).icon.setImageResource(R.drawable.ic_ok);
                break;
            case 1:
                ((MyViewHolder)holder).icon.setImageResource(R.drawable.ic_error);
                break;
            case 2:
                ((MyViewHolder)holder).icon.setImageResource(R.drawable.ic_contributing);
                break;
            case 3:
                ((MyViewHolder)holder).icon.setImageResource(R.drawable.ic_question);
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return source.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView icon;

        public MyViewHolder(View itemView, final OnItemClickListener onClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.elevator_title);
//            source = itemView.findViewById(R.id.source);//FIXME：
            icon = itemView.findViewById(R.id.status_icon);
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
