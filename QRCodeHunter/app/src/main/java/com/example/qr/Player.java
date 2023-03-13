package com.example.qr;

/**
 * The Player class represents a player with a name, email, and phone number.
 */
public class Player {
    private String playName;
    private String email;
    private String phone_number;

    /**
     * Creates a new Player object with the specified name, email, and phone number.
     *
     * @param playName the player's name
     * @param email the player's email
     * @param phone_number the player's phone number
     */
    public Player(String playName, String email, String phone_number) {
        this.playName = playName;
        this.email = email;
        this.phone_number = phone_number;
    }

    /**
     * Returns the player's name.
     *
     * @return the player's name
     */
    public String getPlayName() {
        return playName;
    }

    /**
     * Returns the player's email.
     *
     * @return the player's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the player's phone number.
     *
     * @return the player's phone number
     */
    public String getPhone_number() {
        return phone_number;
    }

    /**
     * Sets the player's name.
     *
     * @param playName the player's name
     */
    public void setPlayName(String playName) {
        this.playName = playName;
    }

    /**
     * Sets the player's email.
     *
     * @param email the player's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the player's phone number.
     *
     * @param phone_number the player's phone number
     */
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
