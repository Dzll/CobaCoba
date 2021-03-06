package com.dzulkarnain_inc.pulsa;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // Data Pulsa
    @GET("datapulsa")
    Call<List<ModelData>> getPulsaData();

    // Data User
    @FormUrlEncoded
    @POST("regisuser")
    Call<ResponseBody> registerInsert(@Field("nama") String nama, @Field("telepon") String telepon, @Field("email") String email, @Field("password") String password );

    @FormUrlEncoded
    @POST("loginuser")
    Call<ModelDataUser> getLoginData(@Field("email") String email, @Field("password") String password);

    // Data Transaksi
    @FormUrlEncoded
    @POST("transaksiadd")
    Call<ResponseBody> transaksi_insert(@Field("id_user") String id_user, @Field("nama_user") String nama_user, @Field("nohp_beli") String telepon,@Field("id_pulsa") String id_pulsa, @Field("jumlah_pulsa") String jumlah_pulsa, @Field("harga_pulsa") String harga_pulsa, @Field("operator") String operator);

    @GET("datatransaksi/{id_user}")
    Call<List<ModelDataTransaksi>> getTransaksiData(@Path("id_user") String id);

    // Pulsa Pay
    @GET("paydata/{id_user}")
    Call<List<ModelPayData>> getPayData(@Path("id_user") String id);

    @GET("pulsapay/{id_user}")
    Call<List<ModelPulsaPay>> getPulsaPay(@Path("id_user") String id);

    @FormUrlEncoded
    @POST("payadd")
    Call<ResponseBody> topupPulsaPay(@Field("nominal_pay")String nominal, @Field("id_user")String id);

    @FormUrlEncoded
    @POST("PulsaPayUp")
    Call<ResponseBody> updateNominal(@Field("id_pulsapay")String id, @Field("nominal_pay")String nominal, @Field("pulsabayar")String bayar);

}
