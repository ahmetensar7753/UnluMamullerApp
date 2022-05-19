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
import com.info.firinotomasyon.Activityler.Classlar.FragmentMusteriEkle;
import com.info.firinotomasyon.Activityler.Classlar.FragmentMusteriler;
import com.info.firinotomasyon.Activityler.Classlar.FragmentPersonelEkle;
import com.info.firinotomasyon.Activityler.Classlar.FragmentPersoneller;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class AdminMusteriActivity extends AppCompatActivity {

    private TabLayout tabLayoutAdminMusteriler;
    private ViewPager2 viewPager2AdminMusteriler;

    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> fragmentBaslikList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_musteri);

        tabLayoutAdminMusteriler = findViewById(R.id.tabLayoutAdminMusteriler);
        viewPager2AdminMusteriler = findViewById(R.id.viewPager2AdminMusteriler);

        fragmentList.add(new FragmentMusteriler());
        fragmentList.add(new FragmentMusteriEkle());

        MusterilerViewPagerAdapter adapterMusteri = new MusterilerViewPagerAdapter(this);
        viewPager2AdminMusteriler.setAdapter(adapterMusteri);

        fragmentBaslikList.add("Müşteriler");
        fragmentBaslikList.add("Müşteri Ekle");

        new TabLayoutMediator(tabLayoutAdminMusteriler,viewPager2AdminMusteriler, (tab, position)->tab.setText(fragmentBaslikList.get(position))).attach();


    }


    private class MusterilerViewPagerAdapter extends FragmentStateAdapter {
        public MusterilerViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
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
        startActivity(new Intent(AdminMusteriActivity.this,AdminActivity.class));
    }


}