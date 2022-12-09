package com.negd.umangwebview;

import android.app.Application;
import android.content.Context;
import com.negd.umangwebview.utils.AppLogger;



public class UmangApplication extends Application {

    public static boolean openingIntent = false;
//    public MemorizingTrustManager mMTM;
    public static boolean isMobileNumberChange=false;

    public static boolean isAccountDelete=false;

    public static boolean isChatConnected=false;

    public static boolean isNewDocFetched=false;

    public static boolean isNewDocUploaded=false;

    public static boolean isBbpsDataUpdated=false;

    public static boolean isFavoriteChange=false;

    public static boolean isProfileDataChange=false;

    public static String docType="";

    @Override
    public void onCreate() {
        super.onCreate();
        //App Logger
        AppLogger.init();

    }



    public static UmangApplication getApp(Context ctx) {
        return (UmangApplication) ctx.getApplicationContext();
    }

//    public static void initImageLoader(Context context) {
//        // This configuration tuning is custom. You can tune every option, you may tune some of them,
//        // or you can create default configuration by
//        //  ImageLoaderConfiguration.createDefault(this);
//        // method.
//        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
//        config.threadPriority(Thread.NORM_PRIORITY - 2);
//        config.denyCacheImageMultipleSizesInMemory();
//        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
//        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
//        config.tasksProcessingOrder(QueueProcessingType.LIFO);
////        config.writeDebugLogs(); // Remove for release app
//        L.writeLogs(false);
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config.build());
//    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        String lang = "en";
//
//        super.attachBaseContext(LocaleHelper.onAttach(base, lang));
//        MultiDex.install(this);
//    }
}
