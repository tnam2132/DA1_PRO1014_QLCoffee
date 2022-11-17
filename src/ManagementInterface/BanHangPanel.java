package ManagementInterface;

import Dao.BaseDaoInterface;
import Dao.GiaoCaDao;
import Dao.HoaDonDao;
import Dao.KhachHangDao;
import Dao.LoaiSanPhamDao;
import Dao.NguoiDungDao;
import Dao.SanPhamDao;
import Entity.GiaoCa;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.LoaiSanPham;
import Entity.SanPham;
import Entity.NguoiDung;
import Helper.AuthHelper;
import Helper.DateHelper;
import Helper.DialogHelper;
import Socket.SocketClient;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import Socket.IReadMessage;

public class BanHangPanel extends javax.swing.JPanel implements IReadMessage {

    BaseDaoInterface daoSP, daoLoaiSP, daoHD, daoND, daoKH, daoGC;
    DefaultComboBoxModel modelCBB_LocLoaiSP;
    DefaultTableModel modelTB_HD_Cho, modelTB_HD_Giao;
    int index = 1;

    public BanHangPanel() {
        initComponents();
        this.modelCBB_LocLoaiSP = (DefaultComboBoxModel) cbbLocLoaiSP.getModel();
        this.daoLoaiSP = new LoaiSanPhamDao();
        this.daoSP = new SanPhamDao();
        this.daoHD = new HoaDonDao();
        this.daoND = new NguoiDungDao();
        this.daoKH = new KhachHangDao();
        this.daoGC = new GiaoCaDao();
        this.modelTB_HD_Cho = (DefaultTableModel) tblHoaDon.getModel();
        this.modelTB_HD_Giao = (DefaultTableModel) tblHoaDonGiao.getModel();
        this.fillCbbLocLoaiSP();
//        this.loadTableHD();
        this.fillTableHDCho();
        this.fillTableHDGiao();
        SocketClient.setIReadMessage(this);
        SocketClient.connect();
    }

