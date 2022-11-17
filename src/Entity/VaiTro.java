package Entity;

public class VaiTro {

    private int id;
    private String maVT, tenVT;

    public VaiTro() {
    }

    public VaiTro(int id, String maVT, String tenVT) {
        this.id = id;
        this.maVT = maVT;
        this.tenVT = tenVT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaVT() {
        return maVT;
    }

    public void setMaVT(String maVT) {
        this.maVT = maVT;
    }

    public String getTenVT() {
        return tenVT;
    }

    public void setTenVT(String tenVT) {
        this.tenVT = tenVT;
    }

    @Override
    public String toString() {
        return tenVT;
    }
}
