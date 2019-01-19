package com.dzulkarnain_inc.pulsa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelPulsaPay {

    @SerializedName("id_pulsapay")
    @Expose
    private String id_pulsapay;

    @SerializedName("nominal_pay")
    @Expose
    private String nominal_pay;

    public static final String idPulsaPay = "ID_PULSAPAY";

    public ModelPulsaPay(String nominal_pay, String id_pulsapay) {
        this.id_pulsapay = id_pulsapay;
        this.nominal_pay = nominal_pay;
    }

    public String getId_pay() { return id_pulsapay; }

    public void setId_pay(String id_pulsapay) { this.id_pulsapay = id_pulsapay; }

    public String getNominal_pay() { return nominal_pay; }

    public void setNominal_pay(String nominal_pay) { this.nominal_pay = nominal_pay; }


}
