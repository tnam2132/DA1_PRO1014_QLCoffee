package ManagementInterface;

import Controller.ChuyenManController;
import Controller.DanhMucBean;
import Helper.DateHelper;
import Helper.DialogHelper;
import Helper.ExcelHelper;
import java.util.ArrayList;
import java.util.List;

public class ThongKePanel extends javax.swing.JPanel {

    public ThongKePanel() {
        initComponents();

        ChuyenManController controller = new ChuyenManController(Jview);
        controller.setView(jtkDoanhThuSanPham);
        this.Jview.add(new ThongKeDTSPPanel());

        List<DanhMucBean> listItem = new ArrayList<>();
        listItem.add(new DanhMucBean("ThongKeDTSP", jtkDoanhThuSanPham));
        listItem.add(new DanhMucBean("ThongKeGiaoCa", jtkGiaoCa));

        controller.setEvent(listItem);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        jtkDoanhThuSanPham = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtkGiaoCa = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Jview = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        dateToTK = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        btnXuatTK = new javax.swing.JButton();
        dateFromTK = new com.toedter.calendar.JDateChooser();

        setLayout(new java.awt.GridLayout(1, 0));

        background.setBackground(new java.awt.Color(249, 238, 232));

        jtkDoanhThuSanPham.setBackground(new java.awt.Color(141, 110, 99));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Doanh thu - Sản phẩm");

        javax.swing.GroupLayout jtkDoanhThuSanPhamLayout = new javax.swing.GroupLayout(jtkDoanhThuSanPham);
        jtkDoanhThuSanPham.setLayout(jtkDoanhThuSanPhamLayout);
        jtkDoanhThuSanPhamLayout.setHorizontalGroup(
            jtkDoanhThuSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jtkDoanhThuSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jtkDoanhThuSanPhamLayout.setVerticalGroup(
            jtkDoanhThuSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jtkDoanhThuSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtkGiaoCa.setBackground(new java.awt.Color(141, 110, 99));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Giao ca");

        javax.swing.GroupLayout jtkGiaoCaLayout = new javax.swing.GroupLayout(jtkGiaoCa);
        jtkGiaoCa.setLayout(jtkGiaoCaLayout);
        jtkGiaoCaLayout.setHorizontalGroup(
            jtkGiaoCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jtkGiaoCaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addContainerGap())
        );
        jtkGiaoCaLayout.setVerticalGroup(
            jtkGiaoCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jtkGiaoCaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Jview.setBackground(new java.awt.Color(249, 238, 232));

        javax.swing.GroupLayout JviewLayout = new javax.swing.GroupLayout(Jview);
        Jview.setLayout(JviewLayout);
        JviewLayout.setHorizontalGroup(
            JviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 845, Short.MAX_VALUE)
        );
        JviewLayout.setVerticalGroup(
            JviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        jPanel13.setBackground(new java.awt.Color(249, 238, 232));
        jPanel13.setAutoscrolls(true);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Từ:");

        dateToTK.setBackground(new java.awt.Color(249, 238, 232));
        dateToTK.setDateFormatString("dd-MM-yyyy");
        ((com.toedter.calendar.JTextFieldDateEditor) dateToTK.getDateEditor()).setEditable(false);
        dateToTK.setDate(Helper.DateHelper.addDay(new java.util.Date(), 1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Đến:");

        btnXuatTK.setBackground(new java.awt.Color(238, 238, 238));
        btnXuatTK.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXuatTK.setText("Xuất TK");
        btnXuatTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatTKActionPerformed(evt);
            }
        });

        dateFromTK.setBackground(new java.awt.Color(249, 238, 232));
        dateFromTK.setDateFormatString("dd-MM-yyyy");
        ((com.toedter.calendar.JTextFieldDateEditor) dateFromTK.getDateEditor()).setEditable(false);
        dateFromTK.setDate(new java.util.Date());

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateToTK, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(19, 19, 19)
                        .addComponent(dateFromTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXuatTK)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnXuatTK, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateFromTK, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateToTK, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(jtkDoanhThuSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jtkGiaoCa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Jview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jtkDoanhThuSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtkGiaoCa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Jview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(background);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXuatTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatTKActionPerformed
        // TODO add your handling code here:
        if (DialogHelper.confirm(this, "Bạn muốn xuất thống kê?")) {
            String fromDate = DateHelper.toString(dateFromTK.getDate(), "MM-dd-yyyy");
            String toDate = DateHelper.toString(dateToTK.getDate(), "MM-dd-yyyy");
            try {
                ExcelHelper.xuatThongKe(fromDate, toDate);
                DialogHelper.alert(null, "Xuất thống kê thành công!");
            } catch (Exception e) {
                DialogHelper.alert(null, "Xuất thống kê thất bại!");
            }
        }
    }//GEN-LAST:event_btnXuatTKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Jview;
    private javax.swing.JPanel background;
    private javax.swing.JButton btnXuatTK;
    private com.toedter.calendar.JDateChooser dateFromTK;
    private com.toedter.calendar.JDateChooser dateToTK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jtkDoanhThuSanPham;
    private javax.swing.JPanel jtkGiaoCa;
    // End of variables declaration//GEN-END:variables
}
