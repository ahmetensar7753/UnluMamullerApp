package com.info.firinotomasyon.Activityler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.RvAdapters.SecilenUrunlerAdapter;
import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.Activityler.RvAdapters.UrunlerAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TezgahActivity extends AppCompatActivity implements View.OnClickListener {

    // Görsel nesneler burada tanımlanmaktadır.
    private Toolbar toolbarTezgah;
    public static  RecyclerView rvUrunler,rvSecilenUrunler;
    public static TextView textViewUrunSayi;
    private Button button0,button1,button2,button3,button4,button5,button6,button7,button8,button9,buttonSil;
    public static TextView textViewTutar;
    private Button buttonTezgahKaydet,buttonTezgahTemizle,buttonImalathaneNot,buttonStokDurumu,buttonGramajliSatis,buttonTezgahHibe,buttonTezgahGider,buttonTezgahMüsteriler,buttonTezgahAskidaEkmek;
    private TextView textViewTarihSaat;
    public static TextView textViewMusteriAd;


    public static ArrayList<Urunler> urunlerArrayList = new ArrayList<>();
    private UrunlerAdapter adapter;
    private ArrayList<Urunler> kaydedilecekUrunlerArrayList = new ArrayList<>();
    public static String musteri;
    private double musteriBorcEkle;


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tezgah);

        //Görsel nesnelerin bağlanması burada yapılmaktadır.
        toolbarTezgah = findViewById(R.id.toolbarTezgah);
        rvUrunler = findViewById(R.id.rvUrunler);
        rvSecilenUrunler = findViewById(R.id.rvlSecilenUrunler);
        textViewUrunSayi = findViewById(R.id.textViewUrunSayi);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonSil = findViewById(R.id.buttonSil);
        buttonTezgahKaydet = findViewById(R.id.buttonTezgahKaydet);
        buttonTezgahTemizle = findViewById(R.id.buttonTezgahTemizle);
        buttonImalathaneNot = findViewById(R.id.buttonTezgahImalathaneNot);
        buttonStokDurumu = findViewById(R.id.buttonStokDurumu);
        buttonGramajliSatis = findViewById(R.id.buttonGramajliSatis);
        buttonTezgahHibe = findViewById(R.id.buttonTezgahHibe);
        buttonTezgahGider = findViewById(R.id.buttonTezgahGider);
        buttonTezgahMüsteriler = findViewById(R.id.buttonTezgahMüsteriler);
        buttonTezgahAskidaEkmek = findViewById(R.id.buttonTezgahAskidaEkmek);
        textViewTutar = findViewById(R.id.textViewTutar);
        textViewTarihSaat = findViewById(R.id.textViewTarihSaat);
        textViewMusteriAd = findViewById(R.id.textViewMusteriAd);

        //Tıklanma kontrollerinin activity bağlantısı.
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonSil.setOnClickListener(this);

        // toolbar işlemleri
        toolbarTezgah.setTitle("SEPET");
        setSupportActionBar(toolbarTezgah);


        /* KAYITLI MÜŞTERİYLE İŞLEM YAPILACAĞI SIRADA musteri_ad_soyad geldiğinden boş olan textView'e doldurulur.
        *  veresiye satış olacağı zaman if'in koşulu değişiyor.
        *  ona göre de eğer veresiye müşteri ile ilgili işlem yapılacaksa if'in içine giriliyor.
        *  son da textview tekrar fiyatlandırılıyor.
        *  buttonTezgahKaydet içerisinde kontrolü yapılıp ona göre veritabanına kayıt ediliyor. */

        if (!TezgahMusteriActivity.musterii.equals("")){
            Intent musteriIntent = getIntent();
            musteri = musteriIntent.getStringExtra("musteri");
            textViewMusteriAd.setText(musteri);

            double tutar=0.0;
            for (int i = 0; i < SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.size(); i++){
                double fiyat = SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.get(i).getUrun_adet()*SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.get(i).getUrun_fiyat();
                tutar = tutar+fiyat;
            }
            textViewTutar.setText(String.valueOf(tutar));

        }

        buttonTezgahKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sekil = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date tarih = new Date();
                if (textViewMusteriAd.getText().toString().trim().equals("")){

                    // NORMAL SATIŞIN KAYDEDİLDİĞİ YER

                    // kaydet butonuna bastıktan sonra kaydedilmesi gereken veriler ayrı bir arrayListe alınıyor.
                    int a = SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.size();
                    for (int i = 0; i < a; i++){
                        Urunler urun = SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.get(i);
                        kaydedilecekUrunlerArrayList.add(urun);
                    }
                    Double ucretToplami =  Double.valueOf(textViewTutar.getText().toString());
                    Toast.makeText(getApplicationContext(),"Satış Kaydedildi.",Toast.LENGTH_LONG).show();

                    //BURADA kaydedilecekUrunlerArrayList ve ucretToplami veriTabanına kaydediliyor.
                    for (Urunler item: kaydedilecekUrunlerArrayList){
                        double tutar = item.getUrun_adet()*item.getUrun_fiyat();
                        satisKaydet(String.valueOf(sekil.format(tarih)),tutar,item.getUrun_ad(),item.getUrun_adet());
                        stokCek(item.getUrun_ad(),(int)item.getUrun_adet());
                    }
                    // hibe ile satışlarda kayıt alınırken nakitGiriş 0 tl yazılmasın diye bu kontrol yapılıyor.
                    if (ucretToplami != 0){
                        nakitKaydet(String.valueOf(sekil.format(tarih)),ucretToplami);
                    }

                    // Ardından kaydedilecekUrunlerArrayList sıfırlanıyor.
                    kaydedilecekUrunlerArrayList.clear();
                    buttonTezgahTemizle.callOnClick();
                    textViewMusteriAd.setText("");

                }else {

                    // nakit girişi olmadan vereyise satış burada kaydediliyor.
                    int a = SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.size();
                    for (int i = 0; i < a; i++){
                        Urunler urun = SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.get(i);
                        kaydedilecekUrunlerArrayList.add(urun);
                    }

                    Double ucretToplamiVeresiye =  Double.valueOf(textViewTutar.getText().toString());
                    musteriBorcEkle = ucretToplamiVeresiye;

                    for (Urunler item: kaydedilecekUrunlerArrayList){
                        double tutar = item.getUrun_adet()*item.getUrun_fiyat();
                        satisKaydet(String.valueOf(sekil.format(tarih)),tutar,item.getUrun_ad(),item.getUrun_adet());
                    }

                    String musteriAd = textViewMusteriAd.getText().toString();
                    kisiAra(musteriAd);

                    // Ardından kaydedilecekUrunlerArrayList sıfırlanıyor.
                    kaydedilecekUrunlerArrayList.clear();
                    buttonTezgahTemizle.callOnClick();
                    textViewMusteriAd.setText("");
                }
            }
        });

        buttonTezgahTemizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewMusteriAd.setText("");
                // BURADA secilenUrunlerin adetleri ilk olarak sıfırlanıyor for döngüsünde.
                for (int i = 0; i<SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.size();i++){
                    SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.get(i).setUrun_adet(0);
                }
                //Sonra ise tekrar eklendiğinde sıra bozulmaması için list temizleniyor. Yukarıdaki atamayı yapmadan clear yapınca ürün adetleri hafızada kalıyor.
                SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.clear();
                //tutar sıfırlanıyor ve RecyclerViewdeki tüm ürünler kaldırılıyor.
                textViewTutar.setText("0");
                rvSecilenUrunler.removeAllViews();
            }
        });
        buttonImalathaneNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tasarim = getLayoutInflater().inflate(R.layout.tezgah_imalathane_not_alert_tasarim,null);
                EditText editTextAlert = tasarim.findViewById(R.id.editTextAlert);

                AlertDialog.Builder ad = new AlertDialog.Builder(TezgahActivity.this);

                ad.setTitle("İmalathaneye Mesaj Gönder");
                ad.setIcon(R.drawable.imalathane_not_resim);
                ad.setView(tasarim);


                ad.setPositiveButton("Gönder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SimpleDateFormat sekil = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date tarih = new Date();

                        if (!editTextAlert.getText().toString().trim().equals("")){
                            notGonder(editTextAlert.getText().toString(),String.valueOf(sekil.format(tarih)));

                            Toast.makeText(TezgahActivity.this,"Not Gönderildi.",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TezgahActivity.this,"Boş Not Yollanmaz!",Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.i("AlertView","Iptal Tıklandı..");

                    }
                });
               ad.create().show();
            }
        });



        buttonStokDurumu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TezgahActivity.this,StokActivity.class));
                finish();
            }
        });
        buttonGramajliSatis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TezgahActivity.this,TezgahGramajActivity.class));
            }
        });

        buttonTezgahHibe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View tasarimHibe = getLayoutInflater().inflate(R.layout.tezgah_hibe_alert_tasarim,null);
                EditText editTextHibeAlert = tasarimHibe.findViewById(R.id.editTextHibeAlert);
                AlertDialog.Builder builder = new AlertDialog.Builder(TezgahActivity.this);
                builder.setTitle("Hibe");
                builder.setView(tasarimHibe);

                builder.setPositiveButton("Hibe Et", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double hibe_tutar = Double.parseDouble(textViewTutar.getText().toString());
                        String hibe_ad = editTextHibeAlert.getText().toString();

                        SimpleDateFormat sekil = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date tarih = new Date();

                        //veritabnına insert ediliyor.
                        hibeKaydet(hibe_ad,hibe_tutar,String.valueOf(sekil.format(tarih)));

                        textViewTutar.setText("0");
                        buttonTezgahKaydet.callOnClick();
                    }
                });
                builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(TezgahActivity.this,"Hibe İptal",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.create().show();

            }
        });

        buttonTezgahGider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TezgahActivity.this,GiderActivity.class));
                finish();
            }
        });

        buttonTezgahMüsteriler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TezgahActivity.this,TezgahMusteriActivity.class));
                finish();
            }
        });

        buttonTezgahAskidaEkmek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TezgahActivity.this,AskidaEkmekActivity.class));
                finish();
            }
        });


        //RecyclerView işlemleri
        rvUrunler.setHasFixedSize(true);
        rvUrunler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));


        // BU FONKSİYON İÇERİSİNDE URUNLER VERİTABANINDAN ÇEKİLİYOR VE ARDINDAN ADAPTER NESNESİ KALDIRILIP YOLLANIYOR.
        rvUrunler.removeAllViews();
        urunlerArrayList.clear();
        urunlerCek();


        SimpleDateFormat sekil = new SimpleDateFormat("dd/MM/yyyy");
        Date tarih = new Date();
        textViewTarihSaat.setText(String.valueOf(sekil.format(tarih)));



    }
        //Menu tasarımı baglanıyor.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tezgah_menu,menu);
        return true;
    }
        // Menudeki actionlara tıklanma kontrolleri burada yapılıyor.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.actions_siparis:

                View tezgahRaporTasarim = getLayoutInflater().inflate(R.layout.tezgah_rapor_alert,null);
                EditText editTextTezgahRaporNakit = tezgahRaporTasarim.findViewById(R.id.editTextTezgahRaporNakit);
                EditText editTextTezgahRaporKart = tezgahRaporTasarim.findViewById(R.id.editTextTezgahRaporKart);
                EditText editTextTezgahRaporBayat = tezgahRaporTasarim.findViewById(R.id.editTextTezgahRaporBayat);

                AlertDialog.Builder builder = new AlertDialog.Builder(TezgahActivity.this);
                builder.setMessage("Gün Sonu Rapor Girişi");
                builder.setView(tezgahRaporTasarim);

                builder.setPositiveButton("GÖNDER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (!editTextTezgahRaporNakit.getText().toString().trim().equals("") &&
                            !editTextTezgahRaporKart.getText().toString().trim().equals("") &&
                            !editTextTezgahRaporBayat.getText().toString().trim().equals("")){

                            SimpleDateFormat sekil = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            Date tarih = new Date();

                            raporKaydet(String.valueOf(sekil.format(tarih))
                                    ,Double.parseDouble(editTextTezgahRaporNakit.getText().toString())
                                    ,Double.parseDouble(editTextTezgahRaporKart.getText().toString())
                                    ,Integer.parseInt(editTextTezgahRaporBayat.getText().toString()));

                            Toast.makeText(TezgahActivity.this,"Rapor Gönderildi.",Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(TezgahActivity.this,"BOŞ ALAN BIRAKMA !! Rapor Gönderilmedi",Toast.LENGTH_SHORT).show();
                        }



                    }
                });

                builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //iptal
                    }
                });

                builder.create().show();

                return true;
            case R.id.actions_cikis:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TezgahActivity ortadaki rakam tablosu button kontrolleri burada yapılıyor.
    @Override
    public void onClick(View v) {
        if (textViewUrunSayi.getText().toString().equals("0")){
            textViewUrunSayi.setText("");
        }
        switch (v.getId()){
            case R.id.button0:
                textViewUrunSayi.setText(textViewUrunSayi.getText().toString()+"0");
                break;

            case R.id.button1:
                textViewUrunSayi.setText(textViewUrunSayi.getText().toString()+"1");
                break;

            case R.id.button2:
                textViewUrunSayi.setText(textViewUrunSayi.getText().toString()+"2");
                break;

            case R.id.button3:
                textViewUrunSayi.setText(textViewUrunSayi.getText().toString()+"3");
                break;

            case R.id.button4:
                textViewUrunSayi.setText(textViewUrunSayi.getText().toString()+"4");
                break;

            case R.id.button5:
                textViewUrunSayi.setText(textViewUrunSayi.getText().toString()+"5");
                break;

            case R.id.button6:
                textViewUrunSayi.setText(textViewUrunSayi.getText().toString()+"6");
                break;

            case R.id.button7:
                textViewUrunSayi.setText(textViewUrunSayi.getText().toString()+"7");
                break;

            case R.id.button8:
                textViewUrunSayi.setText(textViewUrunSayi.getText().toString()+"8");
                break;

            case R.id.button9:
                textViewUrunSayi.setText(textViewUrunSayi.getText().toString()+"9");
                break;

            case R.id.buttonSil:
                textViewUrunSayi.setText("0");

        }
    }
    //TEZGAH SATIŞLARI BU FONKSİYONDA VERİTABNINA KAYDEDİLİYOR.
    public void satisKaydet(String satis_tarih_saat,Double satis_tutar,String urun_ad,Double urun_adet_kg){
        String url = "https://kristalekmek.com/tezgah_satis/insert_tezgah_satis.php";

        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("SatisKayitResponse",response);

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
                params.put("satis_tarih_saat",satis_tarih_saat);
                params.put("satis_tutar",String.valueOf(satis_tutar));
                params.put("urun_ad",urun_ad);
                params.put("urun_adet_kg",String.valueOf(urun_adet_kg));

                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);
    }

    //tezgah nakit girişini vt kaydeden fonksiyon

    public void nakitKaydet(String tarihSaat,Double toplamTutar){
        String url = "https://kristalekmek.com/tezgah_para_giris/insert_tezgah_satis_tutar.php";

        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Para Giris Response",response);
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

                params.put("satis_tarih_saat",tarihSaat);
                params.put("satis_tutar",String.valueOf(toplamTutar));

                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);

    }

    //İlgili tezgah müşterisi bu fonksiyonda VT'den çekilip borcu güncelleniyor.(musteriBorcGuncelle() methodu ile).
    public void kisiAra(String ad){
        String url = "https://kristalekmek.com/tezgah_musteriler/select_isme_gore.php";

        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray musteriListe = jsonObject.getJSONArray("tezgah_musteriler");

                    for (int i =0;i<musteriListe.length();i++){
                        JSONObject k = musteriListe.getJSONObject(i);
                        int musteri_id = k.getInt("musteri_id");
                        Double guncel_borc = k.getDouble("guncel_borc");
                        double duzelt = guncel_borc+musteriBorcEkle;
                        musteriBorcGuncelle(musteri_id,duzelt);
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

                params.put("musteri_ad",ad);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(istek);
    }

    //VERESİYE SATIŞTA MÜŞTERİNİN BORCU BURADA GÜNCELLENİYOR. BU FONKSİYON kisiAra() fonksiyonu içerisinde çağırılıyor.
    public void musteriBorcGuncelle(int id,Double guncelBorc){
        String url = "https://kristalekmek.com/tezgah_musteriler/musteri_borc_guncelle.php";

        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Mesaj",response);

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

                params.put("musteri_id",String.valueOf(id));
                params.put("guncel_borc",String.valueOf(guncelBorc));

                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);
    }

    public void hibeKaydet(String hibe_ad,Double hibe_tutar,String tarih_saat){
        String url = "https://kristalekmek.com/hibe_kaydet/insert_hibe.php";

        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("hibeKaydet Response:",response);
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

                params.put("hibe_ad",hibe_ad);
                params.put("hibe_tutar",String.valueOf(hibe_tutar));
                params.put("tarih_saat",tarih_saat);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);
    }


    public void urunlerCek(){
        String url = "https://kristalekmek.com/urunler/tum_urunler_cek.php";

        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                    adapter = new UrunlerAdapter(getApplicationContext(),urunlerArrayList);
                    rvUrunler.setAdapter(adapter);


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

    public void stokCek(String urun_ad,int dusulecekAdet){
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
                        int guncelle = urun_adet - dusulecekAdet;
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

    public void raporKaydet(String rapor_tarih,Double nakit_tutar,Double kart_tutar,int bayat_adet){
        String url = "https://kristalekmek.com/tezgah_rapor/insert_rapor.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("raporInstrRespons",response);
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

                params.put("rapor_tarih",rapor_tarih);
                params.put("nakit_tutar",String.valueOf(nakit_tutar));
                params.put("kart_tutar",String.valueOf(kart_tutar));
                params.put("bayat_adet",String.valueOf(bayat_adet));


                return params;
            }
        };
        Volley.newRequestQueue(TezgahActivity.this).add(istek);
    }

    public void notGonder(String not,String tarih){
        String url = "https://kristalekmek.com/tezgah_imalat_not/insert_not.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("notGndrRspns",response);
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

                params.put("not_icerik",not);
                params.put("tarih",tarih);

                return params;
            }
        };
        Volley.newRequestQueue(TezgahActivity.this).add(istek);
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder ab = new AlertDialog.Builder(TezgahActivity.this);

        ab.setIcon(R.drawable.uyari_amblem);
        ab.setTitle("GİRİŞ EKRANINA DÖNÜLSÜN MÜ ?");

        ab.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(TezgahActivity.this,MainActivity.class));
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