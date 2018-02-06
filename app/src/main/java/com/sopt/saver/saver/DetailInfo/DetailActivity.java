package com.sopt.saver.saver.DetailInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sopt.saver.saver.Message.MessageRegisterActivity;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class DetailActivity extends AppCompatActivity {

    ///////////네트워크 서비스///////////////
    NetworkService service;
    ////////////객체//////////////////
    private ImageView pays_profile = null;
    private TextView pays_navi = null;
    private TextView pays_h1 = null;
    private TextView pays_id = null;
    private TextView pays_title = null;
    private TextView pays_price_txt = null;
    private TextView pays_price = null;
    private TextView pays_date_txt = null;
    private TextView pays_date = null;
    private TextView pays_where_txt = null;
    private TextView pays_where = null;
    private TextView pays_how_txt = null;
    private TextView pays_how = null;
    private TextView pays_add_txt = null;
    private TextView pays_add = null;
    private TextView pays_sub1 = null;
    private TextView pays_sub2 = null;
    private TextView pays_report = null;
    /////////////인텐트 전달//////////////
    String id;
    String user_num;
    String profileimage;
    String sellerid;
    String title;
    String price;
    String period;
    String local;
    String method;
    String addinformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        service = ApplicationController.getInstance().getNetworkService();
        ///////////////인텐트////////////////////
        id = getIntent().getExtras().getString("id");
        user_num = getIntent().getExtras().getString("user_num");
        profileimage = getIntent().getExtras().getString("profileimage");
        sellerid = getIntent().getExtras().getString("sellerid");
        title = getIntent().getExtras().getString("title");
        price = getIntent().getExtras().getString("price");
        period = getIntent().getExtras().getString("period");
        local = getIntent().getExtras().getString("local");
        method = getIntent().getExtras().getString("method");
        addinformation = getIntent().getExtras().getString("addinformation");
        //네비바//
        pays_navi = (TextView) findViewById(R.id.paysuccess_navi);
        //신고하기
        pays_report = (TextView) findViewById(R.id.paysuccess_report);
        //////////
        pays_profile = (ImageView) findViewById(R.id.detail_profile);
        pays_h1 = (TextView) findViewById(R.id.paysuccess_h1);
        pays_id = (TextView) findViewById(R.id.paysuccess_id);
        pays_title = (TextView) findViewById(R.id.paysuccess_title);
        pays_price_txt = (TextView) findViewById(R.id.paysuccess_price_txt);
        pays_price = (TextView) findViewById(R.id.paysuccess_price);
        pays_date_txt = (TextView) findViewById(R.id.paysuccess_date_txt);
        pays_date = (TextView) findViewById(R.id.paysuccess_date);
        pays_where_txt = (TextView) findViewById(R.id.paysuccess_where_txt);
        pays_where = (TextView) findViewById(R.id.paysuccess_where);
        pays_how_txt = (TextView) findViewById(R.id.paysuccess_how_txt);
        pays_how = (TextView) findViewById(R.id.paysuccess_how);
        pays_add = (TextView) findViewById(R.id.paysuccess_add);
        pays_add_txt = (TextView) findViewById(R.id.paysuccess_add_txt);
        ////////////////
        pays_sub1 = (TextView) findViewById(R.id.paysuccess_sub1);
        pays_sub2 = (TextView) findViewById(R.id.paysuccess_sub2);

        /////폰트적용//////

        /////////////////클릭 이벤트//////////////////////////////
        findViewById(R.id.paysuccess_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent message_intent = new Intent(DetailActivity.this, MessageRegisterActivity.class);
                message_intent.putExtra("user_num", user_num);
                message_intent.putExtra("id", id);
                message_intent.putExtra("sellerid", sellerid);
                startActivity(message_intent);
            }
        });
        findViewById(R.id.paysuccess_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ///////////////////////셀러 정보 setText////////////////////////
        Glide.with(getApplicationContext())
                .load(profileimage)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(pays_profile);
        pays_id.setText(sellerid);
        pays_title.setText(title);
        pays_price.setText(price);
        pays_date.setText(period);
        pays_where.setText(local);
        pays_how.setText(method);
        pays_add.setText(addinformation);
    }
}
