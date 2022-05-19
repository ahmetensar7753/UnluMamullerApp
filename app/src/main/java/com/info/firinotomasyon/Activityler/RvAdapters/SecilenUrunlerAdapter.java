package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.GramajUrunler;
import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.Activityler.TezgahActivity;
import com.info.firinotomasyon.Activityler.TezgahGramajActivity;
import com.info.firinotomasyon.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SecilenUrunlerAdapter extends RecyclerView.Adapter<SecilenUrunlerAdapter.CardViewNesneleriTutucu>{

    private Context mContext;
    public static List<Urunler> secilenUrunlerDisaridanGelenList = new ArrayList<>();

    public SecilenUrunlerAdapter(Context mContext, List<Urunler> secilenUrunlerDisaridanGelenList) {
        this.mContext = mContext;
        this.secilenUrunlerDisaridanGelenList = secilenUrunlerDisaridanGelenList;
    }


    //CardView tasarımının Adapter'a bağlanması ve tasarım üzerindeki görsel nesnelerin tanıtılması.

    public class CardViewNesneleriTutucu extends RecyclerView.ViewHolder {
        public CardView cardViewSecilenUrunler;
        private ImageView imageViewSecilenUrunResim;
        private TextView textViewSecilenUrunAd,textViewUrunAdet;

        public CardViewNesneleriTutucu(@NonNull View itemView) {
            super(itemView);
            cardViewSecilenUrunler = itemView.findViewById(R.id.cardViewSecilenUrunler);
            imageViewSecilenUrunResim = itemView.findViewById(R.id.imageViewSecilenUrunResim);
            textViewSecilenUrunAd = itemView.findViewById(R.id.textViewSecilenUrunAd);
            textViewUrunAdet = itemView.findViewById(R.id.textViewUrunAdet);
        }
    }


    @NonNull
    @Override
    public CardViewNesneleriTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_secilen_urunler,parent,false);
        return new CardViewNesneleriTutucu(itemView);
    }


    //Görsel nesnelerin satır satır doldurulması ve nesnenin yakalanması burada oluyor.

    @Override
    public void onBindViewHolder(@NonNull CardViewNesneleriTutucu holder, int position) {
        final Urunler urun = secilenUrunlerDisaridanGelenList.get(position);

        String url = "https://kristalekmek.com/urunler/resimler/"+urun.getUrun_resim_ad()+".png";
        Picasso.get().load(url).into(holder.imageViewSecilenUrunResim);

        holder.textViewSecilenUrunAd.setText(urun.getUrun_ad());

        if (urun.getKimlik().equals("gramaj")){
            holder.textViewUrunAdet.setText(String.valueOf(urun.getUrun_adet())+" Kg");
        }else{
            holder.textViewUrunAdet.setText(String.valueOf((int) urun.getUrun_adet())+" Adet");
        }




        holder.cardViewSecilenUrunler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // burada CardView ' e tıklandığı anda o cart siliniyor.
                holder.cardViewSecilenUrunler.removeAllViews();
                double adett = (double) urun.getUrun_adet();
                TezgahActivity.textViewTutar.setText(String.valueOf((Double.parseDouble(TezgahActivity.textViewTutar.getText().toString()))- (adett*urun.getUrun_fiyat())));
                urun.setUrun_adet(0);

            }
        }); if (urun.getUrun_adet() == 0){ holder.cardViewSecilenUrunler.removeAllViews();} // bu satırda ürün adeti sıfıra eşit olduğunda ürün tablodan siliniyor.


    }

    @Override
    public int getItemCount() {

        return secilenUrunlerDisaridanGelenList.size();
    }


}
