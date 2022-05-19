package com.info.firinotomasyon.Activityler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AskidaEkmekActivity extends AppCompatActivity {

    //GORSEL NESNE TANIMLARI --- askıda ekmek al
    private RadioButton radioButtonPara,radioButtonAdet;
    private EditText editTextAskidaTutar,editTextAskidaAdet;
    private TextView tutar,adet;
    private TextView textViewAskidaSonuc;
    private Button buttonAskidaAlKaydet;

    //GORSEL NESNE TANIMLARI --- askıda ekmek ver
    private TextView textViewAskidaGuncelAdet;
    private EditText editTextAskidaVerilenAdet;
    private Button buttonAskidaVerKaydet;

    private Double ekmekFiyat = 0.0;
    private Double bagisTutar = 0.0;
    private int bagisAdet = 0;
    private int askidaEkmekAdet;

    private int guncelEkmekStok = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askida_ekmek);

        //GORSEL NESNELER BAĞLANIYOR.
        radioButtonPara = findViewById(R.id.radioButtonPara);
        radioButtonAdet = findViewById(R.id.radioButtonAdet);
        editTextAskidaTutar = findViewById(R.id.editTextAskidaTutar);
        editTextAskidaAdet = findViewById(R.id.editTextAskidaAdet);
        tutar = findViewById(R.id.tutar);
        adet = findViewById(R.id.adet);
        textViewAskidaSonuc = findViewById(R.id.textViewAskidaSonuc);
        buttonAskidaAlKaydet = findViewById(R.id.buttonAskidaAlKaydet);
        //-------------------------------------------------------------//
        textViewAskidaGuncelAdet = findViewById(R.id.textViewAskidaGuncelAdet);
        editTextAskidaVerilenAdet = findViewById(R.id.editTextAskidaVerilenAdet);
        buttonAskidaVerKaydet = findViewById(R.id.buttonAskidaVerKaydet);
        //-------------------------------------------------------------//

        adetCek();
        urunFiyatCek("Ekmek");
        stokCek("Ekmek");



        radioButtonPara.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    textViewAskidaSonuc.setText("");
                    tutar.setVisibility(View.VISIBLE);
                    editTextAskidaTutar.setVisibility(View.VISIBLE);
                    textViewAskidaSonuc.setVisibility(View.VISIBLE);
                    buttonAskidaAlKaydet.setVisibility(View.VISIBLE);

                    editTextAskidaTutar.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (editTextAskidaTutar.getText().toString().equals("")){
                                editTextAskidaTutar.setText("0");
                            }
                            double girilen = Double.parseDouble(editTextAskidaTutar.getText().toString());
                            String yazdir = Math.round((girilen/ekmekFiyat))+" adet ekmek";
                            textViewAskidaSonuc.setText(yazdir);
                            bagisAdet = (int) Math.round((girilen/ekmekFiyat));
                            bagisTutar = girilen;
                        }
                    });

                    adet.setVisibility(View.INVISIBLE);
                    editTextAskidaAdet.setVisibility(View.INVISIBLE);

                }

            }
        });

        radioButtonAdet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    textViewAskidaSonuc.setText("");
                    adet.setVisibility(View.VISIBLE);
                    editTextAskidaAdet.setVisibility(View.VISIBLE);
                    textViewAskidaSonuc.setVisibility(View.VISIBLE);
                    buttonAskidaAlKaydet.setVisibility(View.VISIBLE);

                    editTextAskidaAdet.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (editTextAskidaAdet.getText().toString().equals("")){
                                editTextAskidaAdet.setText("0");
                            }
                            int girilen = Integer.parseInt(editTextAskidaAdet.getText().toString());
                            String yazdir = (girilen*ekmekFiyat)+" TL değerinde ekmek bağışı.";
                            textViewAskidaSonuc.setText(yazdir);
                            bagisTutar = (girilen*ekmekFiyat);
                            bagisAdet = girilen;
                        }
                    });

                    tutar.setVisibility(View.INVISIBLE);
                    editTextAskidaTutar.setVisibility(View.INVISIBLE);

                }

            }
        });

        buttonAskidaAlKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sekil = new SimpleDateFormat("yyyy-MM-dd  hh:mm");
                Date tarih = new Date();

                paraGiris(String.valueOf(sekil.format(tarih)),bagisTutar);
                int guncelle = askidaEkmekAdet+bagisAdet;
                adetGuncelle(1,guncelle);

                Toast.makeText(getApplicationContext(),"Askıda Ekmek Alındı.",Toast.LENGTH_SHORT).show();

                onBackPressed();
            }
        });

        buttonAskidaVerKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adetCek();

                if (askidaEkmekAdet <= 0){
                    Toast.makeText(getApplicationContext(),"Askıda Ekmek Mevcut Değil.",Toast.LENGTH_SHORT).show();
                }else {
                    int guncelle = askidaEkmekAdet - Integer.parseInt(editTextAskidaVerilenAdet.getText().toString());
                    adetGuncelle(1,guncelle);

                    Toast.makeText(getApplicationContext(),"Askıda Ekmek Verildi.",Toast.LENGTH_SHORT).show();

                    int stokGuncelle = guncelEkmekStok - Integer.parseInt(editTextAskidaVerilenAdet.getText().toString());

                    stokGnclle(stokGuncelle);
                }

                onBackPressed();
            }
        });





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AskidaEkmekActivity.this,TezgahActivity.class));
    }

    public void urunFiyatCek(String urunAd){
        String url = "https://kristalekmek.com/urunler/urun_fiyat_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray urunFiyatList = jsonObject.getJSONArray("urunler");

                    for (int i=0;i<urunFiyatList.length();i++){
                        JSONObject k = urunFiyatList.getJSONObject(i);
                        ekmekFiyat = k.getDouble("urun_fiyat");
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
                params.put("urun_ad",urunAd);
                return params;
            }
        };
        Volley.newRequestQueue(AskidaEkmekActivity.this).add(istek);
    }

    public void paraGiris(String tarih_saat,Double tutar){
        String url = "https://kristalekmek.com/askida_ekmek/askida_ekmek_nakit_giris.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("askidaParaGrsResp",response);
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
                params.put("tutar",String.valueOf(tutar));

                return params;
            }
        };
        Volley.newRequestQueue(AskidaEkmekActivity.this).add(istek);
    }

    public void adetCek(){
        String url = "https://kristalekmek.com/askida_ekmek/adet_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("askida_ekmek");

                    for (int i=0;i<array.length();i++){
                        JSONObject k = array.getJSONObject(i);
                        askidaEkmekAdet = k.getInt("guncel_rakam");
                        textViewAskidaGuncelAdet.setText(String.valueOf(askidaEkmekAdet));
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
        Volley.newRequestQueue(AskidaEkmekActivity.this).add(istek);
    }

    public void adetGuncelle(int id,int guncelleRakam){
        String url = "https://kristalekmek.com/askida_ekmek/adet_guncelle.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("askidaGncelleResp",response);
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
                params.put("kayit_id",String.valueOf(id));
                params.put("guncel_rakam",String.valueOf(guncelleRakam));

                return params;
            }
        };
        Volley.newRequestQueue(AskidaEkmekActivity.this).add(istek);
    }

    public void stokCek(String urun_ad){
        String url = "https://kristalekmek.com/stok/stok_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("stok");

                    JSONObject k = jsonArray.getJSONObject(0);

                    guncelEkmekStok = k.getInt("urun_adet");


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
        Volley.newRequestQueue(AskidaEkmekActivity.this).add(istek);
    }

    public void stokGnclle(int adet){
        String url = "https://kristalekmek.com/stok/update_stok.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("StkGnclleAskdaRspns",response);
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

                params.put("urun_ad","Ekmek");
                params.put("urun_adet",String.valueOf(adet));

                return params;
            }
        };
        Volley.newRequestQueue(AskidaEkmekActivity.this).add(istek);
    }


}