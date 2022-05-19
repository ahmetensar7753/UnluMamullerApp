package com.info.firinotomasyon.Activityler.Classlar;

public class TezgahMusteriler {
    private int musteri_id;
    private String musteri_ad_soyad;
    private String musteri_telefon;
    private String son_odeme_tarihi;
    private Double guncel_borc;
    private String musteri_adres;



    public TezgahMusteriler() {
    }

    public TezgahMusteriler(String musteri_ad_soyad, String musteri_telefon, String son_odeme_tarihi, Double guncel_borc,String musteri_adres) {
        this.musteri_ad_soyad = musteri_ad_soyad;
        this.musteri_telefon = musteri_telefon;
        this.son_odeme_tarihi = son_odeme_tarihi;
        this.guncel_borc = guncel_borc;
        this.musteri_adres = musteri_adres;
    }

    public int getMusteri_id() {
        return musteri_id;
    }

    public void setMusteri_id(int musteri_id) {
        this.musteri_id = musteri_id;
    }

    public String getMusteri_ad_soyad() {
        return musteri_ad_soyad;
    }

    public void setMusteri_ad_soyad(String musteri_ad_soyad) {
        this.musteri_ad_soyad = musteri_ad_soyad;
    }

    public String getMusteri_telefon() {
        return musteri_telefon;
    }

    public void setMusteri_telefon(String musteri_telefon) {
        this.musteri_telefon = musteri_telefon;
    }

    public String getSon_odeme_tarihi() {
        return son_odeme_tarihi;
    }

    public void setSon_odeme_tarihi(String son_odeme_tarihi) {
        this.son_odeme_tarihi = son_odeme_tarihi;
    }

    public Double getGuncel_borc() {
        return guncel_borc;
    }

    public void setGuncel_borc(Double guncel_borc) {
        this.guncel_borc = guncel_borc;
    }
    public String getMusteri_adres() {
        return musteri_adres;
    }

    public void setMusteri_adres(String musteri_adres) {
        this.musteri_adres = musteri_adres;
    }
}
