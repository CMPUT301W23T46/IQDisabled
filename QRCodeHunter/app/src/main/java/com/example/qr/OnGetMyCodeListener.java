package com.example.qr;

public interface OnGetMyCodeListener {
    void success(QRCode[] qrCodes);

    void failure(Exception e);
}
