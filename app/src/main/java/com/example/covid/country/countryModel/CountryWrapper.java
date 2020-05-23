package com.example.covid.country.countryModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryWrapper {
    @SerializedName("countries")
    private List<Country> countries;

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
