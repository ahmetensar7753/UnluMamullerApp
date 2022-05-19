package com.info.firinotomasyon.Activityler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.Activityler.RvAdapters.ImalatToplamSayilarAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class ImalatToplamSayilarActivity extends AppCompatActivity {

    private RecyclerView rvImalatToplamSayilar;

    private ImalatToplamSayilarAdapter adapterToplamSayilar;
    private ArrayList<Urunler> adapterGonderilcekList;
    private HashSet<Urunler> hashSetUrunler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imalat_toplam_sayilar);

        rvImalatToplamSayilar = findViewById(R.id.rvImalatToplamSayilar);

        siparisleriCek();


    }

    public void siparisleriCek(){
        String url = "https://kristalekmek.com/imalat_siparis/tum_siparisleri_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hashSetUrunler = new HashSet<>();
                double sablonAdet = 0.0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("musteri_aldigi_urunler");

                    for (int i = 0 ; i< jsonArray.length() ; i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        String urun_ad = k.getString("urun_ad");
                        int urun_adet = k.getInt("urun_adet");

                        Urunler urun = new Urunler(urun_ad,"",0.0);

                        if (hashSetUrunler.contains(urun)){
                            ArrayList<Urunler> sablonList = new ArrayList<>(hashSetUrunler);
                            for (Urunler u : sablonList){
                                if (u.getUrun_ad().trim().equals(urun_ad)){
                                    sablonAdet = u.getUrun_adet();
                                }
                            }
                            hashSetUrunler.remove(urun);
                            urun.setUrun_adet((double) urun_adet+sablonAdet);
                            sablonAdet = 0.0;
                            hashSetUrunler.add(urun);
                        }else {
                            urun.setUrun_adet(urun_adet);
                            hashSetUrunler.add(urun);
                        }

                    }

                    adapterGonderilcekList = new ArrayList<>(hashSetUrunler);

                    rvImalatToplamSayilar.setHasFixedSize(true);
                    rvImalatToplamSayilar.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    adapterToplamSayilar = new ImalatToplamSayilarAdapter(ImalatToplamSayilarActivity.this,adapterGonderilcekList);
                    rvImalatToplamSayilar.setAdapter(adapterToplamSayilar);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(ImalatToplamSayilarActivity.this).add(istek);
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ImalatToplamSayilarActivity.this,ImalatSiparisActivity.class));
    }


}