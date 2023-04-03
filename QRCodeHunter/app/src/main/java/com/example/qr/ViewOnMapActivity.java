package com.example.qr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.content.pm.PackageManager;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
/**
 * ViewOnMapActivity is an activity that displays a map with markers
 * representing QR codes with their geolocations. This activity implements
 * OnMapReadyCallback and GoogleMap.OnMapClickListener interfaces to handle
 * map interactions.
 */
public class ViewOnMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    DataBaseHelper dbHelper = new DataBaseHelper();
    private GoogleMap mMap;
    private FirebaseFirestore db;
    /**
     * Called when the activity is starting. Initializes the activity, sets the
     * content view, and loads the map asynchronously.
     *
     * @param savedInstanceState a Bundle containing the data most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        String qr_name = extras.getString("qrName");
        String latitude = extras.getString("qrLa");
        String longitude = extras.getString("qrLo");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        db = FirebaseFirestore.getInstance();
        dbHelper = new DataBaseHelper();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    /**
     * Called when the map is ready to be used. Sets the initial camera position,
     * adds markers for the QR codes with their geolocations, and sets up map click
     * listener.
     *
     * @param googleMap a non-null instance of a GoogleMap associated with the MapFragment or MapView that defines the callback
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);

        Bundle extras = getIntent().getExtras();
        String qr_name = extras.getString("qrName");
        String latitude = extras.getString("qrLa");
        String longitude = extras.getString("qrLo");

        dbHelper.getGeoByName(qr_name, new OnGetGeoListener() {
            @Override
            public void success(List<String> qrGeo) {

                double latitude = Double.parseDouble(qrGeo.get(0));
                double longitude = Double.parseDouble(qrGeo.get(1));


                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(latitude, longitude));
                Marker marker = mMap.addMarker(markerOptions);
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                marker.setAlpha(0.8f);
                marker.setZIndex(1.0f);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16));

                dbHelper.getAllQRCode(new OnGetAllQRCodeListener() {
                    @Override
                    public void onSuccess(String[] hashcodes) {
                        for (String hash : hashcodes) {
                            dbHelper.getQRCodeGeolocations(hash, new OnGetQRCodeGeolocations() {
                                @Override
                                public void onSuccess(HashMap<String, Double> geo) {
                                    // Use the retrieved geolocation data
                                    double latitude = geo.get("latitude");
                                    double longitude = geo.get("longitude");
                                    System.out.println(latitude + "/" + longitude);
//                            LatLng location = new LatLng(latitude, longitude);
//                            Toast.makeText(MapActivity.this, String.valueOf(longitude), Toast.LENGTH_SHORT).show();
                                    MarkerOptions markerOptions = new MarkerOptions()
                                            .position(new LatLng(latitude, longitude));
                                    Marker marker = mMap.addMarker(markerOptions);
                                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                    marker.setAlpha(0.8f);
                                    marker.setZIndex(1.0f);

                                }
                            });
                        }
                    }
                });

            }

            @Override
            public void failure(Exception e) {
            }
        });
    }
    /**
     * Called when the user clicks on the map. Adds a marker at the clicked position
     * and moves the camera to the clicked position with a zoom level of 16.
     *
     * @param latLng a LatLng object containing the position that was clicked
     */
    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng);
        Marker marker = mMap.addMarker(markerOptions);
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        marker.setAlpha(0.8f);
        marker.setZIndex(1.0f);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    }
}
