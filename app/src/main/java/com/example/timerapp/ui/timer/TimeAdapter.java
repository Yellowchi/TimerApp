package com.example.timerapp.ui.timer;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timerapp.R;
import com.example.timerapp.bean.Action;
import com.example.timerapp.bean.Activity;

import java.util.Date;
import java.util.List;

public class TimeAdapter extends BaseAdapter {
    private SparseBooleanArray selectedItems;
    private List<Activity> activities;

    private int num;

    private int screenWidth;

    public TimeAdapter(int time, int screenWidth, List<Activity> activities) {
        selectedItems = new SparseBooleanArray();
        num = 60/time + 1;
        this.screenWidth = screenWidth;
        this.activities = activities;
    }

    static class ViewHolder{
        TextView textView;
    }

    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyDataSetChanged();
    }

    public void changeSelection(int startPosition, int endPosition) {
        selectedItems.clear();
        int min = Math.min(startPosition, endPosition);
        int max = Math.max(startPosition, endPosition);
        for (int i = min; i <= max; i++) {
            selectedItems.put(i, true);
        }
        notifyDataSetChanged();
    }

    public void init(){
        selectedItems.clear();
    }

    @Override
    public int getCount() {
        return num*24;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (parent.getChildCount()%num == 0){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hour,parent,false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.hour);
            convertView.setTag(holder);
            holder.textView.setText(String.valueOf(parent.getChildCount()/num));
        }else {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_min,parent,false);
            convertView.setTag(holder);
        }
        if (selectedItems.get(position) == true && position%num != 0){
            convertView.setBackgroundResource(R.color.teal_200);
        }
        if (position%num != 0){
            convertView.setLayoutParams(new ViewGroup.LayoutParams((screenWidth-40)/(num-1),80));
        }
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for (Activity activity : activities){
            Date start = activity.getStart();
            
        }
    }
}
