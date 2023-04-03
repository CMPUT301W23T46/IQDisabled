package com.example.qr;
/**
 * This interface represents a listener for getting QR code score operation.
 * It provides a method to handle the success of the operation.
 */
public interface OnGetQRCodeScoreListener {
    /**
     * This method is called when the get QR code score operation is successful.
     *
     * @param score the score retrieved from the QR code
     */
    void onSuccess(Integer score);
}
