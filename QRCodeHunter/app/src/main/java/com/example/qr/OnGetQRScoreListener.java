package com.example.qr;

import java.util.List;
/**
 * An interface used to handle callbacks for Firestore database operations that retrieve a QR code's scores.
 */
public interface OnGetQRScoreListener {
    /**
     * Called when a Firestore database operation to retrieve a QR code's scores is completed successfully.
     * This method retrieves a List of strings representing the scores associated with the QR code.
     *
     * @param qrScore a List of strings representing the scores associated with the QR code
     */
    void success(List<String> qrScore);
    /**
     * Called when a Firestore database operation to retrieve a QR code's scores fails.
     *
     * @param e an Exception object representing the error that occurred during the database operation
     */
    void failure(Exception e);
}
