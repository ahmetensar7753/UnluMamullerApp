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
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.Activityler.SevkiyatMusteriActivity;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SevkiyatMusteriUrunlerAdapter extends RecyclerView.Adapter<SevkiyatMusteriUrunlerAdapter.CardViewSevkiyatUruntutucu>{

    private Context mContext;
    private ArrayList<SevkiyatUrunler> disaridanGelenSevkiyatUrunler = new ArrayList<>();



    public SevkiyatMusteriUrunlerAdapter(Context mContext, ArrayList<SevkiyatUrunler> disaridanGelenSevkiyatUrunler) {
        this.mContext = mContext;
        this.disaridanGelenSevkiyatUrunler = disaridanGelenSevkiyatUrunler;
    }

    public class CardViewSevkiyatUruntutucu extends RecyclerView.ViewHolder{
        public TextView textView_urun_ad,textView_toplam_fiyat;
        public EditText editText_urun_iade;
        public TextView editText_urun_adet;
        public CardView cardViewSevkiyatMusteriUrunler;
        public CardViewSevkiyatUruntutucu(@NonNull View itemView) {
            super(itemView);
            textView_urun_ad = itemView.findViewById(R.id.textView_urun_ad);
            editText_urun_adet = itemView.findViewById(R.id.editText_urun_adet);
            editText_urun_iade = itemView.findViewById(R.id.editText_urun_iade);
            textView_toplam_fiyat = itemView.findViewById(R.id.textView_toplam_fiyat);
            cardViewSevkiyatMusteriUrunler = itemView.findViewById(R.id.cardViewSevkiyatMusteriUrunler);
        }
    }

    @NonNull
    @Override
    public CardViewSevkiyatUruntutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_sevkiyat_musteri_urunler,parent,false);
       return new CardViewSevkiyatUruntutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewSevkiyatUruntutucu holder, int position) {
       final SevkiyatUrunler sevkiyatUrun = disaridanGelenSevkiyatUrunler.get(position);

       holder.textView_urun_ad.setText(sevkiyatUrun.getUrun_ad().toString());
       holder.editText_urun_adet.setText(String.valueOf(sevkiyatUrun.getUrun_adet()));


       sevkiyatUrun.setToplam_fiyat((sevkiyatUrun.getUrun_adet()-sevkiyatUrun.getUrun_iade())*sevkiyatUrun.getUrun_fiyat());

       holder.textView_toplam_fiyat.setText(String.valueOf(sevkiyatUrun.getToplam_fiyat()));

       holder.editText_urun_iade.setText("");




       holder.editText_urun_iade.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
           }

           @Override
           public void afterTextChanged(Editable s) {
               if (holder.editText_urun_iade.getText().toString().isEmpty()){
                   holder.editText_urun_iade.setText("0");
               }
               //SevkiyatMusteriActivity.textViewGunlukToplam.setText(String.valueOf(Double.parseDouble(SevkiyatMusteriActivity.textViewGunlukToplam.getText().toString())-sevkiyatUrun.getToplam_fiyat()));
               sevkiyatUrun.setUrun_iade(Integer.parseInt(holder.editText_urun_iade.getText().toString()));
               sevkiyatUrun.setToplam_fiyat((sevkiyatUrun.getUrun_adet()-sevkiyatUrun.getUrun_iade())*sevkiyatUrun.getUrun_fiyat());
               holder.textView_toplam_fiyat.setText(String.valueOf(sevkiyatUrun.getToplam_fiyat()));
               //SevkiyatMusteriActivity.textViewGunlukToplam.setText(String.valueOf(Double.parseDouble(SevkiyatMusteriActivity.textViewGunlukToplam.getText().toString())+sevkiyatUrun.getToplam_fiyat()));
           }
       });
       /*holder.editText_urun_adet.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
           }

           @Override
           public void afterTextChanged(Editable s) {
               if (!holder.editText_urun_adet.getText().toString().isEmpty()){
                   //SevkiyatMusteriActivity.textViewGunlukToplam.setText(String.valueOf(Double.parseDouble(SevkiyatMusteriActivity.textViewGunlukToplam.getText().toString())-sevkiyatUrun.getToplam_fiyat()));
                   sevkiyatUrun.setUrun_adet(Integer.parseInt(holder.editText_urun_adet.getText().toString()));
                   sevkiyatUrun.setToplam_fiyat((sevkiyatUrun.getUrun_adet()-sevkiyatUrun.getUrun_iade())*sevkiyatUrun.getUrun_fiyat());
                   holder.textView_toplam_fiyat.setText(String.valueOf(sevkiyatUrun.getToplam_fiyat()));
                   //SevkiyatMusteriActivity.textViewGunlukToplam.setText(String.valueOf(Double.parseDouble(SevkiyatMusteriActivity.textViewGunlukToplam.getText().toString())+sevkiyatUrun.getToplam_fiyat()));
               }else {
                   //SevkiyatMusteriActivity.textViewGunlukToplam.setText(String.valueOf(Double.parseDouble(SevkiyatMusteriActivity.textViewGunlukToplam.getText().toString())-sevkiyatUrun.getToplam_fiyat()));
                   sevkiyatUrun.setToplam_fiyat(0.0);
                   holder.textView_toplam_fiyat.setText(String.valueOf(sevkiyatUrun.getToplam_fiyat()));
               }

           }
       });*/

    }

    @Override
    public int getItemCount() {
        return disaridanGelenSevkiyatUrunler.size();
    }





}
