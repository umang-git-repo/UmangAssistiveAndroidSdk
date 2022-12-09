package com.negd.umangwebview.data.api;
import com.negd.umangwebview.data.model.biomodel.DeviceListResponse;
import com.negd.umangwebview.data.model.biomodel.RdDeviceRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public  interface APIInterface {

//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/json",
//            "Authorization: Bearer f410bcb9-c874-32ab-b502-7b01561e9b2f"
//    })
//    @POST("coreApi/2.0/openbiolist")
//    Call<RDResponse> getDeviceList(@Body RdDeviceRequest deviceRequest);



    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "x-api-key: VKE9PnbY5k1ZYapR5PyYQ33I26sXTX569Ed7eqyg"
    })
    @POST("coreapi/2.0/openbiolist")
    Call<DeviceListResponse> getDeviceList(@Body RdDeviceRequest deviceRequest);
}
