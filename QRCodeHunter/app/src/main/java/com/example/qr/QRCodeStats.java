package com.example.qr;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 The QRCodeStats class contains methods for generating statistics and visual representation
 of a QR code based on its SHA-256 hash value.
 */

public class QRCodeStats {

    private static String content;

    /**
     Constructs a new QRCodeStats object with the given content.
     @param content the content of the QR code
     */
    public QRCodeStats(String content) {
        this.content = content;
    }

    /**
     Generates the SHA-256 hash value of the QR code's content.
     @return the hexadecimal representation of the hash value
     @throws NoSuchAlgorithmException if SHA-256 is not a valid message digest algorithm
     */
    public static String hashString() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(content.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    /**
     Extracts the first six bits of the given hash value and returns them as a binary string.
     @param hash the hexadecimal representation of the hash value
     @return the binary string representation of the first six bits
     */
    public static String extractFirstSix(String hash) {
        int firstSixBits = Integer.parseInt(hash.substring(0, 2), 16) >> 2;
        String firstSixBitsBinary = String.format("%6s", Integer.toBinaryString(firstSixBits)).replace(' ', '0');
        return firstSixBitsBinary;
    }

    /**
     Generates a naming convention for the QR code based on the first six bits of its hash value.
     @param firstSixBits the binary string representation of the first six bits of the hash value
     @return a naming convention for the QR code
     */
    public static String naming(String firstSixBits) {
        int index = 0;
        String result = "";
        while (index <= 5) {
            switch (index) {
                case 0:
                    if (firstSixBits.charAt(index) == '0') {
                        result += "cool ";
                    }
                    else {
                        result += "hot ";
                    }
                    break;
                case 1:
                    if (firstSixBits.charAt(index) == '0') {
                        result += "Fro";
                    }
                    else {
                        result += "Glo";
                    }
                    break;
                case 2:
                    if (firstSixBits.charAt(index) == '0') {
                        result += "Mo";
                    }
                    else {
                        result += "Lo";
                    }
                    break;
                case 3:
                    if (firstSixBits.charAt(index) == '0') {
                        result += "Mega";
                    }
                    else {
                        result += "Ultra";
                    }
                    break;
                case 4:
                    if (firstSixBits.charAt(index) == '0') {
                        result += "Spectral";
                    }
                    else {
                        result += "Sonic";
                    }
                    break;
                case 5:
                    if (firstSixBits.charAt(index) == '0') {
                        result += "Crab";
                    }
                    else {
                        result += "Shark";
                    }
                    break;
            }
            index++;
        }

        return result;
    }

    /**
     * Calculates a score based on the number of consecutive repeated characters in a hexadecimal string.
     * @param hexString a hexadecimal string to be scored
     * @return the score calculated based on the number of consecutive repeated characters in the input string
     */
    public static int scoring(String hexString) {
        List<String> repeatedCharsList = new ArrayList<>();
        char[] chars = hexString.toCharArray();

        for (int i = 0; i < chars.length - 1; i++) {
            StringBuilder sb = new StringBuilder();
            if (chars[i] == chars[i+1]) {
                sb.append(chars[i]).append(chars[i+1]);
                i++;
                while (i < chars.length - 1 && chars[i] == chars[i+1]) {
                    sb.append(chars[i]);
                    i++;
                }
                repeatedCharsList.add(sb.toString());
            }
        }

        String[] repeatedCharsArray = repeatedCharsList.toArray(new String[0]);
        Integer[] scores = new Integer[repeatedCharsArray.length];
        int index = 0;

        for (String v:repeatedCharsArray) {
            int decimalNumber = Integer.parseInt(v.substring(0,1), 16);
            int score = (int)Math.pow(decimalNumber,v.length() - 1);
            scores[index]=score;
            index++;
        }

        Integer result = 0;
        for (int score : scores) {
            result += score;
        }
        return result;
    }

    /**
     * Generates a visual representation of the first six bits of a hexadecimal string.
     * @param firstSixBits the first six bits of a hexadecimal string to be represented visually
     * @return a string representing the first six bits of the input hexadecimal string in a visual format
     */
    public static String visualRep(String firstSixBits) {
        int index = 0;
        String[][] temp = new String[8][10];
        while (index <= 5) {
            switch (index) {
                case 0:
                    if (firstSixBits.charAt(index) == '0') {
                        temp[3][3] = "^";
                        temp[3][6] = "^";
                    }
                    else {
                        temp[3][3] = "*";
                        temp[3][6] = "*";
                    }
                    break;
                case 1:
                    if (firstSixBits.charAt(index) == '0') {
                        temp[2][3] = "_";
                        temp[2][6] = "_";
                    }
                    break;
                case 2:
                    temp[0][6] = "_";temp[0][3] = "_";temp[2][1]="|";temp[3][1]="|";
                    temp[0][4] = "_";temp[0][5] = "_";temp[4][1]="|";temp[5][1]="|";
                    temp[7][6] = "_";temp[7][3] = "_";temp[6][1]="|";temp[2][8]="|";
                    temp[7][4] = "_";temp[7][5] = "_";temp[3][8]="|";temp[4][8]="|";
                    temp[5][8]="|";temp[6][8]="|";
                    if (firstSixBits.charAt(index) == '0') {
                        temp[1][2]="/";temp[7][7]="/";
                        temp[1][7]="\\";temp[7][2]="\\";
                    }
                    else {
                        temp[0][2] = "_";temp[0][7] = "_";
                        temp[1][1] = "|";temp[7][1] = "|";
                        temp[1][8] = "|";temp[7][8] = "|";
                        temp[7][2] = "_";temp[7][7] = "_";
                    }
                    break;
                case 3:
                    temp[4][4] = "`";
                    temp[4][5] = "`";
                    if (firstSixBits.charAt(index) == '0') {
                        temp[3][4] = "|";
                        temp[3][5] = "|";
                    }
                    break;
                case 4:
                    temp[6][4] = "_";
                    temp[6][5] = "_";
                    if (firstSixBits.charAt(index) == '0') {
                        temp[6][3] = "\\";
                        temp[6][6] = "/";
                    }
                    break;
                case 5:
                    temp[2][0] = "\\";
                    temp[4][0] = "/";
                    temp[2][9] = "/";
                    temp[4][9] = "\\";
                    if (firstSixBits.charAt(index) == '0') {
                        temp[3][0] = "@";
                        temp[3][9] = "@";
                    }
                    else {
                        temp[3][0] = "=";
                        temp[3][9] = "=";
                    }
                    break;
            }
            index++;
        }
        String result = "";
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 10; j ++) {
                if (temp[i][j] == null) {
                    result+=" ";
                }
                else {
                    result+=temp[i][j];
                }
            }
            result+="\n";
        }
        return result;
    }

}

