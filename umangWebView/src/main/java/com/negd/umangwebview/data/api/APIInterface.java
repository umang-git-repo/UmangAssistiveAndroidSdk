package com.negd.umangwebview.data.api;
import com.negd.umangwebview.data.model.biomodel.DeviceListResponse;
import com.negd.umangwebview.data.model.biomodel.RdDeviceRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public  interface APIInterface {
    //    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/json",
//            "x-api-key: VKE9PnbY5k1ZYapR5PyYQ33I26sXTX569Ed7eqyg"
//    })
//    @POST("coreapi/2.0/openbiolist")
//    @POST("https://apigw.umangapp.in/core-encv1/ws1/biolist")
//    Call<DeviceListResponse> getDeviceList(@Body RdDeviceRequest deviceRequest);
    @POST("core-encv1/ws1/biolist")
    Call<String> getDeviceList(@Header("X-REQUEST-VALUE") String requestValue,
                               @Body String deviceRequest);
}
