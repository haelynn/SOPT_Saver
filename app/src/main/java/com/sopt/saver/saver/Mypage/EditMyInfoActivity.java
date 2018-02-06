package com.sopt.saver.saver.Mypage;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMyInfoActivity extends AppCompatActivity {

    private NetworkService service;
    UpdateInfoData updateInfoData;

    private TextView ed_id = null;
    private EditText ed_pass = null;
    private EditText ed_pass_c = null;
    private TextView ed_name = null;
    private EditText ed_phone = null;
    private TextView ed_bank = null;
    private EditText ed_account = null;
    private EditText ed_birth = null;
    private TextView ed_city = null;
    private TextView ed_how = null;
    private TextView ed_card = null;
    private TextView ed_visa = null;
    private TextView ed_tele = null;
    private EditText ed_money = null;

    final String city[] = {"서울", "경기", "인천", "부산", "대전", "대구", "광주", "충북", "충남", "경북", "경남", "강원", "전북", "전남"};
    final String bank[] = {"KB국민", "신한", "우리", "KEB하나", "IBK기업", "NH농협", "SC제일"};
    final String how[] = {"링크", "본인거래", "중개", "기타"};
    final String card[] = {"국민", "신한", "우리", "하나", "롯데", "삼성", "현대"};
    final String visa[] = {"유", "무"};
    final String tele[] = {"SKT", "KT", "LGU+"};

    private NumberPicker picker;
    private Button okBtn;
    private Button cancelBtn;
    //데이터전달
    String user_num;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_info);
        service = ApplicationController.getInstance().getNetworkService();
        /////////인텐트////////////
        user_num = getIntent().getExtras().getString("user_num");
        id = getIntent().getExtras().getString("id");
