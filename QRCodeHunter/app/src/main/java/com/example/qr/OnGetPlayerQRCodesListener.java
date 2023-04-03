package com.example.qr;

import java.util.List;
/**
 * This interface represents a listener for getting player QR codes operation.
 * It provides two methods to handle the success and failure of the operation.
 */
public interface OnGetPlayerQRCodesListener {
    /**
     * This method is called when the get player QR codes operation is successful.
     *
     * @param qrCodes a list of String objects containing the QR codes of the players
     */
    void success(List<String> qrCodes);
    /**
     * This method is called when the get player QR codes operation fails due to an exception.
     *
     * @param e the exception that caused the failure of the operation
     */
    void failure(Exception e);
}
