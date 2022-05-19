package com.info.firinotomasyon.Activityler.Classlar;

public class NotClass {
    private String not_icerik;
    private String not_tarih;

    public NotClass() {
    }

    public NotClass(String not_icerik, String not_tarih) {
        this.not_icerik = not_icerik;
        this.not_tarih = not_tarih;
    }

    public String getNot_icerik() {
        return not_icerik;
    }

    public void setNot_icerik(String not_icerik) {
        this.not_icerik = not_icerik;
    }

    public String getNot_tarih() {
        return not_tarih;
    }

    public void setNot_tarih(String not_tarih) {
        this.not_tarih = not_tarih;
    }
}
