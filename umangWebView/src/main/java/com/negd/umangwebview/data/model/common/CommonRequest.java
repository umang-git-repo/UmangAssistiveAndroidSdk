package com.negd.umangwebview.data.model.common;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CommonRequest {

    private static CommonRequestMembers commonRequestMembers;

    public static CommonRequestMembers getInstance(){
        if(commonRequestMembers ==null)
            commonRequestMembers=new CommonRequestMembers();

        return commonRequestMembers;
    }

    public static class CommonRequestMembers{
        @Expose
        @SerializedName("did")
        public  String deviceId;

        @Expose
        @SerializedName("imei")
        private  String deviceImei;

        @Expose
        @SerializedName("imsi")
        private  String deviceImsi;

        @Expose
        @SerializedName("hmk")
        private String deviceMake;

        @Expose
        @SerializedName("hmd")
        private String deviceModel;

        @Expose
        @SerializedName("os")
        private  String deviceOs;

        @Expose
        @SerializedName("osver")
        private String deviceOsVersion;

        @Expose
        @SerializedName("pkg")
        private  String appPackage;

        @Expose
        @SerializedName("ver")
        private  String appVersion;

        @Expose
        @SerializedName("tkn")
        private  String appToken;

        @Expose
        @SerializedName("rot")
        private String deviceRooted;

        @Expose
        @SerializedName("mod")
        private String mode;

        @Expose
        @SerializedName("mcc")
        private String deviceMcc;

        @Expose
        @SerializedName("mnc")
        private  String deviceMnc;

        @Expose
        @SerializedName("lac")
        private String deviceLac;

        @Expose
        @SerializedName("clid")
        private  String deviceCellId;

        @Expose
        @SerializedName("acc")
        private  String deviceAccuracy;

        @Expose
        @SerializedName("lat")
        private  String deviceLatitude;

        @Expose
        @SerializedName("lon")
        private  String deviceLongitude;

        @Expose
        @SerializedName("peml")
        private  String deviceEmail;

        @Expose
        @SerializedName("lang")
        private  String appLanguage;

        @Expose
        @SerializedName("aadhr")
        private  String userAadhar;

        @Expose
        @SerializedName("node")
        private  String node;

//
//        public void init(Context context) {
//            this.deviceId = DeviceUtils.getDeviceId(context);
//            this.deviceImei=DeviceUtils.getDeviceImei(context);
//            this.deviceImsi=DeviceUtils.getImsiNumber(context);
//            this.deviceMake=DeviceUtils.getDeviceMake();
//            this.deviceModel=DeviceUtils.getDeviceModel();
//            this.deviceOs=DeviceUtils.getMobileOS();
//            this.deviceOsVersion=DeviceUtils.getMobileOSVersion();
//            this.appPackage=DeviceUtils.getPackageName(context);
//            this.appVersion=DeviceUtils.getAppVersionCode(context);
//            this.deviceRooted=DeviceUtils.isRooted();
//            this.deviceMcc=DeviceUtils.getMCC(context);
//            this.deviceMnc=DeviceUtils.getMNC(context);
//            this.deviceLac=DeviceUtils.getLAC(context);
//            this.deviceCellId=DeviceUtils.getCellId(context);
//            this.deviceEmail=DeviceUtils.getEmail(context);
//            this.mode="app";
//            this.deviceAccuracy="";
//            this.deviceLatitude="";
//            this.deviceLongitude="";
//        }



        public void setAppLanguage(String appLanguage) {
            this.appLanguage = appLanguage;
        }

        public void setUserAadhar(String userAadhar) {
            this.userAadhar = userAadhar;
        }

        public void setNode(String node) {
            this.node = node;
        }

        public void setAppToken(String appToken) {
            this.appToken = appToken;
        }


    }

}
