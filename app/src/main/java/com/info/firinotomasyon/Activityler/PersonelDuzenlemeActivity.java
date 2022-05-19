package com.info.firinotomasyon.Activityler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.Personel;
import com.info.firinotomasyon.Activityler.RvAdapters.PersonelAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class PersonelDuzenlemeActivity extends AppCompatActivity {

    //GORSEL NESNE TANIMLARI
    private EditText editTextDuzenleAd,editTextDuzenleTel,editTextDuzenleSifre;
    private TextView textViewDuzenleId;
    private Button buttonDuzenleKaydet;
    private Spinner spinnerDuzenle;

    private ArrayList<String> spinnerList;
    private ArrayAdapter<String> veriAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personel_duzenleme);

        //GORSEL NESNE BAĞLANTILARI
        textViewDuzenleId = findViewById(R.id.textViewDuzenleId);
        editTextDuzenleAd = findViewById(R.id.editTextDuzenleAd);
        editTextDuzenleTel = findViewById(R.id.editTextDuzenleTel);
        editTextDuzenleSifre = findViewById(R.id.editTextDuzenleSifre);
        buttonDuzenleKaydet = findViewById(R.id.buttonDuzenleKaydet);
        spinnerDuzenle = findViewById(R.id.spinnerDuzenle);

        String personel_ad = PersonelAdapter.presonelAd;

        personelCek(personel_ad,PersonelDuzenlemeActivity.this);



        buttonDuzenleKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = spinnerList.get(spinnerDuzenle.getSelectedItemPosition());
                if (!a.equals("Seç...")){
                    int id = Integer.parseInt(textViewDuzenleId.getText().toString());
                    String ad = editTextDuzenleAd.getText().toString();
                    String tel = editTextDuzenleTel.getText().toString();
                    String sifre = editTextDuzenleSifre.getText().toString();
                    String alan = spinnerList.get(spinnerDuzenle.getSelectedItemPosition());

                    personelDuzenle(id,ad,tel,sifre,alan);

                    Toast.makeText(PersonelDuzenlemeActivity.this,"Personel Düzenlendi.",Toast.LENGTH_SHORT).show();

                    onBackPressed();
                }else {
                    Toast.makeText(PersonelDuzenlemeActivity.this,"Bilgileri Kontrol Et!",Toast.LENGTH_SHORT).show();
                }


            }
        });




    }

    public void personelCek(String personel_ad, Context mContext){
        String url = "https://kristalekmek.com/personel/personel_cek_ada_gore.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                spinnerList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray personeller = jsonObject.getJSONArray("personel");

                    for (int i = 0;i<personeller.length();i++){
                        JSONObject k = personeller.getJSONObject(i);

                        int perID = k.getInt("personel_id");
                        String perTel = k.getString("personel_tel");
                        String perSifre = k.getString("personel_sifre");
                        String calisma_alan = k.getString("calisma_alan");

                        textViewDuzenleId.setText(String.valueOf(perID));
                        editTextDuzenleAd.setText(personel_ad);
                        editTextDuzenleTel.setText(perTel);
                        editTextDuzenleSifre.setText(perSifre);

                        spinnerList.add("Seç...");
                        spinnerList.add("admin");
                        spinnerList.add("imalat");
                        spinnerList.add("sevkiyat");
                        spinnerList.add("tezgah");
                    }

                    veriAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,android.R.id.text1, spinnerList);
                    spinnerDuzenle.setAdapter(veriAdapter);



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
                params.put("personel_ad",personel_ad);
                return params;
            }
        };
        Volley.newRequestQueue(PersonelDuzenlemeActivity.this).add(istek);
    }

    public void personelDuzenle(int personel_id,String personel_ad,String personel_tel,String personel_sifre,String calisma_alan){
        String url = "https://kristalekmek.com/personel/personel_duzenle.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("persnDznlRespns",response);
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

                params.put("personel_id",String.valueOf(personel_id));
                params.put("personel_ad",personel_ad);
                params.put("personel_tel",personel_tel);
                params.put("personel_sifre",personel_sifre);
                params.put("calisma_alan",calisma_alan);

                return params;
            }
        };
        Volley.newRequestQueue(PersonelDuzenlemeActivity.this).add(istek);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PersonelDuzenlemeActivity.this,AdminPersonelActivity.class));
    }


}