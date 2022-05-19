package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.RvAdapters.BakiyeMusteriAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.BakiyeOdenenAdapter;
import com.info.firinotomasyon.Activityler.RvAdapters.BakiyeOdenmeyenAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentOdenenler extends Fragment {

    //GORSEL NESNE TANIMLARI
    RecyclerView rvOdenenler;
    TextView textViewBakiyeOdenen;

    private ArrayList<SevkiyatUrunler> odenenUrunList;
    private BakiyeOdenenAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_odenenler_tasarim,container,false);

        SevkiyatMusteriler gelenMusteri = BakiyeMusteriAdapter.tiklananMusteri;

        //gorsel nesnelerin bağlanması
        rvOdenenler = rootView.findViewById(R.id.rvOdenenler);
        textViewBakiyeOdenen = rootView.findViewById(R.id.textViewBakiyeOdenen);

        rvOdenenler.setHasFixedSize(true);
        rvOdenenler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        odenenlerCek(gelenMusteri.getMusteriId());


        return rootView;
    }

    public void odenenlerCek(int musteri_id){
        String url = "https://kristalekmek.com/bakiye/odenen_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                odenenUrunList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray odenenList = jsonObject.getJSONArray("sevkiyat_odenen_urunler");

                    for (int i = 0;i<odenenList.length();i++){
                        JSONObject k = odenenList.getJSONObject(i);

                        String urun_ad = k.getString("urun_ad");
                        int urun_adet = k.getInt("urun_adet");
                        int urun_iade = k.getInt("urun_iade");
                        String odedigi_tarih = k.getString("odedigi_tarih");
                        Double toplam_fiyat = k.getDouble("toplam_fiyat");

                        SevkiyatUrunler urun = new SevkiyatUrunler(urun_ad,0.0);
                        urun.setUrun_adet(urun_adet);
                        urun.setUrun_iade(urun_iade);
                        urun.setOdendigi_tarih(odedigi_tarih);
                        urun.setToplam_fiyat(toplam_fiyat);

                        odenenUrunList.add(urun);
                    }

                    adapter = new BakiyeOdenenAdapter(getActivity().getApplicationContext(),odenenUrunList);
                    rvOdenenler.setAdapter(adapter);

                    double toplam=0;
                    for (SevkiyatUrunler item : odenenUrunList){
                        toplam = toplam + item.getToplam_fiyat();
                    }
                    textViewBakiyeOdenen.setText(String.valueOf(toplam));


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

                params.put("musteri_id",String.valueOf(musteri_id));

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }


}
