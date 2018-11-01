package com.example.auto1.dagger.component;

import com.example.auto1.dagger.module.AppModule;
import com.example.auto1.dagger.module.UtilsModule;
import com.example.auto1.view.fragments.ManufacturerFragment;
import com.example.auto1.view.fragments.ModelFragment;
import com.example.auto1.view.fragments.YearFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppActivityComponent {

    void doInjection(ManufacturerFragment fragment);
    void doInjection(ModelFragment fragment);
    void doInjection(YearFragment fragment);


}