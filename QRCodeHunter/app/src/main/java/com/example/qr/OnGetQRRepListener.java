package com.example.qr;

import java.util.List;
/**
 * An interface used to handle callbacks for Firestore database operations that retrieve a QR code's reports.
 */
public interface OnGetQRRepListener {
    /**
     * Called when a Firestore database operation to retrieve a QR code's reports is completed successfully.
     * This method retrieves a List of strings representing the reports associated with the QR code.
     *
     * @param qrRep a List of strings representing the reports associated with the QR code
     */
    void success(List<String> qrRep);
    /**
     * Called when a Firestore database operation to retrieve a QR code's reports fails.
     *
     * @param e an Exception object representing the error that occurred during the database operation
     */
    void failure(Exception e);
}
