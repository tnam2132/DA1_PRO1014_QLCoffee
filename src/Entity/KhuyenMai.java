package Entity;

import java.util.Date;

public class KhuyenMai {
    
    private int id;
    private String maKM, tenKM;
    private boolean loaiKM;
    private int giaTriKM;
    private Date ngayBD, ngayKT;
    private boolean TrangThai;

    public KhuyenMai() {
    }

    public KhuyenMai(int id, String maKM, String tenKM, boolean loaiKM, int giaTriKM, Date ngayBD, Date ngayKT, boolean TrangThai) {
        this.id = id;
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.loaiKM = loaiKM;
        this.giaTriKM = giaTriKM;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.TrangThai = TrangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public boolean isLoaiKM() {
        return loaiKM;
    }

    public void setLoaiKM(boolean loaiKM) {
        this.loaiKM = loaiKM;
    }

    public int getGiaTriKM() {
        return giaTriKM;
    }

    public void setGiaTriKM(int giaTriKM) {
        this.giaTriKM = giaTriKM;
    }

    public Date getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(Date ngayBD) {
        this.ngayBD = ngayBD;
    }

    public Date getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(Date ngayKT) {
        this.ngayKT = ngayKT;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

}
