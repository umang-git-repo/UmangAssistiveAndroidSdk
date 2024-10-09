package com.negd.umangwebview.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.transition.Slide;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import com.negd.umangwebview.R;
import com.negd.umangwebview.ui.CustomDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class CommonUtils {

    private static final String TAG = CommonUtils.class.getName();

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }


    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        try{
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.show();
            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            return progressDialog;
        }catch (Exception ex){
            return null;
        }

    }

    public static void showInfoDialog(Context context, String msg) {

        try{
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_info);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            AppCompatTextView dialogTxt = dialog.findViewById(R.id.dialogTxt);
            dialogTxt.setText(msg);
            Button btnOK = dialog.findViewById(R.id.btnOk);
            btnOK.setOnClickListener(view -> {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    dialog.cancel();
                }
            });
        }catch (Exception ex){

        }
    }
    public static void showInfoDialog(Context context, String msg, String type, CustomDialog.DialogButtonClickListener clickListener) {

        try {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_info);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            AppCompatTextView dialogTxt = dialog.findViewById(R.id.dialogTxt);
            dialogTxt.setText(msg);
            Button btnOK = dialog.findViewById(R.id.btnOk);
            btnOK.setOnClickListener(view -> {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    dialog.cancel();
                    clickListener.onOkClick(type);
                }
            });
        } catch (Exception ex) {

        }

    }
    public static String getUniqueNumber() {
        String str = "";
        str = "" + System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            str = str + generateRandomNumber(0, 9);
        }

//        AppLogger.d(TAG, "getUniqueNumber : " + str);
        return str;
    }


    public static String getUniqueNumberBbps() {
        String str = "";
        str = "" + System.currentTimeMillis();

        for (int i = 0; i < 12; i++) {
            str = str + generateRandomNumber(0, 9);
        }

//        AppLogger.d(TAG, "getUniqueNumber : " + str);
        return str;
    }

    public static long generateRandomNumber(int min, int max) {
        SecureRandom r = new SecureRandom();
        return r.nextInt((max - min) + 1) + min;
    }

    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        Date date = new Date();
        String timeStamp = sdf.format(date);
        return timeStamp;
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * Method to hide keyboard
     *
     * @param activity context of activity
     */
    public static void hideSoftKeyboard(AppCompatActivity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        AppCompatActivity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(
                Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
    }
    /**
     * Mehtod to show keyboard
     *
     * @param activity context of activity
     */
    public void showKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = activity.getCurrentFocus();
        if (v != null) {
            assert imm != null;
            imm.showSoftInput(v, 0);
        }
    }
//    public static void showInfoDialogUpdate(Context context, String msg, AppCompatActivity activity, AppUpdateManager updateManager) {
//
//        try{
//            Dialog dialog = new Dialog(context);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.dialog_info_cancel);
//            dialog.setCancelable(false);
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//            AppCompatTextView dialogTxt = dialog.findViewById(R.id.dialogTxt);
//            dialogTxt.setText(msg);
//            AppCompatButton btnOK = dialog.findViewById(R.id.btnOk);
//            AppCompatButton btnCancel = dialog.findViewById(R.id.btnCancel);
//            btnOK.setOnClickListener(view -> {
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                    dialog.cancel();
//                    updateManager.completeUpdate();
//                }
//            });
//
//            btnCancel.setOnClickListener(view -> {
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                    dialog.cancel();
//                }
//            });
//        }catch (Exception ex){
//
//        }
//
//    }

    public static void showInfoDialogHtml(Context context, String msg) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        AppCompatTextView dialogTxt = dialog.findViewById(R.id.dialogTxt);
        dialogTxt.setText(Html.fromHtml(msg));
        Button btnOK = dialog.findViewById(R.id.btnOk);
        btnOK.setOnClickListener(view -> {
            dialog.cancel();
        });
    }

    public static void openPermissionSettingsDialog(final Context mContext, String msg) {
        final Dialog d = new Dialog(mContext);
        d.requestWindowFeature(d.getWindow().FEATURE_NO_TITLE);
        d.setContentView(R.layout.open_permission_settings_dialog);
        d.setCancelable(true);

        TextView cancelTxt = (TextView) d.findViewById(R.id.cancelTxt);
        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        TextView settingsTxt = (TextView) d.findViewById(R.id.settingsTxt);
        settingsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                intent.setData(uri);
                mContext.startActivity(intent);
            }
        });

        TextView msgTxt = (TextView) d.findViewById(R.id.msgTxt);
        msgTxt.setText(msg);

        d.show();
    }

