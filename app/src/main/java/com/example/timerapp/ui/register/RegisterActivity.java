package com.example.timerapp.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.timerapp.R;
import com.example.timerapp.ui.login.LoginActivity;
import com.example.timerapp.ui.utils.IP;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FloatingActionButton fab = findViewById(R.id.fab_register);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void register() {
        EditText userName = findViewById(R.id.et_register_username);
        EditText password = findViewById(R.id.et_register_password);
        EditText age = findViewById(R.id.et_register_age);
        EditText sex = findViewById(R.id.et_register_sex);
        EditText tel = findViewById(R.id.et_register_tel);

        if (userName.getText().toString().equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(() -> {
                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                OkHttpClient httpClient = new OkHttpClient();
                jsonObject.put("username", userName.getText().toString());
                jsonObject.put("password", password.getText().toString());
                jsonObject.put("sex", sex.getText().toString());
                jsonObject.put("age", age.getText().toString());
                jsonObject.put("tel", tel.getText().toString());
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
                String url = "http://"+ IP.IP +"/api/user/register";
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();

                Call call = httpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d("register", "failed");
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String responseData = response.body().string();
                        JSONObject result = JSONObject.parseObject(responseData);
                        runOnUiThread(() -> {
                            if ((int)result.get("code")==200) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, result.get("msg").toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }).start();
        }
    }
}