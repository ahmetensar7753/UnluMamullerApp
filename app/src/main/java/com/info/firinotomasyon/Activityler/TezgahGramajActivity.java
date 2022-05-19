package com.info.firinotomasyon.Activityler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.GramajUrunler;
import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.Activityler.RvAdapters.GramajSecilenUrunlerAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.GramajUrunlerAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.SecilenUrunlerAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.UrunlerAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class TezgahGramajActivity extends AppCompatActivity implements View.OnClickListener {

    //GÖRSEL NESNELERİN TANIMLAMALARI
    private Button buttonGramajEkle,buttonGramajIptal,buttonGramajTemizle;
    public static Button buttonSil00;
    private Button button000,button100,button200,button300,button400,button500,button600,button700,button800,button900,buttonVirgul;
    private RecyclerView rvGramajUrunler;
    public static RecyclerView rvGramajSecilenUrunler;
    public static TextView textViewAgirlik,textViewKG,textViewGramajTutar;

    private GramajUrunlerAdapter adapterGramajUrunler;
    public static ArrayList<GramajUrunler> gramajUrunlerArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tezgah_gramaj);

        //GÖRSEL NESNELERİN BAĞLANTISI
        buttonGramajEkle = findViewById(R.id.buttonGramajEkle);
        buttonGramajIptal = findViewById(R.id.buttonGramajIptal);
        buttonGramajTemizle = findViewById(R.id.buttonGramajTemizle);
        rvGramajUrunler = findViewById(R.id.rvGramajUrunler);
        rvGramajSecilenUrunler = findViewById(R.id.rvGramajSecilenUrunler);
        textViewAgirlik = findViewById(R.id.textViewAgirlik);
        textViewKG = findViewById(R.id.textViewKG);
        textViewGramajTutar = findViewById(R.id.textViewGramajTutar);
        button000 = findViewById(R.id.button000);
        button100 = findViewById(R.id.button100);
        button200 = findViewById(R.id.button200);
        button300 = findViewById(R.id.button300);
        button400 = findViewById(R.id.button400);
        button500 = findViewById(R.id.button500);
        button600 = findViewById(R.id.button600);
        button700 = findViewById(R.id.button700);
        button800 = findViewById(R.id.button800);
        button900 = findViewById(R.id.button900);
        buttonVirgul = findViewById(R.id.buttonVirgul);
        buttonSil00 = findViewById(R.id.buttonSil00);
        //Tıklanma kontrollerinin activity bağlantısı.
        button000.setOnClickListener(this);
        button100.setOnClickListener(this);
        button200.setOnClickListener(this);
        button300.setOnClickListener(this);
        button400.setOnClickListener(this);
        button500.setOnClickListener(this);
        button600.setOnClickListener(this);
        button700.setOnClickListener(this);
        button800.setOnClickListener(this);
        button900.setOnClickListener(this);
        buttonVirgul.setOnClickListener(this);
        buttonSil00.setOnClickListener(this);

        gramajUrunlerGetir(); // BU METHODUN İÇERİSİNDE ADAPTER KALDIRILIYOR VE RECYCLERVİEW'A SET EDİLİYOR.

        //DİĞER BUTTONLARIN LİSTENERLARI
        buttonGramajIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
                GramajSecilenUrunlerAdapter.gramajUrunlerSecilenDisaridanGelenList.clear();
                rvGramajSecilenUrunler.removeAllViews();
            }
        });

        buttonGramajTemizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // BURADA secilenUrunlerin adetleri ilk olarak sıfırlanıyor for döngüsünde.
                for (int i = 0; i< GramajSecilenUrunlerAdapter.gramajUrunlerSecilenDisaridanGelenList.size(); i++){
                    GramajSecilenUrunlerAdapter.gramajUrunlerSecilenDisaridanGelenList.get(i).setUrun_agirlik(0.0);
                }
                //Sonra ise tekrar eklendiğinde sıra bozulmaması için list temizleniyor. Yukarıdaki atamayı yapmadan clear yapınca ürün adetleri hafızada kalıyor.
                GramajSecilenUrunlerAdapter.gramajUrunlerSecilenDisaridanGelenList.clear();
                rvGramajSecilenUrunler.removeAllViews();

                textViewGramajTutar.setText(String.valueOf(0.0));
            }
        });
        buttonGramajEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (GramajUrunler urun : GramajSecilenUrunlerAdapter.gramajUrunlerSecilenDisaridanGelenList){
                    Urunler u = new Urunler(urun.getUrun_ad(),urun.getResim_ad(),urun.getUrun_fiyat());
                    u.setUrun_adet(urun.getUrun_agirlik());
                    u.setKimlik("gramaj");
                    SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.add(u);
                    UrunlerAdapter.adapter2 = new SecilenUrunlerAdapter(TezgahGramajActivity.this,SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList);
                    TezgahActivity.rvSecilenUrunler.setAdapter(UrunlerAdapter.adapter2);
                    //eklenen ürünlerin toplam tutarda gösterilmesi
                    double texttekiTutar = Double.parseDouble(TezgahActivity.textViewTutar.getText().toString());
                    double toplamTutar = u.getUrun_fiyat() * u.getUrun_adet() + texttekiTutar;
                    TezgahActivity.textViewTutar.setText(String.valueOf(toplamTutar));
                }
                onBackPressed();
                rvGramajSecilenUrunler.removeAllViews();
                GramajSecilenUrunlerAdapter.gramajUrunlerSecilenDisaridanGelenList.clear();
            }
        });







    }

    // TezgahGramajActivity ortadaki rakam tablosu button kontrolleri burada yapılıyor.

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button000:
                textViewAgirlik.setText(textViewAgirlik.getText().toString()+"0");
                textViewKG.setVisibility(View.VISIBLE);
                buttonSil00.setVisibility(View.VISIBLE);
                break;

            case R.id.button100:
                textViewAgirlik.setText(textViewAgirlik.getText().toString()+"1");
                textViewKG.setVisibility(View.VISIBLE);
                buttonSil00.setVisibility(View.VISIBLE);
                break;

            case R.id.button200:
                textViewAgirlik.setText(textViewAgirlik.getText().toString()+"2");
                textViewKG.setVisibility(View.VISIBLE);
                buttonSil00.setVisibility(View.VISIBLE);
                break;

            case R.id.button300:
                textViewAgirlik.setText(textViewAgirlik.getText().toString()+"3");
                textViewKG.setVisibility(View.VISIBLE);
                buttonSil00.setVisibility(View.VISIBLE);
                break;

            case R.id.button400:
                textViewAgirlik.setText(textViewAgirlik.getText().toString()+"4");
                textViewKG.setVisibility(View.VISIBLE);
                buttonSil00.setVisibility(View.VISIBLE);
                break;

            case R.id.button500:
                textViewAgirlik.setText(textViewAgirlik.getText().toString()+"5");
                textViewKG.setVisibility(View.VISIBLE);
                buttonSil00.setVisibility(View.VISIBLE);
                break;

            case R.id.button600:
                textViewAgirlik.setText(textViewAgirlik.getText().toString()+"6");
                textViewKG.setVisibility(View.VISIBLE);
                buttonSil00.setVisibility(View.VISIBLE);
                break;

            case R.id.button700:
                textViewAgirlik.setText(textViewAgirlik.getText().toString()+"7");
                textViewKG.setVisibility(View.VISIBLE);
                buttonSil00.setVisibility(View.VISIBLE);
                break;

            case R.id.button800:
                textViewAgirlik.setText(textViewAgirlik.getText().toString()+"8");
                textViewKG.setVisibility(View.VISIBLE);
                buttonSil00.setVisibility(View.VISIBLE);
                break;

            case R.id.button900:
                textViewAgirlik.setText(textViewAgirlik.getText().toString()+"9");
                textViewKG.setVisibility(View.VISIBLE);
                buttonSil00.setVisibility(View.VISIBLE);
                break;

            case R.id.buttonVirgul:
                textViewAgirlik.setText(textViewAgirlik.getText().toString()+".");
                textViewKG.setVisibility(View.VISIBLE);
                buttonSil00.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonSil00:
                textViewAgirlik.setText("");
                textViewKG.setVisibility(View.INVISIBLE);
                buttonSil00.setVisibility(View.INVISIBLE);
                break;

        }
        if (buttonSil00.getVisibility() == View.INVISIBLE){
            buttonSil00.setClickable(false);
        }else {
            buttonSil00.setClickable(true);
        }
    }

    public void gramajUrunlerGetir(){
        String url = "https://kristalekmek.com/gramaj_urunler/gramaj_urunleri_getir.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                gramajUrunlerArrayList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray gramajUrunList = jsonObject.getJSONArray("gramaj_urunler");

                    for (int i = 0;i<gramajUrunList.length();i++){
                        JSONObject k = gramajUrunList.getJSONObject(i);
                        int urun_id = k.getInt("urun_id");
                        String urun_ad = k.getString("urun_ad");
                        String urun_resim_ad = k.getString("urun_resim_ad");
                        Double urun_fiyat = k.getDouble("urun_fiyat");
                        GramajUrunler gramajUrun = new GramajUrunler(urun_id,urun_ad,urun_resim_ad,urun_fiyat);
                        gramajUrunlerArrayList.add(gramajUrun);
                    }

                    // adapter RV'ye burada set ediliyor.
                    rvGramajUrunler.setHasFixedSize(true);
                    rvGramajUrunler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                    adapterGramajUrunler = new GramajUrunlerAdapter(TezgahGramajActivity.this,gramajUrunlerArrayList);
                    rvGramajUrunler.setAdapter(adapterGramajUrunler);


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



}