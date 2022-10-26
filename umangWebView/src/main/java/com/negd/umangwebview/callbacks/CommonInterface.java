package com.negd.umangwebview.callbacks;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.negd.umangwebview.R;
import com.negd.umangwebview.UmangAssistiveAndroidSdk;
import com.negd.umangwebview.ui.BarCodeScannerActivity;
import com.negd.umangwebview.ui.DatePickerFragmentDepartment;
import com.negd.umangwebview.ui.OnDatePicker;
import com.negd.umangwebview.ui.UmangWebActivity;
import com.negd.umangwebview.utils.Constants;
import com.negd.umangwebview.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CommonInterface implements OnDatePicker {

    private UmangWebActivity act;


    public CommonInterface(UmangWebActivity activity) {
        this.act = activity;
    }

    /**
     * This method is called to finish the current activity
     */
    @JavascriptInterface
    public void finishActivity() {
        act.finish();
    }


    /**
     * Method to open URL in external browser.
     *
     * @param url :- URL to open in external browser
     */
    @JavascriptInterface
    public void openBrowser(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        act.startActivity(i);
    }

    /**
     * This method will open new inapp browser screen with given title and url.
     *
     * @param url   :- URI to open in browser screen
     * @param title :- title for the screen
     */
    @JavascriptInterface
    public void openWebView(String url, String title) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        act.startActivity(i);
    }

    /**
     * Method to invoke BrowserScreenFeedback screen in app.
     *
     * @param url     :- URL to open in browser screen feedback screen
     * @param title:- Title for the screen.
     */
    @JavascriptInterface
    public void openWebViewFeedback(String url, String title) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        act.startActivity(i);
    }

    /**
     * To check if any USB device is connected to phone with OTG.
     * <p>
     * <br />
     *
     * @return {"is_device_connected":"true"}   / {"is_device_connected":"false"}
     */
    @JavascriptInterface
    public String checkDeviceConnected() {
        UsbManager manager = (UsbManager) act.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        if (deviceList.size() > 0) {
            return "{\"is_device_connected\":\"true\"}";
        }

        return "{\"is_device_connected\":\"false\"}";
    }

    public static boolean jp_startScanning;
    public static String jp_successCallback;
    public static String jp_failureCallback;

    /**
     * @param startScanning
     * @param successCallback
     * @param failureCallback
     */
    @JavascriptInterface
    public void showDeviceChooserRD(boolean startScanning, String successCallback, String failureCallback) {

        jp_startScanning = startScanning;
        jp_successCallback = successCallback;
        jp_failureCallback = failureCallback;

        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_write_storage_permission_help_text));
                final String type="JEEVAN_PRAMAAN_PERMISSION";

            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_MORPHO_RD);
            }
        } else {

            //TODO need to implement nativescreen
            //((UmangWebActivity) act).openBiometricDeviceScreen(successCallback, failureCallback);
        }
    }


    @JavascriptInterface
    public void startScannerRD(String deviceType) {

        //TODO need to implement nativescreen
        //((UmangWebActivity) act).showScannerPopupRD(deviceType);
    }

    public static String nps_fileData;
    public static String nps_fileName;
    public static String nps_file_url;
    public static byte[] nps_fileData_bytes;


    /**
     * Method to save file from given Base64 data.
     *
     * @param fileData Base64 data
     * @param fileName File name for saving Base64.
     */
    @JavascriptInterface
    public void downloadBase64File(String fileData, String fileName) {

        nps_fileData = fileData;
        nps_fileName = fileName;

        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_write_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                //   ((CommonWebViewActivity)act).openDialog("",act.getResources().getString(R.string.allow_write_storage_permission_help_text),"OK","CANCEL",type);

            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_NPS_FILE_SAVE);
            }
        } else {
            ((UmangWebActivity) act).downloadBase64File(fileName, fileData.split(",")[1]);

        }
    }


    /**
     * Method to save PDF file from given Base64 data and open intent for viewing PDF file.
     *
     * @param fileData :- Base64 data.
     * @param fileName :- file name to save PDF file.
     */
    @JavascriptInterface
    public void downloadBase64FileForPDF(String fileData, String fileName) {

        nps_fileData = fileData;
        nps_fileName = fileName;

        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_write_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                // ((CommonWebViewActivity)act).openDialog("",act.getResources().getString(R.string.allow_write_storage_permission_help_text),"OK","CANCEL",type);

            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PDF_DOC);
            }
        } else {

            try {
                ((UmangWebActivity) act).downloadBase64FileForPDF(fileName, fileData.split(",")[1], "pdf");
            } catch (Exception e) {
            }
        }
    }

    /**
     * Method to save Excel file from given Base64 data and open intent for viewing Excel file.
     *
     * @param fileData :- Base64 data.
     * @param fileName :- file name to save Excel file.
     */

    @JavascriptInterface
    public void downloadBase64FileForExcel(String fileData, String fileName) {

        nps_fileData = fileData;
        nps_fileName = fileName;

        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_write_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                //((CommonWebViewActivity)act).openDialog("",act.getResources().getString(R.string.allow_write_storage_permission_help_text),"OK","CANCEL",type);

            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_EXCEL_DOC);
            }
        } else {

            try {
                ((UmangWebActivity) act).downloadBase64FileForPDF(fileName, fileData.split(",")[1], "excel");
            } catch (Exception e) {
            }
        }
    }


    @JavascriptInterface
    public void downloadFileBytesForPDF(String filename, byte[] fileBytes) {

        nps_fileData_bytes = fileBytes;
        nps_fileName = filename;

        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_write_storage_permission_help_text));
            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //show dialog
                final String type="WRITE_PERMISSION";
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_write_storage_permission_help_text));
                // ((CommonWebViewActivity)act).openDialog("",act.getResources().getString(R.string.allow_write_storage_permission_help_text),"OK","CANCEL",type);
            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PDF_DOC_BYTES);
            }
        } else {
            ((UmangWebActivity) act).downloadFileBytesPDF(filename, fileBytes);
        }
    }

    @JavascriptInterface
    public void forceLogoutUser() {
        Toast.makeText(act.getApplicationContext(),"You are logged out",Toast.LENGTH_LONG).show();
        act.finish();
    }

    @JavascriptInterface
    public void saveWebKeyValue(String jsonStr) {

        SharedPreferences pref = act.getApplicationContext().getSharedPreferences("UmangSdkPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.PREF_WEB_KEY_VALUE,jsonStr);
        editor.commit();
    }

    @JavascriptInterface
    public String getWebKeyValue() {

        SharedPreferences pref = act.getApplicationContext().getSharedPreferences("UmangSdkPref", 0); // 0 - for private mode
        return pref.getString(Constants.PREF_WEB_KEY_VALUE,"");
    }

    public static String m4agriAudioSuccessCallback;
    public static String m4agriAudioFailCallback;


    @JavascriptInterface
    public void startRecordAudio(String m4agriSuccessCall, String m4agriFailCall) {

        this.m4agriAudioSuccessCallback = m4agriSuccessCall;
        this.m4agriAudioFailCallback = m4agriFailCall;
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.RECORD_AUDIO)) {

                //fail audio recording
                ((UmangWebActivity) act).sendAudioFailToWeb();

                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_write_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                // ((CommonWebViewActivity)act).openDialog("",act.getResources().getString(R.string.allow_write_storage_permission_help_text),"OK","CANCEL",type);

            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        Constants.MY_PERMISSIONS_RECORD_AUDIO);
            }
        } else {
            ((UmangWebActivity) act).startRecordAudio();
        }
    }

    public static String m4agriVideoSuccessCallback;
    public static String m4agriVideoFailureCallback;

    @JavascriptInterface
    public void startRecordVideo(String videoSuccessCallBack, String videoFailCallBack) {
        this.m4agriVideoSuccessCallback = videoSuccessCallBack;
        this.m4agriVideoFailureCallback = videoFailCallBack;
        if (ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            final List<String> permissionsList = new ArrayList<String>();

            int permissionAskedEarlier = 0;

            if (ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                // Check for Rationale Option
                if (ActivityCompat.shouldShowRequestPermissionRationale(act, Manifest.permission.CAMERA)) {
                    permissionAskedEarlier++;
                } else {
                    permissionsList.add(Manifest.permission.CAMERA);
                }
            }


            if (ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Check for Rationale Option
                if (ActivityCompat.shouldShowRequestPermissionRationale(act, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    permissionAskedEarlier++;
                } else {
                    permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }

            if (permissionAskedEarlier > 0) {
                ((UmangWebActivity) act).sendVideoFailToWeb("");

                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_camera_and_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                //((CommonWebViewActivity)act).openDialog("",act.getResources().getString(R.string.allow_camera_and_storage_permission_help_text),"OK","CANCEL",type);

            } else if (permissionsList.size() > 0) {
                ActivityCompat.requestPermissions(act, permissionsList.toArray(new String[permissionsList.size()]),
                        Constants.MY_PERMISSIONS_RECORD_VIDEO);
            }

        } else {
            ((UmangWebActivity) act).startRecordVideo();
        }
    }

    public static String m4agriImageSuccessCallback;
    public static String m4agriImageFailureCallback;

    @JavascriptInterface
    public void selectImage(String imageSuccessCallBack, String imageFailCallBack) {
        this.m4agriImageSuccessCallback = imageSuccessCallBack;
        this.m4agriImageFailureCallback = imageFailCallBack;

        if (ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            final List<String> permissionsList = new ArrayList<String>();

            int permissionAskedEarlier = 0;

            if (ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                // Check for Rationale Option
                if (ActivityCompat.shouldShowRequestPermissionRationale(act, Manifest.permission.CAMERA)) {
                    permissionAskedEarlier++;
                } else {
                    permissionsList.add(Manifest.permission.CAMERA);
                }
            }


            if (ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Check for Rationale Option
                if (ActivityCompat.shouldShowRequestPermissionRationale(act, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    permissionAskedEarlier++;
                } else {
                    permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }

            if (permissionAskedEarlier > 0) {
                ((UmangWebActivity) act).sendImagesFailToWeb();

                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_camera_and_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                //((CommonWebViewActivity)act).openDialog("",act.getResources().getString(R.string.allow_camera_and_storage_permission_help_text),"OK","CANCEL",type);

            } else if (permissionsList.size() > 0) {
                ActivityCompat.requestPermissions(act, permissionsList.toArray(new String[permissionsList.size()]),
                        Constants.MY_PERMISSIONS_SELECT_IMAGES);
            }

        } else {
            ((UmangWebActivity) act).selectImages();
        }
    }

    public static String share_fileData;
    public static String share_fileName;

    @JavascriptInterface
    public void downloadBase64FileForShare(String fileData, String fileName) {

        share_fileData = fileData;
        share_fileName = fileName;

        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_write_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                //((CommonWebViewActivity)act).openDialog("",act.getResources().getString(R.string.allow_write_storage_permission_help_text),"OK","CANCEL",type);

            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PDF_SHARE);
            }
        } else {
            ((UmangWebActivity) act).downloadBase64FileForShare(fileName, fileData.split(",")[1]);
        }
    }

    /**
     * Method to call from javascript for openIntent to get Document of ID proof DOB proof etc.
     *
     * @param imageSuccessCallBack
     * @param imageFailCallBack
     */
    @JavascriptInterface
    public void proofImage(String imageSuccessCallBack, String imageFailCallBack) {
        act.callBackSuccessFunction = imageSuccessCallBack;
        act.callBackFailureFunction = imageFailCallBack;

        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_read_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                //act.openDialog("",act.getResources().getString(R.string.allow_read_storage_permission_help_text),"OK","CANCEL",type);
            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PAN_PDF);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            String[] mimeTypes = {"image/jpeg", "image/png", "application/pdf"};
            intent.setType("image/*,application/pdf");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            try {
                act.startActivityForResult(intent, Constants.REQUEST_IDENTITY_PROOF);
                UmangAssistiveAndroidSdk.openingIntent = true;
            } catch (Exception e) {

                Toast.makeText(act, act.getResources().getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * method to open intent for photo proof file.
     *
     * @param imageSuccessCallBack
     * @param imageFailCallBack
     */
    @JavascriptInterface
    public void getPhotoFile(String imageSuccessCallBack, String imageFailCallBack) {
        act.callBackSuccessFunction = imageSuccessCallBack;
        act.callBackFailureFunction = imageFailCallBack;
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_read_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                //act.openDialog("",act.getResources().getString(R.string.allow_read_storage_permission_help_text),"OK","CANCEL",type);
            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PAN_PHOTO);
            }
        } else {

            Intent intent = new Intent(Intent.ACTION_PICK);
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            act.startActivityForResult(intent, Constants.REQUEST_PHOTO_PROOF);
        }
        UmangAssistiveAndroidSdk.openingIntent = true;
    }

    /**
     * method to open intent for pdf file.
     *
     * @param successCallBack
     * @param failCallBack
     */
    @JavascriptInterface
    public void getPDFFile(String successCallBack, String failCallBack, String fileSizeStr) {
        act.callBackSuccessFunction = successCallBack;
        act.callBackFailureFunction = failCallBack;
        act.fileSizeStr = fileSizeStr;
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_read_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                //act.openDialog("",act.getResources().getString(R.string.allow_read_storage_permission_help_text),"OK","CANCEL",type);

            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PDF_FILE);
            }
        } else {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            act.startActivityForResult(intent, Constants.REQUEST_PDF_FILE);
        }
        UmangAssistiveAndroidSdk.openingIntent = true;
    }

    @JavascriptInterface
    public void getSignatureFile(String imageSuccessCallBack, String imageFailCallBack) {
        act.callBackSuccessFunction = imageSuccessCallBack;
        act.callBackFailureFunction = imageFailCallBack;

        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_read_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                //act.openDialog("",act.getResources().getString(R.string.allow_read_storage_permission_help_text),"OK","CANCEL",type);
            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PAN_SIGN);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            act.startActivityForResult(intent, Constants.REQUEST_SIGNATURE_FILE);
            UmangAssistiveAndroidSdk.openingIntent = true;
        }
    }

    @JavascriptInterface
    public void installMantraPackageRD() {
        try {
            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.mantra.rdservice")));
        } catch (android.content.ActivityNotFoundException anfe) {
            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.mantra.rdservice")));
        }
    }

    @JavascriptInterface
    public void installMorphoPackageRD() {
        try {
            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.scl.rdservice")));
        } catch (android.content.ActivityNotFoundException anfe) {
            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.scl.rdservice")));
        }
    }

    /**
     * method to open intent for photo proof file.
     *
     * @param imageSuccessCallBack
     * @param imageFailCallBack
     */
    @JavascriptInterface
    public void getPassportPhoto(String imageSuccessCallBack, String imageFailCallBack) {
        act.callBackSuccessFunction = imageSuccessCallBack;
        act.callBackFailureFunction = imageFailCallBack;
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_read_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                //act.openDialog("",act.getResources().getString(R.string.allow_read_storage_permission_help_text),"OK","CANCEL",type);

            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_FOR_PAN_PHOTO);
            }
        } else {

            Intent intent = new Intent(Intent.ACTION_PICK);
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            act.startActivityForResult(intent, Constants.REQUEST_PASSPORT_PHOTO);
        }
        UmangAssistiveAndroidSdk.openingIntent = true;
    }

    /**
     * Method to get Document by type of ,size,and if type includes images then crop enable or not.
     *
     * @param imageSuccessCallBack
     * @param imageFailCallBack
     * @param size                 maximum size for document in kb.
     * @param types                list of extention of document.
     * @param crop                 need crop.
     * @param cropheight           crop image height
     * @param cropwidth            crop image width.
     * @param cropType             crop type Aspect Ratio / Pixels .
     */
    @JavascriptInterface
    public void getDocument(String imageSuccessCallBack, String imageFailCallBack, String size, String types, boolean crop, int cropheight, int cropwidth, String cropType) {
        act.callBackSuccessFunction = imageSuccessCallBack;
        act.callBackFailureFunction = imageFailCallBack;
        act.MaxSize = size;
        act.mTypes = types;
        act.mIsCrop = crop;
        act.mCropHeight = cropheight;
        act.mCropWidth = cropwidth;
        act.mCropType = cropType;

        createChooserDialog();
    }


    public void createChooserDialog() {
        final Dialog dialog = new Dialog(this.act);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_file_dialog_option);
        dialog.setCancelable(true);
        dialog.show();

        LinearLayoutCompat camera = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_camera_txt);
        LinearLayoutCompat gallery = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_gallery_txt);
        LinearLayoutCompat facebook = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_fb_txt);
        LinearLayoutCompat google = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_google_txt);
        LinearLayoutCompat twitter = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_twitter_txt);
        LinearLayoutCompat removePic = (LinearLayoutCompat) dialog.findViewById(R.id.remove_pic_txt);

        removePic.setVisibility(View.GONE);
        facebook.setVisibility(View.GONE);
        google.setVisibility(View.GONE);
        twitter.setVisibility(View.GONE);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                checkCameraPermission(act);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                checkGalleryPermissions(act);
            }
        });
    }


    private void checkCameraPermission(final UmangWebActivity act) {

        if (ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            final List<String> permissionsList = new ArrayList<String>();

            int permissionAskedEarlier = 0;

            if (ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                // Check for Rationale Option
                if (ActivityCompat.shouldShowRequestPermissionRationale(act, Manifest.permission.CAMERA)) {
                    permissionAskedEarlier++;
                } else {
                    permissionsList.add(Manifest.permission.CAMERA);
                }
            }


            if (ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Check for Rationale Option
                if (ActivityCompat.shouldShowRequestPermissionRationale(act, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    permissionAskedEarlier++;
                } else {
                    permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }

            if (permissionAskedEarlier > 0) {
                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_read_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                //act.openDialog("",act.getResources().getString(R.string.allow_read_storage_permission_help_text),"OK","CANCEL",type);

            } else if (permissionsList.size() > 0) {
                ActivityCompat.requestPermissions(act, permissionsList.toArray(new String[permissionsList.size()]),
                        Constants.MY_PERMISSIONS_CAMERA_AND_STORAGE);
            }
        } else {

            openCameraIntent();

        }

    }

    public void openCameraIntent() {
        try {

            // Create AndroidExampleFolder at sdcard
            File imageStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES)
                    , act.getPackageName());

            if (!imageStorageDir.exists()) {
                // Create AndroidExampleFolder at sdcard
                imageStorageDir.mkdirs();
            }

            // Create camera captured image file path and name
            File file = new File(
                    imageStorageDir + File.separator + "IMG_"
                            + String.valueOf(System.currentTimeMillis())
                            + ".jpg");

            Uri capturedImageURI = FileProvider.getUriForFile(act,
                    act.getPackageName() + ".fileprovider",
                    file);
            act.imageSelectedPath = capturedImageURI.toString();

            SharedPreferences pref = act.getApplicationContext().getSharedPreferences("UmangSdkPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(Constants.PREF_CAMERA_IMAGE_URI,capturedImageURI.toString());
            editor.commit();

            // Camera capture image intent
            final Intent captureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);

            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageURI);

            act.startActivityForResult(captureIntent, Constants.REQUEST_COMMON_CAMERA_PHOTO);
            UmangAssistiveAndroidSdk.openingIntent = true;

        } catch (Exception e) {
        }
    }

    private void checkGalleryPermissions(final UmangWebActivity act) {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //show dialog
                Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_read_storage_permission_help_text));
                final String type="WRITE_PERMISSION";
                //act.openDialog("",act.getResources().getString(R.string.allow_read_storage_permission_help_text),"OK","CANCEL",type);

            } else {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_READ_EXTERNAL_STORATE_GALLERY);
            }
        } else {

            openGalleryIntent();
        }
    }

    public void openGalleryIntent() {

        if (act.mTypes.contains("pdf")) {
            if (ContextCompat.checkSelfPermission(act,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //show dialog
                    Utils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_write_storage_permission_help_text));
                    final String type="WRITE_PERMISSION";
                    //act.openDialog("",act.getResources().getString(R.string.allow_read_storage_permission_help_text),"OK","CANCEL",type);
                } else {
                    ActivityCompat.requestPermissions(act,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            Constants.PERMISSIONS_WRITE_EXTERNAL_STORATE_COMMON_METHOD);
                }
            } else {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                String[] mimeTypes = act.mTypes.split(",");
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                try {
                    act.startActivityForResult(intent, Constants.REQUEST_COMMON_DOCUMENT);
                    UmangAssistiveAndroidSdk.openingIntent = true;
                } catch (Exception e) {
                    Toast.makeText(act, act.getResources().getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            try {

                // Create AndroidExampleFolder at sdcard
                File imageStorageDir = new File(
                        Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES)
                        , act.getPackageName());

                if (!imageStorageDir.exists()) {

                    // Create AndroidExampleFolder at sdcard
                    imageStorageDir.mkdirs();
                }

                // Create camera captured image file path and name
                File file = new File(
                        imageStorageDir + File.separator + "IMG_"
                                + String.valueOf(System.currentTimeMillis())
                                + ".jpg");

                Uri capturedImageURI = FileProvider.getUriForFile(act,
                        act.getPackageName() + ".fileprovider",
                        file);
                act.imageSelectedPath = capturedImageURI.toString();

                // Camera capture image intent
                Intent galleyintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleyintent.addCategory(Intent.CATEGORY_OPENABLE);
                galleyintent.setType("image/*");

                // On select image call onActivityResult method of activity
                act.startActivityForResult(galleyintent, Constants.REQUEST_COMMON_GALLERY_PHOTO);
                UmangAssistiveAndroidSdk.openingIntent = true;

            } catch (Exception e) {
            }
        }
    }

    @JavascriptInterface
    public void shareText(String text) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, text);
        act.startActivity(i);
    }

    @JavascriptInterface
    public void openDatePicker(String json, String callback) {
        act.callBackSuccessFunction = callback;
        try {
            JSONObject job = new JSONObject(json);
            String setDateStr = job.getString("setDate");
            String minDateStr = job.getString("minDate");
            String setCurrentMaxStr = job.getString("setCurrentMax");
            String maxDateStr = job.getString("maxDate");

            DatePickerFragmentDepartment newFragment = DatePickerFragmentDepartment.newInstance(setDateStr, minDateStr, setCurrentMaxStr, maxDateStr, CommonInterface.this);
            newFragment.show(act.getSupportFragmentManager(), "datePicker");

        } catch (Exception e) {

            sendDatePickFailure();
        }
    }

    private void sendDatePickFailure() {
        try {
            JSONObject job = new JSONObject();
            job.put("status", "f");
            act.sendLogs(job.toString());
        } catch (JSONException e) {
        }
    }

    @Override
    public void onDatePick(String date, String day) {
        JSONObject object = new JSONObject();
        try {
            object.put("status", "s");
            object.put("date", date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        act.sendLogs(object.toString());
    }

    @JavascriptInterface
    public void scanBarcode(String callback) {
        act.callBackSuccessFunction = callback;
        Intent i = new Intent(act, BarCodeScannerActivity.class);
        act.startActivityForResult(i, Constants.BARCODE_SCAN_REQUEST_CODE);

    }

    @JavascriptInterface
    public boolean isNetworkConnected() {

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) act.getSystemService(act.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }

    @JavascriptInterface
    public void openDialog(String msg) {
        //show dialog
        final String type="INFO";
        Utils.showInfoDialog(act,msg);
    }


    @JavascriptInterface
    public void emailIntent(String json) {
        try {
            JSONObject job = new JSONObject(json);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{job.optString("to")});
            intent.putExtra(Intent.EXTRA_SUBJECT, job.optString("subject"));
            intent.putExtra(Intent.EXTRA_TEXT, job.optString("body"));
            act.startActivity(intent);
        } catch (Exception e) {
        }

    }

    @JavascriptInterface
    public void openPlayStoreForUpdate(String link){
        try {
            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "in.gov.umang.negd.g2c")));
        } catch (android.content.ActivityNotFoundException anfe) {
            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "in.gov.umang.negd.g2c")));
        }
    }

    @JavascriptInterface
    public void printScreen(){

        Context context=act;

        WebView webView=act.mAgentWeb.getWebCreator().getWebView();

        webView.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                // Get a PrintManager instance
                PrintManager printManager = (PrintManager) context
                        .getSystemService(Context.PRINT_SERVICE);

                String jobName = "Umang_" + " Document";

                // Get a print adapter instance
                PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

                // Create a print job with name and adapter instance
                PrintJob printJob = printManager.print(jobName, printAdapter,
                        new PrintAttributes.Builder().build());

                // Save the job object for later status checking
                if(printJob.isCompleted()){
                    Toast.makeText(act, "Complete", Toast.LENGTH_LONG).show();
                }
                else if(printJob.isFailed()){
                    Toast.makeText(act,R.string.failed, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @JavascriptInterface
    public void deptLogout(String deptId){
        Toast.makeText(act.getApplicationContext(),"You are logged out",Toast.LENGTH_LONG).show();
        act.finish();
    }
}
