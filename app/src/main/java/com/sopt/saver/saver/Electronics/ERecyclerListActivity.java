package com.sopt.saver.saver.Electronics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.Write.WritePageActivity;
import com.sopt.saver.saver.application.ApplicationController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ERecyclerListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private EditText find_et;
    private TextView upperbar_tv;
    private RecyclerView recyclerView;
    private ArrayList<EItemData> eDatas;
    private ArrayList<UItemData> uDatas;
    private ArrayList<BItemData> bDatas;
    private ArrayList<SItemData> sDatas;
    private ArrayList<ETCItemData> etcDatas;
    /////////////////////////////////////////////
    private LinearLayoutManager mLayoutManager;
    private ERecyclerAdapter eadapter;
    private URecyclerAdapter uadapter;
    private SRecyclerAdapter sadapter;
    private BRecyclerAdapter badapter;
    private ETCRecyclerAdapter etcadapter;
    /////////////////폰트 및 객체//////////////////
    private EditText search_et;
    private ImageView backImg;
    private ImageView writeImg;
    private ImageView findImg;
    private SwipeRefreshLayout refreshLayout;
    private EItemData edata;
    ///////////////////////////////////////
    NetworkService service;
    Call<EItemResult> requestEItemResult;
    Call<UItemResult> requestUItemResult;
    Call<BItemResult> requestBItemResult;
    Call<SItemResult> requestSItemResult;
    Call<ETCItemResult> requestETCItemResult;
    //////intent 통해서 받아온 변수//////
    String category;
    String user_num;
    String id;
    String find_text;

    //Back 키 두번 클릭 여부 확인
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

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
        eDatas = new ArrayList<EItemData>();
        uDatas = new ArrayList<UItemData>();
        bDatas = new ArrayList<BItemData>();
        sDatas = new ArrayList<SItemData>();
        etcDatas = new ArrayList<ETCItemData>();
        //////////////////////네트워킹/////////////////////
        requestEItemResult = service.getElectronicsResult();
        requestUItemResult = service.getEUtilResult();
        requestBItemResult = service.getEBrandResult();
        requestSItemResult = service.getESmartResult();
        requestETCItemResult = service.getEEtcResult();
        ////////////////////////파라미터로 위의 ArrayList와 클릭이벤트////////////////////////
        eadapter = new ERecyclerAdapter(eDatas, EclickEvent);
        uadapter = new URecyclerAdapter(uDatas, UclickEvent);
        sadapter = new SRecyclerAdapter(sDatas, SclickEvent);
        badapter = new BRecyclerAdapter(bDatas, BclickEvent);
        etcadapter = new ETCRecyclerAdapter(etcDatas, ETCclickEvent);
        ////////////////////////리스트 목록 추가 버튼에 리스너 설정////////////////////////
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        writeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WritePageActivity.class);
                intent.putExtra("user_num", user_num);
