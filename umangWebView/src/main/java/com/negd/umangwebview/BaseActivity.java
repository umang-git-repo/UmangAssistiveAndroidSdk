package com.negd.umangwebview;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import com.androidnetworking.error.ANError;
import com.negd.umangwebview.ui.CustomDialog;
import com.negd.umangwebview.utils.CommonUtils;
import com.negd.umangwebview.utils.NetworkUtils;
import javax.inject.Inject;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;


public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
        extends AppCompatActivity implements BaseFragment.Callback,
        CustomDialog.DialogButtonClickListener {

    private static final String TAG = "BaseActivity";
    private ProgressDialog mProgressDialog;
    private T mViewDataBinding;
    private V mViewModel;
//    private ApiBearerListener apiBearerListener;
//    private ApiDigiBearerListener apiDigiBearerListener;
    private CustomDialog customDialog;
    private AppCompatActivity tempActForDigi;

    public void setBaseConfigListener(BaseConfigListener baseConfigListener) {
        this.baseConfigListener = baseConfigListener;
    }

    private BaseConfigListener baseConfigListener;

//    protected FusedLocationProviderClient mFusedLocationClient;
    public static Location baseLocation=null;

//    public FirebaseRemoteConfig remoteConfig=FirebaseRemoteConfig.getInstance();
//
    private int updateType=0;
//    public AppUpdateManager appUpdateManager;
//    public InstallStateUpdatedListener updatedListener;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public static Context sContext;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (true) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                 // We want to change tint color to white again.
                 // You can also record the flags in advance so that you can turn UI back completely if
                 // you have set other flags before, such as translucent or full screen.
                decor.setSystemUiVisibility(0);
            }
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = getResources().getDrawable(R.drawable.home_gradient);
        getWindow().setBackgroundDrawable(background);


        performDependencyInjection();
        super.onCreate(savedInstanceState);
        performDataBinding();


        getViewModel().context=this;
        sContext=BaseActivity.this;

        //fused location client
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getViewModel().setContext(this);


        //firebase remote config default values
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .build();
//
//        remoteConfig.setConfigSettingsAsync(configSettings);
//
//
//        remoteConfig.setDefaultsAsync(R.xml.remote_config);



//        fetchRemoteConfig();

        //init update manager
//        appUpdateManager= AppUpdateManagerFactory.create(this);

        //app update listener
//        updatedListener=new InstallStateUpdatedListener() {
//            @Override
//            public void onStateUpdate(InstallState state) {
//                if(state.installStatus() == InstallStatus.DOWNLOADED){
//
//                    //unregister update listener
//                    if(appUpdateManager !=null)
//                        appUpdateManager.unregisterListener(updatedListener);
//
//                    //show user an update
//                    CommonUtils.showInfoDialogUpdate(BaseActivity.this,getString(R.string.update_done),BaseActivity.this,appUpdateManager);
//
//                }else if(state.installStatus() == InstallStatus.INSTALLED){
//                    //unregister update listener
//                    if(appUpdateManager !=null)
//                        appUpdateManager.unregisterListener(updatedListener);
//                }
//            }
//        };
    }

//    public void fetchRemoteConfig(){
//
//        remoteConfig.fetch(0).addOnCompleteListener(new OnCompleteListener<Void>() {
//
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                //showToast(getString(R.string.no));
//                if(task.isSuccessful()){
//                    remoteConfig.fetchAndActivate();
//
////                    if(baseConfigListener!=null)
////                        baseConfigListener.onRemoteConfigFetch();
//                }else{
//
////                    if(baseConfigListener!=null)
////                        baseConfigListener.onRemoteConfigFetch();
//                }
//            }
//        });
//
//        final Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(baseConfigListener!=null) {
//                    baseConfigListener.onRemoteConfigFetch();
//                }
//            }
//        }, 3000);
//    }



    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }


//    @Override
//    public void onOkClick(String type){
//        switch (type){
//            case "DGL0016":
//                Intent intent=new Intent(tempActForDigi,DigiLockerMainActivity.class);
//                tempActForDigi.startActivity(intent);
//                tempActForDigi.finish();
//                break;
//
//        }
//    }