    void fillTableHDCho(/*List<HoaDon> list*/) {
        modelTB_HD_Cho.setRowCount(0);
        try {
            List<HoaDon> list = daoHD.selectByInfo(0, null, null, null, null, "TT");
            for (HoaDon hd : list) {
                String trangThai = "";
                if (hd.getTrangThai() == 0) {
                    trangThai = "Chờ order";
                }
                if (hd.getTrangThai() == 2) {
                    trangThai = "Chờ người giao";
                }
                if (hd.getTrangThai() == 3) {
                    trangThai = "Đang giao";
                }
                NguoiDung nd = (NguoiDung) daoND.selectById(hd.getIdND());
                KhachHang kh = (KhachHang) daoKH.selectById(hd.getIdKH());
                String hoTenKH = kh != null ? kh.getHoTen() : "";
                modelTB_HD_Cho.addRow(new Object[]{
                    hd.getMaHD(),
                    nd.getHoTen(),
                    hoTenKH,
                    DateHelper.toString(hd.getTgTao(), "HH:mm dd-MM-yyyy"),
                    trangThai,
                    hd.getGhiChu()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi fillTable!");
        }
    }

    void fillTableHDGiao() {
        modelTB_HD_Giao.setRowCount(0);
        try {
            List<HoaDon> list = daoHD.selectByInfo(2, 3, null, null, null, "TT1_TT2");
            for (HoaDon hd : list) {
                String trangThai = "";
                if (hd.getTrangThai() == 0) {
                    trangThai = "Chờ order";
                }
                if (hd.getTrangThai() == 2) {
                    trangThai = "Chờ người giao";
                }
                if (hd.getTrangThai() == 3) {
                    trangThai = "Đang giao";
                }
                NguoiDung nd = (NguoiDung) daoND.selectById(hd.getIdND());
                KhachHang kh = (KhachHang) daoKH.selectById(hd.getIdKH());
                String hoTenKH = kh != null ? kh.getHoTen() : "";
                modelTB_HD_Giao.addRow(new Object[]{
                    hd.getMaHD(),
                    nd.getHoTen(),
                    hoTenKH,
                    DateHelper.toString(hd.getTgTao(), "HH:mm dd-MM-yyyy"),
                    trangThai,
                    hd.getGhiChu()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi fillTableHDGiao!");
        }
    }

//    void loadTableHD() {
//        String keyword = this.txtTimKiem.getText();
//        int locHD = cbbHoaDonCho.getSelectedIndex();
//        if (locHD == 0) {
//            List<HoaDon> list = daoHD.selectByInfo(0, 2, 3, null, null, "TT023");
//            this.fillTableHD(list);
//            return;
//        }
//        if (locHD == 1) {
//            List<HoaDon> list = daoHD.selectByInfo(0, null, null, null, null, "TT");
//            this.fillTableHD(list);
//            return;
//        }
//        if (locHD == 2) {
//            List<HoaDon> list = daoHD.selectByInfo(2, null, null, null, null, "TT");
//            this.fillTableHD(list);
//            return;
//        }
//        if (locHD == 3) {
//            List<HoaDon> list = daoHD.selectByInfo(3, null, null, null, null, "TT");
//            this.fillTableHD(list);
//            return;
//        }
//    }

    void fillCbbLocLoaiSP() {
        modelCBB_LocLoaiSP.removeAllElements();
        modelCBB_LocLoaiSP.addElement("TẤT CẢ");
        try {
            List<LoaiSanPham> list = daoLoaiSP.getAll();
            for (LoaiSanPham lsp : list) {
                modelCBB_LocLoaiSP.addElement(lsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu CBB");
        }
    }

    void fillListSP(List<SanPham> list) {
        int column = 4;
        GridLayout layout = (GridLayout) listItem.getLayout();
        this.listItem.removeAll();
        this.listItem.validate();
        this.listItem.repaint();
        layout.setColumns(1);
        int maxRow = list.size() / column + 2;
        layout.setRows(maxRow);
        for (int i = 0; i < maxRow; i++) {
            JPanel rowSP = new JPanel();
            rowSP.setBackground(new Color(255, 255, 255));
            rowSP.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
            for (int j = 0; j < column; j++) {
                int index = (i * column) + j;
                if (index < list.size()) {
                    BoxSanPham sp = new BoxSanPham(this.tabsHoaDon, list.get(index));
                    rowSP.add(sp);
                }
            }
            this.listItem.add(rowSP);
        }
    }

    void insert(HoaDon hd) {
        try {
            daoHD.insert(hd);

            HoaDon hdMoi = (HoaDon) daoHD.selectNew();
            this.addTabHoaDon(hdMoi);
            this.fillTableHDCho();
//            this.loadTableHD();
            DialogHelper.alert(null, "Tạo hoá đơn thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(null, "Tạo hoá đơn thất bại!");
        }
    }

    void locLoaiSP() {
        int select = this.cbbLocLoaiSP.getSelectedIndex();
        String keyword = txtTimKiem.getText();
        if (select != 0) {
            LoaiSanPham lsp = (LoaiSanPham) this.cbbLocLoaiSP.getSelectedItem();
            List<SanPham> list = daoSP.selectByInfo(keyword, lsp.getId(), 1, "KW_ID_TT");
            this.fillListSP(list);
        } else {
            List<SanPham> list = daoSP.selectByInfo(keyword, null, 1, "KW_TT");
            this.fillListSP(list);
        }
    }

    void createHoaDon() {
        if (DialogHelper.confirm(null, "Bạn muốn tạo hoá đơn mới?")) {
            HoaDon hd = new HoaDon();
            List<GiaoCa> listGC = daoGC.getAll();
            GiaoCa giaoCa = listGC.get(listGC.size() - 1);
            hd.setIdCa(giaoCa.getId());
            hd.setIdND(AuthHelper.user.getId());
            hd.setTgTao(new Date());
            hd.setTrangThai(0);
            this.insert(hd);
        }
    }

    void openHoaDon(String maHD) {
        try {
            HoaDon hd = (HoaDon) daoHD.selectByMa(maHD);
            this.addTabHoaDon(hd);
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi openHoaDon!");
        }
    }

    void addTabHoaDon(HoaDon hd) {
        for (int i = 0; i < tabsHoaDon.getComponentCount(); i++) {
            BoxHoaDon boxHD = (BoxHoaDon) tabsHoaDon.getComponentAt(i);
            if (boxHD.getHoaDon().getMaHD().equalsIgnoreCase(hd.getMaHD())) {
                DialogHelper.alert(this, "Hoá đơn đang được mở!");
                tabsHoaDon.setSelectedIndex(i);
                return;
            }
        }
        tabsHoaDon.addTab(hd.getMaHD().trim(), new BoxHoaDon(this, hd));
        tabsHoaDon.setSelectedIndex(tabsHoaDon.getComponentCount() - 1);
        index++;
    }

    public void closeTab() {
        this.tabsHoaDon.remove(tabsHoaDon.getSelectedComponent());
    }

    @Override
    public void readMessage(String message) {
        switch (message) {
            case "UPDATE_BH_GIAO":
                this.fillTableHDGiao();
                break;
            case "UPDATE_BH_CHO":
                this.fillTableHDCho();
                break;
            case "DIALOG_BH_GIAO":
                DialogHelper.alert(null, "Có hoá đơn mới đang chờ người giao!");
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jscroll = new javax.swing.JScrollPane();
        listItem = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        cbbLocLoaiSP = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDonGiao = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tabsHoaDon = new javax.swing.JTabbedPane();
        btnThemHoaDon = new javax.swing.JButton();
        btnAnHoaDon = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");
        jPopupMenu1.add(jMenuItem1);

        setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        listItem.setBackground(new java.awt.Color(255, 255, 255));
        listItem.setLayout(new java.awt.GridLayout(1, 0));
        jscroll.setViewportView(listItem);

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Tên sản phẩm:");

        btnTimKiem.setBackground(new java.awt.Color(238, 238, 238));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTimKiem.setText("Tìm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Loại sản phẩm:");

        cbbLocLoaiSP.setBackground(new java.awt.Color(141, 110, 99));
        cbbLocLoaiSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbbLocLoaiSP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbLocLoaiSPItemStateChanged(evt);
            }
        });

        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã HD", "Người tạo", "Khách hàng", "TG Tạo", "Trang Thái", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setRowHeight(25);
        tblHoaDon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Hoá đơn chờ:");

        tblHoaDonGiao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDonGiao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã HD", "Người tạo", "Khách hàng", "TG Tạo", "Trang Thái", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonGiao.setRowHeight(25);
        tblHoaDonGiao.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblHoaDonGiao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonGiaoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDonGiao);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Hoá đơn giao:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTimKiem)
                                .addGap(6, 6, 6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(cbbLocLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1)
                    .addComponent(jscroll, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(0, 423, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem)
                    .addComponent(cbbLocLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jscroll, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel10)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel11)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1);

        jPanel2.setBackground(new java.awt.Color(249, 238, 232));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnThemHoaDon.setBackground(new java.awt.Color(238, 238, 238));
        btnThemHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemHoaDon.setText("+");
        btnThemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHoaDonActionPerformed(evt);
            }
        });

        btnAnHoaDon.setBackground(new java.awt.Color(238, 238, 238));
        btnAnHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAnHoaDon.setText("-");
        btnAnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(419, Short.MAX_VALUE)
                        .addComponent(btnThemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAnHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tabsHoaDon))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemHoaDon)
                    .addComponent(btnAnHoaDon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabsHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHoaDonActionPerformed
        // TODO add your handling code here:
        this.createHoaDon();
    }//GEN-LAST:event_btnThemHoaDonActionPerformed

    private void cbbLocLoaiSPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbLocLoaiSPItemStateChanged
        // TODO add your handling code here:
        this.locLoaiSP();
    }//GEN-LAST:event_cbbLocLoaiSPItemStateChanged

    private void btnAnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnHoaDonActionPerformed
        // TODO add your handling code here:
        if (tabsHoaDon.getComponentCount() <= 0) {
            return;
        }
        if (DialogHelper.confirm(null, "Bạn muốn đóng hoá đơn?")) {
            this.tabsHoaDon.remove(tabsHoaDon.getSelectedComponent());
        }
    }//GEN-LAST:event_btnAnHoaDonActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        this.locLoaiSP();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = tblHoaDon.getSelectedRow();
            if (row == -1) {
                return;
            }
            String maHD = tblHoaDon.getValueAt(row, 0).toString();
            this.openHoaDon(maHD);
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            this.locLoaiSP();
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void tblHoaDonGiaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonGiaoMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = tblHoaDonGiao.getSelectedRow();
            if (row == -1) {
                return;
            }
            String maHD = tblHoaDonGiao.getValueAt(row, 0).toString();
            this.openHoaDon(maHD);
        }
    }//GEN-LAST:event_tblHoaDonGiaoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnHoaDon;
    private javax.swing.JButton btnThemHoaDon;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cbbLocLoaiSP;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jscroll;
    private javax.swing.JPanel listItem;
    private javax.swing.JTabbedPane tabsHoaDon;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonGiao;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
