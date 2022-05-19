package com.info.firinotomasyon.Activityler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.OdenmeyenUrunlerSevkiyat;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.Activityler.RvAdapters.SevkiyatOdemeAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SevkiyatMusteriOdemeActivity extends AppCompatActivity {
    private SevkiyatMusteriler gelenMusteri = SevkiyatMusteriActivity.gelenMusteri;
    private ArrayList<SevkiyatUrunler> odemedigiListe;
    private SevkiyatOdemeAdapter adapter;
    private double toplam = 0.0;

    //GORSEL NESNE TANIMLARI
    private TextView textViewSevkiyatOdeAd,textViewSevkiyatOdeTarihSaat;
    public static TextView textViewSevkiyatOdemeToplam;
    private CheckBox checkBox;
    private  RecyclerView recyclerViewOde;
    private Button buttonSevkiyatOdemeOde,buttonSevkiyatOdemeGeri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevkiyat_musteri_odeme);

        //GORSEL NESNELERİN BAGLANMASİ
        textViewSevkiyatOdeAd = findViewById(R.id.textViewSevkiyatOdeAd);
        textViewSevkiyatOdeTarihSaat = findViewById(R.id.textViewSevkiyatOdeTarihSaat);
        textViewSevkiyatOdemeToplam = findViewById(R.id.textViewSevkiyatOdemeToplam);
        checkBox = findViewById(R.id.checkBox);
        recyclerViewOde = findViewById(R.id.recyclerViewOde);
        buttonSevkiyatOdemeOde = findViewById(R.id.buttonSevkiyatOdemeOde);
        buttonSevkiyatOdemeGeri = findViewById(R.id.buttonSevkiyatOdemeGeri);

        SimpleDateFormat sekil = new SimpleDateFormat("d/M/y");
        Date tarih = new Date();

        textViewSevkiyatOdeTarihSaat.setText(String.valueOf(sekil.format(tarih)));
        textViewSevkiyatOdeAd.setText(gelenMusteri.getMusteriAd());


        odemedigiUrunlerCek(gelenMusteri.getMusteriId());

        recyclerViewOde.setHasFixedSize(true);
        recyclerViewOde.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        toplam = 0.0;
                        SevkiyatOdemeAdapter.isaretliUrunler.clear();
                        for (SevkiyatUrunler u : odemedigiListe){
                            toplam = toplam + u.getToplam_fiyat();
                            SevkiyatOdemeAdapter.isaretliUrunler.add(u);
                        }
                        textViewSevkiyatOdemeToplam.setText(String.valueOf(toplam));
                    }else {
                        toplam = 0.0;
                        textViewSevkiyatOdemeToplam.setText(String.valueOf(toplam));
                        SevkiyatOdemeAdapter.isaretliUrunler.clear();
                    }
                    recyclerViewOde.setHasFixedSize(true);
                    recyclerViewOde.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    adapter = new SevkiyatOdemeAdapter(getApplicationContext(),odemedigiListe,isChecked);
                    recyclerViewOde.setAdapter(adapter);
            }
        });

        buttonSevkiyatOdemeOde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ab = new AlertDialog.Builder(SevkiyatMusteriOdemeActivity.this);
                ab.setTitle("!");
                ab.setMessage("Ödemeyi Onaylıyor Musun ?");

                ab.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SimpleDateFormat sekil = new SimpleDateFormat("yyyy-MM-dd");
                        Date tarih = new Date();

                        // ÖDENEN ÜRÜNLER ödenmeyenÜrünList'ten siliniyor.
                        ArrayList<SevkiyatUrunler> liste = new ArrayList<>(SevkiyatOdemeAdapter.isaretliUrunler); // burada işaretli olan ürünleri ayrı bir listeye aldım.

                        for (SevkiyatUrunler item : liste){
                            odedigiUrunlerSil(item.getSatis_id());
                            odenenUrunlerKaydet(gelenMusteri.getMusteriId(),item.getUrun_ad(),item.getUrun_adet(),item.getUrun_iade(),item.getToplam_fiyat(),String.valueOf(sekil.format(tarih)),item.getSatis_id());
                        }


                        gelenMusteri.setMusteriToplamBorc(gelenMusteri.getMusteriToplamBorc()-Double.parseDouble(textViewSevkiyatOdemeToplam.getText().toString()));

                        borcGuncelle(gelenMusteri.getMusteriId(),gelenMusteri.getMusteriToplamBorc());

                        Toast.makeText(SevkiyatMusteriOdemeActivity.this,"Ödeme Alındı.",Toast.LENGTH_SHORT).show();

                        // bu activity sonlandırılacak. bidahaki Girişinde odemedigi urunler guncellenmis olarak gelicek.
                        startActivity(new Intent(SevkiyatMusteriOdemeActivity.this,SevkiyatActivity.class));
                        finish();


                        SevkiyatOdemeAdapter.isaretliUrunler.clear();
                    }
                });
                ab.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // burada işlem yapılmayacak.

                    }
                });
                ab.create().show();

            }
        });
        buttonSevkiyatOdemeGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SevkiyatMusteriOdemeActivity.this,SevkiyatMusteriActivity.class));
                finish();
            }
        });

    }

    public void odemedigiUrunlerCek(int musteri_id){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/odemedigi_urunleri_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                odemedigiListe = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray odemedigiUrunList = jsonObject.getJSONArray("musteri_odemedigi_urunler");

                    for (int i = 0 ;i<odemedigiUrunList.length();i++){
                        JSONObject k = odemedigiUrunList.getJSONObject(i);

                        String urun_ad = k.getString("urun_ad");
                        int urun_adet = k.getInt("urun_adet");
                        int urun_iade = k.getInt("urun_iade");
                        Double aldigi_fiyat = k.getDouble("aldigi_fiyat");
                        Double toplam_fiyat = k.getDouble("toplam_fiyat");
                        int satis_id = k.getInt("satis_id");

                        SevkiyatUrunler item = new SevkiyatUrunler(urun_ad,aldigi_fiyat);
                        item.setUrun_adet(urun_adet);
                        item.setUrun_iade(urun_iade);
                        item.setToplam_fiyat(toplam_fiyat);
                        item.setSatis_id(satis_id);

                        odemedigiListe.add(item);

                    }

                    adapter = new SevkiyatOdemeAdapter(getApplicationContext(),odemedigiListe,false);
                    recyclerViewOde.setAdapter(adapter);


                } catch (JSONException e) {
                    Log.e("Hata","HATA FIRLATILDI-(yeniMusteri'de kayıt edilmeden ödeme kısmına girildi.)");
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
        Volley.newRequestQueue(this).add(istek);
    }

    public void odedigiUrunlerSil(int satis_id){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/odemedigi_urunler_delete.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("odenenUrunSilResponse",response);
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

                params.put("satis_id",String.valueOf(satis_id));

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

    public void odenenUrunlerKaydet(int musteri_id,String urun_ad,int urun_adet,int urun_iade,Double toplam_fiyat,String odedigi_tarih,int satis_id){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/odenen_urunler.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("odenenKaydetResponse",response);
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
                params.put("toplam_fiyat",String.valueOf(toplam_fiyat));
                params.put("odedigi_tarih",odedigi_tarih);
                params.put("satis_id",String.valueOf(satis_id));


                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SevkiyatMusteriOdemeActivity.this,SevkiyatMusteriActivity.class));
    }
}