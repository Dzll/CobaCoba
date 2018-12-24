package com.dzulkarnain_inc.pulsa;



import com.dzulkarnain_inc.pulsa.ModelData;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by JhonDev on 05/10/2016.
 */

public interface ApiService {

    // Data Pulsa

//    @FormUrlEncoded
//    @POST("tambah_data.php")
//    Call<ResponseBody> tambahData(@Field("nama_mhs") String nama, @Field("jurusan_mhs") String jurusan);
//
//    @FormUrlEncoded
//    @POST("edit_data.php")
//    Call<ResponseBody> editData(@Field("id_mhs") String id, @Field("nama_mhs") String nama, @Field("jurusan_mhs") String jurusan);
//
//    @FormUrlEncoded
//    @POST("hapus_data.php")
//    Call<ResponseBody> hapusData(@Field("id_mhs") String id_barang);

    @GET("lihat_data.php")
    Call<List<ModelData>> getPulsaData();

    @GET("single_data.php")
    Call<List<ModelData>> getSingleDataPulsa(@Query("id_pulsa") String id);


    // Data User

    @GET("user_data.php")
    Call<List<ModelDataUser>> getUserData(@Field("email_user") String email, @Field("password_user") String password);

    @FormUrlEncoded
    @POST("user_register.php")
    Call<ResponseBody> registerInsert(@Field("nama_user") String nama_user, @Field("notelp_user") String notelp_user, @Field("email_user") String email_user, @Field("password_user") String password_user );

    @FormUrlEncoded
    @POST("user_login.php")
    Call<List<ModelDataUser>> getLoginData(@Field("email_user") String email, @Field("password_user") String password);

    // Data Transaksi

    @FormUrlEncoded
    @POST("transaksi_insert.php")
    Call<ResponseBody> transaksi_insert(@Field("id_user") String id_user, @Field("nama_user") String nama_user, @Field("telepon") String telepon,@Field("id_pulsa") String id_pulsa, @Field("jumlah_pulsa") String jumlah_pulsa, @Field("harga_pulsa") String harga_pulsa, @Field("tanggal")String tanggal);

    @GET("transaksi_data.php")
    Call<List<ModelDataTransaksi>> getTransaksiData(@Query("id_user") String id);

}
