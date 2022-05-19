package com.info.firinotomasyon.Activityler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.info.firinotomasyon.R;

public class AdminRaporlarActivity extends AppCompatActivity {
    private Button buttonAdminOzetIstatistik,buttonAdminUrunIstatistik,buttonAdminPersonelistatistik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_raporlar);

        buttonAdminOzetIstatistik = findViewById(R.id.buttonAdminOzetIstatistik);
        buttonAdminUrunIstatistik = findViewById(R.id.buttonAdminUrunIstatistik);
        buttonAdminPersonelistatistik = findViewById(R.id.buttonAdminPersonelistatistik);

        buttonAdminOzetIstatistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRaporlarActivity.this,AdminOzetIstatistiklerActivity.class));
                finish();
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminRaporlarActivity.this,AdminActivity.class));
    }

}