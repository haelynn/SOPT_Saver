package com.sopt.saver.saver.Sign;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class SignUpActivity extends AppCompatActivity {


    private NetworkService service;
    private EditText id_edit, pw_edit, name_edit, pw_check_edit, phone_edit, account_edit, birth_edit;
    private TextView bank_edit;
    private ImageButton back_btn, submit_btn;
    private CheckBox checkBox3, checkBox4;
    private NumberPicker picker;
    private Button okBtn, cancleBtn;
    final String bankArray[] = {"KB국민", "신한", "우리", "KEB하나", "IBK기업", "NH농협", "SC제일"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        service = ApplicationController.getInstance().getNetworkService();

        checkBox3 = (CheckBox)findViewById(R.id.signup_use_check);
        checkBox4 = (CheckBox)findViewById(R.id.signup_personal_check);

        id_edit = (EditText) findViewById(R.id.signup_id_edit);
        pw_edit = (EditText) findViewById(R.id.signup_pw_edit);
        pw_check_edit = (EditText) findViewById(R.id.signup_pw_check_edit);
        name_edit = (EditText) findViewById(R.id.signup_name_edit);
        phone_edit = (EditText) findViewById(R.id.signup_phone_edit);
        account_edit = (EditText) findViewById(R.id.signup_account_edit);
        birth_edit = (EditText) findViewById(R.id.signup_birth_edit);
        bank_edit = (TextView) findViewById(R.id.signup_bank_spinner);
        back_btn = (ImageButton) findViewById(R.id.signup_back_btn);
        submit_btn = (ImageButton) findViewById(R.id.signup_submit_btn);


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_edit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    id_edit.requestFocus();                 // requestFocus() id_edittext로 focus 이동
                    return;
                }
                else if (pw_edit.length() == 0 || pw_edit.getText().toString().length() < 6) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 최소 6자 입력해주세요..", Toast.LENGTH_SHORT).show();
                    pw_edit.requestFocus();
                    return;
                }
                else if (pw_check_edit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    pw_check_edit.requestFocus();
                    return;
                }
                // 비밀번호 일치 확인
                else if (!pw_edit.getText().toString().equals(pw_check_edit.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    pw_edit.setText("");
                    pw_check_edit.setText("");
                    pw_edit.requestFocus();
                    return;
                }
                else if (name_edit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    name_edit.requestFocus();
                    return;
                }
                else if (phone_edit.length() == 0) {
                    Toast.makeText(getApplicationContext(), "핸드폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    phone_edit.requestFocus();
                    return;
                }
                else if (checkBox3.isChecked() == false || checkBox4.isChecked() == false)
                {
                    Toast.makeText(getApplicationContext(), "이용약관에 동의해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (birth_edit.getText().length() != 8)
                {
                    Toast.makeText(getApplicationContext(), "생년월일을 형식에 맞게\n 입력해주세요..", Toast.LENGTH_SHORT).show();
                    return;
                }
                AddInfoDialog addInfoDialog = new AddInfoDialog();
                addInfoDialog.show();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent_login);
                finish();

            }
        });

        bank_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
    }

    private void ShowDialog() {
        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.dialog_selector, null);
        final Dialog myDialog = new Dialog(this);

        picker = (NumberPicker) dialogLayout.findViewById(R.id.info_picker);
        DialogSetting.setNumberPickerTextColor(picker, Color.rgb(50,50,50));

        picker.setMinValue(0);
        picker.setMaxValue(bankArray.length - 1);
        picker.setDisplayedValues(bankArray);


        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        myDialog.setContentView(dialogLayout);
        myDialog.show();

        okBtn = (Button) dialogLayout.findViewById(R.id.select_dialog_ok_btn);
        cancleBtn = (Button) dialogLayout.findViewById(R.id.select_dialog_no_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bank_edit.setText(bankArray[picker.getValue()].toString());
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
    public class AddInfoDialog extends Dialog implements DialogInterface.OnClickListener {
        TextView textView;
        Button okButton;
        Button cancelButton;
        ImageView x_icon_img;
        public AddInfoDialog() {
            super(SignUpActivity.this);
            setContentView(R.layout.dialog_addinfo);
            textView = (TextView) findViewById(R.id.dialog_addinfo_text1);
            okButton = (Button) findViewById(R.id.dialog_addinfo_ok_btn);
            cancelButton = (Button) findViewById(R.id.dialog_addinfo_cancel_btn);
            x_icon_img = (ImageView) findViewById(R.id.dialog_addinfo_x_img);
            ///////////////폰트설정//////////////

            /////////////리스너등록/////////////
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SignUpAddinfoActivity.class);
                    intent.putExtra("id", id_edit.getText().toString());
                    intent.putExtra("pw", pw_edit.getText().toString());
                    intent.putExtra("pw_check", pw_check_edit.getText().toString());
                    intent.putExtra("name", name_edit.getText().toString());
                    intent.putExtra("phone", phone_edit.getText().toString());
                    intent.putExtra("bank", bank_edit.getText().toString());
                    intent.putExtra("account", account_edit.getText().toString());
                    intent.putExtra("birth", birth_edit.getText().toString());
                    startActivity(intent);
                    dismiss();
                    finish();
                }
            });
            x_icon_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /////////////////서버통신///////////////////////
                    Networking();
                    dismiss();
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ///////////////서버통신//////////////////////////
                    Networking();
                    dismiss();
                }
            });
        }
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }
    public void Networking(){
        SignInfo signinfo = new SignInfo();
        signinfo.id = id_edit.getText().toString();
        signinfo.password = pw_edit.getText().toString();
        signinfo.password2 = pw_check_edit.getText().toString();
        signinfo.name = name_edit.getText().toString();
        signinfo.phone = phone_edit.getText().toString();
        signinfo.account = account_edit.getText().toString();
        signinfo.birth = birth_edit.getText().toString();
        signinfo.bankname = bank_edit.getText().toString();
        Call<SignResult> requestSign = service.registerSignInfo(signinfo);
        requestSign.enqueue(new Callback<SignResult>() {
            @Override
            public void onResponse(Call<SignResult> call, Response<SignResult> response) {
                if(response.isSuccessful()){
                    if(response.body().message.equals("signup success"))
                    {
                        Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "회원가입 오류", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
            @Override
            public void onFailure(Call<SignResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "회원가입 오류", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
