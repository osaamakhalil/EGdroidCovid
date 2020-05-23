
package com.example.covid.country.countryModel;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class Country {

    @SerializedName("code")
    private String mCode;
    @SerializedName("name")
    private String mName;



    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @NonNull
    @Override
    public String toString() {
        return mName + "  " + mCode;
    }
}
