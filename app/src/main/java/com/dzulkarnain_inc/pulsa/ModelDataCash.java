package com.dzulkarnain_inc.pulsa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDataCash {

    @SerializedName("id_cash")
    @Expose
    private String id_cash;

    @SerializedName("nominal_cash")
    @Expose
    private int nominal_cash;

    @SerializedName("id_user")
    @Expose
    private String id_user;

    public static final String idCash = "ID_CASH";
    //public static final String nama_mahasiswa = "ID_MAHASISWA";
    //public static final String jurusan_mahasiswa = "ID_MAHASISWA";

    public ModelDataCash(String id, int nominal) {
        this.id_cash = id;
        this.nominal_cash = nominal;
    }

    public String getIdCash() {
        return id_cash;
    }

    public void setId_cash (String id_cash){
        this.id_cash = id_cash;
    }

    public int getNominal_cash() {
        return nominal_cash;
    }

    public void setNominal_cash (int nominal_cash){
        this.nominal_cash = nominal_cash;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user (String id_cash){
        this.id_cash = id_cash;
    }

}
