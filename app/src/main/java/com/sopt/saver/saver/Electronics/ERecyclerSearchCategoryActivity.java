package com.sopt.saver.saver.Electronics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kyi42 on 2017-07-05.
 */

public class ERecyclerSearchCategoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private EditText find_et;
    private TextView upperbar_tv;
    private RecyclerView recyclerView;
    ArrayList<FindData> findDatas;
    /////////////////////////////////////////////
    private LinearLayoutManager mLayoutManager;
    private FindRecyclerAdapter findRecyclerAdapter;
    /////////////////폰트 및 객체//////////////////
    private EditText search_et;
    private ImageView backImg;
    private ImageView writeImg;
    private ImageView findImg;
    private SwipeRefreshLayout refreshLayout;
    /////////////////////////////////////////////
    String category;
    String user_num;
    String id;
    String find_text;

    NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics);
//        Log.i("Tag", "메인");
        ////////////////////////서비스 객체 초기화////////////////////////
        service = ApplicationController.getInstance().getNetworkService();
        ////////////////////////뷰 객체 초기화////////////////////////
        find_et = (EditText) findViewById(R.id.electronics_find_et);
        upperbar_tv = (TextView) findViewById(R.id.ER_UPPERBAR_tv);
        backImg = (ImageView) findViewById(R.id.electronics_back_img);
        writeImg = (ImageView) findViewById(R.id.electronics_write_img);
        findImg = (ImageView) findViewById(R.id.electronics_find_img);
        recyclerView = (RecyclerView) findViewById(R.id.electronics_recycler_view);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.RefreshLayout);
        recyclerView.setHasFixedSize(true);
        refreshLayout.setOnRefreshListener(this);
        //////////////////////////폰트선언////////////////////////////////
        ////////////////////////레이아웃 매니저 설정////////////////////////
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        ////////////////////////각 배열에 모델 개체를 가지는 ArrayList 초기화////////////////////////
        findDatas = new ArrayList<FindData>();
        //////////////////////네트워킹/////////////////////
        ////////////////////////파라미터로 위의 ArrayList와 클릭이벤트////////////////////////
        findRecyclerAdapter = new FindRecyclerAdapter(findDatas, clickEvent);
        ////////////////////////리스트 목록 추가 버튼에 리스너 설정////////////////////////
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        writeImg.setVisibility(View.GONE);
        findImg.setVisibility(View.GONE);
        /*
          OnCreate()- 생명주기 내의 통신
         액티비티가 지워지고 재생성 되지않는 이상 한 번만 실행됩니다.
         이러한 이유로 아래 쪽에 OnRestart()를 오버라이드 하여 메인액티비티가 재실행되는 경우
          리스트를 갱신합니다 아래에 있어요!!

          통신부는 따로 정리해서 올려드리겠습니다!!
         */
        ////////////////////CATEGORY별 서버통신///////////////////////
        category = getIntent().getExtras().getString("category");
        user_num = getIntent().getExtras().getString("user_num");
        id = getIntent().getExtras().getString("id");
        find_text = getIntent().getExtras().getString("find_text");
        Networking();
        upperbar_tv.setText("검색결과");
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
        refreshLayout.setRefreshing(false);
        Toast.makeText(getApplicationContext(), "페이지 리로드", Toast.LENGTH_SHORT).show();
    }

    /*
    리스트를 갱신하는 메소드입니다.
     */

    public void ListReload() {
        Networking();
    }

    ////////////////////////클릭 이벤트 정의////////////////////////
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            int tempId = findDatas.get(itemPosition).num;
            Intent intent = new Intent(getApplicationContext(), ESellerListViewActivity.class);
            intent.putExtra("category", findDatas.get(itemPosition).category);
            intent.putExtra("thing_num", String.valueOf(tempId));
            intent.putExtra("id", id);
            intent.putExtra("user_num", user_num);
            startActivity(intent);
        }
    };

    public void Networking() {
        if (category.equals("전자제품")) {
            Call<FindResult> getElecFindResult = service.getElecSearch(find_text);
            getElecFindResult.enqueue(new Callback<FindResult>() {
                @Override
                public void onResponse(Call<FindResult> call, Response<FindResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            findDatas = response.body().result;
                            findRecyclerAdapter.setAdapter(findDatas);
                            recyclerView.setAdapter(findRecyclerAdapter);
                        }
                    } else {

                    }
                }
                @Override
                public void onFailure(Call<FindResult> call, Throwable t) {

                }
            });
        } else if (category.equals("티켓 및 이용권")) {
            Call<FindResult> getUtilFindResult = service.getUtilSearch(find_text);
            getUtilFindResult.enqueue(new Callback<FindResult>() {
                @Override
                public void onResponse(Call<FindResult> call, Response<FindResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            findDatas = response.body().result;
                            findRecyclerAdapter.setAdapter(findDatas);
                            recyclerView.setAdapter(findRecyclerAdapter);
                        }
                    } else {

                    }
                }
                @Override
                public void onFailure(Call<FindResult> call, Throwable t) {

                }
            });
        } else if (category.equals("브랜드")) {
            Call<FindResult> getBrandFindResult = service.getBrandSearch(find_text);
            getBrandFindResult.enqueue(new Callback<FindResult>() {
                @Override
                public void onResponse(Call<FindResult> call, Response<FindResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            findDatas = response.body().result;
                            findRecyclerAdapter.setAdapter(findDatas);
                            recyclerView.setAdapter(findRecyclerAdapter);
                        }
                    } else {

                    }
                }
                @Override
                public void onFailure(Call<FindResult> call, Throwable t) {

                }
            });
        } else if (category.equals("스마트폰 및 가입상품")) {
            Call<FindResult> getSmartFindResult = service.getSmartSearch(find_text);
            getSmartFindResult.enqueue(new Callback<FindResult>() {
                @Override
                public void onResponse(Call<FindResult> call, Response<FindResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            findDatas = response.body().result;
                            findRecyclerAdapter.setAdapter(findDatas);
                            recyclerView.setAdapter(findRecyclerAdapter);
                        }
                    } else {

                    }
                }
                @Override
                public void onFailure(Call<FindResult> call, Throwable t) {

                }
            });
        } else if (category.equals("기타")) {
            Call<FindResult> getEtcFindResult = service.getEtcSearch(find_text);
            getEtcFindResult.enqueue(new Callback<FindResult>() {
                @Override
                public void onResponse(Call<FindResult> call, Response<FindResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            findDatas = response.body().result;
                            findRecyclerAdapter.setAdapter(findDatas);
                            recyclerView.setAdapter(findRecyclerAdapter);
                            if(findDatas.size() == 0)
                            {
                                recyclerView.setBackgroundResource(R.drawable.nosearch_background);
                            }
                        }
                    } else {

                    }
                }
                @Override
                public void onFailure(Call<FindResult> call, Throwable t) {

                }
            });
        }
    }
}

