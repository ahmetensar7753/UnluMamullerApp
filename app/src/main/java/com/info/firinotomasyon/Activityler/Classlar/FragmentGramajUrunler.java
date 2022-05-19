package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class FragmentGramajUrunler extends Fragment {

    private RecyclerView rvAdminGramajUrunler;
    private ArrayList<Urunler> vtdenCekilenGramajUrunler;

    private AdminUrunlerAdapter adapterGramaj;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gramaj_urunler,container,false);

        rvAdminGramajUrunler = rootView.findViewById(R.id.rvAdminGramajUrunler);

        rvAdminGramajUrunler.setHasFixedSize(true);
        rvAdminGramajUrunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));


        gramajUrunlerCek();


        return rootView;
    }

    public void gramajUrunlerCek(){
        String url = "https://kristalekmek.com/gramaj_urunler/tumunu_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                vtdenCekilenGramajUrunler = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray urunler = jsonObject.getJSONArray("gramaj_urunler");

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

                        vtdenCekilenGramajUrunler.add(urun);
                    }

                    adapterGramaj = new AdminUrunlerAdapter(getContext(),vtdenCekilenGramajUrunler);
                    rvAdminGramajUrunler.setAdapter(adapterGramaj);



                } catch (JSONException e) {
                    Toast.makeText(getActivity().getApplicationContext(),"Sistem yöneticisine Haber Ver !",Toast.LENGTH_SHORT).show();
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
