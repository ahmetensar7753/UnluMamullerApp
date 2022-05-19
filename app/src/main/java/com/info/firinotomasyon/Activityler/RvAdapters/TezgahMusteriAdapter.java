package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.TezgahMusteriler;
import com.info.firinotomasyon.Activityler.TezgahActivity;
import com.info.firinotomasyon.Activityler.TezgahMusteriActivity;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class TezgahMusteriAdapter extends RecyclerView.Adapter<TezgahMusteriAdapter.CardViewMusteriNesneTutucu> implements Filterable {
    private Context mContext;
    private ArrayList<TezgahMusteriler> dataSet;
    private ArrayList<TezgahMusteriler> fullList;

    public TezgahMusteriAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public TezgahMusteriAdapter(ArrayList<TezgahMusteriler> itemList){
        this.dataSet = itemList;
        fullList = new ArrayList<>(itemList);
    }



    public class CardViewMusteriNesneTutucu extends RecyclerView.ViewHolder{
        public TextView textViewMusteriAdSoyad;
        public TextView textViewMusteriTelefon;
        public TextView textViewMusteriSonOdemeTarih;
        public TextView textViewMusteriGuncelBorc;
        public CardView cardViewMusteri;

        public CardViewMusteriNesneTutucu(@NonNull View itemView) {
            super(itemView);
            textViewMusteriAdSoyad = itemView.findViewById(R.id.textViewMusteriAdSoyad);
            textViewMusteriTelefon = itemView.findViewById(R.id.textViewMusteriTelefon);
            textViewMusteriSonOdemeTarih = itemView.findViewById(R.id.textViewMusteriSonOdemeTarih);
            textViewMusteriGuncelBorc = itemView.findViewById(R.id.textViewMusteriGuncelBorc);
            cardViewMusteri = itemView.findViewById(R.id.cardViewMusteri);
        }
    }

    @NonNull
    @Override
    public CardViewMusteriNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_tezgah_musteri,parent,false);
        return new CardViewMusteriNesneTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewMusteriNesneTutucu holder, int position) {
        TezgahMusteriler musteri = dataSet.get(position);
        holder.textViewMusteriAdSoyad.setText(musteri.getMusteri_ad_soyad().toString());
        holder.textViewMusteriTelefon.setText(musteri.getMusteri_telefon().toString());
        holder.textViewMusteriSonOdemeTarih.setText(musteri.getSon_odeme_tarihi().toString());
        holder.textViewMusteriGuncelBorc.setText(String.valueOf(musteri.getGuncel_borc()));

        holder.cardViewMusteri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TezgahMusteriActivity.musterii = musteri.getMusteri_ad_soyad();
                TezgahActivity.textViewMusteriAd.setText(musteri.getMusteri_ad_soyad());
                TezgahMusteriActivity.buttonMusteriGeriDon.callOnClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    // BURADA CARDVİEW LARIN FİLTRELENEBİLMESİ İÇİN Filter-Serached_Filter kullanılıyor.
    //performFiltering içerisinde filterelenmiş listeyi tutabileceğimiz bir arrayList tanımlanıyor.
    //PARAMETREDEN GELEN CharSequence SAYESİNDE FİLTRELEME İŞLENİYOR.
    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }

    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<TezgahMusteriler> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (TezgahMusteriler item : fullList) {
                    if (item.getMusteri_ad_soyad().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataSet.clear();
            dataSet.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

}
