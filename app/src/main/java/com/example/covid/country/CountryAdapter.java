package com.example.covid.country;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.covid.R;
import com.example.covid.country.countryModel.Country;

import java.util.List;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder> {
    private Context mContext;
    private List<Country> countryList;
    private OnCountryClickListener onCountryClickListener;

    public CountryAdapter(Context mContext, List<Country> countryList, OnCountryClickListener onCountryClickListener) {
        this.mContext = mContext;
        this.countryList = countryList;
        this.onCountryClickListener = onCountryClickListener;
    }

    @NonNull
    @Override
    public CountryAdapter.CountryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.country_item, parent, false);
        return new CountryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.CountryHolder holder, int position) {
        holder.bind(countryList.get(position));


    }

    @Override
    public int getItemCount() {

        return countryList.size();
    }

    public void updateList(List<Country> countryList1){
        countryList = countryList1;
        notifyDataSetChanged();
    }

    public class CountryHolder extends RecyclerView.ViewHolder {
        private TextView country_name_tv;
        private View country_item;
        private ImageView flag_icon;


        public CountryHolder(@NonNull View itemView) {
            super(itemView);
            country_item = itemView.findViewById(R.id.country_item);
            country_name_tv = itemView.findViewById(R.id.country_name_tv);
            flag_icon = itemView.findViewById(R.id.flag_icon);

        }

        private void bind(final Country name) {

            country_name_tv.setText(name.getName());
            Glide
                    .with(itemView.getContext())
                    .load("https://www.countryflags.io/" + name.getCode() + "/flat/64.png")
                    .into(flag_icon);

            country_item.setOnClickListener(view -> {
                onCountryClickListener.countryClicked(name);
            });
        }
    }
}
