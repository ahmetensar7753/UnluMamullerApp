package com.info.firinotomasyon.Activityler.RvAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.info.firinotomasyon.Activityler.AdminActivity;
import com.info.firinotomasyon.Activityler.Classlar.SevkiyatUrunler;
import com.info.firinotomasyon.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminSevkiyatUrunlerAdapter extends RecyclerView.Adapter<AdminSevkiyatUrunlerAdapter.CardViewAdminSevkiyatUrunlerTutucu> {
    private Context mContext;
    private ArrayList<SevkiyatUrunler> disaridanGelenUrunler = new ArrayList<>();

    public AdminSevkiyatUrunlerAdapter(Context mContext, ArrayList<SevkiyatUrunler> disaridanGelenUrunler) {
        this.mContext = mContext;
        this.disaridanGelenUrunler = disaridanGelenUrunler;
    }

    public class CardViewAdminSevkiyatUrunlerTutucu extends RecyclerView.ViewHolder {
        TextView textViewAdminSevkiyatUrunId,textViewAdminSevkiyatUrunAd,textViewAdminSevkiyatUrunSil;
        public CardViewAdminSevkiyatUrunlerTutucu(@NonNull View itemView) {
            super(itemView);

            textViewAdminSevkiyatUrunId = itemView.findViewById(R.id.textViewAdminSevkiyatUrunId);
            textViewAdminSevkiyatUrunAd = itemView.findViewById(R.id.textViewAdminSevkiyatUrunAd);
            textViewAdminSevkiyatUrunSil = itemView.findViewById(R.id.textViewAdminSevkiyatUrunSil);

        }
    }

    @NonNull
    @Override
    public CardViewAdminSevkiyatUrunlerTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_admin_sevkiyat_urunler,parent,false);
        return new CardViewAdminSevkiyatUrunlerTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdminSevkiyatUrunlerTutucu holder, int position) {
        SevkiyatUrunler urun = disaridanGelenUrunler.get(position);


        holder.textViewAdminSevkiyatUrunId.setText(String.valueOf(urun.getUrun_id()));
        holder.textViewAdminSevkiyatUrunAd.setText(urun.getUrun_ad());

        holder.textViewAdminSevkiyatUrunSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urunSil(urun.getUrun_id());
                Toast.makeText(mContext,"Ürün Silindi.",Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext.getApplicationContext(), AdminActivity.class));
                ((Activity)mContext).finish();
            }
        });




    }

    @Override
    public int getItemCount() {
        return disaridanGelenUrunler.size();
    }

    public void urunSil(int urun_id){
        String url = "https://kristalekmek.com/sevkiyat_urunler/delete.php";
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

                params.put("urun_id",String.valueOf(urun_id));

                return params;
            }
        };
        Volley.newRequestQueue(mContext).add(istek);
    }





}
