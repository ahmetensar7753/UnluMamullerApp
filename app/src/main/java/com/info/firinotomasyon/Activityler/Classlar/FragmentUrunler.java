package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.RvAdapters.AdminUrunlerAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentUrunler extends Fragment {

    private RecyclerView rvAdminUrunler;

    private AdminUrunlerAdapter adapter;
    private ArrayList<Urunler> vtDenCekilenUrunler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_urunler,container,false);

        rvAdminUrunler = rootView.findViewById(R.id.rvAdminUrunler);

        rvAdminUrunler.setHasFixedSize(true);
        rvAdminUrunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));


        urunlerCek();




        return rootView;
    }

    public void urunlerCek(){
        String url = "https://kristalekmek.com/urunler/urunler_tumunu_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                vtDenCekilenUrunler = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray urunler = jsonObject.getJSONArray("urunler");

                    for (int i =0;i<urunler.length();i++){
                        JSONObject k = urunler.getJSONObject(i);

                        int urun_id = k.getInt("urun_id");
                        String urun_ad = k.getString("urun_ad");
                        String urun_resim_ad = k.getString("urun_resim_ad");
                        Double urun_fiyat = k.getDouble("urun_fiyat");
                        String urun_kimlik = k.getString("urun_kimlik");

                        Urunler urun = new Urunler(urun_ad,urun_resim_ad,urun_fiyat);
                        urun.setUrun_id(urun_id);
                        urun.setKimlik(urun_kimlik);

                        vtDenCekilenUrunler.add(urun);
                    }

                    adapter = new AdminUrunlerAdapter(getContext(),vtDenCekilenUrunler);
                    rvAdminUrunler.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }


}
