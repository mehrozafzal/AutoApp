package com.example.auto1.view.pagination;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.auto1.constants.ResponseVariables;
import com.example.auto1.repository.RemoteRepository;
import com.example.auto1.retrofit.Status;
import com.example.auto1.retrofit.response.ManufacturerResponse;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class ManufacturerDataSource extends PageKeyedDataSource<Integer, ManufacturerResponse> {

    private static final String PAGE_SIZE = "15";
    private static final String PAGE = "1";
    private RemoteRepository repository;
    private Gson gson;
    private int sourceIndex;
    private MutableLiveData<String> progressLiveStatus;
    private CompositeDisposable compositeDisposable;

    public ManufacturerDataSource(RemoteRepository repository, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        progressLiveStatus = new MutableLiveData<>();
        GsonBuilder builder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gson = builder.setLenient().create();
    }

    public MutableLiveData<String> getProgressLiveStatus() {
        return progressLiveStatus;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ManufacturerResponse> callback) {
        repository.executeManufacturers(PAGE, PAGE_SIZE, ResponseVariables.wa_key).doOnSubscribe(disposable -> {
            compositeDisposable.add(disposable);
            progressLiveStatus.postValue(Status.LOADING.toString());
        }).subscribe((JsonElement result) ->
                {
                    progressLiveStatus.postValue(Status.COMPLETED.toString());
//
                    JSONObject object = new JSONObject(gson.toJson(result));
                    JSONArray array = object.getJSONArray("articles");

                    ArrayList<ManufacturerResponse> arrayList = new ArrayList<>();
//
//            for (int i = 0; i < array.length(); i++) {
//                arrayList.add(new NewsModelClass(array.getJSONObject(i).optString("title"),
//                        array.getJSONObject(i).optString("urlToImage")));
//            }

                    sourceIndex++;
                    callback.onResult(arrayList, null, sourceIndex);
                },
                throwable ->
                        progressLiveStatus.postValue(Status.COMPLETED.toString())
        );
//                .doOnSubscribe(disposable ->
//                {
//                    compositeDisposable.add(disposable);
//                    progressLiveStatus.postValue(Constant.LOADING);
//                })
//                .subscribe(
//                        (JsonElement result) ->
//                        {
//                            progressLiveStatus.postValue(Constant.LOADED);
//
//                            JSONObject object = new JSONObject(gson.toJson(result));
//                            JSONArray array = object.getJSONArray("articles");
//
//                            ArrayList<NewsModelClass> arrayList = new ArrayList<>();
//
//                            for (int i = 0; i < array.length(); i++) {
//                                arrayList.add(new NewsModelClass(array.getJSONObject(i).optString("title"),
//                                        array.getJSONObject(i).optString("urlToImage")));
//                            }
//
//                            sourceIndex++;
//                            callback.onResult(arrayList, null, sourceIndex);
//                        },
//                        throwable ->
//                                progressLiveStatus.postValue(Constant.LOADED)
//
//                );
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ManufacturerResponse> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ManufacturerResponse> callback) {
        repository.executeManufacturers(PAGE, PAGE_SIZE, ResponseVariables.wa_key).doOnSubscribe(disposable -> {
            compositeDisposable.add(disposable);
            progressLiveStatus.postValue(Status.LOADING.toString());
        }).subscribe((JsonElement result) ->
                {
                    progressLiveStatus.postValue(Status.COMPLETED.toString());
//
                    JSONObject object = new JSONObject(gson.toJson(result));
                    JSONArray array = object.getJSONArray("articles");

                    ArrayList<ManufacturerResponse> arrayList = new ArrayList<>();
//
//            for (int i = 0; i < array.length(); i++) {
//                arrayList.add(new NewsModelClass(array.getJSONObject(i).optString("title"),
//                        array.getJSONObject(i).optString("urlToImage")));
//            }

                    sourceIndex++;
                    callback.onResult(arrayList, params.key == 3 ? null : params.key + 1);
                },
                throwable ->
                        progressLiveStatus.postValue(Status.COMPLETED.toString())
        );
    }
}