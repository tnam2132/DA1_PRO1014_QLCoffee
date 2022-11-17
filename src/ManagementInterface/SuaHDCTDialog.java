package ManagementInterface;

import Dao.BaseDaoInterface;
import Dao.HoaDonChiTietDao;
import Dao.HoaDonDao;
import Entity.HoaDonChiTiet;
import Helper.DialogHelper;
import Helper.ImageHelper;
import javax.swing.SpinnerNumberModel;

public class SuaHDCTDialog extends java.awt.Dialog {

    BaseDaoInterface daoHDChiTiet, daoHD;
    BoxHoaDon boxHD;
    HoaDonChiTiet hdct;

    public SuaHDCTDialog(java.awt.Frame parent, boolean modal, BoxHoaDon boxHD, HoaDonChiTiet hdct) {
        super(parent, modal);
        initComponents();
        this.setIconImage(ImageHelper.getAppIcon());
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Dev-Coffee: Sửa sản phẩm");
        this.daoHDChiTiet = new HoaDonChiTietDao();
        this.daoHD = new HoaDonDao();
        this.boxHD = boxHD;
        this.hdct = hdct;
        spnSoLuong.setModel(new SpinnerNumberModel(1, 1, hdct.getSoLuong() - 1, 1));
    }

    void xacNhan() {
        if (DialogHelper.confirm(null, "Xác nhận sửa sản phẩm?")) {
            try {
                int soLuong = (int) spnSoLuong.getValue();
                this.daoHDChiTiet.update(
                        new HoaDonChiTiet(this.hdct.getIdHD(),
                                this.hdct.getIdSP(),
                                soLuong,
                                this.hdct.getDonGia(),
                                this.hdct.getGhiChu(),
                                true)
                );

                this.daoHDChiTiet.insert(
                        new HoaDonChiTiet(this.hdct.getIdHD(),
                                this.hdct.getIdSP(),
                                this.hdct.getSoLuong() - soLuong,
                                this.hdct.getDonGia(),
                                this.hdct.getGhiChu() != null ? " - Hỏng" : "Hỏng",
                                false)
                );
                this.dispose();
                DialogHelper.alert(null, "Sửa sản phẩm thành công!");
                this.boxHD.fillTable();
                this.boxHD.getForm();
                this.boxHD.updateForm();
                this.daoHD.update(this.boxHD.getHoaDon());
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(null, "Sửa sản phẩm thất bại!");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        spnSoLuong = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnXacNhan = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Số lượng:");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spnSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(spnSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner spnSoLuong;
    // End of variables declaration//GEN-END:variables
}
