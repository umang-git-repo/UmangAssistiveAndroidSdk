package com.negd.umangwebview;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.negd.umangwebview.di.component.DaggerAppComponent;
import com.negd.umangwebview.utils.AppLogger;
import com.negd.umangwebview.utils.rx.RxBusUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class UmangApplication extends Application implements HasActivityInjector ,HasSupportFragmentInjector{

//    {"rsalt":"@#digitalspice*\u0026","value":"R%d\u0026Wst676#(Na","ctrl":"$f%GY#JX^9H@","msalt":"56$f@D8H2x^","rdigi":"@#localedigitalumang*\u0026","dgvalue":"D@GUM4NG$#4PP","dgctrl":"$f%GY#JX^9H@","kreq":"@#umANG#$%\u0026etKey","kchat":"uMaNgcHat@SpiCey","kdigi":"$%loVHG@\u0026%*eaSey","webauth":"$P!(3UM4NG"}

    public static String u_s = "@#digitalspice*&";
    public static String u_x_k = "$f%GY#JX^9H@";
    public static String u_md_s = "R%d&Wst676#(Na";
    public static String u_mp_s = "56$f@D8H2x^";
    public static String w_a = "$P!(3UM4NG";
    public static String d_s = "@#localedigitalumang*&";
    public static String d_x = "$f%GY#JX^9H@";
    public static String d_m = "D@GUM4NG$#4PP";
    public static String u_k = "@#umANG#$%&etKey";
    public static String c_k = "uMaNgcHat@SpiCey";
    public static String d_k = "$%loVHG@&%*eaSey";

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

    private RxBusUtils rxBusUtils;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //RxBus
        rxBusUtils=new RxBusUtils();
        //Dagger init
        DaggerAppComponent.builder().application(this).build().inject(this);

        //App Logger
        AppLogger.init();

        //trust manager
//        mMTM = new MemorizingTrustManager(this);

        //Networking init
        AndroidNetworking.initialize(getApplicationContext());

        //init firebase
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setApplicationId("1:616074837787:android:05850f1cc8c8f917") // Required for Analytics.
//                .setProjectId("umang-c17d7") // Required for Firebase Installations.
//                .setApiKey("AAAAj3Dr_xs:APA91bEZi1pEFzwhA5dToL6PGaDBqKIyK3CIXPTqd_v_wyTjUMIm7LI1Xo1f1vqu0m8EjGZ2op2JCywWK3p9Mu-D83rWCdPlpTf-W7T2IX34omb1FjHN9i6Qecfu3DmvPb4pCvxMnLB477NM0_31j3diqdvaS7IHIg") // Required for Auth.
//                .build();
//        FirebaseApp.initializeApp(this, options, "UMANG");


        GsonParserFactory parserFactory=new GsonParserFactory();
        AndroidNetworking.setParserFactory(parserFactory);

//        initImageLoader(getApplicationContext());
//        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }
    }

    public RxBusUtils bus() {
        return rxBusUtils;
    }


    public static UmangApplication getApp(Context ctx) {
        return (UmangApplication) ctx.getApplicationContext();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentInjector;
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
