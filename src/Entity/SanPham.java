package Entity;

public class SanPham {

    private int id;
    private String maSP, tenSP;
    private int idLSP;
    private double donGia;
    private String hinh, moTa, ghiChu;
    private boolean trangThai;

    public SanPham() {
    }

    public SanPham(int id, String maSP, String tenSP, int idLSP, double donGia, String hinh, String moTa, String ghiChu, boolean trangThai) {
        this.id = id;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.idLSP = idLSP;
        this.donGia = donGia;
        this.hinh = hinh;
        this.moTa = moTa;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getIdLSP() {
        return idLSP;
    }

    public void setIdLSP(int idLSP) {
        this.idLSP = idLSP;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

}
