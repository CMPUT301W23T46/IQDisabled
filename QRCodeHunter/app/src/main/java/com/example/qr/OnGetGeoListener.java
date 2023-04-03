package com.example.qr;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public interface OnGetGeoListener {
    void success(List<String> geoPoint);
    void failure(Exception e);
}
