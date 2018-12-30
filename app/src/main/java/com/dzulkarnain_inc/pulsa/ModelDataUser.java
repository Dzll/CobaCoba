package com.dzulkarnain_inc.pulsa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDataUser {

    @SerializedName("id")
    @Expose
    private String id_user;

    @SerializedName("name")
    @Expose
    private String nama_user;

    @SerializedName("no_hp")
    @Expose
    private String notelp_user;

    @SerializedName("email")
    @Expose
    private String email_user;

    @SerializedName("password")
    @Expose
    private String password_user;

    @SerializedName("status")
    private int status;

    public static final String idUser = "ID_USER";
    //public static final String nama_mahasiswa = "ID_MAHASISWA";
    //public static final String jurusan_mahasiswa = "ID_MAHASISWA";

    public ModelDataUser(String email_user, String password_user) {
        this.email_user = email_user;
        this.password_user = password_user;
    }

    public void setStatus (int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user (String id_user){
        this.id_user = id_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user (String nama_user){
        this.nama_user = nama_user;
    }

    public String getNotelp_user() {
        return notelp_user;
    }

    public void setNotelp_user (String notelp_user){
        this.notelp_user = notelp_user;
    }

    public String getEmailUser() {
        return email_user;
    }

    public void setEmailUser (String email_user){
        this.email_user = email_user;
    }

    public String getPasswordUser () {
        return password_user;
    }

    public void setPasswordUser (String password_user){
        this.password_user = password_user;
    }

}
