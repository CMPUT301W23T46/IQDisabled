package com.example.qr;

import java.util.List;
/**
 * This interface represents a listener for getting QR code scores operation.
 * It provides two methods to handle the success and failure of the operation.
 */
public interface OnGetQRScoreListener {
    /**
     * This method is called when the get QR code scores operation is successful.
     *
     * @param qrScore a list of String objects containing the scores retrieved from the QR code
     */
    void success(List<String> qrScore);
    /**
     * This method is called when the get QR code scores operation fails due to an exception.
     *
     * @param e the exception that caused the failure of the operation
     */
    void failure(Exception e);
}
