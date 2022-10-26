package com.negd.umangwebview.callbacks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.negd.umangwebview.ui.UmangWebActivity;
import com.negd.umangwebview.utils.Constants;

public class CameraInterface {

    private UmangWebActivity activity;
    private Uri mCapturedImageURI;

    public CameraInterface(UmangWebActivity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(activity,toast,Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void openAppHome() {
        activity.finish();
    }

    @JavascriptInterface
    public void setPageTitle(final String title) {
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                activity.setPageTitle(title);
//            }
//        });
    }



    @JavascriptInterface
    public String getString(){
        return "How are you?";
    }

    @JavascriptInterface
    public void openChooseFrom(String requestFor, String successCallBack,String failureCallback){
        activity.callBackSuccessFunction = successCallBack;
        activity.callBackFailureFunction  = failureCallback;
        activity.requestImageFor = requestFor;

        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("UmangSdkPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.PREF_REQUEST_IMAGE_FOR,requestFor);
        editor.commit();
        activity.commonInterface.createChooserDialog();
    }

    @JavascriptInterface
    public void openChooseFrom(String requestFor, String successCallBack,String failureCallback,String size){
        activity.callBackSuccessFunction = successCallBack;
        activity.callBackFailureFunction  = failureCallback;
        activity.requestImageFor = requestFor;
        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("UmangSdkPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.PREF_REQUEST_IMAGE_FOR,requestFor);
        editor.commit();
        activity.MaxSize= size;
        activity.commonInterface.createChooserDialog();
    }

}
