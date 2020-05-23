package com.example.covid.api;

import com.example.covid.world.StatisticModel.Statistics;

public interface OnStatisticsListener {
    void onSuccess(Statistics response);

    void onFailed(String message);
}
