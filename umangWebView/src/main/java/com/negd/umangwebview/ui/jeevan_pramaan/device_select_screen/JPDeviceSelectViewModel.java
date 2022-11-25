package com.negd.umangwebview.ui.jeevan_pramaan.device_select_screen;

import android.content.Context;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.androidnetworking.error.ANError;
import com.negd.umangwebview.BaseViewModel;
import com.negd.umangwebview.R;
import com.negd.umangwebview.data.DataManager;
import com.negd.umangwebview.data.model.jp.DeviceData;
import com.negd.umangwebview.data.model.jp.DeviceListRequest;
import com.negd.umangwebview.data.model.jp.DeviceListResponse;
import com.negd.umangwebview.utils.EncryptionDecryptionUtils;
import com.negd.umangwebview.utils.ResponseUtils;
import com.negd.umangwebview.utils.rx.SchedulerProvider;

import java.util.List;


public class JPDeviceSelectViewModel extends BaseViewModel<IJPDeviceSelectNavigator> {

    public final ObservableList<DeviceData> observableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<DeviceData>> mListMutableLiveData;

    public JPDeviceSelectViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mListMutableLiveData = new MutableLiveData<>();
    }

    public void addData(List<DeviceData> list) {
        observableArrayList.clear();
        observableArrayList.addAll(list);
    }

    public MutableLiveData<List<DeviceData>> getListMutableLiveData() {
        return mListMutableLiveData;
    }

    public ObservableList<DeviceData> getObservableArrayList() {
        return observableArrayList;
    }

    public void getDeviceList(Context context) {

        DeviceListRequest request = new DeviceListRequest();
        request.init(context, getDataManager());

        //Log.d("requestIs", request.toString());

        getCompositeDisposable().add(getDataManager()
                .doGetDeviceList(request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    DeviceListResponse deviceListResponse = ResponseUtils.getNormalResponseClass(response, DeviceListResponse.class, context, EncryptionDecryptionUtils.NORMAL);
                    if (null != deviceListResponse) {
                        if (deviceListResponse.getRc().equalsIgnoreCase("API0082")) {
                            for (int i = 0; i < deviceListResponse.getPd().getDeviceList().size(); i++) {
                                if (deviceListResponse.getPd().getDeviceList().get(i).getName().equalsIgnoreCase("Morpho"))
                                    deviceListResponse.getPd().getDeviceList().get(i).setUserManual("https://cdn.shopify.com/s/files/1/2363/5731/files/MorhpoRDServiceUserManual-Android_1673f8a3-16b2-4a45-a909-df4e2d1b7b80.pdf?7652205127149753351");
                            }
                            if (deviceListResponse.getPd().getDeviceList().size() > 0) {
                                DeviceData deviceData = new DeviceData();
                                deviceData.setName(context.getString(R.string.other_devices));
                                deviceData.setImg("OTHER_DEVICE");
                                deviceListResponse.getPd().getDeviceList().add(deviceData);
                            }
                            mListMutableLiveData.setValue(deviceListResponse.getPd().getDeviceList());
                            getNavigator().onHideLoader();
                        } else {
                            getNavigator().onHideLoader();
                        }
                    }
                }, throwable -> {
                    try {
                        handleError((ANError) throwable);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }));
    }
}
