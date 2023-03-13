package com.example.qr;

/**
 * A callback interface for when a QR code's length has been determined.
 */
public interface OnQRCodeLengthComplete {
    /**
     * Called when the length of a QR code has been successfully determined.
     * @param length the length of the QR code
     */
    void onSuccess(int length);
}