package com.negd.umangwebview;

import android.app.Application;
import com.negd.umangwebview.utils.AppLogger;



public class UmangApplication extends Application {

    public static boolean openingIntent = false;
    @Override
    public void onCreate() {
        super.onCreate();
        //App Logger
        AppLogger.init();
    }
}
