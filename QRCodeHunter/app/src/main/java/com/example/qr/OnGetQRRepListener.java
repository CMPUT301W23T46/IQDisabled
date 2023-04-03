package com.example.qr;

import java.util.List;
/**
 * This interface represents a listener for getting QR code replies operation.
 * It provides two methods to handle the success and failure of the operation.
 */
public interface OnGetQRRepListener {
    /**
     * This method is called when the get QR code replies operation is successful.
     *
     * @param qrRep a list of String objects containing the replies retrieved from the QR code
     */
    void success(List<String> qrRep);
    /**
     * This method is called when the get QR code replies operation fails due to an exception.
     *
     * @param e the exception that caused the failure of the operation
     */
    void failure(Exception e);
}
