package com.example.Objects;

public class ChuyenNganh {
    private String macn;
    private String tencn;

    public ChuyenNganh() {
    }

    public ChuyenNganh(String macn) {
        this.macn = macn;
    }

    public ChuyenNganh(String macn, String tencn) {
        this.macn = macn;
        this.tencn = tencn;
    }

    public String getMacn() {
        return macn;
    }

    public void setMacn(String macn) {
        this.macn = macn;
    }

    public String getTencn() {
        return tencn;
    }

    public void setTencn(String tencn) {
        this.tencn = tencn;
    }
}
