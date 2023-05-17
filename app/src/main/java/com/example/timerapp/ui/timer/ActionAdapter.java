package com.example.timerapp.ui.timer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timerapp.R;
import com.example.timerapp.bean.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ViewHolder> {
    private List<String> list = Arrays.asList("学习","运动","休息","吃饭","睡觉");
    private List<Action> actions;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView action_text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            action_text = itemView.findViewById(R.id.action);
        }
    }
    public ActionAdapter (Context context,List<Action> actions){
        this.context = context;
        this.actions = actions;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Action action = actions.get(position);
        holder.action_text.setText(action.getActionname());
        holder.action_text.setBackgroundColor(Color.parseColor(action.getColor()));
        holder.action_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Click:"+action.getActionname(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }
}
