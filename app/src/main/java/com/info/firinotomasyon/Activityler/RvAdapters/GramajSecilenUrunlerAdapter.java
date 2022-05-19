package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.GramajUrunler;
import com.info.firinotomasyon.Activityler.TezgahGramajActivity;
import com.info.firinotomasyon.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GramajSecilenUrunlerAdapter extends RecyclerView.Adapter<GramajSecilenUrunlerAdapter.CardViewNesneleriTutucuSecilenUrunlerGramaj>{

    private Context mContext;
    public static ArrayList<GramajUrunler> gramajUrunlerSecilenDisaridanGelenList = new ArrayList<>();

    public GramajSecilenUrunlerAdapter(Context mContext, ArrayList<GramajUrunler> gramajUrunlerSecilenDisaridanGelenList) {
        this.mContext = mContext;
        this.gramajUrunlerSecilenDisaridanGelenList = gramajUrunlerSecilenDisaridanGelenList;
    }

    public class CardViewNesneleriTutucuSecilenUrunlerGramaj extends RecyclerView.ViewHolder{
        public ImageView imageViewGramajSecilenUrunResim;
        public TextView textViewGramajSecilenUrunAd;
        public TextView textViewGramajSecilenUrunlerAgirlik;
        public CardView cardViewGramajSecilenUrunler;
        public CardViewNesneleriTutucuSecilenUrunlerGramaj(@NonNull View itemView) {
            super(itemView);
            imageViewGramajSecilenUrunResim = itemView.findViewById(R.id.imageViewGramajSecilenUrunResim);
            textViewGramajSecilenUrunAd = itemView.findViewById(R.id.textViewGramajSecilenUrunAd);
            textViewGramajSecilenUrunlerAgirlik = itemView.findViewById(R.id.textViewGramajSecilenUrunlerAgirlik);
            cardViewGramajSecilenUrunler = itemView.findViewById(R.id.cardViewGramajSecilenUrunler);
        }
    }

    @NonNull
    @Override
    public CardViewNesneleriTutucuSecilenUrunlerGramaj onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_gramaj_secilen_urunler,parent,false);
        return new CardViewNesneleriTutucuSecilenUrunlerGramaj(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewNesneleriTutucuSecilenUrunlerGramaj holder, int position) {
        final GramajUrunler gramajUrunSecilen = gramajUrunlerSecilenDisaridanGelenList.get(position);

        holder.textViewGramajSecilenUrunAd.setText(gramajUrunSecilen.getUrun_ad());
        holder.textViewGramajSecilenUrunlerAgirlik.setText(String.valueOf(gramajUrunSecilen.getUrun_agirlik()));

        String url = "https://kristalekmek.com/gramaj_urunler/resimler/"+gramajUrunSecilen.getResim_ad()+".png";
        Picasso.get().load(url).into(holder.imageViewGramajSecilenUrunResim);

        holder.cardViewGramajSecilenUrunler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cardViewGramajSecilenUrunler.removeAllViews();
                double agirlik = gramajUrunSecilen.getUrun_agirlik();
                TezgahGramajActivity.textViewGramajTutar.setText(String.valueOf(Double.parseDouble(TezgahGramajActivity.textViewGramajTutar.getText().toString()) - (agirlik*gramajUrunSecilen.getUrun_fiyat())));
                gramajUrunSecilen.setUrun_agirlik(0.0);

            }
        });
        if (gramajUrunSecilen.getUrun_agirlik() == 0.0){ holder.cardViewGramajSecilenUrunler.removeAllViews();}


    }

    @Override
    public int getItemCount() {
        return gramajUrunlerSecilenDisaridanGelenList.size();
    }







}