//        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        /////////////객체화////////////
        ed_id = (TextView) findViewById(R.id.editinfo_id);
        ed_pass = (EditText) findViewById(R.id.editinfo_pass);
        ed_pass_c = (EditText) findViewById(R.id.editinfo_passc);
        ed_name = (TextView) findViewById(R.id.editinfo_name);
        ed_phone = (EditText) findViewById(R.id.editinfo_phone);
        ed_bank = (TextView) findViewById(R.id.editinfo_bank);
        ed_account = (EditText) findViewById(R.id.editinfo_account);
        ed_birth = (EditText) findViewById(R.id.editinfo_birth);
        ////
        ed_city = (TextView) findViewById(R.id.editinfo_city);
        ed_how = (TextView) findViewById(R.id.editinfo_how);
        ed_card = (TextView) findViewById(R.id.editinfo_card);
        ed_visa = (TextView) findViewById(R.id.editinfo_visa);
        ed_tele = (TextView) findViewById(R.id.editinfo_tele);
        ed_money = (EditText) findViewById(R.id.editinfo_money);


        /////폰트설정///

        //GET PREV
        //GET 이전 정보
        Call<PrevUpdataInfoResult> requestUpdateInfo = service.getPrevUpdateInfo(user_num);
        requestUpdateInfo.enqueue(new Callback<PrevUpdataInfoResult>() {
            @Override
            public void onResponse(Call<PrevUpdataInfoResult> call, Response<PrevUpdataInfoResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().message.equals("ok")) {
                        updateInfoData = new UpdateInfoData();
                        updateInfoData.password = response.body().result.password;
                        updateInfoData.name = response.body().result.name;
                        updateInfoData.birth = response.body().result.birth;
                        updateInfoData.phone = response.body().result.phone;
                        updateInfoData.bankname = response.body().result.bankname;
                        updateInfoData.account = response.body().result.account;
                        if (TextUtils.isEmpty(response.body().result.local) == false) {
                            updateInfoData.local = response.body().result.local;
                            ed_city.setText(updateInfoData.local);
                        }
                        if (TextUtils.isEmpty(response.body().result.method) == false) {
                            updateInfoData.method = response.body().result.method;
                            ed_how.setText(updateInfoData.method);
                        }
                        if (TextUtils.isEmpty(response.body().result.card) == false) {
                            updateInfoData.card = response.body().result.card;
                            ed_card.setText(updateInfoData.card);
                        }
                        if (TextUtils.isEmpty(response.body().result.visa) == false) {
                            updateInfoData.visa = response.body().result.visa;
                            ed_visa.setText(updateInfoData.visa);
                        }
                        if (TextUtils.isEmpty(response.body().result.wireless) == false) {
                            updateInfoData.wireless = response.body().result.wireless;
                            ed_tele.setText(updateInfoData.wireless);
                        }
                        if (TextUtils.isEmpty(response.body().result.calling) == false) {
                            updateInfoData.calling = response.body().result.calling;
                            ed_money.setText(updateInfoData.calling);
                        }
                        ed_id.setText(id);
                        ed_name.setText(updateInfoData.name);
                        ed_name.setEnabled(false);
                        ed_birth.setText(updateInfoData.birth);
                        ed_birth.setEnabled(false);
                        ed_birth.setText(updateInfoData.birth);
                        ed_phone.setText(updateInfoData.phone);
                        ed_bank.setText(updateInfoData.bankname);
                        ed_account.setText(updateInfoData.account);
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
        //이전페이지로 이동
        findViewById(R.id.editmyinfo_mypage_btn).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        findViewById(R.id.editmyinfo_success_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //조건검사
                if (ed_pass.getText().length() != 0 && !(ed_pass.getText().toString().equals(ed_pass_c.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_phone.length() == 0) {
                    Toast.makeText(getApplicationContext(), "핸드폰 번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_account.length() == 0) {
                    Toast.makeText(getApplicationContext(), "계좌번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ed_pass.getText().length() != 0 && ed_pass.getText().toString().equals(ed_pass_c.getText().toString())) {
                    updateInfoData.password = ed_pass.getText().toString();
                    updateInfoData.password2 = ed_pass.getText().toString();
                }
                updateInfoData.phone = ed_phone.getText().toString();
                updateInfoData.bankname = ed_bank.getText().toString();
                updateInfoData.account = ed_account.getText().toString();
                updateInfoData.local = ed_city.getText().toString();
                updateInfoData.method = ed_how.getText().toString();
                updateInfoData.card = ed_card.getText().toString();
                updateInfoData.visa = ed_visa.getText().toString();
                updateInfoData.wireless = ed_tele.getText().toString();
                updateInfoData.calling = ed_money.getText().toString();
                //PUT 이후 정보
                Call<UpdateInfoResult> putUpdateInfo = service.putUpdateInfo(user_num, updateInfoData);
                putUpdateInfo.enqueue(new Callback<UpdateInfoResult>() {
                    @Override
                    public void onResponse(Call<UpdateInfoResult> call, Response<UpdateInfoResult> response) {
                        if (response.isSuccessful()) {
                            if (response.body().message.equals("ok")) {
                                Toast.makeText(getApplicationContext(), "수정완료", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "CONNECTED BUT FAILED", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateInfoResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        ed_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(0);
            }
        });

        ed_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(1);
            }
        });

        ed_how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(2);
            }
        });

        ed_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(3);
            }
        });

        ed_visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(4);
            }
        });

        ed_tele.setOnClickListener(new View.OnClickListener() {
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

        picker.setMinValue(0);

        if (check == 0) {
            picker.setMaxValue(bank.length - 1);
            picker.setDisplayedValues(bank);
        } else if (check == 1) {
            picker.setMaxValue(city.length - 1);
            picker.setDisplayedValues(city);
        } else if (check == 2) {
            picker.setMaxValue(how.length - 1);
            picker.setDisplayedValues(how);
        } else if (check == 3) {
            picker.setMaxValue(card.length - 1);
            picker.setDisplayedValues(card);
        } else if (check == 4) {
            picker.setMaxValue(visa.length - 1);
            picker.setDisplayedValues(visa);
        } else if (check == 5) {
            picker.setMaxValue(tele.length - 1);
            picker.setDisplayedValues(tele);
        }


        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        myDialog.setContentView(dialogLayout);
        myDialog.show();

        okBtn = (Button) dialogLayout.findViewById(R.id.select_dialog_ok_btn);
        cancelBtn = (Button) dialogLayout.findViewById(R.id.select_dialog_no_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == 0) {
                    ed_bank.setText(bank[picker.getValue()].toString());
                } else if (check == 1) {
                    ed_city.setText(city[picker.getValue()].toString());
                } else if (check == 2) {
                    ed_how.setText(how[picker.getValue()].toString());
                } else if (check == 3) {
                    ed_card.setText(card[picker.getValue()].toString());
                } else if (check == 4) {
                    ed_visa.setText(visa[picker.getValue()].toString());
                } else if (check == 5) {
                    ed_tele.setText(tele[picker.getValue()].toString());
                }

                myDialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }
}
