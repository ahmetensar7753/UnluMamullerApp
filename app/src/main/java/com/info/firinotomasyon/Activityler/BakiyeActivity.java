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
import com.info.firinotomasyon.Activityler.RvAdapters.BakiyeMusteriAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.SevkiyatMusteriAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BakiyeActivity extends AppCompatActivity {
    //GORSEL NESNE TANIMLARI
    private RecyclerView rvSevkiyatBakiyeMusteriler;
    private Button buttonSevkiyatBakiyeGeri;
    private Toolbar toolbarSevkiyatBakiye;

    private BakiyeMusteriAdapter adapter;
    private ArrayList<SevkiyatMusteriler> musteriList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakiye);

        rvSevkiyatBakiyeMusteriler = findViewById(R.id.rvSevkiyatBakiyeMusteriler);
        buttonSevkiyatBakiyeGeri = findViewById(R.id.buttonSevkiyatBakiyeGeri);
        toolbarSevkiyatBakiye = findViewById(R.id.toolbarSevkiyatBakiye);

        //TOOLBAR İŞLEMLERİ
        toolbarSevkiyatBakiye.setTitle("Müşteriler");
        toolbarSevkiyatBakiye.setLogo(R.drawable.person);
        setSupportActionBar(toolbarSevkiyatBakiye);

        musterileriCek();


        rvSevkiyatBakiyeMusteriler.setHasFixedSize(true);
        rvSevkiyatBakiyeMusteriler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        buttonSevkiyatBakiyeGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BakiyeActivity.this,SevkiyatActivity.class));
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

    public void musterileriCek(){

        String url = "https://kristalekmek.com/sevkiyat_musteriler/sevkiyat_musteriler_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                musteriList = new ArrayList<>();
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

                        musteriList.add(musteri);
                    }

                    adapter = new BakiyeMusteriAdapter(BakiyeActivity.this,musteriList);
                    rvSevkiyatBakiyeMusteriler.setAdapter(adapter);


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
        startActivity(new Intent(BakiyeActivity.this,SevkiyatActivity.class));
    }

}