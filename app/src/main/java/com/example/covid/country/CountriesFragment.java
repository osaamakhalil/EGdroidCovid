package com.example.covid.country;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid.R;
import com.example.covid.country.countryModel.Country;
import com.example.covid.country.countryModel.CountryWrapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountriesFragment extends Fragment implements OnCountryClickListener {
    private RecyclerView country_recycler;
    private CountryAdapter countryAdapter;
    private List<Country> countryList;

    public CountriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        country_recycler = view.findViewById(R.id.country_recycler);


        CountryWrapper country = jsonString(view.getContext());
        countryList = country.getCountries();
        country_recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        countryAdapter = new CountryAdapter(getContext(), countryList, this);
        country_recycler.setAdapter(countryAdapter);

    }

    @Override
    public void countryClicked(Country name) {
        Intent intent = new Intent(requireActivity(), CountryStatisticsActivity.class);
        //to set country name
        intent.putExtra("countryName", name.getName());
        intent.putExtra("IconCode", name.getCode());
        startActivity(intent);
    }

    private String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }

    private CountryWrapper jsonString(Context context) {
        String jsonFileString = getJsonFromAssets(context, "country.json");
        Log.i("data", jsonFileString);
        Gson gson = new Gson();

        CountryWrapper countries = gson.fromJson(jsonFileString, CountryWrapper.class);
        for (int i = 0; i < countries.getCountries().size(); i++) {
            Log.i("data", "> Item " + i + "\n" + countries.getCountries().get(i));
        }
        return countries;
    }

    public void search(String query) {
        if (query == null || query.isEmpty()) {
            countryAdapter.updateList(countryList);
        } else {
            countryAdapter.updateList(findUsingLoop(query, countryList));
        }
    }

    //search fun
    private List<Country> findUsingLoop(String search, List<Country> list) {
        List<Country> matches = new ArrayList<>();

        for (Country country : list) {
            String name = country.getName().toLowerCase();
            if (name.contains(search)) {
                matches.add(country);
            }
        }
        return matches;
    }
}

