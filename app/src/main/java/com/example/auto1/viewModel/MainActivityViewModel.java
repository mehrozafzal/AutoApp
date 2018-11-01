package com.example.auto1.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.auto1.repository.RemoteRepository;
import com.example.auto1.retrofit.ApiResponse;
import com.example.auto1.utils.DisposableManagerUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {

    public RemoteRepository remoteRepository;
    private final MutableLiveData<ApiResponse> manufacturerResponseLiveData = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse> modelResponseLiveData = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse> yearResponseLiveData = new MutableLiveData<>();


    public MainActivityViewModel(RemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    public MutableLiveData<ApiResponse> manufacturerResponse() {
        return manufacturerResponseLiveData;
    }

    public MutableLiveData<ApiResponse> modelResponse() {
        return modelResponseLiveData;
    }

    public MutableLiveData<ApiResponse> yearResponse() {
        return yearResponseLiveData;
    }

    public void hitManufacturerApi(String page, String pageSize, String key) {
        DisposableManagerUtils.add(remoteRepository.executeManufacturers(page, pageSize, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> manufacturerResponseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> manufacturerResponseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> manufacturerResponseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }

    public void hitModelApi(String manufacturer, String page, String pageSize, String key) {
        DisposableManagerUtils.add(remoteRepository.executeModels(manufacturer, page, pageSize, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> modelResponseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> modelResponseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> modelResponseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }

    public void hitYearApi(String manufacturer, String model, String key) {
        DisposableManagerUtils.add(remoteRepository.executeYear(manufacturer, model, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> yearResponseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> yearResponseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> yearResponseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        DisposableManagerUtils.clear();
    }
}
