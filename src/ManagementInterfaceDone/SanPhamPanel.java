package ManagementInterfaceDone;

import Dao.BaseDaoInterface;
import Dao.LoaiSanPhamDao;
import Dao.SanPhamDao;
import Entity.LoaiSanPham;
import Entity.SanPham;
import Helper.MoneyHelper;
import Helper.DialogHelper;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class SanPhamPanel extends javax.swing.JPanel {

    BaseDaoInterface daoSP, daoLoaiSP;
    DefaultTableModel modelTable;
    DefaultComboBoxModel modelCBB_LoaiSP, modelCBB_LocLoaiSP;
    int row = -1;

    public SanPhamPanel() {
        initComponents();
        this.daoSP = (BaseDaoInterface) new SanPhamDao();
        this.daoLoaiSP = (BaseDaoInterface) new LoaiSanPhamDao();
        this.modelTable = (DefaultTableModel) this.tblSanPham.getModel();
        this.modelCBB_LoaiSP = (DefaultComboBoxModel) this.cbbLoaiSP.getModel();
        this.modelCBB_LocLoaiSP = (DefaultComboBoxModel) this.cbbLocLoaiSP.getModel();
        this.fillCbbLoaiSP();
        this.fillCbbLocLoaiSP();
        this.loadTable();
    }

    void fillCbbLoaiSP() {
        this.modelCBB_LoaiSP.removeAllElements();
        try {
            List<LoaiSanPham> list = this.daoLoaiSP.getAll();
            for (LoaiSanPham lsp : list) {
                this.modelCBB_LoaiSP.addElement(lsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu CBB");
        }
    }

    void fillCbbLocLoaiSP() {
        this.modelCBB_LocLoaiSP.removeAllElements();
        this.modelCBB_LocLoaiSP.addElement("Tất cả");
        try {
            List<LoaiSanPham> list = this.daoLoaiSP.getAll();
            for (LoaiSanPham lsp : list) {
                this.modelCBB_LocLoaiSP.addElement(lsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu CBB");
        }
    }

    void fillTable(List<SanPham> list) {
        this.modelTable.setRowCount(0);
        try {
            for (SanPham sp : list) {
                LoaiSanPham loaiSP = (LoaiSanPham) this.daoLoaiSP.selectById(sp.getIdLSP());
                String donGia = MoneyHelper.moneyToString(sp.getDonGia());
                this.modelTable.addRow(new Object[]{
                    sp.getMaSP().trim(),
                    sp.getTenSP(),
                    loaiSP.getTenLSP(),
                    donGia,
                    sp.isTrangThai() ? "Đang bán" : "Ngừng bán"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void loadTable() {
        String keyword = this.txtTimKiem.getText();
        int loai = this.cbbLocLoaiSP.getSelectedIndex();
        int trangThai = this.cbbLocTrangThai.getSelectedIndex();
        if (loai == 0 && trangThai == 0) {
            List<SanPham> list = this.daoSP.selectByInfo(keyword, null, null, "KW");
            this.fillTable(list);
            return;
        }
        if (loai == 0 && trangThai != 0) {
            List<SanPham> list = this.daoSP.selectByInfo(keyword, null, trangThai - 1, "KW_TT");
            this.fillTable(list);
            return;
        }
        if (loai != 0 && trangThai == 0) {
            LoaiSanPham lsp = (LoaiSanPham) this.cbbLocLoaiSP.getSelectedItem();
            List<SanPham> list = this.daoSP.selectByInfo(keyword, lsp.getId(), null, "KW_ID");
            this.fillTable(list);
            return;
        }
        if (loai != 0 && trangThai != 0) {
            LoaiSanPham lsp = (LoaiSanPham) this.cbbLocLoaiSP.getSelectedItem();
            List<SanPham> list = this.daoSP.selectByInfo(keyword, lsp.getId(), trangThai - 1, "KW_ID_TT");
            this.fillTable(list);
            return;

        }
    }

    public void UpHinh(String image) {
        ImageIcon i = new ImageIcon("Images\\" + image);
        Image img = i.getImage();
        ImageIcon icon = new ImageIcon(img.getScaledInstance(130, 130, img.SCALE_SMOOTH));
        this.lblHinh.setIcon(icon);
    }

    void setForm(SanPham sp) {
        this.txtMaSP.setText(sp.getMaSP());
        this.txtTenSP.setText(sp.getTenSP());
        for (int i = 0; i < this.modelCBB_LoaiSP.getSize(); i++) {
            LoaiSanPham lsp = (LoaiSanPham) this.modelCBB_LoaiSP.getElementAt(i);
            if (lsp.getId() == sp.getIdLSP()) {
                this.cbbLoaiSP.setSelectedIndex(i);
                break;
            }
        }
        this.txtDonGia.setText(String.valueOf(Math.round(sp.getDonGia())));
        this.txtMota.setText(sp.getMoTa());
        this.txtGhiChu.setText(sp.getGhiChu());
        this.cbbTrangThai.setSelectedItem(sp.isTrangThai() ? "Đang bán" : "Ngừng bán");
        this.UpHinh(sp.getHinh());
        this.tblSanPham.setRowSelectionInterval(row, row);
    }

    SanPham getForm() {
        SanPham sp = new SanPham();
        sp.setMaSP(this.txtMaSP.getText());
        sp.setTenSP(this.txtTenSP.getText());
        LoaiSanPham lsp = (LoaiSanPham) this.modelCBB_LoaiSP.getSelectedItem();
        sp.setIdLSP(lsp.getId());
        sp.setDonGia(Double.parseDouble(this.txtDonGia.getText()));
        sp.setMoTa(this.txtMota.getText());
        sp.setGhiChu(this.txtGhiChu.getText());
        sp.setTrangThai(this.cbbTrangThai.getSelectedIndex() == 0 ? true : false);
        sp.setHinh(this.lblHinh.getText());
        return sp;
    }

    void clearForm() {
        this.txtMaSP.setText("");
        this.txtTenSP.setText("");
        this.txtDonGia.setText("");
        this.txtMota.setText("");
        this.txtGhiChu.setText("");
        this.cbbLoaiSP.setSelectedIndex(0);
        this.cbbTrangThai.setSelectedIndex(0);
        this.lblHinh.setText("");
        this.lblHinh.setSize(130, 130);
        this.lblHinh.setIcon(null);
        this.row = -1;
    }

    void edit() {
        String maSP = (String) this.tblSanPham.getValueAt(this.row, 0);
        SanPham sp = (SanPham) this.daoSP.selectByMa(maSP);
        this.setForm(sp);
    }

    void insert() {
        if (DialogHelper.confirm(this, "Xác nhận thêm sản phẩm")) {
            SanPham sp = getForm();
            try {
                this.daoSP.insert(sp);
                this.loadTable();
                this.clearForm();
                DialogHelper.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(this, "Thêm mới thất bại!");
            }
        }
    }

    void update() {
        if(this.txtMaSP.getText().trim().isEmpty()){
            DialogHelper.alert(this, "Chưa thấy mã sản phẩm!");
            return;
        }
        if (DialogHelper.confirm(this, "Xác nhận cập nhật sản phẩm")) {
            SanPham sp = getForm();
            try {
                this.daoSP.update(sp);
                this.loadTable();
                DialogHelper.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMota = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbbLoaiSP = new javax.swing.JComboBox<>();
        btnThemLoaiSP = new javax.swing.JButton();
        txtTenSP = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        cbbTrangThai = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbbLocLoaiSP = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        cbbLocTrangThai = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(238, 238, 238));
        setLayout(new java.awt.GridLayout(1, 0));

        background1.setBackground(new java.awt.Color(249, 238, 232));
        background1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblHinh.setBackground(new java.awt.Color(204, 204, 204));
        lblHinh.setFont(new java.awt.Font("Arial", 0, 1)); // NOI18N
        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("    Mã sản phẩm:");

        txtMaSP.setEditable(false);
        txtMaSP.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("    Tên sẩn phẩm:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("    Loại sản phẩm:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("    Đơn giá:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("    Mô tả:");

        txtMota.setColumns(20);
        txtMota.setRows(5);
        jScrollPane1.setViewportView(txtMota);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("    Ghi chú:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("    Trạng thái:");

        cbbLoaiSP.setBackground(new java.awt.Color(141, 110, 99));
        cbbLoaiSP.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btnThemLoaiSP.setBackground(new java.awt.Color(238, 238, 238));
        btnThemLoaiSP.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThemLoaiSP.setText("+");
        btnThemLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLoaiSPActionPerformed(evt);
            }
        });

        txtTenSP.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtDonGia.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        cbbTrangThai.setBackground(new java.awt.Color(141, 110, 99));
        cbbTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang bán", "Ngừng bán" }));

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane4.setViewportView(txtGhiChu);

        jPanel2.setBackground(new java.awt.Color(249, 238, 232));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 15, 10));

        btnThem.setBackground(new java.awt.Color(238, 238, 238));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel2.add(btnThem);

        btnCapNhat.setBackground(new java.awt.Color(238, 238, 238));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        jPanel2.add(btnCapNhat);

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(34, 34, 34)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTenSP)
                    .addComponent(cbbLoaiSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaSP)
                    .addComponent(txtDonGia)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane4)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnThemLoaiSP)
                .addGap(13, 13, 13))
            .addGroup(background1Layout.createSequentialGroup()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7))
                    .addGroup(background1Layout.createSequentialGroup()
                        .addGap(236, 236, 236)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnThemLoaiSP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        add(background1);

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblSanPham.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Loại SP", "Đơn giá", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setRowHeight(30);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setResizable(false);
            tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblSanPham.getColumnModel().getColumn(1).setResizable(false);
            tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(180);
            tblSanPham.getColumnModel().getColumn(2).setResizable(false);
            tblSanPham.getColumnModel().getColumn(3).setResizable(false);
            tblSanPham.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel3.setLayout(new java.awt.GridLayout());

        jPanel4.setBackground(new java.awt.Color(249, 238, 232));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("Tên sản phẩm");

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        btnTimKiem.setBackground(new java.awt.Color(238, 238, 238));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnTimKiem.setText("Tìm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 138, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtTimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4);

        jPanel5.setLayout(new java.awt.GridLayout());

        jPanel6.setBackground(new java.awt.Color(249, 238, 232));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setText("Loại sản phẩm");

        cbbLocLoaiSP.setBackground(new java.awt.Color(141, 110, 99));
        cbbLocLoaiSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbbLocLoaiSP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbLocLoaiSPItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbLocLoaiSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbLocLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(249, 238, 232));

        cbbLocTrangThai.setBackground(new java.awt.Color(141, 110, 99));
        cbbLocTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbbLocTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Ngừng bán", "Ðang bán" }));
        cbbLocTrangThai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbLocTrangThaiItemStateChanged(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setText("Trạng thái");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbbLocTrangThai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbLocTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel7);

        jPanel3.add(jPanel5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    public boolean check() {
        if (this.txtMaSP.getText().equals("")
                || this.txtTenSP.getText().equals("")
                || this.txtDonGia.getText().equals("")) {
            DialogHelper.alert(this, "Không được để trống thông tin!");
            return false;
        }
        try {
            double tl = Float.parseFloat(this.txtDonGia.getText());
            if (tl < 0) {
                DialogHelper.alert(this, "Giá tiền phải lớn hơn 0!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Giá tiền phải là một số!");
            return false;
        }
        return true;
    }
    
    boolean checkTrungTen(){
        try {
            String tenSP = this.txtTenSP.getText().trim();
            List<SanPham> listSP = this.daoSP.getAll();
            for (SanPham sanPham : listSP) {
                if(sanPham.getTenSP().trim().equalsIgnoreCase(tenSP)){
                    DialogHelper.alert(this, "Tên sản phẩm đã tồn tại!");
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (!check() || !checkTrungTen()) {
            return;
        }
        this.insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (!check()) {
            return;
        }
        this.update();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnThemLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLoaiSPActionPerformed
        new LoaiSPDialog(null, true).setVisible(true);
    }//GEN-LAST:event_btnThemLoaiSPActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        this.loadTable();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void cbbLocLoaiSPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbLocLoaiSPItemStateChanged
        this.loadTable();
    }//GEN-LAST:event_cbbLocLoaiSPItemStateChanged

    private void cbbLocTrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbLocTrangThaiItemStateChanged
        this.loadTable();
    }//GEN-LAST:event_cbbLocTrangThaiItemStateChanged

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        JFileChooser f = new JFileChooser("Images\\");
        int result = f.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                this.lblHinh.setIcon(null);
                this.lblHinh.setText("" + f.getSelectedFile().getName());
                this.UpHinh("" + f.getSelectedFile().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_lblHinhMouseClicked

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        if (evt.getClickCount() == 2) {
            clearForm();
            this.row = this.tblSanPham.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.loadTable();
        }
    }//GEN-LAST:event_txtTimKiemKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background1;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemLoaiSP;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cbbLoaiSP;
    private javax.swing.JComboBox<String> cbbLocLoaiSP;
    private javax.swing.JComboBox<String> cbbLocTrangThai;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextArea txtMota;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
