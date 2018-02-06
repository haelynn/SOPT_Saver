package com.sopt.saver.saver.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.MainViewPager.MainViewPagerActivity;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.Sign.SignUpActivity;
import com.sopt.saver.saver.application.ApplicationController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    SharedPreferences sf;
    private NetworkService service;
    Vibrator vibe;
    private Button login_btn;
    private TextView signup_btn;
    private TextView find_id_password_tv;
    private EditText login_id_edit, login_pw_edit;
    CheckBox auto_check;

    String id;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /////////////서비스 객체 초기화/////////////////
        service = ApplicationController.getInstance().getNetworkService();
        vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        //////////////////객체 초기화/////////////////
        login_id_edit = (EditText) findViewById(R.id.login_id_edit);
        login_pw_edit = (EditText) findViewById(R.id.login_pw_edit);
        login_btn = (Button) findViewById(R.id.login_btn);
        signup_btn = (TextView) findViewById(R.id.signup_btn);
        find_id_password_tv = (TextView)findViewById(R.id.find_id_password_tv);
        auto_check = (CheckBox)findViewById(R.id.auto_check_box);
        ////////////////////폰트적용////////////////////////////
        signup_btn.setText(Html.fromHtml("<u>" + "회원가입" + "</u>"));
        ///////////////////클릭이벤트//////////////////////////
        find_id_password_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "관리자(kkang9413@gmail.com)에게 문의하세요.", Toast.LENGTH_SHORT).show();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_id_edit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (login_pw_edit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                ///////서버 통신////////
                login_btn.setEnabled(false);
                id = login_id_edit.getText().toString();
                password = login_pw_edit.getText().toString();
                LoginInfo loginInfo = new LoginInfo(id, password);
                Call<LoginResult> requestLogin = service.tryLogin(loginInfo);
                requestLogin.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                        if (response.isSuccessful()) {
                            //////////로그인 성공////////////
                            ////////마이페이지 연동//////////
                            if (response.body().message.equals("ok")) {
                                if(auto_check.isChecked() == true)
                                {
                                    sf = getSharedPreferences("login_data", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sf.edit();
                                    editor.clear();
                                    editor.putString("id", id.toString()); // 입력
                                    editor.putString("password",password.toString()); // 입력
                                    editor.commit(); // 파일에 최종 반영함
                                }
                                Intent intent = new Intent(LoginActivity.this, MainViewPagerActivity.class);
                                intent.putExtra("id", id.toString());
                                intent.putExtra("user_num", String.valueOf(response.body().result.user_num));
//                                Toast.makeText(getApplicationContext(), String.valueOf(response.body().result.user_num), Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            vibe.vibrate(1000);
                            Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        vibe.vibrate(1000);
                        Toast.makeText(getApplicationContext(), "On Failure", Toast.LENGTH_SHORT).show();
                        Log.i("myTag", t.toString());
                    }
                });
                login_btn.setEnabled(true);
            }
        });
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

}
