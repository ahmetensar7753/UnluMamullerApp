package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.Giderler;
import com.info.firinotomasyon.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GiderAdapter extends RecyclerView.Adapter<GiderAdapter.CardViewGiderNesneTutucu>{

    private Context mContext;
    private ArrayList<Giderler> giderlerDisaridanGelenArrayList = new ArrayList<>();

    public GiderAdapter(Context mContext, ArrayList<Giderler> giderlerArrayList) {
        this.mContext = mContext;
        this.giderlerDisaridanGelenArrayList = giderlerArrayList;
    }

    public class CardViewGiderNesneTutucu extends RecyclerView.ViewHolder{
        public TextView textViewGiderTarih,textViewGiderKalem,textViewGiderTutar;
        public CardViewGiderNesneTutucu(@NonNull View itemView) {
            super(itemView);
            textViewGiderTarih = itemView.findViewById(R.id.textViewGiderTarih);
            textViewGiderKalem = itemView.findViewById(R.id.textViewGiderKalem);
            textViewGiderTutar = itemView.findViewById(R.id.textViewGiderTutar);
        }
    }

    @NonNull
    @Override
    public CardViewGiderNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_gider,parent,false);
        return new CardViewGiderNesneTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewGiderNesneTutucu holder, int position) {
        final Giderler gider = giderlerDisaridanGelenArrayList.get(position);

        holder.textViewGiderTarih.setText(gider.getTarih());
        holder.textViewGiderKalem.setText(gider.getGider_ad());
        holder.textViewGiderTutar.setText(String.valueOf((int)gider.getGider_tutar())+" TL");


    }

    @Override
    public int getItemCount() {
        return giderlerDisaridanGelenArrayList.size();
    }





}
