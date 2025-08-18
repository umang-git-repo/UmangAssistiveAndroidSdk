package com.negd.umangwebview;

import static com.negd.umangwebview.utils.Constants.DEVICE_TKN_RESPONSE;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.webkit.WebStorage;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.google.gson.Gson;
import com.negd.umangwebview.data.AppSharedPreferences;
import com.negd.umangwebview.data.api.APIClient;
import com.negd.umangwebview.data.api.APIInterface;
import com.negd.umangwebview.data.model.biomodel.RdDeviceRequest;
import com.negd.umangwebview.ui.UmangWebActivity;
import com.negd.umangwebview.ui.jeevan_pramaan.EncryptionDecryptionHelper;
import com.negd.umangwebview.utils.Constants;
import com.negd.umangwebview.utils.DeviceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UmangAssistiveAndroidSdk {

   private final String deptUrl;
   private final String deptHeader;
   private final boolean enableDeptHeader;
   private final String deptId;
   private final String serviceId;
   private final String backButtonColor;
   private final String headerTextColor;
   private final String headerColor;
   private final String loaderColor;
   private final String swipeLoaderColor;
   private final String nssoPayload;
   private final String nssoJwtToken;
   private final String loggedInUserNumber;
   private final SdkServicesViewType sdkServicesViewType;
   private final int customHeaderLayoutId;
   private final int customHeaderClickViewId;
   private final boolean customHeaderSdkCloseOnCick;
   private final int customFooterLayoutId;
   private final boolean customFooterSdkCloseOnCick;
   private final int customFooterClickViewId;
   public static boolean openingIntent=false;
   public static IUmangAssistiveListener assistiveListener;

   public enum SdkServicesViewType {
      TOP("top"),
      ALL("all");

      private final String label;

      SdkServicesViewType(String label) {
         this.label = label;
      }

      public String getLabel() {
         return label;
      }
   }

   public UmangAssistiveAndroidSdk(Builder builder)
   {
      this.deptUrl=builder.deptUrl;
      this.deptHeader=builder.deptHeader;
      this.enableDeptHeader=builder.enableDeptHeader;
      this.deptId=builder.deptId;
      this.serviceId=builder.serviceId;
      this.backButtonColor=builder.backButtonColor;
      this.headerTextColor=builder.headerTextColor;
      this.headerColor=builder.headerColor;
      this.loaderColor=builder.loaderColor;
      this.swipeLoaderColor=builder.swipeLoaderColor;
      this.nssoPayload=builder.nssoPayload;
      this.customHeaderLayoutId = builder.customHeaderLayoutId;
      this.customHeaderClickViewId = builder.customHeaderClickViewId;
      this.customHeaderSdkCloseOnCick = builder.customHeaderSdkCloseOnClick;
      this.customFooterLayoutId = builder.customFooterLayoutId;
      this.customFooterClickViewId = builder.customFooterClickViewId;
      this.customFooterSdkCloseOnCick = builder.customFooterSdkCloseOnClick;
      this.nssoJwtToken = builder.nssoJwtToken;
      this.loggedInUserNumber = builder.loggedInUserNumber;
      this.sdkServicesViewType = builder.sdkServicesViewType;
      UmangAssistiveAndroidSdk.assistiveListener =  builder.umangAssistiveListener;
   }

   public void startUmangWebview(Context context){

      try {
         Intent intent = new Intent(context, UmangWebActivity.class);

         if(deptUrl!=null && deptUrl.trim().length()>0){
            intent.putExtra(Constants.DEPT_URL,deptUrl);
         }else{
            Log.e("Dept Url Error","Department url not set");
            return;
         }


         if(deptId!=null && deptId.trim().length()>0){
            intent.putExtra(Constants.DEPT_ID,deptId);
         }


         if(deptHeader!=null && deptHeader.trim().length()>0){
            intent.putExtra(Constants.DEPT_NAME,deptHeader);
         }
         intent.putExtra(Constants.ENABLE_DEPT_HEADER, enableDeptHeader);
         if(backButtonColor!=null && backButtonColor.trim().length()>0){
            intent.putExtra(Constants.BACK_BUTTON_COLOR,backButtonColor);
         }

         if(headerTextColor!=null && headerTextColor.trim().length()>0){
            intent.putExtra(Constants.HEADER_TEXT_COLOR,headerTextColor);
         }

         if(headerColor!=null && headerColor.trim().length()>0){
            intent.putExtra(Constants.HEADER_COLOR,headerColor);
         }

         if(loaderColor!=null && loaderColor.trim().length()>0){
            intent.putExtra(Constants.LOADER_COLOR,loaderColor);
         }
         if(nssoPayload!=null && !nssoPayload.trim().isEmpty()){
            intent.putExtra(Constants.NSSO_PAYLOAD,nssoPayload);
         }
         if(nssoJwtToken!=null && !nssoJwtToken.trim().isEmpty()){
            intent.putExtra(Constants.NSSO_JWT_TOKEN,nssoJwtToken);
         }
         if(customHeaderLayoutId != 0){
            intent.putExtra(Constants.CUSTOM_HEADER_LAYOUT_ID, customHeaderLayoutId);
            intent.putExtra(Constants.CUSTOM_HEADER_CLOSE_SDK_ON_CLICK, customHeaderSdkCloseOnCick);
         }
         if(customHeaderClickViewId != 0){
            intent.putExtra(Constants.CUSTOM_HEADER_VIEW_CLICK_ID, customHeaderClickViewId);
         }
         if(customFooterLayoutId != 0){
            intent.putExtra(Constants.CUSTOM_FOOTER_LAYOUT_ID, customFooterLayoutId);
            intent.putExtra(Constants.CUSTOM_FOOTER_CLOSE_SDK_ON_CLICK, customFooterSdkCloseOnCick);
         }
         if(customFooterClickViewId != 0){
            intent.putExtra(Constants.CUSTOM_FOOTER_VIEW_CLICK_ID, customFooterClickViewId);
         }
         if(loggedInUserNumber!= null && !loggedInUserNumber.trim().isEmpty()){
            intent.putExtra(Constants.SDK_LOGGED_IN_USER_MOBILE_NUMBER, loggedInUserNumber);
         }
         if(sdkServicesViewType!= null){
            intent.putExtra(Constants.SDK_SERVICES_VIEW_TYPE, sdkServicesViewType.getLabel());
         }
         context.startActivity(intent);

      }catch (Exception ex){

      }
   }

   public void logoutUser(Context context){
      try {
         logoutAndClear(context);
      } catch (Exception ex){

      }
   }

   public static class Builder{
      private String deptUrl;
      private String deptHeader;
      private boolean enableDeptHeader;
      private String deptId;
      private String serviceId;
      private String backButtonColor;
      private String headerTextColor;
      private String headerColor;
      private String loaderColor;
      private String swipeLoaderColor;
      private String nssoPayload;
      private String nssoJwtToken;
      private String loggedInUserNumber;
      private SdkServicesViewType sdkServicesViewType;
      private int customHeaderLayoutId;
      private int customHeaderClickViewId;
      private boolean customHeaderSdkCloseOnClick;
      private int customFooterLayoutId;
      private int customFooterClickViewId;
      private boolean customFooterSdkCloseOnClick;
      private IUmangAssistiveListener umangAssistiveListener;

      public static Builder newInstance(){
         return new Builder();
      }

      private Builder() {}

      public Builder setDeptUrl(String deptUrl) {
         this.deptUrl = deptUrl;
         return this;
      }

      public Builder setDeptHeader(String deptHeader) {
         this.deptHeader = deptHeader;
         return this;
      }
      public Builder enableDeptHeader(boolean enableDeptHeader) {
         this.enableDeptHeader = enableDeptHeader;
         return this;
      }

      public Builder setDeptId(String deptId) {
         this.deptId = deptId;
         return this;
      }

      public Builder setServiceId(String serviceId) {
         this.serviceId = serviceId;
         return this;
      }

      public Builder setBackButtonColor(String backButtonColor) {
         this.backButtonColor = backButtonColor;
         return this;
      }

      public Builder setHeaderTextColor(String headerTextColor) {
         this.headerTextColor = headerTextColor;
         return this;
      }

      public Builder setHeaderColor(String headerColor) {
         this.headerColor = headerColor;
         return this;
      }

      public Builder setLoaderColor(String loaderColor) {
         this.loaderColor = loaderColor;
         return this;
      }

      public Builder setSwipeLoaderColor(String swipeLoaderColor) {
         this.swipeLoaderColor = swipeLoaderColor;
         return this;
      }

      public Builder setNssoPayload(String nssoPayload) {
         this.nssoPayload = nssoPayload;
         return this;
      }

      public Builder setNssoJwtToken(String nssoJwtToken) {
         this.nssoJwtToken = nssoJwtToken;
         return this;
      }

      public Builder setCustomHeaderLayoutId(@LayoutRes int headerLayoutId) {
         this.customHeaderLayoutId = headerLayoutId;
         return this;
      }
      public Builder setCustomHeaderClickViewId(@IdRes int headerClickViewId) {
         this.customHeaderClickViewId = headerClickViewId;
         return this;
      }
      public Builder closeSdkOnCustomHeaderClick(boolean closeSdk) {
         this.customHeaderSdkCloseOnClick = closeSdk;
         return this;
      }

      public Builder setCustomFooterLayoutId(@LayoutRes int footerLayoutId) {
         this.customFooterLayoutId = footerLayoutId;
         return this;
      }

      public Builder setCustomFooterClickViewId(@IdRes int footerClickViewId) {
         this.customFooterClickViewId = footerClickViewId;
         return this;
      }
      public Builder closeSdkOnCustomFooterClick(boolean closeSdk) {
         this.customFooterSdkCloseOnClick = closeSdk;
         return this;
      }

      public Builder setAssistiveListener(IUmangAssistiveListener assistiveListener) {
         this.umangAssistiveListener = assistiveListener;
         return this;
      }
      public Builder setLoggedInMobileNumber(String loggedInUserNumber) {
         this.loggedInUserNumber = loggedInUserNumber;
         return this;
      }
      public Builder setServiceViewType(SdkServicesViewType sdkServicesViewType) {
         this.sdkServicesViewType = sdkServicesViewType;
         return this;
      }
      public UmangAssistiveAndroidSdk build(){
         return new UmangAssistiveAndroidSdk(this);
      }



   }
   private void logoutAndClear(Context context) {
      // Clearing WebStorage
      WebStorage.getInstance().deleteAllData();
      EncryptionDecryptionHelper encryptionDecryptionHelper = new EncryptionDecryptionHelper();
      AppSharedPreferences appSharedPreferences = null;
      try {
         appSharedPreferences = AppSharedPreferences.getInstance(context);
         String token = appSharedPreferences.getStringPreference(DEVICE_TKN_RESPONSE, "");
         // Clearing AppSharedPreferences
         appSharedPreferences.deleteAllPreference();
         // Clearing user session by logging out user
         RdDeviceRequest request = new RdDeviceRequest();
         request.setLang("en");
         request.setVer("160");
         request.setAcc("");
         request.setClid(DeviceUtils.getCellId(context));
         request.setPeml("");
         request.setDid(DeviceUtils.getDeviceId(context));
         request.setImei("");
         request.setLac(DeviceUtils.getLAC(context));
         request.setLat("");
         request.setLon("");
         request.setHmk(DeviceUtils.getDeviceMake());
         request.setMcc(DeviceUtils.getMCC(context));
         request.setMnc(DeviceUtils.getMNC(context));
         request.setHmd(DeviceUtils.getDeviceModel());
         request.setOs(DeviceUtils.getMobileOS());
         request.setRot("no");
         request.setMod("app");
         request.setDeviceOsVersion(DeviceUtils.getMobileOSVersion());
         request.setDeviceImsi("");
         request.setUserAadhar("");
         request.setNode("");
         request.setAppPackage("in.gov.umang.negd.g2c");
          request.setTkn(token);
         String requestString = new Gson().toJson(request);
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
               APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
               Call<String> call = apiInterface.logoutUser(
                       encryptionDecryptionHelper.getMD5(requestString),
                       encryptionDecryptionHelper.encryptAes(requestString));
               call.enqueue(new Callback<String>() {
                  @Override
                  public void onResponse(Call<String> call, Response<String> response) {
                  }
                  @Override
                  public void onFailure(Call<String> call, Throwable t) {
                  }
               });
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

   }
}
