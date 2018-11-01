package com.example.auto1.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

public class YearResponse {
    @SerializedName("wkda")
    @Expose
    private LinkedHashMap<String, String> wkdaYearMap;

    public LinkedHashMap<String, String> getWkdaYearMap() {
        return wkdaYearMap;
    }

    public void setWkdaYearMap(LinkedHashMap<String, String> wkdaYearMap) {
        this.wkdaYearMap = wkdaYearMap;
    }
}
