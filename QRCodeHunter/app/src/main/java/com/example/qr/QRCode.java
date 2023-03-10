package com.example.qr;

import java.security.NoSuchAlgorithmException;

public class QRCode {
    private String content;
    private String hashCode;
    private Integer score;
    private String qrcodeName;
    private String visual_rep;
    private String[] comments;

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

    public String getHashCode() {
        return hashCode;
    }

    public Integer getScore() {
        return score;
    }

    public String getQrcodeName() {
        return qrcodeName;
    }

    public String getVisual_rep() {
        return visual_rep;
    }

    public String[] getComments() {
        return comments;
    }
}
