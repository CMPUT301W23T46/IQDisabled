package com.example.qr;

/**
 * A callback interface for when the hashcodes for a given username have been retrieved.
 */
public interface OnGetHashByUsernameListener {
    /**
     * Called when the hashcodes for a given username have been successfully retrieved.
     *
     * @param hashcodes an array of String objects representing the hashcodes for the given username
     */
    void onSuccess(String[] hashcodes) throws InterruptedException;
}