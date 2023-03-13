package com.example.qr;

import java.security.NoSuchAlgorithmException;
/**
 * A callback interface for when the comments for a given hash have been retrieved.
 */
public interface OnGetCommentByHashListener {
    /**
     * Called when the comments for a given hash have been successfully retrieved.
     *
     * @param comments an array of String objects representing the comments for the given hash
     * @throws NoSuchAlgorithmException if the hashing algorithm used to generate the hashcode is not available
     */
    void onSuccess(String[] comments) throws NoSuchAlgorithmException;
}