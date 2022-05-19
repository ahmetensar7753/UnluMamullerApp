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
import com.info.firinotomasyon.Activityler.Classlar.FragmentSevkiyatUrunler;
import com.info.firinotomasyon.Activityler.Classlar.FragmentUrunEkle;
import com.info.firinotomasyon.Activityler.Classlar.FragmentUrunler;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class AdminUrunlerActivity extends AppCompatActivity {
    private TabLayout tabLayoutUrunler;
    private ViewPager2 viewPager2Urunler;

    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> fragmentBaslikList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_urunler);

        tabLayoutUrunler = findViewById(R.id.tabLayoutUrunler);
        viewPager2Urunler = findViewById(R.id.viewPager2Urunler);

        fragmentList.add(new FragmentUrunler());
        fragmentList.add(new FragmentGramajUrunler());
        fragmentList.add(new FragmentUrunEkle());
        fragmentList.add(new FragmentSevkiyatUrunler());



        UrunlerViewPagerAdapter adapter = new UrunlerViewPagerAdapter(this);
        viewPager2Urunler.setAdapter(adapter);

        fragmentBaslikList.add("Ürunler");
        fragmentBaslikList.add("Gramaj Ürünler");
        fragmentBaslikList.add("Ürün Ekle");
        fragmentBaslikList.add("Sevkiyat Ürünler");



        new TabLayoutMediator(tabLayoutUrunler,viewPager2Urunler, (tab, position)->tab.setText(fragmentBaslikList.get(position))).attach();

    }

    private class UrunlerViewPagerAdapter extends FragmentStateAdapter {
        public UrunlerViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
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
        startActivity(new Intent(AdminUrunlerActivity.this,AdminActivity.class));
    }


}