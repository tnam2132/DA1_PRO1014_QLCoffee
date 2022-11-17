package Entity;

public class LoaiThanhToan {

    private String maLTT, tenLTT;

    public LoaiThanhToan() {
    }

    public LoaiThanhToan(String maLTT, String tenLTT) {
        this.maLTT = maLTT;
        this.tenLTT = tenLTT;
    }

    public String getMaLTT() {
        return maLTT;
    }

    public void setMaLTT(String maLTT) {
        this.maLTT = maLTT;
    }

    public String getTenLTT() {
        return tenLTT;
    }

    public void setTenLTT(String tenLTT) {
        this.tenLTT = tenLTT;
    }
    
    @Override
    public String toString() {
        return tenLTT;
    }
}
