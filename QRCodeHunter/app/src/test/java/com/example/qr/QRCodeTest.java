package com.example.qr;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QRCodeTest {
    QRCode code;
    String[] comments= {"waterBottle1","waterBottle2","waterBottle3","waterBottle4"};

    @Before
    public void setUpMockCode(){
        try {
            code = new QRCode("069000061008", comments);
        }catch (Exception e){
            System.out.println(e);
        }

    }
    @Test
    public void testConstructor(){

    }

    @Test
    public void testGetHashCode(){
        String expected = "fb1a3dcbbf707c9b809ba897e815ec090069ea7424d0e01a8113f4b516ef4540";
        String output = code.getHashCode();
        System.out.println(expected);
        System.out.println(output);
        assertEquals(expected,output);
    }

    @Test
    public void testGetScore(){
        int expected = 12;
        int output = code.getScore();
        assertEquals(expected,output);
    }
    @Test
    public void testGetQrcodeName(){
        String expected = "hot GloLoUltraSonicCrab";
        String output  = code.getQrcodeName();
        assertEquals(expected,output);
    }

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
        assertEquals(expected,output);
    }

    @Test
    public void testGetComments(){
        String[] expected = comments;
        String[] output = code.getComments();
        assertEquals(expected,output);
    }


}
