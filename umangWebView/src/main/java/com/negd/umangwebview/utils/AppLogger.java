package com.negd.umangwebview.utils;

import android.util.Log;

import timber.log.Timber;

public final class AppLogger {

    private AppLogger() {
        // This utility class is not publicly instantiable
    }

    public static void d(String s, Object... objects) {
        Timber.d(s, objects);
    }

    public static void d(Throwable throwable, String s, Object... objects) {

        Timber.d(throwable, s, objects[0].toString());
    }

    public static void e(String s, Object... objects) {
        try{
            Timber.e(s, objects[0].toString());
        }catch (Exception ex){
            Log.e(s,s);
        }

    }

    public static void e(Throwable throwable, String s, Object... objects) {
        Timber.e(throwable, s, objects[0].toString());
    }

    public static void i(String s, Object... objects) {
        Timber.i(s, objects[0].toString());
    }

    public static void i(Throwable throwable, String s, Object... objects) {
        Timber.i(throwable, s, objects[0].toString());
    }

    public static void init() {

            Timber.plant(new Timber.DebugTree());

    }

    public static void w(String s, Object... objects) {
        Timber.w(s, objects[0].toString());
    }

    public static void w(Throwable throwable, String s, Object... objects) {
        Timber.w(throwable, s, objects[0].toString());
    }
}
