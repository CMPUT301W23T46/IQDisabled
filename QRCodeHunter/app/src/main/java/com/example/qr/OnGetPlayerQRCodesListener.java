package com.example.qr;

import java.util.List;

public interface OnGetPlayerQRCodesListener {
    void success(List<String> qrCodes);

    void failure(Exception e);
}