//    @Override
//    public void onCancelClick(String type){
//
//    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {
        if(!isFinishing()){
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.cancel();
            }
        }

    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void openActivityOnTokenExpire() {
        //startActivity(LoginActivity.newIntent(this));
        finish();
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    //show loder
    public void showLoading() {
        try{
            hideLoading();
            if(!isFinishing())
                mProgressDialog = CommonUtils.showLoadingDialog(this);
        }catch (Exception ex){

        }

    }

    private void performDataBinding() {
        try{
            mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
            this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
            mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
            mViewDataBinding.executePendingBindings();
        }catch (Exception ex){

        }

    }

    public boolean hasPermissionsCheck(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    //handle API error
    public void handleApiError(ANError throwable){

        try{

            if(throwable.getErrorCode()==401){
//                mViewModel.doBearerRefresh(apiBearerListener);
            }else if(throwable.getErrorCode()==403){
                showSessionExpiredDialog(this);
            }else if(throwable.getErrorCode()==423 || throwable.getErrorCode()==408){
                showMaintenance();
            }else if(throwable.getErrorCode()==429){
                CommonUtils.showInfoDialog(this,"Dear User, System is experiencing heavy load currently, please try after sometime.");
            }else if(throwable.getErrorCode()==500 || throwable.getErrorCode()==0 || throwable.getErrorCode()==400 ){
               // showToast(getString(R.string.something_went_wrong));
                //CommonUtils.showInfoDialog(this,getString(R.string.oops_message));
            }
            hideLoading();
        }catch (Exception ex){
            //showToast(getString(R.string.please_try_again));
        }

    }

    /**
     * Method to handle Digilocker API error
     * @param throwable
     */
    public void handleDigiLockerApiError(ANError throwable,String type){

        try{
            if(throwable.getErrorCode()==401){
//                mViewModel.doDigiBearerRefresh(apiDigiBearerListener,type);
            }else if(throwable.getErrorCode()==403){
                showSessionExpiredDialog(this);
            }else if(throwable.getErrorCode()==423){
                showMaintenance();
            }else{
                showToast(getString(R.string.oops_message));
            }
        }catch (Exception ex){
            showToast(getString(R.string.oops_message));
        }
    }

    /**
     * Method to handle success error code for Digilocker
     * @param responseCode DGL0016 for unlinked from digilocker
     */
    public void handleDigiLockerResponse(String responseCode,String msg,AppCompatActivity activity){
        if(responseCode.equalsIgnoreCase("DGL0016") || responseCode.equalsIgnoreCase("DGL0116") || responseCode.equalsIgnoreCase("DGL1114")){
            CommonUtils.showInfoDialog(this, msg);
        }else if(responseCode.equalsIgnoreCase("F")){
            showToast(getString(R.string.please_try_again));
        }
        else{
            showToast(getString(R.string.please_try_again));
            //CommonUtils.showInfoDialog(this, msg);
        }
    }

//
//    public void setApiBearerListener(ApiBearerListener apiBearerListener){
//        this.apiBearerListener=apiBearerListener;
//    }
//
//    public void setApiDigiBearerListener(ApiDigiBearerListener listener){
//        this.apiDigiBearerListener=listener;
//    }

//    public FirebaseRemoteConfig getRemoteConfig() {
//        return remoteConfig;
//    }

//    public interface ApiBearerListener{
//        void onBearerRefresh(String bearer);
//    }

//    public interface ApiDigiBearerListener{
//        void onDigiBearerRefresh(String type);
//    }

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

//    @Override
//    public AndroidInjector<Fragment> supportFragmentInjector() {
//        return fragmentDispatchingAndroidInjector;
//    }

    public void showSessionExpiredDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        if(!dialog.isShowing()) {
            dialog.show();
        }
        AppCompatTextView dialogTxt = dialog.findViewById(R.id.dialogTxt);

        if(UmangApplication.isMobileNumberChange){
            UmangApplication.isMobileNumberChange=false;

            dialogTxt.setText(context.getString(R.string.session_loggedout_msg));
        }else{
            dialogTxt.setText(context.getString(R.string.session_expire_msg));
        }

        Button btnOK = dialog.findViewById(R.id.btnOk);
        btnOK.setOnClickListener(view -> {
            if (dialog.isShowing()) {
                logoutUser();
            }
        });
    }

    public void logoutUser(){

//        //delete all tables
//        Thread thread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getViewModel().getDataManager().deleteAllData();
//            }
//        });
//        thread.start();

        //logout profile
//        getViewModel().logoutProfile();

        //delete all tables
//        getViewModel().deleteDbData();

        //delete digilocker docs
//        deleteDigiDocs();

        //delete all preference values
//        getViewModel().getDataManager().deleteAllPreference();

        //clear cookies
        clearCookies(this);

        //delete web storage
//        WebStorage webStorage = WebStorage.getInstance();
//
//        webStorage.deleteAllData();


        //logout social media
//        try{
//            logoutFacebook();
//            logoutGoogle();
//            logoutTwitter();
//
//        }catch (Exception ex){
//
//        }

        //do nps logout
//        deleteNpsData();
//
//        //delete epfo data
//        deleteEpfoData();


        //clear all notifications
        try {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancelAll();
        } catch (Exception e) {
//            AppLogger.e("error",e);
        }



        finish();
//        Intent i = null;
//        if(UmangApplication.isAccountDelete){
//            UmangApplication.isAccountDelete=false;
//            i=new Intent(this, SplashActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(i);
//        }else {
//            i=new Intent(this, PreLoginActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(i);
//        }


    }

//    private void deleteDigiDocs(){
//        try{
//
//            //storage directory for save
//            File storageDir = FileUtils.getBaseStorage2(this);
//
//            //User id to distinguish
//            String userid=getViewModel().getDataManager().getStringPreference(AppPreferencesHelper.PREF_USER_ID,"");
//
//
//            //create umang directory for download if not exist
//            File umangFolder = new File(storageDir, "UMANG/Digilocker/"+ DeviceUtils.getMD5(userid)+"/");
//
//            //loop in folder
//            if(umangFolder.exists()) {
//
//                for (File f : umangFolder.listFiles()) {
//                    if (f.isFile()) {
//                        f.delete();
//                    }
//
//                }
//            }
//
//        }catch (Exception ex){
//            Log.e("Error,", "Error  in digi docs delete");
//        }
//    }

    @SuppressWarnings("deprecation")
    public static void clearCookies(Context context)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else
        {
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }


    private void showMaintenance(){
//        startActivity(new Intent(this, UnderMaintenanceActivity.class));
        showToast("somthing went wrong!");
    }

//    private void deleteNpsData(){
//
//        //first check for NPS data
//        String deptKeyValues = getViewModel().getDataManager().getEncryptedStringPreference(AppPreferencesHelper.PREF_WEB_KEY_VALUE,"");
//
//        try {
//            JSONObject deptKeyValuesObj = new JSONObject(deptKeyValues);
//
//            if (deptKeyValuesObj.optJSONObject("npsData") != null)
//            {
//                getViewModel().doNpsLogOut(deptKeyValues);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void deleteEpfoData(){
//        //first check for EPFO data
//        String epfoData = getViewModel().getDataManager().getEncryptedStringPreference(AppPreferencesHelper.PREF_WEB_KEY_VALUE,"");
//
//        try {
//            JSONObject deptKeyValuesObj = new JSONObject(epfoData);
//
//            if (deptKeyValuesObj.optJSONObject("epfoData") != null)
//            {
//                getViewModel().epfoLogout(epfoData);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void logoutFacebook(){
//        try {
//            AccessToken.setCurrentAccessToken(null);
//            if (LoginManager.getInstance() != null) {
//                LoginManager.getInstance().logOut();
//            }
//        } catch (Exception ex) {
//            Log.e("fb logut","error");
//        }
//    }
//
//    private void logoutGoogle(){
//        try {
//
//            //google client
//            GoogleSignInClient mGoogleSignInClient;
//
//            //google sign-in init
//            // Configure sign-in to request the user's ID, email address, and basic
//            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestEmail()
//                    .build();
//
//            // Build a GoogleSignInClient with the options specified by gso.
//            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//            mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//
//                }
//            });
//
//        } catch (Exception ex) {
//            Log.e("google logut","error");
//        }
//    }
//
//    private void logoutTwitter(){
//        try{
//
//            TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
//            if (twitterSession != null) {
//                ClearCookies(this);
//
//                TwitterSession twitterSessionLG = TwitterCore.getInstance().getSessionManager().getActiveSession();
//                if (twitterSession != null) {
//                    ClearCookies(getApplicationContext());
//                    TwitterCore.getInstance().getSessionManager().clearActiveSession();
//
//                }
//            }
//        }catch (Exception ex){
//            Log.e("twitter logut","error");
//        }
//    }

    public static void ClearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoading();
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(LocaleHelper.onAttach(base));
//
//    }
//
//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            getWindow().getDecorView().setLayoutDirection(
//                    "ur".equals(LocaleHelper.getLanguage(this)) ?
//                            View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
//        }
//    }

//    public void getAndSaveLocation(){
//        mFusedLocationClient.getLastLocation().addOnCompleteListener(
//                new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//
//                        try{
//
//                            Location location = task.getResult();
//                            if (location == null) {
//                                requestNewLocationData();
//                            } else {
//                                baseLocation=location;
//                                getViewModel().getDataManager().writeStringPreference(AppPreferencesHelper.PREF_USER_LAT,""+location.getLatitude());
//                                getViewModel().getDataManager().writeStringPreference(AppPreferencesHelper.PREF_USER_LON,""+location.getLongitude());
//                            }
//
//                        }catch (Exception ex){
//
//                        }
//
//                    }
//                }
//        );
//    }

    @SuppressLint("MissingPermission")
//    private void requestNewLocationData(){
//
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(0);
//        mLocationRequest.setFastestInterval(0);
//        mLocationRequest.setNumUpdates(1);
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        mFusedLocationClient.requestLocationUpdates(
//                mLocationRequest, mLocationCallback,
//                Looper.myLooper()
//        );
//
//    }
//
//    private LocationCallback mLocationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            Location mLastLocation = locationResult.getLastLocation();
//            baseLocation= mLastLocation;
//            getViewModel().getDataManager().writeStringPreference(AppPreferencesHelper.PREF_USER_LAT,""+mLastLocation.getLatitude());
//            getViewModel().getDataManager().writeStringPreference(AppPreferencesHelper.PREF_USER_LON,""+mLastLocation.getLongitude());
//        }
//    };


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public interface BaseConfigListener{

        void onRemoteConfigFetch();
    }


//    public void checkForUpdate(){
//
//        try{
//
//            // Returns an intent object that you use to check for an update.
//            com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
//
//
//            updateType=(int) remoteConfig.getLong("update_type")== 0 ? 0 : 1;
//
//            // Checks that the platform will allow the specified type of update.
//            appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
//                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                        && appUpdateInfo.isUpdateTypeAllowed( updateType ==0? AppUpdateType.FLEXIBLE :AppUpdateType.IMMEDIATE /*AppUpdateType.IMMEDIATE*/)){
//                    // Request the update.
//
//                    // Before starting an update, register a listener for updates.
//                    appUpdateManager.registerListener(updatedListener);
//
//                    try {
//                        appUpdateManager.startUpdateFlowForResult(
//                                // Pass the intent that is returned by 'getAppUpdateInfo()'.
//                                appUpdateInfo,
//                                // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
//                                updateType ==0? AppUpdateType.FLEXIBLE :AppUpdateType.IMMEDIATE,
//                                // The current activity making the update request.
//                                this,
//                                // Include a request code to later monitor this update request.
//                                APP_UPDATE_CODE);
//                    } catch (IntentSender.SendIntentException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }catch (Exception ex){
//            Log.e("error","error");
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == APP_UPDATE_CODE && resultCode != RESULT_OK){
//
//            // If the update is cancelled or fails, you can request to start the update again.
//            if(updateType == 1 && resultCode==RESULT_CANCELED){
//                checkForUpdate();
//            }
//
//        }
//    }
}
