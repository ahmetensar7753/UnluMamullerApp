package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.Activityler.SevkiyatMusteriOdemeActivity;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class SevkiyatOdemeAdapter extends RecyclerView.Adapter<SevkiyatOdemeAdapter.CardViewMusteriOdemeNesneTutucu>{

    private Context mContext;
    private ArrayList<SevkiyatUrunler> disaridanGelenler = new ArrayList<>();
    public static ArrayList<SevkiyatUrunler> isaretliUrunler = new ArrayList<>();
    private boolean check;

    public SevkiyatOdemeAdapter(Context mContext, ArrayList<SevkiyatUrunler> disaridanGelenler,boolean check) {
        this.mContext = mContext;
        this.disaridanGelenler = disaridanGelenler;
        this.check = check;
    }

    public class CardViewMusteriOdemeNesneTutucu extends RecyclerView.ViewHolder{

        public TextView textViewOdemeUrunAd,textViewOdemeUrunAdet,textViewOdemeUrunIade,textViewOdemeToplamFiyat;
        public final CheckBox checkBox2;


        public CardViewMusteriOdemeNesneTutucu(@NonNull View itemView) {
            super(itemView);

            textViewOdemeUrunAd = itemView.findViewById(R.id.textViewOdemeUrunAd);
            textViewOdemeUrunAdet = itemView.findViewById(R.id.textViewOdemeUrunAdet);
            textViewOdemeUrunIade = itemView.findViewById(R.id.textViewOdemeUrunIade);
            textViewOdemeToplamFiyat = itemView.findViewById(R.id.textViewOdemeToplamFiyat);
            checkBox2 = itemView.findViewById(R.id.checkBox2);

        }
    }

    @NonNull
    @Override
    public CardViewMusteriOdemeNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_sevkiyat_odeme,parent,false);
        return new CardViewMusteriOdemeNesneTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewMusteriOdemeNesneTutucu holder, int position) {
        SevkiyatUrunler urun = disaridanGelenler.get(position);

        double urunFiyat = urun.getToplam_fiyat();

        holder.textViewOdemeUrunAd.setText(urun.getUrun_ad());
        holder.textViewOdemeUrunAdet.setText(String.valueOf(urun.getUrun_adet()));
        holder.textViewOdemeUrunIade.setText(String.valueOf(urun.getUrun_iade()));
        holder.textViewOdemeToplamFiyat.setText(String.valueOf(urun.getToplam_fiyat()));

        if (check){
            holder.checkBox2.setChecked(true);
            urun.setSeciliMi(true);
        }else{
            holder.checkBox2.setChecked(false);
            urun.setSeciliMi(false);
        }


        holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    urun.setSeciliMi(true);
                }else {
                    urun.setSeciliMi(false);
                }

                if (urun.getSeciliMi()){
                    SevkiyatMusteriOdemeActivity.textViewSevkiyatOdemeToplam.setText(String.valueOf(Double.parseDouble(SevkiyatMusteriOdemeActivity.textViewSevkiyatOdemeToplam.getText().toString())+urunFiyat));
                    isaretliUrunler.add(urun);
                }else {
                    SevkiyatMusteriOdemeActivity.textViewSevkiyatOdemeToplam.setText(String.valueOf(Double.parseDouble(SevkiyatMusteriOdemeActivity.textViewSevkiyatOdemeToplam.getText().toString())-urunFiyat));
                    isaretliUrunler.remove(urun);
                }


                /*if (isChecked){
                    //BURADA ÜRÜN TİKLENDİĞİNDE İLGİLİ ÜRÜN TEXTVİEW'E EKLENİYOR.
                    SevkiyatMusteriOdemeActivity.textViewSevkiyatOdemeToplam.setText(String.valueOf(Double.parseDouble(SevkiyatMusteriOdemeActivity.textViewSevkiyatOdemeToplam.getText().toString())+urunFiyat));
                    isaretliUrunler.add(urun);
                }else {
                    // BURADA ÜRÜNÜN TİKİ KALDIRILDIĞINDAN O ÜRÜNÜN TOPLAM FİYATI TEXTVİEW'DAN ÇIAKRILIYOR.
                    SevkiyatMusteriOdemeActivity.textViewSevkiyatOdemeToplam.setText(String.valueOf(Double.parseDouble(SevkiyatMusteriOdemeActivity.textViewSevkiyatOdemeToplam.getText().toString())-urunFiyat));
                    isaretliUrunler.remove(urun);
                }*/

            }
        });




    }

    @Override
    public int getItemCount() {
        return disaridanGelenler.size();
    }





}
