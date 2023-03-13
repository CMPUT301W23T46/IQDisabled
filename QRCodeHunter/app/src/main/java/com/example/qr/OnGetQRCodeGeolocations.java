package com.example.qr;

import java.util.HashMap;

/**
 * A callback interface for when geolocations for a QR code have been retrieved.
 */
public interface OnGetQRCodeGeolocations {
    /**
     * Called when the geolocations for a QR code have been successfully retrieved.
     * @param geo a HashMap of String keys and Double values representing the geolocations for the QR code
     */
    void onSuccess(HashMap<String, Double> geo);
}