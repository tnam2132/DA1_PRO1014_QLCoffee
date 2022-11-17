package Entity;

import java.util.Date;

public class GiaoCa {

    private int id;
    private String maCa;
    private Integer idND;
    private Date tgBatDau, tgKetThuc;
    private double tienBanDau, tienDoanhThu, tienChuyenKhoan, tienMat, tongTienCa, tienChuThu, tienPhatSinh;
    private String ghiChu;
    private int trangThai;

    public GiaoCa() {
    }

    public GiaoCa(int id, String maCa, Integer idND, Date tgBatDau, Date tgKetThuc, double tienBanDau, double tienDoanhThu, double tienChuyenKhoan, double tienMat, double tongTienCa, double tienChuThu, double tienPhatSinh, String ghiChu, int trangThai) {
        this.id = id;
        this.maCa = maCa;
        this.idND = idND;
        this.tgBatDau = tgBatDau;
        this.tgKetThuc = tgKetThuc;
        this.tienBanDau = tienBanDau;
        this.tienDoanhThu = tienDoanhThu;
        this.tienChuyenKhoan = tienChuyenKhoan;
        this.tienMat = tienMat;
        this.tongTienCa = tongTienCa;
        this.tienChuThu = tienChuThu;
        this.tienPhatSinh = tienPhatSinh;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaCa() {
        return maCa;
    }

    public void setMaCa(String maCa) {
        this.maCa = maCa;
    }

    public Integer getIdND() {
        return idND;
    }

    public void setIdND(Integer idND) {
        this.idND = idND;
    }

    public Date getTgBatDau() {
        return tgBatDau;
    }

    public void setTgBatDau(Date tgBatDau) {
        this.tgBatDau = tgBatDau;
    }

    public Date getTgKetThuc() {
        return tgKetThuc;
    }

    public void setTgKetThuc(Date tgKetThuc) {
        this.tgKetThuc = tgKetThuc;
    }

    public double getTienBanDau() {
        return tienBanDau;
    }

    public void setTienBanDau(double tienBanDau) {
        this.tienBanDau = tienBanDau;
    }

    public double getTienDoanhThu() {
        return tienDoanhThu;
    }

    public void setTienDoanhThu(double tienDoanhThu) {
        this.tienDoanhThu = tienDoanhThu;
    }

    public double getTienChuyenKhoan() {
        return tienChuyenKhoan;
    }

    public void setTienChuyenKhoan(double tienChuyenKhoan) {
        this.tienChuyenKhoan = tienChuyenKhoan;
    }

    public double getTienMat() {
        return tienMat;
    }

    public void setTienMat(double tienMat) {
        this.tienMat = tienMat;
    }

    public double getTongTienCa() {
        return tongTienCa;
    }

    public void setTongTienCa(double tongTienCa) {
        this.tongTienCa = tongTienCa;
    }

    public double getTienChuThu() {
        return tienChuThu;
    }

    public void setTienChuThu(double tienChuThu) {
        this.tienChuThu = tienChuThu;
    }

    public double getTienPhatSinh() {
        return tienPhatSinh;
    }

    public void setTienPhatSinh(double tienPhatSinh) {
        this.tienPhatSinh = tienPhatSinh;
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
