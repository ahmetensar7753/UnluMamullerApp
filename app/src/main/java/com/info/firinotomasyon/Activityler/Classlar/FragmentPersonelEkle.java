package com.info.firinotomasyon.Activityler.Classlar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class FragmentPersonelEkle extends Fragment {

    private TextInputEditText editTextPersonelAd,editTextPersonelTelefon,editTextPersonelSifre;
    private Spinner spinnerPersonel;
    private Button buttonPersonelEkle;

    private ArrayList<String> spinnerList = new ArrayList<>();
    private ArrayAdapter<String> veriAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personel_ekle,container,false);

        editTextPersonelAd = rootView.findViewById(R.id.editTextPersonelAd);
        editTextPersonelTelefon = rootView.findViewById(R.id.editTextPersonelTelefon);
        editTextPersonelSifre = rootView.findViewById(R.id.editTextPersonelSifre);
        spinnerPersonel = rootView.findViewById(R.id.spinnerPersonel);
        buttonPersonelEkle = rootView.findViewById(R.id.buttonPersonelEkle);

        spinnerList.add("Alan Seçiniz...");
        spinnerList.add("admin");
        spinnerList.add("tezgah");
        spinnerList.add("imalat");
        spinnerList.add("sevkiyat");

        veriAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext()
                                                , android.R.layout.simple_list_item_1
                                                ,android.R.id.text1,spinnerList);

        spinnerPersonel.setAdapter(veriAdapter);

        buttonPersonelEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editTextPersonelAd.getText().toString().trim().equals("") &&
                        !editTextPersonelTelefon.getText().toString().trim().equals("") &&
                        !editTextPersonelSifre.getText().toString().trim().equals("") &&
                        !spinnerList.get(spinnerPersonel.getSelectedItemPosition()).equals("Alan Seçiniz...")){

                    String perAd = editTextPersonelAd.getText().toString();
                    String perTel = editTextPersonelTelefon.getText().toString();
                    String perParola = editTextPersonelSifre.getText().toString();
                    String perCalismaAlan = spinnerList.get(spinnerPersonel.getSelectedItemPosition());

                    personelEkle(perAd,perTel,perParola,perCalismaAlan);

                    Toast.makeText(getActivity().getApplicationContext(),"Personel Eklendi.",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getActivity().getApplicationContext(), AdminActivity.class));
                    getActivity().finish();

                }else {
                    Toast.makeText(getActivity().getApplicationContext(),"Alanları Düzgün Doldur..",Toast.LENGTH_SHORT).show();
                }



            }
        });
        return rootView;
    }

    public void personelEkle(String personel_ad,String personel_tel,String personel_sifre,String calisma_alan){
        String url = "https://kristalekmek.com/personel/personel_ekle.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("prsnlEkleRespn",response);
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

                params.put("personel_ad",personel_ad);
                params.put("personel_tel",personel_tel);
                params.put("personel_sifre",personel_sifre);
                params.put("calisma_alan",calisma_alan);

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }


}
