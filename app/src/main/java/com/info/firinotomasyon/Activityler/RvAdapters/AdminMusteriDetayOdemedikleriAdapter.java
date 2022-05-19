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

public class AdminMusteriDetayOdemedikleriAdapter extends RecyclerView.Adapter<AdminMusteriDetayOdemedikleriAdapter.CardViewDetayOdemedigiUrunTutucu>{
    private Context mContext;
    private ArrayList<SevkiyatUrunler> disaridanGelenUrunList = new ArrayList<>();

    public AdminMusteriDetayOdemedikleriAdapter(Context mContext, ArrayList<SevkiyatUrunler> disaridanGelenUrunList) {
        this.mContext = mContext;
        this.disaridanGelenUrunList = disaridanGelenUrunList;
    }

    public class CardViewDetayOdemedigiUrunTutucu extends RecyclerView.ViewHolder {
        TextView textViewDetayUrunAd,textViewDetayUrunAdet,textViewDetayUrunIade,textViewDetayAldigiFiyat,textViewDetayToplamFiyat,textViewDetayTarih;
        public CardViewDetayOdemedigiUrunTutucu(@NonNull View itemView) {
            super(itemView);

            textViewDetayUrunAd = itemView.findViewById(R.id.textViewDetayUrunAd);
            textViewDetayUrunAdet = itemView.findViewById(R.id.textViewDetayUrunAdet);
            textViewDetayUrunIade = itemView.findViewById(R.id.textViewDetayUrunIade);
            textViewDetayAldigiFiyat = itemView.findViewById(R.id.textViewDetayAldigiFiyat);
            textViewDetayToplamFiyat = itemView.findViewById(R.id.textViewDetayToplamFiyat);
            textViewDetayTarih = itemView.findViewById(R.id.textViewDetayTarih);
        }
    }

    @NonNull
    @Override
    public CardViewDetayOdemedigiUrunTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_admin_musteri_detay_odemedikleri,parent,false);
        return new CardViewDetayOdemedigiUrunTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewDetayOdemedigiUrunTutucu holder, int position) {
        SevkiyatUrunler urun = disaridanGelenUrunList.get(position);

        holder.textViewDetayUrunAd.setText(urun.getUrun_ad());
        holder.textViewDetayUrunAdet.setText(String.valueOf(urun.getUrun_adet()));
        holder.textViewDetayUrunIade.setText(String.valueOf(urun.getUrun_iade()));
        holder.textViewDetayAldigiFiyat.setText(String.valueOf(urun.getUrun_fiyat()));
        holder.textViewDetayToplamFiyat.setText(String.valueOf(urun.getToplam_fiyat()));
        holder.textViewDetayTarih.setText(urun.getAlindigi_tarih());

    }

    @Override
    public int getItemCount() {
        return disaridanGelenUrunList.size();
    }





}
