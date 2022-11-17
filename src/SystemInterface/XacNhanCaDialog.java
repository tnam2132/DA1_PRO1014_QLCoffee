package SystemInterface;

import Dao.BaseDaoInterface;
import Dao.GiaoCaDao;
import Entity.GiaoCa;
import Helper.AuthHelper;
import Helper.DateHelper;
import Helper.GiaoCaHelper;
import Helper.ImageHelper;
import Helper.MoneyHelper;
import java.util.Date;
import java.util.List;

public class XacNhanCaDialog extends javax.swing.JDialog {

    BaseDaoInterface daoGC;
    GiaoCa ca;

    public XacNhanCaDialog() {
        this.initComponents();
        this.setLocationRelativeTo(null);
        this.setIconImage(ImageHelper.getAppIcon());
        this.setTitle("Dev-Coffee");
        this.ca = new GiaoCa();
        this.daoGC = (BaseDaoInterface) new GiaoCaDao();
        this.setForm();
    }

    void setForm() {
//        this.lblTenNV.setText(AuthHelper.user.getHoTen());
        List<GiaoCa> listGC = daoGC.getAll();
        if (listGC.isEmpty()) {
            //Trường hợp ca đầu tiên của hệ thống
            this.lblThoiGianBD.setText(DateHelper.toString(new Date(), "HH:mm dd-MM-yyyy"));
            this.lblTienBanDau.setText(MoneyHelper.moneyToString(1000000));
        } else {
            GiaoCa caGanNhat = (GiaoCa) daoGC.selectNew();
            if (caGanNhat.getTrangThai() == 0) {
                //Trường hợp ca đang làm
                this.lblThoiGianBD.setText(DateHelper.toString(caGanNhat.getTgBatDau(), "HH:mm dd-MM-yyyy"));
                this.lblTienBanDau.setText(MoneyHelper.moneyToString(caGanNhat.getTienBanDau() - caGanNhat.getTienChuyenKhoan()));
            } else {
                //Trường hợp ca mới
                this.lblThoiGianBD.setText(DateHelper.toString(new Date(), "HH:mm dd-MM-yyyy"));
                this.lblTienBanDau.setText(MoneyHelper.moneyToString(caGanNhat.getTongTienCa() - caGanNhat.getTienChuyenKhoan()));
            }
        }
    }

    void setCa() {
        try {
            List<GiaoCa> listGC = daoGC.getAll();
            if (listGC.isEmpty()) {
                this.ca.setTgBatDau(new Date());
                this.ca.setTienBanDau(1000000);
                this.ca.setTrangThai(0);
                this.daoGC.insert(this.ca);
                
                GiaoCa giaoCa = (GiaoCa) daoGC.selectNew();
                GiaoCaHelper.ca = giaoCa;
            } else {
                GiaoCa giaoCa = (GiaoCa) daoGC.selectNew();
                if (giaoCa.getTrangThai() == 0) {
                    GiaoCaHelper.ca = giaoCa;
                } else if (giaoCa.getTrangThai() == 1) {
                    //Xác nhận hoàn thành ca cũ
                    giaoCa.setTrangThai(2);
                    this.daoGC.update(giaoCa);
                    //Tạo ca mới
                    this.ca.setTgBatDau(new Date());
                    this.ca.setTienBanDau(giaoCa.getTongTienCa() - giaoCa.getTienChuyenKhoan());
                    this.ca.setTrangThai(0);
                    this.daoGC.insert(this.ca);
                    GiaoCaHelper.ca = giaoCa;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void xacNhan() {
        this.setCa();
        this.dispose();
        new TrangChuForm().setVisible(true);
    }

    void huy() {
        AuthHelper.clear();
        this.dispose();
        new LoginForm().setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnXacNhan = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        lblThoiGianBD = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        lblTienBanDau = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("XÁC NHẬN CA");

        jPanel2.setBackground(new java.awt.Color(249, 238, 232));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnXacNhan.setBackground(new java.awt.Color(238, 238, 238));
        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });
        jPanel2.add(btnXacNhan);

        btnHuy.setBackground(new java.awt.Color(238, 238, 238));
        btnHuy.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel2.add(btnHuy);

        jPanel3.setBackground(new java.awt.Color(249, 238, 232));
        jPanel3.setLayout(new java.awt.GridLayout(3, 0));

        jPanel19.setBackground(new java.awt.Color(249, 238, 232));
        jPanel19.setLayout(new java.awt.GridLayout(1, 0));

        jPanel20.setBackground(new java.awt.Color(249, 238, 232));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("     Thời gian bắt đầu:");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 71, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel19.add(jPanel20);

        jPanel21.setBackground(new java.awt.Color(249, 238, 232));

        lblThoiGianBD.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblThoiGianBD.setText("22:22 pm - 22/22/2022 ");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(lblThoiGianBD)
                .addGap(0, 44, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(lblThoiGianBD)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel19.add(jPanel21);

        jPanel3.add(jPanel19);

        jPanel16.setBackground(new java.awt.Color(249, 238, 232));
        jPanel16.setLayout(new java.awt.GridLayout(1, 0));

        jPanel17.setBackground(new java.awt.Color(249, 238, 232));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("     Tiền ban đầu:");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 108, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        jPanel16.add(jPanel17);

        jPanel18.setBackground(new java.awt.Color(249, 238, 232));

        lblTienBanDau.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTienBanDau.setText("1.000.000 VND");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(lblTienBanDau)
                .addGap(0, 115, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(lblTienBanDau)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel16.add(jPanel18);

        jPanel3.add(jPanel16);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.huy();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        this.xacNhan();
    }//GEN-LAST:event_btnXacNhanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblThoiGianBD;
    private javax.swing.JLabel lblTienBanDau;
    // End of variables declaration//GEN-END:variables

    private void init() {
        setLocationRelativeTo(null);
        setTitle("Giao ca");
    }
}
