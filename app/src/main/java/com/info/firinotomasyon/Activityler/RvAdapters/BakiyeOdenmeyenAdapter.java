package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class BakiyeOdenmeyenAdapter extends RecyclerView.Adapter<BakiyeOdenmeyenAdapter.CardNesneTutucuOdenmeyen>{
    private Context mContext;
    private ArrayList<SevkiyatUrunler> disaridanGelenUrunList = new ArrayList<>();

    public BakiyeOdenmeyenAdapter(Context mContext, ArrayList<SevkiyatUrunler> disaridanGelenUrunList) {
        this.mContext = mContext;
        this.disaridanGelenUrunList = disaridanGelenUrunList;
    }

    public class CardNesneTutucuOdenmeyen extends RecyclerView.ViewHolder{
        TextView textViewBakiyeUrunAd,textViewBakiyeUrunAdet,textViewBakiyeUrunIade,textViewBakiyeAlindigiTarih,textViewBakiyeToplamFiyat;
        public CardNesneTutucuOdenmeyen(@NonNull View itemView) {
            super(itemView);
            textViewBakiyeUrunAd = itemView.findViewById(R.id.textViewBakiyeUrunAd);
            textViewBakiyeUrunAdet = itemView.findViewById(R.id.textViewBakiyeUrunAdet);
            textViewBakiyeUrunIade = itemView.findViewById(R.id.textViewBakiyeUrunIade);
            textViewBakiyeAlindigiTarih = itemView.findViewById(R.id.textViewBakiyeAlindigiTarih);
            textViewBakiyeToplamFiyat = itemView.findViewById(R.id.textViewBakiyeToplamFiyat);
        }
    }

    @NonNull
    @Override
    public CardNesneTutucuOdenmeyen onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_odenmeyenler,parent,false);
        return new CardNesneTutucuOdenmeyen(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardNesneTutucuOdenmeyen holder, int position) {
        SevkiyatUrunler item = disaridanGelenUrunList.get(position);

        holder.textViewBakiyeUrunAd.setText(item.getUrun_ad());
        holder.textViewBakiyeUrunAdet.setText(String.valueOf(item.getUrun_adet()));
        holder.textViewBakiyeUrunIade.setText(String.valueOf(item.getUrun_iade()));
        holder.textViewBakiyeAlindigiTarih.setText(item.getAlindigi_tarih());
        holder.textViewBakiyeToplamFiyat.setText(String.valueOf(item.getToplam_fiyat()));

    }

    @Override
    public int getItemCount() {
        return disaridanGelenUrunList.size();
    }






}
