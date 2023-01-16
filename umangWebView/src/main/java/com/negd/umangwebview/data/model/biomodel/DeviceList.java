package com.negd.umangwebview.data.model.biomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeviceList {

    @SerializedName("deviceList")
    @Expose
    private List<DeviceData> deviceList;

    public List<DeviceData> getDeviceList() {
        return deviceList;
    }
}
