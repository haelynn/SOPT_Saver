package com.sopt.saver.saver.Write;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.API.DialogSetting;
import com.sopt.saver.saver.API.ETCOperation;
import com.sopt.saver.saver.Electronics.ESellerListViewActivity;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteCommentPage extends AppCompatActivity {

    private NetworkService service;
    private EditText comment_title_input = null;
    private EditText comment_price_input = null;
    private EditText comment_sub_input = null;
    private TextView comment_image = null;
    private DatePicker comment_date_picker = null;
    private TextView select_region_tv;
    private RadioGroup method_rg;
    private ImageButton submit_btn;
    ////////////////////////데이터 전달///////////////////////
    String id;
    String user_num;
    String category;
    String thing_num;
    WriteCommentInfo writeCommentInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment_page);
        ///////////////////인텐트 초기화/////////////////////////
        id = getIntent().getExtras().getString("id");
        user_num = getIntent().getExtras().getString("user_num");
        category = getIntent().getExtras().getString("category");
        thing_num = getIntent().getExtras().getString("thing_num");
        /////////////////////네트워크서비스/////////////////////////
        service = ApplicationController.getInstance().getNetworkService();
        /////////////////////객체화/////////////////////////////////
        comment_title_input = (EditText) findViewById(R.id.comment_title_input);
        comment_price_input = (EditText) findViewById(R.id.comment_price_input);
        comment_date_picker = (DatePicker) findViewById(R.id.comment_date_picker);
        comment_sub_input = (EditText) findViewById(R.id.comment_sub_input);
        submit_btn = (ImageButton) findViewById(R.id.editmyinfo_success_btn);
        select_region_tv = (TextView)findViewById(R.id.comment_city_tv);
        method_rg = (RadioGroup)findViewById(R.id.comment_rg);
        //////폰트지정/////

        ///////////////가이드 화면 띄우기///////////////////////
        Guide2Dialog guide2Dialog = new Guide2Dialog();
        guide2Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        guide2Dialog.show();
        ///////////클릭이벤트////////////////////////////
        select_region_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegionSelectDialog regionSelectDialog= new RegionSelectDialog();
                DialogSetting.setNumberPickerTextColor(regionSelectDialog.picker, Color.rgb(50,50,50));
                regionSelectDialog.show();
            }
        });
        findViewById(R.id.comment_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int rDay = calendar.get(Calendar.DAY_OF_MONTH);
                int rMonth = calendar.get(Calendar.MONTH) + 1;
                int rYear = calendar.get(Calendar.YEAR);
                int sDay = comment_date_picker.getDayOfMonth();
                int sMonth = comment_date_picker.getMonth() + 1;
                int sYear = comment_date_picker.getYear();
                /////////////////빈칸체크/////////////////////////
                if(comment_title_input.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(comment_price_input.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "가격을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(select_region_tv.getText().toString().equals("선택하기"))
                {
                    Toast.makeText(getApplicationContext(), "지역을 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(sYear < rYear || ((sYear == rYear) && (sMonth < rMonth)) || ((sYear == rYear) && (sMonth == rMonth) && (sDay < rDay)))
                {
                    Toast.makeText(getApplicationContext(), "선택한 구매기한을 확인하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(comment_sub_input.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "세부 정보를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(method_rg.getCheckedRadioButtonId() < 0)
                {
                    Toast.makeText(getApplicationContext(), "구매 방법을 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(getApplicationContext(), id + " : " + user_num,Toast.LENGTH_SHORT).show();
                ////////////////서버통신 객체화///////////////////
                submit_btn.setEnabled(false);
                writeCommentInfo = new WriteCommentInfo();
                writeCommentInfo.id = id;
                writeCommentInfo.user_num = user_num;
                writeCommentInfo.title = comment_title_input.getText().toString();
                writeCommentInfo.price = comment_price_input.getText().toString();
                writeCommentInfo.time = "";
                writeCommentInfo.period = comment_date_picker.getYear() + "-" + ETCOperation.checkDigit(comment_date_picker.getMonth() + 1) + "-" + ETCOperation.checkDigit(comment_date_picker.getDayOfMonth());
                writeCommentInfo.addinformation = comment_sub_input.getText().toString();
                writeCommentInfo.local = select_region_tv.getText().toString();
                RadioButton radioButton = (RadioButton)findViewById(method_rg.getCheckedRadioButtonId());
                writeCommentInfo.method = radioButton.getText().toString();
                //////////////////////서버통신////////////////////
                if (category.equals("전자제품")) {
                    Call<WriteCommentResult> registerEComment = service.registerElecComment(thing_num, writeCommentInfo);
                    registerEComment.enqueue(new Callback<WriteCommentResult>() {
                        @Override
                        public void onResponse(Call<WriteCommentResult> call, Response<WriteCommentResult> response) {
                            if (response.isSuccessful()) {
                                if (response.body().message.equals("ok")) {
//                                    Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(WriteCommentPage.this, ESellerListViewActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("user_num", user_num);
                                    intent.putExtra("id", id);
                                    intent.putExtra("thing_num",thing_num);
                                    intent.putExtra("category", category);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "FAIL1" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "FAIL2" , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<WriteCommentResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();
                            Log.i("FAIL", t.getMessage());
                        }
                    });
                }
                else if (category.equals("티켓 및 이용권")) {
                    Call<WriteCommentResult> registerUComment = service.registerUtilComment(thing_num, writeCommentInfo);
                    registerUComment.enqueue(new Callback<WriteCommentResult>() {
                        @Override
                        public void onResponse(Call<WriteCommentResult> call, Response<WriteCommentResult> response) {
                            if (response.isSuccessful()) {
                                if (response.body().message.equals("ok")) {
//                                    Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(WriteCommentPage.this, ESellerListViewActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("user_num", user_num);
                                    intent.putExtra("id", id);
                                    intent.putExtra("thing_num",thing_num);
                                    intent.putExtra("category", category);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "FAIL1" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "FAIL2" , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<WriteCommentResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();
                            Log.i("FAIL", t.getMessage());
                        }
                    });
                } else if (category.equals("브랜드")) {
                    Call<WriteCommentResult> registerBComment = service.registerBrandCommet(thing_num, writeCommentInfo);
                    registerBComment.enqueue(new Callback<WriteCommentResult>() {
                        @Override
                        public void onResponse(Call<WriteCommentResult> call, Response<WriteCommentResult> response) {
                            if (response.isSuccessful()) {
                                if (response.body().message.equals("ok")) {
//                                    Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(WriteCommentPage.this, ESellerListViewActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("user_num", user_num);
                                    intent.putExtra("id", id);
                                    intent.putExtra("thing_num",thing_num);
                                    intent.putExtra("category", category);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "FAIL1" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "FAIL2" , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<WriteCommentResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();
                            Log.i("FAIL", t.getMessage());
                        }
                    });
                } else if (category.equals("스마트폰 및 가입상품")) {
                    Call<WriteCommentResult> registerSComment = service.registerSmartComment(thing_num, writeCommentInfo);
                    registerSComment.enqueue(new Callback<WriteCommentResult>() {
                        @Override
                        public void onResponse(Call<WriteCommentResult> call, Response<WriteCommentResult> response) {
                            if (response.isSuccessful()) {
                                if (response.body().message.equals("ok")) {
//                                    Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(WriteCommentPage.this, ESellerListViewActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("user_num", user_num);
                                    intent.putExtra("id", id);
                                    intent.putExtra("thing_num",thing_num);
                                    intent.putExtra("category", category);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "FAIL1" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "FAIL2" , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<WriteCommentResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();
                            Log.i("FAIL", t.getMessage());
                        }
                    });
                } else if (category.equals("기타")) {
                    Call<WriteCommentResult> registerETCComment = service.registerEtcComment(thing_num, writeCommentInfo);
                    registerETCComment.enqueue(new Callback<WriteCommentResult>() {
                        @Override
                        public void onResponse(Call<WriteCommentResult> call, Response<WriteCommentResult> response) {
                            if (response.isSuccessful()) {
                                if (response.body().message.equals("ok")) {
//                                    Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(WriteCommentPage.this, ESellerListViewActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("user_num", user_num);
                                    intent.putExtra("id", id);
                                    intent.putExtra("thing_num",thing_num);
                                    intent.putExtra("category", category);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "FAIL1" , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "FAIL2" , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<WriteCommentResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();
                            Log.i("FAIL", t.getMessage());
                        }
                    });
                }
                submit_btn.setEnabled(true);
            }
        });
    }

    public class Guide2Dialog extends Dialog implements DialogInterface.OnClickListener {
        ImageView x_icon;

        public Guide2Dialog() {
            super(WriteCommentPage.this);
            setContentView(R.layout.dialog_guide2);
            x_icon = (ImageView) findViewById(R.id.guide2_xicon_img);
            x_icon.setOnClickListener(new View.OnClickListener() {
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
    public class RegionSelectDialog extends Dialog implements DialogInterface.OnClickListener {
        Button okButton;
        Button cancelButton;
        NumberPicker picker;
        final String picklist[] = {"서울", "경기", "충청", "경상", "제주도", "인천", "부산", "광주"};

        public RegionSelectDialog() {
            super(WriteCommentPage.this);
            setContentView(R.layout.dialog_selector);
            ////////////////객체화//////////////////////
            okButton = (Button)findViewById(R.id.select_dialog_ok_btn);
            cancelButton = (Button)findViewById(R.id.select_dialog_no_btn);
            picker = (NumberPicker)findViewById(R.id.info_picker);
            picker.setMinValue(0);
            picker.setMaxValue(picklist.length - 1);
            picker.setDisplayedValues(picklist);
            picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            ////////////// 폰트설정////////////////

            ////////////////클릭이벤트/////////////////
            okButton = (Button) findViewById(R.id.select_dialog_ok_btn);
            cancelButton = (Button) findViewById(R.id.select_dialog_no_btn);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_region_tv.setText(picklist[picker.getValue()].toString());
                    dismiss();
                }
            });
//            cancelButton = (Button) findViewById(R.id.select_dialog_cancel_btn);
            cancelButton.setOnClickListener(new View.OnClickListener() {
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
