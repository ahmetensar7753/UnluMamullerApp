package com.info.firinotomasyon.Activityler.Classlar;

import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.info.firinotomasyon.Activityler.AdminActivity;
import com.info.firinotomasyon.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentUrunEkle extends Fragment {

    private TextInputEditText editTextUrunEkleAd,editTextUrunEkleResimAd,editTextUrunEkleFiyat;
    private Button buttonAdminUrunEkleKaydet;
    private Spinner spinnerUrunEkleKimlik;

    private ArrayList<String> spinnerList = new ArrayList<>();
    private ArrayAdapter<String> veriAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_urun_ekle,container,false);

        editTextUrunEkleAd = rootView.findViewById(R.id.editTextUrunEkleAd);
        editTextUrunEkleResimAd = rootView.findViewById(R.id.editTextUrunEkleResimAd);
        editTextUrunEkleFiyat = rootView.findViewById(R.id.editTextUrunEkleFiyat);
        buttonAdminUrunEkleKaydet = rootView.findViewById(R.id.buttonAdminUrunEkleKaydet);
        spinnerUrunEkleKimlik = rootView.findViewById(R.id.spinnerUrunEkleKimlik);

        spinnerList.add("tane");
        spinnerList.add("gramaj");

        veriAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,android.R.id.text1,spinnerList);

        spinnerUrunEkleKimlik.setAdapter(veriAdapter);

        buttonAdminUrunEkleKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editTextUrunEkleAd.getText().toString().trim().equals("") && !editTextUrunEkleResimAd.getText().toString().trim().equals("") && !editTextUrunEkleFiyat.getText().toString().trim().equals("")){

                    if (spinnerList.get(spinnerUrunEkleKimlik.getSelectedItemPosition()).equals("tane")){
                        urunEkle(editTextUrunEkleAd.getText().toString()
                                ,editTextUrunEkleResimAd.getText().toString()
                                ,Double.parseDouble(editTextUrunEkleFiyat.getText().toString())
                                ,spinnerList.get(spinnerUrunEkleKimlik.getSelectedItemPosition()));

                        stokUrunEkle(editTextUrunEkleAd.getText().toString());

                    }else {
                        gramajUrunEkle(editTextUrunEkleAd.getText().toString()
                                ,editTextUrunEkleResimAd.getText().toString()
                                ,Double.parseDouble(editTextUrunEkleFiyat.getText().toString())
                                ,spinnerList.get(spinnerUrunEkleKimlik.getSelectedItemPosition()));
                    }

                    startActivity(new Intent(getActivity().getApplicationContext(), AdminActivity.class));
                    getActivity().finish();

                }else {
                    Toast.makeText(getActivity().getApplicationContext(),"Boş Alan Bırakma!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }

    public void urunEkle(String urun_ad,String urun_resim_ad,Double urun_fiyat,String urun_kimlik){
        String url = "https://kristalekmek.com/urunler/urun_ekle.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("urunEkleRspns",response);
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

                params.put("urun_ad",urun_ad);
                params.put("urun_resim_ad",urun_resim_ad);
                params.put("urun_fiyat",String.valueOf(urun_fiyat));
                params.put("urun_kimlik",urun_kimlik);

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }

    public void gramajUrunEkle(String urun_ad,String urun_resim_ad,Double urun_fiyat,String urun_kimlik){
        String url = "https://kristalekmek.com/gramaj_urunler/gramaj_urun_ekle.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("GrmjUrunEkleRspns",response);
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

                params.put("urun_ad",urun_ad);
                params.put("urun_resim_ad",urun_resim_ad);
                params.put("urun_fiyat",String.valueOf(urun_fiyat));
                params.put("urun_kimlik",urun_kimlik);

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }

    public void stokUrunEkle(String urun_ad){
        String url = "https://kristalekmek.com/stok/stok_tabloya_urun_ekleme.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("stokUrunEklRspns",response);
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
                params.put("urun_ad",urun_ad);
                params.put("urun_adet","0");

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }



}
