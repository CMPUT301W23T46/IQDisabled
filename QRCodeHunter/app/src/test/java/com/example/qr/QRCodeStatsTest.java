package com.example.qr;

import static org.junit.Assert.*;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class QRCodeStatsTest {

    @Test
    public void hashString() throws NoSuchAlgorithmException {
        String input = "9661932184";
        String expected = "db0a4118f7f9926f26e2a71b19f8a4932f33e257c6271c175acb9ea0e89d0daf";
        QRCodeStats s = new QRCodeStats(input);
        assertEquals(QRCodeStats.hashString(),expected);
    }

    @Test
    public void extractFirstSix() {
        String input = "9661932184";
        String ouput = QRCodeStats.extractFirstSix(input);
        String expected = "100101";
        assertEquals(ouput,expected);
    }

    @Test
    public void naming() {
        String input = "9661932184";
        String ouput = QRCodeStats.naming(input);
        String expected = "hot GloLoUltraSonicShark";
        assertEquals(ouput,expected);
    }

    @Test
    public void scoring() {
        String input = "9661932184";
        int ouput = QRCodeStats.scoring(input);
        int expected = 6;
        assertEquals(ouput,expected);
    }

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