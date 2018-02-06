package com.sopt.saver.saver.Write;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.saver.saver.API.ETCOperation;
import com.sopt.saver.saver.Network.NetworkService;
import com.sopt.saver.saver.R;
import com.sopt.saver.saver.application.ApplicationController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritePageActivity extends AppCompatActivity {

    /////////////네트워크 및 이미지처리/////////////
    private NetworkService service;
    final int REQ_CODE_SELECT_IMAGE = 100;
    String imgUrl = "";
    Uri data;
    MultipartBody.Part body;
    /////////////객체 처리////////////////
    private EditText title_et;
    private EditText kind_et;
    private EditText product_et;
    private EditText price_et;
    private EditText url_et;
    private EditText want_et;
    private EditText add_info_et;
    private TextView upper_tv;
    private TextView title_tv;
    private TextView category_tv;
    private TextView kind_tv;
    private TextView product_tv;
    private TextView price_tv;
    private TextView url_tv;
    private TextView sub_tv;
    private TextView img_tv;
    private TextView add_info_tv;

    private ImageView add_img;
    private ImageButton elec_img_btn;
    private ImageButton ticket_btn;
    private ImageButton brand_btn;
    private ImageButton smart_btn;
    private ImageButton etc_btn;
    private DatePicker datePicker;
    private ImageButton submit_btn;
    private ImageButton back_btn;
    ///////////////데이터 처리/////////////
    private int[] checked = {0, 0, 0, 0, 0};
    File image;
    String id;
    String user_num;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_page);
        ///////////네트워크 서비스//////////
        service = ApplicationController.getInstance().getNetworkService();
        //////////객체초기화//////////////
        add_info_et = (EditText) findViewById(R.id.write_add_info_et);
        title_et = (EditText) findViewById(R.id.write_title_et);
        kind_et = (EditText) findViewById(R.id.write_kind_et);
        product_et = (EditText) findViewById(R.id.write_product_et);
        price_et = (EditText) findViewById(R.id.write_price_et);
        url_et = (EditText) findViewById(R.id.write_url_et);
        want_et = (EditText) findViewById(R.id.write_want_et);
        upper_tv = (TextView) findViewById(R.id.write_upper_tv);
        title_tv = (TextView) findViewById(R.id.write_title_tv);
        category_tv = (TextView) findViewById(R.id.write_cate_tv);
        kind_tv = (TextView) findViewById(R.id.write_kind_tv);
        product_tv = (TextView) findViewById(R.id.write_prod_tv);
        price_tv = (TextView) findViewById(R.id.write_price_tv);
        url_tv = (TextView) findViewById(R.id.write_url_tv);
        sub_tv = (TextView) findViewById(R.id.write_sub_tv);
        img_tv = (TextView) findViewById(R.id.write_image_tv);
        add_info_tv = (TextView) findViewById(R.id.write_add_info_tv);

        add_img = (ImageView) findViewById(R.id.image_add_img);
        elec_img_btn = (ImageButton) findViewById(R.id.write_electronics_btn);
        ticket_btn = (ImageButton) findViewById(R.id.write_ticket_btn);
        brand_btn = (ImageButton) findViewById(R.id.write_brand_btn);
        smart_btn = (ImageButton) findViewById(R.id.write_smartphone_btn);
        etc_btn = (ImageButton) findViewById(R.id.write_etc_btn);
        datePicker = (DatePicker) findViewById(R.id.write_datepicker);
        submit_btn = (ImageButton) findViewById(R.id.writepage_success_btn);
        back_btn = (ImageButton) findViewById(R.id.write_back_btn);
        ///////////Intent 받아오기////////////////////
        user_num = getIntent().getExtras().getString("user_num");
        id = getIntent().getExtras().getString("id");
        category = getIntent().getExtras().getString("category");
        //////////////////폰트 설정/////////////////////

        //DatePicker 폰트설정
        
        try {
            if (category.equals("전자제품")) {
                elec_img_btn.setImageResource(R.drawable.writepage_electronics);
                checked[0] = 1;
                checked[1] = 0;
                checked[2] = 0;
                checked[3] = 0;
                checked[4] = 0;
                category = "전자제품";
            } else if (category.equals("티켓 및 이용권")) {
                ticket_btn.setImageResource(R.drawable.writepage_ticket);
                checked[0] = 0;
                checked[1] = 1;
                checked[2] = 0;
                checked[3] = 0;
                checked[4] = 0;
                category = "이용권";
            } else if (category.equals("브랜드")) {
                brand_btn.setImageResource(R.drawable.writepage_brand);
                checked[0] = 0;
                checked[1] = 0;
                checked[2] = 1;
                checked[3] = 0;
                checked[4] = 0;
                category = "브랜드";
            } else if (category.equals("스마트폰 및 가입상품")) {
                smart_btn.setImageResource(R.drawable.writepage_smartphone);
                checked[0] = 0;
                checked[1] = 0;
                checked[2] = 0;
                checked[3] = 1;
                checked[4] = 0;
                category = "스마트";
            } else if (category.equals("기타")) {
                etc_btn.setImageResource(R.drawable.writepage_guita);
                checked[0] = 0;
                checked[1] = 0;
                checked[2] = 0;
                checked[3] = 0;
                checked[4] = 1;
                category = "기타";
            }
        } catch (
                NullPointerException e)

        {
            e.printStackTrace();
        }
        ////////////클릭이벤트 처리///////////////
        back_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add_img.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });

        elec_img_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (checked[0] == 0) {
                    elec_img_btn.setImageResource(R.drawable.writepage_electronics);
                    ticket_btn.setImageResource(R.drawable.writepage_ticket2);
                    brand_btn.setImageResource(R.drawable.writepage_brand2);
                    smart_btn.setImageResource(R.drawable.writepage_smartphone2);
                    etc_btn.setImageResource(R.drawable.writepage_guita2);
                    checked[0] = 1;
                    checked[1] = 0;
                    checked[2] = 0;
                    checked[3] = 0;
                    checked[4] = 0;
                    category = "전자제품";
                } else if (checked[0] == 1) {
                    elec_img_btn.setImageResource(R.drawable.wrtiepage_electronics2);
                    ticket_btn.setImageResource(R.drawable.writepage_ticket2);
                    brand_btn.setImageResource(R.drawable.writepage_brand2);
                    smart_btn.setImageResource(R.drawable.writepage_smartphone2);
                    etc_btn.setImageResource(R.drawable.writepage_guita2);
                    checked[0] = 0;
                    checked[1] = 0;
                    checked[2] = 0;
                    checked[3] = 0;
                    checked[4] = 0;
                    category = "";
                }
            }
        });
        ticket_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (checked[1] == 0) {
                    elec_img_btn.setImageResource(R.drawable.wrtiepage_electronics2);
                    ticket_btn.setImageResource(R.drawable.writepage_ticket);
                    brand_btn.setImageResource(R.drawable.writepage_brand2);
                    smart_btn.setImageResource(R.drawable.writepage_smartphone2);
                    etc_btn.setImageResource(R.drawable.writepage_guita2);
                    checked[0] = 0;
                    checked[1] = 1;
                    checked[2] = 0;
                    checked[3] = 0;
                    checked[4] = 0;
                    category = "이용권";
                } else if (checked[1] == 1) {
                    elec_img_btn.setImageResource(R.drawable.wrtiepage_electronics2);
                    ticket_btn.setImageResource(R.drawable.writepage_ticket2);
                    brand_btn.setImageResource(R.drawable.writepage_brand2);
                    smart_btn.setImageResource(R.drawable.writepage_smartphone2);
                    etc_btn.setImageResource(R.drawable.writepage_guita2);
                    checked[0] = 0;
                    checked[1] = 0;
                    checked[2] = 0;
                    checked[3] = 0;
                    checked[4] = 0;
                    category = "";
                }
            }
        });
        brand_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (checked[2] == 0) {
                    elec_img_btn.setImageResource(R.drawable.wrtiepage_electronics2);
                    ticket_btn.setImageResource(R.drawable.writepage_ticket2);
                    brand_btn.setImageResource(R.drawable.writepage_brand);
                    smart_btn.setImageResource(R.drawable.writepage_smartphone2);
                    etc_btn.setImageResource(R.drawable.writepage_guita2);
                    checked[0] = 0;
                    checked[1] = 0;
                    checked[2] = 1;
                    checked[3] = 0;
                    checked[4] = 0;
                    category = "브랜드";
                } else if (checked[2] == 1) {
                    elec_img_btn.setImageResource(R.drawable.wrtiepage_electronics2);
                    ticket_btn.setImageResource(R.drawable.writepage_ticket2);
                    brand_btn.setImageResource(R.drawable.writepage_brand2);
                    smart_btn.setImageResource(R.drawable.writepage_smartphone2);
                    etc_btn.setImageResource(R.drawable.writepage_guita2);
                    checked[0] = 0;
                    checked[1] = 0;
                    checked[2] = 0;
                    checked[3] = 0;
                    checked[4] = 0;
                    category = "";
                }
            }
        });
        smart_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (checked[3] == 0) {
                    elec_img_btn.setImageResource(R.drawable.wrtiepage_electronics2);
                    ticket_btn.setImageResource(R.drawable.writepage_ticket2);
                    brand_btn.setImageResource(R.drawable.writepage_brand2);
                    smart_btn.setImageResource(R.drawable.writepage_smartphone);
                    etc_btn.setImageResource(R.drawable.writepage_guita2);
                    checked[0] = 0;
                    checked[1] = 0;
                    checked[2] = 0;
                    checked[3] = 1;
                    checked[4] = 0;
                    category = "스마트";
                } else if (checked[3] == 1) {
                    elec_img_btn.setImageResource(R.drawable.wrtiepage_electronics2);
                    ticket_btn.setImageResource(R.drawable.writepage_ticket2);
                    brand_btn.setImageResource(R.drawable.writepage_brand2);
                    smart_btn.setImageResource(R.drawable.writepage_smartphone2);
                    etc_btn.setImageResource(R.drawable.writepage_guita2);
                    checked[0] = 0;
                    checked[1] = 0;
                    checked[2] = 0;
                    checked[3] = 0;
                    checked[4] = 0;
                    category = "";
                }
            }
        });
        etc_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (checked[4] == 0) {
                    elec_img_btn.setImageResource(R.drawable.wrtiepage_electronics2);
                    ticket_btn.setImageResource(R.drawable.writepage_ticket2);
                    brand_btn.setImageResource(R.drawable.writepage_brand2);
                    smart_btn.setImageResource(R.drawable.writepage_smartphone2);
                    etc_btn.setImageResource(R.drawable.writepage_guita);
                    checked[0] = 0;
                    checked[1] = 0;
                    checked[2] = 0;
                    checked[3] = 0;
                    checked[4] = 1;
                    category = "기타";
                } else if (checked[4] == 1) {
                    elec_img_btn.setImageResource(R.drawable.wrtiepage_electronics2);
                    ticket_btn.setImageResource(R.drawable.writepage_ticket2);
                    brand_btn.setImageResource(R.drawable.writepage_brand2);
                    smart_btn.setImageResource(R.drawable.writepage_smartphone2);
                    etc_btn.setImageResource(R.drawable.writepage_guita2);
                    checked[0] = 0;
                    checked[1] = 0;
                    checked[2] = 0;
                    checked[3] = 0;
                    checked[4] = 0;
                    category = "";
                }
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                //////조건 검사///////
                if (title_et.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (product_et.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "상품을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (kind_et.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "품종을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (price_et.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "가격을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (url_et.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "URL을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (want_et.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "요구사항을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (add_info_et.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "추가정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                submit_btn.setEnabled(false);
                //텍스트 파일 정리
                RequestBody send_category = RequestBody.create(MediaType.parse("multipart/form-data"), category);
                RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"), title_et.getText().toString());
                RequestBody product = RequestBody.create(MediaType.parse("multipart/form-data"), product_et.getText().toString());
                RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), price_et.getText().toString());
                RequestBody time = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                RequestBody explantion = RequestBody.create(MediaType.parse("multipart/form-data"), want_et.getText().toString());
                RequestBody addinformation = RequestBody.create(MediaType.parse("multipart/form-data"), add_info_et.getText().toString());
                RequestBody kind = RequestBody.create(MediaType.parse("multipart/form-data"), kind_et.getText().toString());
                RequestBody period = RequestBody.create(MediaType.parse("multipart/form-data"),
                        datePicker.getYear() + "-" + ETCOperation.checkDigit(datePicker.getMonth() + 1) + "-" + ETCOperation.checkDigit(datePicker.getDayOfMonth()));
                RequestBody url = RequestBody.create(MediaType.parse("multipart/form-data"), url_et.getText().toString());
                ///////////////////이미지 파일처리//////////////////
                if (imgUrl == "") {
                    body = null;
                } else {

                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다

                    InputStream in = null; // here, you need to get your context.
                    try {
                        in = getContentResolver().openInputStream(data);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                        /*inputstream 형태로 받은 이미지로 부터 비트맵을 만들어 바이트 단위로 압축
                        그이우 스트림 배열에 담아서 전송합니다.
                         */

                    Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ), 압축된 바이트 배열을 담을 스트림
                    RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

                    image = new File(imgUrl); // 가져온 파일의 이름을 알아내려고 사용합니다
                    // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
                    body = MultipartBody.Part.createFormData("image", image.getName(), photoBody);
                }
                /////////////////통신 부분/////////////////
                Call<WriteResult> requestImgNotice = service.registerWriteInfo(user_num, send_category, title, product, price, time, addinformation, explantion, kind, period, url, body);
                requestImgNotice.enqueue(new Callback<WriteResult>() {
                    @Override
                    public void onResponse(Call<WriteResult> call, Response<WriteResult> response) {
                        if (response.isSuccessful()) {
                            if (response.body().message.equals("ok")) {
//                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "WRITE ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WriteResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
                        Log.i("myTag", t.toString());
                    }
                });
                submit_btn.setEnabled(true);
            }
        });
        ////////////GUIDE 화면 띄우기///////////////////////
        Guide1Dialog guide1Dialog = new Guide1Dialog();
        guide1Dialog.getWindow().

                setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        guide1Dialog.show();
    }

    // 선택된 이미지 가져오기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());
                    //이미지 미리보기
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    add_img.setImageBitmap(image_bitmap);
                    this.data = data.getData();
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
            super(WritePageActivity.this);
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

    public class Guide2Dialog extends Dialog implements DialogInterface.OnClickListener {
        ImageView x_icon;

        public Guide2Dialog() {
            super(WritePageActivity.this);
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
}
