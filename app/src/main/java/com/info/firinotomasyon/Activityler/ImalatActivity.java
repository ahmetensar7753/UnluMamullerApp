package com.info.firinotomasyon.Activityler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.NotClass;
import com.info.firinotomasyon.Activityler.RvAdapters.ImalatNotlarAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.ImalatSecilenUrunlerAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.ImalatUrunlerAdapter;
import com.info.firinotomasyon.Activityler.Classlar.SecilenUrunler;
import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ImalatActivity extends AppCompatActivity implements View.OnClickListener{
    // Görsel Nesnelerin Tanımlanması**

    private Toolbar toolbarImalat;
    private RecyclerView rv_imalat_urunler,rv_imalat_secilen_urunler;
    public static TextView textViewImalatUrunAd,textViewImalatAdet,textView3,textView4;
    public static ImageView imageViewImalatUrunResim;
    public static Button buttonImalatEkle;
    private Button button00,button10,button20,button30,button40,button50,button60,button70,button80,button90,buttonSil0;
    public static ConstraintLayout constraintLayout;
    private Button buttonImalatTemizle,buttonImalatKaydet;


    private ArrayList<Urunler> urunlerArrayList;
    private ImalatUrunlerAdapter adapter3;
    public static HashSet<SecilenUrunler> secilenUrunlerHashSet = new HashSet<>();
    private ArrayList<SecilenUrunler> secilenUrunlerList ;
    private ImalatSecilenUrunlerAdapter adapter4;
    public static String imageViewImalatUrunResimAd;
    private int adet=0;

    private ArrayList<NotClass> notArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imalat);

        //Görsel Nesnelerin Bağlanması **

        toolbarImalat = findViewById(R.id.toolbarImalat);
        rv_imalat_urunler = findViewById(R.id.rv_imalat_urunler);
        rv_imalat_secilen_urunler = findViewById(R.id.rv_imalet_secilen_urunler);
        button00 = findViewById(R.id.button00);
        button10 = findViewById(R.id.button10);
        button20 = findViewById(R.id.button20);
        button30 = findViewById(R.id.button30);
        button40 = findViewById(R.id.button40);
        button50 = findViewById(R.id.button50);
        button60 = findViewById(R.id.button60);
        button70 = findViewById(R.id.button70);
        button80 = findViewById(R.id.button80);
        button90 = findViewById(R.id.button90);
        buttonSil0 = findViewById(R.id.buttonSil0);
        textViewImalatUrunAd = findViewById(R.id.textViewImalatUrunAd);
        textViewImalatAdet = findViewById(R.id.textViewImalatAdet);
        imageViewImalatUrunResim = findViewById(R.id.imageViewImalatUrunResim);
        buttonImalatEkle = findViewById(R.id.buttonImalatEkle);
        constraintLayout = findViewById(R.id.constraintLayout);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        buttonImalatKaydet = findViewById(R.id.buttonImalatKaydet);
        buttonImalatTemizle = findViewById(R.id.buttonImalatTemizle);


        //Tıklanma kontrollerinin activity bağlantısı.
        button00.setOnClickListener(this);
        button10.setOnClickListener(this);
        button20.setOnClickListener(this);
        button30.setOnClickListener(this);
        button40.setOnClickListener(this);
        button50.setOnClickListener(this);
        button60.setOnClickListener(this);
        button70.setOnClickListener(this);
        button80.setOnClickListener(this);
        button90.setOnClickListener(this);
        buttonSil0.setOnClickListener(this);


        // toolbar işlemleri
        toolbarImalat.setTitle(" ");
        setSupportActionBar(toolbarImalat);


        //RecyclerView işlemleri
        rv_imalat_urunler.setHasFixedSize(true);
        rv_imalat_urunler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        // TÜM ÜRÜNLER BU METHOD İÇERSİSİNDE VT'DAN ÇEKİLİYOR VE ADAPTER KALDIRILIP YOLLANIYOR. ADAPTER DA BU METHOD İÇİNDE SET EDİLİYOR.
        urunlerCek();

        notCek();

       // EKLE BUTTONU LISTENER'I ---

        buttonImalatEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rv_imalat_secilen_urunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

                //BURADA SEÇİLEN ÜRÜNLER CLASSINDA HER SEFERİNDE NESNE KALDIRILIP YAPICI METODUNA BU ACTİVİTYDEKİ İLGİLİ NESNELER YOLLANIYOR.
                SecilenUrunler urun = new SecilenUrunler(imageViewImalatUrunResimAd,textViewImalatUrunAd.getText().toString(),Integer.parseInt(textViewImalatAdet.getText().toString()));
                // BURADA İF BLOĞU SONUNA KADARKİ AMAÇ AYNI ÜRÜNÜ EKLEMEYEN HESHSET YAPISI ÖZELLİĞİ ÇALIŞTIMI ÖYLEYSE ÜRÜN EKLENMEMİŞTİR VE İFTE TOAST MESSAGE VERİLİYOR.
                int boyut = secilenUrunlerHashSet.size();
                secilenUrunlerHashSet.add(urun);

                if (boyut == secilenUrunlerHashSet.size()){
                    Toast.makeText(ImalatActivity.this,"Ürün zaten ekli!",Toast.LENGTH_SHORT).show();
                }

                // BURADA OLUŞTURULAN HESHSET SIRALI OLMADIĞINDAN DOLAYI SIRALI YAPIYA İHTİYAÇ DUYAN ADAPTERA ARRAYLİSTE DÖNÜŞTÜRÜLEREK YOLLANIYOR.
                secilenUrunlerList = new ArrayList<>(secilenUrunlerHashSet);
                adapter4 = new ImalatSecilenUrunlerAdapter(ImalatActivity.this,secilenUrunlerList);
                rv_imalat_secilen_urunler.setAdapter(adapter4);

                textViewImalatAdet.setText("0");
                textView4.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                constraintLayout.setVisibility(View.INVISIBLE);
                textViewImalatAdet.setVisibility(View.INVISIBLE);
                textViewImalatUrunAd.setVisibility(View.INVISIBLE);
                buttonImalatEkle.setVisibility(View.INVISIBLE);
                imageViewImalatUrunResim.setVisibility(View.INVISIBLE);
                rv_imalat_secilen_urunler.setHasFixedSize(true);
            }
        });
        // temizle buttonu listener'ı BURAYA TIKLANDIĞINDA LİSTE HASHSET VE RecyclerView'daki Viewların hepsi siliniyor.
        buttonImalatTemizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                secilenUrunlerHashSet.clear();
                secilenUrunlerList.clear();
                rv_imalat_secilen_urunler.removeAllViews();

                textView4.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                constraintLayout.setVisibility(View.INVISIBLE);
                textViewImalatAdet.setVisibility(View.INVISIBLE);
                textViewImalatUrunAd.setVisibility(View.INVISIBLE);
                buttonImalatEkle.setVisibility(View.INVISIBLE);
                imageViewImalatUrunResim.setVisibility(View.INVISIBLE);

            }
        });
        buttonImalatKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ab = new AlertDialog.Builder(ImalatActivity.this);
                ab.setIcon(R.drawable.unlem_resim);
                ab.setTitle(" ");
                ab.setMessage("Ürünler stoğa eklensin mi ?");


                ab.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // BURADA secilen ürünler stoğaEkleniyor ve imalEdilenUrunlere kaydediliyor.


                        SimpleDateFormat sekil = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date tarih = new Date();

                        for (SecilenUrunler item : secilenUrunlerList){
                            stokCek(item.getAd(),item.getAdet());
                            imalEdilenKaydet(item.getAd(),item.getAdet(),String.valueOf(sekil.format(tarih)));
                        }




                        //BURADA ÜRÜNLER VERİTABANINA KAYDEDİDİLDİKTEN SONRA HASHSET-LİSTE-RecyclerView temizlenip ilgili kısımlar INVISIBLE YAPILIYOR.
                        secilenUrunlerHashSet.clear();
                        secilenUrunlerList.clear();
                        rv_imalat_secilen_urunler.removeAllViews();

                        textView4.setVisibility(View.INVISIBLE);
                        textView3.setVisibility(View.INVISIBLE);
                        constraintLayout.setVisibility(View.INVISIBLE);
                        textViewImalatAdet.setVisibility(View.INVISIBLE);
                        textViewImalatUrunAd.setVisibility(View.INVISIBLE);
                        buttonImalatEkle.setVisibility(View.INVISIBLE);
                        imageViewImalatUrunResim.setVisibility(View.INVISIBLE);
                        Toast.makeText(ImalatActivity.this,"Stok Eklendi.",Toast.LENGTH_SHORT).show();
                    }
                });

                ab.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(ImalatActivity.this,"Ürünler Eklenmedi !",Toast.LENGTH_SHORT).show();

                    }
                });
                ab.create().show();

            }
        });






    }


    //Menu tasarımı baglanıyor.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.imalat_menu,menu);
        return true;
    }
    // Menudeki actionlara tıklanma kontrolleri burada yapılıyor.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.actions_siparis:
                startActivity(new Intent(ImalatActivity.this,ImalatSiparisActivity.class));
                finish();
                return true;
            case R.id.actions_cikis:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            case R.id.actions_mesaj:

                View tasarim = getLayoutInflater().inflate(R.layout.imalat_not_goruntule_alert,null);
                RecyclerView rvImalatNotlar = tasarim.findViewById(R.id.rvImalatNotlar);
                AlertDialog.Builder ad = new AlertDialog.Builder(ImalatActivity.this);

                rvImalatNotlar.setHasFixedSize(true);
                rvImalatNotlar.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                ImalatNotlarAdapter adapterNot = new ImalatNotlarAdapter(ImalatActivity.this,notArrayList);
                rvImalatNotlar.setAdapter(adapterNot);

                ad.setTitle("SON 1 HAFTA GELEN NOTLAR");
                ad.setIcon(R.drawable.imalathane_not_resim);
                ad.setView(tasarim);

                ad.create().show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //  rakam tablosu button kontrolleri burada yapılıyor.
    @Override
    public void onClick(View v) {
        if (textViewImalatAdet.getText().toString().equals("0")){
            textViewImalatAdet.setText("");
        }
        switch (v.getId()){
            case R.id.button00:
                textViewImalatAdet.setText(textViewImalatAdet.getText().toString()+"0");
                break;

            case R.id.button10:
                textViewImalatAdet.setText(textViewImalatAdet.getText().toString()+"1");
                break;

            case R.id.button20:
                textViewImalatAdet.setText(textViewImalatAdet.getText().toString()+"2");
                break;

            case R.id.button30:
                textViewImalatAdet.setText(textViewImalatAdet.getText().toString()+"3");
                break;

            case R.id.button40:
                textViewImalatAdet.setText(textViewImalatAdet.getText().toString()+"4");
                break;

            case R.id.button50:
                textViewImalatAdet.setText(textViewImalatAdet.getText().toString()+"5");
                break;

            case R.id.button60:
                textViewImalatAdet.setText(textViewImalatAdet.getText().toString()+"6");
                break;

            case R.id.button70:
                textViewImalatAdet.setText(textViewImalatAdet.getText().toString()+"7");
                break;

            case R.id.button80:
                textViewImalatAdet.setText(textViewImalatAdet.getText().toString()+"8");
                break;

            case R.id.button90:
                textViewImalatAdet.setText(textViewImalatAdet.getText().toString()+"9");
                break;

            case R.id.buttonSil0:
                textViewImalatAdet.setText("0");

        }
    }

    public void urunlerCek(){
        String url = "https://kristalekmek.com/urunler/tum_urunler_cek.php";

        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                urunlerArrayList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray urunList = jsonObject.getJSONArray("urunler");

                    for (int i =0;i<urunList.length();i++){
                        JSONObject k = urunList.getJSONObject(i);
                        String urun_ad = k.getString("urun_ad");
                        String urun_resim_ad = k.getString("urun_resim_ad");
                        Double urun_fiyat = k.getDouble("urun_fiyat");
                        Urunler urun = new Urunler(urun_ad,urun_resim_ad,urun_fiyat);
                        urunlerArrayList.add(urun);
                    }
                    // Adapter'ın ürünler için atanması.
                    adapter3 = new ImalatUrunlerAdapter(getApplicationContext(),urunlerArrayList);
                    rv_imalat_urunler.setAdapter(adapter3);


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

    public void stokCek(String urun_ad,int eklenecekAdet){
        String url = "https://kristalekmek.com/stok/stok_cek.php";

        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray stokList = jsonObject.getJSONArray("stok");


                    for (int i=0;i<stokList.length();i++){
                        JSONObject k = stokList.getJSONObject(i);
                        int urun_adet = k.getInt("urun_adet");
                        int guncelle = urun_adet+eklenecekAdet;
                        stokGuncelle(urun_ad,guncelle);
                    }

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
                params.put("urun_ad",urun_ad);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);
    }

    public void stokGuncelle(String urun_ad,int urun_adet){
        String url = "https://kristalekmek.com/stok/update_stok.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("stokGuncelle Response",response);
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

                params.put("urun_ad",urun_ad);
                params.put("urun_adet",String.valueOf(urun_adet));

                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);
    }

    public void imalEdilenKaydet(String uretilen_urun_ad,int uretilen_urun_adet,String uretim_tarih_saat){
        String url ="https://kristalekmek.com/imal_edilen_urunler/uretilen_urun_ekle.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("imalKaydetRspns",response);

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

                params.put("uretilen_urun_ad",uretilen_urun_ad);
                params.put("uretilen_urun_adet",String.valueOf(uretilen_urun_adet));
                params.put("uretim_tarih_saat",uretim_tarih_saat);

                return params;
            }
        };
        Volley.newRequestQueue(ImalatActivity.this).add(istek);
    }

    public void notCek(){
        String url = "https://kristalekmek.com/tezgah_imalat_not/haftalik_not_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                notArrayList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("tezgah_imalat_not");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        String not_icerik = k.getString("not_icerik");
                        String not_tarih = k.getString("tarih");

                        NotClass not = new NotClass(not_icerik,not_tarih);
                        notArrayList.add(not);
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(ImalatActivity.this).add(istek);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(ImalatActivity.this);

        ab.setIcon(R.drawable.uyari_amblem);
        ab.setTitle("GİRİŞ EKRANINA DÖNÜLSÜN MÜ ?");

        ab.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(ImalatActivity.this,MainActivity.class));
            }
        });
        ab.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        ab.create().show();
    }
}