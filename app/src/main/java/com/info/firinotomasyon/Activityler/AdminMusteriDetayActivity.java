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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.Activityler.RvAdapters.AdminMusteriDetayOdemedikleriAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.AdminMusterilerAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminMusteriDetayActivity extends AppCompatActivity {

    //GORSEL NESNE TANIMLARI
    private TextView editTextAdminMusteriDetayAd,textViewAdminMusteriDetayId,editTextAdminMusteriDetayTel,editTextAdminMusteriDetayAdres,textViewAdminMusteriDetayToplamBorc,textViewAdminMusteriDetaySira;
    private RecyclerView rvAdminMusteriDetayOdemedikleri;
    private Button buttonMusteriDetayAldigiGec,button,buttonMusteriSil;


    private SevkiyatMusteriler gelenMusteri;
    private ArrayList<SevkiyatUrunler> odemedigiUrunList;
    private AdminMusteriDetayOdemedikleriAdapter adapter0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_musteri_detay);

        gelenMusteri = AdminMusterilerAdapter.tiklananMusteri; //TIKLANAN MUSTERİ ALINIYOR.

        //GORSEL NESNE BAĞLAMALARI
        editTextAdminMusteriDetayAd = findViewById(R.id.editTextAdminMusteriDetayAd);
        textViewAdminMusteriDetayId = findViewById(R.id.textViewAdminMusteriDetayId);
        editTextAdminMusteriDetayTel = findViewById(R.id.editTextAdminMusteriDetayTel);
        editTextAdminMusteriDetayAdres = findViewById(R.id.editTextAdminMusteriDetayAdres);
        textViewAdminMusteriDetayToplamBorc = findViewById(R.id.textViewAdminMusteriDetayToplamBorc);
        textViewAdminMusteriDetaySira = findViewById(R.id.textViewAdminMusteriDetaySira);
        rvAdminMusteriDetayOdemedikleri = findViewById(R.id.rvAdminMusteriDetayOdemedikleri);
        buttonMusteriDetayAldigiGec = findViewById(R.id.buttonMusteriDetayAldigiGec);
        button = findViewById(R.id.button);
        buttonMusteriSil = findViewById(R.id.buttonMusteriSil);

        editTextAdminMusteriDetayAd.setText(gelenMusteri.getMusteriAd());
        textViewAdminMusteriDetayId.setText(String.valueOf(gelenMusteri.getMusteriId()));
        editTextAdminMusteriDetayTel.setText(gelenMusteri.getMusteriTelefon());
        editTextAdminMusteriDetayAdres.setText(gelenMusteri.getMusteriAdres());
        textViewAdminMusteriDetayToplamBorc.setText(String.valueOf(gelenMusteri.getMusteriToplamBorc()));
        textViewAdminMusteriDetaySira.setText(String.valueOf(gelenMusteri.getMusteriSira()));


        rvAdminMusteriDetayOdemedikleri.setHasFixedSize(true);
        rvAdminMusteriDetayOdemedikleri.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        odemedigiCek(gelenMusteri.getMusteriId());

        buttonMusteriDetayAldigiGec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMusteriDetayActivity.this,AdminMusteriAldigiUrunlerActivity.class);
                intent.putExtra("musteriId",textViewAdminMusteriDetayId.getText().toString());
                intent.putExtra("sira",textViewAdminMusteriDetaySira.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                musteriGuncelle(Integer.parseInt(textViewAdminMusteriDetayId.getText().toString())
                        ,editTextAdminMusteriDetayAd.getText().toString(),editTextAdminMusteriDetayTel.getText().toString(),editTextAdminMusteriDetayAdres.getText().toString());

            }
        });

        buttonMusteriSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ad = new AlertDialog.Builder(AdminMusteriDetayActivity.this);
                ad.setTitle("MÜŞTERİ SİLİNSİN Mİ ?");

                ad.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        musteriSil(gelenMusteri.getMusteriId());
                        Toast.makeText(AdminMusteriDetayActivity.this,"Müşteri Silindi",Toast.LENGTH_SHORT).show();
                        onBackPressed();

                    }
                });
                ad.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //boş
                    }
                });
                ad.create().show();

            }
        });




    }

    public void odemedigiCek(int musteri_id){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/odemedigi_urunleri_cek_2.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                odemedigiUrunList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray odemedikleri = jsonObject.getJSONArray("musteri_odemedigi_urunler");

                    for (int i = 0;i<odemedikleri.length();i++){
                        JSONObject k = odemedikleri.getJSONObject(i);
                        String urun_ad = k.getString("urun_ad");
                        int urun_adet = k.getInt("urun_adet");
                        int urun_iade = k.getInt("urun_iade");
                        Double aldigi_fiyat = k.getDouble("aldigi_fiyat");
                        Double toplam_fiyat = k.getDouble("toplam_fiyat");
                        String aldigi_tarih = k.getString("aldigi_tarih");

                        SevkiyatUrunler urun = new SevkiyatUrunler(urun_ad,aldigi_fiyat);
                        urun.setUrun_adet(urun_adet);
                        urun.setUrun_iade(urun_iade);
                        urun.setToplam_fiyat(toplam_fiyat);
                        urun.setAlindigi_tarih(aldigi_tarih);

                        odemedigiUrunList.add(urun);

                    }

                    adapter0 = new AdminMusteriDetayOdemedikleriAdapter(AdminMusteriDetayActivity.this,odemedigiUrunList);
                    rvAdminMusteriDetayOdemedikleri.setAdapter(adapter0);





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
        Volley.newRequestQueue(AdminMusteriDetayActivity.this).add(istek);
    }

    public void musteriGuncelle(int id,String ad,String tel,String adres){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/musteri_update.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("mstrGncllRspns",response);
                Toast.makeText(AdminMusteriDetayActivity.this,"Müşteri Bilgileri Güncellendi.",Toast.LENGTH_SHORT).show();
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
                params.put("musteri_ad",ad);
                params.put("musteri_tel",tel);
                params.put("musteri_adres",adres);

                return params;

            }
        };
        Volley.newRequestQueue(AdminMusteriDetayActivity.this).add(istek);
    }

    public void musteriSil(int musteri_id){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/delete_musteri.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("mstrSilRspns",response);

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
        Volley.newRequestQueue(AdminMusteriDetayActivity.this).add(istek);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminMusteriDetayActivity.this,AdminMusteriActivity.class));
    }
}