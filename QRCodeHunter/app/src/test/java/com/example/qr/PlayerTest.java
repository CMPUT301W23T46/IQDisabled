package com.example.qr;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * The PlayerTest class contains unit tests for the Player class.
 */
public class PlayerTest {
    Player player;
    /**
     * Sets up the mock player for each test.
     */
    @Before
    public void setUpMockPlayer(){
        player = new Player("xiaoMing","6","66666");

    }
    /**
     * Tests the getPhone_number method of the Player class.
     */
    @Test
    public void testPhoneNumber(){
        String output = player.getPhone_number();
        String expected = "66666";
        assertEquals(expected, output);

    }
    /**
     * Tests the setPhone_number method of the Player class.
     */
    @Test
    public void testSetUpPhoneNumber(){
        String expected = "123456";
        player.setPhone_number(expected);
        String output  = player.getPhone_number();
        assertEquals(expected,output);

    }
    /**
     * Tests the getPlayName method of the Player class.
     */
    @Test
    public void testName(){
        String output = player.getPlayName();
        String expected = "xiaoMing";
        assertEquals(expected, output);
    }
    /**
     * Tests the setPlayName method of the Player class.
     */
    @Test
    public void testSetUpName(){
        String expected = "daMing";
        player.setPlayName(expected);
        String output  = player.getPlayName();
        assertEquals(expected,output);

    }
    /**
     * Tests the getEmail method of the Player class.
     */
    @Test
    public void testEmail(){
        String output = player.getEmail();
        String expected = "6";
        assertEquals(expected, output);
    }
    /**
     * Tests the setEmail method of the Player class.
     */
    @Test
    public void testSetUpEmail(){
        String expected = "daMingnb@gmail.com";
        player.setEmail(expected);
        String output  = player.getEmail();
        assertEquals(expected,output);

    }
}
