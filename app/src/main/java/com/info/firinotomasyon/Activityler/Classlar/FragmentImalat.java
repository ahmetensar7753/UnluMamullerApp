package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.info.firinotomasyon.Activityler.RvAdapters.ImalEdilenUrunlerAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FragmentImalat extends Fragment {

    private Spinner spinnerImalatGun,spinnerImalatAy,spinnerImalatYil;
    private Button buttonImalatTariheGoreAra,buttonImalatHaftalik,buttonImalatAylik;
    private RecyclerView rvImalatUretilenUrunler;

    private ImalEdilenUrunlerAdapter adapter;


    private ArrayList<Urunler> urunListesiHaftalik;
    private ArrayList<Urunler> urunListesiAylik;
    private ArrayList<Urunler> urunlerListesiTarih;

    private HashSet<Urunler> hashSetUrunlerHaftalik;
    private HashSet<Urunler> hashSetUrunlerAylik;
    private HashSet<Urunler> hashSetUrunlerTarih;


    private ArrayList<String> spinnerGunListImalat = new ArrayList<>();
    private ArrayAdapter<String> gunAdapterImalat;

    private ArrayList<String> spinnerAyListImalat = new ArrayList<>();
    private ArrayAdapter<String> ayAdapterImalat;

    private ArrayList<String> spinnerYilListImalat = new ArrayList<>();
    private ArrayAdapter<String> yilAdapterImalat;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_imalat,container,false);

        spinnerImalatGun = rootView.findViewById(R.id.spinnerImalatGun);
        spinnerImalatAy = rootView.findViewById(R.id.spinnerImalatAy);
        spinnerImalatYil = rootView.findViewById(R.id.spinnerImalatYil);
        buttonImalatTariheGoreAra = rootView.findViewById(R.id.buttonImalatTariheGoreAra);
        buttonImalatHaftalik = rootView.findViewById(R.id.buttonImalatHaftalik);
        buttonImalatAylik = rootView.findViewById(R.id.buttonImalatAylik);
        rvImalatUretilenUrunler = rootView.findViewById(R.id.rvImalatUretilenUrunler);

        for (int i = 1 ; i<=31;i++){
            spinnerGunListImalat.add(String.valueOf(i));
        }
        for (int i = 1 ; i<=12;i++){
            spinnerAyListImalat.add(String.valueOf(i));
        }
        spinnerYilListImalat.add("2021");
        spinnerYilListImalat.add("2022");


        gunAdapterImalat = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,android.R.id.text1,spinnerGunListImalat);
        spinnerImalatGun.setAdapter(gunAdapterImalat);
        ayAdapterImalat = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,android.R.id.text1,spinnerAyListImalat);
        spinnerImalatAy.setAdapter(ayAdapterImalat);
        yilAdapterImalat = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,android.R.id.text1,spinnerYilListImalat);
        spinnerImalatYil.setAdapter(yilAdapterImalat);


        buttonImalatTariheGoreAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gun = Integer.parseInt(spinnerGunListImalat.get(spinnerImalatGun.getSelectedItemPosition()));
                int ay = Integer.parseInt(spinnerAyListImalat.get(spinnerImalatAy.getSelectedItemPosition()));
                int yil = Integer.parseInt(spinnerYilListImalat.get(spinnerImalatYil.getSelectedItemPosition()));
                rvImalatUretilenUrunler.removeAllViews();
                tariheGoreUretilenCek(gun,ay,yil);

            }
        });

        buttonImalatHaftalik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvImalatUretilenUrunler.removeAllViews();
                haftalikUretilenCek();

            }
        });

        buttonImalatAylik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvImalatUretilenUrunler.removeAllViews();
                aylikUretilenCek();

            }
        });



        return rootView;
    }


    public void haftalikUretilenCek(){
        String url = "https://kristalekmek.com/imal_edilen_urunler/uretilen_urun_cek_ada_gore_haftalik.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hashSetUrunlerHaftalik = new HashSet<>();
                double sablonAdet=0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("imal_edilen_urunler");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        int uretilen_urun_adet = k.getInt("uretilen_urun_adet");
                        String uretilen_urun_ad = k.getString("uretilen_urun_ad");


                        Urunler urun = new Urunler(uretilen_urun_ad,"",0.0);

                        if (hashSetUrunlerHaftalik.contains(urun)){
                            ArrayList<Urunler> sablonList = new ArrayList<>(hashSetUrunlerHaftalik);
                            for (Urunler u : sablonList){
                                if (u.getUrun_ad().trim().equals(uretilen_urun_ad)){
                                    sablonAdet = u.getUrun_adet();
                                }
                            }
                            hashSetUrunlerHaftalik.remove(urun);
                            urun.setUrun_adet((double) uretilen_urun_adet+sablonAdet);
                            sablonAdet = 0;
                            hashSetUrunlerHaftalik.add(urun);
                        }else {
                            urun.setUrun_adet(uretilen_urun_adet);
                            hashSetUrunlerHaftalik.add(urun);
                        }

                    }

                    urunListesiHaftalik = new ArrayList<>(hashSetUrunlerHaftalik);

                    rvImalatUretilenUrunler.setHasFixedSize(true);
                    rvImalatUretilenUrunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    adapter = new ImalEdilenUrunlerAdapter(getActivity().getApplicationContext(),urunListesiHaftalik);
                    rvImalatUretilenUrunler.setAdapter(adapter);

                    

                } catch (JSONException e) {
                    rvImalatUretilenUrunler.removeAllViews();
                    Toast.makeText(getActivity().getApplicationContext(),"Kayıtlı Veri Yok!",Toast.LENGTH_SHORT).show();
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

    public void aylikUretilenCek(){
        String url = "https://kristalekmek.com/imal_edilen_urunler/uretilen_urun_cek_ada_gore_aylik.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hashSetUrunlerAylik = new HashSet<>();
                double sablonAdet=0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("imal_edilen_urunler");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        int uretilen_urun_adet = k.getInt("uretilen_urun_adet");
                        String uretilen_urun_ad = k.getString("uretilen_urun_ad");

                        Urunler urun = new Urunler(uretilen_urun_ad,"",0.0);

                        if (hashSetUrunlerAylik.contains(urun)){
                            ArrayList<Urunler> sablonList = new ArrayList<>(hashSetUrunlerAylik);
                            for (Urunler u : sablonList){
                                if (u.getUrun_ad().trim().equals(uretilen_urun_ad)){
                                    sablonAdet = u.getUrun_adet();
                                }
                            }
                            hashSetUrunlerAylik.remove(urun);
                            urun.setUrun_adet((double) uretilen_urun_adet+sablonAdet);
                            sablonAdet = 0;
                            hashSetUrunlerAylik.add(urun);
                        }else {
                            urun.setUrun_adet(uretilen_urun_adet);
                            hashSetUrunlerAylik.add(urun);
                        }

                    }

                    urunListesiAylik = new ArrayList<>(hashSetUrunlerAylik);

                    rvImalatUretilenUrunler.setHasFixedSize(true);
                    rvImalatUretilenUrunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    adapter = new ImalEdilenUrunlerAdapter(getActivity().getApplicationContext(),urunListesiAylik);
                    rvImalatUretilenUrunler.setAdapter(adapter);



                } catch (JSONException e) {
                    rvImalatUretilenUrunler.removeAllViews();
                    Toast.makeText(getActivity().getApplicationContext(),"Kayıtlı Veri Yok!",Toast.LENGTH_SHORT).show();
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

    public void tariheGoreUretilenCek(int gun,int ay,int yil){
        String url = "https://kristalekmek.com/imal_edilen_urunler/uretilen_urun_cek_ada_gore_tarih.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hashSetUrunlerTarih = new HashSet<>();
                double sablonAdet = 0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("imal_edilen_urunler");

                    for (int i = 0 ; i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        String uretilen_urun_ad = k.getString("uretilen_urun_ad");
                        int uretilen_urun_adet = k.getInt("uretilen_urun_adet");

                        Urunler urun = new Urunler(uretilen_urun_ad,"",0.0);

                        if (hashSetUrunlerTarih.contains(urun)){
                            ArrayList<Urunler> sablonList = new ArrayList<>(hashSetUrunlerTarih);
                            for (Urunler u : sablonList){
                                if (u.getUrun_ad().trim().equals(uretilen_urun_ad)){
                                    sablonAdet = u.getUrun_adet();
                                }
                            }
                            hashSetUrunlerTarih.remove(urun);
                            urun.setUrun_adet((double) uretilen_urun_adet+sablonAdet);
                            sablonAdet = 0;
                            hashSetUrunlerTarih.add(urun);
                        }else {
                            urun.setUrun_adet(uretilen_urun_adet);
                            hashSetUrunlerTarih.add(urun);
                        }

                    }

                    urunlerListesiTarih = new ArrayList<>(hashSetUrunlerTarih);

                    rvImalatUretilenUrunler.setHasFixedSize(true);
                    rvImalatUretilenUrunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    adapter = new ImalEdilenUrunlerAdapter(getActivity().getApplicationContext(),urunlerListesiTarih);
                    rvImalatUretilenUrunler.setAdapter(adapter);


                } catch (JSONException e) {
                    rvImalatUretilenUrunler.removeAllViews();
                    Toast.makeText(getActivity().getApplicationContext(),"Kayıtlı Veri Yok!",Toast.LENGTH_SHORT).show();
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

                params.put("gun",String.valueOf(gun));
                params.put("ay",String.valueOf(ay));
                params.put("yil",String.valueOf(yil));

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }



}
