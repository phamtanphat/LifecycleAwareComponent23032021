package com.example.lifecycleawarecomponent23032021;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyLocation implements LifecycleObserver {
    Context mContext;
    OnListenLocation mOnListenLocation;

    Location gps_loc;
    Location network_loc;
    Location final_loc;
    double longitudeNew;
    double latitudeNew;
    LocationManager locationManager;
    Handler mHandler;

    public MyLocation(Context mContext, OnListenLocation mOnListenLocation) {
        this.mContext = mContext;
        this.mOnListenLocation = mOnListenLocation;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mHandler = new Handler();
    }

    public void startListenLocation() {
        mHandler.postDelayed(runnableGetLocation, 1000);

    }

    @SuppressLint("MissingPermission")
    private Runnable runnableGetLocation = new Runnable() {
        @Override
        public void run() {
            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (gps_loc != null) {
                final_loc = gps_loc;
                latitudeNew = final_loc.getLatitude();
                longitudeNew = final_loc.getLongitude();
            } else if (network_loc != null) {
                final_loc = network_loc;
                latitudeNew = final_loc.getLatitude();
                longitudeNew = final_loc.getLongitude();
            } else {
                latitudeNew = 0.0;
                longitudeNew = 0.0;
            }
            mOnListenLocation.callbackLocation(latitudeNew,longitudeNew);
            mHandler.postDelayed(this,1000);
        }
    };

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void stopGetLocation(){
        Toast.makeText(mContext, "Stopped", Toast.LENGTH_SHORT).show();
        mHandler.removeCallbacks(runnableGetLocation);
    }
}
