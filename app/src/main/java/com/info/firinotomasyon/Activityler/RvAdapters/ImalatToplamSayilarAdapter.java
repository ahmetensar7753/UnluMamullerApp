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

public class ImalatToplamSayilarAdapter extends RecyclerView.Adapter<ImalatToplamSayilarAdapter.CardViewToplamSayiTutucu>{

    private Context mContext;
    private ArrayList<Urunler> disaridanGelenList = new ArrayList<>();

    public ImalatToplamSayilarAdapter(Context mContext, ArrayList<Urunler> disaridanGelenList) {
        this.mContext = mContext;
        this.disaridanGelenList = disaridanGelenList;
    }

    public class CardViewToplamSayiTutucu extends RecyclerView.ViewHolder {
        TextView textViewImalatToplamSayıAd,textViewImalatToplamSayıAdet;
        public CardViewToplamSayiTutucu(@NonNull View itemView) {
            super(itemView);
            textViewImalatToplamSayıAd = itemView.findViewById(R.id.textViewImalatToplamSayıAd);
            textViewImalatToplamSayıAdet = itemView.findViewById(R.id.textViewImalatToplamSayıAdet);
        }
    }

    @NonNull
    @Override
    public CardViewToplamSayiTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_imalat_toplam_sayilar,parent,false);
        return new CardViewToplamSayiTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewToplamSayiTutucu holder, int position) {
        Urunler urun = disaridanGelenList.get(position);

        int adet = (int) urun.getUrun_adet();

        holder.textViewImalatToplamSayıAd.setText(urun.getUrun_ad());
        holder.textViewImalatToplamSayıAdet.setText(String.valueOf(adet));

    }

    @Override
    public int getItemCount() {
        return disaridanGelenList.size();
    }



}
