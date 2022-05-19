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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.Activityler.RvAdapters.AdminAldigiUrunlerAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminMusteriAldigiUrunlerActivity extends AppCompatActivity {

    private RecyclerView rvAdminAldigiUrunler;
    private Button buttonAdminAldigiUrunlerKaydet;
    private TextView textViewAdminAldigiUrunlerMusteriId,textViewAdminAldigiUrunlerMusteriSira;
    private FloatingActionButton fabAdminAldigiUrunEkle;


    private ArrayList<SevkiyatUrunler> vtDenCekilenAldigiUrunler;
    private AdminAldigiUrunlerAdapter adapter00;

    private ArrayList<String> spinnerListe;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_musteri_aldigi_urunler);

        rvAdminAldigiUrunler = findViewById(R.id.rvAdminAldigiUrunler);
        buttonAdminAldigiUrunlerKaydet = findViewById(R.id.buttonAdminAldigiUrunlerKaydet);
        textViewAdminAldigiUrunlerMusteriId = findViewById(R.id.textViewAdminAldigiUrunlerMusteriId);
        textViewAdminAldigiUrunlerMusteriSira = findViewById(R.id.textViewAdminAldigiUrunlerMusteriSira);
        fabAdminAldigiUrunEkle = findViewById(R.id.fabAdminAldigiUrunEkle);

        Intent gelenIntent = getIntent();
        String musteri_id = gelenIntent.getStringExtra("musteriId");
        String musteri_sira = gelenIntent.getStringExtra("sira");

        textViewAdminAldigiUrunlerMusteriId.setText(musteri_id);
        textViewAdminAldigiUrunlerMusteriSira.setText(musteri_sira);

        rvAdminAldigiUrunler.setHasFixedSize(true);
        rvAdminAldigiUrunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        aldigiUrunlerCek(musteri_id);



        buttonAdminAldigiUrunlerKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<SevkiyatUrunler> kaydedilecekListe = new ArrayList<>(AdminAldigiUrunlerAdapter.disariyaGidenList);
                int musteriID = Integer.parseInt(textViewAdminAldigiUrunlerMusteriId.getText().toString());


                for (SevkiyatUrunler item : kaydedilecekListe){
                    urunGuncelle(musteriID,item.getUrun_ad(),item.getUrun_adet(),item.getUrun_fiyat());
                }

                Toast.makeText(AdminMusteriAldigiUrunlerActivity.this,"Güncelleme Kaydedildi.",Toast.LENGTH_SHORT).show();

                onBackPressed();

            }
        });

        fabAdminAldigiUrunEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                View tasarim = getLayoutInflater().inflate(R.layout.admin_aldigi_urun_ekle_alert,null);
                EditText editTextAdminAldigiUrunAdet = tasarim.findViewById(R.id.editTextAdminAldigiUrunAdet);
                EditText editTextAdminAldigiUrunFiyat = tasarim.findViewById(R.id.editTextAdminAldigiUrunFiyat);
                Spinner spinnerAldigiUrunSec = tasarim.findViewById(R.id.spinnerAldigiUrunSec);
                AlertDialog.Builder ab = new AlertDialog.Builder(AdminMusteriAldigiUrunlerActivity.this);

                ab.setTitle("Müşteri  Aldığı Ürün Ekle");
                ab.setView(tasarim);
                sevkiyatUrunlerCek(spinnerAldigiUrunSec);

                ab.setPositiveButton("EKLE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Integer musteri_id = Integer.parseInt(textViewAdminAldigiUrunlerMusteriId.getText().toString());
                        String urun_ad = spinnerListe.get(spinnerAldigiUrunSec.getSelectedItemPosition());
                        Integer urun_adet = Integer.parseInt(editTextAdminAldigiUrunAdet.getText().toString());
                        Double urun_fiyat = Double.parseDouble(editTextAdminAldigiUrunFiyat.getText().toString());
                        int musteri_sira = Integer.parseInt(textViewAdminAldigiUrunlerMusteriSira.getText().toString());

                        ArrayList<String> aldigiUrunAdList = new ArrayList<>();

                        for (SevkiyatUrunler item : vtDenCekilenAldigiUrunler){
                            aldigiUrunAdList.add(item.getUrun_ad());
                        }

                        if (aldigiUrunAdList.contains(urun_ad)){
                            Toast.makeText(AdminMusteriAldigiUrunlerActivity.this,"Ürünü Zaten Alıyor!",Toast.LENGTH_SHORT).show();
                        }else {
                            aldigiUrunEkle(musteri_id,urun_ad,urun_adet,urun_fiyat,musteri_sira);

                            Toast.makeText(AdminMusteriAldigiUrunlerActivity.this,"Ürün Eklendi.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                ab.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //boş
                    }
                });

                ab.create().show();


            }
        });




    }

    public void aldigiUrunlerCek(String musteri_id){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/a.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                vtDenCekilenAldigiUrunler = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray aldigiUrunler = jsonObject.getJSONArray("musteri_aldigi_urunler");

                    for (int i=0 ; i<aldigiUrunler.length();i++){
                        JSONObject k = aldigiUrunler.getJSONObject(i);

                        int musteri_id = k.getInt("musteri_id");
                        String urun_ad = k.getString("urun_ad");
                        int urun_adet = k.getInt("urun_adet");
                        Double aldigi_fiyat = k.getDouble("aldigi_fiyat");

                        SevkiyatUrunler urun = new SevkiyatUrunler(urun_ad,aldigi_fiyat);
                        urun.setUrun_adet(urun_adet);

                        vtDenCekilenAldigiUrunler.add(urun);

                    }

                    adapter00 = new AdminAldigiUrunlerAdapter(AdminMusteriAldigiUrunlerActivity.this,vtDenCekilenAldigiUrunler,Integer.parseInt(musteri_id));
                    rvAdminAldigiUrunler.setAdapter(adapter00);


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

                params.put("musteri_id",musteri_id);

                return params;
            }
        };
        Volley.newRequestQueue(AdminMusteriAldigiUrunlerActivity.this).add(istek);
    }

    public void urunGuncelle(int musteri_id,String urun_ad,int urun_adet,Double aldigi_fiyat){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/aldigi_urunler_guncelle_admin.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("urunGnclleRespns",response);
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
                params.put("aldigi_fiyat",String.valueOf(aldigi_fiyat));

                return params;
            }
        };
        Volley.newRequestQueue(AdminMusteriAldigiUrunlerActivity.this).add(istek);
    }

    public void sevkiyatUrunlerCek(Spinner spinner){
        String url = "https://kristalekmek.com/sevkiyat_urunler/urunler_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                spinnerListe = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray sevkiyatUrunler = jsonObject.getJSONArray("sevkiyat_urunler");

                    for (int i = 0 ;i<sevkiyatUrunler.length();i++){
                        JSONObject k = sevkiyatUrunler.getJSONObject(i);
                        int urun_id = k.getInt("urun_id");
                        String urun_ad = k.getString("urun_ad");

                        spinnerListe.add(urun_ad);
                    }
                    spinnerAdapter = new ArrayAdapter<String>(AdminMusteriAldigiUrunlerActivity.this
                            , android.R.layout.simple_list_item_1
                            ,android.R.id.text1,spinnerListe);

                    spinner.setAdapter(spinnerAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(AdminMusteriAldigiUrunlerActivity.this).add(istek);
    }

    public void aldigiUrunEkle(int musteri_id,String urun_ad,int urun_adet,double aldigi_fiyat,int sira){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/aldigi_urun_ekle.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("aldigi_fiyat",String.valueOf(aldigi_fiyat));
                params.put("servis_sira",String.valueOf(sira));

                return params;
            }
        };
        Volley.newRequestQueue(AdminMusteriAldigiUrunlerActivity.this).add(istek);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminMusteriAldigiUrunlerActivity.this,AdminMusteriDetayActivity.class));
    }


}