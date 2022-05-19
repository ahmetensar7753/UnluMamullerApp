package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Build;

import java.util.Objects;

public class SecilenUrunler {
    private String resim_ad;
    private String ad;
    private int adet;

    public SecilenUrunler() {
    }

    public SecilenUrunler(String resim_ad, String ad, int adet) {
        this.resim_ad = resim_ad;
        this.ad = ad;
        this.adet = adet;
    }

    public String getResim_ad() {
        return resim_ad;
    }

    public void setResim_ad(String resim_ad) {
        this.resim_ad = resim_ad;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecilenUrunler that = (SecilenUrunler) o;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(ad, that.ad);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.hash(ad);
        }
        return 0;
    }
}
