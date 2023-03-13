package com.example.qr;

/**
 * A callback interface for when a single player has been retrieved.
 */
public interface OnGetPlayerListener {
    /**
     * Called when the player has been successfully retrieved.
     *
     * @param player the Player object representing the retrieved player
     */
    void onSuccess(Player player);
}