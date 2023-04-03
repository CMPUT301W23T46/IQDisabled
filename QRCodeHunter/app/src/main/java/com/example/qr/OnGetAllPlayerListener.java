package com.example.qr;
/**
 * This interface represents a listener for getting all players operation.
 * It provides two methods to handle the success and failure of the operation.
 */
public interface OnGetAllPlayerListener {
    /**
     * This method is called when the get all players operation is successful.
     *
     * @param players an array of Player objects containing all the retrieved players
     */
    void success(Player[] players);

    /**
     * This method is called when the get all players operation fails due to an exception.
     *
     * @param e the exception that caused the failure of the operation
     */
    void failure(Exception e);
}
