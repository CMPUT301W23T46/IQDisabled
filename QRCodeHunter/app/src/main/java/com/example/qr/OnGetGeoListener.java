package com.example.qr;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;
/**
 * This interface represents a listener for getting geolocation operation.
 * It provides two methods to handle the success and failure of the operation.
 */
public interface OnGetGeoListener {
    /**
     * This method is called when the get geolocation operation is successful.
     *
     * @param geoPoint a list of String objects containing the geolocation coordinates
     */
    void success(List<String> geoPoint);
    /**
     * This method is called when the get geolocation operation fails due to an exception.
     *
     * @param e the exception that caused the failure of the operation
     */
    void failure(Exception e);
}