//    public static void logOutUser(Context ctx) {
//        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(ctx);
//        appPreferencesHelper.clearAllPref();
//        Intent i = new Intent(ctx, LoginActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        ctx.startActivity(i);
//    }


    public static void requestFocus(Activity activity, View view) {
        if (view.requestFocus()) {
            Objects.requireNonNull(activity).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public static String getPackageName(Context context) {
        return context.getPackageName();
    }


//    public static void showProfileImageFromURL(Uri imageUrl, Context context, CircleImageView mImageViewProfile) {
//        Glide.with(context)
//                .load(new File(Objects.requireNonNull(imageUrl.getPath())))
//                .transform(new RoundedCorners(10))
//                .placeholder(R.drawable.ic_profile_photo)
//                .error(R.drawable.ic_profile_photo)
//                .into(mImageViewProfile);
//    }

    public static Drawable getDrawable(Context context, int drawableId) {
        return ContextCompat.getDrawable(context, drawableId);
    }

    public static String checkIfEmpty(String val) {
        if (val != null) {

            return val;
        } else {
            return "";
        }
    }


    public static void setAnimation(Window window, Context context) {
        if (Build.VERSION.SDK_INT > 20) {

            try{
                Slide slide = new Slide(GravityCompat.getAbsoluteGravity(GravityCompat.END, context.getResources().getConfiguration().getLayoutDirection()));
                slide.setDuration(400);
                slide.setInterpolator(new DecelerateInterpolator());
                window.setExitTransition(slide);
                window.setEnterTransition(slide);
            }catch (Exception ex){

            }

        }
    }

    public static String getFormattedDateForNotification(String dateStr) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Date dt1 = format1.parse(dateStr);
            DateFormat format2 = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.US);
            return format2.format(dt1);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCurrentDateBbps() {
        try {
            Date date2= new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            return format1.format(date2);
        } catch (Exception e) {
            return "";
        }
    }


    public static boolean checkIsJeevanPramanUrl(String url) {
        if (url.contains("/jp/")) {
            return true;
        } else {
            return false;
        }
    }

//    public static void openDigilocker(Context context, String serviceId, String sTab, String sSection, String sState, String sBanner, DataManager dataManager) {
//
//
//        //hitRecentAPI(context, serviceId);
//        //hitBILoggingAPI(context, serviceId, sTab, sSection, sState, sBanner);
//
//        if (dataManager.getStringPreference(AppPreferencesHelper.PREF_DIGILOCKER_LINKED, "").equalsIgnoreCase("Y")) {
//            context.startActivity(new Intent(context, DigiLockerDocsViewActivity.class));
//        } else {
//            context.startActivity(new Intent(context, DigiLockerMainActivity.class));
//        }
//    }
//    public static void openBBPS(Context context, String serviceId, String serviceName, String sTab, String sSection, String sState, String sBanner, DataManager dataManager) {
//
//        CommonWebViewActivity.BbpsServieId=serviceId;
//        Intent intent = new Intent(context, BbpsLandingActivity.class);
//        context.startActivity(intent);
//    }
//
//    public static String getRegMob(Context context, DataManager manager) {
//        try {
//            UserDataHandler userProfileHandler = new UserDataHandler(manager);
//            manager.writeStringPreference(AppPreferencesHelper.PREF_MOBILE_NUMBER, userProfileHandler.getUserData().getMno());
//        } catch (Exception e) {
//            LogUtil.printStackTrace(e);
//        }
//        return manager.getStringPreference(AppPreferencesHelper.PREF_MOBILE_NUMBER, "");
//    }


    public static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    // PROFILE IMAGE
//    public static void createPictureChooser(final Activity act, final
//    Uri mCapturedImageURI, boolean showSocialOptions, boolean showRemovePicOption) {
//        final Dialog dialog = new Dialog(act);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.choose_file_option_dialog);
//        dialog.setCancelable(true);
//        dialog.show();
//
//        LinearLayoutCompat camera = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_camera_txt);
//        LinearLayoutCompat gallery = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_gallery_txt);
//        LinearLayoutCompat removePic = (LinearLayoutCompat) dialog.findViewById(R.id.remove_pic_txt);
//        LinearLayoutCompat facebook = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_fb_txt);
//        LinearLayoutCompat google = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_google_txt);
//        LinearLayoutCompat twitter = (LinearLayoutCompat) dialog.findViewById(R.id.dialog_twitter_txt);
//
//        try{
//            if(removePic!=null) {
//                removePic.setVisibility(View.GONE);
//            }
//            facebook.setVisibility(View.GONE);
//            google.setVisibility(View.GONE);
//            twitter.setVisibility(View.GONE);
//        }catch(Exception ex){
//
//        }
//
//
//
//        if (showSocialOptions) {
//            if (showRemovePicOption) {
//                removePic.setVisibility(View.VISIBLE);
//            } else {
//                removePic.setVisibility(View.GONE);
//            }
//
//        } else {
//            removePic.setVisibility(View.GONE);
//        }
//        removePic.setOnClickListener(view -> {
//            dialog.dismiss();
//            ((ProfileScreenActivity) act).resetProfilePic();
//        });
//
//
//        camera.setOnClickListener(view -> {
//            dialog.dismiss();
//            checkCameraPermission(act, mCapturedImageURI);
//
//        });
//
//        gallery.setOnClickListener(view -> {
//            dialog.dismiss();
//            checkGalleryPermissions(act);
//
//        });
//
//
//    }

//    public static void checkCameraPermission(final Activity act, final Uri mCapturedImageURI) {
//
//
//        if (ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//            final List<String> permissionsList = new ArrayList<String>();
//
//            int permissionAskedEarlier = 0;
//
//            if (ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//
//                // Check for Rationale Option
//                if (ActivityCompat.shouldShowRequestPermissionRationale(act, Manifest.permission.CAMERA)) {
//                    permissionAskedEarlier++;
//                } else {
//                    permissionsList.add(Manifest.permission.CAMERA);
//                }
//            }
//
//            if (ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//                // Check for Rationale Option
//                if (ActivityCompat.shouldShowRequestPermissionRationale(act, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    permissionAskedEarlier++;
//                } else {
//                    permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                }
//            }
//
//            if (permissionAskedEarlier > 0) {
//                CommonUtils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_camera_and_storage_permission_help_text));
//            } else if (permissionsList.size() > 0) {
//                ActivityCompat.requestPermissions(act, permissionsList.toArray(new String[permissionsList.size()]),
//                        AppConstants.MY_PERMISSIONS_CAMERA_AND_STORAGE);
//            }
//
//
//        } else {
//            openCameraIntent(act, mCapturedImageURI);
//        }
//
//
//    }
//
//
//    public static void openCameraIntent(final Activity act, final Uri mCapturedImageURI) {
//        try {
//            final Intent captureIntent = new Intent(
//                    MediaStore.ACTION_IMAGE_CAPTURE);
//
//            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
//            act.startActivityForResult(captureIntent, AppConstants.CAMERA_INTENT_CODE);
//            UmangApplication.openingIntent = true;
//
//        } catch (Exception e) {
//            LogUtil.printStackTrace(e);
//        }
//    }
//
//
//    public static void checkGalleryPermissions(final Activity act) {
//        if (ContextCompat.checkSelfPermission(act,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                CommonUtils.openPermissionSettingsDialog(act, act.getResources().getString(R.string.allow_read_storage_permission_help_text));
//
//            } else {
//                ActivityCompat.requestPermissions(act,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        AppConstants.MY_PERMISSIONS_READ_EXTERNAL_STORATE);
//            }
//        } else {
//
//            openGalleryIntent(act);
//        }
//    }
//
//    public static void openGalleryIntent(Activity act) {
//        try {
//
//            Intent galleyintent = new Intent(Intent.ACTION_GET_CONTENT);
//            galleyintent.addCategory(Intent.CATEGORY_OPENABLE);
//
//            galleyintent.setType("image/*");
//            act.startActivityForResult(galleyintent, AppConstants.GALLERY_INTENT_CODE);
//            UmangApplication.openingIntent = true;
//
//        } catch (Exception e) {
//            LogUtil.printStackTrace(e);
//        }
//    }

    public static boolean containsLink(String input) {
        boolean result = false;

        String[] parts = input.split("\\s+");

        for (String item : parts) {
            if (Patterns.WEB_URL.matcher(item).matches()) {
                result = true;
                break;
            }
        }
        return result;
    }
    public static long getChatTimeStamp(String time) {
        try {
            String temp = getCurrentDate() + " " + time;
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.US);
            Date dt1 = format1.parse(temp);

            return dt1.getTime();
        } catch (Exception e) {
//            AppLogger.e(e.toString());
            return System.currentTimeMillis();
        }
    }

    public static String getCurrentDate() {
        try {
            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            String formattedDate = df.format(c.getTime());

            return formattedDate;
        } catch (Exception e) {
//            AppLogger.e(e.toString());
            return "";
        }
    }

