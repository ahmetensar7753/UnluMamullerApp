package com.info.firinotomasyon.Activityler.Classlar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.info.firinotomasyon.Activityler.AdminActivity;
import com.info.firinotomasyon.Activityler.RvAdapters.AdminSevkiyatUrunlerAdapter;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentSevkiyatUrunler extends Fragment {

    private RecyclerView rvAdminSevkiyatUrunler;
    private FloatingActionButton fabSevkiyatUrunEkle;
    private EditText editTextAdminSevkiyatUrunEkle;


    private AdminSevkiyatUrunlerAdapter adapterSevkiyat;
    private ArrayList<SevkiyatUrunler> vtDenCekilenSevkiyatUrunler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sevkiyat_urunler_urun_ekle,container,false);

        rvAdminSevkiyatUrunler = rootView.findViewById(R.id.rvAdminSevkiyatUrunler);
        fabSevkiyatUrunEkle = rootView.findViewById(R.id.fabSevkiyatUrunEkle);
        editTextAdminSevkiyatUrunEkle = rootView.findViewById(R.id.editTextAdminSevkiyatUrunEkle);



        rvAdminSevkiyatUrunler.setHasFixedSize(true);
        rvAdminSevkiyatUrunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        urunlerCek();


        fabSevkiyatUrunEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editTextAdminSevkiyatUrunEkle.getText().toString().trim().equals("")){

                    urunEkle(editTextAdminSevkiyatUrunEkle.getText().toString());

                    Toast.makeText(getActivity().getApplicationContext(),"Ürün Eklendi.",Toast.LENGTH_SHORT).show();

                    editTextAdminSevkiyatUrunEkle.setText("");

                    startActivity(new Intent(getActivity().getApplicationContext(), AdminActivity.class));
                }else {
                    Toast.makeText(getActivity().getApplicationContext(),"Boş Bırakma!",Toast.LENGTH_SHORT).show();
                }

            }
        });







        return rootView;
    }



    public void urunlerCek(){
        String url = "https://kristalekmek.com/sevkiyat_urunler/urunler_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                vtDenCekilenSevkiyatUrunler = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray urunler = jsonObject.getJSONArray("sevkiyat_urunler");

                    for (int i = 0;i<urunler.length();i++){
                        JSONObject k = urunler.getJSONObject(i);
                        int urun_id = k.getInt("urun_id");
                        String urun_ad = k.getString("urun_ad");

                        SevkiyatUrunler urun = new SevkiyatUrunler(urun_ad,0.0);
                        urun.setUrun_id(urun_id);

                        vtDenCekilenSevkiyatUrunler.add(urun);
                    }

                    adapterSevkiyat = new AdminSevkiyatUrunlerAdapter(getContext(),vtDenCekilenSevkiyatUrunler);
                    rvAdminSevkiyatUrunler.setAdapter(adapterSevkiyat);




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

    public void urunEkle(String urun_ad){
        String url = "https://kristalekmek.com/sevkiyat_urunler/insert_urun.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("svkytUrunEkleRespn",response);
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

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }




}
