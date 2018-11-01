package com.example.auto1.utils;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DisposableManagerUtils {

    private static CompositeDisposable compositeDisposable;

    private DisposableManagerUtils() {
    }


    public static void add(Disposable disposable) {
        getCompositeDisposable().add(disposable);
    }

    public static void dispose() {
        getCompositeDisposable().dispose();
    }

    public static void clear() {
        if (compositeDisposable != null)
            compositeDisposable.clear();
    }


    private static CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null || compositeDisposable.isDisposed())
            compositeDisposable = new CompositeDisposable();
        return compositeDisposable;
    }

}