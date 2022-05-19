package com.info.firinotomasyon.Activityler.RvAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.BakiyeMusteriActivity;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.SevkiyatMusteriActivity;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class BakiyeMusteriAdapter extends RecyclerView.Adapter<BakiyeMusteriAdapter.CardViewBakiyeNesneTutucu> implements Filterable {
    private Context mContext;
    private ArrayList<SevkiyatMusteriler> disaridanGelenMusterilerList;
    private ArrayList<SevkiyatMusteriler> fullList;
    public static SevkiyatMusteriler tiklananMusteri;

    public BakiyeMusteriAdapter(Context mContext, ArrayList<SevkiyatMusteriler> disaridanGelenMusterilerList) {
        this.mContext = mContext;
        this.disaridanGelenMusterilerList = disaridanGelenMusterilerList;
        fullList = new ArrayList<>(disaridanGelenMusterilerList);
    }

    public class CardViewBakiyeNesneTutucu extends RecyclerView.ViewHolder{
        private CardView cardViewSevkiyatMusteri;
        private TextView textViewSevkiyatCardMusteriAd;
        public CardViewBakiyeNesneTutucu(@NonNull View itemView) {
            super(itemView);
            cardViewSevkiyatMusteri = itemView.findViewById(R.id.cardViewSevkiyatMusteri);
            textViewSevkiyatCardMusteriAd = itemView.findViewById(R.id.textViewSevkiyatCardMusteriAd);
        }
    }



    @NonNull
    @Override
    public CardViewBakiyeNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_sevkiyat_musteriler,parent,false);
        return new CardViewBakiyeNesneTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewBakiyeNesneTutucu holder, int position) {

        SevkiyatMusteriler musteri = disaridanGelenMusterilerList.get(position);

        holder.textViewSevkiyatCardMusteriAd.setText(musteri.getMusteriAd());

        holder.cardViewSevkiyatMusteri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiklananMusteri = musteri; //  BURADAKİ ATAMA sebebi : İLGİLİ musteri BU CLASSTAKİ tiklnanMusteri nesnesine atanıyor. BU NESNEYE DE static erişim ile erişiliyor.
                 mContext.startActivity(new Intent(mContext, BakiyeMusteriActivity.class));
                ((Activity)mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return disaridanGelenMusterilerList.size();
    }

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
