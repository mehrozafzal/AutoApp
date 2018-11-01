package com.example.auto1.dagger.module;

import android.content.Context;

import com.example.auto1.utils.PreferenceUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    PreferenceUtils providePreferenceUtils(Context context) {
        return new PreferenceUtils(context);
    }
}