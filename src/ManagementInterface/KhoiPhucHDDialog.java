package ManagementInterface;

import Dao.BaseDaoInterface;
import Dao.HoaDonDao;
import Dao.NguoiDungDao;
import Entity.HoaDon;
import Entity.NguoiDung;
import Helper.DateHelper;
import Helper.DialogHelper;
import Helper.ImageHelper;
import Socket.SocketClient;
import java.util.List;

public class KhoiPhucHDDialog extends java.awt.Dialog {

    BaseDaoInterface daoHD, daoND;
    HoaDonPanel pn;
    HoaDon hd;

    public KhoiPhucHDDialog(java.awt.Frame parent, boolean modal, HoaDonPanel pn, HoaDon hd) {
        super(parent, modal);
        initComponents();
        this.setIconImage(ImageHelper.getAppIcon());
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Dev-Coffee: Khôi phục hoá đơn");
        this.pn = pn;
        this.hd = hd;
        this.daoHD = new HoaDonDao();
        this.daoND = new NguoiDungDao();
    }

    boolean validateForm() {
        if (txtLyDo.getText().length() <= 0) {
            DialogHelper.alert(this, "Không để trống Lý do!");
            txtLyDo.requestFocus();
            return false;
        }
        return true;
    }

    void xacNhan() {
        if (!validateForm()) {
            return;
        }
        if (DialogHelper.confirm(null, "Bạn muốn khôi phục hoá đơn?")) {
            try {
                String ghiChu = txtLyDo.getText();
                String ghiChuCu = hd.getGhiChu();
                NguoiDung nd = (NguoiDung) daoND.selectById(hd.getIdND());
                this.hd.setGhiChu(ghiChuCu + "\n" + DateHelper.getDate() + ": " + nd.getHoTen() + " khôi phục đơn với lí do: " + ghiChu);

                if (hd.getIdKH() == 0) {
                    hd.setIdKH(null);
                }
                if (hd.getTrangThai() == 4
                        || hd.getTrangThai() == 3
                        || hd.getTrangThai() == 2
                        || hd.getTrangThai() == 1) {
                    hd.setTrangThai(0);
                }

                this.daoHD.update(this.hd);
                List<HoaDon> list = daoHD.getAll();
                this.pn.fillTableLSHD(list);
                DialogHelper.alert(this, "Khôi phục thành công!");
                SocketClient.sendMessage("UPDATE_BH_CHO");
                this.dispose();
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(this, "Khôi phục thất bại!");
                this.dispose();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtLyDo = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnXacNhan = new javax.swing.JButton();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));

        txtLyDo.setColumns(20);
        txtLyDo.setRows(5);
        jScrollPane4.setViewportView(txtLyDo);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Lý do:");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea txtLyDo;
    // End of variables declaration//GEN-END:variables
}
