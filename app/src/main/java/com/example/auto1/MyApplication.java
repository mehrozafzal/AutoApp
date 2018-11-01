package com.example.auto1;

import android.app.Application;
import android.content.Context;

import com.example.auto1.dagger.component.AppActivityComponent;
import com.example.auto1.dagger.component.DaggerAppActivityComponent;
import com.example.auto1.dagger.module.AppModule;
import com.example.auto1.dagger.module.UtilsModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MyApplication extends Application {
    AppActivityComponent appActivityComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appActivityComponent = DaggerAppActivityComponent.builder().appModule(new AppModule(this)).utilsModule(new UtilsModule()).build();
        initTypeface();
    }

    public AppActivityComponent getAppActivityComponent() {
        return appActivityComponent;
    }

    private void initTypeface() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Poppins-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}