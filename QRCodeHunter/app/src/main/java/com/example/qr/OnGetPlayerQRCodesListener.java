package com.example.qr;

import java.util.List;
/**
 * An interface used to handle callbacks for Firestore database operations that retrieve a player's QR codes.
 */
public interface OnGetPlayerQRCodesListener {
    /**
     * Called when a Firestore database operation to retrieve a player's QR codes is completed successfully.
     * This method retrieves a List of strings representing the QR codes associated with the player.
     *
     * @param qrCodes a List of strings representing the QR codes associated with the player
     */
    void success(List<String> qrCodes);
    /**
     * Called when a Firestore database operation to retrieve a player's QR codes fails.
     *
     * @param e an Exception object representing the error that occurred during the database operation
     */

    void failure(Exception e);
}
