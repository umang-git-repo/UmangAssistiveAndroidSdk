package com.negd.umangwebview.data.model.jp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppData implements Parcelable {

    @SerializedName("name")
    @Expose
    private String appName;

    @SerializedName("pkgName")
    @Expose
    private String pkgName;

    protected AppData(Parcel in) {
        appName = in.readString();
        pkgName = in.readString();
    }

    public static final Creator<AppData> CREATOR = new Creator<AppData>() {
        @Override
        public AppData createFromParcel(Parcel in) {
            return new AppData(in);
        }

        @Override
        public AppData[] newArray(int size) {
            return new AppData[size];
        }
    };

    public String getAppName() {
        return appName;
    }

    public String getPkgName() {
        return pkgName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(appName);
        parcel.writeString(pkgName);
    }
}
