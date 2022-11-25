package com.negd.umangwebview.data.remote;

import com.google.gson.Gson;
import com.negd.umangwebview.BuildConfig;
import com.negd.umangwebview.data.local.pref.AppPreferencesHelper;
import com.negd.umangwebview.data.model.jp.DeviceListRequest;
import com.negd.umangwebview.utils.CommonUtils;
import com.negd.umangwebview.utils.DeviceUtils;
import com.negd.umangwebview.utils.EncryptionDecryptionAuthUtils;
import com.negd.umangwebview.utils.EncryptionDecryptionUtils;
import com.negd.umangwebview.utils.OkHttpUtils;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader apiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader){ this.apiHeader= apiHeader;}

    @Inject
    public EncryptionDecryptionAuthUtils encryptionDecryptionAuthUtils;

    @Inject
    public EncryptionDecryptionUtils encryptionDecryptionUtils;

    @Inject
    public AppPreferencesHelper preferencesHelper;

    @Inject
    public Gson gson;

    @Override
    public ApiHeader getApiHeader() {
        return apiHeader;
    }

@Override
public Single<String> doGetDeviceList(DeviceListRequest request) {
    encryptionDecryptionUtils.setEncryptionType(EncryptionDecryptionUtils.NORMAL);
    return Rx2AndroidNetworking.post(ApiEndPoint.FETCH_BIOMETRIC_DEVICES_LIST)
            .addStringBody(encryptionDecryptionUtils.encrypt(gson.toJson(request)))
            .addHeaders("Content-Type","text/plain")
            .addHeaders("Authorization", BuildConfig.WSO2_BEARER)
            .addHeaders("X-REQUEST-CONTROL", DeviceUtils.getHashedToken())
            .addHeaders("X-REQUEST-TSTAMP", CommonUtils.getTimeStamp())
            .addHeaders("X-REQUEST-VALUE",DeviceUtils.getMD5(gson.toJson(request)))
            .addHeaders("X-REQUEST-UV",CommonUtils.getUniqueNumber())
            .addHeaders("User-Agent",System.getProperty("http.agent"))
            .addHeaders("x-api-key",BuildConfig.API_KEY)
            .setContentType("text/plain")
            .setOkHttpClient(OkHttpUtils.getOkHttpClient())
            .build()
            .getStringSingle();
  }
}
