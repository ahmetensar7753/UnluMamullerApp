package com.info.firinotomasyon.Activityler.Classlar;

public class TezgahRapor {
    private int rapor_id;
    private String rapor_tarih;
    private Double nakit_tutar;
    private Double kart_tutar;
    private int bayat_adet;

    public TezgahRapor() {
    }

    public TezgahRapor(Double nakit_tutar, Double kart_tutar, int bayat_adet) {
        this.nakit_tutar = nakit_tutar;
        this.kart_tutar = kart_tutar;
        this.bayat_adet = bayat_adet;
    }

    public int getRapor_id() {
        return rapor_id;
    }

    public void setRapor_id(int rapor_id) {
        this.rapor_id = rapor_id;
    }

    public String getRapor_tarih() {
        return rapor_tarih;
    }

    public void setRapor_tarih(String rapor_tarih) {
        this.rapor_tarih = rapor_tarih;
    }

    public Double getNakit_tutar() {
        return nakit_tutar;
    }

    public void setNakit_tutar(Double nakit_tutar) {
        this.nakit_tutar = nakit_tutar;
    }

    public Double getKart_tutar() {
        return kart_tutar;
    }

    public void setKart_tutar(Double kart_tutar) {
        this.kart_tutar = kart_tutar;
    }

    public int getBayat_adet() {
        return bayat_adet;
    }

    public void setBayat_adet(int bayat_adet) {
        this.bayat_adet = bayat_adet;
    }
}
