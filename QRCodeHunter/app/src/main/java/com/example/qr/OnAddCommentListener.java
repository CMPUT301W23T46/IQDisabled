package com.example.qr;
/**
 * This interface represents a listener for adding a comment operation.
 * It provides two methods to handle the success and failure of the operation.
 */
public interface OnAddCommentListener {
    /**
     * This method is called when the add comment operation is successful.
     */
    void success();
    /**
     * This method is called when the add comment operation fails due to an exception.
     *
     * @param e the exception that caused the failure of the operation
     */
    void failure(Exception e);
}
