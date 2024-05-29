package com.example.Objects;

public class ThongBao {
    private int maTB;
    private String tieude;
    private String noidung;
    private String ngaygio;
    private boolean status;
    private String tennguoigui;
    public ThongBao(){}

    public ThongBao(int maTB, String tieude, String noidung, String ngaygio, boolean status, String tennguoigui) {
        this.maTB = maTB;
        this.tieude = tieude;
        this.noidung = noidung;
        this.ngaygio = ngaygio;
        this.status = status;
        this.tennguoigui = tennguoigui;
    }

    public int getMaTB() {
        return maTB;
    }

    public void setMaTB(int maTB) {
        this.maTB = maTB;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNgaygio() {
        return ngaygio;
    }

    public void setNgaygio(String ngaygio) {
        this.ngaygio = ngaygio;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTennguoigui() {
        return tennguoigui;
    }

    public void setTennguoigui(String tennguoigui) {
        this.tennguoigui = tennguoigui;
    }
}
