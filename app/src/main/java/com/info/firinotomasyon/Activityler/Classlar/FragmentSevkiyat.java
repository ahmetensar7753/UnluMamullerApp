package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentSevkiyat extends Fragment {

    //GORSEL NESNE TANIMLARI
    private TextView textViewSevkiyatGuncelSayiAdmin,textViewSevkiyatGuncelToplamPara;
    private TextView textViewSevkiyatToplamUrunAdet,textViewSevkiyatToplamIadeAdet,textViewSevkiyatOdenenToplamFiyat;
    private TextView textViewSevkiyatOdenmeyenAdet,textViewSevkiyatOdenmeyenIade,textViewOdenmeyenToplamFiyat;
    private TextView textViewSevkiyatRaporTeslimTutar,textViewSevkiyatRaporTeslimIade;
    private Spinner spinnerSevkiyatGun,spinnerSevkiyatAy,spinnerSevkiyatYil;
    private Button buttonSevkiyatTariheGoreAra,buttonSevkiyatHaftalik,buttonSevkiyatAylik;

    private int guncelAdet = 0;
    private double beklenenTutar = 0.0;

    private ArrayList<String> spinnerSevkiyatGunList = new ArrayList<>();
    private ArrayAdapter<String> gunSevkiyatAdapter;

    private ArrayList<String> spinnerSevkiyatAyList = new ArrayList<>();
    private ArrayAdapter<String> aySevkiyatAdapter;

    private ArrayList<String> spinnerSevkiyatYilList = new ArrayList<>();
    private ArrayAdapter<String> yilSevkiyatAdapter;

    private int odenenAdet = 0;
    private int odenenIade = 0;
    private double odenenToplamFiyat = 0.0;

    private int odenmeyenAdet = 0;
    private int odenmeyenIade = 0;
    private double odenmeyenToplamFiyat = 0.0;

    private double raporTutar = 0.0;
    private int iadeToplam = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sevkiyat,container,false);

        //GORSEL NESNE BAĞLANTILARI
        textViewSevkiyatGuncelSayiAdmin = rootView.findViewById(R.id.textViewSevkiyatGuncelSayiAdmin);
        textViewSevkiyatGuncelToplamPara = rootView.findViewById(R.id.textViewSevkiyatGuncelToplamPara);
        textViewSevkiyatToplamUrunAdet = rootView.findViewById(R.id.textViewSevkiyatToplamUrunAdet);
        textViewSevkiyatToplamIadeAdet = rootView.findViewById(R.id.textViewSevkiyatToplamIadeAdet);
        textViewSevkiyatOdenenToplamFiyat = rootView.findViewById(R.id.textViewSevkiyatOdenenToplamFiyat);
        textViewSevkiyatOdenmeyenAdet = rootView.findViewById(R.id.textViewSevkiyatOdenmeyenAdet);
        textViewSevkiyatOdenmeyenIade = rootView.findViewById(R.id.textViewSevkiyatOdenmeyenIade);
        textViewOdenmeyenToplamFiyat = rootView.findViewById(R.id.textViewOdenmeyenToplamFiyat);
        textViewSevkiyatRaporTeslimTutar = rootView.findViewById(R.id.textViewSevkiyatRaporTeslimTutar);
        textViewSevkiyatRaporTeslimIade = rootView.findViewById(R.id.textViewSevkiyatRaporTeslimIade);
        spinnerSevkiyatGun = rootView.findViewById(R.id.spinnerSevkiyatGun);
        spinnerSevkiyatAy = rootView.findViewById(R.id.spinnerSevkiyatAy);
        spinnerSevkiyatYil = rootView.findViewById(R.id.spinnerSevkiyatYil);
        buttonSevkiyatTariheGoreAra = rootView.findViewById(R.id.buttonSevkiyatTariheGoreAra);
        buttonSevkiyatHaftalik = rootView.findViewById(R.id.buttonSevkiyatHaftalik);
        buttonSevkiyatAylik = rootView.findViewById(R.id.buttonSevkiyatAylik);

        guncelSayiTutarCek();

        for (int i = 1 ; i<=31;i++){
            spinnerSevkiyatGunList.add(String.valueOf(i));
        }
        for (int i = 1 ; i<=12;i++){
            spinnerSevkiyatAyList.add(String.valueOf(i));
        }
        spinnerSevkiyatYilList.add("2021");
        spinnerSevkiyatYilList.add("2022");

        gunSevkiyatAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,android.R.id.text1,spinnerSevkiyatGunList);
        spinnerSevkiyatGun.setAdapter(gunSevkiyatAdapter);

        aySevkiyatAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,android.R.id.text1,spinnerSevkiyatAyList);
        spinnerSevkiyatAy.setAdapter(aySevkiyatAdapter);

        yilSevkiyatAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,android.R.id.text1,spinnerSevkiyatYilList);
        spinnerSevkiyatYil.setAdapter(yilSevkiyatAdapter);

        buttonSevkiyatTariheGoreAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int secilenGun = Integer.parseInt(spinnerSevkiyatGunList.get(spinnerSevkiyatGun.getSelectedItemPosition()));
                int secilenAy = Integer.parseInt(spinnerSevkiyatAyList.get(spinnerSevkiyatAy.getSelectedItemPosition()));
                int secilenYil = Integer.parseInt(spinnerSevkiyatYilList.get(spinnerSevkiyatYil.getSelectedItemPosition()));

                tariheGoreOdenenleriCek(secilenGun,secilenAy,secilenYil);
                tariheGoreOdenmeyenleriCek(secilenGun,secilenAy,secilenYil);
                tariheGoreRaporCek(secilenGun,secilenAy,secilenYil);

            }
        });

        buttonSevkiyatHaftalik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                haftalikOdenenleriCek();
                haftalikOdenmeyenleriCek();
                haftalikRaporCek();
            }
        });

        buttonSevkiyatAylik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aylikOdenenleriCek();
                aylikOdenmeyenleriCek();
                aylikRaporCek();
            }
        });




        return rootView;
    }

    public void guncelSayiTutarCek(){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/guncel_sayi_tutar_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                guncelAdet = 0;
                beklenenTutar =0.0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("musteri_aldigi_urunler");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        int urun_adet = k.getInt("urun_adet");
                        double aldigi_fiyat = k.getDouble("aldigi_fiyat");


                        guncelAdet = guncelAdet+urun_adet;
                        beklenenTutar = beklenenTutar+(urun_adet*aldigi_fiyat);

                    }

                    textViewSevkiyatGuncelSayiAdmin.setText(String.valueOf(guncelAdet));
                    textViewSevkiyatGuncelToplamPara.setText(String.valueOf(beklenenTutar));


                } catch (JSONException e) {
                    textViewSevkiyatGuncelSayiAdmin.setText("0");
                    textViewSevkiyatGuncelToplamPara.setText("0.0");
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

    public void tariheGoreOdenenleriCek(int gun,int ay,int yil){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/tarihe_gore_odenenleri_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                odenenAdet = 0;
                odenenIade = 0;
                odenenToplamFiyat = 0.0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("sevkiyat_odenen_urunler");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        int urun_adet = k.getInt("urun_adet");
                        int urun_iade = k.getInt("urun_iade");
                        double toplam_fiyat = k.getDouble("toplam_fiyat");

                        odenenAdet = odenenAdet+urun_adet;
                        odenenIade = odenenIade+urun_iade;
                        odenenToplamFiyat = odenenToplamFiyat+toplam_fiyat;

                    }

                    textViewSevkiyatToplamUrunAdet.setText(String.valueOf(odenenAdet));
                    textViewSevkiyatToplamIadeAdet.setText(String.valueOf(odenenIade));
                    textViewSevkiyatOdenenToplamFiyat.setText(String.valueOf(odenenToplamFiyat));

                } catch (JSONException e) {
                    textViewSevkiyatToplamUrunAdet.setText("0");
                    textViewSevkiyatToplamIadeAdet.setText("0");
                    textViewSevkiyatOdenenToplamFiyat.setText("0.0");
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

    public void tariheGoreOdenmeyenleriCek(int gun,int ay,int yil){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/tarihe_gore_odenmeyenleri_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                odenmeyenAdet = 0;
                odenmeyenIade = 0;
                odenmeyenToplamFiyat = 0.0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("musteri_odemedigi_urunler");

                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        int urun_adet = k.getInt("urun_adet");
                        int urun_iade = k.getInt("urun_iade");
                        double toplam_fiyat = k.getDouble("toplam_fiyat");

                        odenmeyenAdet = odenmeyenAdet+urun_adet;
                        odenmeyenIade = odenmeyenIade+urun_iade;
                        odenmeyenToplamFiyat = odenmeyenToplamFiyat+toplam_fiyat;

                    }

                    textViewSevkiyatOdenmeyenAdet.setText(String.valueOf(odenmeyenAdet));
                    textViewSevkiyatOdenmeyenIade.setText(String.valueOf(odenmeyenIade));
                    textViewOdenmeyenToplamFiyat.setText(String.valueOf(odenmeyenToplamFiyat));


                } catch (JSONException e) {
                    textViewSevkiyatOdenmeyenAdet.setText("0");
                    textViewSevkiyatOdenmeyenIade.setText("0");
                    textViewOdenmeyenToplamFiyat.setText("0.0");
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

    public void haftalikOdenenleriCek(){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/haftalik_odenenleri_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                odenenAdet = 0;
                odenenIade = 0;
                odenenToplamFiyat = 0.0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("sevkiyat_odenen_urunler");

                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        int urun_adet = k.getInt("urun_adet");
                        int urun_iade = k.getInt("urun_iade");
                        double toplam_fiyat = k.getDouble("toplam_fiyat");

                        odenenAdet = odenenAdet+urun_adet;
                        odenenIade = odenenIade+urun_iade;
                        odenenToplamFiyat = odenenToplamFiyat+toplam_fiyat;

                    }

                    textViewSevkiyatToplamUrunAdet.setText(String.valueOf(odenenAdet));
                    textViewSevkiyatToplamIadeAdet.setText(String.valueOf(odenenIade));
                    textViewSevkiyatOdenenToplamFiyat.setText(String.valueOf(odenenToplamFiyat));


                } catch (JSONException e) {
                    textViewSevkiyatToplamUrunAdet.setText("0");
                    textViewSevkiyatToplamIadeAdet.setText("0");
                    textViewSevkiyatOdenenToplamFiyat.setText("0.0");
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

    public void haftalikOdenmeyenleriCek(){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/haftalik_odenmeyenleri_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                odenmeyenAdet = 0;
                odenmeyenIade = 0;
                odenmeyenToplamFiyat = 0.0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("musteri_odemedigi_urunler");

                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        int urun_adet = k.getInt("urun_adet");
                        int urun_iade = k.getInt("urun_iade");
                        double toplam_fiyat = k.getDouble("toplam_fiyat");

                        odenmeyenAdet = odenmeyenAdet+urun_adet;
                        odenmeyenIade = odenmeyenIade+urun_iade;
                        odenmeyenToplamFiyat = odenmeyenToplamFiyat+toplam_fiyat;

                    }

                    textViewSevkiyatOdenmeyenAdet.setText(String.valueOf(odenmeyenAdet));
                    textViewSevkiyatOdenmeyenIade.setText(String.valueOf(odenmeyenIade));
                    textViewOdenmeyenToplamFiyat.setText(String.valueOf(odenmeyenToplamFiyat));


                } catch (JSONException e) {
                    textViewSevkiyatOdenmeyenAdet.setText("0");
                    textViewSevkiyatOdenmeyenIade.setText("0");
                    textViewOdenmeyenToplamFiyat.setText("0.0");
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

    public void aylikOdenenleriCek(){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/aylik_odenenleri_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                odenenAdet = 0;
                odenenIade = 0;
                odenenToplamFiyat = 0.0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("sevkiyat_odenen_urunler");

                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        int urun_adet = k.getInt("urun_adet");
                        int urun_iade = k.getInt("urun_iade");
                        double toplam_fiyat = k.getDouble("toplam_fiyat");

                        odenenAdet = odenenAdet+urun_adet;
                        odenenIade = odenenIade+urun_iade;
                        odenenToplamFiyat = odenenToplamFiyat+toplam_fiyat;

                    }

                    textViewSevkiyatToplamUrunAdet.setText(String.valueOf(odenenAdet));
                    textViewSevkiyatToplamIadeAdet.setText(String.valueOf(odenenIade));
                    textViewSevkiyatOdenenToplamFiyat.setText(String.valueOf(odenenToplamFiyat));


                } catch (JSONException e) {
                    textViewSevkiyatToplamUrunAdet.setText("0");
                    textViewSevkiyatToplamIadeAdet.setText("0");
                    textViewSevkiyatOdenenToplamFiyat.setText("0.0");
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

    public void aylikOdenmeyenleriCek(){
        String url = "https://kristalekmek.com/sevkiyat_musteriler/aylik_odenmeyenleri_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                odenmeyenAdet = 0;
                odenmeyenIade = 0;
                odenmeyenToplamFiyat = 0.0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("musteri_odemedigi_urunler");

                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        int urun_adet = k.getInt("urun_adet");
                        int urun_iade = k.getInt("urun_iade");
                        double toplam_fiyat = k.getDouble("toplam_fiyat");

                        odenmeyenAdet = odenmeyenAdet+urun_adet;
                        odenmeyenIade = odenmeyenIade+urun_iade;
                        odenmeyenToplamFiyat = odenmeyenToplamFiyat+toplam_fiyat;

                    }

                    textViewSevkiyatOdenmeyenAdet.setText(String.valueOf(odenmeyenAdet));
                    textViewSevkiyatOdenmeyenIade.setText(String.valueOf(odenmeyenIade));
                    textViewOdenmeyenToplamFiyat.setText(String.valueOf(odenmeyenToplamFiyat));


                } catch (JSONException e) {
                    textViewSevkiyatOdenmeyenAdet.setText("0");
                    textViewSevkiyatOdenmeyenIade.setText("0");
                    textViewOdenmeyenToplamFiyat.setText("0.0");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getActivity().getApplicationContext());
    }

    public void haftalikRaporCek(){
        String url = "https://kristalekmek.com/sevkiyat_rapor/haftalik_rapor_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                raporTutar = 0.0;
                iadeToplam =0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("sevkiyat_rapor");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        double teslim_edilen_para = k.getDouble("teslim_edilen_para");
                        int teslim_edilen_iade = k.getInt("teslim_edilen_iade");

                        raporTutar = raporTutar+teslim_edilen_para;
                        iadeToplam = iadeToplam+teslim_edilen_iade;
                    }

                    textViewSevkiyatRaporTeslimTutar.setText(String.valueOf(raporTutar));
                    textViewSevkiyatRaporTeslimIade.setText(String.valueOf(iadeToplam));

                } catch (JSONException e) {
                    textViewSevkiyatRaporTeslimTutar.setText("0.0");
                    textViewSevkiyatRaporTeslimIade.setText("0");
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

    public void aylikRaporCek(){
        String url = "https://kristalekmek.com/sevkiyat_rapor/aylik_rapor_cek.php";
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                raporTutar = 0.0;
                iadeToplam =0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("sevkiyat_rapor");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        double teslim_edilen_para = k.getDouble("teslim_edilen_para");
                        int teslim_edilen_iade = k.getInt("teslim_edilen_iade");

                        raporTutar = raporTutar+teslim_edilen_para;
                        iadeToplam = iadeToplam+teslim_edilen_iade;
                    }

                    textViewSevkiyatRaporTeslimTutar.setText(String.valueOf(raporTutar));
                    textViewSevkiyatRaporTeslimIade.setText(String.valueOf(iadeToplam));

                } catch (JSONException e) {
                    textViewSevkiyatRaporTeslimTutar.setText("0.0");
                    textViewSevkiyatRaporTeslimIade.setText("0");
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

    public void tariheGoreRaporCek(int gun,int ay,int yil){
        String url = "https://kristalekmek.com/sevkiyat_rapor/tarihe_gore_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                raporTutar = 0.0;
                iadeToplam =0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("sevkiyat_rapor");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        double teslim_edilen_para = k.getDouble("teslim_edilen_para");
                        int teslim_edilen_iade = k.getInt("teslim_edilen_iade");

                        raporTutar = raporTutar+teslim_edilen_para;
                        iadeToplam = iadeToplam+teslim_edilen_iade;
                    }

                    textViewSevkiyatRaporTeslimTutar.setText(String.valueOf(raporTutar));
                    textViewSevkiyatRaporTeslimIade.setText(String.valueOf(iadeToplam));

                } catch (JSONException e) {
                    textViewSevkiyatRaporTeslimTutar.setText("0.0");
                    textViewSevkiyatRaporTeslimIade.setText("0");
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
