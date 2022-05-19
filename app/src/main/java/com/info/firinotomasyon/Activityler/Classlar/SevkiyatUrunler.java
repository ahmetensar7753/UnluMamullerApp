package com.info.firinotomasyon.Activityler.Classlar;

public class SevkiyatUrunler {
    private int urun_id;
    private String urun_ad="";
    private int urun_adet=0;
    private double urun_fiyat=0;
    private int urun_iade=0;
    private double toplam_fiyat=0;
    private int satis_id;
    private String alindigi_tarih;
    private String odendigi_tarih;
    private boolean seciliMi;

    public void setSeciliMi(boolean seciliMi) {
        this.seciliMi = seciliMi;
    }

    public boolean getSeciliMi() {
        return seciliMi;
    }


    public String getOdendigi_tarih() {
        return odendigi_tarih;
    }

    public void setOdendigi_tarih(String odendigi_tarih) {
        this.odendigi_tarih = odendigi_tarih;
    }

    public String getAlindigi_tarih() {
        return alindigi_tarih;
    }

    public void setAlindigi_tarih(String alindigi_tarih) {
        this.alindigi_tarih = alindigi_tarih;
    }

    public int getSatis_id() {
        return satis_id;
    }

    public void setSatis_id(int satis_id) {
        this.satis_id = satis_id;
    }

    public double getToplam_fiyat() {
        return toplam_fiyat;
    }

    public void setToplam_fiyat(double toplam_fiyat) {
        this.toplam_fiyat = toplam_fiyat;
    }

    public SevkiyatUrunler() {
    }

    public SevkiyatUrunler( String urun_ad, double urun_fiyat) {
        this.urun_ad = urun_ad;
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

    public int getUrun_adet() {
        return urun_adet;
    }

    public void setUrun_adet(int urun_adet) {
        this.urun_adet = urun_adet;
    }

    public double getUrun_fiyat() {
        return urun_fiyat;
    }

    public void setUrun_fiyat(double urun_fiyat) {
        this.urun_fiyat = urun_fiyat;
    }

    public int getUrun_iade() {
        return urun_iade;
    }

    public void setUrun_iade(int urun_iade) {
        this.urun_iade = urun_iade;
    }
}
