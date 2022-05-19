package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
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
import com.info.firinotomasyon.Activityler.Classlar.ServisSiparis;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImalatSiparisDetayServisAdapter extends RecyclerView.Adapter<ImalatSiparisDetayServisAdapter.CardViewServisSiraNesneTutucu>{


    private Context mContext;
    private ArrayList<ServisSiparis> disaridanGelenList = new ArrayList<>();

    public ImalatSiparisDetayServisAdapter(Context mContext, ArrayList<ServisSiparis> disaridanGelenList) {
        this.mContext = mContext;
        this.disaridanGelenList = disaridanGelenList;
    }

    public class CardViewServisSiraNesneTutucu extends RecyclerView.ViewHolder {
        TextView textViewServisMusteriAd,textViewServisUrunAd,textViewServisUrunAdet;
        public CardViewServisSiraNesneTutucu(@NonNull View itemView) {
            super(itemView);
            textViewServisMusteriAd = itemView.findViewById(R.id.textViewServisMusteriAd);
            textViewServisUrunAd = itemView.findViewById(R.id.textViewServisUrunAd);
            textViewServisUrunAdet = itemView.findViewById(R.id.textViewServisUrunAdet);
        }
    }

    @NonNull
    @Override
    public CardViewServisSiraNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_siparis_detay,parent,false);
        return new CardViewServisSiraNesneTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewServisSiraNesneTutucu holder, int position) {
        ServisSiparis siparis = disaridanGelenList.get(position);

        adCek(siparis,holder);

    }

    @Override
    public int getItemCount() {
        return disaridanGelenList.size();
    }


    public void adCek(ServisSiparis siparis,CardViewServisSiraNesneTutucu holder){
        String url = "https://kristalekmek.com/servis/idye_gore_ad_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("sevkiyat_musteriler");

                    for (int i = 0 ;i<jsonArray.length();i++){
                        JSONObject j = jsonArray.getJSONObject(i);
                        String musteri_ad = j.getString("musteri_ad");

                        siparis.setMusteri_ad(musteri_ad);

                        holder.textViewServisMusteriAd.setText(siparis.getMusteri_ad());
                        holder.textViewServisUrunAd.setText(siparis.getUrun_ad());
                        holder.textViewServisUrunAdet.setText(String.valueOf(siparis.getUrun_adet()));

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
                params.put("musteri_id",String.valueOf(siparis.getMusteri_id()));
                return params;
            }
        };
        Volley.newRequestQueue(mContext).add(istek);
    }


}
