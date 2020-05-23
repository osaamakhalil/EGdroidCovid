
package com.example.covid.world.StatisticModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Statistics {


    @SerializedName("get")
    private String mGet;
    @SerializedName("response")
    private List<Response> mResponse;
    @SerializedName("results")
    private Long mResults;



    public String getGet() {
        return mGet;
    }

    public void setGet(String get) {
        mGet = get;
    }


    public List<Response> getResponse() {
        return mResponse;
    }

    public void setResponse(List<Response> response) {
        mResponse = response;
    }

    public Long getResults() {
        return mResults;
    }

    public void setResults(Long results) {
        mResults = results;
    }

}
