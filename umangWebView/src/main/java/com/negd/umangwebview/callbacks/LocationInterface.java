package com.negd.umangwebview.callbacks;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.webkit.JavascriptInterface;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.negd.umangwebview.R;
import com.negd.umangwebview.ui.UmangWebActivity;
import com.negd.umangwebview.utils.Constants;

public class LocationInterface {

    private UmangWebActivity activity;
    private String TAG = "LocationInterface";

    public LocationInterface(UmangWebActivity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public String getCurrentLocation() {
        String locationStr = "";
        return locationStr;
    }

    public static String locationResponse;

    @JavascriptInterface
    public void fetchLocation(String response) {
        locationResponse = response;
        checkLocationPermission();
    }

    @JavascriptInterface
    public void viewDirection(String address) {

        if (address == null || address.isEmpty()) {

        }
        Uri uri = Uri.parse(address);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(mapIntent);
        } else {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
                activity.startActivity(intent);
            } catch (Exception e) {
            }
        }

    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                activity.sendLocationCallBack("F", "", locationResponse);
                String TYPE="PERMISSION";
                activity.openDialog(activity.getResources().getString(R.string.location_services_disabled),
                        activity.getResources().getString(R.string.enable_location_txt),
                        activity.getResources().getString(R.string.ok),
                        activity.getResources().getString(R.string.cancel_caps),
                        TYPE);
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.MY_PERMISSIONS_LOCATION);
            }
        } else {
            //get location
            startLocationTask();
        }
    }

    public void startLocationTask() {
        //AppLogger.d(TAG, "startLocationTask===========================");
        activity.getLastLocation();
    }

}