package com.example.qr;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * The PlayerTest class is a JUnit test class for testing the Player class. It contains several test methods that test various functionalities of the Player class.
 */
public class PlayerTest {
    Player player;
    /**
     * Sets up a mock Player object for testing purposes. This method is executed before each test method is run.
     */
    @Before
    public void setUpMockPlayer(){
        player = new Player("xiaoMing","6","66666");

    }
    /**
     * Test method for checking the functionality of the getPhone_number() method in the Player class.
     */
    @Test
    public void testPhoneNumber(){
        String output = player.getPhone_number();
        String expected = "66666";
        assertEquals(expected, output);

    }
    /**
     * Test method for checking the functionality of the setPhone_number() and getPhone_number() methods in the Player class.
     */
    @Test
    public void testSetUpPhoneNumber(){
        String expected = "123456";
        player.setPhone_number(expected);
        String output  = player.getPhone_number();
        assertEquals(expected,output);

    }
    /**
     * Test method for checking the functionality of the getPlayName() method in the Player class.
     */
    @Test
    public void testName(){
        String output = player.getPlayName();
        String expected = "xiaoMing";
        assertEquals(expected, output);
    }
    /**
     * Test method for checking the functionality of the setPlayName() and getPlayName() methods in the Player class.
     */
    @Test
    public void testSetUpName(){
        String expected = "daMing";
        player.setPlayName(expected);
        String output  = player.getPlayName();
        assertEquals(expected,output);

    }
    /**
     * Test method for checking the functionality of the getEmail() method in the Player class.
     */
    @Test
    public void testEmail(){
        String output = player.getEmail();
        String expected = "6";
        assertEquals(expected, output);
    }
    /**
     * Test method for checking the functionality of the setEmail() and getEmail() methods in the Player class.
     */
    @Test
    public void testSetUpEmail(){
        String expected = "daMingnb@gmail.com";
        player.setEmail(expected);
        String output  = player.getEmail();
        assertEquals(expected,output);

    }
}
