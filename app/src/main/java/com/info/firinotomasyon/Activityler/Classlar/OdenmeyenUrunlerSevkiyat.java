package com.info.firinotomasyon.Activityler.Classlar;

public class OdenmeyenUrunlerSevkiyat {
    private String urun_ad;
    private Integer urun_adet;
    private Integer urun_iade;
    private Double toplam_fiyat;

    public OdenmeyenUrunlerSevkiyat() {
    }

    public OdenmeyenUrunlerSevkiyat(String urun_ad, Integer urun_adet, Integer urun_iade, Double toplam_fiyat) {
        this.urun_ad = urun_ad;
        this.urun_adet = urun_adet;
        this.urun_iade = urun_iade;
        this.toplam_fiyat = toplam_fiyat;
    }

    public String getUrun_ad() {
        return urun_ad;
    }

    public void setUrun_ad(String urun_ad) {
        this.urun_ad = urun_ad;
    }

    public Integer getUrun_adet() {
        return urun_adet;
    }

    public void setUrun_adet(Integer urun_adet) {
        this.urun_adet = urun_adet;
    }

    public Integer getUrun_iade() {
        return urun_iade;
    }

    public void setUrun_iade(Integer urun_iade) {
        this.urun_iade = urun_iade;
    }

    public Double getToplam_fiyat() {
        return toplam_fiyat;
    }

    public void setToplam_fiyat(Double toplam_fiyat) {
        this.toplam_fiyat = toplam_fiyat;
    }
}
