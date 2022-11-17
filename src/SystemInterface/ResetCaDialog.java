package SystemInterface;

import Dao.BaseDaoInterface;
import Dao.GiaoCaDao;
import Dao.HoaDonDao;
import Dao.NguoiDungDao;
import Entity.GiaoCa;
import Entity.HoaDon;
import Entity.NguoiDung;
import Helper.AuthHelper;
import Helper.DialogHelper;
import Helper.ImageHelper;
import Helper.MoneyHelper;
import java.awt.event.KeyEvent;
import java.util.List;

public class ResetCaDialog extends java.awt.Dialog {

    BaseDaoInterface daoND, daoGC, daoHD;
    GiaoCaDialog giaoCaDialog;
    double tienChuThu = 0;

    public ResetCaDialog(java.awt.Frame parent, boolean modal, GiaoCaDialog giaoCaDialog) {
        super(parent, modal);
        this.initComponents();
        this.setIconImage(ImageHelper.getAppIcon());
        this.setLocationRelativeTo(null);
        this.setTitle("Dev-Coffee");
        this.giaoCaDialog = giaoCaDialog;
        this.daoND = (BaseDaoInterface) new NguoiDungDao();
        this.daoGC = (BaseDaoInterface) new GiaoCaDao();
        this.daoHD = (BaseDaoInterface) new HoaDonDao();
        this.txtMaND.setText("ND001");
        this.txtMK.setText("123456");
        this.setForm();
    }

    void setForm() {
        GiaoCa ca = (GiaoCa) daoGC.selectNew();
        List<HoaDon> listHD = daoHD.getAll();

        double tienMat = 0;
        double tienCK = 0;

        if (listHD.isEmpty()) {
            tienMat = ca.getTienBanDau();
        } else {
            for (HoaDon hd : listHD) {
                if (hd.getMaLTT().equalsIgnoreCase("LTT001")
                        && hd.getIdCa() == ca.getId()
                        && hd.getTrangThai() == 4) {
                    tienMat += hd.getTongTien();
                } else if (hd.getMaLTT().equalsIgnoreCase("LTT002")
                        && hd.getIdCa() == ca.getId()
                        && hd.getTrangThai() == 4) {
                    tienCK += hd.getTongTien();
                }
            }
        }
        this.tienChuThu = tienMat + ca.getTienBanDau() - 1000000;
        this.lblTienMatHienTai.setText(MoneyHelper.moneyToString(tienMat + ca.getTienBanDau() - ca.getTienChuThu()));
        this.lblTienLayRa.setText(MoneyHelper.moneyToString(tienMat + ca.getTienBanDau() - ca.getTienChuThu() - 1000000));
        this.lblTienConLai.setText(MoneyHelper.moneyToString(1000000));
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMaND = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMK = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        btnXacNhan = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        lblTienMatHienTai = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        lblTienLayRa = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        lblTienConLai = new javax.swing.JLabel();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Mã người dùng:");

        txtMaND.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaND.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMaNDKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("RESET CA");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Mật khẩu:");

        txtMK.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMKKeyPressed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(249, 238, 232));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 15, 0));

        btnXacNhan.setBackground(new java.awt.Color(238, 238, 238));
        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });
        jPanel2.add(btnXacNhan);

        btnHuy.setBackground(new java.awt.Color(238, 238, 238));
        btnHuy.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel2.add(btnHuy);

        jPanel3.setLayout(new java.awt.GridLayout(3, 0));

        jPanel10.setBackground(new java.awt.Color(249, 238, 232));
        jPanel10.setLayout(new java.awt.GridLayout(1, 0));

        jPanel11.setBackground(new java.awt.Color(249, 238, 232));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("     Tiền mặt hiện tại::");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 59, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel11);

        jPanel12.setBackground(new java.awt.Color(249, 238, 232));

        lblTienMatHienTai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTienMatHienTai.setText("9.000.000 VND");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(lblTienMatHienTai)
                .addGap(0, 98, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(lblTienMatHienTai)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel12);

        jPanel3.add(jPanel10);

        jPanel13.setBackground(new java.awt.Color(249, 238, 232));
        jPanel13.setLayout(new java.awt.GridLayout(1, 0));

        jPanel14.setBackground(new java.awt.Color(249, 238, 232));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("     Tiền lấy ra:");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 114, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel14);

        jPanel15.setBackground(new java.awt.Color(249, 238, 232));

        lblTienLayRa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTienLayRa.setText("8.000.000 VND");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(lblTienLayRa)
                .addGap(0, 98, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(lblTienLayRa)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel15);

        jPanel3.add(jPanel13);

        jPanel16.setBackground(new java.awt.Color(249, 238, 232));
        jPanel16.setLayout(new java.awt.GridLayout(1, 0));

        jPanel17.setBackground(new java.awt.Color(249, 238, 232));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("     Tiền còn lại:");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 105, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel16.add(jPanel17);

        jPanel18.setBackground(new java.awt.Color(249, 238, 232));

        lblTienConLai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTienConLai.setText("1.000.000 VND");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(lblTienConLai)
                .addGap(0, 98, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(lblTienConLai)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel16.add(jPanel18);

        jPanel3.add(jPanel16);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMK, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                    .addComponent(txtMaND))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtMaND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void xacNhan() {
        if (this.txtMaND.getText().isEmpty() || this.txtMK.getText().isEmpty()) {
            DialogHelper.alert(this, "Không để trống thông tin!");
            return;
        }
        String maND = this.txtMaND.getText();
        String mk = this.txtMK.getText();
        NguoiDung nd = (NguoiDung) daoND.selectByMa(maND);
        if (nd == null
                || !AuthHelper.getMD5(mk).equals(nd.getMatKhau())) {
            DialogHelper.alert(this, "Dùng tài khoản quản lí để reset ca!");
        } else {
            if (nd.getIdVT() == 1) {
                if (DialogHelper.confirm(this, "Xác nhận reset ca")) {
                    GiaoCa ca = (GiaoCa) daoGC.selectNew();
                    ca.setTienChuThu(this.tienChuThu);
                    if (ca.getIdND() == 0) {
                        ca.setIdND(null);
                    }
                    this.daoGC.update(ca);
                    DialogHelper.alert(this, "Reset ca thành công!");
                    this.dispose();
                    this.giaoCaDialog.setForm();
                }
            } else {
                DialogHelper.alert(this, "Dùng tài khoản quản lí để reset ca!");
                return;
            }
        }
    }

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closeDialog

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        this.xacNhan();
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void txtMaNDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaNDKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.xacNhan();
        }
    }//GEN-LAST:event_txtMaNDKeyPressed

    private void txtMKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMKKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.xacNhan();
        }
    }//GEN-LAST:event_txtMKKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblTienConLai;
    private javax.swing.JLabel lblTienLayRa;
    private javax.swing.JLabel lblTienMatHienTai;
    private javax.swing.JPasswordField txtMK;
    private javax.swing.JTextField txtMaND;
    // End of variables declaration//GEN-END:variables
}
