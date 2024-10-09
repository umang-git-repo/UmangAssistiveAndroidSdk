package com.negd.umangwebview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.negd.umangwebview.ui.UmangWebActivity;
import com.negd.umangwebview.utils.Constants;

public class UmangAssistiveAndroidSdk {

   private final String deptUrl;
   private final String deptHeader;
   private final String deptId;
   private final String serviceId;
   private final String backButtonColor;
   private final String headerTextColor;
   private final String headerColor;
   private final String loaderColor;
   private final String swipeLoaderColor;
   private final String nssoPayload;
   private final int customHeaderLayoutId;
   private final int customHeaderClickViewId;
   private final boolean customHeaderSdkCloseOnCick;
   private final int customFooterLayoutId;
   private final boolean customFooterSdkCloseOnCick;
   private final int customFooterClickViewId;
   public static boolean openingIntent=false;
   public static IUmangAssistiveListener assistiveListener;


   public UmangAssistiveAndroidSdk(Builder builder)
   {
      this.deptUrl=builder.deptUrl;
      this.deptHeader=builder.deptHeader;
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
         context.startActivity(intent);

      }catch (Exception ex){

      }
   }

   public static class Builder{
      private String deptUrl;
      private String deptHeader;
      private String deptId;
      private String serviceId;
      private String backButtonColor;
      private String headerTextColor;
      private String headerColor;
      private String loaderColor;
      private String swipeLoaderColor;
      private String nssoPayload;
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

      public UmangAssistiveAndroidSdk build(){
         return new UmangAssistiveAndroidSdk(this);
      }



   }
}
