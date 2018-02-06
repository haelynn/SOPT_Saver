package com.sopt.saver.saver.Message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView ryc;
    private String id, thing_num;
    private ArrayList<MessageDetailListData> detailDatas;
    private MDetailRecyclerAdapter mDetailRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout refreshLayout;
    private ImageButton back_btn, write_btn;

    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ////////////////////////서비스 객체 초기화////////////////////////
        service = ApplicationController.getInstance().getNetworkService();
        ////////////////////////뷰 객체 초기화////////////////////////
        back_btn = (ImageButton) findViewById(R.id.message_detail_back);
        write_btn = (ImageButton) findViewById(R.id.message_detail_write);
        ryc = (RecyclerView) findViewById(R.id.m_detail_recycler);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.m_detail_refresh);
        //////////////폰트적용//////////////

        ////////////////////////레이아웃 매니저 설정////////////////////////
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ryc.setLayoutManager(linearLayoutManager);
        ////////////////////////각 배열에 모델 개체를 가지는 ArrayList 초기화////////////////////////
        detailDatas = new ArrayList<MessageDetailListData>();
        ////////////////////////리사이클러 뷰와 어뎁터 연동////////////////////////
        mDetailRecyclerAdapter = new MDetailRecyclerAdapter(detailDatas, clickListener);
        ryc.setAdapter(mDetailRecyclerAdapter);
        ////////////////////////아이디값 전달받기////////////////////////
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        ////////////////////////네트워킹////////////////////////
        Networking();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Networking() {
        Call<MessageDetailResult> requestDetail = service.getDetailMessage(thing_num);
        requestDetail.enqueue(new Callback<MessageDetailResult>() {
            @Override
            public void onResponse(Call<MessageDetailResult> call, Response<MessageDetailResult> response) {
                if (response.isSuccessful()) {
//                    Log.i("myTag", String.valueOf(response.body().result.size()));
                    detailDatas = response.body().result;
                    mDetailRecyclerAdapter.setAdapter(detailDatas);
                }
            }

            @Override
            public void onFailure(Call<MessageDetailResult> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    /*
    onRestart()를 오버라이드하여 onPause -> onRestart 시
    리스트를 갱신하는 ListReload 메소드를 실행!!
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        ListReload();
    }

    /*
    리스트를 당기면 갱신되는 메소드입니다 사용하기 위해서
    implements SwipeRefreshLayout.OnRefreshListener 와
     xml에서 리스트를 감싸는 SwipeRefreshLayout 가 필요합니다!!
     */
    @Override
    public void onRefresh() {
        ListReload();
        refreshLayout.setRefreshing(false); //새로고침 완료
        Toast.makeText(getApplicationContext(), "페이지 리로드", Toast.LENGTH_SHORT).show();

    }

    /*
    리스트를 갱신하는 메소드입니다.
     */
    public void ListReload() {
        Call<MessageDetailResult> requestMainData = service.getDetailMessage(thing_num);
        requestMainData.enqueue(new Callback<MessageDetailResult>() {
            @Override
            public void onResponse(Call<MessageDetailResult> call, Response<MessageDetailResult> response) {

                if (response.isSuccessful()) {

//                    Log.i("myTag", String.valueOf(response.body().result.size()));
                    detailDatas = response.body().result;
                    mDetailRecyclerAdapter.setAdapter(detailDatas);

                }
            }

            @Override
            public void onFailure(Call<MessageDetailResult> call, Throwable t) {

            }
        });
    }
    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
