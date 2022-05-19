package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;

public class SevkiyatMusteriler {

    private int musteriId;
    private int musteriSira;
    private String musteriAd;
    private String musteriTelefon;
    private String musteriAdres;
    private ArrayList<SevkiyatUrunler> musteriAldıgıUrunlerList  ;
    private ArrayList<SevkiyatUrunler> musteriOdemedigiUrunlerList;
    private double musteriToplamBorc=0.0;
    private double musteriGunlukBorc;



    public SevkiyatMusteriler() {
    }

    public SevkiyatMusteriler( String musteriAd, String musteriTelefon, String musteriAdres) {

        this.musteriAd = musteriAd;
        this.musteriTelefon = musteriTelefon;
        this.musteriAdres = musteriAdres;
    }

    public int getMusteriId() {
        return musteriId;
    }

    public void setMusteriId(int musteriId) {
        this.musteriId = musteriId;
    }

    public int getMusteriSira() {
        return musteriSira;
    }

    public void setMusteriSira(int musteriSira) {
        this.musteriSira = musteriSira;
    }

    public String getMusteriAd() {
        return musteriAd;
    }

    public void setMusteriAd(String musteriAd) {
        this.musteriAd = musteriAd;
    }

    public String getMusteriTelefon() {
        return musteriTelefon;
    }

    public void setMusteriTelefon(String musteriTelefon) {
        this.musteriTelefon = musteriTelefon;
    }

    public String getMusteriAdres() {
        return musteriAdres;
    }

    public void setMusteriAdres(String musteriAdres) {
        this.musteriAdres = musteriAdres;
    }

    public ArrayList<SevkiyatUrunler> getMusteriAldıgıUrunlerList() {
        return musteriAldıgıUrunlerList;
    }

    public void setMusteriAldıgıUrunlerList(ArrayList<SevkiyatUrunler> musteriAldıgıUrunlerList) {
        this.musteriAldıgıUrunlerList = musteriAldıgıUrunlerList;
    }

    public double getMusteriToplamBorc() {
        return musteriToplamBorc;
    }

    public void setMusteriToplamBorc(double musteriToplamBorc) {
        this.musteriToplamBorc = musteriToplamBorc;
    }

    public double getMusteriGunlukBorc() {
        return musteriGunlukBorc;
    }

    public void setMusteriGunlukBorc(double musteriGunlukBorc) {
        this.musteriGunlukBorc = musteriGunlukBorc;
    }
    public ArrayList<SevkiyatUrunler> getMusteriOdemedigiUrunlerList() {
        return musteriOdemedigiUrunlerList;
    }

    public void setMusteriOdemedigiUrunlerList(ArrayList<SevkiyatUrunler> musteriOdemedigiUrunlerList) {
        this.musteriOdemedigiUrunlerList = musteriOdemedigiUrunlerList;
    }


}
