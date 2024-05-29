package com.example.Objects;

public class KeHoachGiangDay {
    private String macn;
    private String mamh;
    private int hesogk;
    private int hesock;

    public KeHoachGiangDay(String mamh) {
        this.mamh = mamh;
    }

    public KeHoachGiangDay(String macn, String mamh) {
        this.macn = macn;
        this.mamh = mamh;
    }

    public KeHoachGiangDay(String macn, String mamh, int hesogk, int hesock) {
        this.macn = macn;
        this.mamh = mamh;
        this.hesogk = hesogk;
        this.hesock = hesock;
    }

    public String getMacn() {
        return macn;
    }

    public void setMacn(String macn) {
        this.macn = macn;
    }

    public String getMamh() {
        return mamh;
    }

    public void setMamh(String mamh) {
        this.mamh = mamh;
    }

    public int getHesogk() {
        return hesogk;
    }

    public void setHesogk(int hesogk) {
        this.hesogk = hesogk;
    }

    public int getHesock() {
        return hesock;
    }

    public void setHesock(int hesock) {
        this.hesock = hesock;
    }



}
