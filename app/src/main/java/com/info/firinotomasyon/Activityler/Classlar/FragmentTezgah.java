package com.info.firinotomasyon.Activityler.Classlar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FragmentTezgah extends Fragment {

    private Button buttonGunluk,buttonHaftalik,buttonAylik;
    private Spinner gun,ay,yil;
    private Button buttonTariheGoreAra;

    private TextView textViewKasayaGirisAdmin
            ,textViewAskidaNakitGirisAdmin
            ,textViewTezgahGiderToplamAdmin
            ,textViewTezgahHibeTutarAdmin
            ,textViewBayatSayisiAdmin
            ,textViewTezgahtarNakitRaporAdmin
            ,textViewTezgahtarKartRaporAdmin
            ,textViewKasadakiAcikAdmin;

    private ArrayList<Double> nakitGirisList;
    private Double nakitGirisToplam = 0.0;

    private ArrayList<Double> askidaTutarList;
    private Double askidaTutarToplam = 0.0;

    private ArrayList<Double> giderList;
    private Double giderToplamTutar = 0.0;

    private ArrayList<Double> hibeTutarList;
    private Double toplamHibeTutar = 0.0;

    private ArrayList<TezgahRapor> tezgahRaporList;
    private double toplamNakit = 0.0;
    private double toplamKart = 0.0;
    private int toplamBayat = 0;

    private ArrayList<String> spinnerGunList = new ArrayList<>();
    private ArrayAdapter<String> gunAdapter;

    private ArrayList<String> spinnerAyList = new ArrayList<>();
    private ArrayAdapter<String> ayAdapter;

    private ArrayList<String> spinnerYilList = new ArrayList<>();
    private ArrayAdapter<String> yilAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tezgah,container,false);

        buttonGunluk = rootView.findViewById(R.id.buttonGunluk);
        buttonHaftalik = rootView.findViewById(R.id.buttonHaftalik);
        buttonAylik = rootView.findViewById(R.id.buttonAylik);


        textViewKasayaGirisAdmin = rootView.findViewById(R.id.textViewKasayaGirisAdmin);
        textViewAskidaNakitGirisAdmin = rootView.findViewById(R.id.textViewAskidaNakitGirisAdmin);
        textViewTezgahGiderToplamAdmin = rootView.findViewById(R.id.textViewTezgahGiderToplamAdmin);
        textViewTezgahHibeTutarAdmin = rootView.findViewById(R.id.textViewTezgahHibeTutarAdmin);
        textViewBayatSayisiAdmin = rootView.findViewById(R.id.textViewBayatSayisiAdmin);
        textViewTezgahtarNakitRaporAdmin = rootView.findViewById(R.id.textViewTezgahtarNakitRaporAdmin);
        textViewTezgahtarKartRaporAdmin = rootView.findViewById(R.id.textViewTezgahtarKartRaporAdmin);
        textViewKasadakiAcikAdmin = rootView.findViewById(R.id.textViewKasadakiAcikAdmin);
        buttonTariheGoreAra = rootView.findViewById(R.id.buttonTariheGoreAra);
        gun = rootView.findViewById(R.id.gun);
        ay = rootView.findViewById(R.id.ay);
        yil = rootView.findViewById(R.id.yil);


        for (int i = 1 ; i<=31;i++){
            spinnerGunList.add(String.valueOf(i));
        }
        for (int i = 1 ; i<=12;i++){
            spinnerAyList.add(String.valueOf(i));
        }
        spinnerYilList.add("2021");
        spinnerYilList.add("2022");

        gunAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,android.R.id.text1,spinnerGunList);
        gun.setAdapter(gunAdapter);
        ayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,android.R.id.text1,spinnerAyList);
        ay.setAdapter(ayAdapter);
        yilAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,android.R.id.text1,spinnerYilList);
        yil.setAdapter(yilAdapter);



        buttonGunluk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nakitGirisCek("gunluk_giris_cek.php");
                askidaTutarCek("gunluk_tutar_cek.php");
                giderCek("gunluk_gider_cek.php");
                hibetutarCek("gunluk_hibe_cek.php");
                raporCek("gunluk_rapor_cek.php");
            }
        });
        buttonHaftalik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nakitGirisCek("haftalik_giris_cek.php");
                askidaTutarCek("haftalik_tutar_cek.php");
                giderCek("haftalik_gider_cek.php");
                hibetutarCek("haftalik_hibe_cek.php");
                raporCek("haftalik_rapor_cek.php");
            }
        });

        buttonAylik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nakitGirisCek("aylik_giris_cek.php");
                askidaTutarCek("aylik_tutar_cek.php");
                giderCek("aylik_gider_cek.php");
                hibetutarCek("aylik_hibe_cek.php");
                raporCek("aylik_rapor_cek.php");
            }
        });

        textViewKasadakiAcikAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double kasayaGiris = Double.parseDouble(textViewKasayaGirisAdmin.getText().toString());
                double askidaGiris = Double.parseDouble(textViewAskidaNakitGirisAdmin.getText().toString());

                double tezgahtarKart = Double.parseDouble(textViewTezgahtarKartRaporAdmin.getText().toString());
                double tezgahtarNakit = Double.parseDouble(textViewTezgahtarNakitRaporAdmin.getText().toString());

                double beklenen = (kasayaGiris+askidaGiris);
                double hesaplanan = (tezgahtarKart+tezgahtarNakit);

                if (hesaplanan == beklenen){
                    textViewKasadakiAcikAdmin.setText("0 TL");
                }
                if (hesaplanan < beklenen){
                    double yaz = beklenen-hesaplanan;
                    textViewKasadakiAcikAdmin.setText(String.valueOf(yaz)+"  TL açık vardır.");
                }
                if (beklenen < hesaplanan){
                    double yaz = hesaplanan-beklenen;
                    textViewKasadakiAcikAdmin.setText(String.valueOf(yaz)+"  TL FAZLA vardır.");
                }

            }
        });

        buttonTariheGoreAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int secilenGun = Integer.parseInt(spinnerGunList.get(gun.getSelectedItemPosition()));
                int secilenAy = Integer.parseInt(spinnerAyList.get(ay.getSelectedItemPosition()));
                int secilenYil = Integer.parseInt(spinnerYilList.get(yil.getSelectedItemPosition()));

                tariheGoreParaGirisCek(secilenYil,secilenAy,secilenGun);
                tariheGoreAskidaNakitCek(secilenYil,secilenAy,secilenGun);
                tariheGoreGiderCek(secilenYil,secilenAy,secilenGun);
                tariheGoreHibeCek(secilenYil,secilenAy,secilenGun);
                tariheGoreRaporCek(secilenYil,secilenAy,secilenGun);

            }
        });




        return rootView;
    }

    public void tariheGoreParaGirisCek(int yil,int ay,int gun){
        String url = "https://kristalekmek.com/tezgah_para_giris/tarihe_gore_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                nakitGirisList = new ArrayList<>();
                nakitGirisToplam = 0.0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("gunluk_para_giris");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        double satis_tutar = k.getDouble("satis_tutar");

                        nakitGirisList.add(satis_tutar);
                    }

                    for (Double t : nakitGirisList){
                        nakitGirisToplam = nakitGirisToplam+t;
                    }

                    textViewKasayaGirisAdmin.setText(String.valueOf(nakitGirisToplam));


                } catch (JSONException e) {
                    textViewKasayaGirisAdmin.setText("0");
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

                params.put("yil",String.valueOf(yil));
                params.put("ay",String.valueOf(ay));
                params.put("gun",String.valueOf(gun));

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }

    public void tariheGoreAskidaNakitCek(int yil,int ay,int gun){
        String url = "https://kristalekmek.com/askida_ekmek/tarihe_gore_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                askidaTutarList = new ArrayList<>();
                askidaTutarToplam = 0.0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("askida_ekmek_nakit_giris");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        double tutar = k.getDouble("tutar");

                        askidaTutarList.add(tutar);
                    }

                    for (Double t : askidaTutarList){
                        askidaTutarToplam = askidaTutarToplam+t;
                    }

                    textViewAskidaNakitGirisAdmin.setText(String.valueOf(askidaTutarToplam));


                } catch (JSONException e) {
                    textViewAskidaNakitGirisAdmin.setText("0");
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

                params.put("yil",String.valueOf(yil));
                params.put("ay",String.valueOf(ay));
                params.put("gun",String.valueOf(gun));

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }

    public void tariheGoreGiderCek(int yil,int ay,int gun){
        String url ="https://kristalekmek.com/giderler/tarihe_gore_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                giderList = new ArrayList<>();
                giderToplamTutar = 0.0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("gunluk_giderler");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        double gider_tutar = k.getDouble("gider_tutar");

                        giderList.add(gider_tutar);
                    }

                    for (Double t : giderList){
                        giderToplamTutar = giderToplamTutar+t;
                    }

                    textViewTezgahGiderToplamAdmin.setText(String.valueOf(giderToplamTutar));


                } catch (JSONException e) {
                    textViewTezgahGiderToplamAdmin.setText("0");
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

                params.put("yil",String.valueOf(yil));
                params.put("ay",String.valueOf(ay));
                params.put("gun",String.valueOf(gun));

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }

    public void tariheGoreHibeCek(int yil,int ay,int gun){
        String url = "https://kristalekmek.com/hibe_kaydet/tarihe_gore_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hibeTutarList = new ArrayList<>();
                toplamHibeTutar = 0.0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("hibeler");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);

                        double hibe_tutar = k.getDouble("hibe_tutar");

                        hibeTutarList.add(hibe_tutar);

                    }

                    for (Double t : hibeTutarList){
                        toplamHibeTutar = toplamHibeTutar+t;
                    }

                    textViewTezgahHibeTutarAdmin.setText(String.valueOf(toplamHibeTutar));

                } catch (JSONException e) {
                    textViewTezgahHibeTutarAdmin.setText("0");
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

                params.put("yil",String.valueOf(yil));
                params.put("ay",String.valueOf(ay));
                params.put("gun",String.valueOf(gun));

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }

    public void tariheGoreRaporCek(int yil,int ay,int gun){
        String url = "https://kristalekmek.com/tezgah_rapor/tarihe_gore_cek.php";
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tezgahRaporList = new ArrayList<>();
                toplamNakit = 0.0;
                toplamKart = 0.0;
                toplamBayat = 0;
                tezgahRaporList.clear();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("tezgah_rapor");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject k = jsonArray.getJSONObject(i);
                        double nakit_tutar = k.getDouble("nakit_tutar");
                        double kart_tutar = k.getDouble("kart_tutar");
                        int bayat_adet = k.getInt("bayat_adet");

                        TezgahRapor rapor = new TezgahRapor(nakit_tutar,kart_tutar,bayat_adet);

                        tezgahRaporList.add(rapor);
                    }

                    for (TezgahRapor r : tezgahRaporList){
                        toplamNakit = toplamNakit+r.getNakit_tutar();
                        toplamKart = toplamKart+r.getKart_tutar();
                        toplamBayat = toplamBayat+r.getBayat_adet();
                    }

                    textViewTezgahtarNakitRaporAdmin.setText(String.valueOf(toplamNakit));
                    textViewTezgahtarKartRaporAdmin.setText(String.valueOf(toplamKart));
                    textViewBayatSayisiAdmin.setText(String.valueOf(toplamBayat));




                } catch (JSONException e) {
                    textViewTezgahtarNakitRaporAdmin.setText("0");
                    textViewTezgahtarKartRaporAdmin.setText("0");
                    textViewBayatSayisiAdmin.setText("0");
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

                params.put("yil",String.valueOf(yil));
                params.put("ay",String.valueOf(ay));
                params.put("gun",String.valueOf(gun));

                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }


    public void nakitGirisCek(String dilim){
        String url = "https://kristalekmek.com/tezgah_para_giris/"+dilim;
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                nakitGirisList = new ArrayList<>();
                nakitGirisToplam = 0.0;
                textViewKasayaGirisAdmin.setText("0.0");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray nakitGirisleri = jsonObject.getJSONArray("gunluk_para_giris");

                    for (int i=0;i<nakitGirisleri.length();i++){
                        JSONObject k = nakitGirisleri.getJSONObject(i);

                        Double tutar = k.getDouble("satis_tutar");

                        nakitGirisList.add(tutar);
                    }

                    for (Double d : nakitGirisList){
                        nakitGirisToplam = nakitGirisToplam+d;
                    }

                    textViewKasayaGirisAdmin.setText(String.valueOf(nakitGirisToplam));


                } catch (JSONException e) {
                    textViewKasayaGirisAdmin.setText("0");
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

    public void askidaTutarCek(String dilim){
        String url = "https://kristalekmek.com/askida_ekmek/"+dilim;
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                askidaTutarList = new ArrayList<>();
                askidaTutarToplam = 0.0;
                textViewAskidaNakitGirisAdmin.setText("0.0");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray askidaTutarGirisleri = jsonObject.getJSONArray("askida_ekmek_nakit_giris");

                    for (int i = 0;i<askidaTutarGirisleri.length();i++){
                        JSONObject k = askidaTutarGirisleri.getJSONObject(i);

                        Double tutar = k.getDouble("tutar");

                        askidaTutarList.add(tutar);
                    }

                    for (Double d : askidaTutarList){
                        askidaTutarToplam = askidaTutarToplam+d;
                    }

                    textViewAskidaNakitGirisAdmin.setText(String.valueOf(askidaTutarToplam));



                } catch (JSONException e) {
                    textViewAskidaNakitGirisAdmin.setText("0");
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

    public void giderCek(String dilim){
        String url = "https://kristalekmek.com/giderler/"+dilim;
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                giderList = new ArrayList<>();
                giderToplamTutar = 0.0;
                textViewTezgahGiderToplamAdmin.setText("0.0");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray giderler = jsonObject.getJSONArray("gunluk_giderler");

                    for (int i=0;i<giderler.length();i++){
                        JSONObject k = giderler.getJSONObject(i);

                        Double gider_tutar = k.getDouble("gider_tutar");

                        giderList.add(gider_tutar);
                    }

                    for (Double d : giderList){
                        giderToplamTutar = giderToplamTutar+d;
                    }

                    textViewTezgahGiderToplamAdmin.setText(String.valueOf(giderToplamTutar));



                } catch (JSONException e) {
                    textViewTezgahGiderToplamAdmin.setText("0");
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

    public void hibetutarCek(String dilim){
        String url = "https://kristalekmek.com/hibe_kaydet/"+dilim;
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hibeTutarList = new ArrayList<>();
                toplamHibeTutar = 0.0;
                textViewTezgahHibeTutarAdmin.setText("0.0");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray hibeler = jsonObject.getJSONArray("hibeler");

                    for (int i=0;i<hibeler.length();i++){
                        JSONObject k = hibeler.getJSONObject(i);

                        Double hibe_tutar = k.getDouble("hibe_tutar");

                        hibeTutarList.add(hibe_tutar);
                    }

                    for (Double d : hibeTutarList){
                        toplamHibeTutar = toplamHibeTutar+d;
                    }

                    textViewTezgahHibeTutarAdmin.setText(String.valueOf(toplamHibeTutar));



                } catch (JSONException e) {
                    textViewTezgahHibeTutarAdmin.setText("0");
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

    public void raporCek(String dilim){
        String url = "https://kristalekmek.com/tezgah_rapor/"+dilim;
        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tezgahRaporList = new ArrayList<>();
                toplamNakit = 0.0;
                toplamKart = 0.0;
                toplamBayat = 0;
                tezgahRaporList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray raporArray = jsonObject.getJSONArray("tezgah_rapor");

                    for (int a = 0;a<raporArray.length();a++){
                        JSONObject k = raporArray.getJSONObject(a);
                        double nakit_tutar = k.getDouble("nakit_tutar");
                        double kart_tutar = k.getDouble("kart_tutar");
                        int bayat_adet = k.getInt("bayat_adet");

                        TezgahRapor rapor = new TezgahRapor(nakit_tutar,kart_tutar,bayat_adet);

                        tezgahRaporList.add(rapor);
                    }


                    for (TezgahRapor r : tezgahRaporList){
                        toplamNakit = toplamNakit+r.getNakit_tutar();
                        toplamKart = toplamKart+r.getKart_tutar();
                        toplamBayat = toplamBayat+r.getBayat_adet();
                    }

                    textViewTezgahtarNakitRaporAdmin.setText(String.valueOf(toplamNakit));
                    textViewTezgahtarKartRaporAdmin.setText(String.valueOf(toplamKart));
                    textViewBayatSayisiAdmin.setText(String.valueOf(toplamBayat));



                } catch (JSONException e) {
                    textViewTezgahtarNakitRaporAdmin.setText("0");
                    textViewTezgahtarKartRaporAdmin.setText("0");
                    textViewBayatSayisiAdmin.setText("0");
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
