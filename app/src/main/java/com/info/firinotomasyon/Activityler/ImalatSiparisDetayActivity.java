package com.info.firinotomasyon.Activityler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.info.firinotomasyon.Activityler.Classlar.FragmentGramajUrunler;
import com.info.firinotomasyon.Activityler.Classlar.FragmentServis1;
import com.info.firinotomasyon.Activityler.Classlar.FragmentServis2;
import com.info.firinotomasyon.Activityler.Classlar.FragmentServis3;
import com.info.firinotomasyon.Activityler.Classlar.FragmentServis4;
import com.info.firinotomasyon.Activityler.Classlar.FragmentSevkiyatUrunler;
import com.info.firinotomasyon.Activityler.Classlar.FragmentUrunEkle;
import com.info.firinotomasyon.Activityler.Classlar.FragmentUrunler;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class ImalatSiparisDetayActivity extends AppCompatActivity {

    private TabLayout tabLayoutImalatSiparisServisler;
    private ViewPager2 viewPager2ImalatSiparisServisler;

    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> fragmentBaslikList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imalat_siparis_detay);

        tabLayoutImalatSiparisServisler = findViewById(R.id.tabLayoutImalatSiparisServisler);
        viewPager2ImalatSiparisServisler = findViewById(R.id.viewPager2ImalatSiparisServisler);


        fragmentList.add(new FragmentServis1());
        fragmentList.add(new FragmentServis2());
        fragmentList.add(new FragmentServis3());
        fragmentList.add(new FragmentServis4());


        ServisSiraViewPagerAdapter adapter = new ServisSiraViewPagerAdapter(ImalatSiparisDetayActivity.this);
        viewPager2ImalatSiparisServisler.setAdapter(adapter);

        fragmentBaslikList.add("___ 1.Servis ___");
        fragmentBaslikList.add("___ 2.Servis ___");
        fragmentBaslikList.add("___ 3.Servis ___");
        fragmentBaslikList.add("___ 4.Servis ___");

        new TabLayoutMediator(tabLayoutImalatSiparisServisler,viewPager2ImalatSiparisServisler, (tab, position)->tab.setText(fragmentBaslikList.get(position))).attach();



    }

    private class ServisSiraViewPagerAdapter extends FragmentStateAdapter {
        public ServisSiraViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ImalatSiparisDetayActivity.this,ImalatSiparisActivity.class));
    }


}