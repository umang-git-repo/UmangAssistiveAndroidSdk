package com.negd.umangwebview.callbacks;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.negd.umangwebview.ui.UmangWebActivity;
import com.negd.umangwebview.utils.Constants;

public class MHAInterface {

    private UmangWebActivity activity;

    public MHAInterface(UmangWebActivity activity) {
        this.activity = activity;
    }

    /**
     * @param successCallback Callback for success  return result
     * @param failureCallback Callback for failure  return result
     * @param maxSize  maximum size require for File
     *
     *  Exception result code :- FILE_SIZE_EXCEEDED , FILE_NOT_FOUND, IO_EXCEPTION
     *
     * method api :- stg:-  version code - 48    1.0.27
     *                 pro:- version code- 28    1.2.3
     *
     */
    @JavascriptInterface
    public void openFileChooser(String successCallback,String failureCallback,String maxSize){
        activity.callBackSuccessFunction=successCallback;
        activity.callBackFailureFunction=failureCallback;
        activity.MaxSize=maxSize;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        activity.startActivityForResult(i, Constants.INTENT_MHA_FILE);
    }

    /**
     * @param successCallback Callback for success  return result
     * @param failureCallback Callback for failure  return result
     * @param maxSize  maximum size require for File
     *
     *  Exception result code :- FILE_SIZE_EXCEEDED , FILE_NOT_FOUND, IO_EXCEPTION
     *
     * method api :- stg:-  version code - 48    1.0.27
     *                 pro:- version code- 28    1.2.3
     *
     */
    @JavascriptInterface
    public void openFileChooserWihFileName(String successCallback,String failureCallback,String maxSize){
        activity.callBackSuccessFunction=successCallback;
        activity.callBackFailureFunction=failureCallback;
        activity.MaxSize=maxSize;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        activity.startActivityForResult(i, Constants.INTENT_MHA_FILE_WITH_NAME);
    }
}
