package com.example.auto1.viewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.auto1.repository.RemoteRepository;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    public RemoteRepository remoteRepository;

    @Inject
    public ViewModelFactory(RemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(remoteRepository);
        }
        return null;
    }

}
