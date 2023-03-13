package com.example.qr;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.Manifest;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * A class that fetches the user's current location using the Google Play Services API.
 * This class implements the GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
 * and com.google.android.gms.location.LocationListener interfaces to handle the callbacks for the
 * Google Play Services API.
 */
public class GeoLocationFetcher extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    /**
     * Initializes the GeoLocationFetcher object and sets up the GoogleApiClient and LocationRequest objects.
     *
     * @param savedInstanceState the saved instance state bundle to restore the activity state from
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of GoogleApiClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)  // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    }

    /**
     * Called when the Google Play Services API connection is successful.
     *
     * @param bundle the connection result bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            // Request location updates
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    /**
     * Called when the Google Play Services API connection is suspended.
     *
     * @param i the connection suspend cause
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * Called when the Google Play Services API connection fails.
     *
     * @param connectionResult the connection result indicating the reason for the failure
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * Called when the user's location changes.
     *
     * @param location the new location of the user
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Do something with the latitude and longitude values.
        Toast.makeText(this, "Latitude: " + latitude + "\nLongitude: " + longitude, Toast.LENGTH_SHORT);
    }
}
