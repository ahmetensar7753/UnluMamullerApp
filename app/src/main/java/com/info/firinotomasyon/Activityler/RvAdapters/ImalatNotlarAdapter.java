package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.NotClass;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class ImalatNotlarAdapter extends RecyclerView.Adapter<ImalatNotlarAdapter.CardViewNotTutucu>{

    private Context mContext;
    private ArrayList<NotClass> disaridanGelenMesajlar = new ArrayList<>();

    public ImalatNotlarAdapter(Context mContext, ArrayList<NotClass> disaridanGelenMesajlar) {
        this.mContext = mContext;
        this.disaridanGelenMesajlar = disaridanGelenMesajlar;
    }

    public class CardViewNotTutucu extends RecyclerView.ViewHolder {
        TextView textViewNotTarihSaat,textViewImalatNotIcerik;
        public CardViewNotTutucu(@NonNull View itemView) {
            super(itemView);
            textViewNotTarihSaat = itemView.findViewById(R.id.textViewNotTarihSaat);
            textViewImalatNotIcerik = itemView.findViewById(R.id.textViewImalatNotIcerik);
        }
    }

    @NonNull
    @Override
    public CardViewNotTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_imalat_not,parent,false);
        return new CardViewNotTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewNotTutucu holder, int position) {
        NotClass not = disaridanGelenMesajlar.get(position);

        holder.textViewNotTarihSaat.setText(not.getNot_tarih());
        holder.textViewImalatNotIcerik.setText(not.getNot_icerik());

    }

    @Override
    public int getItemCount() {
        return disaridanGelenMesajlar.size();
    }




}
