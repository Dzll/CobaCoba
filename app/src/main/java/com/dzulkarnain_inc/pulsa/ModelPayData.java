package com.dzulkarnain_inc.pulsa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelPayData {

    @SerializedName("id_pay")
    @Expose
    private String id_pay;

    @SerializedName("nominal_pay")
    @Expose
    private String nominal_pay;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("status")
    @Expose
    private String status;

    public static final String idPayData = "ID_PAYDATA";

    public ModelPayData(String id_pay, String nominal_pay, String created_at, String status) {
        this.id_pay = id_pay;
        this.nominal_pay = nominal_pay;
        this.created_at = created_at;
        this.status = status;
    }

    public String getId_pay() { return id_pay; }

    public void setId_pay(String id_pay) { this.id_pay = id_pay; }

    public String getNominal_pay() { return nominal_pay; }

    public void setNominal_pay(String nominal_pay) { this.nominal_pay = nominal_pay; }

    public String getCreated_at() { return created_at; }

    public void setCreated_at(String created_at) { this.created_at = created_at; }

    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }
}
