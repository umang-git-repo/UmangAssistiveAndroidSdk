package com.negd.umangwebview.utils;

import android.util.Log;

import com.negd.umangwebview.BuildConfig;


public class LogUtil {

    private LogUtil(){
        // private constructor
    }

    private static boolean PRINT_LOG = BuildConfig.DEBUG;

    public static void d(String tag, String msg) {
        if (PRINT_LOG)
            Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (PRINT_LOG)
            Log.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (PRINT_LOG)
            Log.v(tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        if (PRINT_LOG)
            Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (PRINT_LOG)
            Log.e(tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        if (PRINT_LOG)
            Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Exception e) {
        if (PRINT_LOG)
            Log.i(tag, msg, e);
    }

    public static void printStackTrace(Exception ex) {
        if (PRINT_LOG)
            ex.printStackTrace();

    }

    public static void printStackTrace(OutOfMemoryError ex) {
        if (PRINT_LOG)
            ex.printStackTrace();

    }

    public static void printStackTrace(Throwable exception) {
        if (PRINT_LOG)
            exception.printStackTrace();
    }


}