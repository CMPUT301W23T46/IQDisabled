package com.example.qr;

import java.util.HashMap;

public interface OnGetQRCodeGeolocations {
    void onSuccess(HashMap<String, Double> geo);
}
