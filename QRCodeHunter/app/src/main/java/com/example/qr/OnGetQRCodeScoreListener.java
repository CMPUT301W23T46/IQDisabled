package com.example.qr;
/**
 * An interface used to handle callbacks for Firestore database operations that retrieve a QR code's score.
 */
public interface OnGetQRCodeScoreListener {
    /**
     * Called when a Firestore database operation to retrieve a QR code's score is completed successfully.
     * This method retrieves an integer representing the score associated with the QR code.
     *
     * @param score an integer representing the score associated with the QR code
     */
    void onSuccess(Integer score);
}
