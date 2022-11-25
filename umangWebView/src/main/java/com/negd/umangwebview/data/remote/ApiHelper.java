package com.negd.umangwebview.data.remote;


import com.negd.umangwebview.data.model.jp.DeviceListRequest;

import io.reactivex.Single;

public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<String> doGetDeviceList(DeviceListRequest request);

}
