package com.example.Objects;

public class TaiKhoan {
    private String matk;
    private String tentk;

    private String matkhau;



    public TaiKhoan(String matk, String tentk) {
        this.matk = matk;
        this.tentk = tentk;

    }

//    public TaiKhoan(String tentk, String matkhau) {
//
//        this.tentk = tentk;
//        this.matkhau = matkhau;
//
//    }

    public TaiKhoan(String matk, String tentk, String matkhau) {
        this.matk = matk;
        this.tentk = tentk;
        this.matkhau = matkhau;

    }

    public String getMatk() {
        return matk;
    }

    public void setMatk(String matk) {
        this.matk = matk;
    }

    public String getTentk() {
        return tentk;
    }

    public void setTentk(String tentk) {
        this.tentk = tentk;
    }
    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }


}
