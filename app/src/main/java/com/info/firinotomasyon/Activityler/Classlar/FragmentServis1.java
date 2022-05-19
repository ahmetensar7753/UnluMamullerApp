package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.RvAdapters.ImalatSiparisDetayServisAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FragmentServis1 extends Fragment {

    private RecyclerView rvServis1;
    private ImalatSiparisDetayServisAdapter adapter1;

    private ArrayList<ServisSiparis> vtDenCekilenServis1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_servis1,container,false);
        rvServis1 = rootView.findViewById(R.id.rvServis1);

        rvServis1.setHasFixedSize(true);
        rvServis1.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));


        siparisCek();


        return rootView;
    }

    public void siparisCek(){
        String url = "https://kristalekmek.com/servis/siraya_gore_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                vtDenCekilenServis1 = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("musteri_aldigi_urunler");

                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        int musteri_id = k.getInt("musteri_id");
                        String urun_ad = k.getString("urun_ad");
                        int urun_adet = k.getInt("urun_adet");

                        ServisSiparis siparis = new ServisSiparis(musteri_id,1,urun_ad,urun_adet);
                        vtDenCekilenServis1.add(siparis);
                    }

                    Collections.sort(vtDenCekilenServis1);

                    adapter1 = new ImalatSiparisDetayServisAdapter(getActivity().getApplicationContext(),vtDenCekilenServis1);
                    rvServis1.setAdapter(adapter1);



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
                params.put("servis_sira","1");
                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }



}
