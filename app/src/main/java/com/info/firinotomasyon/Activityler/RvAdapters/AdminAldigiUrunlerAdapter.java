package com.info.firinotomasyon.Activityler.RvAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.AdminMusteriDetayActivity;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminAldigiUrunlerAdapter extends RecyclerView.Adapter<AdminAldigiUrunlerAdapter.CardViewAdminAldigiUrunTutucu>{
    private Context mContext;
    private int musteri_id;
    public static ArrayList<SevkiyatUrunler> disaridanGelenUrunler = new ArrayList<>();
    public static ArrayList<SevkiyatUrunler> disariyaGidenList = new ArrayList<>();

    public AdminAldigiUrunlerAdapter(Context mContext, ArrayList<SevkiyatUrunler> disaridanGelenUrunler,int musteri_id) {
        this.mContext = mContext;
        this.disaridanGelenUrunler = disaridanGelenUrunler;
        this.musteri_id = musteri_id;
    }

    public class CardViewAdminAldigiUrunTutucu extends RecyclerView.ViewHolder {
        TextView textViewAdminAldigiUrunAd;
        EditText editTextTextAdminAldigiUrunAdet,editTextTextAldigiUrunFiyat;
        Button buttonAdminAldigiUrunSil;
        public CardViewAdminAldigiUrunTutucu(@NonNull View itemView) {
            super(itemView);
            textViewAdminAldigiUrunAd = itemView.findViewById(R.id.textViewAdminAldigiUrunAd);
            editTextTextAdminAldigiUrunAdet = itemView.findViewById(R.id.editTextTextAdminAldigiUrunAdet);
            editTextTextAldigiUrunFiyat = itemView.findViewById(R.id.editTextTextAldigiUrunFiyat);
            buttonAdminAldigiUrunSil = itemView.findViewById(R.id.buttonAdminAldigiUrunSil);
        }
    }

    @NonNull
    @Override
    public CardViewAdminAldigiUrunTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_admin_aldigi_urunler,parent,false);
        return new CardViewAdminAldigiUrunTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdminAldigiUrunTutucu holder, int position) {
        SevkiyatUrunler urun = disaridanGelenUrunler.get(position);

        holder.textViewAdminAldigiUrunAd.setText(urun.getUrun_ad());
        holder.editTextTextAdminAldigiUrunAdet.setText(String.valueOf(urun.getUrun_adet()));
        holder.editTextTextAldigiUrunFiyat.setText(String.valueOf(urun.getUrun_fiyat()));

        holder.buttonAdminAldigiUrunSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urunSil(musteri_id,urun.getUrun_ad());
                mContext.startActivity(new Intent(mContext, AdminMusteriDetayActivity.class));
                ((Activity)mContext).finish();
            }
        });

        holder.editTextTextAdminAldigiUrunAdet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!holder.editTextTextAdminAldigiUrunAdet.getText().toString().trim().equals("")){
                    urun.setUrun_adet(Integer.parseInt(holder.editTextTextAdminAldigiUrunAdet.getText().toString()));
                    disariyaGidenList.remove(urun);
                    disariyaGidenList.add(urun);
                }
            }
        });

        holder.editTextTextAldigiUrunFiyat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!holder.editTextTextAldigiUrunFiyat.getText().toString().trim().equals("")){
                    urun.setUrun_fiyat(Double.parseDouble(holder.editTextTextAldigiUrunFiyat.getText().toString()));
                    disariyaGidenList.remove(urun);
                    disariyaGidenList.add(urun);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return disaridanGelenUrunler.size();
    }

    public void urunSil(int id,String urunad){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/delete_aldigi_urun.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("musteri_id",String.valueOf(id));
                params.put("urun_ad",urunad);

                return params;
            }
        };
        Volley.newRequestQueue(mContext).add(istek);
    }





}
