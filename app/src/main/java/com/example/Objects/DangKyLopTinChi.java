package com.example.Objects;

public class DangKyLopTinChi {
    private int id;
    private String maLTC;
    private String tenMH;
    private int slTD;
    private int slCL;
    private String ngayBD;
    private String ngayKT;
    private boolean ischecked;

    public DangKyLopTinChi(){
    }
    public DangKyLopTinChi(int id, String maLTC, String tenMH, int slTD, int slCL, String ngayBD, String ngayKT, boolean ischecked) {
        this.id = id;
        this.maLTC = maLTC;
        this.tenMH = tenMH;
        this.slTD = slTD;
        this.slCL = slCL;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.ischecked = ischecked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaLTC() {
        return maLTC;
    }

    public void setMaLTC(String maLTC) {
        this.maLTC = maLTC;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public int getSlTD() {
        return slTD;
    }

    public void setSlTD(int slTD) {
        this.slTD = slTD;
    }

    public int getSlCL() {
        return slCL;
    }

    public void setSlCL(int slCL) {
        this.slCL = slCL;
    }

    public String getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(String ngayBD) {
        this.ngayBD = ngayBD;
    }

    public String getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(String ngayKT) {
        this.ngayKT = ngayKT;
    }

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }
}
