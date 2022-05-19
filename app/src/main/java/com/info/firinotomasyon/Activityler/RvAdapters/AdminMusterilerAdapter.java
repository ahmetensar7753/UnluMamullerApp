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

import com.info.firinotomasyon.Activityler.AdminMusteriDetayActivity;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class AdminMusterilerAdapter extends RecyclerView.Adapter<AdminMusterilerAdapter.CardViewAdminMusteriTutucu>{
    private Context mContext;
    private ArrayList<SevkiyatMusteriler> disaridanGelenMusteriList = new ArrayList<>();

    public static SevkiyatMusteriler tiklananMusteri;

    public AdminMusterilerAdapter(Context mContext, ArrayList<SevkiyatMusteriler> disaridanGelenMusteriList) {
        this.mContext = mContext;
        this.disaridanGelenMusteriList = disaridanGelenMusteriList;
    }

    public class CardViewAdminMusteriTutucu extends RecyclerView.ViewHolder {
        TextView textViewAdminMusteriId,textViewAdminMusteriAd,textViewAdminMusteriTel,textViewAdminMusteriAdres,textViewAdminMusteriToplamBorc;
        CardView cardViewAdminMusteriler;

        public CardViewAdminMusteriTutucu(@NonNull View itemView) {
            super(itemView);
            textViewAdminMusteriId = itemView.findViewById(R.id.textViewAdminMusteriId);
            textViewAdminMusteriAd = itemView.findViewById(R.id.textViewAdminMusteriAd);
            textViewAdminMusteriTel = itemView.findViewById(R.id.textViewAdminMusteriTel);
            textViewAdminMusteriAdres = itemView.findViewById(R.id.textViewAdminMusteriAdres);
            textViewAdminMusteriToplamBorc = itemView.findViewById(R.id.textViewAdminMusteriToplamBorc);
            cardViewAdminMusteriler = itemView.findViewById(R.id.cardViewAdminMusteriler);
        }
    }

    @NonNull
    @Override
    public CardViewAdminMusteriTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_admin_musteriler,parent,false);
        return new CardViewAdminMusteriTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdminMusteriTutucu holder, int position) {
        SevkiyatMusteriler musteri = disaridanGelenMusteriList.get(position);

        holder.textViewAdminMusteriId.setText(String.valueOf(musteri.getMusteriId()));
        holder.textViewAdminMusteriAd.setText(musteri.getMusteriAd());
        holder.textViewAdminMusteriTel.setText(musteri.getMusteriTelefon());
        holder.textViewAdminMusteriAdres.setText(musteri.getMusteriAdres());
        holder.textViewAdminMusteriToplamBorc.setText(String.valueOf(musteri.getMusteriToplamBorc()));

        holder.cardViewAdminMusteriler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiklananMusteri = musteri;
                mContext.startActivity(new Intent(mContext, AdminMusteriDetayActivity.class));
                ((Activity)mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return disaridanGelenMusteriList.size();
    }

}
