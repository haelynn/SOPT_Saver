package com.sopt.saver.saver.Network;

import com.sopt.saver.saver.Electronics.BDetailResult;
import com.sopt.saver.saver.Electronics.BItemResult;
import com.sopt.saver.saver.Electronics.DeleteResult;
import com.sopt.saver.saver.Electronics.EDetailResult;
import com.sopt.saver.saver.Electronics.EItemResult;
import com.sopt.saver.saver.Electronics.ETCDetailResult;
import com.sopt.saver.saver.Electronics.ETCItemResult;
import com.sopt.saver.saver.Electronics.FindResult;
import com.sopt.saver.saver.Electronics.PointInfo;
import com.sopt.saver.saver.Electronics.PointPayResult;
import com.sopt.saver.saver.Electronics.SDetailResult;
import com.sopt.saver.saver.Electronics.SItemResult;
import com.sopt.saver.saver.Electronics.UDetailResult;
import com.sopt.saver.saver.Electronics.UItemResult;
import com.sopt.saver.saver.Login.LoginInfo;
import com.sopt.saver.saver.Login.LoginResult;
import com.sopt.saver.saver.MainPage.MainPageResult;
import com.sopt.saver.saver.Message.MessageDetailResult;
import com.sopt.saver.saver.Message.MessageRegisterData;
import com.sopt.saver.saver.Message.MessageRegisterResult;
import com.sopt.saver.saver.Message.MessageResult;
import com.sopt.saver.saver.Mydeal.Mydeal_ProductResult;
import com.sopt.saver.saver.Mypage.ImageUpResult;
import com.sopt.saver.saver.Mypage.MyPageResult;
import com.sopt.saver.saver.Mypage.PrevUpdataInfoResult;
import com.sopt.saver.saver.Mypage.UpdateInfoData;
import com.sopt.saver.saver.Mypage.UpdateInfoResult;
import com.sopt.saver.saver.Question.QuestionInfo;
import com.sopt.saver.saver.Question.QuestionResult;
import com.sopt.saver.saver.Sign.SignInfo;
import com.sopt.saver.saver.Sign.SignResult;
import com.sopt.saver.saver.Write.WriteCommentInfo;
import com.sopt.saver.saver.Write.WriteCommentResult;
import com.sopt.saver.saver.Write.WriteResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;


/**
 * Created by pc on 2017-05-14.
 */

public interface NetworkService {

    ////////////로그인///////////////////////
    @POST("/login")
    Call<LoginResult> tryLogin(@Body LoginInfo loginInfo);

    //////////특정 사용자 권한확인////////////
    ///////////////////마이페이지////////////////
    @GET("/mypage/{user_num}")
    Call<MyPageResult> getMyPageInfo(@Path("user_num") String user_num);
    @Multipart
    @PUT("/mypage/{user_num}")
    Call<ImageUpResult> putImageInfo(@Path("user_num") String user_num, @Part MultipartBody.Part File);
    ///////////////////메인화면///////////////////
    @GET("/mainpage")
    Call<MainPageResult> getMainPageInfo();

    ////////////////////회원가입//////////////////
    @POST("/register")
    Call<SignResult> registerSignInfo(@Body SignInfo signInfo);

    /////////전체 게시글 조회////////////////
    @GET("/eleclist")
    Call<EItemResult> getElectronicsResult();

    @GET("/brandlist")
    Call<BItemResult> getEBrandResult();

    @GET("/utilizelist")
    Call<UItemResult> getEUtilResult();

    @GET("/etclist")
    Call<ETCItemResult> getEEtcResult();

    @GET("/smartlist")
    Call<SItemResult> getESmartResult();

    //////////////////////////////////////
    ////////////////특정 게시글 조회/////
    @GET("/eleclist/{elec_num}")
    Call<EDetailResult> getElectronicsDetailResult(@Path("elec_num") String elec_num);

    @GET("/utilizelist/{util_num}")
    Call<UDetailResult> getUtilDetailResult(@Path("util_num") String util_num);

    @GET("/brandlist/{brand_num}")
    Call<BDetailResult> getBrandDetailResult(@Path("brand_num") String brand_num);

    @GET("/smartlist/{smart_num}")
    Call<SDetailResult> getSmartDetailResult(@Path("smart_num") String smart_num);

    @GET("/etclist/{etc_num}")
    Call<ETCDetailResult> getEtcDetailResult(@Path("etc_num") String etc_num);

