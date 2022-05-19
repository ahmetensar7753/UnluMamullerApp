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

import com.info.firinotomasyon.Activityler.Classlar.SecilenUrunler;
import com.info.firinotomasyon.Activityler.ImalatActivity;
import com.info.firinotomasyon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImalatSecilenUrunlerAdapter extends RecyclerView.Adapter<ImalatSecilenUrunlerAdapter.CardViewNesneleriniTutucuImalathane> {

    private Context mContext;
    private List<SecilenUrunler> secilenUrunlerList;


    public ImalatSecilenUrunlerAdapter(Context mContext,List<SecilenUrunler> secilenUrunlerList) {
        this.mContext = mContext;
        this.secilenUrunlerList = secilenUrunlerList;
    }

    //CardView tasarımının Adapter'a bağlanması ve tasarım üzerindeki görsel nesnelerin tanıtılması.        **INNER CLASS**

    public class CardViewNesneleriniTutucuImalathane extends RecyclerView.ViewHolder{

        public CardView cardViewSecilenUrunler;
        private ImageView imageViewSecilenUrunResim;
        private TextView textViewSecilenUrunAd,textViewUrunAdet;

        public CardViewNesneleriniTutucuImalathane(@NonNull View itemView) {
            super(itemView);
            cardViewSecilenUrunler = itemView.findViewById(R.id.cardViewSecilenUrunler);
            imageViewSecilenUrunResim = itemView.findViewById(R.id.imageViewSecilenUrunResim);
            textViewSecilenUrunAd = itemView.findViewById(R.id.textViewSecilenUrunAd);
            textViewUrunAdet = itemView.findViewById(R.id.textViewUrunAdet);
        }
    }

    @NonNull
    @Override
    public CardViewNesneleriniTutucuImalathane onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_secilen_urunler,parent,false);
        return new CardViewNesneleriniTutucuImalathane(itemView);
    }

    //Görsel nesnelerin satır satır doldurulması ve nesnenin yakalanması burada oluyor.

    @Override
    public void onBindViewHolder(@NonNull CardViewNesneleriniTutucuImalathane holder, int position) {
        final SecilenUrunler secilenUrunler = secilenUrunlerList.get(position);

        String url = "https://kristalekmek.com/urunler/resimler/"+secilenUrunler.getResim_ad()+".png";
        Picasso.get().load(url).into(holder.imageViewSecilenUrunResim);

        holder.textViewSecilenUrunAd.setText(secilenUrunler.getAd());
        holder.textViewUrunAdet.setText(String.valueOf(secilenUrunler.getAdet()));

        holder.cardViewSecilenUrunler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CARTA TIKLANDIĞINDA LİSTEDEN O ÜRÜN KALKIYOR.
                holder.cardViewSecilenUrunler.removeAllViews();
                ImalatActivity.secilenUrunlerHashSet.remove(secilenUrunler);
            }
        });

    }

    @Override
    public int getItemCount() {

        return secilenUrunlerList.size();
    }






}
