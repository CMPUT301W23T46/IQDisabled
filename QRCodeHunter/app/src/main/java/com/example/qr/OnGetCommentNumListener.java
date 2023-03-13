package com.example.qr;

/**
 * A callback interface for when the number of comments for a given resource has been retrieved.
 */
public interface OnGetCommentNumListener {
    /**
     * Called when the number of comments for a given resource has been successfully retrieved.
     *
     * @param length an integer value representing the number of comments for the given resource
     */
    void onSuccess(int length);
}