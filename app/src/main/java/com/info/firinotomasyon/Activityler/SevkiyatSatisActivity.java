package com.info.firinotomasyon.Activityler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.Activityler.RvAdapters.SevkiyatMusteriAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SevkiyatSatisActivity extends AppCompatActivity {
    //GORSEL NESNELERİN TANIMLARI
    private RecyclerView rvSevkiyatSatisMusteriler;
    private Button buttonSevkiyatSatisGeri;
    private Toolbar toolbarSevkiyatSatis;

    private ArrayList<SevkiyatMusteriler> databasedenCekilenMusterilerList = new ArrayList<>();

    private SevkiyatMusteriAdapter adapter1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevkiyat_satis);

        //GORSEL NESNELERİN BAĞLANMASI
        toolbarSevkiyatSatis = findViewById(R.id.toolbarSevkiyatSatis);
        rvSevkiyatSatisMusteriler = findViewById(R.id.rvSevkiyatSatisMusteriler);
        buttonSevkiyatSatisGeri = findViewById(R.id.buttonSevkiyatSatisGeri);

        //TOOLBAR İŞLEMLERİ
        toolbarSevkiyatSatis.setTitle("Müşteriler");
        toolbarSevkiyatSatis.setLogo(R.drawable.person);
        setSupportActionBar(toolbarSevkiyatSatis);


        musterileriCek();

        rvSevkiyatSatisMusteriler.setHasFixedSize(true);
        rvSevkiyatSatisMusteriler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));



        buttonSevkiyatSatisGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter1 = new SevkiyatMusteriAdapter(SevkiyatSatisActivity.this,databasedenCekilenMusterilerList);
                rvSevkiyatSatisMusteriler.setAdapter(adapter1);
                startActivity(new Intent(SevkiyatSatisActivity.this,SevkiyatActivity.class));
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

                adapter1.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public void musterileriCek(){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/sevkiyat_musteriler_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //databasedenCekilenMusterilerList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray sevkiyatMusteriList = jsonObject.getJSONArray("sevkiyat_musteriler");

                    for (int i = 0 ; i < sevkiyatMusteriList.length() ; i++ ){
                        JSONObject k = sevkiyatMusteriList.getJSONObject(i);

                        int musteri_id = k.getInt("musteri_id");
                        String musteri_ad = k.getString("musteri_ad");
                        String musteri_tel = k.getString("musteri_tel");
                        String musteri_adres = k.getString("musteri_adres");
                        Double toplam_borc = k.getDouble("toplam_borc");

                        SevkiyatMusteriler musteri = new SevkiyatMusteriler(musteri_ad,musteri_tel,musteri_adres);
                        musteri.setMusteriId(musteri_id);
                        musteri.setMusteriToplamBorc(toplam_borc);


                        databasedenCekilenMusterilerList.add(musteri);

                    }
                    adapter1 = new SevkiyatMusteriAdapter(SevkiyatSatisActivity.this,databasedenCekilenMusterilerList);
                    rvSevkiyatSatisMusteriler.setAdapter(adapter1);




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
    /*public void aldigiUrunlerCek(int musteri_id, SevkiyatMusteriler musteri){   BU BLOK SEVKİYATMUSTERİACTİVİTYE TAŞINDI. ARTIK TEK MÜŞTERİNİN(TIKLANAN) ALDIĞI ÜRÜNLER DİREK ÇEKİLİP ADAPTERINA SET EDİLİYOR.
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
                    musteri.setMusteriAldıgıUrunlerList(aldıgıUrunler);


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
        Volley.newRequestQueue(mContext).add(istek);
    }*/



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SevkiyatSatisActivity.this,SevkiyatActivity.class));
    }
}