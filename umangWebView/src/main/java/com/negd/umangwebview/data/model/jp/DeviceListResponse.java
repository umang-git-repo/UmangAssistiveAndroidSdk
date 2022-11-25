package com.negd.umangwebview.data.model.jp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceListResponse {

    @SerializedName("rs")
    @Expose
    private String rs;

    @SerializedName("rc")
    @Expose
    private String rc;

    @SerializedName("rd")
    @Expose
    private String rd;

    @SerializedName("pd")
    @Expose
    private DeviceList pd;

    public String getRs() {
        return rs;
    }

    public String getRc() {
        return rc;
    }

    public String getRd() {
        return rd;
    }

    public DeviceList getPd() {
        return pd;
    }
}
