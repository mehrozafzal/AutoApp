package com.example.auto1.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import static com.example.auto1.constants.SharedPrefsVariables.APP_PREFERENCE_NAME;


public class PreferenceUtils {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public PreferenceUtils(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void setStringPref(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringPref(String key) {
        String result = "n/a";
        if (sharedPreferences != null)
            result = sharedPreferences.getString(key, "");
        return result;
    }

    public void setIntegerPref(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public Integer getIntegerPref(String key) {
        int result = -1;
        if (sharedPreferences != null)
            result = sharedPreferences.getInt(key, -1);
        return result;
    }

    public void setFloatPref(String key, Float value) {
        editor.putFloat(key, value);
        editor.apply();
    }


    public Float getFloatPref(String key) {
        float result = 0;
        if (sharedPreferences != null)
            result = sharedPreferences.getFloat(key, -1);
        return result;
    }

    public void setBooleanPref(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }


    public Boolean getBooleanPref(String key) {
        Boolean result = false;
        if (sharedPreferences != null)
            result = sharedPreferences.getBoolean(key, false);
        return result;
    }


    public String convertToStringJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public Object convertStringToObject(String strObject, Class<?> classObject) {
        Gson gson = new Gson();
        return gson.fromJson(strObject, classObject);

    }

}
