package com.sopt.saver.saver.Mypage;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sopt.saver.saver.Login.LoginActivity;
import com.sopt.saver.saver.MainViewPager.MainViewPagerActivity;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.PointPage.PointPageActivity;
import com.sopt.saver.saver.Question.QuestionActivity;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity extends AppCompatActivity {

    SharedPreferences sf;
    private NetworkService service;
    final int REQ_CODE_SELECT_IMAGE = 100;
    String imgUrl = "";
    Uri uri_data;
    MultipartBody.Part body;
    File image;
    Bitmap image_bitmap;
    RequestBody photoBody;
    ////////////////////////////////////////
    private TextView mypage_point = null;
    private TextView mypage_id = null;
    private TextView mypage_name = null;
    private ImageView mypage_image = null;

    String user_num;
    String id;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        ///////////////통신 준비////////////////////
        service = ApplicationController.getInstance().getNetworkService();
        user_num = getIntent().getExtras().getString("user_num");
        id = getIntent().getExtras().getString("id");
        ///////////객체화////////////////////////
        mypage_point = (TextView) findViewById(R.id.mypage_point);
        mypage_id = (TextView) findViewById(R.id.mypage_id);
        mypage_name = (TextView) findViewById(R.id.mypage_name);
        mypage_image = (ImageView) findViewById(R.id.mypage_profile);
        /////폰트설정///

        //////////////////클릭이벤트//////////////////
        mypage_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });
        //////////////////네트워크//////////////////
        Call<MyPageResult> requestMyPage = service.getMyPageInfo(user_num);
        requestMyPage.enqueue(new Callback<MyPageResult>() {
            @Override
            public void onResponse(Call<MyPageResult> call, Response<MyPageResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().message.equals("ok")) {
                        if (response.body().result.profileimage != null) {
                            Glide.with(getApplicationContext())
                                    .load(response.body().result.profileimage)
                                    .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                                    .into(mypage_image);
                        } else {
                            Glide.with(getApplicationContext())
                                    .load(R.drawable.mypage_icon)
                                    .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                                    .into(mypage_image);
                        }
                        mypage_id.setText(response.body().result.id);
                        mypage_name.setText(response.body().result.name);
                        name = response.body().result.name.toString();
                        mypage_point.setText(String.valueOf(response.body().result.point));

                        long value = Long.parseLong(mypage_point.getText().toString());
                        DecimalFormat format = new DecimalFormat("###,###");
                        format.format(value);
                        mypage_point.setText(format.format(value) + " P");
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<MyPageResult> call, Throwable t) {

            }
        });

        // 메인페이지 이동
        findViewById(R.id.mypage_main_btn).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MyPageActivity.this, MainViewPagerActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("user_num", user_num);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }
                }
        );

        // 리스트뷰
        ListView listview;
        MyPageListViewAdapter adapter;

        adapter = new MyPageListViewAdapter();
        adapter.setContext(getApplicationContext());

        listview = (ListView) findViewById(R.id.mypage_list);
        listview.setAdapter(adapter);

        // 섹션 0, 그냥 1, 토글 2, 넥스트 3
        adapter.addItem("계정", 0);
        adapter.addItem("내정보 수정", 3);
        adapter.addItem("포인트 관리", 3);
        adapter.addItem("로그아웃", 1);

        adapter.addItem("앱 설정", 0);
        adapter.addItem("댓글 알림 설정", 2);
        adapter.addItem("쪽지 알림 설정", 2);

        adapter.addItem("앱 정보", 0);
        adapter.addItem("앱 버전", 1);
        adapter.addItem("문의하기", 3);
        adapter.addItem("공지사항", 3);

        listview.setOnItemClickListener(onItemClickListener);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MyPageActivity.this, MainViewPagerActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("user_num", user_num);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 리스트뷰 별 페이지 이동
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 2) {
                Intent intent = new Intent(getApplicationContext(), PointPageActivity.class);
                intent.putExtra("user_num", user_num);
                intent.putExtra("id", mypage_id.getText());
                intent.putExtra("name", name);
                intent.putExtra("point", mypage_point.getText().toString());
                startActivity(intent);
            } else if (position == 1) {
                Intent intent = new Intent(getApplication(), EditMyInfoActivity.class);
                intent.putExtra("user_num", user_num);
                intent.putExtra("id", mypage_id.getText());
                intent.putExtra("point", mypage_point.getText().toString());
                startActivity(intent);
            } else if (position == 3) {
                //로그아웃
                sf = getSharedPreferences("login_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sf.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//액티비티 스택 비우기
                startActivity(intent);
                finish();
            } else if (position == 9) {
                Intent intent = new Intent(MyPageActivity.this, QuestionActivity.class);
                intent.putExtra("user_num", user_num);
                intent.putExtra("id", id);
                startActivity(intent);
            } else if(position == 10){
                Intent intent = new Intent(MyPageActivity.this, DevelopePage.class);
                startActivity(intent);
            }
        }
    };

    // 선택된 이미지 가져오기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());
                    //이미지 미리보기
                    image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    this.uri_data = data.getData();
                    //////////////////////서버통신////////////////////////////////
                    if (imgUrl == "") {
                        body = null;
                    } else {
                        BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다
                        InputStream in = null; // here, you need to get your context.
                        try {
                            in = getContentResolver().openInputStream(uri_data);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        /*inputstream 형태로 받은 이미지로 부터 비트맵을 만들어 바이트 단위로 압축
                        그이우 스트림 배열에 담아서 전송합니다.
                         */

                        final Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                        // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ), 압축된 바이트 배열을 담을 스트림
                        photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

                        image = new File(imgUrl); // 가져온 파일의 이름을 알아내려고 사용합니다
                        // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
                        body = MultipartBody.Part.createFormData("profileimage", image.getName(), photoBody);
                        Call<ImageUpResult> putImageUp = service.putImageInfo(user_num, body);
                        putImageUp.enqueue(new Callback<ImageUpResult>() {
                            @Override
                            public void onResponse(Call<ImageUpResult> call, Response<ImageUpResult> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().message.equals("ok")) {
                                        //////////////이미지 수정 시 보이도록 한다///////////////////
                                        Glide.with(getApplicationContext())
                                                .load(uri_data)
                                                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                                                .into(mypage_image);
//                                        Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "CONNECTED BUT FAIL", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ImageUpResult> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "NOT CONNECTED", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                imgUrl = "";
//                imgNameTextView.setText("");
            }
        }
    }

    // 선택된 이미지 파일명 가져오기
    public String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        imgUrl = imgPath;
        return imgName;
    }

    public class Guide1Dialog extends Dialog implements DialogInterface.OnClickListener {
        ImageView x_icon;

        public Guide1Dialog() {
            super(MyPageActivity.this);
            setContentView(R.layout.dialog_guide1);
            x_icon = (ImageView) findViewById(R.id.guide1_xicon_img);
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
}
