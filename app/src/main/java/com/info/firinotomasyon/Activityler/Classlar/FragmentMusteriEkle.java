package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.info.firinotomasyon.R;

import java.util.HashMap;
import java.util.Map;

public class FragmentMusteriEkle extends Fragment {

    private TextInputEditText editTextAdminMusteriAd,editTextAdminMusteriTel,editTextAdminMusteriAdres;
    private Button buttonAdminMusteriKaydet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_musteri_ekle,container,false);

        editTextAdminMusteriAd = rootView.findViewById(R.id.editTextAdminMusteriAd);
        editTextAdminMusteriTel = rootView.findViewById(R.id.editTextAdminMusteriTel);
        editTextAdminMusteriAdres = rootView.findViewById(R.id.editTextAdminMusteriAdres);
        buttonAdminMusteriKaydet = rootView.findViewById(R.id.buttonAdminMusteriKaydet);

        buttonAdminMusteriKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editTextAdminMusteriAd.getText().toString().trim().equals("") &&
                        !editTextAdminMusteriTel.getText().toString().trim().equals("") &&
                        !editTextAdminMusteriAdres.getText().toString().trim().equals("")){

                    musteriEkle(editTextAdminMusteriAd.getText().toString(),editTextAdminMusteriTel.getText().toString(),editTextAdminMusteriAdres.getText().toString());

                    editTextAdminMusteriAd.setText("");
                    editTextAdminMusteriTel.setText("");
                    editTextAdminMusteriAdres.setText("");

                    Toast.makeText(getActivity().getApplicationContext(),"Müşteri Eklendi.",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getActivity().getApplicationContext(),"Boş Alan Bırakma !",Toast.LENGTH_SHORT).show();
                }

            }
        });





        return rootView;
    }

    public void musteriEkle(String musteri_ad,String musteri_tel,String musteri_adres){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/insert_musteri.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("msterEkleRspns",response);
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

                params.put("musteri_ad",musteri_ad);
                params.put("musteri_tel",musteri_tel);
                params.put("musteri_adres",musteri_adres);

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }




}
