package com.example.qr;

public interface OnGetAllPlayerListener {
    void success(Player[] players);

    void failure(Exception e);
}
