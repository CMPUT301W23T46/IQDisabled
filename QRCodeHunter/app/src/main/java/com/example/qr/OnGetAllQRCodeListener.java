package com.example.qr;

/**
 * A callback interface for when all QR codes have been retrieved.
 */
public interface OnGetAllQRCodeListener {
    /**
     * Called when all QR codes have been successfully retrieved.
     *
     * @param hashcodes an array of String objects representing the hashcodes of all retrieved QR codes
     */
    void onSuccess(String[] hashcodes);
}