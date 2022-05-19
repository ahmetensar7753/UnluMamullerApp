
package com.info.firinotomasyon.Activityler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.Activityler.RvAdapters.SevkiyatMusteriAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.SevkiyatMusteriUrunlerAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SevkiyatMusteriActivity extends AppCompatActivity  {

    //GOSEL NESNE TANIMLARI
    private TextView textView_musteri_ad,textView_musteri_tarih_saat;
    private RecyclerView rv_musteri_urunler;
    private Button buttonMusteriGunlukGeriDon,buttonMusteriBorcKaydet,buttonMusteriOdemeYap;



    public static SevkiyatMusteriler gelenMusteri ;
    private ArrayList<SevkiyatUrunler> odemedigiUrunler;
    private SevkiyatMusteriUrunlerAdapter sevkiyatMusteriUrunlerAdapter;
    private ArrayList<SevkiyatUrunler> aldıgıUrunler;
    private double toplamEklenecekTutar = 0.0;

    // HATADAN KAÇIŞ İÇİN OLAN tanımlar null pointer exep.
    private ArrayList<SevkiyatUrunler> bos = new ArrayList<>();
    private SevkiyatUrunler bosUrun = new SevkiyatUrunler("yeniMusteri",0.0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevkiyat_musteri);

        gelenMusteri =  SevkiyatMusteriAdapter.tiklananMusteri;
        aldigiUrunlerCek(gelenMusteri.getMusteriId(),gelenMusteri);



        textView_musteri_ad = findViewById(R.id.textView_musteri_ad);
        textView_musteri_tarih_saat = findViewById(R.id.textView_musteri_tarih_saat);
        rv_musteri_urunler = findViewById(R.id.rv_musteri_urunler);
        buttonMusteriGunlukGeriDon = findViewById(R.id.buttonMusteriGunlukGeriDon);
        buttonMusteriBorcKaydet = findViewById(R.id.buttonMusteriBorcKaydet);
        buttonMusteriOdemeYap = findViewById(R.id.buttonMusteriOdemeYap);

        SimpleDateFormat sekil = new SimpleDateFormat("dd/MM/yyyy");
        Date tarih = new Date();

        textView_musteri_tarih_saat.setText(String.valueOf(sekil.format(tarih)));
        textView_musteri_ad.setText(gelenMusteri.getMusteriAd());

        // HATADAN KAÇIŞ İÇİN OLAN BLOK null pointer exep.
        bos.add(bosUrun);
        gelenMusteri.setMusteriOdemedigiUrunlerList(bos);

        rv_musteri_urunler.setHasFixedSize(true);
        rv_musteri_urunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        /*sevkiyatMusteriUrunlerAdapter = new SevkiyatMusteriUrunlerAdapter(SevkiyatMusteriActivity.this,gelenMusteri.getMusteriAldıgıUrunlerList());
        rv_musteri_urunler.setAdapter(sevkiyatMusteriUrunlerAdapter);*/



        buttonMusteriGunlukGeriDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SevkiyatMusteriActivity.this,SevkiyatActivity.class));
                finish();

            }
        });

        buttonMusteriBorcKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gelenMusteri.setMusteriGunlukBorc(Double.parseDouble(textViewGunlukToplam.getText().toString()));
                //gelenMusteri.setMusteriToplamBorc(Double.parseDouble(textViewGunlukToplam.getText().toString())+gelenMusteri.getMusteriToplamBorc());
                gelenMusteri.setMusteriOdemedigiUrunlerList(gelenMusteri.getMusteriAldıgıUrunlerList());
                System.out.println(gelenMusteri.getMusteriToplamBorc());


                AlertDialog.Builder ad = new AlertDialog.Builder(SevkiyatMusteriActivity.this);

                for (SevkiyatUrunler item : gelenMusteri.getMusteriOdemedigiUrunlerList()){
                    toplamEklenecekTutar = toplamEklenecekTutar+item.getToplam_fiyat();
                }

                ad.setTitle("Eklenecek Toplam Borç");
                ad.setMessage(String.valueOf(toplamEklenecekTutar)+" TL");

                ad.setPositiveButton("ONAYLA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        gelenMusteri.setMusteriToplamBorc(gelenMusteri.getMusteriToplamBorc()+toplamEklenecekTutar);
                        System.out.println(gelenMusteri.getMusteriToplamBorc()+"-----------");


                        odemedigiUrunler = new ArrayList<>(gelenMusteri.getMusteriOdemedigiUrunlerList());

                        SimpleDateFormat sekil = new SimpleDateFormat("yyyy-MM-dd");
                        Date tarih = new Date();

                        //BURADA MUSTERİNİN ALDIGI URUNLER ÖDEMEDİĞİ URUNLERE KAYDEDİLİYOR.
                        for (SevkiyatUrunler item : odemedigiUrunler){
                            odemedigiUrunlerKaydet(gelenMusteri.getMusteriId(),item.getUrun_ad(),item.getUrun_adet(),item.getUrun_iade(),item.getUrun_fiyat(),item.getToplam_fiyat(),String.valueOf(sekil.format(tarih)));
                         }

                        // VE ARDINDAN BORCU GÜNCELLENİYOR.
                        borcGuncelle(gelenMusteri.getMusteriId(),gelenMusteri.getMusteriToplamBorc());

                        gelenMusteri.getMusteriOdemedigiUrunlerList().clear();
                    }
                });
                ad.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                ad.create().show();


            }
        });

        buttonMusteriOdemeYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SevkiyatMusteriActivity.this,SevkiyatMusteriOdemeActivity.class));
                finish();
            }
        });




    }

    public void odemedigiUrunlerKaydet(int musteri_id,String urun_ad,int urun_adet,int urun_iade,Double aldigi_fiyat,Double toplam_fiyat,String aldigi_tarih){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/odemedigi_urunler_insert.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("sevkiyatSatisKaydet",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("musteri_id",String.valueOf(musteri_id));
                params.put("urun_ad",urun_ad);
                params.put("urun_adet",String.valueOf(urun_adet));
                params.put("urun_iade",String.valueOf(urun_iade));
                params.put("aldigi_fiyat",String.valueOf(aldigi_fiyat));
                params.put("toplam_fiyat",String.valueOf(toplam_fiyat));
                params.put("aldigi_tarih",aldigi_tarih);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);
    }

    public void borcGuncelle(int musteri_id,Double toplam_borc){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/borc_update.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("borcGuncelleResponse",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("musteri_id",String.valueOf(musteri_id));
                params.put("toplam_borc",String.valueOf(toplam_borc));

                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SevkiyatMusteriActivity.this,SevkiyatActivity.class));
    }

    public void aldigiUrunlerCek(int musteri_id, SevkiyatMusteriler musteri){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/a.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                aldıgıUrunler = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray aldıgıUrunList = jsonObject.getJSONArray("musteri_aldigi_urunler");

                    for (int i = 0;i<aldıgıUrunList.length();i++){
                        JSONObject k = aldıgıUrunList.getJSONObject(i);
                        String urun_ad = k.getString("urun_ad");
                        Integer urun_adet = k.getInt("urun_adet");
                        Double aldigi_fiyat = k.getDouble("aldigi_fiyat");
                        SevkiyatUrunler urun = new SevkiyatUrunler(urun_ad,aldigi_fiyat);
                        urun.setUrun_adet(urun_adet);
                        aldıgıUrunler.add(urun);
                    }
                    for (SevkiyatUrunler urun : aldıgıUrunler){
                        urun.setToplam_fiyat((urun.getUrun_adet()-urun.getUrun_iade())*urun.getUrun_fiyat());
                    }
                    musteri.setMusteriAldıgıUrunlerList(aldıgıUrunler);

                    sevkiyatMusteriUrunlerAdapter = new SevkiyatMusteriUrunlerAdapter(SevkiyatMusteriActivity.this,musteri.getMusteriAldıgıUrunlerList());
                    rv_musteri_urunler.setAdapter(sevkiyatMusteriUrunlerAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("musteri_id",String.valueOf(musteri_id));

                return params;
            }
        };
        Volley.newRequestQueue(SevkiyatMusteriActivity.this).add(istek);
    }
}