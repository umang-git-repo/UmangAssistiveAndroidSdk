package com.negd.umangwebview.data;

import android.content.Context;
import com.google.gson.Gson;
import com.negd.umangwebview.data.local.pref.IPreferencesHelper;
import com.negd.umangwebview.data.local.pref.ISecuredPreferencesHelper;
import com.negd.umangwebview.data.model.jp.DeviceListRequest;
import com.negd.umangwebview.data.remote.ApiHeader;
import com.negd.umangwebview.data.remote.ApiHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;
    private final Context mContext;
    private final Gson mGson;
    private final IPreferencesHelper mPreferencesHelper;
    private final ISecuredPreferencesHelper iSecuredPreferencesHelper;
    public static AppDataManager appDataManager;

    @Inject
    public AppDataManager(Context context, IPreferencesHelper preferencesHelper, ApiHelper apiHelper, Gson gson, ISecuredPreferencesHelper securedPreferencesHelper) {
        mContext = context;
//        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mGson = gson;
        iSecuredPreferencesHelper=securedPreferencesHelper;
        appDataManager=this;
    }

    @Override
    public ApiHeader getApiHeader() {
        return null;
    }

    @Override
    public Single<String> doGetDeviceList(DeviceListRequest request) {
        return mApiHelper.doGetDeviceList(request);
    }

    @Override
    public void writeStringPreference(String key, String value) {
        mPreferencesHelper.writeStringPreference(key,value);
    }

    @Override
    public String getStringPreference(String key, String defaultValue) {
        return mPreferencesHelper.getStringPreference(key,defaultValue);
    }

    @Override
    public void deleteAllPreference() {
        iSecuredPreferencesHelper.deleteSecuredPreference();
        mPreferencesHelper.deleteAllPreference();
    }


    @Override
    public void writeEncryptedStringPreference(String key, String value) {
        iSecuredPreferencesHelper.writeEncryptedStringPreference(key,value);
    }

    @Override
    public String getEncryptedStringPreference(String key, String defaultValue) {
        return iSecuredPreferencesHelper.getEncryptedStringPreference(key,defaultValue);
    }

    @Override
    public void deleteSecuredPreference() {
        iSecuredPreferencesHelper.deleteSecuredPreference();
    }
}
