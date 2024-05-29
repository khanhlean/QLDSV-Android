package com.example.Objects;

public class MonHoc {
    private String mamh;
    private String tenmh;
    private int tietLT;
    private int tietTH;
    private int tinchi;
    private int cc;
    private int gk;
    private int ck;
    private String macn;

    public MonHoc(String mamh, String tenmh) {
        this.mamh = mamh;
        this.tenmh = tenmh;
    }

    public MonHoc(String mamh, String tenmh, String macn) {
        this.mamh = mamh;
        this.tenmh = tenmh;
        this.macn = macn;
    }

    public MonHoc(String mamh, String tenmh, int tietLT, int tietTH, int tinchi, int cc, int gk, int ck, String macn) {
        this.mamh = mamh;
        this.tenmh = tenmh;
        this.tietLT = tietLT;
        this.tietTH = tietTH;
        this.tinchi = tinchi;
        this.cc = cc;
        this.gk = gk;
        this.ck = ck;
        this.macn = macn;
    }

    public String getMamh() {
        return mamh;
    }

    public void setMamh(String mamh) {
        this.mamh = mamh;
    }

    public String getTenmh() {
        return tenmh;
    }

    public void setTenmh(String tenmh) {
        this.tenmh = tenmh;
    }

    public int getTietLT() {
        return tietLT;
    }

    public void setTietLT(int tietLT) {
        this.tietLT = tietLT;
    }

    public int getTietTH() {
        return tietTH;
    }

    public void setTietTH(int tietTH) {
        this.tietTH = tietTH;
    }

    public int getTinchi() {
        return tinchi;
    }

    public void setTinchi(int tinchi) {
        this.tinchi = tinchi;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public int getGk() {
        return gk;
    }

    public void setGk(int gk) {
        this.gk = gk;
    }

    public int getCk() {
        return ck;
    }

    public void setCk(int ck) {
        this.ck = ck;
    }

    public String getMacn() {
        return macn;
    }

    public void setMacn(String macn) {
        this.macn = macn;
    }
}
