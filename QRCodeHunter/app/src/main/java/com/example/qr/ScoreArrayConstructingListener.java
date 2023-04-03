package com.example.qr;
/**
 * The ScoreArrayConstructingListener interface provides a method for handling
 * the successful construction of an array of scores.
 */
public interface ScoreArrayConstructingListener {
    /**
     * Called when the array of scores has been successfully constructed.
     * Implement this method to define what should happen upon successful
     * construction of the scores array.
     *
     * @param scores an array of Integer objects representing the scores
     */
    void onSuccess(Integer[] scores);
}
