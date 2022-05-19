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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.info.firinotomasyon.Activityler.Classlar.Giderler;
import com.info.firinotomasyon.Activityler.RvAdapters.GiderAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GiderActivity extends AppCompatActivity {
    private EditText editTextGiderKalem,editTextGiderTutar;
    private Button buttonGiderCikis;
    private RecyclerView rv_giderler;
    private GiderAdapter adapter;
    private FloatingActionButton fabEkle;

    private ArrayList<Giderler> giderlerArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gider);

        editTextGiderKalem = findViewById(R.id.editTextGiderKalem);
        editTextGiderTutar = findViewById(R.id.editTextGiderTutar);
        buttonGiderCikis = findViewById(R.id.buttonGiderCikis);
        rv_giderler = findViewById(R.id.rv_giderler);
        fabEkle = findViewById(R.id.fabEkle);



        // GİDERLER VERİTABANINDAN BU METHOD İLE ÇEKİLİYOR VE ADAPTER RVY'YE BU METHOD İÇİNDE SET EDİLİYOR.
        giderleriCek();





        fabEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextGiderKalem.getText().toString().trim().equals("") || editTextGiderTutar.getText().toString().trim().equals("")){
                    Toast.makeText(GiderActivity.this,"Boş alan bırakma !",Toast.LENGTH_SHORT).show();
                }else {
                    SimpleDateFormat sekil = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date tarih = new Date();

                    giderEkle(String.valueOf(sekil.format(tarih)),editTextGiderKalem.getText().toString(),Double.parseDouble(editTextGiderTutar.getText().toString()));

                    editTextGiderKalem.setText("");
                    editTextGiderTutar.setText("");

                }
            }
        });

        buttonGiderCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GiderActivity.this,TezgahActivity.class));
                finish();
            }
        });


    }
    public void giderleriCek(){
        String url = "https://kristalekmek.com/giderler/giderleri_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray giderList = jsonObject.getJSONArray("gunluk_giderler");

                    for (int i=0;i<giderList.length();i++){
                        JSONObject k = giderList.getJSONObject(i);
                        String tarih_saat = k.getString("tarih_saat");
                        String gider_ad = k.getString("gider_ad");
                        Double gider_tutar = k.getDouble("gider_tutar");
                        Giderler gider = new Giderler(tarih_saat,gider_ad,gider_tutar);
                        giderlerArrayList.add(gider);
                    }
                    rv_giderler.setHasFixedSize(true);
                    rv_giderler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    adapter = new GiderAdapter(GiderActivity.this,giderlerArrayList);
                    rv_giderler.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(istek);
    }

    public void giderEkle(String tarih_saat,String gider_ad,Double gider_tutar){
        String url = "https://kristalekmek.com/giderler/gider_kaydet.php";

        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("giderEkle Response :",response);
                Giderler gider = new Giderler(tarih_saat,gider_ad,gider_tutar);
                giderlerArrayList.add(gider);
                rv_giderler.setHasFixedSize(true);
                rv_giderler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                adapter = new GiderAdapter(GiderActivity.this,giderlerArrayList);
                rv_giderler.setAdapter(adapter);
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

                params.put("tarih_saat",tarih_saat);
                params.put("gider_ad",gider_ad);
                params.put("gider_tutar",String.valueOf(gider_tutar));

                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GiderActivity.this,TezgahActivity.class));
    }
}