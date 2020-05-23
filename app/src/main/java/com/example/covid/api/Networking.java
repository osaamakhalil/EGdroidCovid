package com.example.covid.api;

import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.covid.country.CountryStatisticsActivity;
import com.example.covid.world.StatisticModel.Response;
import com.example.covid.world.StatisticModel.Statistics;

import java.util.List;

public class Networking {
    public static void getStatistics(final OnStatisticsListener listener) {
        AndroidNetworking.get("https://covid-193.p.rapidapi.com/statistics")
                .addHeaders("x-rapidapi-host", "covid-193.p.rapidapi.com")
                .addHeaders("x-rapidapi-key", "0afccce25amsh2c82f5da3a90addp1f3307jsn00d72e68da4c")
                .build()
                .getAsObject(Statistics.class, new ParsedRequestListener<Statistics>() {

                    @Override
                    public void onResponse(Statistics response) {

                        listener.onSuccess(response);

                    }

                    @Override
                    public void onError(ANError anError) {
                        listener.onFailed(anError.getMessage());
                    }
                });

    }

}
