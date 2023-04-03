package com.example.qr;
/**
 * An interface used to handle callbacks for Firestore database operations that retrieve all player data.
 */
public interface OnGetAllPlayerListener {
    /**
     * Called when a Firestore database operation to retrieve all player data is completed successfully.
     * This method retrieves an array of Player objects representing all players in the database.
     *
     * @param players an array of Player objects representing all players in the database
     */
    void success(Player[] players);
    /**
     * Called when a Firestore database operation to retrieve all player data fails.
     *
     * @param e an Exception object representing the error that occurred during the database operation
     */
    void failure(Exception e);
}
