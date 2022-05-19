package com.info.firinotomasyon.Activityler.Classlar;

public class GramajUrunler {
    private int urun_id;
    private String urun_ad;
    private String resim_ad;
    private Double urun_fiyat;
    private Double urun_agirlik = 0.0;
    private String kimlik = "gramaj";

    public String getKimlik() {
        return kimlik;
    }

    public void setKimlik(String kimlik) {
        this.kimlik = kimlik;
    }

    public GramajUrunler() {
    }

    public GramajUrunler(int urun_id, String urun_ad, String resim_ad, Double urun_fiyat) {
        this.urun_id = urun_id;
        this.urun_ad = urun_ad;
        this.resim_ad = resim_ad;
        this.urun_fiyat = urun_fiyat;
    }

    public int getUrun_id() {
        return urun_id;
    }

    public void setUrun_id(int urun_id) {
        this.urun_id = urun_id;
    }

    public String getUrun_ad() {
        return urun_ad;
    }

    public void setUrun_ad(String urun_ad) {
        this.urun_ad = urun_ad;
    }

    public String getResim_ad() {
        return resim_ad;
    }

    public void setResim_ad(String resim_ad) {
        this.resim_ad = resim_ad;
    }

    public Double getUrun_fiyat() {
        return urun_fiyat;
    }

    public void setUrun_fiyat(Double urun_fiyat) {
        this.urun_fiyat = urun_fiyat;
    }

    public double getUrun_agirlik() {
        return urun_agirlik;
    }

    public void setUrun_agirlik(double urun_agirlik) {
        this.urun_agirlik = urun_agirlik;
    }
}
