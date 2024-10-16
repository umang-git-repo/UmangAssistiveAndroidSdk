package com.negd.umangwebview.listeners;

import android.content.Intent;
import android.webkit.ValueCallback;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

public interface ICommonCallbackListener {
    String getUserInfo();

    String getNssoPayload();

    String getSharedPreferencesValue(String key, String defaultValue);

    void setSharedPreferencesValue(String key, String value);

    AppCompatActivity getContext();

    void showToastMessage(String message);

    void showToastMessage(@StringRes int messageId);

    void showInfoPopup(String message, int stringResId, Boolean forceBack);

    // Redirect user to a specified Play Store URL
    void redirectToPlayStore(String url);

    void startActivityForResultWithLauncher(Intent intent, ActivityResultLauncher<Intent> launcher);

    // Set the JavaScript (JP) callbacks for success and failure
    void setCallbackFunctions(String successCallback, String failureCallback);

    String getSuccessCallbackFunction();

    String getFailureCallbackFunction();

    void evaluateJavascriptInterface(String script, ValueCallback<String> resultCallback);

    void loadWebUrl(String url);

    void shareBase64(String base64, String shareText);

    void showDepartmentHeader(String headerName);


}
