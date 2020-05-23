package com.example.covid.country;

import android.content.Context;

import com.example.covid.country.countryModel.Country;
import com.example.covid.saved.database.FavoriteModel;

public interface OnCountryClickListener {
    void countryClicked(Country name);

}
