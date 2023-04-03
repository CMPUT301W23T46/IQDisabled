package com.example.qr;

import java.util.List;
/**
 * An interface used to handle callbacks for Firestore database operations that retrieve a QR code's comments.
 */
public interface OnGetQRCommentListener {/**
     * Called when a Firestore database operation to retrieve a QR code's comments is completed successfully.
     * This method retrieves a List of strings representing the comments associated with the QR code.
     *
     * @param qrCom a List of strings representing the comments associated with the QR code
     */
    void success(List<String> qrCom);
    /**
     * Called when a Firestore database operation to retrieve a QR code's comments fails.
     *
     * @param e an Exception object representing the error that occurred during the database operation
     */
    void failure(Exception e);
}
