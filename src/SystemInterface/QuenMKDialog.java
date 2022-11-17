package SystemInterface;

import Dao.BaseDaoInterface;
import Dao.NguoiDungDao;
import Entity.NguoiDung;
import Helper.AuthHelper;
import Helper.DialogHelper;
import Helper.ImageHelper;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class QuenMKDialog extends java.awt.Dialog {

    BaseDaoInterface dao;
    String maXacNhan;
    boolean isGuiMa = false;

    public QuenMKDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.setIconImage(ImageHelper.getAppIcon());
        this.setLocationRelativeTo(null);
        this.setTitle("Dev-Coffee: Quên mật khẩu");
        this.dao = (BaseDaoInterface) new NguoiDungDao();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtNguoiDung = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtMaXN = new javax.swing.JTextField();
        btnKhoiPhucMK = new javax.swing.JButton();
        lblGuiMa = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBackground(new java.awt.Color(249, 238, 232));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jLabel5.setBackground(new java.awt.Color(249, 238, 232));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/QuenMK.png"))); // NOI18N
        jPanel2.add(jLabel5);

        add(jPanel2);

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));

        txtNguoiDung.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Mã ND:");
        jLabel1.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Email:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Mã xác nhận:");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtMaXN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnKhoiPhucMK.setBackground(new java.awt.Color(238, 238, 238));
        btnKhoiPhucMK.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnKhoiPhucMK.setText("Khôi phục mật khẩu");
        btnKhoiPhucMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucMKActionPerformed(evt);
            }
        });

        lblGuiMa.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        lblGuiMa.setText("<html><u>Gửi mã</u></html>");
        lblGuiMa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGuiMaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblGuiMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNguoiDung, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaXN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addComponent(btnKhoiPhucMK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaXN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGuiMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKhoiPhucMK, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    String createCode() {
        String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += text.charAt((int) (Math.random() * text.length()));
        }
        return code;
    }

    boolean send(String to, String title, String message) {
        try {
            title = URLEncoder.encode(title, "UTF-8");
            message = URLEncoder.encode(message, "UTF-8");
            URL url = new URL("http://zeno.ml/mail.php?to=" + to + "&title=" + title + "&message=" + message);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Connection", "keep-alive");
            http.setRequestProperty("Cache-Control", "max-age=0");
            http.setRequestProperty("Upgrade-Insecure-Requests", "1");
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
            http.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            http.setRequestProperty("Accept-Language", "vi,vi-VN;q=0.9,en;q=0.8");
            http.setRequestProperty("Cookie", "__test=24dd24ca302a5c65468bd9fbc57fd60c; _ga=GA1.2.2041572465.1628702853; _omappvp=RGmq864lAcwc0xRykV0MCALjfLMmTJKM1viBIOBWLcLyg3RB3qF2jPSkz60a1jbbhslnYeOfPoY0VuLU1QS6hNGj7ndJg8z7");
            int responseCode = http.getResponseCode();
            http.getResponseMessage();
            if (responseCode != 200) {
                http.disconnect();
                return false;
            }
            http.disconnect();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    void guiMaXN() {
        if (this.isGuiMa) {
            return;
        }
        String maND = this.txtNguoiDung.getText();
        NguoiDung nd = (NguoiDung) dao.selectByMa(maND);
        if (nd == null) {
            DialogHelper.alert(this, "Tên đăng nhập không tồn tại!");
            return;
        }
        if (!this.txtEmail.getText().equals(nd.getEmail())) {
            DialogHelper.alert(this, "Sai email!");
            return;
        }
        this.maXacNhan = this.createCode();
        String title = "Mã xác nhận email!";
        String message = "Mã xác nhận của bạn là: " + this.maXacNhan;
        if (this.send(nd.getEmail(), title, message)) {
            this.txtNguoiDung.setEditable(false);
            this.txtEmail.setEditable(false);
            this.isGuiMa = true;
            DialogHelper.alert(null, "Mã xác nhận đã được gửi đến Email của bạn.");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 60; i > 0; i--) {
                        try {
                            lblGuiMa.setText("<html><u>Gửi mã (" + i + ")</u></html>");
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                    lblGuiMa.setText("<html><u>Gửi mã</u></html>");
                    isGuiMa = false;
                }
            }).start();
        } else {
            DialogHelper.alert(null, "Gửi Email thất bại.");
        }
    }

    void khoiPhucMK() {
        if (this.txtMaXN.getText().equals(maXacNhan)) {
            if (DialogHelper.confirm(this, "Bạn muốn khôi phục mật khẩu!")) {
                String maND = this.txtNguoiDung.getText();
                NguoiDung nd = (NguoiDung) dao.selectByMa(maND);
                if (nd == null) {
                    DialogHelper.alert(null, "Không có người dùng này!");
                    return;
                } else if (nd != null) {
                    try {
                        if (this.txtEmail.getText().equalsIgnoreCase(nd.getEmail())) {
                            String newPassword = this.createCode();
                            String title = "Mật khẩu mới!";
                            String message = "Mật khẩu mới của bạn là: " + newPassword;
                            this.send(nd.getEmail(), title, message);
                            nd.setMatKhau(AuthHelper.getMD5(newPassword));
                            this.dao.update(nd);
                            DialogHelper.alert(null, "Mật khẩu mới đã được gửi đến email.\n"
                                    + "Vui lòng đăng nhập để đổi lại mật khẩu.");
                            this.dispose();
                        } else {
                            DialogHelper.alert(null, "Email không khớp!");
                            return;
                        }
                    } catch (Exception e) {
                        DialogHelper.alert(null, "Lỗi!");
                    }
                }
            }
        } else {
            DialogHelper.alert(this, "Mã xác nhận không đúng!");
        }
    }

    boolean check() {
        if (this.txtNguoiDung.getText().isEmpty()
                || this.txtEmail.getText().isEmpty()
                || this.txtMaXN.getText().isEmpty()) {
            DialogHelper.alert(this, "Không để trống thông tin!");
            return false;
        }
        String email = this.txtEmail.getText();
        if (!email.matches("\\w+@\\w+(\\.\\w+){1,2}")) {
            DialogHelper.alert(this, "Email không đúng định dạng");
            return false;
        }
        return true;
    }

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closeDialog

    private void btnKhoiPhucMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucMKActionPerformed
        if (!this.check()) {
            return;
        }
        this.khoiPhucMK();
    }//GEN-LAST:event_btnKhoiPhucMKActionPerformed

    private void lblGuiMaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGuiMaMouseClicked
        this.guiMaXN();
    }//GEN-LAST:event_lblGuiMaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKhoiPhucMK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblGuiMa;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaXN;
    private javax.swing.JTextField txtNguoiDung;
    // End of variables declaration//GEN-END:variables
}
