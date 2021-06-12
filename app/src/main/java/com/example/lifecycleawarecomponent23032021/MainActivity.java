package com.example.lifecycleawarecomponent23032021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTvLocation;
    MyLocation mMyLocation;
    int REQUEST_CODE_LOCATION = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvLocation = findViewById(R.id.textViewLocation);

        mTvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION
                    );
                }else{
                    setUpListenLocation();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                setUpListenLocation();
            }
        }
    }

    private void setUpListenLocation(){
        mMyLocation = new MyLocation(MainActivity.this, new OnListenLocation() {
            @Override
            public void callbackLocation(double lat, double lon) {
                if (lat != 0 && lon != 0){
                    mTvLocation.setText("Latitude " + lat + ", Longitude " + lon);
                }
            }
        });

        getLifecycle().addObserver(mMyLocation);
    }
}