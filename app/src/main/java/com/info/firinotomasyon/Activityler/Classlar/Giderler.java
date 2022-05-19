package com.info.firinotomasyon.Activityler.Classlar;

public class Giderler {
    private int islem_id;
    private String tarih;
    private String gider_ad;
    private double gider_tutar;

    public Giderler() {
    }

    public Giderler( String tarih, String gider_ad, double gider_tutar) {
        this.tarih = tarih;
        this.gider_ad = gider_ad;
        this.gider_tutar = gider_tutar;
    }

    public int getIslem_id() {
        return islem_id;
    }

    public void setIslem_id(int islem_id) {
        this.islem_id = islem_id;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getGider_ad() {
        return gider_ad;
    }

    public void setGider_ad(String gider_ad) {
        this.gider_ad = gider_ad;
    }

    public double getGider_tutar() {
        return gider_tutar;
    }

    public void setGider_tutar(double gider_tutar) {
        this.gider_tutar = gider_tutar;
    }
}
