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

import com.info.firinotomasyon.Activityler.AdminPersonelActivity;
import com.info.firinotomasyon.Activityler.Classlar.Personel;
import com.info.firinotomasyon.Activityler.PersonelDuzenlemeActivity;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class PersonelAdapter extends RecyclerView.Adapter<PersonelAdapter.CardViewPersonelTutucu> {
    private Context mContext;
    private ArrayList<Personel> disaridanGelenPersoneller = new ArrayList<>();
    public static String presonelAd;

    public PersonelAdapter(Context mContext, ArrayList<Personel> disaridanGelenPersoneller) {
        this.mContext = mContext;
        this.disaridanGelenPersoneller = disaridanGelenPersoneller;
    }

    public class CardViewPersonelTutucu extends RecyclerView.ViewHolder {
        TextView textViewPersonelid,textViewPersonelAd,textViewPersonelTel,textViewPersonelParolaa,textViewPersonelAlann;
        CardView cardViewPersonel;
        public CardViewPersonelTutucu(@NonNull View itemView) {
            super(itemView);

            textViewPersonelid = itemView.findViewById(R.id.textViewPersonelid);
            textViewPersonelAd = itemView.findViewById(R.id.textViewPersonelAd);
            textViewPersonelTel = itemView.findViewById(R.id.textViewPersonelTel);
            textViewPersonelParolaa = itemView.findViewById(R.id.textViewPersonelParolaa);
            textViewPersonelAlann = itemView.findViewById(R.id.textViewPersonelAlann);
            cardViewPersonel = itemView.findViewById(R.id.cardViewPersonel);

        }
    }

    @NonNull
    @Override
    public CardViewPersonelTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_personel,parent,false);
        return new CardViewPersonelTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewPersonelTutucu holder, int position) {
        Personel personel = disaridanGelenPersoneller.get(position);

        holder.textViewPersonelid.setText(String.valueOf(personel.getPersonel_id()));
        holder.textViewPersonelAd.setText(String.valueOf(personel.getPersonel_ad()));
        holder.textViewPersonelTel.setText(String.valueOf(personel.getPersonel_tel()));
        holder.textViewPersonelParolaa.setText(String.valueOf(personel.getPersonel_sifre()));
        holder.textViewPersonelAlann.setText(String.valueOf(personel.getPersonel_alan()));

        holder.cardViewPersonel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presonelAd = personel.getPersonel_ad();
                mContext.startActivity(new Intent(mContext.getApplicationContext(),PersonelDuzenlemeActivity.class));
                ((Activity)mContext).finish();

            }
        });

    }

    @Override
    public int getItemCount() {
        return disaridanGelenPersoneller.size();
    }




}
