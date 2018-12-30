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

    @SerializedName("nohp_beli")
    @Expose
    private String nohp_beli;

    @SerializedName("id_pulsa")
    @Expose
    private String id_pulsa;

    @SerializedName("created_at")
    @Expose
    private String tanggal;

    @SerializedName("nama_user")
    @Expose
    private String nama_user;

    @SerializedName("jumlah_pulsa")
    @Expose
    private String jumlah_pulsa;

    @SerializedName("harga_pulsa")
    @Expose
    private String harga_pulsa;

    public static final String idTransaksi = "ID_TRANSAKSI";
    //public static final String nama_mahasiswa = "ID_MAHASISWA";
    //public static final String jurusan_mahasiswa = "ID_MAHASISWA";

    public ModelDataTransaksi(String id_transaksi, String nohp_beli, String tanggal, String nama, String jumlah, String harga) {
        this.id_transaksi = id_transaksi;
        this.nohp_beli = nohp_beli;
        this.tanggal = tanggal;
        this.nama_user = nama;
        this.jumlah_pulsa = jumlah;
        this.harga_pulsa = harga;
    }

    public String getId_transaksi() { return id_transaksi; }

    public void setId_transaksi(String id_transaksi) { this.id_transaksi = id_transaksi; }

    public String getNohp_beli() { return nohp_beli; }

    public void setNohp_beli(String nohp_beli) { this.nohp_beli = nohp_beli; }

    public String getTanggal() { return tanggal; }

    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getNama_user() { return nama_user; }

    public void setNama_user(String nama_user) { this.nama_user = nama_user; }

    public String getJumlah_pulsa() { return jumlah_pulsa; }

    public void setJumlah_pulsa(String jumlah_pulsa) { this.jumlah_pulsa = jumlah_pulsa; }

    public String getHarga_pulsa() { return harga_pulsa; }

    public void setHarga_pulsa(String harga_pulsa) { this.harga_pulsa = harga_pulsa; }


}
