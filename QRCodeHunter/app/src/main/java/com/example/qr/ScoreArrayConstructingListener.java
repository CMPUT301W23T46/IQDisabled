package com.example.qr;
/**
 * The ScoreArrayConstructingListener interface defines a listener for events related to
 * constructing an array of scores.
 */
public interface ScoreArrayConstructingListener {
    /**
     * Called when an array of scores is successfully constructed.
     *
     * @param scores the array of scores that was constructed
     */
    void onSuccess(Integer[] scores);
}
