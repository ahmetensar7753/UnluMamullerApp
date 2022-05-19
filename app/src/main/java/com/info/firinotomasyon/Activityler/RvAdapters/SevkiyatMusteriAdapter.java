package com.info.firinotomasyon.Activityler.RvAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.Classlar.TezgahMusteriler;
import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.Activityler.SevkiyatMusteriActivity;
import com.info.firinotomasyon.Activityler.SevkiyatSatisActivity;
import com.info.firinotomasyon.Activityler.SevkiyatSiparisActivity;
import com.info.firinotomasyon.Activityler.TezgahActivity;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class SevkiyatMusteriAdapter extends RecyclerView.Adapter<SevkiyatMusteriAdapter.CardViewSevkiyatMusteriNesneTutucu> implements Filterable {
    private Context mContext;
    private ArrayList<SevkiyatMusteriler> disaridanGelenMusterilerList;
    private ArrayList<SevkiyatMusteriler> fullList;
    public static SevkiyatMusteriler tiklananMusteri;


    public SevkiyatMusteriAdapter(Context mContext, ArrayList<SevkiyatMusteriler> disaridanGelenMusterilerList) {
        this.mContext = mContext;
        this.disaridanGelenMusterilerList = disaridanGelenMusterilerList;
        fullList = new ArrayList<>(disaridanGelenMusterilerList);
    }



    public class CardViewSevkiyatMusteriNesneTutucu extends RecyclerView.ViewHolder{
        private CardView cardViewSevkiyatMusteri;
        private TextView textViewSevkiyatCardMusteriAd;
        public CardViewSevkiyatMusteriNesneTutucu(@NonNull View itemView) {
            super(itemView);
            cardViewSevkiyatMusteri = itemView.findViewById(R.id.cardViewSevkiyatMusteri);
            textViewSevkiyatCardMusteriAd = itemView.findViewById(R.id.textViewSevkiyatCardMusteriAd);
        }
    }

    @NonNull
    @Override
    public CardViewSevkiyatMusteriNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_sevkiyat_musteriler,parent,false);
        return new CardViewSevkiyatMusteriNesneTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewSevkiyatMusteriNesneTutucu holder, int position) {
        SevkiyatMusteriler musteri = disaridanGelenMusterilerList.get(position);

        holder.textViewSevkiyatCardMusteriAd.setText(musteri.getMusteriAd());

        holder.cardViewSevkiyatMusteri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    tiklananMusteri = musteri; //  BURADAKİ ATAMA sebebi : İLGİLİ musteri BU CLASSTAKİ tiklnanMusteri nesnesine atanıyor. BU NESNEYE DE static erişim ile erişiliyor.
                    mContext.startActivity(new Intent(mContext, SevkiyatMusteriActivity.class));
                    ((Activity)mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return disaridanGelenMusterilerList.size();
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
            ArrayList<SevkiyatMusteriler> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (SevkiyatMusteriler item : fullList) {
                    if (item.getMusteriAd().toLowerCase().contains(filterPattern)) {
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
            disaridanGelenMusterilerList.clear();
            disaridanGelenMusterilerList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };





}
