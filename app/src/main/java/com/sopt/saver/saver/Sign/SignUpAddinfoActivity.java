package com.sopt.saver.saver.Sign;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.API.DialogSetting;
import com.sopt.saver.saver.Login.LoginActivity;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpAddinfoActivity extends AppCompatActivity {

    private NetworkService service;
    private ImageButton back_btn, register_btn;
    private EditText signup_pay_edit;

    private TextView area_edit, like_edit, card_edit, visa_edit, tel_edit;

    private NumberPicker picker;
    private Button okBtn, cancleBtn;

    final String city_arr[] = {"서울", "경기", "인천", "부산", "대전", "대구", "광주", "충북", "충남", "경북", "경남", "강원", "전북", "전남"};
    final String how_arr[] = {"링크", "본인거래", "중개", "기타"};
    final String card_arr[] = {"국민", "신한", "우리", "하나", "롯데", "삼성", "현대"};
    final String visa_arr[] = {"유", "무"};
    final String tele_arr[] = {"SKT", "KT", "LGU+"};

    private String id, pw, pw_check, name, phone, bank, account, birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_addinfo);
        service = ApplicationController.getInstance().getNetworkService();

        back_btn = (ImageButton) findViewById(R.id.signup_back_btn);
        register_btn = (ImageButton) findViewById(R.id.signup_addinfo_submit);

        signup_pay_edit = (EditText) findViewById(R.id.signup_money_edit);

        area_edit = (TextView) findViewById(R.id.signup_area_spinner);
        like_edit = (TextView) findViewById(R.id.signup_like_spinner);
        card_edit = (TextView) findViewById(R.id.signup_card_spinner);
        visa_edit = (TextView) findViewById(R.id.signup_visa_spinner);
        tel_edit = (TextView) findViewById(R.id.signup_tel_spinner);
        ///////////////인텐트 전달////////////////////////
        id = getIntent().getExtras().getString("id");
        pw = getIntent().getExtras().getString("pw");
        pw_check = getIntent().getExtras().getString("pw_check");
        name = getIntent().getExtras().getString("name");
        phone = getIntent().getExtras().getString("phone");
        bank = getIntent().getExtras().getString("bank");
        account = getIntent().getExtras().getString("account");
        birth = getIntent().getExtras().getString("birth");
        ////////////////폰트 적용//////////////////////

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (signup_pay_edit.length() == 0) {
//                    Toast.makeText(getApplicationContext(), "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else if (area_edit.getText().equals("선택하기")) {
//                    Toast.makeText(getApplicationContext(), "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                else if (like_edit.getText().equals("선택하기")) {
//                    Toast.makeText(getApplicationContext(), "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else if (card_edit.getText().equals("선택하기")) {
//                    Toast.makeText(getApplicationContext(), "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else if (visa_edit.getText().equals("선택하기")) {
//                    Toast.makeText(getApplicationContext(), "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else if (tel_edit.getText().equals("선택하기")) {
//                    Toast.makeText(getApplicationContext(), "값을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                /////////////////////서버 통신/////////////////////////
                SignInfo signInfo = new SignInfo();
                signInfo.id = id;
                signInfo.password = pw;
                signInfo.password2 = pw_check;
                signInfo.name = name;
                signInfo.phone = phone;
                signInfo.bankname = bank;
                signInfo.account = account;
                signInfo.birth = birth;
                signInfo.local = area_edit.getText().toString();
                signInfo.method = like_edit.getText().toString();
                signInfo.card = card_edit.getText().toString();
                signInfo.visa = visa_edit.getText().toString();
                signInfo.wireless = tel_edit.getText().toString();
                signInfo.calling = signup_pay_edit.getText().toString();
                Call<SignResult> postSign = service.registerSignInfo(signInfo);
                postSign.enqueue(new Callback<SignResult>() {
                    @Override
                    public void onResponse(Call<SignResult> call, Response<SignResult> response) {
                        if(response.isSuccessful())
                        {
                            if(response.body().message.equals("signup success")){
                                Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), response.body().message , Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<SignResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "회원가입 오류", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////////////////서버통신////////////////////////
                Toast.makeText(getApplicationContext(), "회원가입을 처음부터 시작하세요.", Toast.LENGTH_SHORT).show();
                Intent intent_login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent_login);
                finish();
            }
        });


        area_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(1);
            }
        });

        like_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(2);
            }
        });

        card_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(3);
            }
        });

        visa_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(4);
            }
        });

        tel_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(5);
            }
        });
    }

    private void ShowDialog(int temp) {
        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.dialog_selector, null);
        final Dialog myDialog = new Dialog(this);
        final int check = temp;

        picker = (NumberPicker) dialogLayout.findViewById(R.id.info_picker);
        DialogSetting.setNumberPickerTextColor(picker, Color.rgb(50,50,50));
        picker.setMinValue(0);


        if (check == 1) {
            picker.setMaxValue(city_arr.length - 1);
            picker.setDisplayedValues(city_arr);
        } else if (check == 2) {
            picker.setMaxValue(how_arr.length - 1);
            picker.setDisplayedValues(how_arr);
        } else if (check == 3) {
            picker.setMaxValue(card_arr.length - 1);
            picker.setDisplayedValues(card_arr);
        } else if (check == 4) {
            picker.setMaxValue(visa_arr.length - 1);
            picker.setDisplayedValues(visa_arr);
        } else if (check == 5) {
            picker.setMaxValue(tele_arr.length - 1);
            picker.setDisplayedValues(tele_arr);
        }


        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        myDialog.setContentView(dialogLayout);
        myDialog.show();

        okBtn = (Button) dialogLayout.findViewById(R.id.select_dialog_ok_btn);
        cancleBtn = (Button) dialogLayout.findViewById(R.id.select_dialog_no_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == 1) {
                    area_edit.setText(city_arr[picker.getValue()].toString());
                } else if (check == 2) {
                    like_edit.setText(how_arr[picker.getValue()].toString());
                } else if (check == 3) {
                    card_edit.setText(card_arr[picker.getValue()].toString());
                } else if (check == 4) {
                    visa_edit.setText(visa_arr[picker.getValue()].toString());
                } else if (check == 5) {
                    tel_edit.setText(tele_arr[picker.getValue()].toString());
                }

                myDialog.dismiss();
            }
        });

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }


}
