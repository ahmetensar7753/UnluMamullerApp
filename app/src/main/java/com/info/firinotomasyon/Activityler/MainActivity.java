package com.info.firinotomasyon.Activityler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.info.firinotomasyon.Activityler.Classlar.Personel;
import com.info.firinotomasyon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Görsel nesneler burada tanımlanmaktadır.
    private Button buttonTezgahGiris,buttonImalatGiris,buttonAdminGiris,buttonSevkiyatGiris;
    private TextInputEditText editTextParola;

    private ArrayList<Personel> personelArrayList = new ArrayList<>();
    private ArrayList<String> parolaTezgahList = new ArrayList<>();
    private ArrayList<String> parolaImalatList = new ArrayList<>();
    private ArrayList<String> parolaAdminList = new ArrayList<>();
    private ArrayList<String> parolaSevkiyatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Görsel nesnelerin bağlanması burada yapılmaktadır.
        buttonTezgahGiris = findViewById(R.id.buttonTezgahGiris);
        buttonImalatGiris = findViewById(R.id.buttonImalatGiris);
        buttonAdminGiris = findViewById(R.id.buttonAdminGiris);
        buttonSevkiyatGiris = findViewById(R.id.buttonSevkiyatGiris);
        editTextParola = findViewById(R.id.editTextParola);

        personelleriGetir();

        /*Buttonların tıklanma metodları buradadır ve sayfa geçişleri.
          Buttonların altında bulunan editText'e parola girilir.
          Girilen parola doğru ise ilgili buttondan sonraki activity geçişi sağlanır.*/

        buttonTezgahGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean kontrol = false;
                for (String item : parolaTezgahList){
                    if (editTextParola.getText().toString().equals(item)){
                        kontrol=true;
                    }
                }
                if (kontrol){   // Girilen ile veritabanındaki parola eşit mi ?

                    startActivity(new Intent(MainActivity.this, TezgahActivity.class));

                }else{
                    Toast.makeText(MainActivity.this,"Yanlış Parola Girdiniz !",Toast.LENGTH_LONG).show();
                    Log.i("Hatalı Giris"," Tezgahtan Hatalı Giriş Yapıldı");
                }
                editTextParola.setText("");
            }
        });

        buttonImalatGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean kontrol = false;
                for (String item : parolaImalatList){
                    if (editTextParola.getText().toString().equals(item)){
                        kontrol=true;
                    }
                }
                if (kontrol){   // Girilen ile veritabanındaki parola eşit mi ?

                    startActivity(new Intent(MainActivity.this, ImalatActivity.class));

                }else{
                    Toast.makeText(MainActivity.this,"Yanlış Parola Girdiniz !",Toast.LENGTH_LONG).show();
                    Log.i("Hatalı Giris"," Imalathaneden Hatalı Giriş Yapıldı");
                }
                editTextParola.setText("");
            }
        });

        buttonAdminGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean kontrol = false;
                for (String item : parolaAdminList){
                    if (editTextParola.getText().toString().equals(item)){
                        kontrol=true;
                    }
                }
                if (kontrol){    // Girilen ile veritabanındaki parola eşit mi ?

                    startActivity(new Intent(MainActivity.this, AdminActivity.class));

                }else{
                    Toast.makeText(MainActivity.this,"Yanlış Parola Girdiniz !",Toast.LENGTH_LONG).show();
                    Log.i("Hatalı Giris"," Adminden Hatalı Giriş Yapıldı");
                }
                editTextParola.setText("");
            }
        });

        buttonSevkiyatGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean kontrol = false;
                for (String item : parolaSevkiyatList){
                    if (editTextParola.getText().toString().equals(item)){
                        kontrol=true;
                    }
                }
                if (kontrol){    // Girilen ile veritabanındaki parola eşit mi ?

                    Intent intent = new Intent(MainActivity.this,SevkiyatActivity.class);
                    String personelid = sifreyeGorePersonelBelirle(personelArrayList,editTextParola.getText().toString());
                    intent.putExtra("personel",personelid);
                    startActivity(intent);

                }else{
                    Toast.makeText(MainActivity.this,"Yanlış Parola Girdiniz !",Toast.LENGTH_LONG).show();
                    Log.i("Hatalı Giris"," Sevkiyattan Hatalı Giriş Yapıldı");
                }
                editTextParola.setText("");
            }
        });


    }

    public void personelleriGetir(){
        String url = "https://kristalekmek.com/personelleri_getir/personelleri_getir.php";

        StringRequest istek = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray personelListe = jsonObject.getJSONArray("personel");

                    for (int i =0;i<personelListe.length();i++){
                        JSONObject k = personelListe.getJSONObject(i);
                        Personel p = new Personel();
                        p.setPersonel_id(k.getInt("personel_id"));
                        p.setPersonel_sifre(k.getString("personel_sifre"));
                        p.setPersonel_alan(k.getString("calisma_alan"));
                        personelArrayList.add(p);
                    }
                    for (Personel item : personelArrayList){
                        switch (item.getPersonel_alan()){
                            case "admin":
                                parolaAdminList.add(item.getPersonel_sifre());
                                parolaTezgahList.add(item.getPersonel_sifre());
                                parolaImalatList.add(item.getPersonel_sifre());
                                parolaSevkiyatList.add(item.getPersonel_sifre());
                                break;
                            case "tezgah":
                                parolaTezgahList.add(item.getPersonel_sifre());
                                break;
                            case "imalat":
                                parolaImalatList.add(item.getPersonel_sifre());
                                break;
                            case "sevkiyat":
                                parolaSevkiyatList.add(item.getPersonel_sifre());
                                break;
                            default:
                                Toast.makeText(getApplicationContext(),"database personel Kontrol et !",Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(this).add(istek);

    }

    public String sifreyeGorePersonelBelirle(ArrayList<Personel> personelList,String gelenSifre){
        String personelID = null;

        for (Personel p : personelList){
            if (p.getPersonel_sifre().equals(gelenSifre)){
                personelID = String.valueOf(p.getPersonel_id());
            }
        }
        return personelID;
    }


}