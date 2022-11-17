package ManagementInterfaceDone;

import Dao.KhuyenMaiDao;
import Dao.SanPhamDao;
import Dao.ApDungKMDao;
import Dao.BaseDaoInterface;
import Dao.LoaiSanPhamDao;
import Entity.KhuyenMai;
import Entity.SanPham;
import Entity.ApDungKM;
import Entity.LoaiSanPham;
import Helper.DialogHelper;
import Helper.MoneyHelper;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class KhuyenMaiPanel extends javax.swing.JPanel implements TableModelListener {

    BaseDaoInterface daoKM, daoADKM, daoSP, daoLSP;
    DefaultTableModel modelTable_KM, modelTable_SP;
    DefaultComboBoxModel modelCBB_LSP;
    int row = -1;

    public KhuyenMaiPanel() {
        initComponents();
        this.daoKM = (BaseDaoInterface) new KhuyenMaiDao();
        this.daoADKM = (BaseDaoInterface) new ApDungKMDao();
        this.daoSP = (BaseDaoInterface) new SanPhamDao();
        this.daoLSP = (BaseDaoInterface) new LoaiSanPhamDao();
        this.modelTable_KM = (DefaultTableModel) this.tblKM.getModel();
        this.modelTable_SP = (DefaultTableModel) this.tblSP.getModel();
        this.modelCBB_LSP = (DefaultComboBoxModel) this.cbbLoaiSP.getModel();

        this.tblSP.getModel().addTableModelListener(this);
        this.fillCboLoaiSP();
        this.loadTableKM();
        this.loadTableSP();
        this.apDungKM();
    }

    void apDungKM() {
        List<KhuyenMai> listKM = this.daoKM.getAll();
        for (KhuyenMai km : listKM) {
            //getDate
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            if (date.after(km.getNgayBD()) && date.before(km.getNgayKT())) {
                this.daoKM.updateStatus(1, km.getMaKM());
            } else if (date.equals(km.getNgayBD()) || date.equals(km.getNgayKT())) {
                this.daoKM.updateStatus(1, km.getMaKM());
            } else {
                this.daoKM.updateStatus(0, km.getMaKM());
            }
        }
    }

    void fillCboLoaiSP() {
        this.modelCBB_LSP.removeAllElements();
        this.modelCBB_LSP.addElement("Tất cả");
        try {
            List<LoaiSanPham> list = this.daoLSP.getAll();
            for (LoaiSanPham lsp : list) {
                this.modelCBB_LSP.addElement(lsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu combo loaisp");
        }
    }

    void fillTableSPThuocKM() {
        KhuyenMai km = (KhuyenMai) this.daoKM.selectByMa(this.txtMaKM.getText());
        List<ApDungKM> listSPKM = (List<ApDungKM>) this.daoADKM.selectByInfo(km.getId(), null, "KM");
        if (listSPKM != null) {
            this.modelTable_SP.setRowCount(0);
            for (ApDungKM adkm : listSPKM) {
                int idSP = adkm.getIdSP();
                SanPham sp = (SanPham) this.daoSP.selectById(idSP);
                double donGia = sp.getDonGia();
                if (km.isLoaiKM()) {
                    donGia = sp.getDonGia() - (sp.getDonGia() / 100 * km.getGiaTriKM());
                } else {
                    donGia = sp.getDonGia() - km.getGiaTriKM();
                }
                this.modelTable_SP.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    MoneyHelper.moneyToString(sp.getDonGia()),
                    MoneyHelper.moneyToString(donGia)
                });
            }
            this.checkBoxSP(listSPKM);
        } else {
            this.modelTable_SP.setRowCount(0);
        }
    }

    void fillTableSPKM() {
        this.loadTableSP();
        KhuyenMai km = (KhuyenMai) this.daoKM.selectByMa(this.txtMaKM.getText());
        if (km != null) {
            for (int i = 0; i < this.tblSP.getRowCount(); i++) {
                String maSP = (String) this.tblSP.getValueAt(i, 0);
                SanPham sp = (SanPham) this.daoSP.selectByMa(maSP);
                double donGia = sp.getDonGia();
                if (km.isLoaiKM()) {
                    donGia = sp.getDonGia() - (sp.getDonGia() / 100 * km.getGiaTriKM());
                } else {
                    donGia = sp.getDonGia() - km.getGiaTriKM();
                }
                List<ApDungKM> listADKM = this.daoADKM.selectByInfo(km.getId(), null, "KM");
                if (listADKM != null) {
                    for (ApDungKM adkm : listADKM) {
                        if (sp.getId() == adkm.getIdSP()) {
                            this.tblSP.setValueAt(true, i, 4);
                        }
                    }
                }
                this.tblSP.setValueAt(MoneyHelper.moneyToString(donGia), i, 3);
            }
        }
    }

    void fillTableSP(List<SanPham> list) {
        this.modelTable_SP.setRowCount(0);
        try {
            for (SanPham sp : list) {
                this.modelTable_SP.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    MoneyHelper.moneyToString(sp.getDonGia())
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadTableSP() {
        String keyword = this.txtTimKiemSP.getText();
        int loai = this.cbbLoaiSP.getSelectedIndex();
        int trangThai = 1;
        if (loai == 0) {
            List<SanPham> list = this.daoSP.selectByInfo(keyword, null, trangThai, "KW_TT");
            this.fillTableSP(list);
            return;
        }
        if (loai != 0) {
            LoaiSanPham lsp = (LoaiSanPham) this.cbbLoaiSP.getSelectedItem();
            List<SanPham> list = this.daoSP.selectByInfo(keyword, lsp.getId(), 1, "KW_ID_TT");
            this.fillTableSP(list);
            return;
        }
    }

    void fillTableKM(List<KhuyenMai> list) {
        this.modelTable_KM.setRowCount(0);
        try {
            for (KhuyenMai km : list) {
                this.modelTable_KM.addRow(new Object[]{
                    km.getMaKM(),
                    km.getTenKM(),
                    km.isLoaiKM() ? "%" : "Tiền",
                    km.getGiaTriKM(),
                    km.isTrangThai() ? "Ðang áp dụng" : "Ngừng áp dụng"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void loadTableKM() {
        String keyword = this.txtTimKiemKM.getText();
        int loai = this.cbbLocLKM.getSelectedIndex();
        int trangThai = this.cbbLocTT.getSelectedIndex();
        if (loai == 0 && trangThai == 0) {
            List<KhuyenMai> list = this.daoKM.selectByInfo(keyword, null, null, "KW");
            this.fillTableKM(list);
            return;
        }
        if (loai == 0 && trangThai != 0) {
            List<KhuyenMai> list = this.daoKM.selectByInfo(keyword, null, trangThai - 1, "KW_TT");
            this.fillTableKM(list);
            return;
        }
        if (loai != 0 && trangThai == 0) {
            List<KhuyenMai> list = this.daoKM.selectByInfo(keyword, loai - 1, null, "KW_LKM");
            this.fillTableKM(list);
            return;
        }
        if (loai != 0 && trangThai != 0) {
            List<KhuyenMai> list = this.daoKM.selectByInfo(keyword, loai - 1, trangThai - 1, "KW_LKM_TT");
            this.fillTableKM(list);
            return;

        }
    }

    void setForm(KhuyenMai km) {
        this.txtMaKM.setText(km.getMaKM());
        this.txtTenKM.setText(km.getTenKM());
        this.cbbLoaiKM.setSelectedItem(km.isLoaiKM() ? "%" : "Tiền");
        this.txtGiaTri.setText(String.valueOf(km.getGiaTriKM()));
        this.txtNgayBatDau.setDate(km.getNgayBD());
        this.txtNgayKetThuc.setDate(km.getNgayKT());
        this.cbbTrangThai.setSelectedItem(km.isTrangThai() ? "Ðang áp dụng" : "Ngừng áp dụng");
    }

    KhuyenMai getForm() {
        KhuyenMai km = new KhuyenMai();
        km.setMaKM(this.txtMaKM.getText());
        km.setTenKM(this.txtTenKM.getText());
        km.setLoaiKM(this.cbbLoaiKM.getSelectedItem().toString().equalsIgnoreCase("%") ? true : false);
        km.setGiaTriKM(Integer.parseInt(this.txtGiaTri.getText()));
        km.setNgayBD(this.txtNgayBatDau.getDate());
        km.setNgayKT(this.txtNgayKetThuc.getDate());
        km.setTrangThai(this.cbbTrangThai.getSelectedItem().toString().equalsIgnoreCase("Đang áp dụng") ? true : false);
        return km;
    }

    void edit() {
        String maKM = (String) this.tblKM.getValueAt(this.row, 0);
        KhuyenMai km = (KhuyenMai) this.daoKM.selectByMa(maKM);
        this.setForm(km);
    }

    //DANH SÁCH SẢN PHẨM ĐƯỢC CHỌN
    List<SanPham> spDuocChon() {
        int row = this.tblSP.getRowCount();
        List<SanPham> listSP = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            Object chkBox = this.tblSP.getValueAt(i, 4);
            if (chkBox != null) {
                boolean check = (boolean) chkBox;
                if (check) {
                    String maSP = (String) this.tblSP.getValueAt(i, 0);
                    SanPham sp = (SanPham) this.daoSP.selectByMa(maSP);
                    listSP.add(sp);
                }
            }
        }
        return listSP;
    }

    //DANH SÁCH SẢN PHẨM KO ĐƯỢC CHỌN
    List<SanPham> spKhongDuocChon() {
        int row = this.tblSP.getRowCount();
        List<SanPham> listSP = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            Object chkBox = this.tblSP.getValueAt(i, 4);
            if (chkBox != null) {
                boolean check = (boolean) chkBox;
                if (!check) {
                    String maSP = (String) this.tblSP.getValueAt(i, 0);
                    SanPham sp = (SanPham) this.daoSP.selectByMa(maSP);
                    listSP.add(sp);
                }
            }
        }
        return listSP;
    }

    //INSERT, UPDATE KM -START
    private void clearForm() {
        this.txtMaKM.setText("");
        this.txtTenKM.setText("");
        this.cbbLoaiKM.setSelectedIndex(0);
        this.txtGiaTri.setText("");
        this.txtNgayBatDau.setDate(null);
        this.txtNgayKetThuc.setDate(null);
        this.cbbTrangThai.setSelectedIndex(0);
    }

    void insertVsDeleteADKM() {
        try {
            KhuyenMai km = (KhuyenMai) this.daoKM.selectByMa(this.txtMaKM.getText());
            List<SanPham> listSP = this.spDuocChon();
            List<ApDungKM> listADKM = this.daoADKM.selectByInfo(km.getId(), null, "KM");
            List<SanPham> listSP2 = new ArrayList<>();
            List<ApDungKM> listADKM2 = new ArrayList<>();
            for (SanPham sp : listSP) {
                for (int i = 0; i < listADKM.size(); i++) {
                    ApDungKM adkm = listADKM.get(i);
                    if (adkm.getIdSP() == sp.getId()) {
                        listSP2.add(sp);
                        listADKM2.add(adkm);
                    }
                }
            }
            listSP.removeAll(listSP2);
            for (SanPham sp : listSP) {
                ApDungKM adkm = new ApDungKM(km.getId(), sp.getId());
                this.daoADKM.insert(adkm);
//                System.out.println("Insert: " + km.getId() + " - " + sp.getId());
            }
            listADKM.removeAll(listADKM2);
            for (ApDungKM adkm : listADKM) {
                this.daoADKM.delete(adkm.getIdKM(), adkm.getIdSP());
//                System.out.println("Delete: " + km.getId() + " - " + adkm.getIdSP());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    void insert() {
        if (DialogHelper.confirm(this, "Xác nhận thêm khuyến mại")) {
            KhuyenMai km = getForm();
            try {
                this.daoKM.insert(km);
                this.insertVsDeleteADKM();
                this.apDungKM();
                this.loadTableKM();
                this.loadTableSP();
                this.clearForm();
                DialogHelper.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(this, "Thêm mới thất bại!");
            }
        }
    }

    void update() {
        if (this.txtMaKM.getText().trim().isEmpty()) {
            DialogHelper.alert(this, "Chưa thấy mã khuyến mại!");
            return;
        }
        if (DialogHelper.confirm(this, "Xác nhận cập nhật khuyến mại")) {
            KhuyenMai km = getForm();
            try {
                this.daoKM.update(km);
                this.insertVsDeleteADKM();
                this.apDungKM();
                this.loadTableKM();
                this.loadTableSP();
                this.clearForm();
                DialogHelper.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }
    //INSERT, UPDATE KM -END

    //XỬ LÍ CHECKBOX 
    void CheckBoxTatCa() {
        int row = this.tblSP.getRowCount();
        if (this.ckTatCa.isSelected()) {
            for (int i = 0; i < row; i++) {
                this.tblSP.setValueAt(true, i, 4);
            }
        } else {
            for (int i = 0; i < row; i++) {
                this.tblSP.setValueAt(false, i, 4);
            }
        }
    }

    void checkBoxSP(List<ApDungKM> list) {
        for (int i = 0; i < list.size(); i++) {
            this.tblSP.setValueAt(true, i, 4);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMaKM = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTenKM = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbbLoaiKM = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtGiaTri = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbbTrangThai = new javax.swing.JComboBox<>();
        txtNgayBatDau = new com.toedter.calendar.JDateChooser();
        txtNgayKetThuc = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKM = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTimKiemKM = new javax.swing.JTextField();
        cbbLocLKM = new javax.swing.JComboBox<>();
        cbbLocTT = new javax.swing.JComboBox<>();
        btnTimKM = new javax.swing.JButton();
        txtTimKiemSP = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnTimSP = new javax.swing.JButton();
        cbbLoaiSP = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        ckTatCa = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();

        setBackground(new java.awt.Color(249, 238, 232));
        setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Mã khuyến mại:");

        txtMaKM.setEditable(false);
        txtMaKM.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Tên khuyến mại:");

        txtTenKM.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Loại khuyến mại:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Giá trị:");

        cbbLoaiKM.setBackground(new java.awt.Color(141, 110, 99));
        cbbLoaiKM.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbbLoaiKM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "%", "Tiền" }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Ngày bắt đầu:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Ngày kết thúc:");

        txtGiaTri.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText(" Trạng thái:");

        cbbTrangThai.setBackground(new java.awt.Color(141, 110, 99));
        cbbTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang áp dụng", "Ngừng áp dụng" }));

        txtNgayBatDau.setBackground(new java.awt.Color(249, 238, 232));
        txtNgayBatDau.setDateFormatString("dd-M-yyyy");
        txtNgayBatDau.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ((com.toedter.calendar.JTextFieldDateEditor) txtNgayBatDau.getDateEditor()).setEditable(false);

        txtNgayKetThuc.setBackground(new java.awt.Color(249, 238, 232));
        txtNgayKetThuc.setDateFormatString("dd-M-yyyy");
        txtNgayKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ((com.toedter.calendar.JTextFieldDateEditor) txtNgayKetThuc.getDateEditor()).setEditable(false);

        jPanel3.setBackground(new java.awt.Color(249, 238, 232));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnThem.setBackground(new java.awt.Color(238, 238, 238));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel3.add(btnThem);

        btnCapNhat.setBackground(new java.awt.Color(238, 238, 238));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        jPanel3.add(btnCapNhat);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/SaleBig.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(54, 54, 54)
                                    .addComponent(jLabel13)
                                    .addGap(200, 200, 200))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMaKM)
                                        .addComponent(txtTenKM)
                                        .addComponent(cbbLoaiKM, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtGiaTri)
                                        .addComponent(txtNgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbbTrangThai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
                                    .addGap(6, 6, 6))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel7)
                                .addComponent(jLabel6))
                            .addGap(5, 5, 5)))
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTenKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbLoaiKM, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtGiaTri, txtNgayBatDau, txtNgayKetThuc});

        add(jPanel1);

        jPanel2.setBackground(new java.awt.Color(249, 238, 232));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setToolTipText("");

        tblKM.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblKM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã KM", "Tên KM", "Loại KM", "Giá trị", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKM.setRowHeight(30);
        tblKM.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKMMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKM);
        if (tblKM.getColumnModel().getColumnCount() > 0) {
            tblKM.getColumnModel().getColumn(0).setResizable(false);
            tblKM.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblKM.getColumnModel().getColumn(1).setResizable(false);
            tblKM.getColumnModel().getColumn(1).setPreferredWidth(160);
            tblKM.getColumnModel().getColumn(2).setResizable(false);
            tblKM.getColumnModel().getColumn(2).setPreferredWidth(20);
            tblKM.getColumnModel().getColumn(3).setResizable(false);
            tblKM.getColumnModel().getColumn(3).setPreferredWidth(6);
            tblKM.getColumnModel().getColumn(4).setResizable(false);
            tblKM.getColumnModel().getColumn(4).setPreferredWidth(70);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Tìm khuyến mại");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Trạng thái");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText(" Loại KM");

        txtTimKiemKM.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTimKiemKM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKMKeyPressed(evt);
            }
        });

        cbbLocLKM.setBackground(new java.awt.Color(141, 110, 99));
        cbbLocLKM.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbbLocLKM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Tiền", "%" }));
        cbbLocLKM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbLocLKMItemStateChanged(evt);
            }
        });

        cbbLocTT.setBackground(new java.awt.Color(141, 110, 99));
        cbbLocTT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbbLocTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Ngừng áp dụng", "Đang áp dụng" }));
        cbbLocTT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbLocTTItemStateChanged(evt);
            }
        });

        btnTimKM.setBackground(new java.awt.Color(238, 238, 238));
        btnTimKM.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnTimKM.setText("Tìm");
        btnTimKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKMActionPerformed(evt);
            }
        });

        txtTimKiemSP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTimKiemSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemSPKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel11.setText("Tìm sản phẩm");

        btnTimSP.setBackground(new java.awt.Color(238, 238, 238));
        btnTimSP.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnTimSP.setText("Tìm");
        btnTimSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimSPActionPerformed(evt);
            }
        });

        cbbLoaiSP.setBackground(new java.awt.Color(141, 110, 99));
        cbbLoaiSP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbbLoaiSP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbLoaiSPItemStateChanged(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel12.setText(" Loại sản phẩm");

        ckTatCa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ckTatCa.setText("Tất cả ");
        ckTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckTatCaActionPerformed(evt);
            }
        });

        tblSP.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Giá cũ", "Giá mới", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSP.setRowHeight(30);
        tblSP.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblSP);
        if (tblSP.getColumnModel().getColumnCount() > 0) {
            tblSP.getColumnModel().getColumn(0).setResizable(false);
            tblSP.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblSP.getColumnModel().getColumn(1).setResizable(false);
            tblSP.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTimSP)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbbLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ckTatCa)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtTimKiemKM, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnTimKM)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbLocLKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cbbLocTT, 0, 1, Short.MAX_VALUE)))
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(16, 16, 16))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemKM, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbLocLKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKM, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(cbbLocTT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTimSP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbbLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ckTatCa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbbLocLKM, cbbLocTT});

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    public boolean check() {
        if (this.txtTenKM.getText().equals("")
                || this.txtNgayKetThuc.getDate().equals("")
                || this.txtNgayBatDau.getDate().equals("")
                || this.txtGiaTri.getText().equals("")) {
            DialogHelper.alert(this, "Không được để trống thông tin");
            return false;
        }

        try {
            int gt = Integer.parseInt(this.txtGiaTri.getText());
            if (gt < 0 || gt > 100) {
                DialogHelper.alert(this, "Giá trị khuyến mại từ 1 -100");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Giá trị khuyến mại phải là một số");
            return false;
        }
        return true;
    }

    boolean checkTrungTen() {
        try {
            String tenKM = this.txtTenKM.getText().trim();
            List<KhuyenMai> listKM = this.daoKM.getAll();
            for (KhuyenMai km : listKM) {
                if (km.getTenKM().trim().equalsIgnoreCase(tenKM)) {
                    DialogHelper.alert(this, "Tên khuyến mại đã tồn tại!");
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void cbbLocLKMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbLocLKMItemStateChanged
        this.loadTableKM();
    }//GEN-LAST:event_cbbLocLKMItemStateChanged

    private void cbbLocTTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbLocTTItemStateChanged
        this.loadTableKM();
    }//GEN-LAST:event_cbbLocTTItemStateChanged

    private void btnTimKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKMActionPerformed
        this.loadTableKM();
    }//GEN-LAST:event_btnTimKMActionPerformed

    private void tblKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKMMouseClicked
        if (evt.getClickCount() == 2) {
            clearForm();
            this.row = this.tblKM.getSelectedRow();
            this.edit();
            this.fillTableSPThuocKM();
        }
    }//GEN-LAST:event_tblKMMouseClicked

    private void btnTimSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimSPActionPerformed
        ckTatCa.setSelected(false);
        this.fillTableSPKM();
    }//GEN-LAST:event_btnTimSPActionPerformed

    private void cbbLoaiSPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbLoaiSPItemStateChanged
        ckTatCa.setSelected(false);
        this.fillTableSPKM();
    }//GEN-LAST:event_cbbLoaiSPItemStateChanged

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (!check()) {
            return;
        }
        this.update();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (!check() || !checkTrungTen()) {
            return;
        }
        this.insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void ckTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckTatCaActionPerformed
        this.CheckBoxTatCa();
    }//GEN-LAST:event_ckTatCaActionPerformed

    private void txtTimKiemKMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKMKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.loadTableKM();
        }
    }//GEN-LAST:event_txtTimKiemKMKeyPressed

    private void txtTimKiemSPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSPKeyPressed
        ckTatCa.setSelected(false);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // this.loadTableSP();
            this.fillTableSPKM();
        }
    }//GEN-LAST:event_txtTimKiemSPKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKM;
    private javax.swing.JButton btnTimSP;
    private javax.swing.JComboBox<String> cbbLoaiKM;
    private javax.swing.JComboBox<String> cbbLoaiSP;
    private javax.swing.JComboBox<String> cbbLocLKM;
    private javax.swing.JComboBox<String> cbbLocTT;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JCheckBox ckTatCa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblKM;
    private javax.swing.JTable tblSP;
    private javax.swing.JTextField txtGiaTri;
    private javax.swing.JTextField txtMaKM;
    private com.toedter.calendar.JDateChooser txtNgayBatDau;
    private com.toedter.calendar.JDateChooser txtNgayKetThuc;
    private javax.swing.JTextField txtTenKM;
    private javax.swing.JTextField txtTimKiemKM;
    private javax.swing.JTextField txtTimKiemSP;
    // End of variables declaration//GEN-END:variables

    @Override
    public void tableChanged(TableModelEvent e) {
        int column = e.getColumn();
        if (column != 4) {
            return;
        }
        int dong = e.getFirstRow();
        TableModel model = (TableModel) e.getSource();
        Object data = model.getValueAt(dong, column);
        if ((boolean) data == false) {
            ckTatCa.setSelected(false);
        }
    }
}
