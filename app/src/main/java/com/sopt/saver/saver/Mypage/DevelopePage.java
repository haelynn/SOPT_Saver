package com.sopt.saver.saver.Mypage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.sopt.saver.saver.R;

public class DevelopePage extends AppCompatActivity {

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develope_page);

        back = (ImageButton)findViewById(R.id.develope_back_btn);
        /////폰트설정///

        ///페이지이동
        findViewById(R.id.develope_back_btn).setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
}
