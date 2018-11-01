package com.example.auto1.repository;


import com.example.auto1.retrofit.ApiCallInterface;
import com.google.gson.JsonElement;


public class RemoteRepository {

    ApiCallInterface apiCallInterface;

    public RemoteRepository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    public io.reactivex.Observable<JsonElement> executeManufacturers(String page, String pageSize, String key) {
        return apiCallInterface.manufacturers(page, pageSize, key);
    }

    public io.reactivex.Observable<JsonElement> executeModels(String manufacturer, String page, String pageSize, String key) {
        return apiCallInterface.types(manufacturer, page, pageSize, key);
    }

    public io.reactivex.Observable<JsonElement> executeYear(String manufacturer, String model, String key) {
        return apiCallInterface.builtDates(manufacturer, model, key);
    }

}
