package com.info.firinotomasyon.Activityler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.RvAdapters.SevkiyatMusteriAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.SevkiyatSiparisMusteriAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SevkiyatSiparisActivity extends AppCompatActivity {

    //GORSEL NESNE TANIMLARI
    private Toolbar toolbarSevkiyatSiparis;
    private RecyclerView rvSevkiyatSiparisMusteriler;
    private Button buttonSevkiyatSiparisGeri;

    private ArrayList<SevkiyatMusteriler> databasedenCekilenMusterilerList;
    private SevkiyatSiparisMusteriAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevkiyat_siparis);

        //GORSEL NESNELERİN BAGLANMASI
        toolbarSevkiyatSiparis = findViewById(R.id.toolbarSevkiyatSiparis);
        rvSevkiyatSiparisMusteriler = findViewById(R.id.rvSevkiyatSiparisMusteriler);
        buttonSevkiyatSiparisGeri = findViewById(R.id.buttonSevkiyatSiparisGeri);


        //TOOLBAR İŞLEMLERİ
        toolbarSevkiyatSiparis.setTitle("Müşteriler");
        toolbarSevkiyatSiparis.setLogo(R.drawable.person);
        setSupportActionBar(toolbarSevkiyatSiparis);


        musterilerCek(); // müşteriler çekiliyor.


        //rv işlemleri
        rvSevkiyatSiparisMusteriler.setHasFixedSize(true);
        rvSevkiyatSiparisMusteriler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));



        buttonSevkiyatSiparisGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SevkiyatSiparisActivity.this,SevkiyatActivity.class));
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.tezgah_musteri_menu,menu);
        MenuItem item = menu.findItem(R.id.action_araa);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("newText1",query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("newText",newText);

                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public void musterilerCek(){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/sevkiyat_musteriler_cek_siparis.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                databasedenCekilenMusterilerList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray musteriList = jsonObject.getJSONArray("sevkiyat_musteriler");

                    for (int i = 0 ; i<musteriList.length();i++){
                        JSONObject k = musteriList.getJSONObject(i);
                        int musteri_id = k.getInt("musteri_id");
                        String musteri_ad = k.getString("musteri_ad");
                        String musteri_tel = k.getString("musteri_tel");
                        String musteri_adres = k.getString("musteri_adres");

                        SevkiyatMusteriler musteri = new SevkiyatMusteriler(musteri_ad,musteri_tel,musteri_adres);
                        musteri.setMusteriId(musteri_id);

                        databasedenCekilenMusterilerList.add(musteri);
                    }
                    adapter = new SevkiyatSiparisMusteriAdapter(SevkiyatSiparisActivity.this,databasedenCekilenMusterilerList);
                    rvSevkiyatSiparisMusteriler.setAdapter(adapter);


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SevkiyatSiparisActivity.this,SevkiyatActivity.class));
    }
}