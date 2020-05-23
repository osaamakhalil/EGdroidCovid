package com.example.covid.saved;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid.R;
import com.example.covid.country.CountryStatisticsActivity;
import com.example.covid.country.countryModel.Country;
import com.example.covid.country.countryModel.CountryWrapper;
import com.example.covid.saved.database.FavoriteModel;
import com.example.covid.saved.database.SavedCountryDatabase;
import com.example.covid.saved.database.SavedRepo;
import com.example.covid.saved.database.asynctask.InsertAsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedFragment extends Fragment implements OnSavedCountryClickListener {
    private RecyclerView savedRecycler;
    private SavedAdapter adapter;
    private SavedRepo savedRepo;

    private List<FavoriteModel> list = new ArrayList<>();


    public SavedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        savedRepo = new SavedRepo(getContext());
        getAllCountries();

        //set recycler view
        savedRecycler = view.findViewById(R.id.saved_recycler);
        savedRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new SavedAdapter(list,this);
        savedRecycler.setAdapter(adapter);
    }


    private void getAllCountries() {
        savedRepo.getAllCountry().observe(this, new Observer<List<FavoriteModel>>() {
            @Override
            public void onChanged(List<FavoriteModel> favoriteModels) {
                if (list.size() > 0) {
                    list.clear();
                }
                if (favoriteModels != null) {
                    list.addAll(favoriteModels);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void savedCountryClicked(FavoriteModel country) {
        Intent intent = new Intent(requireActivity(),CountryStatisticsActivity.class);
        intent.putExtra("countryName",country.getName());
        intent.putExtra("IconCode",country.getIconCode());
        startActivity(intent);



    }
}
