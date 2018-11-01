package com.example.auto1.view.pagination;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.auto1.repository.RemoteRepository;
import com.example.auto1.retrofit.response.ManufacturerResponse;

import io.reactivex.disposables.CompositeDisposable;

public class ManufacturerSourceFactory extends DataSource.Factory<Integer,ManufacturerResponse> {

    private MutableLiveData<ManufacturerDataSource> liveData;
    private RemoteRepository repository;
    private CompositeDisposable compositeDisposable;

    public ManufacturerSourceFactory(RemoteRepository repository, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<ManufacturerDataSource> getMutableLiveData() {
        return liveData;
    }

    @Override
    public DataSource<Integer, ManufacturerResponse> create() {
        ManufacturerDataSource dataSourceClass = new ManufacturerDataSource(repository, compositeDisposable);
        liveData.postValue(dataSourceClass);
        return dataSourceClass;
    }
}
