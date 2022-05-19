package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.Activityler.TezgahActivity;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StokAdapter extends RecyclerView.Adapter<StokAdapter.CardViewStokNesneleriniTutucu> {

    //Dışarıdan veri alabilmek için constructor ve context tanımı.
    private Context mContext;
    private List<Urunler> urunlerDisaridanGelen;
    private int adet=0;

    public StokAdapter(Context mContext, List<Urunler> urunlerDisaridanGelen) {
        this.mContext = mContext;
        this.urunlerDisaridanGelen = urunlerDisaridanGelen;
    }

    //CardView tasarımının Adapter'a bağlanması ve tasarım üzerindeki görsel nesnelerin tanıtılması.

    public class CardViewStokNesneleriniTutucu extends RecyclerView.ViewHolder{
        public TextView textViewStokUrunAd;
        public TextView textViewStokUrunAdet;

        public CardViewStokNesneleriniTutucu(@NonNull View itemView) {
            super(itemView);
            textViewStokUrunAd = itemView.findViewById(R.id.textViewStokUrunAd);
            textViewStokUrunAdet = itemView.findViewById(R.id.textViewStokUrunAdet);
        }

    }


    @NonNull
    @Override
    public CardViewStokNesneleriniTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_stok,parent,false);
        return new CardViewStokNesneleriniTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewStokNesneleriniTutucu holder, int position) {
        stokCek(position,holder);
    }

    @Override
    public int getItemCount() {
        return urunlerDisaridanGelen.size();
    }

    public void stokCek(int position,CardViewStokNesneleriniTutucu holder){
        String url = "https://kristalekmek.com/stok/stok_cek.php";
        final Urunler urun = urunlerDisaridanGelen.get(position);
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray stokList = jsonObject.getJSONArray("stok");


                    for (int i=0;i<stokList.length();i++){
                        JSONObject k = stokList.getJSONObject(i);
                        int urun_adet = k.getInt("urun_adet");
                        holder.textViewStokUrunAd.setText(urun.getUrun_ad());
                        holder.textViewStokUrunAdet.setText(String.valueOf(urun_adet));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                params.put("urun_ad",urun.getUrun_ad());
                return params;
            }
        };
        Volley.newRequestQueue(mContext).add(istek);
    }



}
