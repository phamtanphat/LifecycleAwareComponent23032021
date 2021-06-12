package com.example.lifecycleawarecomponent23032021;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler.Callback;

import androidx.core.app.ActivityCompat;

public class MyLocation {
    Context mContext;
    OnListenLocation mOnListenLocation;

    Location gps_loc;
    Location network_loc;
    Location final_loc;
    double longitude;
    double latitude;
    LocationManager locationManager;


    public MyLocation(Context mContext, OnListenLocation mOnListenLocation) {
        this.mContext = mContext;
        this.mOnListenLocation = mOnListenLocation;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startListenLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

    }
}
