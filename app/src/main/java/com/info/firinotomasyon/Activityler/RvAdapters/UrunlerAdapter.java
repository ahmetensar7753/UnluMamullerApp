package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.info.firinotomasyon.Activityler.Classlar.Urunler;
import com.info.firinotomasyon.Activityler.TezgahActivity;
import com.info.firinotomasyon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UrunlerAdapter extends RecyclerView.Adapter<UrunlerAdapter.CardViewNesneleriniTutucu> {
    public static SecilenUrunlerAdapter adapter2;

    //Dışarıdan veri alabilmek için constructor ve context tanımı.
    private Context mContext;
    public static List<Urunler> urunlerDisaridanGelenList;

    public UrunlerAdapter(Context mContext, List<Urunler> urunlerDisaridanGelenList) {
        this.mContext = mContext;
        this.urunlerDisaridanGelenList = urunlerDisaridanGelenList;
    }

    //CardView tasarımının Adapter'a bağlanması ve tasarım üzerindeki görsel nesnelerin tanıtılması.

    public class CardViewNesneleriniTutucu extends RecyclerView.ViewHolder{
        public CardView cardViewUrunler;
        public ImageView imageViewUrunResim;
        public TextView textViewUrunAd;

        public CardViewNesneleriniTutucu(@NonNull View itemView) {
            super(itemView);
            cardViewUrunler = itemView.findViewById(R.id.cardViewUrunler);
            imageViewUrunResim = itemView.findViewById(R.id.imageViewUrunResim);
            textViewUrunAd = itemView.findViewById(R.id.textViewUrunAd);
        }
    }
    @NonNull
    @Override
    public CardViewNesneleriniTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_urunler,parent,false);
        return new CardViewNesneleriniTutucu(itemView);
    }


        //Recycler View hazır metodları- implemente etme zorunluluğu var.
        //Görsel nesnelerin satır satır doldurulması ve nesnenin yakalanması burada oluyor.

    @Override
    public void onBindViewHolder(@NonNull CardViewNesneleriniTutucu holder, int position) {
        final Urunler urun = urunlerDisaridanGelenList.get(position);

        holder.textViewUrunAd.setText(urun.getUrun_ad());

        //RESİMLER VERİTABANINDAN DİNAMİK OLARAK ÇEKİLİYOR.
        String url = "https://kristalekmek.com/urunler/resimler/"+urun.getUrun_resim_ad()+".png";
        Picasso.get().load(url).into(holder.imageViewUrunResim);

        TezgahActivity.rvSecilenUrunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        TezgahActivity.rvSecilenUrunler.setHasFixedSize(true);
        adapter2 = new SecilenUrunlerAdapter(mContext,SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList);
        TezgahActivity.rvSecilenUrunler.setAdapter(adapter2);


        holder.cardViewUrunler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                urun.setUrun_adet(Double.parseDouble(TezgahActivity.textViewUrunSayi.getText().toString())+urun.getUrun_adet());

                //Burada ürünün daha önceden eklenip eklenmediği kontrolü yapılıyor.
                for (int i = 0; i < SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.size();++i){
                    if (SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.get(i).getUrun_ad() == urun.getUrun_ad()){
                        SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.remove(urun);
                    }
                }

                /*
                Secilen Ürünler RecyclerView'ı activity'de static olarak tanımlayıp buradan erişerek kaldırıldı
                SecilenUrunlerAdapter'a da aynı şekilde static yöntemiyle erişilerek burada kaldırıldı.
                adapter da burda çağırıldığı için her tıklamada "UrunlerAdapter"daki urun nesnesi SecilenUrunlerin Constructor'una gönderildi ve işlem başarılı oldu.
                Bu yapı biraz daha fixlenebilir ama şuan çalışıyor, daha performanslı ve mantıklı hale gelebilir. */

                SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList.add(urun);
                TezgahActivity.rvSecilenUrunler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                TezgahActivity.rvSecilenUrunler.setHasFixedSize(true);
                adapter2 = new SecilenUrunlerAdapter(mContext,SecilenUrunlerAdapter.secilenUrunlerDisaridanGelenList);
                TezgahActivity.rvSecilenUrunler.setAdapter(adapter2);



                //eklenen ürünlerin toplam tutarda gösterilmesi
                double texttekiTutar = Double.parseDouble(TezgahActivity.textViewTutar.getText().toString());
                double toplamTutar = urun.getUrun_fiyat() * Double.parseDouble(TezgahActivity.textViewUrunSayi.getText().toString()) + texttekiTutar;
                TezgahActivity.textViewTutar.setText(String.valueOf(toplamTutar));


                //butona tıklanıp ürün eklendikten sonra ortadaki ürün adetini gösteren textView sıfırlanır. sonraki ürünü eklerken kolaylık sağlıyor.
                TezgahActivity.textViewUrunSayi.setText("0");


            }
        });
    }

    @Override
    public int getItemCount() {
        return urunlerDisaridanGelenList.size();
    }




}
