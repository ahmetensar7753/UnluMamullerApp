package com.info.firinotomasyon.Activityler.RvAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.Activityler.UrunDuzenleActivity;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class AdminUrunlerAdapter extends RecyclerView.Adapter<AdminUrunlerAdapter.CardViewAdminUrunlerTutucu>{
    private Context mContext;
    private ArrayList<Urunler> disaridanGelenUrunler = new ArrayList<>();

    public static Urunler tiklananUrun;

    public AdminUrunlerAdapter(Context mContext, ArrayList<Urunler> disaridanGelenUrunler) {
        this.mContext = mContext;
        this.disaridanGelenUrunler = disaridanGelenUrunler;
    }

    public class CardViewAdminUrunlerTutucu extends RecyclerView.ViewHolder {

        TextView textViewAdminUrunId,textViewAdminUrunAd,textViewAdminUrunResimAd,textViewAdminUrunFiyat,textViewAdminUrunKimlik;
        CardView cardViewAdminUrun;
        public CardViewAdminUrunlerTutucu(@NonNull View itemView) {
            super(itemView);

            textViewAdminUrunId = itemView.findViewById(R.id.textViewAdminUrunId);
            textViewAdminUrunAd = itemView.findViewById(R.id.textViewAdminUrunAd);
            textViewAdminUrunResimAd = itemView.findViewById(R.id.textViewAdminUrunResimAd);
            textViewAdminUrunFiyat = itemView.findViewById(R.id.textViewAdminUrunFiyat);
            textViewAdminUrunKimlik = itemView.findViewById(R.id.textViewAdminUrunKimlik);
            cardViewAdminUrun = itemView.findViewById(R.id.cardViewAdminUrun);
        }
    }

    @NonNull
    @Override
    public CardViewAdminUrunlerTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_admin_urunler,parent,false);
        return new CardViewAdminUrunlerTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdminUrunlerTutucu holder, int position) {
        Urunler urun = disaridanGelenUrunler.get(position);

        holder.textViewAdminUrunId.setText(String.valueOf(urun.getUrun_id()));
        holder.textViewAdminUrunAd.setText(urun.getUrun_ad());
        holder.textViewAdminUrunResimAd.setText(urun.getUrun_resim_ad());
        holder.textViewAdminUrunFiyat.setText(String.valueOf(urun.getUrun_fiyat()));
        holder.textViewAdminUrunKimlik.setText(urun.getKimlik());

        holder.cardViewAdminUrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tiklananUrun = urun;
                mContext.startActivity(new Intent(mContext, UrunDuzenleActivity.class));
                ((Activity)mContext).finish();

            }
        });


    }

    @Override
    public int getItemCount() {
        return disaridanGelenUrunler.size();
    }





}
