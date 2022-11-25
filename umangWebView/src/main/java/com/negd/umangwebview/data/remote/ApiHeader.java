package com.negd.umangwebview.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.negd.umangwebview.BuildConfig;
import com.negd.umangwebview.utils.CommonUtils;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class ApiHeader {

    private ProtectedApiHeader mProtectedApiHeader;
    private PublicApiHeader mPublicApiHeader;

    @Inject
    public ApiHeader(ProtectedApiHeader mProtectedApiHeader,PublicApiHeader mPublicApiHeader){
        this.mProtectedApiHeader=mProtectedApiHeader;
        this.mPublicApiHeader=mPublicApiHeader;
    }

    public ProtectedApiHeader getProtectedApiHeader(){ return mProtectedApiHeader;}

    public PublicApiHeader getPublicApiHeader(){ return  mPublicApiHeader;}

    public static final class ProtectedApiHeader{

        @Expose
        @SerializedName("Content-Type")
        private String mContentType="text/plain";

        @Expose
        @SerializedName("Authorization")
        private String mAuthorization;

        @Expose
        @SerializedName("X-REQUEST-CONTROL")
        private String mRequestControl;

        @Expose
        @SerializedName("X-REQUEST-TSTAMP")
        private String mRequestTimeStamp = CommonUtils.getTimeStamp();

        @Expose
        @SerializedName("X-REQUEST-VALUE")
        private String mRequestValue;

        @Expose
        @SerializedName("X-REQUEST-UV")
        private String mRequestUv = CommonUtils.getUniqueNumber();

        @Expose
        @SerializedName("User-Agent")
        private String mUserAgent = System.getProperty("http.agent");


        @Inject
        public ProtectedApiHeader(String bearer) {
            mAuthorization = BuildConfig.WSO2_BEARER;
        }

        public void setAuthHeader(String header){
            mAuthorization=header;
        }

        public void setXRequestValue(String rv){
            mRequestValue=rv;
        }

        public void setXRequestControl(String rc){
            mRequestControl=rc;
        }
    }

    public static final class PublicApiHeader{

        @Expose
        @SerializedName("Content-Type")
        private String mContentType="text/plain";

        @Expose
        @SerializedName("K-TYPE")
        private String mBuildType = BuildConfig.DEBUG ? "d" : "r";

        @Expose
        @SerializedName("Authorization")
        private String mAuthorization = BuildConfig.WSO2_BEARER;

        @Expose
        @SerializedName("User-Agent")
        private String mUserAgent = System.getProperty("http.agent");

        @Expose
        @SerializedName("X-REQUEST-UV")
        private String mRequestUv = CommonUtils.getUniqueNumber();

        @Expose
        @SerializedName("x-api-key")
        private String apiKey = "VKE9PnbY5k1ZYapR5PyYQ33I26sXTX569Ed7eqyg";

        @Inject
        public PublicApiHeader() {

        }

    }
}
