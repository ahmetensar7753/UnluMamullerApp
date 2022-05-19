package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.TezgahMusteriler;
import com.info.firinotomasyon.Activityler.TezgahMusteriActivity;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class TezgahMusteriOdeAdapter extends RecyclerView.Adapter<TezgahMusteriOdeAdapter.CardViewNesneTutucuMusteriOde>{
    private Context mContext;
    private ArrayList<TezgahMusteriler> disaridanGelenList;
    public static String musteriAd="";
    private TextView textViewMusteriOde;

    public TezgahMusteriOdeAdapter(Context mContext, ArrayList<TezgahMusteriler> disaridanGelenList,TextView textViewMusteriOde) {
        this.mContext = mContext;
        this.disaridanGelenList = disaridanGelenList;
        this.textViewMusteriOde = textViewMusteriOde;
    }

    public class CardViewNesneTutucuMusteriOde extends RecyclerView.ViewHolder {
        public TextView textViewMusteriOdeAd,textViewMusteriOdeBorc;
        public CardView cardViewMusteriOde;
        public CardViewNesneTutucuMusteriOde(@NonNull View itemView) {
            super(itemView);
            textViewMusteriOdeAd = itemView.findViewById(R.id.textViewMusteriOdeAd);
            textViewMusteriOdeBorc = itemView.findViewById(R.id.textViewMusteriOdeBorc);
            cardViewMusteriOde = itemView.findViewById(R.id.cardViewMusteriOde);
        }
    }

    @NonNull
    @Override
    public CardViewNesneTutucuMusteriOde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_musteri_ode,parent,false);
        return new CardViewNesneTutucuMusteriOde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewNesneTutucuMusteriOde holder, int position) {
        TezgahMusteriler musteri = disaridanGelenList.get(position);
        holder.textViewMusteriOdeAd.setText(musteri.getMusteri_ad_soyad());
        holder.textViewMusteriOdeBorc.setText(String.valueOf(musteri.getGuncel_borc()));

        holder.cardViewMusteriOde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewMusteriOde.setText(musteri.getMusteri_ad_soyad());
            }
        });



    }

    @Override
    public int getItemCount() {
        return disaridanGelenList.size();
    }




}
