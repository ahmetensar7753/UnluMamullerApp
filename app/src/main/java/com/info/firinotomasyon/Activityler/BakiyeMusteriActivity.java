package com.info.firinotomasyon.Activityler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.info.firinotomasyon.Activityler.Classlar.FragmentOdenenler;
import com.info.firinotomasyon.Activityler.Classlar.FragmentOdenmeyenler;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.RvAdapters.BakiyeMusteriAdapter;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class BakiyeMusteriActivity extends AppCompatActivity {

    //GORSEL NESNE TANIMLARI
    private Toolbar toolbarBakiyeMusteri;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> fragmentBaslikList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakiye_musteri);

        SevkiyatMusteriler gelenMusteri = BakiyeMusteriAdapter.tiklananMusteri;

        //GORSEL NESNE BAĞLANMASI.
        toolbarBakiyeMusteri = findViewById(R.id.toolbarBakiyeMusteri);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);

        fragmentList.add(new FragmentOdenmeyenler());
        fragmentList.add(new FragmentOdenenler());

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        fragmentBaslikList.add("Ödenmeyenler");
        fragmentBaslikList.add("Ödenenler");

        new TabLayoutMediator(tabLayout,viewPager2, (tab,position)->tab.setText(fragmentBaslikList.get(position))).attach();



        // toolbar işlemleri
        toolbarBakiyeMusteri.setTitle(gelenMusteri.getMusteriAd());
        setSupportActionBar(toolbarBakiyeMusteri);


    }

    //Menu tasarımı baglanıyor.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bakiye_menu,menu);
        return true;
    }
    // Menudeki actionlara tıklanma kontrolleri burada yapılıyor.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        startActivity(new Intent(BakiyeMusteriActivity.this,SevkiyatActivity.class));
        finish();

        return super.onOptionsItemSelected(item);
    }

    private class MyViewPagerAdapter extends FragmentStateAdapter{

        public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
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
        startActivity(new Intent(BakiyeMusteriActivity.this,SevkiyatActivity.class));
    }
}