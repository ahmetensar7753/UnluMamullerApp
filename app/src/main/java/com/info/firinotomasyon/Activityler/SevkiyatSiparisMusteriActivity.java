package com.info.firinotomasyon.Activityler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.Activityler.RvAdapters.SevkiyatSiparisMusteriAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.SevkiyatSiparisUrunlerAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SevkiyatSiparisMusteriActivity extends AppCompatActivity {
    //GORSEL NESNE TANIMLARI
    private TextView textViewSiparisMusteriAd,textViewSiparisBugunTarih;
    private RecyclerView rvSiparisMusteriUrunler;
    private Button buttonSiparisMusteriGeri,buttonSiparisMusteriSiparisKaydet;



    public static SevkiyatMusteriler gelen_musteri;
    private ArrayList<SevkiyatUrunler> sevkiyatUrunlerList;
    private SevkiyatSiparisUrunlerAdapter adapter;
    private ArrayList<SevkiyatUrunler> aldigiUrunlerGuncelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevkiyat_siparis_musteri);

        gelen_musteri = SevkiyatSiparisMusteriAdapter.tiklananMusteri;


        //GORSEL NESNE BAGLANTILARI
        textViewSiparisBugunTarih = findViewById(R.id.textViewSiparisBugunTarih);
        textViewSiparisMusteriAd = findViewById(R.id.textViewSiparisMusteriAd);
        rvSiparisMusteriUrunler = findViewById(R.id.rvSiparisMusteriUrunler);
        buttonSiparisMusteriGeri = findViewById(R.id.buttonSiparisMusteriGeri);
        buttonSiparisMusteriSiparisKaydet = findViewById(R.id.buttonSiparisMusteriSiparisKaydet);


        SimpleDateFormat sekil = new SimpleDateFormat("d/M/y");
        Date tarih = new Date();
        textViewSiparisBugunTarih.setText(String.valueOf(sekil.format(tarih)));

        urunlerCek(gelen_musteri.getMusteriId()); // sevkiyatUrunler VT'den çekiliyor.

        textViewSiparisMusteriAd.setText(gelen_musteri.getMusteriAd());


        //rv islemleri
        rvSiparisMusteriUrunler.setHasFixedSize(true);
        rvSiparisMusteriUrunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));






        buttonSiparisMusteriSiparisKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BURADA İÇERİSİNDE URUN ADEDİ 0 OLAN BÜTÜN ÜRÜNLER AYIKLANIP URUN ADEDİ OLAN URUNLER BAŞKA BİR LİSTEYE ALINIP VT'DA UPDATE YAPILIYOR.

                aldigiUrunlerGuncelList = new ArrayList<>();

                for (SevkiyatUrunler u1 : SevkiyatSiparisUrunlerAdapter.disariyaGidenList){
                        aldigiUrunlerGuncelList.add(u1);
                }

                for (SevkiyatUrunler item : aldigiUrunlerGuncelList){
                    aldigiUrunGuncelle(gelen_musteri.getMusteriId(),item.getUrun_ad(),item.getUrun_adet());
                }

                SevkiyatSiparisUrunlerAdapter.disariyaGidenList.clear();
                startActivity(new Intent(SevkiyatSiparisMusteriActivity.this,SevkiyatActivity.class));
                finish();

            }
        });

        buttonSiparisMusteriGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SevkiyatSiparisUrunlerAdapter.disariyaGidenList.clear();
                startActivity(new Intent(SevkiyatSiparisMusteriActivity.this,SevkiyatActivity.class));
                finish();
            }
        });

    }

    public void urunlerCek(int musteri_id){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/a.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                sevkiyatUrunlerList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray urunList = jsonObject.getJSONArray("musteri_aldigi_urunler");

                    for (int i=0;i<urunList.length();i++){
                        JSONObject k = urunList.getJSONObject(i);

                        String urun_ad = k.getString("urun_ad");
                        int urun_adet = k.getInt("urun_adet");
                        Double aldigi_fiyat = k.getDouble("aldigi_fiyat");

                        SevkiyatUrunler urun = new SevkiyatUrunler(urun_ad,aldigi_fiyat);
                        urun.setUrun_adet(urun_adet);


                        sevkiyatUrunlerList.add(urun);
                    }

                    adapter = new SevkiyatSiparisUrunlerAdapter(SevkiyatSiparisMusteriActivity.this,sevkiyatUrunlerList);
                    rvSiparisMusteriUrunler.setAdapter(adapter);


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
        Volley.newRequestQueue(SevkiyatSiparisMusteriActivity.this).add(istek);
    }



    public void aldigiUrunGuncelle(int musteri_id,String urun_ad,int urun_adet){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/aldigi_urunler_guncelle.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("aldgUrnGuncllRspns",response);

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
                params.put("urun_ad",String.valueOf(urun_ad));
                params.put("urun_adet",String.valueOf(urun_adet));

                return params;
            }
        };
        Volley.newRequestQueue(SevkiyatSiparisMusteriActivity.this).add(istek);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SevkiyatSiparisUrunlerAdapter.disariyaGidenList.clear();
        startActivity(new Intent(SevkiyatSiparisMusteriActivity.this,SevkiyatActivity.class));
    }
}