package ManagementInterface;

import Dao.BaseDaoInterface;
import Dao.HoaDonDao;
import Dao.KhachHangDao;
import Entity.HoaDon;
import Entity.KhachHang;
import Helper.DialogHelper;
import Helper.ImageHelper;
import Socket.SocketClient;

public class GiaoHangDialog extends java.awt.Dialog {

    BaseDaoInterface daoKH, daoHD;
    BanHangPanel pn;
    HoaDon hd;

    public GiaoHangDialog(java.awt.Frame parent, boolean modal, BanHangPanel pn, HoaDon hd) {
        super(parent, modal);
        initComponents();
        this.setIconImage(ImageHelper.getAppIcon());
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Dev-Coffee: Giao hàng");
        this.pn = pn;
        this.hd = hd;
        this.daoKH = new KhachHangDao();
        this.daoHD = new HoaDonDao();
    }

    boolean validateForm() {
        if (txtHoTen.getText().length() <= 0) {
            DialogHelper.alert(this, "Không để trống Họ tên!");
            txtHoTen.requestFocus();
            return false;
        }
        if (txtSDT.getText().length() <= 0) {
            DialogHelper.alert(this, "Không để trống Số ĐT!");
            txtSDT.requestFocus();
            return false;
        }
        if (!txtSDT.getText().matches("^0[0-9]{9}$")) {
            DialogHelper.alert(this, "Số ĐT sai định dạng!");
            txtSDT.requestFocus();
            return false;
        }
        if (txtPhiShip.getText().length() <= 0) {
            DialogHelper.alert(this, "Không để trống Phí ship!");
            txtPhiShip.requestFocus();
            return false;
        }
        try {
            double phiShip = Double.parseDouble(txtPhiShip.getText());
            if (phiShip <= 0) {
                DialogHelper.alert(this, "Phí ship phải lớn hơn 0!");
                txtPhiShip.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Phí ship phải là một số!");
            txtPhiShip.requestFocus();
            return false;
        }
        if (txtDiaChi.getText().length() <= 0) {
            DialogHelper.alert(this, "Không để trống Địa chỉ!");
            txtDiaChi.requestFocus();
            return false;
        }
        return true;
    }

    KhachHang getForm() {
        KhachHang kh = new KhachHang();
        kh.setHoTen(txtHoTen.getText());
        kh.setSdt(txtSDT.getText());
        kh.setDiaChi(txtDiaChi.getText());
        kh.setDiem(0);
        kh.setTrangThai(false);
        return kh;
    }

    void xacNhan() {
        if (!validateForm()) {
            return;
        }
        if (DialogHelper.confirm(null, "Bạn muốn xác nhận giao hàng?")) {
            try {
                this.daoKH.insert(this.getForm());

                KhachHang kh = (KhachHang) daoKH.selectNew();
                double phiShip = Double.parseDouble(txtPhiShip.getText());
                String diaChi = txtDiaChi.getText();

                this.hd.setIdKH(kh.getId());
                this.hd.setChiPhiKhac(phiShip);
                this.hd.setTongTien(this.hd.getTongTien() + phiShip);
                this.hd.setDiaChi(diaChi);
                this.hd.setTrangThai(1);
                this.daoHD.update(this.hd);

                DialogHelper.alert(this, "Xác nhận thành công!");
                SocketClient.sendMessage("UPDATE_HD_ALL");
                SocketClient.sendMessage("DIALOG_HDPC");
                this.dispose();
                this.pn.fillTableHDCho();
//                this.pn.loadTableHD();
                this.pn.closeTab();
            } catch (Exception e) {
                DialogHelper.alert(this, "Xác nhận thất bại!");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtHoTen = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        txtPhiShip = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnXacNhan = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Họ tên:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Số ĐT:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Địa chỉ:");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane4.setViewportView(txtDiaChi);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Phí ship:");

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
                    .addComponent(txtHoTen)
                    .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPhiShip, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPhiShip, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtPhiShip;
    private javax.swing.JTextField txtSDT;
    // End of variables declaration//GEN-END:variables
}
