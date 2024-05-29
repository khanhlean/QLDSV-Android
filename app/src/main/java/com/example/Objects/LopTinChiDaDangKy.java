package com.example.Objects;


public class LopTinChiDaDangKy {

    private int id;
    private String maLTC;
    private String tenMH;
    private String nienKhoa;
    private String hocKy;
    private String ngayBD;
    private String ngayKT;


    public LopTinChiDaDangKy(){
    }

    public LopTinChiDaDangKy(int id, String maLTC, String tenMH, String nienKhoa, String hocKy, String ngayBD, String ngayKT) {
        this.id = id;
        this.maLTC = maLTC;
        this.tenMH = tenMH;
        this.nienKhoa = nienKhoa;
        this.hocKy = hocKy;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
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

    public String getNienKhoa() {
        return nienKhoa;
    }

    public void setNienKhoa(String nienKhoa) {
        this.nienKhoa = nienKhoa;
    }

    public String getHocKy() {
        return hocKy;
    }

    public void setHocKy(String hocKy) {
        this.hocKy = hocKy;
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

}
