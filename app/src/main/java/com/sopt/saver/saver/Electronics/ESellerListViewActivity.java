package com.sopt.saver.saver.Electronics;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.DetailInfo.DetailActivity;
import com.sopt.saver.saver.Mypage.ChargePageActivity;
import com.sopt.saver.saver.Mypage.MyPageResult;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.Write.WriteCommentPage;
import com.sopt.saver.saver.Write.WritePageActivity;
import com.sopt.saver.saver.application.ApplicationController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sopt.saver.saver.Electronics.ItemDataViewHolder.context;

public class ESellerListViewActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<ESellerData> eDatas;
    private LinearLayoutManager mLayoutManager;
    private TextView upperbar_tv;
    private ImageView back_img;
    private ImageView find_img;
    private ImageView write_img;
    private EditText find_et;
    private NetworkService service;
    private ViewPager viewPager;
    private EProductInfoPagerAdapter pagerAdapter;
    private ListView listView;
    private ESellerListAdapter eSellerListAdapter;
    private EProductInfoFragment eprod_info_frag;
    private EProductPictureFragment eprod_pict_frag;
    //////////////intent 통해서 받은 변수/////////////
    Intent detail_intent;
    String category;
    String thing_num;
    String user_num;
    String id;
    String writer_user_id;
    int my_point;
    int seller_point;
    String info_price;
    String demand_price;
    int list_position;
    int same_check = 0;
    int change_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eseller_list_view);
        ////////////////////////서비스 객체 초기화////////////////////////
        service = ApplicationController.getInstance().getNetworkService();
        ////////////////////////뷰 객체 초기화////////////////////////
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.eseller_sr);
        upperbar_tv = (TextView) findViewById(R.id.esller_list_upperbar_tv);
        find_img = (ImageView) findViewById(R.id.esller_list_find_img);
        write_img = (ImageView) findViewById(R.id.esller_list_write_img);
        back_img = (ImageView) findViewById(R.id.eseller_list_back_img);
        listView = (ListView) findViewById(R.id.eseller_listview);
        find_et = (EditText) findViewById(R.id.esller_list_find_et);
        ////////////////////ViewPager설정////////////////////////////////
        View header = getLayoutInflater().inflate(R.layout.viewpager, null, false);
        viewPager = (ViewPager) header.findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
            }
        });
        pagerAdapter = new EProductInfoPagerAdapter(getSupportFragmentManager());
        eprod_info_frag = new EProductInfoFragment();
        eprod_pict_frag = new EProductPictureFragment();
        eprod_info_frag.setAnsBtnOnClickListener(onClickListener);
        ////////////////////////레이아웃 매니저 설정////////////////////////
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ////////////////////////각 배열에 모델 개체를 가지는 ArrayList 초기화////////////////////////
        eDatas = new ArrayList<ESellerData>();
        ////////////////////////TEST DISPLAY////////////////////////////////////

        ///////////////////////폰트설정///////////////////////////

        //////////////////////인텐트 통해서 아이디값 전달//////////////////////
        category = getIntent().getExtras().getString("category").toString();
        upperbar_tv.setText(category);
        thing_num = getIntent().getExtras().getString("thing_num").toString();
        user_num = getIntent().getExtras().getString("user_num").toString();
        id = getIntent().getExtras().getString("id").toString();
        ////////////////////////ListView & Adapter////////////////////////
        eSellerListAdapter = new ESellerListAdapter(eDatas, open_btn_clickEvent);
        eSellerListAdapter.setUserId(id);
        listView.addHeaderView(header);
        listView.setAdapter(eSellerListAdapter);
        ////////////////////////리스트 목록 추가 버튼에 리스너 설정////////////////////////
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //새로고침 코드
                try {
                    Networking(1);
                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "상품정보 오류", Toast.LENGTH_SHORT).show();
                }
                // 새로고침 완료
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Seller + Product 서버 연동
        try {
            Networking(0);
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "상품정보 오류", Toast.LENGTH_SHORT).show();
        }
    }

    public View.OnClickListener open_btn_clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            //열람하기(자기 자신, 자기가 아닐 때) 다이어로그 1, 다이어로그 2
            list_position = listView.getPositionForView(v) - 1;
