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

public class SearchByCodeMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    DataBaseHelper dbHelper = new DataBaseHelper();
    private GoogleMap mMap;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        String la = extras.getString("latitude");
        String lo = extras.getString("longitude");

        String latitude = extras.getString("latitude");
        String longitude = extras.getString("longitude");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
//        db = FirebaseFirestore.getInstance();
//        dbHelper = new DataBaseHelper();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);

        Bundle extras = getIntent().getExtras();

        double la = extras.getDouble("latitude");
        double lo = extras.getDouble("longitude");




        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(la, lo), 16));
        DataBaseHelper dbHelper = new DataBaseHelper();

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
    public void onMapClick(@NonNull LatLng latLng) {
        //


    }
}
