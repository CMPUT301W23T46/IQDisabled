package com.example.qr;

/**
 * A callback interface for when a check for the existence of a QR code has been performed.
 */
public interface OnCheckQRCodeExistListener {
        /**
         * Called when a check for the existence of a QR code has been successfully performed.
         *
         * @param result a boolean value indicating whether the QR code exists or not
         */
        void onSuccess(boolean result);
}