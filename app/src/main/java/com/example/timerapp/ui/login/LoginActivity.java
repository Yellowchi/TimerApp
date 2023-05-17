package com.example.timerapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timerapp.MainActivity;
import com.example.timerapp.R;
import com.example.timerapp.bean.User;
import com.example.timerapp.ui.register.RegisterActivity;
import com.example.timerapp.ui.utils.IP;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FloatingActionButton fab = findViewById(R.id.fab_login);
        fab.setOnClickListener(view -> {
            login();
        });

        Button btn = findViewById(R.id.btn_register);
        btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    public void login() {
        EditText userName = findViewById(R.id.et_login_user_name);
        EditText password = findViewById(R.id.et_login_password);

        new Thread(() -> {
            MediaType JSON = MediaType.parse("application/json;charset=utf-8");
            JSONObject jsonObject = new JSONObject();
            OkHttpClient httpClient = new OkHttpClient();
            jsonObject.put("username", userName.getText().toString());
            jsonObject.put("password", password.getText().toString());
            RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
            String url = "http://"+ IP.IP +"/api/user/login";
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Call call = httpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("login", "failed");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseData = response.body().string();
                    JSONObject jsonObject1 = JSONObject.parseObject(responseData);
                    runOnUiThread(() -> {
                        if ((int)jsonObject1.get("code")==200) {
                            JSONObject data = (JSONObject) jsonObject1.get("data");
                            String token = (String) data.get("token");
                            int userId = (int) data.get("userId");
//                            本地存储登录信息
                            SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                            editor.putBoolean("isOnline", true);
                            editor.putInt("userId", userId);
                            editor.putString("token",token);
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, jsonObject1.get("msg").toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }).start();
    }
}
