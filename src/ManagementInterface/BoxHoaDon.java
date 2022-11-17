package ManagementInterface;

import Dao.ApDungKMDao;
import ManagementInterfaceDone.KhachHangDialog;
import Dao.BaseDaoInterface;
import Dao.HoaDonChiTietDao;
import Dao.HoaDonDao;
import Dao.KhachHangDao;
import Dao.KhuyenMaiDao;
import Dao.LoaiThanhToanDao;
import Dao.SanPhamDao;
import Entity.ApDungKM;
import Entity.HoaDon;
import Entity.HoaDonChiTiet;
import Entity.KhachHang;
import Entity.KhuyenMai;
import Entity.LoaiThanhToan;
import Entity.SanPham;
import Helper.MoneyHelper;
import Helper.DialogHelper;
import Socket.SocketClient;
import TableModelUI.ButtonEditor;
import TableModelUI.ButtonRenderer;
import TableModelUI.SpinnerEditor;
import TableModelUI.SpinnerRenderer;
import java.awt.EventQueue;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class BoxHoaDon extends javax.swing.JPanel {

    BaseDaoInterface daoHDChiTiet, daoSP, daoLoaiTT, daoKH, daoHD, daoKM, daoADKM;
    DefaultTableModel model;
    DefaultComboBoxModel modelCBB_LoaiTT;
    BanHangPanel pn;
    HoaDon hd;

    public BoxHoaDon(BanHangPanel pn, HoaDon hd) {
        this.pn = pn;
        this.hd = hd;
        initComponents();
        this.model = (DefaultTableModel) tblSanPham.getModel();
        this.modelCBB_LoaiTT = (DefaultComboBoxModel) cbbLoaiTT.getModel();
        this.daoHDChiTiet = new HoaDonChiTietDao();
        this.daoSP = new SanPhamDao();
        this.daoLoaiTT = new LoaiThanhToanDao();
        this.daoKH = new KhachHangDao();
        this.daoHD = new HoaDonDao();
        this.daoKM = new KhuyenMaiDao();
        this.daoADKM = new ApDungKMDao();
        this.fillCbbLoaiTT();
        this.fillTable();
        this.updateForm();
    }

    public void addSanPham(SanPham sp) {
        if (this.hd.getTrangThai() == 2) {
            DialogHelper.alert(null, "Không thể thêm sản phẩm vào hoá đơn đang giao!");
            return;
        }
        String maSP = sp.getMaSP();
        double donGia = this.getGiaSP(sp.getMaSP());
        for (int i = 0; i < tblSanPham.getRowCount(); i++) {
            if (maSP.equalsIgnoreCase(tblSanPham.getValueAt(i, 0).toString())) {
                int soLuong = (int) tblSanPham.getValueAt(i, 3) + 1;
                String ghiChu = tblSanPham.getValueAt(i, 5).toString();
                HoaDonChiTiet hdct = new HoaDonChiTiet(this.hd.getId(), sp.getId(), soLuong, donGia, ghiChu, true);
                this.updateHDCT(hdct);
                this.fillTable();
                this.getForm();
                this.updateForm();
                this.updateDoiDiemTL();
                return;
            }
        }
//        List<HoaDonChiTiet> list = this.daoHDChiTiet.selectByStatus(this.hd.getId());
//        for (HoaDonChiTiet hdct : list) {
//            if (hdct.getIdSP() == sp.getId()) {
//                this.updateHDCT(new HoaDonChiTiet(this.hd.getId(), sp.getId(), 1, donGia, "", true));
//                this.fillTable();
//                this.getForm();
//                this.updateForm();
//                return;
//            }
//        }
        HoaDonChiTiet hdct = new HoaDonChiTiet(this.hd.getId(), sp.getId(), 1, donGia, "", true);
        this.daoHDChiTiet.insert(hdct);
        this.fillTable();
        this.getForm();
        this.updateForm();
        this.updateDoiDiemTL();
    }

    public HoaDon getHoaDon() {
        return this.hd;
    }

    void updateHDCT(HoaDonChiTiet hdct) {
        try {
            this.daoHDChiTiet.update(hdct);
        } catch (Exception e) {
            DialogHelper.alert(null, "Cập nhật thất bại!");
        }
    }

    void updateHD() {
        try {
            this.daoHD.update(this.hd);
        } catch (Exception e) {
            DialogHelper.alert(null, "Cập nhật thất bại!");
        }
    }

    void getForm() {
        this.hd.setGhiChu(txtGhiChu.getText());
        LoaiThanhToan ltt = (LoaiThanhToan) modelCBB_LoaiTT.getSelectedItem();
        this.hd.setMaLTT(ltt.getMaLTT());
        this.hd.setTongTien(this.tongTienHD());
    }

    void updateClick() {

    }

    void updateForm() {
        try {
            if (this.hd.getIdKH() != null) {
                KhachHang kh = (KhachHang) daoKH.selectById(this.hd.getIdKH());
                if (kh != null) {
                    txtSoDT.setText(kh.getSdt());
                    txtHoTen.setText(kh.getHoTen());
                    txtDiemTL.setText("" + kh.getDiem());
                    if (this.hd.getTrangThai() == 2) {
                        txtSoDT.setEditable(false);
                        btnTimKH.setEnabled(false);
                        btnThemKH.setEnabled(false);
                        txtTienKhachTra.setEditable(false);
                        txtTienThua.setEditable(false);
                        btnTachHD.setEnabled(false);
                        btnGiaoHang.setEnabled(false);
                        txtDiemTL.setEnabled(false);
                        chkDoiDiem.setEnabled(false);
                        txtTienDoiDiem.setText(MoneyHelper.moneyToString(hd.getChiPhiKhac()));
                        txtTienKhachTra.setText(String.valueOf(hd.getTongTien()));
                        txtTienThua.setText("0");
                        txtDiaChi.setText(hd.getDiaChi());
                        btnThanhToan.setText("Giao hàng");
                    } else if (this.hd.getTrangThai() == 3) {
                        txtSoDT.setEditable(false);
                        btnTimKH.setEnabled(false);
                        btnThemKH.setEnabled(false);
                        txtTienKhachTra.setEditable(false);
                        txtTienThua.setEditable(false);
                        btnTachHD.setEnabled(false);
                        btnGiaoHang.setEnabled(false);
                        txtDiemTL.setEnabled(false);
                        chkDoiDiem.setEnabled(false);
                        txtTienDoiDiem.setText(MoneyHelper.moneyToString(hd.getChiPhiKhac()));
                        txtTienKhachTra.setText(String.valueOf(hd.getTongTien()));
                        txtTienThua.setText("0");
                        txtDiaChi.setText(hd.getDiaChi());
                        btnThanhToan.setText("Xác nhận");
                    } else {
                        txtSoDT.setEditable(true);
                        btnTimKH.setEnabled(true);
                        btnThemKH.setEnabled(true);
                        txtTienKhachTra.setEditable(true);
                        txtTienThua.setEditable(true);
                        btnTachHD.setEnabled(true);
                        btnGiaoHang.setEnabled(true);
                        txtTienKhachTra.setText("");
                        txtTienThua.setText("");
                        btnThanhToan.setText("Thanh toán");
                    }
                }
            }
            txtGhiChu.setText(hd.getGhiChu());
            for (int i = 0; i < modelCBB_LoaiTT.getSize(); i++) {
                LoaiThanhToan ltt = (LoaiThanhToan) modelCBB_LoaiTT.getElementAt(i);
                if (ltt.getMaLTT().equals(hd.getMaLTT())) {
                    cbbLoaiTT.setSelectedIndex(i);
                    break;
                }
            }
            int index = this.cbbLoaiTT.getSelectedIndex();
            if (index == 1 || (index == 0 && this.hd.getTrangThai() == 2) || (index == 0 && this.hd.getTrangThai() == 3)) {
                this.txtTienKhachTra.setEditable(false);
                this.txtTienKhachTra.setText(String.valueOf(hd.getTongTien()));
            }
            if (this.hd.getTrangThai() == 0) {
                lblTrangThai.setText("Chờ order");
            } else if (this.hd.getTrangThai() == 2) {
                lblTrangThai.setText("Chờ người giao");
            } else if (this.hd.getTrangThai() == 3) {
                lblTrangThai.setText("Đang giao");
            } else {
                lblTrangThai.setText("...");
            }
            txtTongTienSP.setText(MoneyHelper.moneyToString(this.tongTienSP()));
            txtTongTien.setText(MoneyHelper.moneyToString(this.tongTienHD()));
//            this.hd.setTongTien(this.tongTienHD());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fillCbbLoaiTT() {
        this.modelCBB_LoaiTT.removeAllElements();
        try {
            List<LoaiThanhToan> list = this.daoLoaiTT.getAll();
            for (LoaiThanhToan loaiTT : list) {
                this.modelCBB_LoaiTT.addElement(loaiTT);
            }
        } catch (Exception e) {
            DialogHelper.alert(null, "Lỗi fillCbbLoaiTT!");
        }
    }

    void fillTable() {
        this.model.setRowCount(0);
        try {
            List<HoaDonChiTiet> list = this.daoHDChiTiet.selectByInfo(this.hd.getId(), true);
            for (HoaDonChiTiet hdct : list) {
                int soLuong = hdct.getSoLuong();
                double donGia = hdct.getDonGia();
                double thanhTien = donGia * soLuong;
                SanPham sp = (SanPham) daoSP.selectById(hdct.getIdSP());
                model.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    MoneyHelper.moneyToString(hdct.getDonGia()),
                    soLuong,
                    MoneyHelper.moneyToString(thanhTien),
                    hdct.getGhiChu() != null ? hdct.getGhiChu() : ""
                });
            }
        } catch (Exception e) {
            DialogHelper.alert(null, "Lỗi fillTable!");
        }
    }

    void searchKH() {
        String keyword = txtSoDT.getText();
        try {
            List<KhachHang> list = daoKH.selectByInfo(keyword, true);
            if (list.size() > 0) {
                KhachHang kh = list.get(0);
                txtSoDT.setText(kh.getSdt());
                txtHoTen.setText(kh.getHoTen());
                txtDiemTL.setText("" + kh.getDiem());
                chkDoiDiem.setEnabled(true);
                this.hd.setIdKH(kh.getId());

                try {
                    this.daoHD.update(this.hd);
                    this.pn.fillTableHDCho();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                txtSoDT.setText("");
                txtHoTen.setText("");
                txtDiemTL.setText("");
                chkDoiDiem.setEnabled(false);
                this.hd.setIdKH(null);
                try {
                    this.daoHD.update(this.hd);
                    this.pn.fillTableHDCho();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DialogHelper.alert(null, "Không tìm thấy khách hàng!");
            }
        } catch (Exception e) {
        }
    }

    double tongTienSP() {
        double tongTien = 0;
        try {
            List<HoaDonChiTiet> list = this.daoHDChiTiet.selectByInfo(this.hd.getId(), true);
            for (HoaDonChiTiet hdct : list) {
                int soLuong = hdct.getSoLuong();
                double donGia = hdct.getDonGia();
                tongTien += donGia * soLuong;
            }
        } catch (Exception e) {
            DialogHelper.alert(null, "Lỗi tongTienSP!");
        }
        return tongTien;
    }

    double tongTienHD() {
        if (chkDoiDiem.isSelected()) {
            if (this.tongTienSP() > 0) {
                return this.tongTienSP() - this.tienDoiDiem();
            }
        }
        return this.tongTienSP() + this.hd.getChiPhiKhac();
    }

    void updateTienThua() {
        try {
            double tienKhachTra = Double.parseDouble(txtTienKhachTra.getText());
            if ((tienKhachTra - this.tongTienHD()) >= 0) {
                txtTienThua.setText(MoneyHelper.moneyToString((tienKhachTra - this.tongTienHD())));
            } else {
                txtTienThua.setText(MoneyHelper.moneyToString(0));
            }
        } catch (Exception e) {
            txtTienThua.setText(MoneyHelper.moneyToString(0));
        }
    }

    double tienDoiDiem() {
        if (chkDoiDiem.isSelected()) {
            try {
                int diem = Integer.parseInt(txtDiemTL.getText());
                if ((diem * 1000) >= this.tongTienSP()) {
                    return this.tongTienSP();
                } else {
                    return diem * 1000;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    void updateDoiDiemTL() {
        if (chkDoiDiem.isSelected()) {
            if (this.tienDoiDiem() >= this.tongTienHD()) {
                if (this.tienDoiDiem() == 0) {
                    txtTienDoiDiem.setText(MoneyHelper.moneyToString(0));
                } else {
                    txtTienDoiDiem.setText("-" + MoneyHelper.moneyToString(this.tienDoiDiem()));
                }
            } else {
                txtTienDoiDiem.setText("-" + MoneyHelper.moneyToString(this.tienDoiDiem()));
            }
        } else {
            txtTienDoiDiem.setText(MoneyHelper.moneyToString(0));
        }
    }

    void thanhToan() {
        if (!validateForm() || !checkTienKhachTra()) {
            return;
        }
        String xacNhan = "";
        if (this.hd.getTrangThai() == 0) {
            xacNhan = "Xác nhận thanh toán hóa đơn!";
        } else if (this.hd.getTrangThai() == 2) {
            xacNhan = "Xác nhận giao cho shipper!";
        } else if (this.hd.getTrangThai() == 3) {
            xacNhan = "Xác nhận giao thành công!";
        }
        if (DialogHelper.confirm(null, xacNhan)) {
            this.getForm();
            this.hd.setTgThanhToan(new Date());

            if (this.hd.getTrangThai() == 0) {
                this.hd.setTrangThai(1);
            } else if (this.hd.getTrangThai() == 2) {
                this.hd.setTrangThai(3);
            } else if (this.hd.getTrangThai() == 3) {
                this.hd.setTrangThai(4);
            }

            try {
                if (this.hd.getIdKH() == 0) {
                    this.hd.setIdKH(null);
                }
                if (chkDoiDiem.isSelected()) {
                    double tienDoiDiem = -this.tienDoiDiem();
                    this.hd.setChiPhiKhac(tienDoiDiem);
                }
                this.daoHD.update(this.hd);
                this.updateForm();
                this.pn.fillTableHDCho();
                this.pn.fillTableHDGiao();
                this.pn.closeTab();

                if (this.hd.getTrangThai() == 1 && this.hd.getIdKH() != null) {
                    KhachHang kh = (KhachHang) daoKH.selectById(this.hd.getIdKH());
                    if (chkDoiDiem.isSelected()) {
                        int diemDung = (int) (this.tienDoiDiem() / 1000);
                        int diemThem = (int) (this.hd.getTongTien() / 10000);
                        int diem = diemThem + (kh.getDiem() - diemDung);
                        kh.setDiem(diem);
                    } else {
                        int diemThem = (int) (this.hd.getTongTien() / 10000);
                        int diem = diemThem + kh.getDiem();
                        kh.setDiem(diem);
                    }
                    this.daoKH.update(kh);
                }

                DialogHelper.alert(null, "Thành công!");
                if (this.hd.getTrangThai() == 1) {
                    SocketClient.sendMessage("UPDATE_HD_ALL");
                    SocketClient.sendMessage("DIALOG_HDPC");
                } else {
                    SocketClient.sendMessage("UPDATE_LSHD");
                }
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(null, "Thất bại!");
            }
        }
    }

    void huyHoaDon() {
        if (!validateForm()) {
            return;
        }
        new HuyHDDialog(null, true, this.pn, this.hd).setVisible(true);
    }

    void giaoHang() {
        if (!validateForm()) {
            return;
        }
        if (this.hd.getTrangThai() == 2) {
            DialogHelper.alert(null, "Hoá đơn đang chờ shipper!");
            return;
        }
        if (this.hd.getTrangThai() == 3) {
            DialogHelper.alert(null, "Hoá đơn đang giao!");
            return;
        }
        new GiaoHangDialog(null, true, this.pn, this.hd).setVisible(true);
    }

    void tachHD() {
        if (!validateForm()) {
            return;
        }
        if (this.hd.getTrangThai() == 2) {
            DialogHelper.alert(null, "Hoá đơn đang chờ shipper!");
            return;
        }
        if (this.hd.getTrangThai() == 3) {
            DialogHelper.alert(null, "Hoá đơn đang giao!");
            return;
        }
        new TachHDDialog(null, true, this.pn, this).setVisible(true);
    }

    boolean validateForm() {
        if (tblSanPham.getRowCount() <= 0) {
            DialogHelper.alert(null, "Hoá đơn trống!");
            return false;
        }
        return true;
    }

    boolean checkTienKhachTra() {
        if (txtTienKhachTra.getText().length() == 0) {
            DialogHelper.alert(null, "Không để trống Tiền khách trả!");
            txtTienKhachTra.requestFocus();
            return false;
        }
        try {
            double tienKhachTra = Double.parseDouble(txtTienKhachTra.getText());
            if ((tienKhachTra - this.tongTienHD()) < 0) {
                DialogHelper.alert(null, "Tiền khách trả phải lớn hơn tổng tiền hoá đơn!");
                txtTienKhachTra.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(null, "Tiền khách trả phải là số!");
            txtTienKhachTra.requestFocus();
            return false;
        }
        return true;
    }

    double getGiaSP(String maSP) {
        double donGia = 0;
        try {
            SanPham sp = (SanPham) daoSP.selectByMa(maSP);
            List<ApDungKM> listADKM = daoADKM.selectByInfo(null, sp.getId(), "SP");
            donGia = sp.getDonGia();
            for (ApDungKM adkm : listADKM) {
                KhuyenMai km = (KhuyenMai) daoKM.selectById(adkm.getIdKM());
                long now = System.currentTimeMillis();
                if (now >= km.getNgayBD().getTime() && now <= km.getNgayKT().getTime()) {
                    if (km.isLoaiKM()) {
                        donGia = sp.getDonGia() - (sp.getDonGia() / 100 * km.getGiaTriKM());
                    } else {
                        donGia = sp.getDonGia() - km.getGiaTriKM();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return donGia;
    }

    private void tblSanPhamTableModelListener(javax.swing.event.TableModelEvent e) {
        if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
            String maSP = tblSanPham.getValueAt(e.getFirstRow(), 0).toString();
            int soLuong = (int) model.getValueAt(e.getFirstRow(), 3);
            String ghiChu = tblSanPham.getValueAt(e.getFirstRow(), 5).toString();
            SanPham sp = (SanPham) daoSP.selectByMa(maSP);
            double donGia = this.getGiaSP(sp.getMaSP());
            if (this.hd.getTrangThai() == 2) {
                this.fillTable();
                DialogHelper.alert(null, "Hoá đơn đang chờ người giao!");
            } else if (this.hd.getTrangThai() == 3) {
                // sửa hoá đơn khi đang giao
//                if (tblSanPham.getRowCount() == 1 && soLuong == 1) {
//                    DialogHelper.alert(null, "Không thể sửa sản phẩm khi tổng số lượng \n sản phẩm trong hoá đơn là 1!");
//                    return;
//                }
//                if (e.getColumn() == 6) {
//                    if (DialogHelper.confirm(null, "Bạn muốn sửa sản phẩm?")) {
//                        HoaDonChiTiet hdct = new HoaDonChiTiet(this.hd.getId(), sp.getId(), soLuong, donGia, ghiChu, true);
//                        new SuaHDCTDialog(null, true, this, hdct).setVisible(true);
//                    }
//                } else {
                    this.fillTable();
                    DialogHelper.alert(null, "Hoá đơn đang giao!");
//                }
            } else {
                if (e.getColumn() == 3) {
                    HoaDonChiTiet hdct = new HoaDonChiTiet(this.hd.getId(), sp.getId(), soLuong, donGia, ghiChu, true);
                    this.updateHDCT(hdct);
                    this.fillTable();
                    this.getForm();
                    this.updateForm();
                    this.updateDoiDiemTL();
                }
                if (e.getColumn() == 5) {
                    HoaDonChiTiet hdct = new HoaDonChiTiet(this.hd.getId(), sp.getId(), soLuong, donGia, ghiChu, true);
                    this.updateHDCT(hdct);
                    this.fillTable();
                }
                if (e.getColumn() == 6) {
                    if (DialogHelper.confirm(null, "Bạn có muốn loại bỏ sản phẩm khỏi danh sách?")) {
                        try {
//                            HoaDonChiTiet hdct = new HoaDonChiTiet(this.hd.getId(), sp.getId(), soLuong, donGia, ghiChu, false);
                            this.daoHDChiTiet.delete(this.hd.getId(), sp.getId(), soLuong, true);
                            this.fillTable();
                            this.getForm();
                            this.updateForm();
                            this.updateDoiDiemTL();
                            DialogHelper.alert(null, "Bỏ sản phẩm thành công!");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            DialogHelper.alert(null, "Bỏ sản phẩm thất bại!");
                        }

                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable() {
            public void updateUI() {
                super.updateUI();
                EventQueue.invokeLater(() -> {
                    TableColumn column = getColumnModel().getColumn(3);
                    column.setCellRenderer(new SpinnerRenderer());
                    column.setCellEditor(new SpinnerEditor());
                    column.setMinWidth(80);
                    column.setMaxWidth(80);
                    column = getColumnModel().getColumn(5);
                    column.setCellRenderer(new TableModelUI.TextFieldRenderer());
                    column.setCellEditor(new TableModelUI.TextFieldEditor());
                    column = getColumnModel().getColumn(6);
                    //if (hd.getTrangThai() == 3) {
                        //  column.setCellRenderer(new ButtonRenderer(0));
                        //  column.setCellEditor(new ButtonEditor(0));
                        //} else {
                        column.setCellRenderer(new ButtonRenderer(1));
                        column.setCellEditor(new ButtonEditor(1));
                        //}
                    column.setMinWidth(50);
                    column.setMaxWidth(50);
                    repaint();
                });
            }
        };
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtSoDT = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnThemKH = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        chkDoiDiem = new javax.swing.JCheckBox();
        txtDiemTL = new javax.swing.JTextField();
        txtTienDoiDiem = new javax.swing.JTextField();
        btnTimKH = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtTienThua = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTienKhachTra = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtTongTienSP = new javax.swing.JTextField();
        cbbLoaiTT = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        lblTrangThai1 = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnGiaoHang = new javax.swing.JButton();
        btnTachHD = new javax.swing.JButton();
        btnHuyHD = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnThanhToan = new javax.swing.JButton();

        setBackground(new java.awt.Color(249, 238, 232));

        tblSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Đơn giá", "Số lượng", "Thành tiền", "Ghi chú", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.getModel().addTableModelListener(new javax.swing.event.TableModelListener() {
            @Override
            public void tableChanged(javax.swing.event.TableModelEvent e) {
                tblSanPhamTableModelListener(e);
            }
        });
        tblSanPham.setRowHeight(25);
        jScrollPane2.setViewportView(tblSanPham);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBackground(new java.awt.Color(249, 238, 232));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtSoDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoDTKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("SĐT:");

        btnThemKH.setBackground(new java.awt.Color(238, 238, 238));
        btnThemKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemKH.setText("+");
        btnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKHActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Điểm tích lũy:");

        chkDoiDiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        chkDoiDiem.setText("Đổi điểm");
        chkDoiDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDoiDiemActionPerformed(evt);
            }
        });

        txtDiemTL.setEditable(false);

        txtTienDoiDiem.setEditable(false);

        btnTimKH.setBackground(new java.awt.Color(238, 238, 238));
        btnTimKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTimKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/TimKiem.png"))); // NOI18N
        btnTimKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKHActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Họ tên:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane4.setViewportView(txtGhiChu);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Ghi chú:");

        txtHoTen.setEditable(false);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Tiền khác:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTienDoiDiem)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtHoTen)
                                    .addComponent(txtSoDT))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnTimKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnThemKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(txtDiemTL)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chkDoiDiem))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel6))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTimKH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(jLabel13)
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(chkDoiDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiemTL, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTienDoiDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnThemKH, txtHoTen});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnTimKH, txtSoDT});

        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(249, 238, 232));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtTienThua.setEditable(false);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Tiền thừa:");

        txtTienKhachTra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachTraKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Tiền khách trả:");

        txtTongTien.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Tổng tiền hoá đơn:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Tổng tiền sản phẩm:");

        txtTongTienSP.setEditable(false);

        cbbLoaiTT.setBackground(new java.awt.Color(141, 110, 99));
        cbbLoaiTT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbbLoaiTT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbLoaiTTItemStateChanged(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Loại TT:");

        lblTrangThai1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTrangThai1.setText("Trạng thái: ");

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTrangThai.setText("Chờ người giao");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Địa chỉ:");

        txtDiaChi.setEditable(false);
        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane5.setViewportView(txtDiaChi);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTongTien)
                    .addComponent(txtTongTienSP)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbLoaiTT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienKhachTra, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel7))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblTrangThai1)
                                .addComponent(txtTienThua)
                                .addComponent(lblTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongTienSP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienKhachTra, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lblTrangThai1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbLoaiTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3);

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        jPanel5.setBackground(new java.awt.Color(249, 238, 232));

        btnGiaoHang.setBackground(new java.awt.Color(238, 238, 238));
        btnGiaoHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGiaoHang.setText("Giao hàng");
        btnGiaoHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGiaoHangActionPerformed(evt);
            }
        });

        btnTachHD.setBackground(new java.awt.Color(238, 238, 238));
        btnTachHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTachHD.setText("Tách");
        btnTachHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTachHDActionPerformed(evt);
            }
        });

        btnHuyHD.setBackground(new java.awt.Color(238, 238, 238));
        btnHuyHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuyHD.setText("Huỷ");
        btnHuyHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(btnHuyHD, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTachHD, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGiaoHang, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuyHD, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTachHD, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGiaoHang, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(249, 238, 232));

        btnThanhToan.setBackground(new java.awt.Color(238, 238, 238));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        // TODO add your handling code here:
        new KhachHangDialog(null, true).setVisible(true);
    }//GEN-LAST:event_btnThemKHActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        this.thanhToan();
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnHuyHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHDActionPerformed
        // TODO add your handling code here:
        this.huyHoaDon();
    }//GEN-LAST:event_btnHuyHDActionPerformed

    private void chkDoiDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDoiDiemActionPerformed
        // TODO add your handling code here:
        this.updateDoiDiemTL();
        this.updateForm();
    }//GEN-LAST:event_chkDoiDiemActionPerformed

    private void btnTachHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTachHDActionPerformed
        // TODO add your handling code here:
        this.tachHD();
    }//GEN-LAST:event_btnTachHDActionPerformed

    private void btnTimKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKHActionPerformed
        // TODO add your handling code here:
        this.searchKH();
    }//GEN-LAST:event_btnTimKHActionPerformed

    private void txtTienKhachTraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachTraKeyReleased
        // TODO add your handling code here:
        this.updateTienThua();
    }//GEN-LAST:event_txtTienKhachTraKeyReleased

    private void cbbLoaiTTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbLoaiTTItemStateChanged
        // TODO add your handling code here:
        int index = this.cbbLoaiTT.getSelectedIndex();
        if (index == 1 || (index == 0 && this.hd.getTrangThai() == 2) || (index == 0 && this.hd.getTrangThai() == 3)) {
            this.txtTienKhachTra.setEditable(false);
            this.txtTienKhachTra.setText(String.valueOf(this.tongTienHD()));
        } else {
            this.txtTienKhachTra.setEditable(true);
            this.txtTienKhachTra.setText("");
        }
        if (this.hd.getMaLTT() == null) {
            LoaiThanhToan ltt = (LoaiThanhToan) modelCBB_LoaiTT.getSelectedItem();
            this.hd.setMaLTT(ltt.getMaLTT());
        }
        this.updateTienThua();
    }//GEN-LAST:event_cbbLoaiTTItemStateChanged

    private void btnGiaoHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGiaoHangActionPerformed
        // TODO add your handling code here:
        this.giaoHang();
    }//GEN-LAST:event_btnGiaoHangActionPerformed

    private void txtSoDTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoDTKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            this.searchKH();
        }
    }//GEN-LAST:event_txtSoDTKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGiaoHang;
    private javax.swing.JButton btnHuyHD;
    private javax.swing.JButton btnTachHD;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnTimKH;
    private javax.swing.JComboBox<String> cbbLoaiTT;
    private javax.swing.JCheckBox chkDoiDiem;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JLabel lblTrangThai1;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtDiemTL;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtSoDT;
    private javax.swing.JTextField txtTienDoiDiem;
    private javax.swing.JTextField txtTienKhachTra;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTongTien;
    private javax.swing.JTextField txtTongTienSP;
    // End of variables declaration//GEN-END:variables
}
