package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class BakiyeOdenenAdapter extends RecyclerView.Adapter<BakiyeOdenenAdapter.CardTutucuOdenen>{
    private Context mContext;
    private ArrayList<SevkiyatUrunler> disaridanGelenList = new ArrayList<>();

    public BakiyeOdenenAdapter(Context mContext, ArrayList<SevkiyatUrunler> disaridanGelenList) {
        this.mContext = mContext;
        this.disaridanGelenList = disaridanGelenList;
    }

    public class CardTutucuOdenen extends RecyclerView.ViewHolder{
        TextView textViewOdenenUrunAd,textViewOdenenUrunAdet,textViewOdenenUrunIade,textViewOdenenOdedigiTarih,textViewOdenenToplamFiyat;
        public CardTutucuOdenen(@NonNull View itemView) {
            super(itemView);
            textViewOdenenUrunAd = itemView.findViewById(R.id.textViewOdenenUrunAd);
            textViewOdenenUrunAdet = itemView.findViewById(R.id.textViewOdenenUrunAdet);
            textViewOdenenUrunIade = itemView.findViewById(R.id.textViewOdenenUrunIade);
            textViewOdenenOdedigiTarih = itemView.findViewById(R.id.textViewOdenenOdedigiTarih);
            textViewOdenenToplamFiyat = itemView.findViewById(R.id.textViewOdenenToplamFiyat);

        }
    }

    @NonNull
    @Override
    public CardTutucuOdenen onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_odenenler,parent,false);
        return new CardTutucuOdenen(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardTutucuOdenen holder, int position) {
        SevkiyatUrunler item = disaridanGelenList.get(position);

        holder.textViewOdenenUrunAd.setText(item.getUrun_ad());
        holder.textViewOdenenUrunAdet.setText(String.valueOf(item.getUrun_adet()));
        holder.textViewOdenenUrunIade.setText(String.valueOf(item.getUrun_iade()));
        holder.textViewOdenenOdedigiTarih.setText(item.getOdendigi_tarih());
        holder.textViewOdenenToplamFiyat.setText(String.valueOf(item.getToplam_fiyat()));
    }

    @Override
    public int getItemCount() {
        return disaridanGelenList.size();
    }





}
