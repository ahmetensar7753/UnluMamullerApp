package com.info.firinotomasyon.Activityler.Classlar;

public class Personel {
    private int personel_id;
    private String personel_ad;
    private String personel_tel;
    private String personel_sifre;
    private String personel_alan;

    public Personel() {
    }

    public Personel(String personel_ad, String personel_tel, String personel_sifre, String personel_alan) {
        this.personel_ad = personel_ad;
        this.personel_tel = personel_tel;
        this.personel_sifre = personel_sifre;
        this.personel_alan = personel_alan;
    }

    public int getPersonel_id() {
        return personel_id;
    }

    public void setPersonel_id(int personel_id) {
        this.personel_id = personel_id;
    }

    public String getPersonel_ad() {
        return personel_ad;
    }

    public void setPersonel_ad(String personel_ad) {
        this.personel_ad = personel_ad;
    }

    public String getPersonel_tel() {
        return personel_tel;
    }

    public void setPersonel_tel(String personel_tel) {
        this.personel_tel = personel_tel;
    }

    public String getPersonel_sifre() {
        return personel_sifre;
    }

    public void setPersonel_sifre(String personel_sifre) {
        this.personel_sifre = personel_sifre;
    }

    public String getPersonel_alan() {
        return personel_alan;
    }

    public void setPersonel_alan(String personel_alan) {
        this.personel_alan = personel_alan;
    }
}