//            Toast.makeText(getApplicationContext(), String.valueOf(list_position), Toast.LENGTH_SHORT).show();
            /////////////판매자 댓글 입장////////////
            if (id.equals(eDatas.get(list_position).sellerid)) {
                EditCommentDialog editCommentDialog = new EditCommentDialog();
                editCommentDialog.show();
                return;
            }
            ///////////////구매자////////////////
            if (id.equals(writer_user_id)) {
                CheckOkDialog okdialog = new CheckOkDialog();
                okdialog.show();
            } else {
                CheckFailDialog faildialog = new CheckFailDialog();
                faildialog.show();
            }
        }
    };

    public class CheckOkDialog extends Dialog implements DialogInterface.OnClickListener {
        Button okButton;
        Button cancelButton;
        TextView text1;
        TextView text2;
        ImageView x_icon_img;

        public CheckOkDialog() {
            super(ESellerListViewActivity.this);
            setContentView(R.layout.dialog_checkok);
            okButton = (Button) findViewById(R.id.dialog_checkok_ok_btn);
            cancelButton = (Button) findViewById(R.id.dialog_checkok_cancel_btn);
            x_icon_img = (ImageView) findViewById(R.id.dialog_checkok_x_img);
            text1 = (TextView) findViewById(R.id.dialog_ok_text1);
            text2 = (TextView) findViewById(R.id.dialog_ok_text2);
            ///////////////////폰트설정/////////////////////////////

            ////////////////클릭 리스너//////////////////////////////
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detail_intent = new Intent(ESellerListViewActivity.this, DetailActivity.class);
                    detail_intent.putExtra("user_num", user_num);
                    detail_intent.putExtra("id", id);
                    detail_intent.putExtra("profileimage", eDatas.get(list_position).profileimage);
                    detail_intent.putExtra("sellerid", eDatas.get(list_position).sellerid);
                    detail_intent.putExtra("title", eDatas.get(list_position).title);
                    detail_intent.putExtra("price", eDatas.get(list_position).price);
                    info_price = eDatas.get(list_position).price;
                    detail_intent.putExtra("period", eDatas.get(list_position).period);
                    detail_intent.putExtra("local", eDatas.get(list_position).local);
                    detail_intent.putExtra("method", eDatas.get(list_position).method);
                    detail_intent.putExtra("addinformation", eDatas.get(list_position).addinformation);
                    ///////////////////서버통신////////////////////
                    /////////////사용 포인트 가능조회///////////////
                    Call<MyPageResult> requestMyPage = service.getMyPageInfo(user_num);
                    requestMyPage.enqueue(new Callback<MyPageResult>() {
                        @Override
                        public void onResponse(Call<MyPageResult> call, Response<MyPageResult> response) {
                            if (response.isSuccessful()) {
                                if (response.body().message.equals("ok")) {
                                    my_point = response.body().result.point;
//                                    Toast.makeText(getApplicationContext(), "My Point : " + String.valueOf(my_point), Toast.LENGTH_SHORT).show();
                                    /////////포인트 차감 + 구매자 포인트 증감/////////////////////
                                    int info_price_parse = Integer.parseInt(info_price);
                                    int demand_price_parse = Integer.parseInt(demand_price);
                                    change_point = (demand_price_parse - info_price_parse) / 4;
//                                    Toast.makeText(getApplicationContext(), String.valueOf(my_point) + " - " + String.valueOf(change_point), Toast.LENGTH_SHORT).show();
                                    if (my_point - change_point >= 0 && change_point >= 0) {
                                        ////////////서버통신 포인트 차감////////////
                                        ///////////////가격 입력시//////////////////
                                        //포인트 차감(판매자)
                                        PointInfo pointInfo = new PointInfo();
                                        pointInfo.point = my_point - change_point;
                                        Call<PointPayResult> putPayChange = service.putPointChange(user_num, pointInfo);
                                        putPayChange.enqueue(new Callback<PointPayResult>() {
                                            @Override
                                            public void onResponse(Call<PointPayResult> call, Response<PointPayResult> response) {
                                                if (response.isSuccessful()) {
                                                    if (response.body().message.equals("ok")) {
//                                                        Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();

                                                    }
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "CONNECTED BUT FAILED", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<PointPayResult> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        //포인트 조회 판매자
                                        Call<MyPageResult> requestSellerPage = service.getMyPageInfo(eDatas.get(list_position).user_num);
                                        requestSellerPage.enqueue(new Callback<MyPageResult>() {
                                            @Override
                                            public void onResponse(Call<MyPageResult> call, Response<MyPageResult> response) {
                                                if (response.isSuccessful()) {
                                                    if (response.body().message.equals("ok")) {
                                                        seller_point = response.body().result.point;
                                                        //포인트 증가 판매자
                                                        PointInfo pointSellerInfo = new PointInfo();
                                                        pointSellerInfo.point = seller_point + change_point;
//                                                        Toast.makeText(getApplicationContext(), String.valueOf(seller_point) + " + " + String.valueOf(change_point) + " = " + String.valueOf(pointSellerInfo.point), Toast.LENGTH_SHORT).show();
                                                        Call<PointPayResult> putSellerChange = service.putPointChange(eDatas.get(list_position).user_num, pointSellerInfo);
                                                        putSellerChange.enqueue(new Callback<PointPayResult>() {
                                                            @Override
                                                            public void onResponse(Call<PointPayResult> call, Response<PointPayResult> response) {
                                                                if (response.isSuccessful()) {
                                                                    if (response.body().message.equals("ok")) {
//                                                                        Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "CONNECTED BUT FAILED", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<PointPayResult> call, Throwable t) {
                                                                Toast.makeText(getApplicationContext(), "FAILED SELLER ADD", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        startActivity(detail_intent);
//                                                        Toast.makeText(getApplicationContext(), String.valueOf(seller_point) + " : " + String.valueOf(eDatas.get(list_position).user_num), Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "CONNECTED BUT FAILED", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<MyPageResult> call, Throwable t) {
//                                                Toast.makeText(getApplicationContext(), eDatas.get(list_position).user_num, Toast.LENGTH_SHORT).show();
//                                                Toast.makeText(getApplicationContext(), "FAILED SELLER", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        dismiss();
                                    } else {
                                        dismiss();
                                        PointChargeDialog pointChargeDialog = new PointChargeDialog();
                                        pointChargeDialog.show();
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "CONNECTED BUT FAILED", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MyPageResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            x_icon_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

    public class CheckFailDialog extends Dialog implements DialogInterface.OnClickListener {
        Button okButton;
        Button cancelButton;
        TextView text1;
        TextView text2;
        TextView text3;
        ImageView x_icon_img;

        public CheckFailDialog() {
            super(ESellerListViewActivity.this);
            setContentView(R.layout.dialog_checkfail);
            okButton = (Button) findViewById(R.id.dialog_checkfail_ok_btn);
            x_icon_img = (ImageView) findViewById(R.id.dialog_fail_x_img);
            text1 = (TextView) findViewById(R.id.dialog_fail_text1);
            text2 = (TextView) findViewById(R.id.dialog_fail_text2);
            text3 = (TextView) findViewById(R.id.dialog_fail_text3);
            /////////////폰트설정//////////////////

            /////////////리스너등록/////////////
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ESellerListViewActivity.this, WritePageActivity.class);
                    intent.putExtra("user_num", user_num);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    dismiss();
                }
            });
            x_icon_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

    public class PointChargeDialog extends Dialog implements DialogInterface.OnClickListener {
        Button okButton;
        TextView text1;
        TextView text2;
        ImageView x_icon_img;

        public PointChargeDialog() {
            super(ESellerListViewActivity.this);
            setContentView(R.layout.dialog_pointcharge);
            okButton = (Button) findViewById(R.id.dialog_pointcharge_ok_btn);
            x_icon_img = (ImageView) findViewById(R.id.dialog_pointcharge_x_img);
            text1 = (TextView) findViewById(R.id.dialog_pointcharge_text1);
            text2 = (TextView) findViewById(R.id.dialog_pointcharge_text2);
            /////////////폰트설정///////////////

            /////////////리스너등록/////////////
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ESellerListViewActivity.this, ChargePageActivity.class);
                    intent.putExtra("user_num", user_num);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    dismiss();
                }
            });
            x_icon_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

    public void Networking(final int refresh) throws NullPointerException {
        if (category.equals("전자제품")) {
            Call<EDetailResult> requestEDetailData = service.getElectronicsDetailResult(thing_num);
            requestEDetailData.enqueue(new Callback<EDetailResult>() {
                @Override
                public void onResponse(Call<EDetailResult> call, Response<EDetailResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            if (refresh == 0) {
                                Bundle bundle_prod_info = new Bundle();
                                Bundle bundle_pict_info = new Bundle();
                                bundle_prod_info.putString("id", id);
                                bundle_prod_info.putString("user_num", user_num);
                                bundle_prod_info.putString("writer_user_id", response.body().result.info.id);
                                writer_user_id = response.body().result.info.id;
                                bundle_prod_info.putString("same", "0");
                                same_check = 0;
                                if (id.equals(writer_user_id)) {
                                    bundle_prod_info.putString("same", "1");
                                    same_check = 1;
                                }
                                bundle_prod_info.putString("time", response.body().result.info.time);
                                bundle_prod_info.putString("period", response.body().result.info.period);
                                bundle_prod_info.putString("count", response.body().result.info.count);
                                bundle_pict_info.putString("image", response.body().result.info.image);
                                bundle_prod_info.putString("title", response.body().result.info.title);
//                        bundle_prod_info.putString("text2", response.body().result.category);
                                bundle_prod_info.putString("kind", response.body().result.info.kind);
                                bundle_prod_info.putString("product", response.body().result.info.product);
                                bundle_prod_info.putString("price", response.body().result.info.price);
                                demand_price = response.body().result.info.price;
                                bundle_prod_info.putString("profileimage", response.body().result.info.profileimage);
                                bundle_prod_info.putString("addinformation", response.body().result.info.addinformation);
                                bundle_prod_info.putString("explantion", response.body().result.info.explantion);
                                bundle_prod_info.putString("thing_num", String.valueOf(response.body().result.info.elec_num));
                                bundle_prod_info.putString("category", category);
                                bundle_pict_info.putString("count", response.body().result.info.count);
                                bundle_pict_info.putString("image", response.body().result.info.image);

                                eprod_info_frag.setContext(getApplicationContext());
                                eprod_pict_frag.setContext(getApplicationContext());

                                eprod_info_frag.setArguments(bundle_prod_info);
                                eprod_pict_frag.setArguments(bundle_pict_info);
                                //////////뷰페이저 첫페이지 초기화//////////////
                                pagerAdapter.setData(eprod_info_frag, eprod_pict_frag);
                                viewPager.setAdapter(pagerAdapter);
                                viewPager.setCurrentItem(0);
                            }
                            eprod_info_frag.setCount(response.body().result.info.count);
                            eprod_pict_frag.setCount(response.body().result.info.count);
                            eDatas = response.body().result.comment;
                            eSellerListAdapter.setAdapter(eDatas);
                            listView.setAdapter(eSellerListAdapter);
                        }
                    } else {
//                        Toast.makeText(ESellerListViewActivity.this, "HI2", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EDetailResult> call, Throwable t) {
//                    Toast.makeText(ESellerListViewActivity.this, "HI", Toast.LENGTH_SHORT).show();
                    Log.i("err", t.getMessage());
                }
            });
        } else if (category.equals("티켓 및 이용권")) {
            Call<UDetailResult> requestUDetailData = service.getUtilDetailResult(thing_num);
            requestUDetailData.enqueue(new Callback<UDetailResult>() {
                @Override
                public void onResponse(Call<UDetailResult> call, Response<UDetailResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            if (refresh == 0) {
                                Bundle bundle_prod_info = new Bundle();
                                Bundle bundle_pict_info = new Bundle();
                                bundle_prod_info.putString("id", id);
                                bundle_prod_info.putString("user_num", user_num);
                                bundle_prod_info.putString("writer_user_id", response.body().result.info.id);
                                writer_user_id = response.body().result.info.id;
                                bundle_prod_info.putString("same", "0");
                                same_check = 0;
                                if (id.equals(writer_user_id)) {
                                    bundle_prod_info.putString("same", "1");
                                    same_check = 1;
                                }
                                bundle_prod_info.putString("time", response.body().result.info.time);
                                bundle_prod_info.putString("period", response.body().result.info.period);
                                bundle_prod_info.putString("count", response.body().result.info.count);
                                bundle_pict_info.putString("image", response.body().result.info.image);
                                bundle_prod_info.putString("title", response.body().result.info.title);
//                        bundle_prod_info.putString("text2", response.body().result.category);
                                bundle_prod_info.putString("kind", response.body().result.info.kind);
                                bundle_prod_info.putString("product", response.body().result.info.product);
                                bundle_prod_info.putString("price", response.body().result.info.price);
                                demand_price = response.body().result.info.price;
                                bundle_prod_info.putString("thing_num", String.valueOf(response.body().result.info.util_num));
                                bundle_prod_info.putString("addinformation", response.body().result.info.addinformation);
                                bundle_prod_info.putString("explantion", response.body().result.info.explantion);
                                bundle_prod_info.putString("profileimage", response.body().result.info.profileimage);
                                bundle_prod_info.putString("category", category);

                                bundle_pict_info.putString("count", response.body().result.info.count);
                                bundle_pict_info.putString("image", response.body().result.info.image);

                                eprod_info_frag.setContext(getApplicationContext());
                                eprod_pict_frag.setContext(getApplicationContext());

                                eprod_info_frag.setArguments(bundle_prod_info);
                                eprod_pict_frag.setArguments(bundle_pict_info);
                                //////////뷰페이저 첫페이지 초기화//////////////
                                pagerAdapter.setData(eprod_info_frag, eprod_pict_frag);
                                viewPager.setAdapter(pagerAdapter);
                                viewPager.setCurrentItem(0);
                            }
                            eprod_info_frag.setCount(response.body().result.info.count);
                            eprod_pict_frag.setCount(response.body().result.info.count);

                            eDatas = response.body().result.comment;
                            eSellerListAdapter.setAdapter(eDatas);
                            listView.setAdapter(eSellerListAdapter);
                        }
                    } else {
//                        Toast.makeText(ESellerListViewActivity.this, "HI2", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UDetailResult> call, Throwable t) {
//                    Toast.makeText(ESellerListViewActivity.this, "HI", Toast.LENGTH_SHORT).show();
                    Log.i("err", t.getMessage());
                }
            });
        } else if (category.equals("브랜드")) {
            Call<BDetailResult> requestUDetailData = service.getBrandDetailResult(thing_num);
            requestUDetailData.enqueue(new Callback<BDetailResult>() {
                @Override
                public void onResponse(Call<BDetailResult> call, Response<BDetailResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            if (refresh == 0) {
                                Bundle bundle_prod_info = new Bundle();
                                Bundle bundle_pict_info = new Bundle();
                                bundle_prod_info.putString("id", id);
                                bundle_prod_info.putString("user_num", user_num);
                                bundle_prod_info.putString("writer_user_id", response.body().result.info.id);
                                writer_user_id = response.body().result.info.id;
                                bundle_prod_info.putString("same", "0");
                                same_check = 0;
                                if (id.equals(writer_user_id)) {
                                    bundle_prod_info.putString("same", "1");
                                    same_check = 1;
                                }
                                bundle_prod_info.putString("time", response.body().result.info.time);
                                bundle_prod_info.putString("period", response.body().result.info.period);
                                bundle_prod_info.putString("count", response.body().result.info.count);
                                bundle_pict_info.putString("image", response.body().result.info.image);
                                bundle_prod_info.putString("title", response.body().result.info.title);
//                        bundle_prod_info.putString("text2", response.body().result.category);
                                bundle_prod_info.putString("kind", response.body().result.info.kind);
                                bundle_prod_info.putString("product", response.body().result.info.product);
                                bundle_prod_info.putString("price", response.body().result.info.price);
                                demand_price = response.body().result.info.price;
                                bundle_prod_info.putString("profileimage", response.body().result.info.profileimage);
                                bundle_prod_info.putString("addinformation", response.body().result.info.addinformation);
                                bundle_prod_info.putString("explantion", response.body().result.info.explantion);
                                bundle_prod_info.putString("thing_num", String.valueOf(response.body().result.info.brand_num));
                                bundle_prod_info.putString("category", category);

                                bundle_pict_info.putString("image", response.body().result.info.image);
                                bundle_pict_info.putString("count", response.body().result.info.count);

                                eprod_info_frag.setContext(getApplicationContext());
                                eprod_pict_frag.setContext(getApplicationContext());

                                eprod_info_frag.setArguments(bundle_prod_info);
                                eprod_pict_frag.setArguments(bundle_pict_info);
                                //////////뷰페이저 첫페이지 초기화//////////////
                                pagerAdapter.setData(eprod_info_frag, eprod_pict_frag);
                                viewPager.setAdapter(pagerAdapter);
                                viewPager.setCurrentItem(0);
                            }
                            eprod_info_frag.setCount(response.body().result.info.count);
                            eprod_pict_frag.setCount(response.body().result.info.count);

                            eDatas = response.body().result.comment;
                            eSellerListAdapter.setAdapter(eDatas);
                            listView.setAdapter(eSellerListAdapter);
                        }
                    } else {
//                        Toast.makeText(ESellerListViewActivity.this, "HI2", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BDetailResult> call, Throwable t) {
//                    Toast.makeText(ESellerListViewActivity.this, "HI", Toast.LENGTH_SHORT).show();
                    Log.i("err", t.getMessage());
                }
            });
        } else if (category.equals("스마트폰 및 가입상품")) {
            Call<SDetailResult> requestSDetailData = service.getSmartDetailResult(thing_num);
            requestSDetailData.enqueue(new Callback<SDetailResult>() {
                @Override
                public void onResponse(Call<SDetailResult> call, Response<SDetailResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            if (refresh == 0) {
                                Bundle bundle_prod_info = new Bundle();
                                Bundle bundle_pict_info = new Bundle();
                                bundle_prod_info.putString("id", id);
                                bundle_prod_info.putString("user_num", user_num);
                                bundle_prod_info.putString("writer_user_id", response.body().result.info.id);
                                writer_user_id = response.body().result.info.id;
                                bundle_prod_info.putString("same", "0");
                                same_check = 0;
                                if (id.equals(writer_user_id)) {
                                    bundle_prod_info.putString("same", "1");
                                    same_check = 1;
                                }
                                bundle_prod_info.putString("time", response.body().result.info.time);
                                bundle_prod_info.putString("period", response.body().result.info.period);
                                bundle_prod_info.putString("count", response.body().result.info.count);
                                bundle_pict_info.putString("image", response.body().result.info.image);
                                bundle_prod_info.putString("title", response.body().result.info.title);
//                        bundle_prod_info.putString("text2", response.body().result.category);
                                bundle_prod_info.putString("kind", response.body().result.info.kind);
                                bundle_prod_info.putString("product", response.body().result.info.product);
                                bundle_prod_info.putString("price", response.body().result.info.price);
                                demand_price = response.body().result.info.price;
                                bundle_prod_info.putString("profileimage", response.body().result.info.profileimage);
                                bundle_prod_info.putString("addinformation", response.body().result.info.addinformation);
                                bundle_prod_info.putString("explantion", response.body().result.info.explantion);
                                bundle_prod_info.putString("thing_num", String.valueOf(response.body().result.info.smart_num));
                                bundle_prod_info.putString("category", category);

                                bundle_pict_info.putString("image", response.body().result.info.image);
                                bundle_pict_info.putString("count", response.body().result.info.count);

                                eprod_info_frag.setContext(getApplicationContext());
                                eprod_pict_frag.setContext(getApplicationContext());

                                eprod_info_frag.setArguments(bundle_prod_info);
                                eprod_pict_frag.setArguments(bundle_pict_info);
                                //////////뷰페이저 첫페이지 초기화//////////////
                                pagerAdapter.setData(eprod_info_frag, eprod_pict_frag);
                                viewPager.setAdapter(pagerAdapter);
                                viewPager.setCurrentItem(0);
                            }
                            eprod_info_frag.setCount(response.body().result.info.count);
                            eprod_pict_frag.setCount(response.body().result.info.count);

                            eDatas = response.body().result.comment;
                            eSellerListAdapter.setAdapter(eDatas);
                            listView.setAdapter(eSellerListAdapter);
                        }
                    } else {
//                        Toast.makeText(ESellerListViewActivity.this, "HI2", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SDetailResult> call, Throwable t) {
//                    Toast.makeText(ESellerListViewActivity.this, "HI", Toast.LENGTH_SHORT).show();
                    Log.i("err", t.getMessage());
                }
            });
        } else if (category.equals("기타")) {
            Call<ETCDetailResult> requestETCDetailData = service.getEtcDetailResult(thing_num);
            requestETCDetailData.enqueue(new Callback<ETCDetailResult>() {
                @Override
                public void onResponse(Call<ETCDetailResult> call, Response<ETCDetailResult> response) {
                    if (response.isSuccessful()) {
                        if (response.body().message.equals("ok")) {
                            if (refresh == 0) {
                                Bundle bundle_prod_info = new Bundle();
                                Bundle bundle_pict_info = new Bundle();
                                bundle_prod_info.putString("id", id);
                                bundle_prod_info.putString("user_num", user_num);
                                bundle_prod_info.putString("writer_user_id", response.body().result.info.id);
                                writer_user_id = response.body().result.info.id;
                                bundle_prod_info.putString("same", "0");
                                same_check = 0;
                                if (id.equals(writer_user_id)) {
                                    bundle_prod_info.putString("same", "1");
                                    same_check = 1;
                                }
                                bundle_prod_info.putString("time", response.body().result.info.time);
                                bundle_prod_info.putString("period", response.body().result.info.period);
                                bundle_prod_info.putString("count", response.body().result.info.count);
                                bundle_pict_info.putString("image", response.body().result.info.image);
                                bundle_prod_info.putString("title", response.body().result.info.title);
//                        bundle_prod_info.putString("text2", response.body().result.category);
                                bundle_prod_info.putString("kind", response.body().result.info.kind);
                                bundle_prod_info.putString("product", response.body().result.info.product);
                                bundle_prod_info.putString("price", response.body().result.info.price);
                                demand_price = response.body().result.info.price;
                                bundle_prod_info.putString("profileimage", response.body().result.info.profileimage);
                                bundle_prod_info.putString("addinformation", response.body().result.info.addinformation);
                                bundle_prod_info.putString("explantion", response.body().result.info.explantion);
                                bundle_prod_info.putString("thing_num", String.valueOf(response.body().result.info.etc_num));
                                bundle_prod_info.putString("category", category);

                                bundle_pict_info.putString("image", response.body().result.info.image);
                                bundle_pict_info.putString("count", response.body().result.info.count);

                                eprod_info_frag.setContext(getApplicationContext());
                                eprod_pict_frag.setContext(getApplicationContext());

                                eprod_info_frag.setArguments(bundle_prod_info);
                                eprod_pict_frag.setArguments(bundle_pict_info);
                                //////////뷰페이저 첫페이지 초기화//////////////
                                pagerAdapter.setData(eprod_info_frag, eprod_pict_frag);
                                viewPager.setAdapter(pagerAdapter);
                                viewPager.setCurrentItem(0);
                            }
                            eprod_info_frag.setCount(response.body().result.info.count);
                            eprod_pict_frag.setCount(response.body().result.info.count);

                            eDatas = response.body().result.comment;
                            eSellerListAdapter.setAdapter(eDatas);
                            listView.setAdapter(eSellerListAdapter);
                        }
                    } else {
//                        Toast.makeText(ESellerListViewActivity.this, "HI2", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ETCDetailResult> call, Throwable t) {
//                    Toast.makeText(ESellerListViewActivity.this, "HI", Toast.LENGTH_SHORT).show();
                    Log.i("err", t.getMessage());
                }
            });
        }
    }

    private void enableDisableSwipeRefresh(boolean enable) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(enable);
        }
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (same_check == 0) {
                Intent answer_intent = new Intent(ESellerListViewActivity.this, WriteCommentPage.class);
                answer_intent.putExtra("id", id);
                answer_intent.putExtra("user_num", user_num);
                answer_intent.putExtra("category", category);
                answer_intent.putExtra("thing_num", thing_num);
                startActivity(answer_intent);
            } else {
                EditDialog editDialog = new EditDialog();
                editDialog.show();
            }
        }
    };

    public class EditDialog extends Dialog implements DialogInterface.OnClickListener {
        Button editButton;
        Button delButton;
        TextView text1;
        TextView text2;
        ImageView x_icon_img;

        public EditDialog() {
            super(ESellerListViewActivity.this);
            setContentView(R.layout.dialog_edit);
            editButton = (Button) findViewById(R.id.dialog_edit_ok_btn);
            delButton = (Button) findViewById(R.id.dialog_edit_cancel_btn);
            x_icon_img = (ImageView) findViewById(R.id.dialog_edit_x_img);
            text1 = (TextView) findViewById(R.id.dialog_edit_text1);
            text2 = (TextView) findViewById(R.id.dialog_edit_text2);
            ///////////폰트설정//////////////////

            /////////////리스너등록/////////////
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "준비중 입니다", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (category.equals("전자제품")) {
                        Call<DeleteResult> deleteElec = service.deleteElecInfo(thing_num);
                        deleteElec.enqueue(new Callback<DeleteResult>() {
                            @Override
                            public void onResponse(Call<DeleteResult> call, Response<DeleteResult> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().message.equals("ok")) {
                                        ///////////////////이전 액티비티로 넘어간다./////////////////////////
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResult> call, Throwable t) {
                                Toast.makeText(context.getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                    } else if (category.equals("티켓 및 이용권")) {
                        Call<DeleteResult> deleteUtil = service.deleteUtilInfo(thing_num);
                        deleteUtil.enqueue(new Callback<DeleteResult>() {
                            @Override
                            public void onResponse(Call<DeleteResult> call, Response<DeleteResult> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().message.equals("ok")) {
                                        ///////////////////이전 액티비티로 넘어간다./////////////////////////
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResult> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                    } else if (category.equals("브랜드")) {
                        Call<DeleteResult> deleteBrand = service.deleteBrandcInfo(thing_num);
                        deleteBrand.enqueue(new Callback<DeleteResult>() {
                            @Override
                            public void onResponse(Call<DeleteResult> call, Response<DeleteResult> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().message.equals("ok")) {
                                        ///////////////////이전 액티비티로 넘어간다./////////////////////////
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResult> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                    } else if (category.equals("스마트폰 및 가입상품")) {
                        Call<DeleteResult> deleteSmart = service.deleteSmartInfo(thing_num);
                        deleteSmart.enqueue(new Callback<DeleteResult>() {
                            @Override
                            public void onResponse(Call<DeleteResult> call, Response<DeleteResult> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().message.equals("ok")) {
                                        ///////////////////이전 액티비티로 넘어간다./////////////////////////
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResult> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                    } else if (category.equals("기타")) {
                        Call<DeleteResult> deleteEtc = service.deleteEtcInfo(thing_num);
                        deleteEtc.enqueue(new Callback<DeleteResult>() {
                            @Override
                            public void onResponse(Call<DeleteResult> call, Response<DeleteResult> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().message.equals("ok")) {
                                        ///////////////////이전 액티비티로 넘어간다./////////////////////////
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResult> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "CATEGORY NOT FINDED", Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                }
            });
            x_icon_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

    public class EditCommentDialog extends Dialog implements DialogInterface.OnClickListener {
        Button editButton;
        Button delButton;
        TextView text1;
        TextView text2;
        ImageView x_icon_img;

        public EditCommentDialog() {
            super(ESellerListViewActivity.this);
            setContentView(R.layout.dialog_edit);
            editButton = (Button) findViewById(R.id.dialog_edit_ok_btn);
            delButton = (Button) findViewById(R.id.dialog_edit_cancel_btn);
            x_icon_img = (ImageView) findViewById(R.id.dialog_edit_x_img);
            text1 = (TextView) findViewById(R.id.dialog_edit_text1);
            text2 = (TextView) findViewById(R.id.dialog_edit_text2);
            ///////////폰트설정//////////////////
            /////////////리스너등록/////////////
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "준비중 입니다", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (category.equals("전자제품")) {
                        Call<DeleteResult> deleteElec = service.deleteElecComment(String.valueOf(eDatas.get(list_position).num));
                        deleteElec.enqueue(new Callback<DeleteResult>() {
                            @Override
                            public void onResponse(Call<DeleteResult> call, Response<DeleteResult> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().message.equals("ok")) {
                                        ///////////////////이전 액티비티로 넘어간다./////////////////////////
//                                        Toast.makeText(getApplicationContext(), "삭제 성공", Toast.LENGTH_SHORT).show();
                                        Networking(1);
                                        dismiss();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResult> call, Throwable t) {
                                Toast.makeText(context.getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                    } else if (category.equals("티켓 및 이용권")) {
                        Call<DeleteResult> deleteUtil = service.deleteUtilComment(String.valueOf(eDatas.get(list_position).num));
                        deleteUtil.enqueue(new Callback<DeleteResult>() {
                            @Override
                            public void onResponse(Call<DeleteResult> call, Response<DeleteResult> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().message.equals("ok")) {
                                        ///////////////////이전 액티비티로 넘어간다./////////////////////////
//                                        Toast.makeText(getApplicationContext(), "삭제 성공", Toast.LENGTH_SHORT).show();
                                        Networking(1);
                                        dismiss();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResult> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                    } else if (category.equals("브랜드")) {
                        Call<DeleteResult> deleteBrand = service.deleteBrandComment(String.valueOf(eDatas.get(list_position).num));
                        deleteBrand.enqueue(new Callback<DeleteResult>() {
                            @Override
                            public void onResponse(Call<DeleteResult> call, Response<DeleteResult> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().message.equals("ok")) {
                                        ///////////////////이전 액티비티로 넘어간다./////////////////////////
//                                        Toast.makeText(getApplicationContext(), "삭제 성공", Toast.LENGTH_SHORT).show();
                                        Networking(1);
                                        dismiss();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResult> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                    } else if (category.equals("스마트폰 및 가입상품")) {
                        Call<DeleteResult> deleteSmart = service.deleteSmartComment(String.valueOf(eDatas.get(list_position).num));
                        deleteSmart.enqueue(new Callback<DeleteResult>() {
                            @Override
                            public void onResponse(Call<DeleteResult> call, Response<DeleteResult> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().message.equals("ok")) {
                                        ///////////////////이전 액티비티로 넘어간다./////////////////////////
//                                        Toast.makeText(getApplicationContext(), "삭제 성공", Toast.LENGTH_SHORT).show();
                                        Networking(1);
                                        dismiss();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResult> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                    } else if (category.equals("기타")) {
                        Call<DeleteResult> deleteEtc = service.deleteEtcComment(String.valueOf(eDatas.get(list_position).num));
                        deleteEtc.enqueue(new Callback<DeleteResult>() {
                            @Override
                            public void onResponse(Call<DeleteResult> call, Response<DeleteResult> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().message.equals("ok")) {
                                        ///////////////////이전 액티비티로 넘어간다./////////////////////////
//                                        Toast.makeText(getApplicationContext(), "삭제 성공", Toast.LENGTH_SHORT).show();
                                        Networking(1);
                                        dismiss();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResult> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "삭제 오류", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "CATEGORY NOT FINDED", Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                }
            });
            x_icon_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }
}
