package com.negd.umangwebview.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.negd.umangwebview.data.AppDataManager;
import com.negd.umangwebview.data.DataManager;
import com.negd.umangwebview.data.local.pref.AppPreferencesHelper;
import com.negd.umangwebview.data.local.pref.AppSecuredPreferencesHelper;
import com.negd.umangwebview.data.local.pref.IPreferencesHelper;
import com.negd.umangwebview.data.local.pref.ISecuredPreferencesHelper;
import com.negd.umangwebview.data.remote.ApiHeader;
import com.negd.umangwebview.data.remote.ApiHelper;
import com.negd.umangwebview.data.remote.AppApiHelper;
import com.negd.umangwebview.di.ApiInfo;
import com.negd.umangwebview.di.PreferenceInfo;
import com.negd.umangwebview.di.SecuredPreferenceInfo;
import com.negd.umangwebview.utils.AppConstants;
import com.negd.umangwebview.utils.EncryptionDecryptionAuthUtils;
import com.negd.umangwebview.utils.EncryptionDecryptionUtils;
import com.negd.umangwebview.utils.rx.AppSchedulerProvider;
import com.negd.umangwebview.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return "";
    }

//    @Provides
//    @Singleton
//    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
//        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
//                .build();
//    }
    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

//    @Provides
//    @DatabaseInfo
//    String provideDatabaseName() {
//        return AppConstants.DB_NAME;
//    }
//
//    @Provides
//    @Singleton
//    IDbHelper provideDbHelper(AppDbHelper appDbHelper) {
//        return appDbHelper;
//    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().setLenient().excludeFieldsWithoutExposeAnnotation().create();
    }
    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @SecuredPreferenceInfo
    String provideSecurePreferenceName() {
        return AppConstants.SECURE_PREF_NAME;
    }

    @Provides
    @Singleton
    IPreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ISecuredPreferencesHelper provideSecuredPreferencesHelper(AppSecuredPreferencesHelper appSecuredPreferncesHelper) {
        return appSecuredPreferncesHelper;
    }
    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey,
                                                           IPreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(preferencesHelper.getStringPreference(AppPreferencesHelper.PREF_BEARER,""));
    }

    @Provides
    @Singleton
    ApiHeader.PublicApiHeader providePublicApiHeader(@ApiInfo String apiKey,
                                                           IPreferencesHelper preferencesHelper) {
        return new ApiHeader.PublicApiHeader();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    EncryptionDecryptionAuthUtils provideEncryptionUtils(Context context)
    { return new EncryptionDecryptionAuthUtils(context);}

    @Provides
    EncryptionDecryptionUtils provideEncryptionDecryptionUtils(Context context) {
        return new EncryptionDecryptionUtils(context);}
}
