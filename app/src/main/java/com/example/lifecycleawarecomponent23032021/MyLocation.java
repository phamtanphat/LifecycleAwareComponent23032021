package com.example.lifecycleawarecomponent23032021;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyLocation implements LifecycleObserver {
    Context mContext;
    OnListenLocation mOnListenLocation;
    LocationManager locationManager;

    public MyLocation(Context mContext, OnListenLocation mOnListenLocation) {
        this.mContext = mContext;
        this.mOnListenLocation = mOnListenLocation;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @SuppressLint("MissingPermission")
    public void startListenLocation() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener);
        Location gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (gps_loc != null){
            locationListener.onLocationChanged(gps_loc);
        }

    }



    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void stopGetLocation(){
        if (locationManager == null){
            return;
        }
        locationManager.removeUpdates(locationListener);
        Toast.makeText(mContext, "Location Update Pause", Toast.LENGTH_SHORT).show();
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (mOnListenLocation != null){
                mOnListenLocation.callbackLocation(location.getLatitude(),location.getLongitude());
            }
        }
        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };
}
