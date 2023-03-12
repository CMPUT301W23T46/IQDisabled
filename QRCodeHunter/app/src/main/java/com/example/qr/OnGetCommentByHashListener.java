package com.example.qr;

import java.security.NoSuchAlgorithmException;

public interface OnGetCommentByHashListener {
    void onSuccess(String[] comments) throws NoSuchAlgorithmException;
}
