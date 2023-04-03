package com.example.qr;

import static org.junit.Assert.*;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
/**
 * The QRCodeStatsTest class is a JUnit test class for testing the QRCodeStats class. It contains several test methods that test various functionalities of the QRCodeStats class.
 */
public class QRCodeStatsTest {
    /**
     * Test method for checking the hashString() method in the QRCodeStats class.
     * @throws NoSuchAlgorithmException if the hash algorithm is not available.
     */
    @Test
    public void hashString() throws NoSuchAlgorithmException {
        String input = "9661932184";
        String expected = "db0a4118f7f9926f26e2a71b19f8a4932f33e257c6271c175acb9ea0e89d0daf";
        QRCodeStats s = new QRCodeStats(input);
        assertEquals(QRCodeStats.hashString(),expected);
    }
    /**
     * Test method for checking the extractFirstSix() method in the QRCodeStats class.
     */
    @Test
    public void extractFirstSix() {
        String input = "9661932184";
        String ouput = QRCodeStats.extractFirstSix(input);
        String expected = "100101";
        assertEquals(ouput,expected);
    }
    /**
     * Test method for checking the naming() method in the QRCodeStats class.
     */
    @Test
    public void naming() {
        String input = "9661932184";
        String ouput = QRCodeStats.naming(input);
        String expected = "hot GloLoUltraSonicShark";
        assertEquals(ouput,expected);
    }
    /**
     * Test method for checking the scoring() method in the QRCodeStats class.
     */
    @Test
    public void scoring() {
        String input = "9661932184";
        int ouput = QRCodeStats.scoring(input);
        int expected = 6;
        assertEquals(ouput,expected);
    }
    /**
     * Test method for checking the visualRep() method in the QRCodeStats class.
     */
    @Test
    public void visualRep() {
        String input = "9661932184";
        String ouput = QRCodeStats.visualRep(input);
        String expected = "  ______  \n" +
                " |      | \n" +
                "\\|      |/\n" +
                "=| *  * |=\n" +
                "/|  ``  |\\\n" +
                " |      | \n" +
                " |  __  | \n" +
                " |______| \n";
        assertEquals(ouput,expected);
    }
}