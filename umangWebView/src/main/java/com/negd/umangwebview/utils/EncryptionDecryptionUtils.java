package com.negd.umangwebview.utils;

import android.content.Context;
import android.util.Base64;

import androidx.annotation.IntDef;

import com.negd.umangwebview.UmangApplication;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public final class EncryptionDecryptionUtils {

    private String valueToEnc = null;
    private String dValue = null;
    private String eValue = null;
    private byte[] encValue = null;
    private byte[] decordedValue = null;
    private byte[] decValue = null;
    private String valueToDecrypt = null;
    private Key key = null;
    private Cipher c = null;

    private Context context;
    private int encryptionType;

    public static final int NORMAL=0;
    public static final int DIGI=1;
    public static final int CHAT=2;
    public static final int WITH=3;

    @IntDef({NORMAL,DIGI,CHAT,WITH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EncryptionType{

    }

    public EncryptionDecryptionUtils(Context context) {
        this.context = context;
    }

    public void setEncryptionType(@EncryptionType int encryptionType){
        this.encryptionType=encryptionType;
    }

    public String encrypt(String value)  {
        eValue = value;
        try {
            key = generateKey();
            c = Cipher.getInstance(AppConstants.ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);

            for (int i = 0; i < AppConstants.ITERATIONS; i++) {

                if(encryptionType == DIGI)
                    valueToEnc = UmangApplication.d_s + eValue;
                else if(encryptionType == CHAT)
                    valueToEnc = "this" + eValue;
                else
                    valueToEnc = UmangApplication.u_s + eValue;

                encValue = c.doFinal(valueToEnc.getBytes());
                eValue = Base64.encodeToString(encValue, Base64.NO_WRAP);
            }

        } catch (Exception e) {
            eValue = "";

            try {
                AppLogger.e(e.getMessage());
                //MyUtils.sendExceptionDataToBigQuery(context, Log.getStackTraceString(e));
            } catch (Exception e1) {
                AppLogger.e(e1.getMessage());
            }
            try {
                //Crashlytics.logException(e);
                //Crashlytics.log("encrypt u_s:"+MyApplication.u_s+"------value to encrypt:"+value);
            } catch (Exception e1) {
                AppLogger.e(e1.getMessage());
            }
        } finally {
            c = null;
            valueToEnc = null;
            encValue = null;
            key = null;

        }
        return eValue;
    }

    public String decrypt(String value) {
        valueToDecrypt = value;

        try {
            key = generateKey();
            c = Cipher.getInstance(AppConstants.ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);

            for (int i = 0; i < AppConstants.ITERATIONS; i++) {
                decordedValue = Base64.decode(valueToDecrypt, Base64.NO_WRAP);
                decValue = c.doFinal(decordedValue);

                if(encryptionType==DIGI)
                    dValue = new String(decValue).substring(UmangApplication.d_s.length());
                else if(encryptionType==CHAT)
                    dValue = new String(decValue).substring("this".length());
                else
                    dValue = new String(decValue).substring(16);

                valueToDecrypt = dValue;

            }
        } catch (Exception e) {
            dValue = "";
            AppLogger.e(e.getMessage());
            try {
                //MyUtils.sendExceptionDataToBigQuery(context, Log.getStackTraceString(e));
            } catch (Exception e1) {
                AppLogger.e(e1.getMessage());
            }
            try {
                //Crashlytics.logException(e);
                //Crashlytics.log("value to decrypt:"+value);
            } catch (Exception e1) {
                AppLogger.e(e1.getMessage());
            }
        } finally {
            c = null;
            decordedValue = null;
            decValue = null;
            valueToDecrypt = null;
            key = null;
        }
        return dValue;
    }

    private Key generateKey() throws Exception {

        try {
            if(encryptionType ==DIGI)
                key = new SecretKeySpec(UmangApplication.d_k.getBytes(), AppConstants.ALGORITHM);
            else if(encryptionType ==CHAT)
                key = new SecretKeySpec(UmangApplication.c_k.getBytes(), AppConstants.ALGORITHM);
            else
                key = new SecretKeySpec(UmangApplication.u_k.getBytes(), AppConstants.ALGORITHM);

        } catch (Exception e) {
            AppLogger.e(e.getMessage());
            try {
                //MyUtils.sendExceptionDataToBigQuery(context, Log.getStackTraceString(e));
            } catch (Exception e1) {
                AppLogger.e(e1.getMessage());
            }
            try {
                //Crashlytics.logException(e);
                //Crashlytics.log("generateKey u_k : "+MyApplication.u_k);
            } catch (Exception e1) {
                AppLogger.e(e1.getMessage());
            }
        }
        return key;
    }
}
