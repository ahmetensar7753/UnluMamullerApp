package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.info.firinotomasyon.Activityler.Classlar.GramajUrunler;
import com.info.firinotomasyon.Activityler.TezgahActivity;
import com.info.firinotomasyon.Activityler.TezgahGramajActivity;
import com.info.firinotomasyon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GramajUrunlerAdapter extends RecyclerView.Adapter<GramajUrunlerAdapter.CardViewGramajNesneleriTutucu>{
    public static GramajSecilenUrunlerAdapter adapterr;

    private Context mContext;
    private List<GramajUrunler> disaridanGelenListe;

    public GramajUrunlerAdapter(Context mContext, List<GramajUrunler> disaridanGelenListe) {
        this.mContext = mContext;
        this.disaridanGelenListe = disaridanGelenListe;
    }

    public class CardViewGramajNesneleriTutucu extends RecyclerView.ViewHolder{
        public ImageView imageViewGramajResim;
        public TextView textViewGramajAd;
        public CardView cardViewGramajUrunler;
        public CardViewGramajNesneleriTutucu(@NonNull View itemView) {
            super(itemView);
            imageViewGramajResim = itemView.findViewById(R.id.imageViewGramajResim);
            textViewGramajAd = itemView.findViewById(R.id.textViewGramajAd);
            cardViewGramajUrunler = itemView.findViewById(R.id.cardViewGramajUrunler);
        }
    }

    @NonNull
    @Override
    public CardViewGramajNesneleriTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_gramajli_urunler,parent,false);
        return new CardViewGramajNesneleriTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewGramajNesneleriTutucu holder, int position) {
        final GramajUrunler gramajUrunler = disaridanGelenListe.get(position);

        holder.textViewGramajAd.setText(gramajUrunler.getUrun_ad());

        //RESİMLER VT'DEN DİNAMİK OLARAK ÇEKİLİYOR.

        String url = "https://kristalekmek.com/gramaj_urunler/resimler/"+gramajUrunler.getResim_ad()+".png";
        Picasso.get().load(url).into(holder.imageViewGramajResim);

        holder.cardViewGramajUrunler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TezgahGramajActivity.textViewAgirlik.getText().toString().equals("")){
                    TezgahGramajActivity.textViewAgirlik.setText(String.valueOf(0.0));
                }

                gramajUrunler.setUrun_agirlik(Double.parseDouble(TezgahGramajActivity.textViewAgirlik.getText().toString())+gramajUrunler.getUrun_agirlik());

                //Burada ürünün daha önceden eklenip eklenmediği kontrolü yapılıyor.
                for (int i = 0; i < GramajSecilenUrunlerAdapter.gramajUrunlerSecilenDisaridanGelenList.size();++i){
                    if (GramajSecilenUrunlerAdapter.gramajUrunlerSecilenDisaridanGelenList.get(i).getUrun_ad() == gramajUrunler.getUrun_ad()){
                        GramajSecilenUrunlerAdapter.gramajUrunlerSecilenDisaridanGelenList.remove(gramajUrunler);
                    }
                }

                double texttekiTutar = Double.parseDouble(TezgahGramajActivity.textViewGramajTutar.getText().toString());
                double toplamTutar = gramajUrunler.getUrun_fiyat() * Double.parseDouble(TezgahGramajActivity.textViewAgirlik.getText().toString()) + texttekiTutar;

                TezgahGramajActivity.textViewAgirlik.setText("");
                TezgahGramajActivity.textViewKG.setVisibility(View.INVISIBLE);
                TezgahGramajActivity.buttonSil00.setVisibility(View.INVISIBLE);




                GramajSecilenUrunlerAdapter.gramajUrunlerSecilenDisaridanGelenList.add(gramajUrunler);
                TezgahGramajActivity.rvGramajSecilenUrunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                TezgahGramajActivity.rvGramajSecilenUrunler.setHasFixedSize(true);
                adapterr = new GramajSecilenUrunlerAdapter(mContext,GramajSecilenUrunlerAdapter.gramajUrunlerSecilenDisaridanGelenList);
                TezgahGramajActivity.rvGramajSecilenUrunler.setAdapter(adapterr);




                TezgahGramajActivity.textViewGramajTutar.setText(String.valueOf(toplamTutar));



            }
        });


    }

    @Override
    public int getItemCount() {
        return disaridanGelenListe.size();
    }




}
