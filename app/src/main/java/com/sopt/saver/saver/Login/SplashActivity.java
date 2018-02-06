package com.sopt.saver.saver.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.sopt.saver.saver.MainViewPager.MainViewPagerActivity;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sf;
    private NetworkService service;
    LoginInfo loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        service = ApplicationController.getInstance().getNetworkService();
        ///////////////자동로그인체크////////////////////////
        sf = getSharedPreferences("login_data", MODE_PRIVATE);
        String id = sf.getString("id", "");
        String password = sf.getString("password", "");
        loginInfo = new LoginInfo(id, password);
        /////////////서버 통신////////////////
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                sf = getSharedPreferences("login_data", MODE_PRIVATE);
                String id = sf.getString("id", "");
                String password = sf.getString("password", "");
                LoginInfo loginInfo = new LoginInfo(id, password);
                Call<LoginResult> requestLogin = service.tryLogin(loginInfo);
                requestLogin.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                        try {
                            if (response.isSuccessful()) {
                                if (response.body().message.equals("ok")) {
                                    Intent auto_login = new Intent(SplashActivity.this, MainViewPagerActivity.class);
                                    auto_login.putExtra("user_num", String.valueOf(response.body().result.user_num));
                                    auto_login.putExtra("id", response.body().result.id);
                                    startActivity(auto_login);
                                    finish();
                                } else {
                                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                    SplashActivity.this.startActivity(intent);
                                    finish();
                                }
                            } else {
                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                SplashActivity.this.startActivity(intent);
                                finish();
                            }
                        } catch (NullPointerException e)
                        {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            SplashActivity.this.startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Intent fail = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(fail);
                    }
                });
            }
        }, 500);
    }
}
