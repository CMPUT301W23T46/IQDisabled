package com.example.qr;

import java.util.List;

public interface OnGetQRScoreListener {
    void success(List<String> qrScore);

    void failure(Exception e);
}
