package com.info.firinotomasyon.Activityler.Classlar;

public class TelefonNumaralari {
    private String telNo,kisiAd;
    private int kisi_id;



    public TelefonNumaralari() {
    }

    public TelefonNumaralari(String telNo, String kisiAd) {
        this.telNo = telNo;
        this.kisiAd = kisiAd;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getKisiAd() {
        return kisiAd;
    }

    public void setKisiAd(String kisiAd) {
        this.kisiAd = kisiAd;
    }

    public int getKisi_id() {
        return kisi_id;
    }

    public void setKisi_id(int kisi_id) {
        this.kisi_id = kisi_id;
    }
}
