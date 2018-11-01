package com.example.auto1.retrofit;


import com.example.auto1.constants.UrlConstants;
import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCallInterface {

    @GET(UrlConstants.MANUFACTURER_URL)
    Observable<JsonElement> manufacturers(@Query("page") String page, @Query("pageSize") String pageSize, @Query("wa_key") String key);

    @GET(UrlConstants.TYPE_URL)
    Observable<JsonElement> types(@Query("manufacturer") String manufacturer,@Query("page") String page, @Query("pageSize") String pageSize, @Query("wa_key") String key);

    @GET(UrlConstants.BUILT_DATE_URL)
    Observable<JsonElement> builtDates(@Query("manufacturer") String manufacturer, @Query("main-type") String main_type, @Query("wa_key") String key);

}


