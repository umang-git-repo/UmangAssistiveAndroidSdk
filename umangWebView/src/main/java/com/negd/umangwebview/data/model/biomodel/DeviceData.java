package com.negd.umangwebview.data.model.biomodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeviceData implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("img")
    @Expose
    private String img;

    @SerializedName("make")
    @Expose
    private String make;

    @SerializedName("appHeading")
    @Expose
    private String appHeading;

    @SerializedName("appDesc")
    @Expose
    private String appDesc;

    @SerializedName("appList")
    @Expose
    private List<AppData> appList;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("regDevHeading")
    @Expose
    private String regDevHeading;

    @SerializedName("regDevDesc")
    @Expose
    private String regDevDesc;

    @SerializedName("helpHeading")
    @Expose
    private String helpHeading;

    @SerializedName("helpDesc")
    @Expose
    private String helpDesc;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("userManual")
    @Expose
    private String userManual;

    public DeviceData(){

    }

    protected DeviceData(Parcel in) {
        name = in.readString();
        img = in.readString();
        make = in.readString();
        appHeading = in.readString();
        appDesc = in.readString();
        appList=in.createTypedArrayList(AppData.CREATOR);
        note = in.readString();
        regDevHeading = in.readString();
        regDevDesc = in.readString();
        helpHeading = in.readString();
        helpDesc = in.readString();
        email = in.readString();
        phone = in.readString();
        userManual = in.readString();

    }

    public static final Creator<DeviceData> CREATOR = new Creator<DeviceData>() {
        @Override
        public DeviceData createFromParcel(Parcel in) {
            return new DeviceData(in);
        }

        @Override
        public DeviceData[] newArray(int size) {
            return new DeviceData[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getMake() {
        return make;
    }

    public String getAppHeading() {
        return appHeading;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public List<AppData> getAppList() {
        return appList;
    }

    public String getNote() {
        return note;
    }

    public String getRegDevHeading() {
        return regDevHeading;
    }

    public String getRegDevDesc() {
        return regDevDesc;
    }

    public String getHelpHeading() {
        return helpHeading;
    }

    public String getHelpDesc() {
        return helpDesc;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserManual() {
        return userManual;
    }

    public void setUserManual(String userManual) {
        this.userManual = userManual;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(img);
        parcel.writeString(make);
        parcel.writeString(appHeading);
        parcel.writeString(appDesc);
        parcel.writeTypedList(appList);
        parcel.writeString(note);
        parcel.writeString(regDevHeading);
        parcel.writeString(regDevDesc);
        parcel.writeString(helpHeading);
        parcel.writeString(helpDesc);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(userManual);
    }

    public void setAppList(List<AppData> appList) {
        this.appList = appList;
    }
}
