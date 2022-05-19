package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Bundle;
import android.util.Log;
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
import com.info.firinotomasyon.Activityler.RvAdapters.BakiyeOdenmeyenAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentOdenmeyenler extends Fragment {

    //GORSEL NESNE TANIMLARI
    RecyclerView rvOdenmeyenler;
    TextView textViewBakiyeBorc;

    private ArrayList<SevkiyatUrunler> urunListesi;
    private BakiyeOdenmeyenAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_odenmeyenler_tasarim,container,false);

        SevkiyatMusteriler gelenMusteri = BakiyeMusteriAdapter.tiklananMusteri;

        //gorsel nesnelerin bağlanması
        rvOdenmeyenler = rootView.findViewById(R.id.rvOdenmeyenler);
        textViewBakiyeBorc = rootView.findViewById(R.id.textViewBakiyeBorc);

        rvOdenmeyenler.setHasFixedSize(true);
        rvOdenmeyenler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        odenmeyenCek(gelenMusteri.getMusteriId());


        return rootView;
    }

    public void odenmeyenCek(int musteri_id){
        String url = "https://kristalekmek.com/bakiye/odenmeyen_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                urunListesi = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray urunList = jsonObject.getJSONArray("musteri_odemedigi_urunler");

                    for (int i = 0 ;i<urunList.length();i++){
                        JSONObject k = urunList.getJSONObject(i);
                        String urun_ad = k.getString("urun_ad");
                        int urun_adet = k.getInt("urun_adet");
                        int urun_iade = k.getInt("urun_iade");
                        Double aldigi_fiyat = k.getDouble("aldigi_fiyat");
                        String aldigi_tarih = k.getString("aldigi_tarih");
                        Double toplam_fiyat = k.getDouble("toplam_fiyat");

                        SevkiyatUrunler urun = new SevkiyatUrunler(urun_ad,aldigi_fiyat);
                        urun.setUrun_adet(urun_adet);
                        urun.setUrun_iade(urun_iade);
                        urun.setAlindigi_tarih(aldigi_tarih);
                        urun.setToplam_fiyat(toplam_fiyat);

                        urunListesi.add(urun);
                    }
                    adapter = new BakiyeOdenmeyenAdapter(getActivity().getApplicationContext(),urunListesi);
                    rvOdenmeyenler.setAdapter(adapter);
                    double toplam=0;
                    for (SevkiyatUrunler item : urunListesi){
                        toplam = toplam + item.getToplam_fiyat();
                    }
                    textViewBakiyeBorc.setText(String.valueOf(toplam));



                } catch (JSONException e) {
                    Log.e("catchBlog","catch Blogu calisti");
                    textViewBakiyeBorc.setText("0");
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
