package com.example.timerapp.ui.timer;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.example.timerapp.bean.Action;
import com.example.timerapp.bean.Activity;
import com.example.timerapp.ui.utils.IP;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TimerViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<Action>> mutableLiveData_action;
    private MutableLiveData<ArrayList<Activity>> mutableLiveData_activity;

    public TimerViewModel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        mText = new MutableLiveData<>();
        mText.setValue(simpleDateFormat.format(new Date()));
        mutableLiveData_action = new MutableLiveData<>();
        mutableLiveData_activity = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
    public MutableLiveData<ArrayList<Action>> getActions(int userId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                String url = "http://"+IP.IP+"/api/action/initactions?userId="+userId;
                Request request = new Request.Builder().url(url).get().build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.d("INFO",JSONObject.parseObject(result).toString());
                        Object data = JSONObject.parseObject(result).get("list");
                        Gson gson = new Gson();
                        ArrayList<Action> actions = gson.fromJson(data.toString(),new TypeToken<ArrayList<Action>>(){}.getType());
                        mutableLiveData_action.postValue(actions);
                    }
                });
            }
        }).start();
        return mutableLiveData_action;
    }
    public MutableLiveData<ArrayList<Activity>> getActivitys(int userId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                String url = "http://"+IP.IP+"/api/action/initactivities?userId="+userId;
                Request request = new Request.Builder().url(url).get().build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.d("INFO",JSONObject.parseObject(result).toString());
                        Object data = JSONObject.parseObject(result).get("list");
                        if (data != null){
                            Gson gson = new Gson();
                            ArrayList<Activity> activities = gson.fromJson(data.toString(),new TypeToken<ArrayList<Activity>>(){}.getType());
                            mutableLiveData_activity.postValue(activities);
                        }
                    }
                });
            }
        }).start();
        return mutableLiveData_activity;
    }
}