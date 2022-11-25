package com.negd.umangwebview.utils;

import android.content.Context;
import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public final class EncryptionDecryptionAuthUtils {

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

    public EncryptionDecryptionAuthUtils(Context context) {
        this.context = context;
    }

    public String encrypt(String value)  {

        eValue = value;

        try {
            key = generateKey();
            c = Cipher.getInstance(AppConstants.ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);

            for (int i = 0; i < AppConstants.ITERATIONS; i++) {
                valueToEnc = DeviceUtils.getHashKey(context) + eValue;
                encValue = c.doFinal(valueToEnc.getBytes());
                eValue = Base64.encodeToString(encValue, Base64.NO_WRAP);
            }

        } catch (Exception e) {
            eValue = "";
            AppLogger.e(e.getMessage());
            try {
                //MyUtils.sendExceptionDataToBigQuery(context, Log.getStackTraceString(e));
            } catch (Exception e1) {
                AppLogger.e(e1.getMessage());
            }
            try {
                //Crashlytics.logException(e);
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


    public String encrypt(String keyString, String value) throws Exception {

        eValue = value;

        try {
            Key keySpec = generateKey(keyString);
            c = Cipher.getInstance(AppConstants.ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, keySpec);

            for (int i = 0; i < AppConstants.ITERATIONS; i++) {
                valueToEnc = DeviceUtils.getHashKey(context) + eValue;
                encValue = c.doFinal(valueToEnc.getBytes());
                eValue = Base64.encodeToString(encValue, Base64.NO_WRAP);
            }

        } catch (Exception e) {
            eValue = "";
            AppLogger.e(e.getMessage());
            try {
                //MyUtils.sendExceptionDataToBigQuery(context, Log.getStackTraceString(e));
            } catch (Exception e1) {
                AppLogger.e(e1.getMessage());
            }
            try {
                //Crashlytics.logException(e);
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

    public String decrypt(String value) throws Exception {

        valueToDecrypt = value;

        try {
            key = generateKey();
            c = Cipher.getInstance(AppConstants.ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);

            for (int i = 0; i < AppConstants.ITERATIONS; i++) {
//                decordedValue = DatatypeConverter.parseBase64Binary(valueToDecrypt);
                decordedValue = Base64.decode(valueToDecrypt, Base64.NO_WRAP);
                decValue = c.doFinal(decordedValue);
                dValue = new String(decValue).substring(DeviceUtils.getHashKey(context).length());
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


    public String decrypt(String keyString, String value) throws Exception {

        valueToDecrypt = value;

        try {
            Key keySpec = generateKey(keyString);
            c = Cipher.getInstance(AppConstants.ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, keySpec);

            for (int i = 0; i < AppConstants.ITERATIONS; i++) {
//                decordedValue = DatatypeConverter.parseBase64Binary(valueToDecrypt);
                decordedValue = Base64.decode(valueToDecrypt, Base64.NO_WRAP);
                decValue = c.doFinal(decordedValue);
                dValue = new String(decValue).substring(DeviceUtils.getHashKey(context).length());
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
            key = new SecretKeySpec("ptxPH4oyaFIm40nCre+GPuG2/q8\\u003d".substring(0, 16).getBytes(), AppConstants.ALGORITHM);

        } catch (Exception e) {
            AppLogger.e(e.getMessage());
            try {
                //MyUtils.sendExceptionDataToBigQuery(context, Log.getStackTraceString(e));
            } catch (Exception e1) {
                AppLogger.e(e1.getMessage());
            }
            try {
                //Crashlytics.logException(e);
            } catch (Exception e1) {
                AppLogger.e(e1.getMessage());
            }
        }
        return key;
    }

    private Key generateKey(String keyString) throws Exception {

        try {
            SecretKeySpec keyGen = new SecretKeySpec(keyString.getBytes(), AppConstants.ALGORITHM);
            return keyGen;
        } catch (Exception e) {
            AppLogger.e(e.getMessage());
            try {
                //MyUtils.sendExceptionDataToBigQuery(context, Log.getStackTraceString(e));
            } catch (Exception e1) {
                AppLogger.e(e1.getMessage());
            }
            try {
                //Crashlytics.logException(e);
            } catch (Exception e1) {
                AppLogger.e(e1.getMessage());
            }
        }
        return null;
    }
}
