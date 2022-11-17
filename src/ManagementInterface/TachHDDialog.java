package ManagementInterface;

import Dao.ApDungKMDao;
import Dao.BaseDaoInterface;
import Dao.HoaDonChiTietDao;
import Dao.HoaDonDao;
import Dao.KhuyenMaiDao;
import Dao.SanPhamDao;
import Entity.ApDungKM;
import Entity.HoaDon;
import Entity.HoaDonChiTiet;
import Entity.KhuyenMai;
import Entity.SanPham;
import Helper.AuthHelper;
import Helper.DateHelper;
import Helper.DialogHelper;
import Helper.ImageHelper;
import Helper.MoneyHelper;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TachHDDialog extends java.awt.Dialog {

    BaseDaoInterface daoHDChiTiet, daoSP, daoHD, daoKM, daoADKM;
    DefaultTableModel model_HD_Cu, model_HD_Moi;
    BanHangPanel pn;
    BoxHoaDon boxHD;

    public TachHDDialog(java.awt.Frame parent, boolean modal, BanHangPanel pn, BoxHoaDon boxHD) {
        super(parent, modal);
        initComponents();
        this.setIconImage(ImageHelper.getAppIcon());
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Dev-Coffee: Tách hoá đơn");
        this.pn = pn;
        this.boxHD = boxHD;
        this.daoHDChiTiet = new HoaDonChiTietDao();
        this.daoSP = new SanPhamDao();
        this.daoHD = new HoaDonDao();
        this.daoKM = new KhuyenMaiDao();
        this.daoADKM = new ApDungKMDao();
        this.model_HD_Cu = (DefaultTableModel) tblHoaDonCu.getModel();
        this.model_HD_Moi = (DefaultTableModel) tblHoaDonMoi.getModel();
        this.model_HD_Moi.setRowCount(0);
        lblMaHD.setText(this.boxHD.hd.getMaHD());
        this.fillTable();
    }

    void fillTable() {
        this.model_HD_Cu.setRowCount(0);
        try {
            List<HoaDonChiTiet> list = this.daoHDChiTiet.selectByInfo(this.boxHD.hd.getId(), true);
            for (HoaDonChiTiet hdct : list) {
                int soLuong = hdct.getSoLuong();
                SanPham sp = (SanPham) daoSP.selectById(hdct.getIdSP());
                model_HD_Cu.addRow(new Object[]{
                    sp.getMaSP().trim(),
                    sp.getTenSP(),
                    MoneyHelper.moneyToString(hdct.getDonGia()),
                    soLuong
                });
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi fillTable!");
        }
    }

    void tachSP() {
        int row = tblHoaDonCu.getSelectedRow();
        if (row == -1) {
            return;
        }
        try {
            String maSP = model_HD_Cu.getValueAt(row, 0).toString();
            String tenSP = model_HD_Cu.getValueAt(row, 1).toString();
            int soLuong = (int) model_HD_Cu.getValueAt(row, 3);
            int soLuongTach = 1;
            if (soLuong > 1) {
                try {
                    soLuongTach = Integer.parseInt(DialogHelper.prompt(null, "Mời nhập số lượng"));
                } catch (Exception e) {
                    soLuongTach = 0;
                }
            }
            if (soLuongTach <= 0) {
                return;
            }
            if (tblHoaDonCu.getRowCount() == 1 && (soLuong - soLuongTach) <= 0 || soLuong < soLuongTach) {
                DialogHelper.alert(this, "Không thể tách sản phẩm!");
                return;
            }

            for (int i = 0; i < model_HD_Moi.getRowCount(); i++) {
                if (maSP.equalsIgnoreCase(model_HD_Moi.getValueAt(i, 0).toString())) {
                    int soLuongHDMoi = (int) model_HD_Moi.getValueAt(i, 3);
                    model_HD_Moi.setValueAt(soLuongTach + soLuongHDMoi, i, 3);
                    if ((soLuong - soLuongTach) <= 0) {
                        model_HD_Cu.removeRow(row);
                    } else {
                        tblHoaDonCu.setValueAt(soLuong - soLuongTach, row, 3);
                    }
                    return;
                }
            }
            model_HD_Moi.addRow(new Object[]{
                maSP,
                tenSP,
                MoneyHelper.moneyToString(this.getGiaSP(maSP)),
                soLuongTach
            });
            if ((soLuong - soLuongTach) <= 0) {
                model_HD_Cu.removeRow(row);
            } else {
                tblHoaDonCu.setValueAt(soLuong - soLuongTach, row, 3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void xacNhan() {
        if (DialogHelper.confirm(null, "Bạn muốn xác nhận tách hoá đơn?")) {
            try {
                // update hoá đơn cũ
                List<HoaDonChiTiet> list = this.daoHDChiTiet.selectByInfo(this.boxHD.hd.getId(), true);
                for (HoaDonChiTiet hdct : list) {
                    for (int i = 0; i < model_HD_Cu.getRowCount(); i++) {
                        String maSP = model_HD_Cu.getValueAt(i, 0).toString();
                        int soLuong = (int) model_HD_Cu.getValueAt(i, 3);
                        SanPham sp = (SanPham) daoSP.selectById(hdct.getIdSP());
                        if (maSP.trim().equals(sp.getMaSP().trim())) {
                            hdct.setSoLuong(soLuong);
                            this.daoHDChiTiet.update(hdct);
                        } else {
                            hdct.setTrangThai(false);
                            this.daoHDChiTiet.update(hdct);
                        }
                    }
                }
                // create hoá đơn mới
                HoaDon hd = new HoaDon();
                hd.setIdND(AuthHelper.user.getId());
                hd.setTgTao(new Date());
                hd.setTrangThai(0);
                hd.setGhiChu("Tách từ hoá đơn " + this.boxHD.hd.getMaHD().trim());

                this.daoHD.insert(hd);
                // insert sản phẩm vào hoá đơn mới
                hd = (HoaDon) daoHD.selectNew();
                for (int i = 0; i < model_HD_Moi.getRowCount(); i++) {
                    String maSP = model_HD_Moi.getValueAt(i, 0).toString();
                    int soLuong = (int) model_HD_Moi.getValueAt(i, 3);
                    SanPham sp = (SanPham) daoSP.selectByMa(maSP);

                    HoaDonChiTiet hdct = new HoaDonChiTiet();
                    hdct.setIdHD(hd.getId());
                    hdct.setIdSP(sp.getId());
                    hdct.setDonGia(this.getGiaSP(maSP));
                    hdct.setSoLuong(soLuong);
                    hdct.setTrangThai(true);
                    hdct.setGhiChu("Tách từ hoá đơn " + this.boxHD.hd.getMaHD().trim() + " số lượng " + soLuong);

                    this.daoHDChiTiet.insert(hdct);
                }
                DialogHelper.alert(null, "Tách hoá đơn thành công!");
                this.dispose();
//                this.pn.loadTableHD();
                this.pn.fillTableHDCho();
                this.boxHD.fillTable();
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(null, "Tách hoá đơn thất bại!");
            }
        }
    }

    void huy() {
        if (tblHoaDonMoi.getRowCount() > 0) {
            if (DialogHelper.confirm(null, "Bạn muốn huỷ tách hoá đơn?")) {
                this.fillTable();
                model_HD_Moi.setRowCount(0);
            }
        } else {
            this.dispose();
        }
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

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDonCu = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDonMoi = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnXacNhan = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnTachSP = new javax.swing.JButton();
        lblMaHD = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));

        tblHoaDonCu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Đơn giá", "Số lượng"
            }
        ));
        tblHoaDonCu.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblHoaDonCu);

        tblHoaDonMoi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Đơn giá", "Số lượng"
            }
        ));
        tblHoaDonMoi.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblHoaDonMoi);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Mã HD:");

        jPanel2.setBackground(new java.awt.Color(249, 238, 232));

        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.setMargin(new java.awt.Insets(10, 14, 10, 14));
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });
        jPanel2.add(btnXacNhan);

        btnHuy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuy.setText("Huỷ");
        btnHuy.setMargin(new java.awt.Insets(10, 14, 10, 14));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel2.add(btnHuy);

        jPanel3.setBackground(new java.awt.Color(249, 238, 232));

        btnTachSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTachSP.setText("Tách sản phẩm");
        btnTachSP.setMargin(new java.awt.Insets(10, 14, 10, 14));
        btnTachSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTachSPActionPerformed(evt);
            }
        });
        jPanel3.add(btnTachSP);

        lblMaHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMaHD.setText("...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMaHD)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblMaHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        // TODO add your handling code here:
        this.xacNhan();
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnTachSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTachSPActionPerformed
        // TODO add your handling code here:
        this.tachSP();
    }//GEN-LAST:event_btnTachSPActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
        this.huy();
    }//GEN-LAST:event_btnHuyActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnTachSP;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JTable tblHoaDonCu;
    private javax.swing.JTable tblHoaDonMoi;
    // End of variables declaration//GEN-END:variables
}
