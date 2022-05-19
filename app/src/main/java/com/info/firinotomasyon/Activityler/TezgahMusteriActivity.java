package com.info.firinotomasyon.Activityler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.info.firinotomasyon.Activityler.Classlar.TezgahMusteriler;
import com.info.firinotomasyon.Activityler.RvAdapters.TezgahMusteriAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.TezgahMusteriOdeAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TezgahMusteriActivity extends AppCompatActivity  {
    //GORSEL NESNE TANIMLARI
    private Toolbar toolbarTezgahMusteri;
    private RecyclerView rv_musteriler;
    private FloatingActionButton fabMusteriEkle;
    public static Button buttonMusteriGeriDon;
    private Button buttonMusteriBorcOde;

    private TezgahMusteriAdapter adapter;
    private ArrayList<TezgahMusteriler> musterilerArrayList;
    public static String musterii="";
    TezgahMusteriOdeAdapter adapterOde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tezgah_musteri);

        // GÖRSEL NESNELERİN BAĞLANMASI
        toolbarTezgahMusteri = findViewById(R.id.toolbarTezgahMusteri);
        rv_musteriler = findViewById(R.id.rv_musteriler);
        fabMusteriEkle = findViewById(R.id.fab_musteri_ekle);
        buttonMusteriGeriDon = findViewById(R.id.buttonMusteriGeriDon);
        buttonMusteriBorcOde = findViewById(R.id.buttonMusteriBorcOde);


        toolbarTezgahMusteri.setTitle("MÜŞTERİLER");
        toolbarTezgahMusteri.setLogo(R.drawable.person);
        setSupportActionBar(toolbarTezgahMusteri);



        // RECYLERVİEW VE ADAPTER İŞLEMLERİ-ATAMALARI
        rv_musteriler.setHasFixedSize(true);
        rv_musteriler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        musterileriGetir(); // adapter bu method içerisinde kaldırılıp rv ye set ediliyor.


        //fab'a tıklandığında müşteri eklemek için bir alertView çıkarılır.

        fabMusteriEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewMusteriEkle = getLayoutInflater().inflate(R.layout.tezgah_musteri_ekle_alert_tasarim,null);
                TextInputEditText editTextMusteriEkleAdSoyad = viewMusteriEkle.findViewById(R.id.editTextMusteriEkleAdSoyad);
                TextInputEditText editTextMusteriEkleTelefon = viewMusteriEkle.findViewById(R.id.editTextMusteriEkleTelefon);
                TextInputEditText editTextMusteriEkleAdres = viewMusteriEkle.findViewById(R.id.editTextMusteriEkleAdres);

                AlertDialog.Builder ab = new AlertDialog.Builder(TezgahMusteriActivity.this);
                ab.setTitle("Müşteri Ekle");
                ab.setView(viewMusteriEkle);

                ab.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if (editTextMusteriEkleAdSoyad.getText().toString().trim().equals("")
                                ||editTextMusteriEkleTelefon.getText().toString().trim().equals("")
                                ||editTextMusteriEkleAdres.getText().toString().trim().equals("")){
                            Toast.makeText(TezgahMusteriActivity.this,"Boş Alan Bırakma!",Toast.LENGTH_SHORT).show();
                            fabMusteriEkle.callOnClick();
                        }else{
                            SimpleDateFormat sekil = new SimpleDateFormat("dd.MM.yyyy");
                            Date tarih = new Date();
                            String ad = editTextMusteriEkleAdSoyad.getText().toString();
                            String tel = editTextMusteriEkleTelefon.getText().toString();
                            String adres = editTextMusteriEkleAdres.getText().toString();
                            musteriEkle(ad,tel,String.valueOf(sekil.format(tarih)),0.0,adres);

                            Toast.makeText(TezgahMusteriActivity.this,"Müşteri Kaydedildi.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ab.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                ab.create().show();
            }
        });

        buttonMusteriGeriDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(TezgahMusteriActivity.this,TezgahActivity.class);
                newIntent.putExtra("musteri", musterii);
                startActivity(newIntent);
                finish();
            }
        });

        buttonMusteriBorcOde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View tasarim = getLayoutInflater().inflate(R.layout.musteri_borc_ode_alert,null);
                RecyclerView rv_musteri_ode = tasarim.findViewById(R.id.rv_musteri_ode);
                TextView textViewMusteriOde = tasarim.findViewById(R.id.textViewMusteriOde);
                EditText editTextTutarOde = tasarim.findViewById(R.id.editTextTutarOde);
                AlertDialog.Builder bd = new AlertDialog.Builder(TezgahMusteriActivity.this);




                rv_musteri_ode.setHasFixedSize(true);
                rv_musteri_ode.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

                adapterOde = new TezgahMusteriOdeAdapter(TezgahMusteriActivity.this,musterilerArrayList,textViewMusteriOde);
                rv_musteri_ode.setAdapter(adapterOde);

                bd.setPositiveButton("ÖDE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SimpleDateFormat sekil = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Date tarih = new Date();

                            kisiAra(textViewMusteriOde.getText().toString(),Double.parseDouble(editTextTutarOde.getText().toString()));

                            Toast.makeText(TezgahMusteriActivity.this,"İşlem tamalandı.",Toast.LENGTH_SHORT).show();

                            nakitKaydet(String.valueOf(sekil.format(tarih)),Double.parseDouble(editTextTutarOde.getText().toString()));

                        startActivity(new Intent(TezgahMusteriActivity.this,TezgahActivity.class));
                        finish();
                    }
                });
                bd.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //boş
                    }
                });
                bd.setMessage("Müşteri seçmek için üzerine tıkla !");
                bd.setView(tasarim);
                bd.create().show();
            }
        });

    }

    //MENU TOOLBAR ÜZERİNDE TANIMLANIP BAĞLANIYOR VE ARAMA İÇİN SEARCHVİEW kullanılarak adapter filtreleniyor.
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

    public void musterileriGetir(){
        String url = "https://kristalekmek.com/tezgah_musteriler/musterileri_getir.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                musterilerArrayList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray musteriList = jsonObject.getJSONArray("tezgah_musteriler");

                    for (int i = 0; i<musteriList.length();i++){
                        JSONObject k = musteriList.getJSONObject(i);
                        String musteri_ad = k.getString("musteri_ad");
                        String musteri_tel = k.getString("musteri_tel");
                        String son_odeme_tarih = k.getString("son_odeme_tarih");
                        Double guncel_borc = k.getDouble("guncel_borc");
                        String adres = k.getString("adres");
                        TezgahMusteriler musteri = new TezgahMusteriler(musteri_ad,musteri_tel,son_odeme_tarih,guncel_borc,adres);
                        musterilerArrayList.add(musteri);
                    }
                    //ADAPTER KALDIRMA VE SET ETME
                    adapter = new TezgahMusteriAdapter(musterilerArrayList);
                    rv_musteriler.setAdapter(adapter);


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

    public void musteriEkle(String musteri_ad,String musteri_tel,String son_odeme_tarih,Double guncel_borc,String adres){
        String url = "https://kristalekmek.com/tezgah_musteriler/insert_musteri.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("musteriEkle()response :",response);
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
                params.put("musteri_ad",musteri_ad);
                params.put("musteri_tel",musteri_tel);
                params.put("son_odeme_tarih",son_odeme_tarih);
                params.put("guncel_borc",String.valueOf(guncel_borc));
                params.put("adres",adres);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);
    }

    public void musteriBorcOde(int musteri_id,Double guncel_borc){
        String url = "https://kristalekmek.com/tezgah_musteriler/musteri_borc_guncelle.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("musteriBorcOde response",response);
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
                params.put("guncel_borc",String.valueOf(guncel_borc));

                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);
    }

    public void kisiAra(String ad,double odenen){
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
                        double duzelt = guncel_borc-odenen;
                        musteriBorcOde(musteri_id,duzelt);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TezgahMusteriActivity.this,TezgahActivity.class));
    }
}