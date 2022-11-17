package ManagementInterface;

import Dao.BaseDaoInterface;
import Dao.GiaoCaDao;
import Dao.NguoiDungDao;
import Entity.GiaoCa;
import Entity.NguoiDung;
import Helper.DateHelper;
import Helper.MoneyHelper;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ThongKeGiaoCa extends javax.swing.JPanel {

    BaseDaoInterface daoGC, daoND;
    DefaultTableModel model;

    public ThongKeGiaoCa() {
        initComponents();
        this.daoGC = new GiaoCaDao();
        this.daoND = new NguoiDungDao();
        this.model = (DefaultTableModel) tblThongKeGiaoCa.getModel();
        this.dateFrom.setDate(new Date());
        this.dateTo.setDate(DateHelper.addDay(new Date(), 1));
        this.fillTable(null);
    }

    void fillTable(List<GiaoCa> list) {
        this.model.setRowCount(0);
        try {
            if (list == null) {
                String fromDate = DateHelper.toString(DateHelper.addDay(new Date(), -2), "MM-dd-yyyy");
                String toDate = DateHelper.toString(DateHelper.addDay(new Date(), 1), "MM-dd-yyyy");
                list = this.daoGC.selectByInfo(fromDate, toDate, "TYPE2");
            }
            for (GiaoCa gc : list) {
                NguoiDung nd = (NguoiDung) daoND.selectById(gc.getIdND());
                this.model.addRow(new Object[]{
                    gc.getMaCa(),
                    nd != null ? nd.getHoTen() : null,
                    DateHelper.toString(gc.getTgBatDau(), "HH:mm dd-MM-yyyy"),
                    gc.getTgKetThuc() != null ? DateHelper.toString(gc.getTgKetThuc(), "HH:mm dd-MM-yyyy") : null,
                    MoneyHelper.moneyToString(gc.getTienBanDau()),
                    MoneyHelper.moneyToString(gc.getTienDoanhThu()),
                    MoneyHelper.moneyToString(gc.getTienChuyenKhoan()),
                    MoneyHelper.moneyToString(gc.getTienMat()),
                    MoneyHelper.moneyToString(gc.getTongTienCa()),
                    MoneyHelper.moneyToString(gc.getTienChuThu()),
                    MoneyHelper.moneyToString(gc.getTienPhatSinh()),
                    gc.getGhiChu()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongKeGiaoCa = new javax.swing.JTable() {
            @Override
            public javax.swing.table.TableCellRenderer getCellRenderer(int row, int column) {
                return new TableModelUI.CustomCellRenderer(0);
            }
        };
        dateFrom = new com.toedter.calendar.JDateChooser();
        dateTo = new com.toedter.calendar.JDateChooser();
        btnLoc = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblThongKeGiaoCa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã ca", "Nhân viên", "TG bắt đầu", "TG kết thúc", "Tiền ban đầu", "Tiền doanh thu", "Tiền chuyển khoản", "Tiền mặt", "Tổng tiền ca", "Tiền chủ thu", "Tiền phát sinh", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThongKeGiaoCa.setRowHeight(30);
        jScrollPane1.setViewportView(tblThongKeGiaoCa);

        dateFrom.setBackground(new java.awt.Color(249, 238, 232));
        dateFrom.setDateFormatString("dd-MM-yyyy");
        ((com.toedter.calendar.JTextFieldDateEditor) dateFrom.getDateEditor()).setEditable(false);

        dateTo.setBackground(new java.awt.Color(249, 238, 232));
        dateTo.setDateFormatString("dd-MM-yyyy");
        ((com.toedter.calendar.JTextFieldDateEditor) dateTo.getDateEditor()).setEditable(false);

        btnLoc.setBackground(new java.awt.Color(238, 238, 238));
        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Từ ngày:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Đến ngày:");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setForeground(java.awt.Color.orange);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("+ Ca hiện tại");
        jPanel2.add(jLabel1);

        jLabel2.setForeground(java.awt.Color.red);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("+ Ca đã reset");
        jPanel2.add(jLabel2);

        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("+ Ca có phí phát sinh");
        jPanel2.add(jLabel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLoc)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLoc)
                    .addComponent(dateTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:
        String fromDate = DateHelper.toString(dateFrom.getDate(), "MM-dd-yyyy");
        String toDate = DateHelper.toString(dateTo.getDate(), "MM-dd-yyyy");
        try {
            List<GiaoCa> list = this.daoGC.selectByInfo(fromDate, toDate, "TYPE1");
            this.fillTable(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnLocActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoc;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblThongKeGiaoCa;
    // End of variables declaration//GEN-END:variables
}
