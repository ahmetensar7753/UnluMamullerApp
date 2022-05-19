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

import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.Activityler.ImalatActivity;
import com.info.firinotomasyon.R;
import com.squareup.picasso.Picasso;

import java.util.List;
public class ImalatUrunlerAdapter extends RecyclerView.Adapter<ImalatUrunlerAdapter.CardViewNesneleriniTutucu>{
    public SecilenUrunlerAdapter adapter3;


    //Dışarıdan veri alabilmek için constructor ve context tanımı.
    private Context mContext;
    public static List<Urunler> urunlerDisaridanGelenList;

    public ImalatUrunlerAdapter(Context mContext, List<Urunler> urunlerDisaridanGelenList) {
        this.mContext = mContext;
        this.urunlerDisaridanGelenList = urunlerDisaridanGelenList;
    }

    //CardView tasarımının Adapter'a bağlanması ve tasarım üzerindeki görsel nesnelerin tanıtılması.

    public class CardViewNesneleriniTutucu extends RecyclerView.ViewHolder{
        public CardView cardViewUrunlerimalat;
        public ImageView imageViewUrunResimimalat;
        public TextView textViewUrunAdimalat;

        public CardViewNesneleriniTutucu(@NonNull View itemView) {
            super(itemView);
            cardViewUrunlerimalat = itemView.findViewById(R.id.cardViewUrunler);
            imageViewUrunResimimalat = itemView.findViewById(R.id.imageViewUrunResim);
            textViewUrunAdimalat = itemView.findViewById(R.id.textViewUrunAd);
        }
    }
    @NonNull
    @Override
    public ImalatUrunlerAdapter.CardViewNesneleriniTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_urunler,parent,false);
        return new ImalatUrunlerAdapter.CardViewNesneleriniTutucu(itemView);
    }


    //Recycler View hazır metodları- implemente etme zorunluluğu var.
    //Görsel nesnelerin satır satır doldurulması ve nesnenin yakalanması burada oluyor.

    @Override
    public void onBindViewHolder(@NonNull ImalatUrunlerAdapter.CardViewNesneleriniTutucu holder, int position) {
        final Urunler urun = urunlerDisaridanGelenList.get(position);

        String url = "https://kristalekmek.com/urunler/resimler/"+urun.getUrun_resim_ad()+".png";
        Picasso.get().load(url).into(holder.imageViewUrunResimimalat);

        holder.textViewUrunAdimalat.setText(urun.getUrun_ad());


        holder.cardViewUrunlerimalat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImalatActivity.textView4.setVisibility(View.VISIBLE);
                ImalatActivity.textView3.setVisibility(View.VISIBLE);
                ImalatActivity.constraintLayout.setVisibility(View.VISIBLE);
                ImalatActivity.textViewImalatAdet.setVisibility(View.VISIBLE);
                ImalatActivity.textViewImalatUrunAd.setVisibility(View.VISIBLE);
                ImalatActivity.buttonImalatEkle.setVisibility(View.VISIBLE);
                ImalatActivity.imageViewImalatUrunResim.setVisibility(View.VISIBLE);
                Picasso.get().load(url).into( ImalatActivity.imageViewImalatUrunResim);
                ImalatActivity.textViewImalatUrunAd.setText(urun.getUrun_ad());
                ImalatActivity.imageViewImalatUrunResimAd = urun.getUrun_resim_ad();

            }
        });

    }



    @Override
    public int getItemCount() {
        return urunlerDisaridanGelenList.size();
    }


}
