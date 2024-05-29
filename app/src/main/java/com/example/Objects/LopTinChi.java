package com.example.Objects;

import java.sql.Date;

public class LopTinChi {
    private String maltc;
    private String namhoc;
    private String hocki;
    private int sltoithieu;
    private int sltoida;
    private Date batdau;
    private Date ketthuc;
    private String mamh;

    public LopTinChi(String maltc, String namhoc, String hocki, int sltoithieu, int sltoida, Date batdau, Date ketthuc, String mamh) {
        this.maltc = maltc;
        this.namhoc = namhoc;
        this.hocki = hocki;
        this.sltoithieu = sltoithieu;
        this.sltoida = sltoida;
        this.batdau = batdau;
        this.ketthuc = ketthuc;
        this.mamh = mamh;
    }

    public LopTinChi(String maltc, String namhoc, String hocki) {
        this.maltc = maltc;
        this.namhoc = namhoc;
        this.hocki = hocki;
    }

    public LopTinChi(String maltc, String namhoc) {
        this.maltc = maltc;
        this.namhoc = namhoc;
    }

    public String getMaltc() {
        return maltc;
    }

    public void setMaltc(String maltc) {
        this.maltc = maltc;
    }

    public String getNamhoc() {
        return namhoc;
    }

    public void setNamhoc(String namhoc) {
        this.namhoc = namhoc;
    }

    public String getHocki() {
        return hocki;
    }

    public void setHocki(String hocki) {
        this.hocki = hocki;
    }

    public int getSltoithieu() {
        return sltoithieu;
    }

    public void setSltoithieu(int sltoithieu) {
        this.sltoithieu = sltoithieu;
    }

    public int getSltoida() {
        return sltoida;
    }

    public void setSltoida(int sltoida) {
        this.sltoida = sltoida;
    }

    public Date getBatdau() {
        return batdau;
    }

    public void setBatdau(Date batdau) {
        this.batdau = batdau;
    }

    public Date getKetthuc() {
        return ketthuc;
    }

    public void setKetthuc(Date ketthuc) {
        this.ketthuc = ketthuc;
    }

    public String getMamh() {
        return mamh;
    }

    public void setMamh(String mamh) {
        this.mamh = mamh;
    }
}
