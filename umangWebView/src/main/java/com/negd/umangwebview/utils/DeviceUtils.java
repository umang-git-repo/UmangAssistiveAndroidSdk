package com.negd.umangwebview.utils;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Base64;
import android.util.Log;

import androidx.core.content.ContextCompat;
import java.security.MessageDigest;



public final class DeviceUtils {

    private static final String TAG = DeviceUtils.class.getName();

    private DeviceUtils() {

    }



    /**
     * Method to get device id
     * @param context
     * @return- device id
     */
    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Method to get imei number
     * @param context
     * @return- imei number
     */
    @SuppressLint("all")
    public static String getDeviceImei(Context context){
        String temp = "";
        try {
            TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            temp = tManager.getDeviceId();
        } catch (Exception e) {
            temp = "";
            Log.e(TAG, "IMEI ERROR : " + e.getMessage());
        }
        Log.i(TAG, "IMEI : " + temp);
        return temp;
    }

    /**
     * Method to get imsi number
     * @param context- context
     * @return - imsi number
     */
    @SuppressLint("all")
    public static String getImsiNumber(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);


        String imsi = "";
        try {
            imsi = manager.getSubscriberId().toString();
        } catch (Exception e) {
            Log.i(TAG, "Ex in getImsiNumber...: " + e.getMessage());
            imsi = "";
        }
        if (imsi == null)
            return "";
        else
            return imsi;
    }

    /**
     * Method to get device maker name
     * @return - name of device maker
     */
    public static String getDeviceMake() {
        try {
            return "" + android.os.Build.MANUFACTURER;
        } catch (Exception e) {
            Log.e(TAG, "Error device maker :"+ e.getMessage() );
            return "";
        }
    }
    /**
     * Method to get device model
     * @return- device model
     */
    public static String getDeviceModel() {
        try {
            return "" + android.os.Build.MODEL;
        } catch (Exception e) {
            Log.e(TAG, "Error device model :"+ e.getMessage() );
            return "";
        }
    }

    /**
     * Method to get Android OS
     * @return- OS
     */
    public static String getMobileOS() {
        try {
            return "Android " + android.os.Build.VERSION.RELEASE;
        } catch (Exception e) {
            Log.e(TAG, "Error device OS Name :"+ e.getMessage() );
            return "";
        }
    }

    /**
     * Method to get Android OS Version
     * @return- version
     */
    public static String getMobileOSVersion() {
        try {
            return android.os.Build.VERSION.RELEASE;
        } catch (Exception e) {
            Log.e(TAG, "Error device OS Version :"+ e.getMessage() );
            return "";
        }
    }

    /**
     * Method to get package name
     * @param context
     * @return- package name
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * Method to get App version code
     * @param context
     * @return - Version code
     */
    public static String getAppVersionCode(Context context) {
        PackageInfo pInfo;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return "" + pInfo.versionCode;
        } catch (Exception e) {
            Log.e(TAG, "Error getting App Version Code:"+ e.getMessage() );
        }
        return "";
    }
    public static String isRooted() {
        if (RootUtils.isDeviceRooted()) {
            return "yes";
        } else {
            return "no";
        }
    }

    /**
     * Method to get MCC
     * @param context
     * @return
     */
    public static String getMCC(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String networkOperator = tel.getNetworkOperator();
            if (networkOperator != null) {
                if (!networkOperator.equalsIgnoreCase("")) {
                    String mcc = networkOperator.substring(0, 3);
                    return mcc;
                } else
                    return "";
            } else
                return "";

        } catch (Exception e) {
            Log.e(TAG, "Error getting MCC:"+ e.getMessage() );
            return "";
        }
    }

    /**
     * Method to get MNC
     * @param context
     * @return
     */
    public static String getMNC(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String networkOperator = tel.getNetworkOperator();
            if (networkOperator != null) {
                if (!networkOperator.equalsIgnoreCase("")) {
                    String mnc = networkOperator.substring(3);
                    return mnc;
                } else
                    return "";
            } else
                return "";


        } catch (Exception e) {
            Log.e(TAG, "Error getting MNC:"+ e.getMessage() );
            return "";
        }
    }

    /**
     * Method to get LAC
     * @param mContext
     * @return
     */
    @SuppressLint("all")
    public static String getLAC(Context mContext) {
        String lac = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
            lac = String.valueOf(cellLocation.getLac());
        } catch (Exception e) {
            Log.e(TAG, "Error getting LAC:"+ e.getMessage() );
            lac = "";
        }
        return lac;
    }


    /**
     * Method to get Cell Id
     * @param context
     * @return
     */
    @SuppressLint("all")
    public static String getCellId(Context context) {
        try {
            final TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                final GsmCellLocation location = (GsmCellLocation) telephony.getCellLocation();
                if (location != null) {
                    String cellId = "" + location.getCid();
                    return cellId;
                } else {
                    return "";
                }
            } else
                return "";
        } catch (Exception e) {
            Log.e(TAG, "Error getting CELL Id:"+ e.getMessage());
            return "";
        }
    }







    /**
     * Method to get key hash
     * @param context
     * @return
     */
    public static String getHashKey(Context context) {
        String keyhashStr = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES); //Your package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyhashStr = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d(TAG, "KEY HASH:>>" + keyhashStr );
                return keyhashStr.trim();

            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting Key Hash:"+ e.getMessage() );
        }
        return "";
    }

    /**
     * Method to get operator name
     * @param context
     * @return
     */
    public static String getOperatorName(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        return carrierName;
    }







    public static boolean containsLink(String input) {
        boolean result = false;

        String[] parts = input.split("\\s+");

        for (String item : parts) {
            if (android.util.Patterns.WEB_URL.matcher(item).matches()) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static String getAppVersionName(Context context) {
        PackageInfo pInfo;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return "" + pInfo.versionName;
        } catch (Exception e) {
            //LogUtil.printStackTrace(e);
        }
        return "";

    }



}