//    private static ServiceData serviceData;
    /**
     * Handles the item click event on Notification list item.
     *
//     * @param notificationData Notification object
     */
//    public static void handleListItemClick(Context context, NotificationData notificationData, String section, DataManager dataManager) {
//
//        CommonUtils.sendClickEventAnalytics(
//                (Activity) context,
//                null,
//                "Notification Click",
//                "clicked",
//                "Notification Screen"
//        );
//
//        String subType = notificationData.getNotifSubType();
//        String dialogMsg = notificationData.getNotifDialogMsg();
//        String title = notificationData.getNotifTitle();
//        String url = notificationData.getNotifUrl();
//        String webpageTitle = notificationData.getNotifWebpageTitle();
//        String screenName = notificationData.getNotifScreenName();
//        String serviceId = notificationData.getServiceId();
//
//        Intent i;
//        if (subType.equalsIgnoreCase("openApp")) {
//            i = new Intent(context, HomeActivity.class);
//        } else if (subType.equalsIgnoreCase("openAppWithDialog")) {
//            i = null;
//            if (dialogMsg != null) {
//                if (title.equalsIgnoreCase("")) {
//                    title = context.getResources().getString(R.string.app_name);
//                }
//                CommonUtils.showInfoDialog(context, dialogMsg);
//            }
//
//        } else if (subType.equalsIgnoreCase("playstore")) {
//            i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        } else if (subType.equalsIgnoreCase("browser")) {
//            i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        } else if (subType.equalsIgnoreCase("webview")) {
//            i = new Intent(context, BrowserActivity.class);
//            i.putExtra("title", webpageTitle);
//            i.putExtra("url", url);
//        } else if (subType.equalsIgnoreCase("rating")) {
//            i = new Intent(context, SideMenuActivity.class);
//            i.putExtra("showRating", "showRating");
//        } else if (subType.equalsIgnoreCase("share")) {
//            i = new Intent(context, HomeActivity.class);
//            i.putExtra("share", "share");
//        } else if (subType.equalsIgnoreCase("openAppWithTab")) {
//            i = new Intent(context, HomeActivity.class);
//            i.putExtra("openAppWithTab", screenName);
//        } else if (subType.equalsIgnoreCase("openAppWithScreen")) {
//            if (screenName.equalsIgnoreCase("settings")) {
//                i = new Intent(context, SettingActivity.class);
//            } else if (screenName.equalsIgnoreCase("help")) {
//                i = new Intent(context, PhoneSupportActivity.class);
//            } else if (screenName.equalsIgnoreCase("feedback")) {
//                i = new Intent(context, SendFeedbackActivity.class);
//            } else if (screenName.equalsIgnoreCase("accountsettings")) {
//                i = new Intent(context, AccountSettingActivity.class);
//            } else if (screenName.equalsIgnoreCase("myprofile")) {
//                i = new Intent(context, ProfileScreenActivity.class);
//            } else if (screenName.equalsIgnoreCase("myprofilegeneral")) {
//                i = new Intent(context, ProfileScreenActivity.class);
//            } else {
//                i = new Intent(context, HomeActivity.class);
//            }
//
//        }else if(subType.equalsIgnoreCase("sub_service") && serviceId !=null && url !=null){
//            i = new Intent(context, CommonWebViewActivity.class);
//            i.putExtra(CommonWebViewActivity.INTENT_SERVICE_ID,serviceId);
//            i.putExtra(CommonWebViewActivity.INTENT_SERVICE_NAME,title);
//            i.putExtra(CommonWebViewActivity.INTENT_SERVICE_URL,url);
//            String lang="en";
//            lang=dataManager.getStringPreference(AppPreferencesHelper.PREF_LANGUAGE_SELECTED,"en");
//            i.putExtra(CommonWebViewActivity.INTENT_SERVICE_LANGUAGE,lang);
//
//        }  else if (subType.equalsIgnoreCase("service")) {
//
//            if (serviceId != null) {
//                if (!serviceId.equalsIgnoreCase("")) {
//                    //TODO check data
//                    serviceData=null;
//
//                    Thread t = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            serviceData=dataManager.getSeviceForNotification(serviceId);
//                        }});
//
//                    t.start(); // spawn thread
//
//                    try {
//                        t.join();  // wait for thread to
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    if(serviceData ==null){
//                        return;
//                    }
//                    //ServiceData serviceData = dataManager.getSeviceForNotification(serviceId);
//
//                    String tempTitle = "";
//                    String tempImg = "";
//
//                    if (serviceData != null) {
//                        tempImg = serviceData.getImage();
//                        tempTitle = serviceData.getServiceName();
//                    } else {
//                        tempImg = "drawable://" + R.drawable.app_icon;
//                        tempTitle = context.getResources().getString(R.string.app_name);
//                    }
//                    if (webpageTitle != null) {
//                        if (!webpageTitle.equalsIgnoreCase("")) {
//                            tempTitle = webpageTitle;
//                        }
//                    }
//
//                    if (CommonUtils.checkIsDigilockerUrl(url)) {
//                        i = null;
//                        CommonUtils.openDigilocker(
//                                context,
//                                serviceId,
//                                "notification",
//                                section,
//                                "",
//                                "",
//                                dataManager
//                        );
//                    } else if (CommonUtils.checkIsBBPSUrl(url)) {
//                        i = null;
//                        CommonUtils.openBBPS(
//                                context,
//                                serviceId,
//                                tempTitle,
//                                "notification",
//                                section,
//                                "",
//                                "",
//                                dataManager
//                        );
//                    } else {
//                        i = new Intent(context, CommonWebViewActivity.class);
//                        i.putExtra("fromNotif", "fromNotif");
//                        i.putExtra(CommonWebViewActivity.INTENT_SERVICE_NAME, tempTitle);
//                        i.putExtra(CommonWebViewActivity.INTENT_SERVICE_URL, url);
//                        i.putExtra(CommonWebViewActivity.INTENT_SERVICE_ID, serviceId);
//
//                        i.putExtra(AppConstants.SOURCE_TAB, "notification");
//                        i.putExtra(AppConstants.SOURCE_SECTION, section);
//                        i.putExtra(AppConstants.SOURCE_STATE, "");
//                        i.putExtra(AppConstants.SOURCE_BANNER, "");
//                    }
//
//
//                } else {
//                    i = new Intent(context, HomeActivity.class);
//                }
//            } else {
//                i = new Intent(context, HomeActivity.class);
//            }
//        } else {
//            i = new Intent(context, HomeActivity.class);
//        }
//        if (i != null)
//            try {
//                context.startActivity(i);
//            } catch (Exception e) {
//                LogUtil.printStackTrace(e);
//            }
//    }
//
//    public static synchronized void showNewInfoDialog(final Context context, ObjDialog dialogJson, boolean shouldShowAttentionDialog, DataManager dataManager) {
//        if (dialogJson == null || !shouldShowAttentionDialog) {
//            return;
//        }
//        try {
//            String dialogId = dialogJson.getId();
//            if ("".equalsIgnoreCase(dialogId)) {
//                return;
//            }
//            String title = dialogJson.getTitle();
//            String imageUrl = dialogJson.getBimg();
//            String description = dialogJson.getDes();
//            String btnText = dialogJson.getbTxt();
//            final String actionType = dialogJson.getActionType();
//            final String actionURL = dialogJson.getActionUrl();
//
//            final Dialog dialog = new Dialog(context);
//            dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.setContentView(R.layout.dialog_new_update);
//            dialog.setCancelable(false);
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//            TextView titleTxt = (TextView) dialog.findViewById(R.id.headeing_txt);
//            TextView descTxt = (TextView) dialog.findViewById(R.id.description_txt);
//            TextView actionTxt = (TextView) dialog.findViewById(R.id.action_txt);
//            ImageView bannerImg = (ImageView) dialog.findViewById(R.id.banner_img);
//            ImageView closeImg = (ImageView) dialog.findViewById(R.id.close_img);
//
//
//            titleTxt.setText(title);
//            descTxt.setText(Html.fromHtml(description));
//            actionTxt.setText(btnText);
//
//            ImageLoader imgLoader = ImageLoader.getInstance();
//            DisplayImageOptions dialogImageOptions = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.banner_default)
//                    .showImageForEmptyUri(R.drawable.banner_default)
//                    .showImageOnFail(R.drawable.banner_default)
//                    .cacheInMemory(true)
//                    .cacheOnDisc(true)
//                    .bitmapConfig(Bitmap.Config.RGB_565)
//                    .build();
//            if (imageUrl != null && !imageUrl.isEmpty()) {
//                bannerImg.setVisibility(View.VISIBLE);
//                imgLoader.displayImage(imageUrl, bannerImg, dialogImageOptions);
//            } else {
//                bannerImg.setVisibility(View.GONE);
//            }
//
//            actionTxt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                    CommonUtils.handleBannerClick(context, actionType, actionURL,dataManager);
//                }
//            });
//            closeImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//
//        } catch (Exception e) {
//            AppLogger.e(e.toString());
//        }
//
//
//    }
//
//    public static void handleBannerClick(Context context, String actionType, String actionUrl,DataManager dataManager) {
//
//
//        Intent i=null;
//        if (actionType.equalsIgnoreCase("youtube")) {
//            i = new Intent(Intent.ACTION_VIEW, Uri.parse(actionUrl));
//        } else if (actionType.equalsIgnoreCase("playstore")) {
//            i = new Intent(Intent.ACTION_VIEW, Uri.parse(actionUrl));
//        } else if (actionType.equalsIgnoreCase("browser")) {
//            i = new Intent(Intent.ACTION_VIEW, Uri.parse(actionUrl));
//        } else if (actionType.equalsIgnoreCase("webview")) {
//
//            String action[] = actionUrl.split("\\|");
//            String tempUrl = action[0];
//            String tempTitle = action[1];
//
//            i = new Intent(context, BrowserActivity.class);
//            i.putExtra("title", tempTitle);
//            i.putExtra("url", tempUrl);
//        } else if (actionType.equalsIgnoreCase("rating")) {
//            i = new Intent(context, HomeActivity.class);
//            i.putExtra("showRating", "showRating");
//        } else if (actionType.equalsIgnoreCase("share")) {
//            i = new Intent(context, HomeActivity.class);
//            i.putExtra("share", "share");
//        } else if (actionType.equalsIgnoreCase("openAppWithTab")) {
//            i = new Intent(context, HomeActivity.class);
//            i.putExtra("openAppWithTab", actionUrl);
//        } else if (actionType.equalsIgnoreCase("openAppWithScreen")) {
//            if (actionUrl.equalsIgnoreCase("settings")) {
//                i = new Intent(context, SettingActivity.class);
//            } else if (actionUrl.equalsIgnoreCase("help")) {
//                i = new Intent(context, PhoneSupportActivity.class);
//            } else if (actionUrl.equalsIgnoreCase("social")) {
//                i = new Intent(context, AccountSettingActivity.class);
//            } else if (actionUrl.equalsIgnoreCase("aadhaar")) {
//                //i = new Intent(context, AadhaarProfileScreen.class);
//            } else if (actionUrl.equalsIgnoreCase("feedback")) {
//                i = new Intent(context, SendFeedbackActivity.class);
//            } else if (actionUrl.equalsIgnoreCase("accountsettings")) {
//                i = new Intent(context, AccountSettingActivity.class);
//            } else if (actionUrl.equalsIgnoreCase("myprofile")) {
//                i = new Intent(context, ProfileScreenActivity.class);
//            } else if (actionUrl.equalsIgnoreCase("myprofilegeneral")) {
//                i = new Intent(context, ProfileScreenActivity.class);
//            } else {
//                i = new Intent(context, HomeActivity.class);
//            }
//
//        } else if (actionType.equalsIgnoreCase("service")) {
//
//            String action[] = actionUrl.split("\\|");
//            String tempUrl = action[0];
//            String tempWebTitle = action[1];
//            String serviceId = action[2];
//
//            if (serviceId != null) {
//                if (!serviceId.equalsIgnoreCase("")) {
//                    //TODO check data
//
//                    serviceData=null;
//
//                    Thread t = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            serviceData=dataManager.getSeviceForNotification(serviceId);
//                        }});
//
//                    t.start(); // spawn thread
//
//                    try {
//                        t.join();  // wait for thread to
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    if(serviceData ==null){
//                        return;
//                    }
//
//                    ServiceData bean =serviceData;
//
//                    String tempTitle = "";
//                    String tempImg = "";
//
//                    if (bean != null) {
//                        tempImg = bean.getImage();
//                        tempTitle = bean.getServiceName();
//                    } else {
//                        tempImg = "drawable://" + R.drawable.app_icon;
//                        tempTitle = context.getResources().getString(R.string.app_name);
//                    }
//                    if (tempWebTitle != null) {
//                        if (!tempWebTitle.equalsIgnoreCase("")) {
//                            tempTitle = tempWebTitle;
//                        }
//                    }
//
//                    if (CommonUtils.checkIsDigilockerUrl(tempUrl)) {
//                        i = null;
//                        CommonUtils.openDigilocker(
//                                context,
//                                serviceId,
//                                "notification",
//                                "",
//                                "",
//                                "",
//                                dataManager
//                        );
//                    } else if (CommonUtils.checkIsBBPSUrl(tempUrl)) {
//                        i = null;
//                        CommonUtils.openBBPS(
//                                context,
//                                serviceId,
//                                tempTitle,
//                                "notification",
//                                "",
//                                "",
//                                "",
//                                dataManager
//                        );
//                    } else {
//                        i = new Intent(context, CommonWebViewActivity.class);
//                        i.putExtra("fromNotif", "fromNotif");
//                        i.putExtra(CommonWebViewActivity.INTENT_SERVICE_NAME, tempTitle);
//                        i.putExtra(CommonWebViewActivity.INTENT_SERVICE_URL, tempUrl);
//                        i.putExtra(CommonWebViewActivity.INTENT_SERVICE_ID, serviceId);
//
//                        i.putExtra(AppConstants.SOURCE_TAB, "attention_screen");
//                        i.putExtra(AppConstants.SOURCE_SECTION, "");
//                        i.putExtra(AppConstants.SOURCE_STATE, "");
//                        i.putExtra(AppConstants.SOURCE_BANNER, "");
//                    }
//
//
//
//                } else {
//                    i = new Intent(context, HomeActivity.class);
//                }
//            } else {
//                i = new Intent(context, HomeActivity.class);
//            }
//        } else {
//            i = null;
//        }
//        try {
//            if (i != null) {
//                context.startActivity(i);
//            }
//        } catch (Exception e) {
//            AppLogger.e(e.toString());
//        }
//    }

//    public static void showRecoveryOptionDialog(final Context context, boolean showOnlyRecoveryOption, final String dialogData,DataManager dataManager) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setContentView(R.layout.dialog_mpin_confirmation);
//
//        ImageView imageView10 = (ImageView) dialog.findViewById(R.id.imageView10);
//        TextView textView24 = (TextView) dialog.findViewById(R.id.textView24);
//        TextView textView34 = (TextView) dialog.findViewById(R.id.textView34);
//
//
//        if (showOnlyRecoveryOption) {
//            imageView10.setImageResource(R.drawable.lock);
//            textView24.setText(context.getResources().getString(R.string.secure_your_account));
//            textView34.setVisibility(View.GONE);
//        }
//
//        TextView close_txt = (TextView) dialog.findViewById(R.id.close_txt);
//        close_txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "close click recovery dialog...............");
//
//                dialog.dismiss();
//
//                try {
//                    if (dialogData != null) {
//                        if (!dialogData.equalsIgnoreCase("")) {
//                            JSONObject dialogObj = new JSONObject(dialogData);
//
//                            String dialogId = dialogObj.getString("id");
//
//                            String oldDialogId =dataManager.getStringPreference(AppPreferencesHelper.PREF_APP_NEW_UPDATE_DIALOG,"");
//
//
//                            if (!dialogId.equalsIgnoreCase(oldDialogId)) {
//
//                                CommonUtils.showNewInfoDialog(context, dialogObj, true,dataManager);
//                                dataManager.writeStringPreference(AppPreferencesHelper.PREF_APP_NEW_UPDATE_DIALOG,dialogId);
//                            }
//                        }
//                    }
//                } catch (JSONException e) {
//                    AppLogger.e(e.toString());
//                }
//            }
//        });
//
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                Log.d(TAG, "onCancel recovery dialog...............");
//                try {
//
//                    if (dialogData != null) {
//                        if (!dialogData.equalsIgnoreCase("")) {
//                            JSONObject dialogObj = new JSONObject(dialogData);
//
//                            String dialogId = dialogObj.getString("id");
//
//                            String oldDialogId =dataManager.getStringPreference(AppPreferencesHelper.PREF_APP_NEW_UPDATE_DIALOG,"");
//
//
//                            if (!dialogId.equalsIgnoreCase(oldDialogId)) {
//                                CommonUtils.showNewInfoDialog(context, dialogObj, true,dataManager);
//                                dataManager.writeStringPreference(AppPreferencesHelper.PREF_APP_NEW_UPDATE_DIALOG,dialogId);
//
//                            }
//                        }
//                    }
//                } catch (JSONException e) {
//                    AppLogger.e(e.toString());
//                }
//            }
//        });
//
//        View.OnClickListener accountSettingListner = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                Intent i = new Intent(context, AccRec.class);
//                context.startActivity(i);
//                dialog.dismiss();
//            }
//        };
//
//
//        dialog.findViewById(R.id.ll_security).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, AccountRecoveryOptionScreen.class);
//                i.putExtra("fromDialog", "true");
//                i.putExtra("type", "secQues");
//                context.startActivity(i);
//                dialog.dismiss();
//            }
//        });
//        dialog.findViewById(R.id.ll_alt_mobile).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(context, AccountRecoveryOptionScreen.class);
//                i.putExtra("fromDialog", "true");
//                i.putExtra("type", "altNumber");
//                context.startActivity(i);
//                dialog.dismiss();
//            }
//        });
//        dialog.findViewById(R.id.ll_email).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, AccountRecoveryOptionScreen.class);
//                i.putExtra("fromDialog", "true");
//                i.putExtra("type", "email");
//                context.startActivity(i);
//                dialog.dismiss();
//            }
//        });
//        dialog.findViewById(R.id.tv_account_settings).setOnClickListener(accountSettingListner);
//
//        dialog.show();
//
//    }


//    public static ArrayList<String> getQuestionAlist(Context context) {
//        ArrayList<String> alist = new ArrayList<>();
//        alist.add(context.getResources().getString(R.string.question1));
//        alist.add(context.getResources().getString(R.string.question2));
//        alist.add(context.getResources().getString(R.string.question3));
//        alist.add(context.getResources().getString(R.string.question4));
//        alist.add(context.getResources().getString(R.string.question5));
//        return alist;
//    }

//    public static ArrayList<String> getQuesIdAlist(Context context) {
//        ArrayList<String> alist = new ArrayList<>();
//        alist.add(context.getResources().getString(R.string.ques_id1));
//        alist.add(context.getResources().getString(R.string.ques_id2));
//        alist.add(context.getResources().getString(R.string.ques_id3));
//        alist.add(context.getResources().getString(R.string.ques_id4));
//        alist.add(context.getResources().getString(R.string.ques_id5));
//        return alist;
//    }
//
//    public static void sendScreenNameAnalytics(Activity activity, String screenName) {
//        try {
//            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity);
//            mFirebaseAnalytics.setCurrentScreen(activity, screenName, null);
//        } catch (Exception e) {
//            AppLogger.e(e.getMessage());
//        }
//
//    }
//
//    public static void sendClickEventAnalytics(Activity activity, String campaignId, String category, String action, String label) {
//        if (campaignId == null) {
//            try {
//                FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity);
//                Bundle params = new Bundle();
//                params.putString("category", category);
//                params.putString("action", action);
//                params.putString("label", label);
//                params.putLong("value", 01);
//                mFirebaseAnalytics.logEvent("custom_ga_event", params);
//            } catch (Exception e) {
//                AppLogger.e(e.getMessage());
//            }
//        } else{
//            try {
//                FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity);
//                Bundle params = new Bundle();
//                params.putString("campaignId", campaignId);
//                params.putString("category", category);
//                params.putString("action", action);
//                params.putString("label", label);
//                params.putLong("value", 01);
//                mFirebaseAnalytics.logEvent("custom_ga_event", params);
//            } catch (Exception e) {
//                AppLogger.e(e.getMessage());
//            }
//        }
//    }
//
//    public static void sendModuleClickEventAnalytics(Activity activity, String moduleName, String campaignId, String category, String action, String label) {
//        if (campaignId == null) {
//            try {
//                FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity);
//                Bundle params = new Bundle();
//                params.putString("category", category);
//                params.putString("action", action);
//                params.putString("label", label);
//                //params.putLong("value", 01);
//                mFirebaseAnalytics.logEvent(moduleName, params);
//            } catch (Exception e) {
//                AppLogger.e(e.getMessage());
//            }
//        } else{
//            try {
//                FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity);
//                Bundle params = new Bundle();
//                params.putString("campaignId", campaignId);
//                params.putString("category", category);
//                params.putString("action", action);
//                params.putString("label", label);
//                //params.putLong("value", 01);
//                mFirebaseAnalytics.logEvent(moduleName, params);
//            } catch (Exception e) {
//                AppLogger.e(e.getMessage());
//            }
//        }
//    }
//
//    public static void sendLoginEventAnalytics(Context context, String method){
//        try {
//            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//            Bundle bundle = new Bundle();
//            bundle.putString(FirebaseAnalytics.Param.SIGN_UP_METHOD, method);
//            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
//        } catch (Exception e) {
//            AppLogger.e(e.getMessage());
//        }
//    }
//
//    public static void sendSignUpEventAnalytics(Context context, String method){
//        try {
//            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//            Bundle bundle = new Bundle();
//            bundle.putString(FirebaseAnalytics.Param.SIGN_UP_METHOD, method);
//            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
//        } catch (Exception e) {
//            AppLogger.e(e.getMessage());
//        }
//    }
//
//    public static void sendShareEventAnalytics(Context context){
//        try {
//            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//            Bundle bundle = new Bundle();
//            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
//        } catch (Exception e) {
//            AppLogger.e(e.getMessage());
//        }
//    }
//
//    public static void sendSearchEventAnalytics(Context context, String searchItem){
//        try {
//            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//            Bundle bundle = new Bundle();
//            bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, searchItem);
//            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
//        } catch (Exception e) {
//            AppLogger.e(e.getMessage());
//        }
//
//    }

    public static String getNumbers(String message) {

        String otpNumber="";
        //find any 6 digit number
        Pattern mPattern = Pattern.compile("(|^)\\d{6}");

        if(message!=null) {
            Matcher mMatcher = mPattern.matcher(message);
            if(mMatcher.find()) {
                otpNumber = mMatcher.group(0);
            }else {
                //something went wrong

            }
        }

        return otpNumber;
    }

    /**
     * Http HEAD Method to get URL content type
     *
     * @param urlString
     * @return content type
     * @throws IOException
     */
    public static String getContentType(String urlString) throws IOException{
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        if (isRedirect(connection.getResponseCode())) {
            String newUrl = connection.getHeaderField("Location"); // get redirect url from "location" header field
            //logger.warn("Original request URL: '{}' redirected to: '{}'", urlString, newUrl);
            return getContentType(newUrl);
        }
        String contentType = connection.getContentType();
        return contentType;
    }

    /**
     * Check status code for redirects
     *
     * @param statusCode
     * @return true if matched redirect group
     */
    protected static boolean isRedirect(int statusCode) {
        if (statusCode != HttpURLConnection.HTTP_OK) {
            if (statusCode == HttpURLConnection.HTTP_MOVED_TEMP
                    || statusCode == HttpURLConnection.HTTP_MOVED_PERM
                    || statusCode == HttpURLConnection.HTTP_SEE_OTHER) {
                return true;
            }
        }
        return false;
    }

//    public static File takeScreenShot(Context context,View view){
//
//        Date now = new Date();
//        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
//
//        try {
//            // image naming and path  to include sd card  appending name you choose for file
//            String mPath = FileUtils.getBaseStorage2(context).toString() + "/" + now + ".jpg";
//
//            // create bitmap screen capture
//            View v1 = view;
//            v1.setDrawingCacheEnabled(true);
//            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
//            v1.setDrawingCacheEnabled(false);
//
//            File imageFile = new File(FileUtils.getBaseStorage2(context), "UMANG/BBPS/" +  now + ".jpg");
//
//            FileOutputStream outputStream = new FileOutputStream(imageFile);
//            int quality = 100;
//            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
//            outputStream.flush();
//            outputStream.close();
//
//            return imageFile;
//            //openScreenshot(context,imageFile);
//        } catch (Throwable e) {
//            // Several error may come out with file handling or DOM
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static void openScreenshot(Context context,File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        context.startActivity(intent);
    }


    public static void openFile(Context context, Uri url) throws IOException {
        // Create URI
        Uri uri = url;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if(url.toString().contains(".pdf") || url.toString().contains("_pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if(url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if(url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if(url.toString().contains(".zip") || url.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if(url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if(url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if(url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if(url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if(url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if(url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static final Spannable getColoredString(Context context, CharSequence text, int color) {
        Spannable spannable = new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(color), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public static Uri imageBase64ToUri(Context context, String imageBase64String) {
        try {
            String[] split = imageBase64String.split(",");
            byte[] decodedBytes = Base64.decode(split[1], Base64.DEFAULT);

            if (decodedBytes != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    String mime = imageBase64String.substring(0, imageBase64String.indexOf(',')).split(":")[1].split(";")[0];

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis() + ".png");
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mime);
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                    Uri uri = context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);

                    if (uri != null) {
                        try (OutputStream outputStream = context.getContentResolver().openOutputStream(uri)) {
                            if (outputStream != null) {
                                outputStream.write(decodedBytes);
                            }
                        }
                        return uri;
                    } else {
                        return null;
                    }
                } else {
                    File directory = Environment.getExternalStorageDirectory();
                    File umangFolder = new File(directory, "UMANG");
                    boolean directoryCreated = umangFolder.exists() || umangFolder.mkdirs();

                    if (directoryCreated) {
                        File file = new File(umangFolder, System.currentTimeMillis() + ".png");

                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            fos.write(decodedBytes);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                        return Uri.fromFile(file);
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void shareImageViaShareIntent(Context context, Uri uri, String shareText) {
        if (uri == null) {
            return;
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        shareIntent.setType("image/png");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        context.startActivity(Intent.createChooser(shareIntent, "Choose App"));
    }

}
