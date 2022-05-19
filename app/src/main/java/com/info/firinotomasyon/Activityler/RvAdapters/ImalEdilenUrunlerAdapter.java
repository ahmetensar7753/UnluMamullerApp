package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class ImalEdilenUrunlerAdapter extends RecyclerView.Adapter<ImalEdilenUrunlerAdapter.CardViewImalEdilenNesneTutucu>{

    private Context mContext;
    private ArrayList<Urunler> disaridanGelenListe = new ArrayList<>();

    public ImalEdilenUrunlerAdapter(Context mContext, ArrayList<Urunler> disaridanGelenListe) {
        this.mContext = mContext;
        this.disaridanGelenListe = disaridanGelenListe;
    }

    public class CardViewImalEdilenNesneTutucu extends RecyclerView.ViewHolder{
        TextView textViewImalEdilenUrunAd,textViewImalEdilenUrunAdet;
        public CardViewImalEdilenNesneTutucu(@NonNull View itemView) {
            super(itemView);
            textViewImalEdilenUrunAd = itemView.findViewById(R.id.textViewImalEdilenUrunAd);
            textViewImalEdilenUrunAdet = itemView.findViewById(R.id.textViewImalEdilenUrunAdet);
        }
    }

    @NonNull
    @Override
    public CardViewImalEdilenNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_imal_edilenler,parent,false);
        return new CardViewImalEdilenNesneTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewImalEdilenNesneTutucu holder, int position) {
        Urunler urun = disaridanGelenListe.get(position);

        holder.textViewImalEdilenUrunAd.setText(urun.getUrun_ad());
        holder.textViewImalEdilenUrunAdet.setText(String.valueOf(urun.getUrun_adet()));

    }

    @Override
    public int getItemCount() {
        return disaridanGelenListe.size();
    }





}
