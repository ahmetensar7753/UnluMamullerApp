package com.info.firinotomasyon.Activityler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.Activityler.RvAdapters.AdminUrunlerAdapter;
import com.info.firinotomasyon.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UrunDuzenleActivity extends AppCompatActivity {

    //GORSEL NESNE TANIMLARI
    private EditText editTextAdminUrunDuzenleAd,editTextAdminUrunDuzenleResimAd,editTextAdminUrunDuzenleFiyat;
    private Button buttonUrunDuzenleKaydet;
    private TextView textViewAdminUrunDuzenleId;


    private Urunler gelenUrun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_duzenle);

        gelenUrun = AdminUrunlerAdapter.tiklananUrun;  //GELEN URUN ATAMASI STATİC OLARAK ALINIP YAPILIYOR.

        //GORSEL NESNE BAĞLANMASI
        textViewAdminUrunDuzenleId = findViewById(R.id.textViewAdminUrunDuzenleId);
        editTextAdminUrunDuzenleAd = findViewById(R.id.editTextAdminUrunDuzenleAd);
        editTextAdminUrunDuzenleResimAd = findViewById(R.id.editTextAdminUrunDuzenleResimAd);
        editTextAdminUrunDuzenleFiyat = findViewById(R.id.editTextAdminUrunDuzenleFiyat);
        buttonUrunDuzenleKaydet = findViewById(R.id.buttonUrunDuzenleKaydet);

        textViewAdminUrunDuzenleId.setText(String.valueOf(gelenUrun.getUrun_id()));
        editTextAdminUrunDuzenleAd.setText(gelenUrun.getUrun_ad());
        editTextAdminUrunDuzenleResimAd.setText(gelenUrun.getUrun_resim_ad());
        editTextAdminUrunDuzenleFiyat.setText(String.valueOf(gelenUrun.getUrun_fiyat()));


        buttonUrunDuzenleKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (gelenUrun.getKimlik().equals("tane")){

                    urunGuncelle(gelenUrun.getUrun_id()
                            ,editTextAdminUrunDuzenleAd.getText().toString()
                            ,editTextAdminUrunDuzenleResimAd.getText().toString()
                            ,Double.parseDouble(editTextAdminUrunDuzenleFiyat.getText().toString()));

                    Toast.makeText(UrunDuzenleActivity.this,"Değişiklik Kaydedildi.",Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }else {

                    gramajUrunGuncelle(gelenUrun.getUrun_id()
                            ,editTextAdminUrunDuzenleAd.getText().toString()
                            ,editTextAdminUrunDuzenleResimAd.getText().toString()
                            ,Double.parseDouble(editTextAdminUrunDuzenleFiyat.getText().toString()));

                    Toast.makeText(UrunDuzenleActivity.this,"Değişiklik Kaydedildi.",Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }
            }
        });




    }

    public void urunGuncelle(int urun_id,String urun_ad,String urun_resim_ad,Double urun_fiyat){
        String url = "https://kristalekmek.com/urunler/update_urun.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("urunGncllRspns",response);
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

                params.put("urun_id",String.valueOf(urun_id));
                params.put("urun_ad",urun_ad);
                params.put("urun_resim_ad",urun_resim_ad);
                params.put("urun_fiyat",String.valueOf(urun_fiyat));

                return params;
            }
        };
        Volley.newRequestQueue(UrunDuzenleActivity.this).add(istek);
    }

    public void gramajUrunGuncelle(int urun_id,String urun_ad,String urun_resim_ad,Double urun_fiyat){
        String url = "https://kristalekmek.com/gramaj_urunler/update_urun.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("GrmjUrunGncllRspns",response);
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

                params.put("urun_id",String.valueOf(urun_id));
                params.put("urun_ad",urun_ad);
                params.put("urun_resim_ad",urun_resim_ad);
                params.put("urun_fiyat",String.valueOf(urun_fiyat));

                return params;
            }
        };
        Volley.newRequestQueue(UrunDuzenleActivity.this).add(istek);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UrunDuzenleActivity.this,AdminUrunlerActivity.class));
    }



}