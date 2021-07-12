package com.devidea.grigoapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsHelper {

    public static final String PREFERENCE_NAME = "HELPER";
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;

    // SingleTon 으로 생성
    private static class LazyHolder {
        public static final PrefsHelper uniqueInstance = new PrefsHelper();
    }

    public static PrefsHelper getInstance() {
        return LazyHolder.uniqueInstance;
    }

    // init
    public static void init(Context context) {
        prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    // String read, write
    public static String read(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    public static void write(String key, String value) {
        prefsEditor.putString(key, value).apply();
    }

    // Integer read, write
    public static Integer read(String key, int defValue) {
        return prefs.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        prefsEditor.putInt(key, value).apply();
    }

    // boolean read, write
    public static boolean read(String key, boolean defValue) {
        return prefs.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        prefsEditor.putBoolean(key, value).apply();
    }

    // clear
    public static void destroyPref() {
        prefsEditor.clear().apply();
    }
}
