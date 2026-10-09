package com.negd.umangwebview.data.model.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.negd.umangwebview.utils.Constants;
import com.negd.umangwebview.utils.DeviceUtils;

import java.io.Serializable;


public class CommonParams implements Serializable {



    protected CommonParams(){

    }

    public static CommonParams commonParams;

    @Expose
    @SerializedName("did")
    public String deviceId;

    @Expose
    @SerializedName("imei")
    public String deviceImei;

    @Expose
    @SerializedName("imsi")
    public String deviceImsi;

    @Expose
    @SerializedName("hmk")
    public String deviceMake;

    @Expose
    @SerializedName("hmd")
    public String deviceModel;

    @Expose
    @SerializedName("os")
    public String deviceOs;

    @Expose
    @SerializedName("osver")
    public String deviceOsVersion;

    @Expose
    @SerializedName("pkg")
    public String appPackage;

    @Expose
    @SerializedName("ver")
    public String appVersion;

    @Expose
    @SerializedName("tkn")
    public String appToken;

    @Expose
    @SerializedName("rot")
    public String deviceRooted;

    @Expose
    @SerializedName("mod")
    public String mode;

    @Expose
    @SerializedName("mcc")
    public String deviceMcc;

    @Expose
    @SerializedName("mnc")
    public String deviceMnc;

    @Expose
    @SerializedName("lac")
    public String deviceLac;

    @Expose
    @SerializedName("clid")
    public String deviceCellId;

    @Expose
    @SerializedName("acc")
    public String deviceAccuracy;

    @Expose
    @SerializedName("lat")
    public String deviceLatitude;

    @Expose
    @SerializedName("lon")
    public String deviceLongitude;

    @Expose
    @SerializedName("peml")
    public String deviceEmail;

    @Expose
    @SerializedName("lang")
    public String appLanguage;

    @Expose
    @SerializedName("aadhr")
    public String userAadhar;

    @Expose
    @SerializedName("node")
    public String node;

    public static CommonParams getInstance(Context context){
        if(commonParams==null) {
            commonParams = new CommonParams();


        }
        return commonParams;
    }

    public void init(Context context) {

        try{
            SharedPreferences pref = context.getSharedPreferences("UmangSdkPref", 0);
            String pref_token= pref.getString(Constants.DEVICE_TOKEN,"");
            Log.d("MyValueIs",pref_token);
            deviceId = DeviceUtils.getDeviceId(context);
            deviceImei=DeviceUtils.getDeviceImei(context);
            deviceImsi=DeviceUtils.getImsiNumber(context);
            deviceMake=DeviceUtils.getDeviceMake();
            deviceModel=DeviceUtils.getDeviceModel();
            deviceOs=DeviceUtils.getMobileOS();
            deviceOsVersion=DeviceUtils.getMobileOSVersion();
//            appPackage=DeviceUtils.getPackageName(context);
//            appPackage="com.negd.umangsdk";
            appPackage="in.gov.umang.negd.g2c";
//            appVersion=DeviceUtils.getAppVersionCode(context);
            appVersion=""+140;
            deviceRooted=DeviceUtils.isRooted();
            deviceMcc=DeviceUtils.getMCC(context);
            deviceMnc=DeviceUtils.getMNC(context);
            deviceLac=DeviceUtils.getLAC(context);
            deviceCellId=DeviceUtils.getCellId(context);
            deviceEmail="";
            mode="app";
            deviceAccuracy="";
            deviceLatitude="";
            deviceLongitude="";
//            appLanguage=getLanguage(context);
             appLanguage="en";
//            appToken= dataManager.getStringPreference(AppPreferencesHelper.PREF_TOKEN,"");
            appToken= pref_token;
//            appToken= Constants.APP_TOKEN;
//            node= dataManager.getStringPreference(AppPreferencesHelper.PREF_NODE,"");
            node= "";
//            userAadhar= dataManager.getEncryptedStringPreference(AppPreferencesHelper.PREF_AADHAAR_NUMBER,"");
            userAadhar= "";
        }catch (Exception ex){

        }
    }

    //get language
//    public String getLanguage(Context context) {
//        String systemLang = context.getResources().getConfiguration().locale.toString();
//        String enLangCode = "en";
//        if (systemLang.contains("GB")) {
//            enLangCode = systemLang.substring(0, systemLang.length() - 3);
//        }
//        return dataManager.getStringPreference(AppPreferencesHelper.PREF_SELECTED_LOCALE, enLangCode);
//    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceImei() {
        return deviceImei;
    }

    public String getDeviceImsi() {
        return deviceImsi;
    }

    public String getDeviceMake() {
        return deviceMake;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getDeviceOs() {
        return deviceOs;
    }

    public String getDeviceOsVersion() {
        return deviceOsVersion;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getAppToken() {
        return appToken;
    }

    public String getDeviceRooted() {
        return deviceRooted;
    }

    public String getMode() {
        return mode;
    }

    public String getDeviceMcc() {
        return deviceMcc;
    }

    public String getDeviceMnc() {
        return deviceMnc;
    }

    public String getDeviceLac() {
        return deviceLac;
    }

    public String getDeviceCellId() {
        return deviceCellId;
    }

    public String getDeviceAccuracy() {
        return deviceAccuracy;
    }

    public String getDeviceLatitude() {
        return deviceLatitude;
    }

    public String getDeviceLongitude() {
        return deviceLongitude;
    }

    public String getDeviceEmail() {
        return deviceEmail;
    }

    public String getAppLanguage() {
        return appLanguage;
    }

    public String getUserAadhar() {
        return userAadhar;
    }

    public String getNode() {
        return node;
    }

    public void setAppLanguage(String appLanguage) {
        this.appLanguage = appLanguage;
    }



    public static CommonParams getCommonParams() {
        return commonParams;
    }

    public static void setCommonParams(CommonParams commonParams) {
        CommonParams.commonParams = commonParams;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceImei(String deviceImei) {
        this.deviceImei = deviceImei;
    }

    public void setDeviceImsi(String deviceImsi) {
        this.deviceImsi = deviceImsi;
    }

    public void setDeviceMake(String deviceMake) {
        this.deviceMake = deviceMake;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public void setDeviceOs(String deviceOs) {
        this.deviceOs = deviceOs;
    }

    public void setDeviceOsVersion(String deviceOsVersion) {
        this.deviceOsVersion = deviceOsVersion;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public void setDeviceRooted(String deviceRooted) {
        this.deviceRooted = deviceRooted;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setDeviceMcc(String deviceMcc) {
        this.deviceMcc = deviceMcc;
    }

    public void setDeviceMnc(String deviceMnc) {
        this.deviceMnc = deviceMnc;
    }

    public void setDeviceLac(String deviceLac) {
        this.deviceLac = deviceLac;
    }

    public void setDeviceCellId(String deviceCellId) {
        this.deviceCellId = deviceCellId;
    }

    public void setDeviceAccuracy(String deviceAccuracy) {
        this.deviceAccuracy = deviceAccuracy;
    }

    public void setDeviceLatitude(String deviceLatitude) {
        this.deviceLatitude = deviceLatitude;
    }

    public void setDeviceLongitude(String deviceLongitude) {
        this.deviceLongitude = deviceLongitude;
    }

    public void setDeviceEmail(String deviceEmail) {
        this.deviceEmail = deviceEmail;
    }

    public void setUserAadhar(String userAadhar) {
        this.userAadhar = userAadhar;
    }

    public void setNode(String node) {
        this.node = node;
    }
}