    /////////////////////////나의거래////////////////////////////////////////////
    @GET("/mydealcomment/{user_num}")
    Call<Mydeal_ProductResult> getMydealCommentProductResult(@Path("user_num") String user_num);
    @GET("/mydealwriter/{user_num}")
    Call<Mydeal_ProductResult> getMydealWriterProductResult(@Path("user_num") String user_num);
    /////////////////////글 삭제하기/////////////////////////////////////
    @DELETE("/elecdelete/{num}")
    Call<DeleteResult> deleteElecInfo(@Path("num") String num);
    @DELETE("/etcdelete/{num}")
    Call<DeleteResult> deleteEtcInfo(@Path("num") String num);
    @DELETE("/branddelete/{num}")
    Call<DeleteResult> deleteBrandcInfo(@Path("num") String num);
    @DELETE("/utildelete/{num}")
    Call<DeleteResult> deleteUtilInfo(@Path("num") String num);
    @DELETE("/smartdelete/{num}")
    Call<DeleteResult> deleteSmartInfo(@Path("num") String num);
    //////////////////////댓글 삭제하기//////////////////////////////////////////
    @DELETE("/eleccomdelete/{num}")
    Call<DeleteResult> deleteElecComment(@Path("num") String num);
    @DELETE("/utilcomdelete/{num}")
    Call<DeleteResult> deleteUtilComment(@Path("num") String num);
    @DELETE("/brandcomdelete/{num}")
    Call<DeleteResult> deleteBrandComment(@Path("num") String num);
    @DELETE("/smartcomdelete/{num}")
    Call<DeleteResult> deleteSmartComment(@Path("num") String num);
    @DELETE("/etccomdelete/{num}")
    Call<DeleteResult> deleteEtcComment(@Path("num") String num);
    ////////////////////////문의하기//////////////////////////////////////////////
    @POST("/mail")
    Call<QuestionResult> postQuestion(@Body QuestionInfo questionInfo);
    /////////////////////////////작성하기/////////////////////////////////////////
    @Multipart
    @POST("/buying/{user_num}")
    Call<WriteResult> registerWriteInfo(@Path("user_num") String user_num,
                                        @Part("category") RequestBody category,
                                        @Part("title") RequestBody title,
                                        @Part("product") RequestBody product,
                                        @Part("price") RequestBody price,
                                        @Part("time") RequestBody time,
                                        @Part("addinformation") RequestBody addinformation,
                                        @Part("explantion") RequestBody explantion,
                                        @Part("kind") RequestBody kind,
                                        @Part("period") RequestBody period,
                                        @Part("url") RequestBody url,
                                        @Part MultipartBody.Part file);
    ////////////////////////////////코멘트/////////////////////////////////
    @POST("/elecsellering/{elec_num}")
    Call<WriteCommentResult> registerElecComment(@Path("elec_num") String elec_num, @Body WriteCommentInfo writeCommentInfo);
    @POST("/utilsellering/{util_num}")
    Call<WriteCommentResult> registerUtilComment(@Path("util_num") String util_num, @Body WriteCommentInfo writeCommentInfo);
    @POST("/brandsellering/{brand_num}")
    Call<WriteCommentResult> registerBrandCommet(@Path("brand_num") String brand_num, @Body WriteCommentInfo writeCommentInfo);
    @POST("/smartsellering/{smart_num}")
    Call<WriteCommentResult> registerSmartComment(@Path("smart_num") String smart_num, @Body WriteCommentInfo writeCommentInfo);
    @POST("/etcsellering/{etc_num}")
    Call<WriteCommentResult> registerEtcComment(@Path("etc_num") String etc_num, @Body WriteCommentInfo writeCommentInfo);
    ///////////////////////////메시지////////////////////////////////////////////
    @GET("/message/{sellerid}")
    Call<MessageResult> getMessage(@Path("sellerid") String sellerid);
    @GET("/message/{sellerid}")
    Call<MessageDetailResult> getDetailMessage(@Path("sellerid") String sellerid);
    @POST("/message/{user_num}")
    Call<MessageRegisterResult> registerMessage(@Path("user_num") String user_num, @Body MessageRegisterData messageRegisterData);


    ///////////////////////////상세정보/////////////////////////////////////////
    //포인트처리
    @PUT("/point/{user_num}")
    Call<PointPayResult> putPointChange(@Path("user_num") String user_num, @Body PointInfo pointInfo);
    //////////////////////////검색하기/////////////////////////////////
    /////전체////////////
    @GET("/search/{search}")
    Call<FindResult> getTotalSearch(@Path("search") String search);
    /////카테고리별/////////
    @GET("/elecsearch/{elecsearch}")
    Call<FindResult> getElecSearch(@Path("elecsearch") String elecsearch);
    @GET("/smartsearch/{smartsearch}")
    Call<FindResult> getSmartSearch(@Path("smartsearch") String smartsearch);
    @GET("/etcsearch/{etcsearch}")
    Call<FindResult> getEtcSearch(@Path("etcsearch") String etcsearch);
    @GET("/utilsearch/{utilsearch}")
    Call<FindResult> getUtilSearch(@Path("utilsearch") String utilsearch);
    @GET("/brandsearch/{brandsearch}")
    Call<FindResult> getBrandSearch(@Path("brandsearch") String brandsearch);
    /////////내정보수정////////////////////
    @PUT("/infoupdate/{user_num}")
    Call<UpdateInfoResult> putUpdateInfo(@Path("user_num") String user_num, @Body UpdateInfoData updateInfoData);
    @GET("/infoupdate/{user_num}")
    Call<PrevUpdataInfoResult> getPrevUpdateInfo(@Path("user_num") String user_num);
}
