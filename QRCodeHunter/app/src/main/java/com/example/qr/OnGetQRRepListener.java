package com.example.qr;

import java.util.List;

public interface OnGetQRRepListener {

    void success(List<String> qrRep);

    void failure(Exception e);
}
