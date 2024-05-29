package com.example.Objects;


public class LopTinChiNhapDiem {
    private int id;
    private String maLTC;
    private String tenMH;



    public LopTinChiNhapDiem(){
    }


    public LopTinChiNhapDiem(int id, String maLTC, String tenMH) {
        this.id = id;
        this.maLTC = maLTC;
        this.tenMH = tenMH;
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
}
