package com.info.firinotomasyon.Activityler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.info.firinotomasyon.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminActivity extends AppCompatActivity {

    //GORSEL NESNE TANIMLARI

    private TextView textViewAdminTarih;
    private Button buttonAdminUrunIslemleri,buttonAdminRaporlar,buttonAdminPersonelislemleri,buttonAdminMusteriIslemleri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //GORSEL NESNELERİN BAGLANMASI

        textViewAdminTarih = findViewById(R.id.textViewAdminTarih);
        buttonAdminUrunIslemleri = findViewById(R.id.buttonAdminUrunIslemleri);
        buttonAdminRaporlar = findViewById(R.id.buttonAdminRaporlar);
        buttonAdminPersonelislemleri = findViewById(R.id.buttonAdminPersonelislemleri);
        buttonAdminMusteriIslemleri = findViewById(R.id.buttonAdminMusteriIslemleri);

        SimpleDateFormat sekil = new SimpleDateFormat("dd/MM/yyyy");
        Date tarih = new Date();
        textViewAdminTarih.setText(String.valueOf(sekil.format(tarih)));

        buttonAdminUrunIslemleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,AdminUrunlerActivity.class));
                finish();
            }
        });

        buttonAdminRaporlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminRaporlarActivity.class));
                finish();
            }
        });

        buttonAdminPersonelislemleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,AdminPersonelActivity.class));
                finish();
            }
        });

        buttonAdminMusteriIslemleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,AdminMusteriActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(AdminActivity.this);

        ab.setIcon(R.drawable.uyari_amblem);
        ab.setTitle("GİRİŞ EKRANINA DÖNÜLSÜN MÜ ?");

        ab.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(AdminActivity.this,MainActivity.class));
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