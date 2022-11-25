package com.negd.umangwebview;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.error.ANError;
import com.negd.umangwebview.data.DataManager;
import com.negd.umangwebview.utils.rx.SchedulerProvider;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseViewModel<N> extends ViewModel {

    private final DataManager mDataManager;

    private final ObservableBoolean mIsLoading = new ObservableBoolean();

    private final SchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mNavigator;

    //private ApiBearerListener apiBearerListener;

    public Context context;

    public BaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }


    public void setContext(Context context){
        this.context=context;
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

//    public void doBearerRefresh(BaseActivity.ApiBearerListener apiBearerListener){
//        this.apiBearerListener=apiBearerListener;
//        CommonParams commonParams=CommonParams.getInstance(context,getDataManager());
//        getCompositeDisposable().add(getDataManager()
//                .doRefreshBearer(commonParams)
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(response -> {
//                    if(this.apiBearerListener != null)
//                        apiBearerListener.onBearerRefresh(response);
//                }, throwable -> {
//                        Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
//                        //doBearerRefresh(this.apiBearerListener);
//                }));
//    }
//
//    public void doDigiBearerRefresh(BaseActivity.ApiDigiBearerListener apiDigiBearerListener,String type){
//        CommonParams commonParams=CommonParams.getInstance(context,getDataManager());
//        getCompositeDisposable().add(getDataManager()
//                .doRefreshDigiBearer(commonParams)
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(response -> {
//                    BearerResponse authResponse = ResponseUtils.getNormalResponseClass(response, BearerResponse.class, context,0);
//                    if(apiDigiBearerListener != null){
//                        getDataManager().writeStringPreference(AppPreferencesHelper.PREF_BEARER_DIGI,authResponse.getmTokenType() + " " + authResponse.getmAccessToken());
//                        getDataManager().writeStringPreference(AppPreferencesHelper.PREF_BEARER_EXPIRY_DIGI,authResponse.getmExpiresin());
//                        apiDigiBearerListener.onDigiBearerRefresh(type);
//                    }
//
//                }, throwable -> {
//                    if(apiDigiBearerListener != null)
//                        apiDigiBearerListener.onDigiBearerRefresh("");
//                }));
//    }

    public void handleError(ANError error){
        context=BaseActivity.sContext;
        if(context !=null){
            try{
                ((BaseActivity)context).handleApiError(error);
            }catch (Exception ex){
                Toast.makeText(context,context.getString(R.string.please_try_again),Toast.LENGTH_LONG).show();
            }
        }
    }

//    public void deleteDbData(){
//        getCompositeDisposable().add(getDataManager()
//                .deleteAllData()
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(response -> {
//
//                }, throwable -> {
//                }));
//    }
//    //method to nps logout
//    public void doNpsLogOut(String npsKey){
//        try {
//
//            //language
//            String systemLang = context.getResources().getConfiguration().locale.toString();
//            String enLangCode = "en";
//            if (systemLang.contains("GB")) {
//                enLangCode = systemLang.substring(0, systemLang.length() - 3);
//            }
//
//            JSONObject jsonObject=new JSONObject(npsKey);
//            NpsLogoutRequest request=new NpsLogoutRequest();
//            request.setTrkr("" + System.currentTimeMillis());
//            request.setTkn(getDataManager().getStringPreference(AppPreferencesHelper.PREF_TOKEN,""));
//            request.setLac(DeviceUtils.getLAC(context));
//            request.setLat("21");
//            request.setLon("12");
//            request.setLang(enLangCode);
//            request.setImei(DeviceUtils.getDeviceImei(context));
//            request.setUsag("12");
//            request.setUsrid("12");
//            request.setSrvid("12");
//            request.setMode("android");
//            request.setPltfrm("app");
//            request.setDid("12");
//            request.setOs("Android");
//            request.setAcesstkn("");
//            request.setUserid(jsonObject.optJSONObject("npsData").optString("value"));
//            request.setMaver("4.0.1");
//            request.setMosver("6.0");
//            request.setUid(getDataManager().getStringPreference(AppPreferencesHelper.PREF_USER_ID,""));
//
//            getCompositeDisposable().add(getDataManager()
//                    .doNPSLogout(request)
//                    .subscribeOn(getSchedulerProvider().io())
//                    .observeOn(getSchedulerProvider().ui())
//                    .subscribe(response -> {
//                    }, throwable -> {
//                        AppLogger.e(throwable,"Error in doNpsLogOut...", "Error in doNpsLogOut");
//                        setIsLoading(false);
//                    }));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void epfoLogout(String epfoData){
//        try {
//
//            //language
//            String systemLang = context.getResources().getConfiguration().locale.toString();
//            String enLangCode = "en";
//            if (systemLang.contains("GB")) {
//                enLangCode = systemLang.substring(0, systemLang.length() - 3);
//            }
//
//            JSONObject jsonObject=new JSONObject(epfoData);
//            NpsLogoutRequest request=new NpsLogoutRequest();
//            request.setTrkr("" + System.currentTimeMillis());
//            request.setTkn(getDataManager().getStringPreference(AppPreferencesHelper.PREF_TOKEN,""));
//            request.setLac(DeviceUtils.getLAC(context));
//            request.setLat("");
//            request.setLon("");
//            request.setLang(enLangCode);
//            request.setUsag("12");
//            request.setUsrid("12");
//            request.setMode("android");
//            request.setPltfrm("app");
//            request.setDid("12");
//            request.setOs("Android");
//            request.setUan(jsonObject.optJSONObject("epfoData").optJSONObject("value").optString("uid"));
//
//            getCompositeDisposable().add(getDataManager()
//                    .doEPFOLogout(request)
//                    .subscribeOn(getSchedulerProvider().io())
//                    .observeOn(getSchedulerProvider().ui())
//                    .subscribe(response -> {
//                    }, throwable -> {
//                        AppLogger.e(throwable,"Error in epfoLogout...", "Error in epfoLogout");
//                        setIsLoading(false);
//                    }));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void logoutProfile(){
//        CommonParams commonParams = CommonParams.getInstance(context, getDataManager());
//        commonParams.init(context,getDataManager());
//
//        getCompositeDisposable().add(getDataManager()
//                .doLogout(commonParams)
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(response -> {
//
//                }, throwable -> {
//                }));
//    }
}
