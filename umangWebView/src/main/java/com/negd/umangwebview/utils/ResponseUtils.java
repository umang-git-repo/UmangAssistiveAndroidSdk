package com.negd.umangwebview.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

public final class ResponseUtils {

    public static <T> T getResponseClass(String response, Class<T> type, Context context) throws Exception {
        Gson gson=new Gson();
        EncryptionDecryptionAuthUtils decryptionUtils=new EncryptionDecryptionAuthUtils(context);
        String  decrptResponse=decryptionUtils.decrypt(response);
        return gson.fromJson(decrptResponse,type);
    }

    public static <T> T getNormalResponseClass(String response, Class<T> type, Context context, int enType)  {
        Gson gson=new Gson();
        EncryptionDecryptionUtils decryptionUtils=new EncryptionDecryptionUtils(context);
        if(enType==0)
            decryptionUtils.setEncryptionType(EncryptionDecryptionUtils.NORMAL);
        else if(enType==1)
            decryptionUtils.setEncryptionType(EncryptionDecryptionUtils.DIGI);
        else if(enType==2)
            decryptionUtils.setEncryptionType(EncryptionDecryptionUtils.CHAT);

        String  decrptResponse=decryptionUtils.decrypt(response);
        Log.d("responselanguageSelect", decrptResponse);
        return gson.fromJson(decrptResponse,type);
    }

    public static String encrypt(String value,Context context){
        EncryptionDecryptionUtils decryptionUtils=new EncryptionDecryptionUtils(context);
        decryptionUtils.setEncryptionType(EncryptionDecryptionUtils.DIGI);
        return decryptionUtils.encrypt(value);
    }

    public static String decrypt(String value,Context context){
        EncryptionDecryptionUtils decryptionUtils=new EncryptionDecryptionUtils(context);
        decryptionUtils.setEncryptionType(EncryptionDecryptionUtils.DIGI);
        return decryptionUtils.decrypt(value);
    }

}
