package com.info.firinotomasyon.Activityler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.RvAdapters.SevkiyatMusteriSiraAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SevkiyatSiparisSirasiActivity extends AppCompatActivity {

    private RecyclerView rvSevkiyatSiraMusteriler;
    public static Button buttonSiparisSiraKaydet;
    private ArrayList<SevkiyatMusteriler> kaydedileceMusteriListe;
    private ArrayList<SevkiyatMusteriler> vtDenGelenMusteriList;
    private SevkiyatMusteriSiraAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevkiyat_siparis_sirasi);

        rvSevkiyatSiraMusteriler = findViewById(R.id.rvSevkiyatSiraMusteriler);
        buttonSiparisSiraKaydet = findViewById(R.id.buttonSiparisSiraKaydet);

        musteriCek();

        buttonSiparisSiraKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kaydedileceMusteriListe = new ArrayList<>(SevkiyatMusteriSiraAdapter.disariyaGidenList);

                for (SevkiyatMusteriler item : kaydedileceMusteriListe){
                    siraGuncelle(item.getMusteriAd(),item.getMusteriSira());
                    musteriAldigiUrunlerSiraGuncelle(item.getMusteriId(),item.getMusteriSira());
                }

                Toast.makeText(SevkiyatSiparisSirasiActivity.this,"Sıra GÜNCELLENDİ.",Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void musteriCek(){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/musteriler_ad_sira_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                vtDenGelenMusteriList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("sevkiyat_musteriler");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        String musteri_ad = k.getString("musteri_ad");
                        int servis_sira = k.getInt("servis_sira");
                        int musteri_id = k.getInt("musteri_id");

                        SevkiyatMusteriler musteri = new SevkiyatMusteriler(musteri_ad,"","");
                        musteri.setMusteriSira(servis_sira);
                        musteri.setMusteriId(musteri_id);

                        vtDenGelenMusteriList.add(musteri);
                    }


                    rvSevkiyatSiraMusteriler.setHasFixedSize(true);
                    rvSevkiyatSiraMusteriler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    adapter = new SevkiyatMusteriSiraAdapter(SevkiyatSiparisSirasiActivity.this,vtDenGelenMusteriList);
                    rvSevkiyatSiraMusteriler.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(SevkiyatSiparisSirasiActivity.this).add(istek);
    }

    public void siraGuncelle(String ad,int sira){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/ada_gore_sira_guncelle.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("sraGncllRspns",response);
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

                params.put("musteri_ad",ad);
                params.put("servis_sira",String.valueOf(sira));

                return params;
            }
        };
        Volley.newRequestQueue(SevkiyatSiparisSirasiActivity.this).add(istek);
    }

    public void musteriAldigiUrunlerSiraGuncelle(int id,int sira){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/update_musteri_aldigi_urunler_sira.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("mstrAldgUrnSiraGnclRspn",response);
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

                params.put("musteri_id",String.valueOf(id));
                params.put("servis_sira",String.valueOf(sira));

                return params;
            }
        };
        Volley.newRequestQueue(SevkiyatSiparisSirasiActivity.this).add(istek);
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SevkiyatSiparisSirasiActivity.this,SevkiyatActivity.class));
    }



}