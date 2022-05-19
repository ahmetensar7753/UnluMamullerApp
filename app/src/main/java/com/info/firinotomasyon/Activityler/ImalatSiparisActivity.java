package com.info.firinotomasyon.Activityler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.info.firinotomasyon.R;

public class ImalatSiparisActivity extends AppCompatActivity {
    private Button buttonImalatSiparisler,buttonImalatToplamSayilar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imalat_siparis);

        buttonImalatToplamSayilar = findViewById(R.id.buttonImalatToplamSayilar);
        buttonImalatSiparisler = findViewById(R.id.buttonImalatSiparisler);


        buttonImalatToplamSayilar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImalatSiparisActivity.this,ImalatToplamSayilarActivity.class));
                finish();
            }
        });

        buttonImalatSiparisler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImalatSiparisActivity.this,ImalatSiparisDetayActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ImalatSiparisActivity.this,ImalatActivity.class));
    }



}