package com.example.qr;

/**
 * A callback interface for when a list of QR code names has been retrieved.
 */
public interface OnGetQRCodeNamesListener {
    /**
     * Called when the list of QR code names has been successfully retrieved.
     * @param qrCodes an array of QRCode objects representing the retrieved QR codes
     */
    void onSuccess(QRCode[] qrCodes);
}