package Controller;

import javax.swing.JPanel;

public class DanhMucBean {

    private String kind;
    private JPanel jpn;

    public DanhMucBean() {
    }

    public DanhMucBean(String kind, JPanel jpn) {
        this.kind = kind;
        this.jpn = jpn;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public JPanel getJpn() {
        return jpn;
    }

    public void setJpn(JPanel jpn) {
        this.jpn = jpn;
    }

}
