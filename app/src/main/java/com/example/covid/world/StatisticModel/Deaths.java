
package com.example.covid.world.StatisticModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Deaths {

    @SerializedName("new")
    private Object mNew;
    @SerializedName("total")
    private Long mTotal;

    public Object getNew() {
        return mNew;
    }

    public void setNew(Object Mnew) {
        mNew = Mnew;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

}