//                Toast.makeText(getApplicationContext(), user_num, Toast.LENGTH_SHORT).show();
                intent.putExtra("id", id);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });
        findImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////////////////EDIT TEXT 비쥬얼 변경//////////////////////
                if (find_et.getVisibility() == View.VISIBLE) {
                    find_text = find_et.getText().toString();
                    Intent find_intent = new Intent(getApplicationContext(), ERecyclerSearchCategoryActivity.class);
                    find_intent.putExtra("id", id);
                    find_intent.putExtra("user_num", user_num);
                    find_intent.putExtra("category", category);
                    find_intent.putExtra("find_text", find_text);
                    startActivity(find_intent);
                } else if (find_et.getVisibility() != View.VISIBLE) {
                    find_et.setVisibility(View.VISIBLE);
                    upperbar_tv.setVisibility(View.GONE);
                }
                ///////////////////////검색 시 서버와 통신////////////////////////
            }
        });
        findImg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            find_text = find_et.getText().toString();
                            Intent find_intent = new Intent(getApplicationContext(), ERecyclerSearchCategoryActivity.class);
                            find_intent.putExtra("id", id);
                            find_intent.putExtra("user_num", user_num);
                            find_intent.putExtra("category", category);
                            find_intent.putExtra("find_text", find_text);
                            startActivity(find_intent);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
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
        if (category.equals("전자제품")) {
            Networking(2);
            upperbar_tv.setText("전자제품");
        } else if (category.equals("티켓 및 이용권")) {
            Networking(3);
            upperbar_tv.setText("티켓 및 이용권");
        } else if (category.equals("브랜드")) {
            Networking(4);
            upperbar_tv.setText("브랜드");
        } else if (category.equals("스마트폰 및 가입상품")) {
            Networking(5);
            upperbar_tv.setText("스마트폰 및 가입상품");
        } else if (category.equals("기타")) {
            Networking(6);
            upperbar_tv.setText("기타");
        }
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
        if (category.equals("전자제품")) {
            Networking(2);
            upperbar_tv.setText("전자제품");
        } else if (category.equals("티켓 및 이용권")) {
            Networking(3);
            upperbar_tv.setText("티켓 및 이용권");
        } else if (category.equals("브랜드")) {
            Networking(4);
            upperbar_tv.setText("브랜드");
        } else if (category.equals("스마트폰 및 가입상품")) {
            Networking(5);
            upperbar_tv.setText("스마트폰 및 가입상품");
        } else if (category.equals("기타")) {
            Networking(6);
            upperbar_tv.setText("기타");
        }
    }

    ////////////////////////클릭 이벤트 정의////////////////////////
    public View.OnClickListener EclickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            int tempId = eDatas.get(itemPosition).elec_num;
            Intent intent = new Intent(getApplicationContext(), ESellerListViewActivity.class);
            intent.putExtra("category", upperbar_tv.getText().toString());
            intent.putExtra("thing_num", String.valueOf(tempId));
            intent.putExtra("id", id);
            intent.putExtra("user_num", user_num);
            startActivity(intent);
        }
    };
    public View.OnClickListener UclickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            int tempId = uDatas.get(itemPosition).util_num;
            Intent intent = new Intent(getApplicationContext(), ESellerListViewActivity.class);
            intent.putExtra("category", upperbar_tv.getText().toString());
            intent.putExtra("thing_num", String.valueOf(tempId));
            intent.putExtra("id", id);
            intent.putExtra("user_num", user_num);
            startActivity(intent);
        }
    };
    public View.OnClickListener BclickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            int tempId = bDatas.get(itemPosition).brand_num;
            Intent intent = new Intent(getApplicationContext(), ESellerListViewActivity.class);
            intent.putExtra("category", upperbar_tv.getText().toString());
            intent.putExtra("thing_num", String.valueOf(tempId));
            intent.putExtra("id", id);
            intent.putExtra("user_num", user_num);
            startActivity(intent);
        }
    };
    public View.OnClickListener SclickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            int tempId = sDatas.get(itemPosition).smart_num;
            Intent intent = new Intent(getApplicationContext(), ESellerListViewActivity.class);
            intent.putExtra("category", upperbar_tv.getText().toString());
            intent.putExtra("thing_num", String.valueOf(tempId));
            intent.putExtra("id", id);
            intent.putExtra("user_num", user_num);
            startActivity(intent);
        }
    };
    public View.OnClickListener ETCclickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            int tempId = etcDatas.get(itemPosition).etc_num;
            Intent intent = new Intent(getApplicationContext(), ESellerListViewActivity.class);
            intent.putExtra("category", upperbar_tv.getText().toString());
            intent.putExtra("thing_num", String.valueOf(tempId));
            intent.putExtra("id", id);
            intent.putExtra("user_num", user_num);
            startActivity(intent);
        }
    };


    public void Networking(int category) {
        if (category == 2) {
            requestEItemResult = service.getElectronicsResult();
            requestEItemResult.enqueue(new Callback<EItemResult>() {
                @Override
                public void onResponse(Call<EItemResult> call, Response<EItemResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            eDatas = response.body().result;
                            eadapter.setAdapter(eDatas);
                            recyclerView.setAdapter(eadapter);
                        } else {
                            Toast.makeText(ERecyclerListActivity.this, "UNKNOWN ERROR OCCURED", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<EItemResult> call, Throwable t) {
                    Log.i("fail", t.getMessage());
                    Toast.makeText(ERecyclerListActivity.this, "NOT CONNECTED", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (category == 3) {
            requestUItemResult = service.getEUtilResult();
            requestUItemResult.enqueue(new Callback<UItemResult>() {
                @Override
                public void onResponse(Call<UItemResult> call, Response<UItemResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            uDatas = response.body().result;
                            uadapter.setAdapter(uDatas);
                            recyclerView.setAdapter(uadapter);
                        } else {
                            Toast.makeText(ERecyclerListActivity.this, "UNKNOWN ERROR OCCURED", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UItemResult> call, Throwable t) {
                    Log.i("fail", t.getMessage());
                    Toast.makeText(ERecyclerListActivity.this, "NOT CONNECTED", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (category == 4) {
            requestBItemResult = service.getEBrandResult();
            requestBItemResult.enqueue(new Callback<BItemResult>() {
                @Override
                public void onResponse(Call<BItemResult> call, Response<BItemResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            bDatas = response.body().result;
                            badapter.setAdapter(bDatas);
                            recyclerView.setAdapter(badapter);
                        } else {
                            Toast.makeText(ERecyclerListActivity.this, "UNKNOWN ERROR OCCURED", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BItemResult> call, Throwable t) {
                    Log.i("fail", t.getMessage());
                    Toast.makeText(ERecyclerListActivity.this, "NOT CONNECTED", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (category == 5) {
            requestSItemResult = service.getESmartResult();
            requestSItemResult.enqueue(new Callback<SItemResult>() {
                @Override
                public void onResponse(Call<SItemResult> call, Response<SItemResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            sDatas = response.body().result;
                            sadapter.setAdapter(sDatas);
                            recyclerView.setAdapter(sadapter);
                        } else {
                            Toast.makeText(ERecyclerListActivity.this, "UNKNOWN ERROR OCCURED", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SItemResult> call, Throwable t) {
                    Log.i("fail", t.getMessage());
                    Toast.makeText(ERecyclerListActivity.this, "NOT CONNECTED", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (category == 6) {
            requestETCItemResult = service.getEEtcResult();
            requestETCItemResult.enqueue(new Callback<ETCItemResult>() {
                @Override
                public void onResponse(Call<ETCItemResult> call, Response<ETCItemResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            etcDatas = response.body().result;
                            etcadapter.setAdapter(etcDatas);
                            recyclerView.setAdapter(etcadapter);
                        } else {
                            Toast.makeText(ERecyclerListActivity.this, "UNKNOWN ERROR OCCURED", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ETCItemResult> call, Throwable t) {
                    Log.i("fail", t.getMessage());
                    Toast.makeText(ERecyclerListActivity.this, "NOT CONNECTED", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
