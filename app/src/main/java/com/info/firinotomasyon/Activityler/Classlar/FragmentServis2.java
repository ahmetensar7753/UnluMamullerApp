package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.info.firinotomasyon.Activityler.RvAdapters.ImalatSiparisDetayServisAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FragmentServis2 extends Fragment {

    private RecyclerView rvServis2;
    private ImalatSiparisDetayServisAdapter adapter2;

    private ArrayList<ServisSiparis> vtDenCekilenServis2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_servis2,container,false);

        rvServis2 = rootView.findViewById(R.id.rvServis2);

        rvServis2.setHasFixedSize(true);
        rvServis2.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        siparisCek();

        return rootView;
    }

    public void siparisCek(){
        String url = "https://kristalekmek.com/servis/siraya_gore_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                vtDenCekilenServis2 = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("musteri_aldigi_urunler");

                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        int musteri_id = k.getInt("musteri_id");
                        String urun_ad = k.getString("urun_ad");
                        int urun_adet = k.getInt("urun_adet");

                        ServisSiparis siparis = new ServisSiparis(musteri_id,2,urun_ad,urun_adet);
                        vtDenCekilenServis2.add(siparis);
                    }

                    Collections.sort(vtDenCekilenServis2);

                    adapter2 = new ImalatSiparisDetayServisAdapter(getActivity().getApplicationContext(),vtDenCekilenServis2);
                    rvServis2.setAdapter(adapter2);



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
                params.put("servis_sira","2");
                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }

}
