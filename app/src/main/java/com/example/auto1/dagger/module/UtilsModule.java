package com.example.auto1.dagger.module;

import android.arch.lifecycle.ViewModelProvider;

import com.example.auto1.constants.UrlConstants;
import com.example.auto1.repository.RemoteRepository;
import com.example.auto1.retrofit.ApiCallInterface;
import com.example.auto1.viewModel.ViewModelFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(UrlConstants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    ApiCallInterface getApiCallInterface(Retrofit retrofit) {
        return retrofit.create(ApiCallInterface.class);
    }

    @Provides
    @Singleton
    OkHttpClient getRequestHeader() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder().build();
            return chain.proceed(request);
        }).connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS).
                readTimeout(300, TimeUnit.SECONDS);

        return httpClient.build();
    }

    @Provides
    @Singleton
    RemoteRepository getRepository(ApiCallInterface apiCallInterface) {
        return new RemoteRepository(apiCallInterface);
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(RemoteRepository myRepository) {
        return new ViewModelFactory(myRepository);
    }
}
