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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.Activityler.AdminPersonelActivity;
import com.info.firinotomasyon.Activityler.RvAdapters.PersonelAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentPersoneller extends Fragment {

    private RecyclerView rvPersoneller;
    private PersonelAdapter adapter;
    private ArrayList<Personel> vtdenCekilenPersonelList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personeller,container,false);

        rvPersoneller = rootView.findViewById(R.id.rvPersoneller);

        rvPersoneller.setHasFixedSize(true);
        rvPersoneller.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        personelleriCek();



        return rootView;
    }

    public void personelleriCek(){
        String url = "https://kristalekmek.com/personel/personelleri_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                vtdenCekilenPersonelList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray personeller = jsonObject.getJSONArray("personel");

                    for (int i=0;i<personeller.length();i++){
                        JSONObject k = personeller.getJSONObject(i);
                        int personel_id = k.getInt("personel_id");
                        String personel_ad = k.getString("personel_ad");
                        String personel_tel = k.getString("personel_tel");
                        String personel_sifre = k.getString("personel_sifre");
                        String calisma_alan = k.getString("calisma_alan");

                        Personel personel = new Personel(personel_ad,personel_tel,personel_sifre,calisma_alan);
                        personel.setPersonel_id(personel_id);

                        vtdenCekilenPersonelList.add(personel);
                    }

                    adapter = new PersonelAdapter(getContext(),vtdenCekilenPersonelList);
                    rvPersoneller.setAdapter(adapter);


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
