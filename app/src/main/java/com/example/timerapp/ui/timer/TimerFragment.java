package com.example.timerapp.ui.timer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timerapp.R;
import com.example.timerapp.bean.Action;
import com.example.timerapp.bean.Activity;
import com.example.timerapp.databinding.FragmentTimerBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TimerFragment extends Fragment implements OnDateSelectedListener {

    private FragmentTimerBinding binding;
    private TextView date_text,time;
    private GridView gridView;
    private int initialPosition;
    private TimeAdapter timeAdapter;
    private boolean isScrolling = false;
    private SeekBar seekBar;
    private MaterialCalendarView materialCalendarView;
    private RecyclerView recyclerView;
    private ArrayList<Activity> activityList;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TimerViewModel timerViewModel =
                new ViewModelProvider(this).get(TimerViewModel.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId",-1);
        binding = FragmentTimerBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        date_text = binding.TextDate;
        time = binding.timeText;
        materialCalendarView = binding.calendarView;
        recyclerView = binding.actionRecyclerView;
        timerViewModel.getText().observe(getViewLifecycleOwner(),day -> {
            this.date_text.setText(day);
        });
        timerViewModel.getActions(userId).observe(getViewLifecycleOwner(), this::showRecyclerView);
        timerViewModel.getActivitys(userId).observe(getViewLifecycleOwner(),activities -> {
            this.activityList = activities;
        });
        gridView = binding.timeGridView;
        seekBar = binding.minSeekBar;
        initSeekBar();
        initMcv();
        showGridView(activityList);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                boolean flag = false;
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        initialPosition = gridView.pointToPosition((int) event.getX(), (int) event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        flag = true;
                        int currentPosition = gridView.pointToPosition((int) event.getX(), (int) event.getY());
                        if (currentPosition != initialPosition) {
                            isScrolling = true;
                            timeAdapter.changeSelection(initialPosition, currentPosition);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isScrolling) {
                            int clickedPosition = gridView.pointToPosition((int) event.getX(), (int) event.getY());
                            timeAdapter.toggleSelection(clickedPosition);

                        }
                        if (!flag){
                            timeAdapter.init();
                        }
                        isScrolling = false;
                        break;
                }
                return true;
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showRecyclerView(ArrayList<Action> actions) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ActionAdapter actionAdapter = new ActionAdapter(getContext(),actions);
        recyclerView.setAdapter(actionAdapter);
    }

    public void showGridView(ArrayList<Activity> activities){
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int t = Integer.parseInt(time.getText().toString());
        gridView.setNumColumns((60 / t + 1));
        gridView.setColumnWidth(screenWidth/(60 / t + 1));
        timeAdapter = new TimeAdapter(t,screenWidth,activities);
        gridView.setAdapter(timeAdapter);
    }

    private void initSeekBar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                switch (seekBar.getProgress()){
                    case 0:
                        time.setText("1");
                        showGridView(activityList);
                        break;
                    case 1:
                        time.setText("2");
                        showGridView(activityList);
                        break;
                    case 2:
                        time.setText("3");
                        showGridView(activityList);
                        break;
                    case 3:
                        time.setText("5");
                        showGridView(activityList);
                        break;
                    case 4:
                        time.setText("6");
                        showGridView(activityList);
                        break;
                    case 5:
                        time.setText("10");
                        showGridView(activityList);
                        break;
                    case 6:
                        time.setText("12");
                        showGridView(activityList);
                        break;
                    case 7:
                        time.setText("15");
                        showGridView(activityList);
                        break;
                    case 8:
                        time.setText("20");
                        showGridView(activityList);
                        break;
                    case 9:
                        time.setText("30");
                        showGridView(activityList);
                        break;
                    case 10:
                        time.setText("60");
                        showGridView(activityList);
                        break;
                }
            }
        });
    }

    public void initMcv(){
        materialCalendarView.state().edit().setFirstDayOfWeek(Calendar.SUNDAY).setCalendarDisplayMode(CalendarMode.WEEKS).commit();
        materialCalendarView.setSelectedDate(new Date());
        materialCalendarView.setSelectionColor(R.color.green);
        materialCalendarView.setOnDateChangedListener(this);
    }

    /**
     * 日期选择 回调函数
     * @param widget   the view associated with this listener
     * @param date     the date that was selected or unselected
     * @param selected true if the day is now selected, false otherwise
     */
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Date date1 = materialCalendarView.getSelectedDate().getDate();
        date_text.setText(new SimpleDateFormat("MM月dd日").format(date1));
    }
}