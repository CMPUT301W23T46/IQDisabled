package com.example.qr;

import java.util.List;
/**
 * This interface represents a listener for getting QR code comments operation.
 * It provides two methods to handle the success and failure of the operation.
 */
public interface OnGetQRCommentListener {
    /**
     * This method is called when the get QR code comments operation is successful.
     *
     * @param qrCom a list of String objects containing the comments retrieved from the QR code
     */
    void success(List<String> qrCom);
    /**
     * This method is called when the get QR code comments operation fails due to an exception.
     *
     * @param e the exception that caused the failure of the operation
     */
    void failure(Exception e);
}
