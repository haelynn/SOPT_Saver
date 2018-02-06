package com.sopt.saver.saver.PointPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.Mypage.ChargePageActivity;
import com.sopt.saver.saver.Mypage.PrevUpdataInfoResult;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointPageActivity extends AppCompatActivity {

    String bankname;
    String account;
    PointPageListViewAdapter adapter;
    ListView listview;

    private NetworkService service;
    private TextView point_navi = null;
    private TextView point_point = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_page);
        service = ApplicationController.getInstance().getNetworkService();

        point_navi = (TextView)findViewById(R.id.point_navi);
        point_point = (TextView)findViewById(R.id.point_point);
        adapter = new PointPageListViewAdapter();
        listview = (ListView)findViewById(R.id.pointlistviews);

        //인텐트전달//
        Call<PrevUpdataInfoResult> requestUpdateInfo = service.getPrevUpdateInfo(getIntent().getExtras().getString("user_num"));
        requestUpdateInfo.enqueue(new Callback<PrevUpdataInfoResult>() {
            @Override
            public void onResponse(Call<PrevUpdataInfoResult> call, Response<PrevUpdataInfoResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().message.equals("ok")) {
//                        Toast.makeText(getApplicationContext(), response.body().result.bankname + " : " + response.body().result.account, Toast.LENGTH_SHORT).show();
                        bankname = response.body().result.bankname.toString();
                        account = response.body().result.account.toString();
                        adapter.addItem("계좌");
                        adapter.addItem(bankname, account);
                        adapter.addItem("앱 설정");
                        adapter.addItem("요구 환급액", 1);
//                        adapter.addItem("이용");
//                        adapter.addItem("이용내역 조회", 0);
//                        adapter.addItem("이용 총계", 0);
                        listview.setAdapter(adapter);
                    } else
                        Toast.makeText(getApplicationContext(), "CONNECTED BUT FAILED1", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "CONNECTED BUT FAILED2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PrevUpdataInfoResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
            }
        });
        //폰트적용//

        /////////////////////////////////////
        point_point.setText(getIntent().getExtras().getString("point"));
        findViewById(R.id.point_mypage_btn).setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        findViewById(R.id.point_charge_btn).setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ChargePageActivity.class);
                        intent.putExtra("id", getIntent().getExtras().getString("id"));
                        intent.putExtra("user_num", getIntent().getExtras().getString("user_num"));
                        intent.putExtra("name", getIntent().getExtras().getString("name"));
                        intent.putExtra("point", getIntent().getExtras().getString("point"));
                        startActivity(intent);
                    }
                }
        );
    }
}
