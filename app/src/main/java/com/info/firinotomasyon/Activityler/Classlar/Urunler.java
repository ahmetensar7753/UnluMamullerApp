package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Build;

import java.util.Objects;

public class Urunler {
    private int urun_id;
    private String urun_ad;
    private String urun_resim_ad;
    private Double urun_fiyat;
    private double urun_adet = 0.0;
    private String kimlik = "tane";

        public String getKimlik() {
            return kimlik;
        }

        public void setKimlik(String kimlik) {
            this.kimlik = kimlik;
        }

        public Urunler() {
    }

    public Urunler(String urun_ad, String urun_resim_ad, Double urun_fiyat) {

        this.urun_ad = urun_ad;
        this.urun_resim_ad = urun_resim_ad;
        this.urun_fiyat = urun_fiyat;
    }

    public int getUrun_id() {
        return urun_id;
    }

    public void setUrun_id(int urun_id) {
        this.urun_id = urun_id;
    }

        public double getUrun_adet() {
            return urun_adet;
        }

        public void setUrun_adet(double urun_adet) {
            this.urun_adet = urun_adet;
        }

        public String getUrun_ad() {
        return urun_ad;
    }

    public void setUrun_ad(String urun_ad) {
        this.urun_ad = urun_ad;
    }

    public String getUrun_resim_ad() {
        return urun_resim_ad;
    }

    public void setUrun_resim_ad(String urun_resim_ad) {
        this.urun_resim_ad = urun_resim_ad;
    }

    public Double getUrun_fiyat() {
        return urun_fiyat;
    }

    public void setUrun_fiyat(Double urun_fiyat) {
        this.urun_fiyat = urun_fiyat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Urunler that = (Urunler) o;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(urun_ad, that.urun_ad);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.hash(urun_ad);
        }
        return 0;
    }

}
