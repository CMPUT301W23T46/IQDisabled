package com.example.qr;

import java.security.NoSuchAlgorithmException;

/**
 QRCode class represents a QR code containing content, hash code, score, QR code name, visual representation, and comments.
 */

public class QRCode {
    private String content;
    private String hashCode;
    private Integer score;
    private String qrcodeName;
    private String visual_rep;
    private String[] comments;

    /**

     Constructor for QRCode object with content and comments as parameters
     @param content the content of the QR code
     @param comments an array of comments about the QR code
     @throws NoSuchAlgorithmException if hashing algorithm is not found
     */

    public QRCode(String content,String[] comments) throws NoSuchAlgorithmException {
        this.content = content;
        QRCodeStats s = new QRCodeStats(content);
        String hashCode = s.hashString();
        this.hashCode = hashCode;
        this.score = s.scoring(hashCode);
        String firstSix = s.extractFirstSix(hashCode);
        this.qrcodeName = s.naming(firstSix);
        this.score = s.scoring(hashCode);
        this.visual_rep = s.visualRep(firstSix);
        this.comments = comments;
    }
    /**
     Constructor for QRCode object with hash code as parameter
     @param hashCode the hash code of the QR code
     */

    public QRCode(String hashCode) {
        this.content = "";
        this.hashCode = hashCode;
        QRCodeStats s = new QRCodeStats(content);
        String hash = hashCode;
        this.hashCode = hash;
        this.score = s.scoring(hash);
        String firstSix = s.extractFirstSix(hash);
        this.qrcodeName = s.naming(firstSix);
        this.visual_rep = s.visualRep(firstSix);
        this.comments = new String[]{""};
    }

    /**
     Returns the hash code of the QR code
     @return the hash code of the QR code
     */

    public String getHashCode() {
        return hashCode;
    }

    /**
     Returns the score of the QR code
     @return the score of the QR code
     */

    public Integer getScore() {
        return score;
    }

    /**
     Returns the name of the QR code
     @return the name of the QR code
     */

    public String getQrcodeName() {
        return qrcodeName;
    }

    /**
     Returns the visual representation of the QR code
     @return the visual representation of the QR code
     */
    public String getVisual_rep() {
        return visual_rep;
    }

    /**
     Returns the comments about the QR code
     @return an array of comments about the QR code
     */
    public String[] getComments() {
        return comments;
    }

    public String getContent(){
        return content;
    }


}
