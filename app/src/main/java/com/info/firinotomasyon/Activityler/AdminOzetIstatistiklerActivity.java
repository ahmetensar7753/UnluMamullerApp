package com.info.firinotomasyon.Activityler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.info.firinotomasyon.Activityler.Classlar.FragmentImalat;
import com.info.firinotomasyon.Activityler.Classlar.FragmentSevkiyat;
import com.info.firinotomasyon.Activityler.Classlar.FragmentTezgah;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class AdminOzetIstatistiklerActivity extends AppCompatActivity {

    //GORSEL NESNE TANIMLARI
    private TabLayout tabLayoutAdminRaporlar;
    private ViewPager2 viewPager2AdminRaporlar;


    private ArrayList<Fragment> fragmentListe = new ArrayList<>();
    private ArrayList<String> fragmentBaslikListe = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ozet_istatistikler);

        //GORSEL NESNE BAĞLANTILARI
        tabLayoutAdminRaporlar = findViewById(R.id.tabLayoutAdminRaporlar);
        viewPager2AdminRaporlar = findViewById(R.id.viewPager2AdminRaporlar);




        fragmentListe.add(new FragmentTezgah());
        fragmentListe.add(new FragmentSevkiyat());
        fragmentListe.add(new FragmentImalat());

        RaporlarViewPagerAdapter raporlarAdapter = new RaporlarViewPagerAdapter(this);
        viewPager2AdminRaporlar.setAdapter(raporlarAdapter);

        fragmentBaslikListe.add("Tezgah Rapor");
        fragmentBaslikListe.add("Sevkiyat Rapor");
        fragmentBaslikListe.add("Imalathane Rapor");

        new TabLayoutMediator(tabLayoutAdminRaporlar,viewPager2AdminRaporlar, (tab, position)->tab.setText(fragmentBaslikListe.get(position))).attach();


    }


    private class RaporlarViewPagerAdapter extends FragmentStateAdapter {
        public RaporlarViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentListe.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentListe.size();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminOzetIstatistiklerActivity.this,AdminRaporlarActivity.class));
    }


}