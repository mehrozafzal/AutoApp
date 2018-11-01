package com.example.auto1.retrofit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonElement;

import static com.example.auto1.retrofit.Status.ERROR;
import static com.example.auto1.retrofit.Status.LOADING;
import static com.example.auto1.retrofit.Status.SUCCESS;


public class ApiResponse {

    public final Status status;

    @Nullable
    public final JsonElement data;

    @Nullable
    public final Throwable error;

    private ApiResponse(Status status, @Nullable JsonElement data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse success(@NonNull JsonElement data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }

}