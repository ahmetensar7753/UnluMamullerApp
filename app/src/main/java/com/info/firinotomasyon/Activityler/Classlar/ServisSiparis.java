package com.info.firinotomasyon.Activityler.Classlar;

import androidx.annotation.NonNull;

public class ServisSiparis implements Comparable<ServisSiparis>{

    private int musteri_id;
    private int servis_sira;
    private String musteri_ad;
    private String urun_ad;
    private int urun_adet;
    private double aldigi_fiyat;
    private int kayit_id;

    public ServisSiparis() {
    }

    public ServisSiparis(int musteri_id, int servis_sira, String urun_ad, int urun_adet) {
        this.musteri_id = musteri_id;
        this.servis_sira = servis_sira;
        this.urun_ad = urun_ad;
        this.urun_adet = urun_adet;
    }


    public int getMusteri_id() {
        return musteri_id;
    }

    public void setMusteri_id(int musteri_id) {
        this.musteri_id = musteri_id;
    }

    public int getServis_sira() {
        return servis_sira;
    }

    public void setServis_sira(int servis_sira) {
        this.servis_sira = servis_sira;
    }

    public String getMusteri_ad() {
        return musteri_ad;
    }

    public void setMusteri_ad(String musteri_ad) {
        this.musteri_ad = musteri_ad;
    }

    public String getUrun_ad() {
        return urun_ad;
    }

    public void setUrun_ad(String urun_ad) {
        this.urun_ad = urun_ad;
    }

    public int getUrun_adet() {
        return urun_adet;
    }

    public void setUrun_adet(int urun_adet) {
        this.urun_adet = urun_adet;
    }

    public double getAldigi_fiyat() {
        return aldigi_fiyat;
    }

    public void setAldigi_fiyat(double aldigi_fiyat) {
        this.aldigi_fiyat = aldigi_fiyat;
    }

    public int getKayit_id() {
        return kayit_id;
    }

    public void setKayit_id(int kayit_id) {
        this.kayit_id = kayit_id;
    }


    @Override
    public int compareTo(ServisSiparis o) {
        return new Integer(this.musteri_id).compareTo(o.getMusteri_id());
    }
}
