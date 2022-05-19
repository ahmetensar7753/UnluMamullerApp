package com.info.firinotomasyon.Activityler.RvAdapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info.firinotomasyon.Activityler.Classlar.SevkiyatMusteriler;
import com.info.firinotomasyon.Activityler.SevkiyatSiparisSirasiActivity;
import com.info.firinotomasyon.R;

import java.util.ArrayList;

public class SevkiyatMusteriSiraAdapter extends RecyclerView.Adapter<SevkiyatMusteriSiraAdapter.CardViewMusteriSiraNesneTutucu>{

    private Context mContext;
    private ArrayList<SevkiyatMusteriler> disaridanGelenList = new ArrayList<>();

    public static ArrayList<SevkiyatMusteriler> disariyaGidenList = new ArrayList<>();


    public SevkiyatMusteriSiraAdapter(Context mContext,ArrayList<SevkiyatMusteriler> disaridanGelenList) {
        this.mContext = mContext;
        this.disaridanGelenList = disaridanGelenList;
    }

    public class CardViewMusteriSiraNesneTutucu extends RecyclerView.ViewHolder {
        TextView textViewSevkiyatSiraMusteriAd;
        EditText editTextSevkiyatMusteriSira;
        Button buttonSiraKaydet;
        public CardViewMusteriSiraNesneTutucu(@NonNull View itemView) {
            super(itemView);
            textViewSevkiyatSiraMusteriAd = itemView.findViewById(R.id.textViewSevkiyatSiraMusteriAd);
            editTextSevkiyatMusteriSira = itemView.findViewById(R.id.editTextSevkiyatMusteriSira);
            buttonSiraKaydet = itemView.findViewById(R.id.buttonSiraKaydet);
        }
    }

    @NonNull
    @Override
    public CardViewMusteriSiraNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim_sevkiyat_musteri_sira,parent,false);
        return new CardViewMusteriSiraNesneTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewMusteriSiraNesneTutucu holder, int position) {
        SevkiyatMusteriler musteri = disaridanGelenList.get(position);

        holder.textViewSevkiyatSiraMusteriAd.setText(musteri.getMusteriAd());
        holder.editTextSevkiyatMusteriSira.setText(String.valueOf(musteri.getMusteriSira()));

        holder.buttonSiraKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!holder.editTextSevkiyatMusteriSira.getText().toString().trim().equals("")){
                    disariyaGidenList.remove(musteri);
                    musteri.setMusteriSira(Integer.parseInt(holder.editTextSevkiyatMusteriSira.getText().toString()));
                    disariyaGidenList.add(musteri);
                }

                SevkiyatSiparisSirasiActivity.buttonSiparisSiraKaydet.callOnClick();

            }
        });



    }

    @Override
    public int getItemCount() {
        return disaridanGelenList.size();
    }



}
