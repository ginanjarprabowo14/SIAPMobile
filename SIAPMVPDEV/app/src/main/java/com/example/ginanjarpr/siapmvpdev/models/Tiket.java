package com.example.ginanjarpr.siapmvpdev.models;

public class Tiket {

    private String no_tiket;
    private String nama_pengadu;
    private String alamat_pengadu;
    private String no_telepon_pengadu;
    private String topik_aduan;
    private String isi_aduan;
    private String status_aduan;
    private String username;
    private String departemen;
    private String isi;
    private String status_jawab;

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public void setDepartemen(String departemen) {
        this.departemen = departemen;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNo_tiket() {
        return no_tiket;
    }

    public String getStatus_jawab() {
        return status_jawab;
    }

    public void setNo_tiket(String no_tiket) {
        this.no_tiket = no_tiket;
    }

    public String getNama_pengadu() {
        return nama_pengadu;
    }

    public void setNama_pengadu(String nama_pengadu) {
        this.nama_pengadu = nama_pengadu;
    }

    public String getAlamat_pengadu() {
        return alamat_pengadu;
    }

    public void setAlamat_pengadu(String alamat_pengadu) {
        this.alamat_pengadu = alamat_pengadu;
    }

    public String getNo_telepon_pengadu() {
        return no_telepon_pengadu;
    }

    public void setNo_telepon_pengadu(String no_telepon_pengadu) {
        this.no_telepon_pengadu = no_telepon_pengadu;
    }

    public String getTopik_aduan() {
        return topik_aduan;
    }

    public void setTopik_aduan(String topik_aduan) {
        this.topik_aduan = topik_aduan;
    }

    public String getIsi_aduan() {
        return isi_aduan;
    }

    public void setIsi_aduan(String isi_aduan) {
        this.isi_aduan = isi_aduan;
    }

    public String getStatus_aduan() {
        return status_aduan;
    }

    public void setStatus_aduan(String status_aduan) {
        this.status_aduan = status_aduan;
    }
}
