package com.example.qr;

/**
 * A callback interface for when a single QR code has been retrieved.
 */
public interface OnGetQRCodeListener {
    /**
     * Called when the QR code has been successfully retrieved.
     *
     * @param qrCode the QRCode object representing the retrieved QR code
     */
    void onSuccess(QRCode qrCode);
}