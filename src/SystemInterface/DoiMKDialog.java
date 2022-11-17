package SystemInterface;

import Dao.BaseDaoInterface;
import Dao.NguoiDungDao;
import Entity.NguoiDung;
import Helper.AuthHelper;
import Helper.DialogHelper;
import Helper.ImageHelper;

public class DoiMKDialog extends java.awt.Dialog {

    BaseDaoInterface<NguoiDung> dao;

    public DoiMKDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.setIconImage(ImageHelper.getAppIcon());
        this.setLocationRelativeTo(null);
        this.setTitle("Dev-Coffee: Đổi mật khẩu");
        this.dao = new NguoiDungDao();
        this.txtNguoiDung.setText(AuthHelper.user.getMaND());
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnXacNhan = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNguoiDung = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtMatKhauCu = new javax.swing.JPasswordField();
        txtMatKhauMoi = new javax.swing.JPasswordField();
        txtXacNhanMK = new javax.swing.JPasswordField();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        setLayout(new java.awt.GridLayout(1, 0));

        jPanel3.setBackground(new java.awt.Color(249, 238, 232));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/DoiMK.png"))); // NOI18N
        jPanel3.add(jLabel5);

        add(jPanel3);

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));

        btnXacNhan.setBackground(new java.awt.Color(238, 238, 238));
        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setText("Xác nhận mật khẩu mới:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Mật khẩu mới:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Mật khẩu cũ:");

        txtNguoiDung.setEditable(false);
        txtNguoiDung.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Người dùng:");
        jLabel1.setToolTipText("");

        txtMatKhauCu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtMatKhauMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtXacNhanMK.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(txtNguoiDung, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(txtMatKhauCu)
                            .addComponent(txtMatKhauMoi)
                            .addComponent(txtXacNhanMK)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(btnXacNhan)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMatKhauCu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtXacNhanMK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void xacNhan() {
        if (DialogHelper.confirm(this, "Bạn muốn đổi mật khẩu!")) {
            String matKhauCu = this.txtMatKhauCu.getText();
            String matKhauMoi = this.txtMatKhauMoi.getText();
            String xacNhanMK = this.txtXacNhanMK.getText();
            if (!AuthHelper.getMD5(matKhauCu).equalsIgnoreCase(AuthHelper.user.getMatKhau())) {
                DialogHelper.alert(this, "Mật khẩu cũ sai");
            } else if (!matKhauMoi.equalsIgnoreCase(xacNhanMK)) {
                DialogHelper.alert(this, "Mật khẩu mới chưa khớp");
            } else {
                AuthHelper.user.setMatKhau(AuthHelper.getMD5(matKhauMoi));
                this.dao.update(AuthHelper.user);
                DialogHelper.alert(this, "Đổi mật khẩu thành công!");
                this.dispose();
            }
        }
    }

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closeDialog

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        if (this.txtMatKhauCu.getText().equals("")
                || this.txtMatKhauMoi.getText().equals("")
                || this.txtXacNhanMK.getText().equals("")) {
            DialogHelper.alert(this, "Không được để trống thông tin!");
            return;
        }
        this.xacNhan();
    }//GEN-LAST:event_btnXacNhanActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField txtMatKhauCu;
    private javax.swing.JPasswordField txtMatKhauMoi;
    private javax.swing.JTextField txtNguoiDung;
    private javax.swing.JPasswordField txtXacNhanMK;
    // End of variables declaration//GEN-END:variables
}
