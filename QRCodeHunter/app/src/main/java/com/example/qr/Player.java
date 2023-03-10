package com.example.qr;

public class Player {
    private String playName;
    private String email;
    private String phone_number;

    public Player(String playName, String email, String phone_number) {
        this.playName = playName;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String getPlayName() {
        return playName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
