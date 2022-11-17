package Entity;

import java.util.Date;

public class HoaDon {

    private int id;
    private String maHD;
    private Integer idCa, idND, idKH;
    private Date tgTao, tgThanhToan;
    private double chiPhiKhac, tongTien;
    private String diaChi, maLTT, ghiChu;
    private int trangThai;

    public HoaDon() {
    }

    public HoaDon(int id, String maHD, Integer idCa, Integer idND, Integer idKH, Date tgTao, Date tgThanhToan, double chiPhiKhac, double tongTien, String diaChi, String maLTT, String ghiChu, int trangThai) {
        this.id = id;
        this.maHD = maHD;
        this.idCa = idCa;
        this.idND = idND;
        this.idKH = idKH;
        this.tgTao = tgTao;
        this.tgThanhToan = tgThanhToan;
        this.chiPhiKhac = chiPhiKhac;
        this.tongTien = tongTien;
        this.diaChi = diaChi;
        this.maLTT = maLTT;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public Integer getIdCa() {
        return idCa;
    }

    public void setIdCa(Integer idCa) {
        this.idCa = idCa;
    }

    public Integer getIdND() {
        return idND;
    }

    public void setIdND(Integer idND) {
        this.idND = idND;
    }

    public Integer getIdKH() {
        return idKH;
    }

    public void setIdKH(Integer idKH) {
        this.idKH = idKH;
    }

    public Date getTgTao() {
        return tgTao;
    }

    public void setTgTao(Date tgTao) {
        this.tgTao = tgTao;
    }

    public Date getTgThanhToan() {
        return tgThanhToan;
    }

    public void setTgThanhToan(Date tgThanhToan) {
        this.tgThanhToan = tgThanhToan;
    }

    public double getChiPhiKhac() {
        return chiPhiKhac;
    }

    public void setChiPhiKhac(double chiPhiKhac) {
        this.chiPhiKhac = chiPhiKhac;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMaLTT() {
        return maLTT;
    }

    public void setMaLTT(String maLTT) {
        this.maLTT = maLTT;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

}
