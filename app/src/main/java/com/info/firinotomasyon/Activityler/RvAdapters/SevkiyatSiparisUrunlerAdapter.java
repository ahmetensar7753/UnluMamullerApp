package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class SevkiyatSiparisUrunlerAdapter extends RecyclerView.Adapter<SevkiyatSiparisUrunlerAdapter.CardViewSiparisUrunNesneTutucu>{

    private Context mContext;
    private ArrayList<SevkiyatUrunler> disaridanGelenListe = new ArrayList<>();
    public static ArrayList<SevkiyatUrunler> disariyaGidenList = new ArrayList<>();

    public SevkiyatSiparisUrunlerAdapter(Context mContext, ArrayList<SevkiyatUrunler> disaridanGelenListe) {
        this.mContext = mContext;
        this.disaridanGelenListe = disaridanGelenListe;
    }

    public class CardViewSiparisUrunNesneTutucu extends RecyclerView.ViewHolder {

        public TextView textViewSiparisUrunAd;
        public EditText editTextSiparisUrunAdet;

        public CardViewSiparisUrunNesneTutucu(@NonNull View itemView) {
            super(itemView);
            textViewSiparisUrunAd = itemView.findViewById(R.id.textViewSiparisUrunAd);
            editTextSiparisUrunAdet = itemView.findViewById(R.id.editTextSiparisUrunAdet);
        }
    }

    @NonNull
    @Override
    public CardViewSiparisUrunNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_sevkiyat_siparis_urunler,parent,false);
        return new CardViewSiparisUrunNesneTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewSiparisUrunNesneTutucu holder, int position) {
        SevkiyatUrunler urun = disaridanGelenListe.get(position);

        holder.textViewSiparisUrunAd.setText(urun.getUrun_ad());
        holder.editTextSiparisUrunAdet.setText(String.valueOf(urun.getUrun_adet()));

        disariyaGidenList.add(urun);

        holder.editTextSiparisUrunAdet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.editTextSiparisUrunAdet.getText().toString().isEmpty()){
                    holder.editTextSiparisUrunAdet.setText("0");
                }
                urun.setUrun_adet(Integer.parseInt(holder.editTextSiparisUrunAdet.getText().toString()));
                disariyaGidenList.remove(urun);
                disariyaGidenList.add(urun);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return disaridanGelenListe.size();
    }





}
