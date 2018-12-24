package com.dzulkarnain_inc.pulsa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelData {

    @SerializedName("jumlah_pulsa")
    @Expose
    private String jumlah_pulsa;

    @SerializedName("id_pulsa")
    @Expose
    private String id_pulsa;

    @SerializedName("masa_aktif")
    @Expose
    private String masa_aktif;

    @SerializedName("harga_pulsa")
    @Expose
    private String harga_pulsa;

    public static final String idPulsa = "ID_PULSA";
    //public static final String nama_mahasiswa = "ID_MAHASISWA";
    //public static final String jurusan_mahasiswa = "ID_MAHASISWA";

    public ModelData(String id_pulsa, String jumlah_pulsa, String masa_aktif, String harga_pulsa) {
        this.id_pulsa = id_pulsa;
        this.jumlah_pulsa = jumlah_pulsa;
        this.masa_aktif = masa_aktif;
        this.harga_pulsa = harga_pulsa;
    }

    /**
     *
     * @return
     *     The id_pulsa
     */
    public String getIdPulsa() {
        return id_pulsa;
    }

    /**
     *
     * @param id_pulsa
     *     The id_pulsa
     */
    public void setIdPulsa(String id_pulsa) {
        this.id_pulsa = id_pulsa;
    }

    /**
     * 
     * @return
     *     The jumlah_pulsa
     */
    public String getJumlah_pulsa() {
        return jumlah_pulsa;
    }

    /**
     * 
     * @param jumlah_pulsa
     *     The masa_aktif
     */
    public void setJumlah_pulsa(String jumlah_pulsa) {
        this.jumlah_pulsa = jumlah_pulsa;
    }

    /**
     * 
     * @return
     *     The masa_aktif
     */
    public String getMasa_aktif() {
        return masa_aktif;
    }

    /**
     * 
     * @param masa_aktif
     *     The masa_aktif
     */
    public void setMasa_aktif(String masa_aktif) {
        this.masa_aktif = masa_aktif;
    }

    /**
     *
     * @return
     *     The harga_pulsa
     */
    public String getHarga_pulsa() {
        return harga_pulsa;
    }

    /**
     *
     * @param harga_pulsa
     *     The harga_pulsa
     */
    public void setHarga_pulsa(String harga_pulsa) {
        this.harga_pulsa = harga_pulsa;
    }

}
