package com.dzulkarnain_inc.pulsa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDataTransaksi {

    @SerializedName("id_transaksi")
    @Expose
    private String id_transaksi;

    @SerializedName("id_user")
    @Expose
    private String id_user;

    @SerializedName("nama_user")
    @Expose
    private String nama_user;

    @SerializedName("telepon")
    @Expose
    private String telepon;

    @SerializedName("id_pulsa")
    @Expose
    private String id_pulsa;

    @SerializedName("jumlah_pulsa")
    @Expose
    private String jumlah_pulsa;

    @SerializedName("harga_pulsa")
    @Expose
    private String harga_pulsa;

    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    public static final String idTransaksi = "ID_TRANSAKSI";
    //public static final String nama_mahasiswa = "ID_MAHASISWA";
    //public static final String jurusan_mahasiswa = "ID_MAHASISWA";

    public ModelDataTransaksi(String id_transaksi, String jumlah_pulsa, String nama_user, String telepon, String harga_pulsa, String tanggal) {
        this.id_transaksi = id_transaksi;
        this.jumlah_pulsa = jumlah_pulsa;
        this.nama_user = nama_user;
        this.telepon = telepon;
        this.harga_pulsa = harga_pulsa;
        this.tanggal = tanggal;
    }

    public String getId_transaksi() { return id_transaksi; }

    public void setId_transaksi(String id_transaksi) { this.id_transaksi = id_transaksi; }

    public String getJumlah_pulsa() { return jumlah_pulsa; }

    public void setJumlah_pulsa(String jumlah_pulsa) { this.jumlah_pulsa = jumlah_pulsa; }

    public String getNama_user() { return nama_user; }

    public void setNama_user(String nama_user) { this.nama_user = nama_user; }

    public String getTelepon() { return telepon; }

    public void setTelepon(String telepon) { this.telepon = telepon; }

    public String getHarga_pulsa() { return harga_pulsa; }

    public void setHarga_pulsa(String harga_pulsa) { this.harga_pulsa = harga_pulsa; }

    public String getTanggal() { return tanggal; }

    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

}
