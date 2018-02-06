package com.sopt.saver.saver.Message;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sopt.saver.saver.R.id.message_send_content_edit;

public class MessageRegisterActivity extends AppCompatActivity {

    private ImageButton send_btn;
    private TextView id_title;
    private EditText message_send_content;
    private TextView text_length;

    final int REQ_CODE_SELECT_IMAGE=100;
    String imgUrl = "";
    Uri data;
    NetworkService service;

    MessageRegisterData messageRegisterData;
    String id;
    String user_num;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_register);
        ///////////인텐트 전달/////////////////
        id = getIntent().getExtras().getString("id").toString();
        user_num = getIntent().getExtras().getString("user_num").toString();
        ////////////////////////서비스 객체 초기화////////////////////////
        service = ApplicationController.getInstance().getNetworkService();

        ////////////////////////뷰 객체 초기화////////////////////////
        send_btn= (ImageButton)findViewById(R.id.message_send_btn);
        id_title= (TextView) findViewById(R.id.message_id);
        message_send_content= (EditText)findViewById(message_send_content_edit);
        text_length = (TextView)findViewById(R.id.text_length);
        //////////////////데이터 전달//////////////////////////
        id_title.setText(getIntent().getExtras().getString("sellerid").toString());
        ///////////////////////폰트설정////////////////////////////

        ////////////////////////프로그래스 다이얼로그 입니다////////////////////////
//        mProgressDialog = new ProgressDialog(MessageRegisterActivity.this);
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setMessage("등록 중...");
//        mProgressDialog.setIndeterminate(true);
        ////////////////////////text 필드의 텍스트 길이를 출력!!////////////////////////
        message_send_content.addTextChangedListener(new TextWatcher() {
            String strCur;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strCur = s.toString();

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text_length.setText(String.valueOf(s.length())+"/500");
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ////////////////////////Edit Text 길이제한!!////////////////////////
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(500);
        message_send_content.setFilters(FilterArray);

        ////////////////////////완료버튼!!////////////////////////
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_title.length() == 0 || message_send_content.length() == 0){
                    Toast.makeText(getApplicationContext(),"제목 및 내용을 확인해주세요.",Toast.LENGTH_SHORT).show();
                }
                else{
//                    mProgressDialog.show();
                    messageRegisterData = new MessageRegisterData();
                    messageRegisterData.sellerid = getIntent().getExtras().getString("sellerid");
                    messageRegisterData.content = message_send_content.getText().toString();
                    Call<MessageRegisterResult> registerMessage = service.registerMessage(user_num, messageRegisterData);
                    registerMessage.enqueue(new Callback<MessageRegisterResult>() {
                        @Override
                        public void onResponse(Call<MessageRegisterResult> call, Response<MessageRegisterResult> response) {
                            if(response.isSuccessful())
                            {
                                if(response.body().message.equals("ok"))
                                {
                                    Toast.makeText(getApplicationContext(), "메시지 전송 완료", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "NOT SUCCESS", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                                Toast.makeText(getApplicationContext(), "CONNECTED BUT FAILED", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<MessageRegisterResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
