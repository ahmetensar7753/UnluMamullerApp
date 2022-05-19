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
import com.info.firinotomasyon.Activityler.Classlar.FragmentOdenenler;
import com.info.firinotomasyon.Activityler.Classlar.FragmentPersonelEkle;
import com.info.firinotomasyon.Activityler.Classlar.FragmentPersoneller;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class AdminPersonelActivity extends AppCompatActivity {
    private TabLayout tabLayoutPersonel;
    private ViewPager2 viewPager2Personel;

    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> fragmentBaslikList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_personel);

        tabLayoutPersonel = findViewById(R.id.tabLayoutPersonel);
        viewPager2Personel = findViewById(R.id.viewPager2Personel);

        fragmentList.add(new FragmentPersoneller());
        fragmentList.add(new FragmentPersonelEkle());

        PersonelViewPagerAdapter adapter = new PersonelViewPagerAdapter(this);
        viewPager2Personel.setAdapter(adapter);

        fragmentBaslikList.add("Personeller");
        fragmentBaslikList.add("Personel Ekle");

        new TabLayoutMediator(tabLayoutPersonel,viewPager2Personel, (tab, position)->tab.setText(fragmentBaslikList.get(position))).attach();


    }

    private class PersonelViewPagerAdapter extends FragmentStateAdapter{

        public PersonelViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
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
        startActivity(new Intent(AdminPersonelActivity.this,AdminActivity.class));
    }



}