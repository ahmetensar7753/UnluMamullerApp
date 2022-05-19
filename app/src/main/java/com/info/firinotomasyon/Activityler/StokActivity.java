package com.info.firinotomasyon.Activityler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.RvAdapters.StokAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StokActivity extends AppCompatActivity {
    //Görsel nesnelerin tanımları
    private RecyclerView rv_Stok;
    private Button buttonStokGeriDon;

    private StokAdapter stokAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);

        //Gorsel nesnelerin baglanmasi
        rv_Stok = findViewById(R.id.rv_stok);
        buttonStokGeriDon = findViewById(R.id.buttonStokGeriDon);

        buttonStokGeriDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StokActivity.this,TezgahActivity.class));
                finish();
            }
        });

        rv_Stok.setHasFixedSize(true);
        rv_Stok.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        stokAdapter = new StokAdapter(getApplicationContext(),TezgahActivity.urunlerArrayList);
        rv_Stok.setAdapter(stokAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(StokActivity.this,TezgahActivity.class));
    }
}