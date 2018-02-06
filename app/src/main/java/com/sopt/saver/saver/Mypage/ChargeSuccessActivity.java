package com.sopt.saver.saver.Mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sopt.saver.saver.R;

import java.text.DecimalFormat;

public class ChargeSuccessActivity extends AppCompatActivity {


    private TextView charge2_money;
    private TextView charge2_name;
    private TextView charge2_chargemoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_success);

        charge2_money = (TextView)findViewById(R.id.charge2_money);
        charge2_name = (TextView)findViewById(R.id.charge2_name);
        charge2_chargemoney = (TextView)findViewById(R.id.charge2_charge_money);

        /////폰트적용//////
        Intent intent = getIntent();
        String charge_price = intent.getStringExtra("charge_pay");
        String name = intent.getExtras().getString("name");


        if(!charge_price.contains(",")){
            long value = Long.parseLong(charge_price);
            DecimalFormat format = new DecimalFormat("###,###");
            format.format(value);
            charge_price = format.format(value);
        }
        charge2_name.setText(name);
        charge2_money.setText(charge_price + " P");
        charge2_chargemoney.setText(charge_price + " 원");

        ///이전 페이지 이동
        findViewById(R.id.charge2_charge_btn).setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
}
