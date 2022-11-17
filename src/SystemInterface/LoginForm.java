package SystemInterface;

import Dao.BaseDaoInterface;
import Dao.GiaoCaDao;
import Dao.NguoiDungDao;
import Entity.GiaoCa;
import Entity.NguoiDung;
import Helper.AuthHelper;
import Helper.DialogHelper;
import Helper.ImageHelper;
import java.awt.event.KeyEvent;
import java.util.List;

public class LoginForm extends javax.swing.JFrame {

    BaseDaoInterface daoND, daoGC;

    public LoginForm() {
        this.initComponents();
        this.setIconImage(ImageHelper.getAppIcon());
        this.setLocationRelativeTo(null);
        this.setTitle("Dev-Coffee: Đăng nhập");
        this.daoND = (BaseDaoInterface) new NguoiDungDao();
        this.daoGC = (BaseDaoInterface) new GiaoCaDao();
        this.txtTenDN.setText("ND003");
        this.txtMK.setText("123456");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        background = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTenDN = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMK = new javax.swing.JPasswordField();
        lblQuenMK = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnDangNhap = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBackground(new java.awt.Color(141, 110, 99));
        jPanel2.setLayout(new java.awt.GridLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/LogoLogin.png"))); // NOI18N
        jPanel2.add(jLabel1);

        getContentPane().add(jPanel2);

        background.setBackground(new java.awt.Color(141, 110, 99));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tên đăng nhập:");

        txtTenDN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenDN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTenDNKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Mật khẩu:");

        txtMK.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMKKeyPressed(evt);
            }
        });

        lblQuenMK.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblQuenMK.setForeground(new java.awt.Color(255, 255, 255));
        lblQuenMK.setText("<html><u>Quên mật khẩu ?</u></html>");
        lblQuenMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQuenMKMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(141, 110, 99));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnDangNhap.setBackground(new java.awt.Color(238, 238, 238));
        btnDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });
        jPanel1.add(btnDangNhap);

        btnThoat.setBackground(new java.awt.Color(238, 238, 238));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        jPanel1.add(btnThoat);

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblQuenMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtTenDN, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtMK, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
                    .addComponent(jLabel2))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenDN, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMK, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblQuenMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        getContentPane().add(background);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void dangNhap() {
        if (this.txtTenDN.getText().isEmpty() || this.txtMK.getText().isEmpty()) {
            DialogHelper.alert(this, "Không để trống thông tin!");
            return;
        }
        String maND = this.txtTenDN.getText();
        String mk = this.txtMK.getText();
        NguoiDung nd = (NguoiDung) daoND.selectByMa(maND);
        if (nd == null
                || !AuthHelper.getMD5(mk).equals(nd.getMatKhau())) {
            DialogHelper.alert(this, "Sai thông tin đăng nhập!");
        } else {
            AuthHelper.user = nd;
//            this.dispose();
//            new TrangChuForm().setVisible(true);
//            List<GiaoCa> listGC = daoGC.getAll();
//            if (!listGC.isEmpty()) {
//                GiaoCa caGanNhat = listGC.get(listGC.size() - 1);
//                if (caGanNhat.getTrangThai() == 0
//                        && caGanNhat.getIdND() != AuthHelper.user.getId()) {
//                    NguoiDung ndCaGanNhat = (NguoiDung) daoND.selectById(caGanNhat.getIdND());
//                    DialogHelper.alert(this, "Ca của "
//                            + ndCaGanNhat.getMaND().trim() + " - "
//                            + ndCaGanNhat.getHoTen() + " chưa được giao\n"
//                            + "Yêu cầu hoàn thành ca trước!");
//                    return;
//                } else {
                    this.dispose();
                    new XacNhanCaDialog().setVisible(true);
//                }
//            } else {
//                this.dispose();
//                new XacNhanCaDialog().setVisible(true);
//            }
        }
    }

    void thoat() {
        if (DialogHelper.confirm(this, "Xác nhận thoát!")) {
            System.exit(0);
        }
    }

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
        this.dangNhap();
    }//GEN-LAST:event_btnDangNhapActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        this.thoat();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void lblQuenMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuenMKMouseClicked
        new QuenMKDialog(this, true).setVisible(true);
    }//GEN-LAST:event_lblQuenMKMouseClicked

    private void txtMKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMKKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.dangNhap();
        }
    }//GEN-LAST:event_txtMKKeyPressed

    private void txtTenDNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenDNKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.dangNhap();
        }
    }//GEN-LAST:event_txtTenDNKeyPressed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblQuenMK;
    private javax.swing.JPasswordField txtMK;
    private javax.swing.JTextField txtTenDN;
    // End of variables declaration//GEN-END:variables
}
