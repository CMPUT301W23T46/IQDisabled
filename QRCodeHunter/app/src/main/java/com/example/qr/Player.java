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

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
