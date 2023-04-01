package com.example.qr;

import java.util.List;

public interface OnGetQRCommentListener {
    void success(List<String> qrCom);

    void failure(Exception e);
}
