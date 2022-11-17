package ManagementInterfaceDone;

import Dao.BaseDaoInterface;
import Dao.NguoiDungDao;
import Dao.VaiTroDao;
import Entity.NguoiDung;
import Entity.VaiTro;
import Helper.AuthHelper;
import Helper.DialogHelper;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class NhanVienPanel extends javax.swing.JPanel {

    BaseDaoInterface daoND, daoVT;
    DefaultTableModel modelTable;
    DefaultComboBoxModel modelCBB_VaiTro, modelCBB_LocVT;
    int row = -1;

    public NhanVienPanel() {
        initComponents();
        this.daoND = (BaseDaoInterface) new NguoiDungDao();
        this.daoVT = (BaseDaoInterface) new VaiTroDao();
        this.modelTable = (DefaultTableModel) this.tblNhanVien.getModel();
        this.modelCBB_VaiTro = (DefaultComboBoxModel) this.cbbVaiTro.getModel();
        this.modelCBB_LocVT = (DefaultComboBoxModel) this.cbbLocVaiTro.getModel();
        this.fillCbbVaiTro();
        this.fillCbbLocVaiTro();
        this.loadTable();
    }

    void fillCbbVaiTro() {
        this.modelCBB_VaiTro.removeAllElements();
        try {
            List<VaiTro> list = this.daoVT.getAll();
            for (VaiTro vt : list) {
                this.modelCBB_VaiTro.addElement(vt);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu CBB");
        }
    }

    void fillCbbLocVaiTro() {
        this.modelCBB_LocVT.removeAllElements();
        this.modelCBB_LocVT.addElement("Tất cả");
        try {
            List<VaiTro> list = this.daoVT.getAll();
            for (VaiTro vt : list) {
                this.modelCBB_LocVT.addElement(vt);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu CBB");
        }
    }

    void fillTable(List<NguoiDung> list) {
        this.modelTable.setRowCount(0);
        try {
            for (NguoiDung nd : list) {
                VaiTro vaiTro = (VaiTro) this.daoVT.selectById(nd.getIdVT());
                this.modelTable.addRow(new Object[]{
                    nd.getMaND(),
                    nd.getHoTen(),
                    vaiTro.getTenVT()});
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void loadTable() {
        String keyword = this.txtTimKiem.getText();
        int vaiTro = this.cbbLocVaiTro.getSelectedIndex();
        int trangThai = this.cbbLocTrangThai.getSelectedIndex();
        if (vaiTro == 0 && trangThai == 0) {
            List<NguoiDung> list = this.daoND.selectByInfo(keyword, null, null, "KW");
            this.fillTable(list);
            return;
        }
        if (vaiTro == 0 && trangThai != 0) {
            List<NguoiDung> list = this.daoND.selectByInfo(keyword, null, trangThai - 1, "KW_TT");
            this.fillTable(list);
            return;
        }
        if (vaiTro != 0 && trangThai == 0) {
            VaiTro vt = (VaiTro) this.cbbLocVaiTro.getSelectedItem();
            List<NguoiDung> list = this.daoND.selectByInfo(keyword, vt.getId(), null, "KW_VT");
            this.fillTable(list);
            return;
        }
        if (vaiTro != 0 && trangThai != 0) {
            VaiTro vt = (VaiTro) this.cbbLocVaiTro.getSelectedItem();
            List<NguoiDung> list = this.daoND.selectByInfo(keyword, vt.getId(), trangThai - 1, "KW_VT_TT");
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

    void setForm(NguoiDung nd) {
        this.txtMaNV.setText(nd.getMaND());
        this.txtHoTen.setText(nd.getHoTen());
        this.radioNam.setSelected(nd.isGioiTinh());
        this.radioNu.setSelected(!nd.isGioiTinh());
        this.txtNgaySinh.setDate(nd.getNgaySinh());
        this.txtEmail.setText(nd.getEmail());
        this.txtSDT.setText(nd.getSdt());
        this.txtMatKhau.setText("");
//        List<VaiTro> listVT = daoVT.getAll();
//        for (VaiTro vaiTro : listVT) {
//            if(vaiTro.getId()==nd.getIdVT()){
//                this.cbbVaiTro.setSelectedItem(vaiTro);
//            }
//        }
        
        for (int i = 0; i < this.modelCBB_VaiTro.getSize(); i++) {
            VaiTro vt = (VaiTro) this.modelCBB_VaiTro.getElementAt(i);
            if(nd.getIdVT()==vt.getId()){
                this.cbbVaiTro.setSelectedIndex(i);
                break;
            }
        }
        
        this.txtGhiChu.setText(nd.getGhiChu());
        this.cbbTrangThai.setSelectedItem(nd.isTrangThai() ? "Đang làm" : "Nghỉ làm");
        this.UpHinh(nd.getHinh());
        this.tblNhanVien.setRowSelectionInterval(row, row);
    }

    NguoiDung getForm() {
        NguoiDung nd = new NguoiDung();
        nd.setMaND(this.txtMaNV.getText());
        nd.setHoTen(this.txtHoTen.getText());
        nd.setGioiTinh(this.radioNam.isSelected() ? true : false);
        nd.setNgaySinh(this.txtNgaySinh.getDate());
        nd.setEmail(this.txtEmail.getText());
        nd.setSdt(this.txtSDT.getText());
        nd.setMatKhau(AuthHelper.getMD5(this.txtMatKhau.getText()));
        VaiTro vaiTro = (VaiTro) this.modelCBB_VaiTro.getSelectedItem();
        nd.setIdVT(vaiTro.getId());
        nd.setGhiChu(this.txtGhiChu.getText());
        nd.setTrangThai(this.cbbTrangThai.getSelectedIndex() == 0 ? true : false);
        nd.setHinh(this.lblHinh.getText());
        return nd;
    }

    void clearForm() {
        this.txtMaNV.setText("");
        this.txtHoTen.setText("");
        this.buttonGroup1.clearSelection();
        this.txtNgaySinh.setDate(null);
        this.txtEmail.setText("");
        this.txtSDT.setText("");
        this.txtMatKhau.setText("");
        this.txtGhiChu.setText("");
        this.cbbVaiTro.setSelectedIndex(0);
        this.cbbTrangThai.setSelectedIndex(0);
        this.lblHinh.setText("");
        this.lblHinh.setSize(130, 130);
        this.lblHinh.setIcon(null);
        this.row = -1;
    }

    void edit() {
        String maND = (String) this.tblNhanVien.getValueAt(this.row, 0);
        NguoiDung nd = (NguoiDung) this.daoND.selectByMa(maND);
        this.setForm(nd);
    }

    void insert() {
        if (DialogHelper.confirm(this, "Xác nhận thêm nhân viên")) {
            NguoiDung nd = getForm();
            try {
                this.daoND.insert(nd);
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
        if(this.txtMaNV.getText().trim().isEmpty()){
            DialogHelper.alert(this, "Chưa thấy mã nhân viên!");
            return;
        }
        if (DialogHelper.confirm(this, "Xác nhận cập nhật nhân viên")) {
            NguoiDung nd = getForm();
            try {
                this.daoND.update(nd);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        background1 = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        cbbTrangThai = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        radioNam = new javax.swing.JRadioButton();
        radioNu = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbbVaiTro = new javax.swing.JComboBox<>();
        btnThemVaiTro = new javax.swing.JButton();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        cbbLocTrangThai = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cbbLocVaiTro = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();

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
        jLabel1.setText("     Mã:");

        txtMaNV.setEditable(false);
        txtMaNV.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("     Họ tên:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("     Giới tính:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("     Ngày sinh:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("     Ghi chú:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("     Trạng thái:");

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        cbbTrangThai.setBackground(new java.awt.Color(141, 110, 99));
        cbbTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ðang làm", "Nghỉ làm" }));

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

        buttonGroup1.add(radioNam);
        radioNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        radioNam.setText("Nam");

        buttonGroup1.add(radioNu);
        radioNu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        radioNu.setText("Nữ");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("     Mật khẩu:");

        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setText("     Vai trò:");

        cbbVaiTro.setBackground(new java.awt.Color(141, 110, 99));
        cbbVaiTro.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btnThemVaiTro.setBackground(new java.awt.Color(238, 238, 238));
        btnThemVaiTro.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThemVaiTro.setText("+");
        btnThemVaiTro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemVaiTroActionPerformed(evt);
            }
        });

        txtNgaySinh.setBackground(new java.awt.Color(249, 238, 232));
        txtNgaySinh.setDateFormatString("dd-M-yyyy");
        txtNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        ((com.toedter.calendar.JTextFieldDateEditor) txtNgaySinh.getDateEditor()).setEditable(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("     Email:");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("     SDT:");

        txtSDT.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel7)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(radioNam)
                        .addGap(18, 18, 18)
                        .addComponent(radioNu))
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtSDT)
                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(background1Layout.createSequentialGroup()
                        .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbTrangThai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbbVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThemVaiTro))
                    .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(background1Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(radioNam)
                    .addComponent(radioNu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cbbVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemVaiTro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        add(background1);

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Vai trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setRowHeight(30);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(0).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblNhanVien.getColumnModel().getColumn(1).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(2).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(2).setPreferredWidth(40);
        }

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("Tên nhân viên");

        btnTimKiem.setBackground(new java.awt.Color(238, 238, 238));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnTimKiem.setText("Tìm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        cbbLocTrangThai.setBackground(new java.awt.Color(141, 110, 99));
        cbbLocTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbbLocTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Nghỉ làm", "Ðang làm" }));
        cbbLocTrangThai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbLocTrangThaiItemStateChanged(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setText("Trạng thái");

        cbbLocVaiTro.setBackground(new java.awt.Color(141, 110, 99));
        cbbLocVaiTro.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbbLocVaiTro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbLocVaiTroItemStateChanged(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel13.setText("Vai trò");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTimKiem)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(cbbLocVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(cbbLocTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbLocTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbLocVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    public boolean validateForm() {
        if (this.txtHoTen.getText().equals("")
                || this.txtNgaySinh.getDate().equals("")
                || this.txtMatKhau.getText().equals("")) {
            DialogHelper.alert(this, "Không được để trống thông tin!");
            return false;
        }
        if (this.radioNam.isSelected() == false
                && this.radioNu.isSelected() == false) {
            DialogHelper.alert(this, "Không được để trống thông tin!");
            return false;
        }
        String email = this.txtEmail.getText();
        if (!email.matches("\\w+@\\w+(\\.\\w+){1,2}")) {
            DialogHelper.alert(this, "Email không đúng định dạng!");
            return false;
        }
        String sdt = this.txtSDT.getText();
        if (!sdt.matches("^0[0-9]{9}$")) {
            DialogHelper.alert(this, "Số điện thoại không đúng định dạng!");
            return false;
        }
        return true;
    }

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (!validateForm()) {
            return;
        }
        this.insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (!validateForm()) {
            return;
        }
        this.update();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        this.loadTable();
    }//GEN-LAST:event_btnTimKiemActionPerformed

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

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        if (evt.getClickCount() == 2) {
            clearForm();
            this.row = this.tblNhanVien.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnThemVaiTroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemVaiTroActionPerformed
        new VaiTroDialog(null, true).setVisible(true);
    }//GEN-LAST:event_btnThemVaiTroActionPerformed

    private void cbbLocTrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbLocTrangThaiItemStateChanged
        this.loadTable();
    }//GEN-LAST:event_cbbLocTrangThaiItemStateChanged

    private void cbbLocVaiTroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbLocVaiTroItemStateChanged
        this.loadTable();
    }//GEN-LAST:event_cbbLocVaiTroItemStateChanged

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.loadTable();
        }
    }//GEN-LAST:event_txtTimKiemKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background1;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemVaiTro;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbLocTrangThai;
    private javax.swing.JComboBox<String> cbbLocVaiTro;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JComboBox<String> cbbVaiTro;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JRadioButton radioNam;
    private javax.swing.JRadioButton radioNu;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMatKhau;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
