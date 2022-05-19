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
import com.info.firinotomasyon.Activityler.RvAdapters.AdminMusterilerAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentMusteriler extends Fragment {

    private RecyclerView rvAdminMusteriler;

    private ArrayList<SevkiyatMusteriler> vtDenCekilenMusteriList;
    private AdminMusterilerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_musteriler,container,false);

        rvAdminMusteriler = rootView.findViewById(R.id.rvAdminMusteriler);

        rvAdminMusteriler.setHasFixedSize(true);
        rvAdminMusteriler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        musterilerCek();

        return rootView;
    }

    public void musterilerCek(){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/sevkiyat_musteriler_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                vtDenCekilenMusteriList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray musteriler = jsonObject.getJSONArray("sevkiyat_musteriler");

                    for (int i = 0 ; i<musteriler.length();i++){
                        JSONObject k = musteriler.getJSONObject(i);

                        int musteri_id = k.getInt("musteri_id");
                        String musteri_ad = k.getString("musteri_ad");
                        String musteri_tel = k.getString("musteri_tel");
                        String musteri_adres = k.getString("musteri_adres");
                        Double toplam_borc = k.getDouble("toplam_borc");
                        int servis_sira = k.getInt("servis_sira");

                        SevkiyatMusteriler m = new SevkiyatMusteriler(musteri_ad,musteri_tel,musteri_adres);
                        m.setMusteriId(musteri_id);
                        m.setMusteriToplamBorc(toplam_borc);
                        m.setMusteriSira(servis_sira);

                        vtDenCekilenMusteriList.add(m);

                    }

                    adapter = new AdminMusterilerAdapter(getContext(),vtDenCekilenMusteriList);
                    rvAdminMusteriler.setAdapter(adapter);


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
