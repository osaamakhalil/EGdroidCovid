package com.example.covid.saved;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.covid.R;
import com.example.covid.country.OnCountryClickListener;
import com.example.covid.saved.database.FavoriteModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SavedHolder> {
    private List<FavoriteModel> favoriteModelList;
    private OnSavedCountryClickListener onSavedCountryClickListener;

    public SavedAdapter(List<FavoriteModel> favoriteModelList, OnSavedCountryClickListener onSavedCountryClickListener) {
        this.favoriteModelList = favoriteModelList;
        this.onSavedCountryClickListener = onSavedCountryClickListener;
    }

    @NonNull
    @Override
    public SavedAdapter.SavedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_item, parent, false);
        return new SavedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.SavedHolder holder, int position) {
        holder.bind(favoriteModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return favoriteModelList.size();
    }

    public class SavedHolder extends RecyclerView.ViewHolder {

        private TextView countryName;
        private ImageView countryIcon;
        private View saved_country_item;

        public SavedHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.saved_country_name_tv);
            countryIcon = itemView.findViewById(R.id.saved_flag_icon);
            saved_country_item = itemView.findViewById(R.id.saved_country_item);
        }

        void bind(final FavoriteModel country) {

            countryName.setText(country.getName());
            Glide
                    .with(itemView.getContext())
                    .load("https://www.countryflags.io/" + country.getIconCode() + "/flat/64.png")
                    .into(countryIcon);
            saved_country_item.setOnClickListener(view -> {
                onSavedCountryClickListener.savedCountryClicked(country);
            });

        }
    }
}
