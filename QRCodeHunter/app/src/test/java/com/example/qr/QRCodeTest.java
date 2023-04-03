package com.example.qr;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * The QRCodeTest class is a JUnit test class for testing the QRCode class. It contains several test methods that test various functionalities of the QRCode class.
 */
public class QRCodeTest {
    QRCode code;
    QRCode code1;
    String[] comments= {"waterBottle1","waterBottle2","waterBottle3","waterBottle4"};
    /**
     * Sets up mock QRCode objects for testing purposes. This method is executed before each test method is run.
     */
    @Before
    public void setUpMockCode(){
        try {
            code = new QRCode("069000061008", comments);
        }catch (Exception e){
            System.out.println(e);
        }
        code1 = new QRCode("fb1a3dcbbf707c9b809ba897e815ec090069ea7424d0e01a8113f4b516ef4540");

    }
    /**
     * Test method for checking the functionality of the getHashCode() method in the QRCode class.
     */
    @Test
    public void testGetHashCode(){
        String expected = "fb1a3dcbbf707c9b809ba897e815ec090069ea7424d0e01a8113f4b516ef4540";
        String output = code.getHashCode();
        String output1 = code1.getHashCode();

        assertEquals(expected,output);
        assertEquals(expected,output1);
    }
    /**
     * Test method for checking the functionality of the getScore() method in the QRCode class.
     */
    @Test
    public void testGetScore(){
        int expected = 12;
        int output = code.getScore();
        int output1 = code1.getScore();
        assertEquals(expected,output);
        assertEquals(expected,output1);
    }
    /**
     * Test method for checking the functionality of the getQrcodeName() method in the QRCode class.
     */
    @Test
    public void testGetQrcodeName(){
        String expected = "hot GloLoUltraSonicCrab";
        String output  = code.getQrcodeName();
        String output1  = code1.getQrcodeName();
        assertEquals(expected,output);
        assertEquals(expected,output1);
    }
    /**
     * Test method for checking the functionality of the getVisual_rep() method in the QRCode class.
     */
    @Test
    public void testGetVisual_rep(){
        String expected = "  ______  \n" +
                " |      | \n" +
                "\\|      |/\n" +
                "@| *  * |@\n" +
                "/|  ``  |\\\n" +
                " |      | \n" +
                " |  __  | \n" +
                " |______| \n";
        String output = code.getVisual_rep();
        String output1 = code1.getVisual_rep();
        assertEquals(expected,output);
        assertEquals(expected,output1);
    }
    /**
     * Test method for checking the functionality of the getComments() method in the QRCode class.
     */
    @Test
    public void testGetComments(){
        String[] expected = comments;
        String[] output = code.getComments();

        assertEquals(expected,output);

    }
    /**
     * Test method for checking the functionality of the getContent() method in the QRCode class.
     */
    @Test
    public void testGetContent() {
        String expected = "069000061008";
        String output = code.getContent();

        assertEquals(expected, output);
    }
}
