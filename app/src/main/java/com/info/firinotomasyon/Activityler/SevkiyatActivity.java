package com.info.firinotomasyon.Activityler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.info.firinotomasyon.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SevkiyatActivity extends AppCompatActivity {
    //GÖRSEL NESNELERİN TANIMLARI
    private Button buttonSatis,buttonSiparis,buttonSevkiyatServisSirasiBelirle,buttonBakiyeGoruntuleme,buttonSevkiyatRaporVer;
    private TextView textViewSevkiyatTarihSaat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevkiyat);

        //Gelen personel Alınıyor.
        Intent gelenIntent = getIntent();
        String personelID = gelenIntent.getStringExtra("personel");



        //GOSEL NESNELERİN BAĞLANMASI
        buttonSatis = findViewById(R.id.buttonSatis);
        buttonSiparis = findViewById(R.id.buttonSiparis);
        buttonSevkiyatServisSirasiBelirle = findViewById(R.id.buttonSevkiyatServisSirasiBelirle);
        buttonBakiyeGoruntuleme = findViewById(R.id.buttonBakiyeGoruntuleme);
        textViewSevkiyatTarihSaat = findViewById(R.id.textViewSevkiyatTarihSaat);
        buttonSevkiyatRaporVer = findViewById(R.id.buttonSevkiyatRaporVer);


        SimpleDateFormat gorunum = new SimpleDateFormat("dd/MM/yyyy");
        Date tarih = new Date();
        textViewSevkiyatTarihSaat.setText(String.valueOf(gorunum.format(tarih)));


        buttonSatis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SevkiyatActivity.this,SevkiyatSatisActivity.class));
                finish();
            }
        });

        buttonSiparis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SevkiyatActivity.this,SevkiyatSiparisActivity.class));
                finish();
            }
        });

        buttonSevkiyatServisSirasiBelirle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SevkiyatActivity.this,SevkiyatSiparisSirasiActivity.class));
                finish();
            }
        });

        buttonBakiyeGoruntuleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SevkiyatActivity.this,BakiyeActivity.class));
                finish();
            }
        });

        buttonSevkiyatRaporVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tasarim = getLayoutInflater().inflate(R.layout.sevkiyat_rapor_gonder_alert,null);
                EditText editTextSevkiyatRaporParaTutar = tasarim.findViewById(R.id.editTextSevkiyatRaporParaTutar);
                EditText editTextSevkiyatRaporIade = tasarim.findViewById(R.id.editTextSevkiyatRaporIade);

                AlertDialog.Builder builder = new AlertDialog.Builder(SevkiyatActivity.this);

                builder.setTitle("GÜN SONU RAPORU");
                builder.setView(tasarim);

                builder.setPositiveButton("GÖNDER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SimpleDateFormat sekil = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date tarih = new Date();

                        if (!editTextSevkiyatRaporParaTutar.getText().toString().trim().equals("") && !editTextSevkiyatRaporIade.getText().toString().trim().equals("")){
                            sevkiyatRaporGonder(Double.parseDouble(editTextSevkiyatRaporParaTutar.getText().toString())
                                    ,Integer.parseInt(editTextSevkiyatRaporIade.getText().toString())
                                    ,String.valueOf(sekil.format(tarih)),Integer.parseInt(personelID));

                            Toast.makeText(SevkiyatActivity.this,"RAPOR GÖNDERİLDİ.",Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(SevkiyatActivity.this,"BAŞARISIZ- BOŞ ALAN BIRAKMA !",Toast.LENGTH_SHORT).show();
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


            }
        });

    }


    public void sevkiyatRaporGonder(Double para,int iade,String tarih,int islemi_yapan_id){
        String url = "https://kristalekmek.com/sevkiyat_rapor/insert_sevkiyat_rapor.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("svkytRprGnderRspns",response);

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

                params.put("teslim_edilen_para",String.valueOf(para));
                params.put("teslim_edilen_iade",String.valueOf(iade));
                params.put("rapor_tarih",tarih);
                params.put("islemi_yapan_id",String.valueOf(islemi_yapan_id));

                return params;
            }
        };
        Volley.newRequestQueue(SevkiyatActivity.this).add(istek);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(SevkiyatActivity.this);

        ab.setIcon(R.drawable.uyari_amblem);
        ab.setTitle("GİRİŞ EKRANINA DÖNÜLSÜN MÜ ?");

        ab.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(SevkiyatActivity.this,MainActivity.class));
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