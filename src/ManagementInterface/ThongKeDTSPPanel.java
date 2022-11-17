package ManagementInterface;

import Dao.BaseDaoInterface;
import Dao.LoaiSanPhamDao;
import Dao.ThongKeDAO;
import Entity.LoaiSanPham;
import Helper.DateHelper;
import Helper.DialogHelper;
import Helper.MoneyHelper;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class ThongKeDTSPPanel extends javax.swing.JPanel {

    ThongKeDAO daoTK;
    BaseDaoInterface daoLoaiSP;
    DefaultTableModel model;
    DefaultComboBoxModel modelCBB_LocLoaiSP;

    public ThongKeDTSPPanel() {
        initComponents();
        this.daoTK = new ThongKeDAO();
        this.daoLoaiSP = new LoaiSanPhamDao();
        this.model = (DefaultTableModel) tblTKSP.getModel();
        this.modelCBB_LocLoaiSP = (DefaultComboBoxModel) cbbLocLoaiSP.getModel();
        this.dateFromTKSP.setDate(new Date());
        this.dateToTKSP.setDate(DateHelper.addDay(new Date(), 1));
        this.dateFromDT.setDate(new Date());
        this.dateToDT.setDate(DateHelper.addDay(new Date(), 1));
        this.loadDoanhThuHomNay();
        this.loadDoanhThu7Ngay();
        this.loadDoanhThuThang();
        this.loadDoanhThuTuyChon();
        this.fillCbbLocLoaiSP();
        this.fillTableTKSP(null);
    }

    void fillCbbLocLoaiSP() {
        modelCBB_LocLoaiSP.removeAllElements();
        modelCBB_LocLoaiSP.addElement("TẤT CẢ");
        try {
            List<LoaiSanPham> list = daoLoaiSP.getAll();
            for (LoaiSanPham lsp : list) {
                modelCBB_LocLoaiSP.addElement(lsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu CBB");
        }
    }

    void fillTableTKSP(List<Object[]> list) {
        this.model.setRowCount(0);
        try {
            if (list == null) {
                list = this.daoTK.getSanPham(null, null, null, null);
            }
            for (Object[] obj : list) {
                String trangThaiSP = ((boolean) obj[3]) ? "Đang bán" : "Ngừng bán";
                this.model.addRow(new Object[]{
                    obj[0],
                    obj[1],
                    obj[2],
                    trangThaiSP
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadDoanhThuHomNay() {
        String fromDate = DateHelper.toString(new Date(), "MM-dd-yyyy");
        String toDate = DateHelper.toString(DateHelper.addDay(new Date(), 1), "MM-dd-yyyy");
        try {
            Object[] obj = this.daoTK.getDoanhThu(fromDate, toDate);
            if (obj[0] != null) {
                lblDoanhThuHN.setText(obj[0].toString());
                
            } else {
                lblDoanhThuHN.setText("0 ₫");
            }
            lblHDBanHN.setText("Hóa đơn bán: " + obj[1].toString());
            lblHDGiaoHN.setText("Hoá đơn giao: " + obj[2].toString());
            lblHDHuyHN.setText("Hóa đơn huỷ: " + obj[3].toString());
        } catch (Exception e) {
        }
    }

    void loadDoanhThu7Ngay() {
        String fromDate = DateHelper.toString(DateHelper.addDay(new Date(), -6), "MM-dd-yyyy");
        String toDate = DateHelper.toString(DateHelper.addDay(new Date(), 1), "MM-dd-yyyy");
        try {
            Object[] obj = this.daoTK.getDoanhThu(fromDate, toDate);
            if (obj[0] != null) {
                lblDoanhThu7Ngay.setText(obj[0].toString());
            } else {
                lblDoanhThu7Ngay.setText("0 ₫");
            }
            lblHDBan7Ngay.setText("Hóa đơn bán: " + obj[1].toString());
            lblHDGiao7Ngay.setText("Hoá đơn giao: " + obj[2].toString());
            lblHDHuy7Ngay.setText("Hóa đơn huỷ: " + obj[3].toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadDoanhThuThang() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int nextMonth = month >= 12 ? 1 : month + 1;
        int nextYear = month >= 12 ? year + 1 : year;
        try {
            Object[] obj = this.daoTK.getDoanhThu(month + "-01-" + year, nextMonth + "-01-" + nextYear);
            if (obj[0] != null) {
                lblDoanhThuThang.setText(obj[0].toString());
            } else {
                lblDoanhThuThang.setText("0 ₫");
            }
            lblHDBanThang.setText("Hóa đơn bán: " + obj[1].toString());
            lblHDGiaoThang.setText("Hoá đơn giao: " + obj[2].toString());
            lblHDHuyThang.setText("Hóa đơn huỷ: " + obj[3].toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadDoanhThuTuyChon() {
        String fromDate = DateHelper.toString(dateFromDT.getDate(), "MM-dd-yyyy");
        String toDate = DateHelper.toString(dateToDT.getDate(), "MM-dd-yyyy");
        try {
            Object[] obj = this.daoTK.getDoanhThu(fromDate, toDate);
            if (obj[0] != null) {
                lblDoanhThuTC.setText(obj[0].toString());
            } else {
                lblDoanhThuTC.setText("0 ₫");
            }
            lblHDBanTC.setText("Hóa đơn bán: " + obj[1].toString());
            lblHDGiaoTC.setText("Hoá đơn giao: " + obj[2].toString());
            lblHDHuyTC.setText("Hóa đơn huỷ: " + obj[3].toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblDoanhThuHN = new javax.swing.JLabel();
        lblHDBanHN = new javax.swing.JLabel();
        lblHDHuyHN = new javax.swing.JLabel();
        lblHDGiaoHN = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblDoanhThu7Ngay = new javax.swing.JLabel();
        lblHDHuy7Ngay = new javax.swing.JLabel();
        lblHDBan7Ngay = new javax.swing.JLabel();
        lblHDGiao7Ngay = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblDoanhThuThang = new javax.swing.JLabel();
        lblHDBanThang = new javax.swing.JLabel();
        lblHDHuyThang = new javax.swing.JLabel();
        lblHDGiaoThang = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblDoanhThuTC = new javax.swing.JLabel();
        lblHDBanTC = new javax.swing.JLabel();
        lblHDHuyTC = new javax.swing.JLabel();
        lblHDGiaoTC = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        dateToDT = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        btnChon = new javax.swing.JButton();
        dateFromDT = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTKSP = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cbbLocLoaiSP = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        dateFromTKSP = new com.toedter.calendar.JDateChooser();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbbLocTrangThai = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        dateToTKSP = new com.toedter.calendar.JDateChooser();
        jPanel9 = new javax.swing.JPanel();
        btnLoc = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));
        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        jPanel2.setBackground(new java.awt.Color(249, 238, 232));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel8.setBackground(new java.awt.Color(249, 238, 232));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0, 30, 0));

        jPanel4.setBackground(new java.awt.Color(141, 110, 99));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Hôm nay");

        lblDoanhThuHN.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        lblDoanhThuHN.setForeground(new java.awt.Color(255, 255, 255));
        lblDoanhThuHN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoanhThuHN.setText("0 ₫");

        lblHDBanHN.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHDBanHN.setForeground(new java.awt.Color(255, 255, 255));
        lblHDBanHN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHDBanHN.setText("Hóa đơn bán: 0");

        lblHDHuyHN.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHDHuyHN.setForeground(new java.awt.Color(255, 255, 255));
        lblHDHuyHN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHDHuyHN.setText("Hóa đơn hủy: 0");

        lblHDGiaoHN.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHDGiaoHN.setForeground(new java.awt.Color(255, 255, 255));
        lblHDGiaoHN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHDGiaoHN.setText("Hóa đơn giao: 0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblDoanhThuHN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblHDBanHN, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
            .addComponent(lblHDHuyHN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblHDGiaoHN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDoanhThuHN)
                .addGap(7, 7, 7)
                .addComponent(lblHDBanHN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addComponent(lblHDGiaoHN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHDHuyHN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        jPanel8.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(141, 110, 99));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("7 ngày");

        lblDoanhThu7Ngay.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        lblDoanhThu7Ngay.setForeground(new java.awt.Color(255, 255, 255));
        lblDoanhThu7Ngay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoanhThu7Ngay.setText("0 ₫");

        lblHDHuy7Ngay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHDHuy7Ngay.setForeground(new java.awt.Color(255, 255, 255));
        lblHDHuy7Ngay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHDHuy7Ngay.setText("Hóa đơn hủy: 0");

        lblHDBan7Ngay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHDBan7Ngay.setForeground(new java.awt.Color(255, 255, 255));
        lblHDBan7Ngay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHDBan7Ngay.setText("Hóa đơn bán: 0");

        lblHDGiao7Ngay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHDGiao7Ngay.setForeground(new java.awt.Color(255, 255, 255));
        lblHDGiao7Ngay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHDGiao7Ngay.setText("Hóa đơn giao: 0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblDoanhThu7Ngay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblHDBan7Ngay, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
            .addComponent(lblHDGiao7Ngay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblHDHuy7Ngay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDoanhThu7Ngay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHDBan7Ngay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHDGiao7Ngay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHDHuy7Ngay)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(141, 110, 99));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Tháng ");

        lblDoanhThuThang.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        lblDoanhThuThang.setForeground(new java.awt.Color(255, 255, 255));
        lblDoanhThuThang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoanhThuThang.setText("0 ₫");

        lblHDBanThang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHDBanThang.setForeground(new java.awt.Color(255, 255, 255));
        lblHDBanThang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHDBanThang.setText("Hóa đơn bán: 0");

        lblHDHuyThang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHDHuyThang.setForeground(new java.awt.Color(255, 255, 255));
        lblHDHuyThang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHDHuyThang.setText("Hóa đơn hủy: 0");

        lblHDGiaoThang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHDGiaoThang.setForeground(new java.awt.Color(255, 255, 255));
        lblHDGiaoThang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHDGiaoThang.setText("Hóa đơn giao: 0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblDoanhThuThang, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
            .addComponent(lblHDBanThang, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
            .addComponent(lblHDGiaoThang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblHDHuyThang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDoanhThuThang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHDBanThang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHDGiaoThang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHDHuyThang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(141, 110, 99));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Tùy chọn");

        lblDoanhThuTC.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        lblDoanhThuTC.setForeground(new java.awt.Color(255, 255, 255));
        lblDoanhThuTC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoanhThuTC.setText("0 ₫");

        lblHDBanTC.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHDBanTC.setForeground(new java.awt.Color(255, 255, 255));
        lblHDBanTC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHDBanTC.setText("Hóa đơn bán: 0");

        lblHDHuyTC.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHDHuyTC.setForeground(new java.awt.Color(255, 255, 255));
        lblHDHuyTC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHDHuyTC.setText("Hóa đơn hủy: 0");

        lblHDGiaoTC.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHDGiaoTC.setForeground(new java.awt.Color(255, 255, 255));
        lblHDGiaoTC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHDGiaoTC.setText("Hóa đơn giao: 0");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblDoanhThuTC, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
            .addComponent(lblHDBanTC, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
            .addComponent(lblHDHuyTC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblHDGiaoTC, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDoanhThuTC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHDBanTC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblHDGiaoTC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHDHuyTC)
                .addGap(23, 23, 23))
        );

        jPanel8.add(jPanel7);

        jPanel13.setBackground(new java.awt.Color(249, 238, 232));
        jPanel13.setAutoscrolls(true);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Từ:");

        dateToDT.setBackground(new java.awt.Color(249, 238, 232));
        dateToDT.setDateFormatString("dd-MM-yyyy");
        ((com.toedter.calendar.JTextFieldDateEditor) dateToDT.getDateEditor()).setEditable(false);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Đến:");

        btnChon.setBackground(new java.awt.Color(238, 238, 238));
        btnChon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChon.setText("Chọn");
        btnChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonActionPerformed(evt);
            }
        });

        dateFromDT.setBackground(new java.awt.Color(249, 238, 232));
        dateFromDT.setDateFormatString("dd-MM-yyyy");
        ((com.toedter.calendar.JTextFieldDateEditor) dateFromDT.getDateEditor()).setEditable(false);

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
                        .addComponent(dateToDT, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(19, 19, 19)
                        .addComponent(dateFromDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChon)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnChon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateFromDT, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateToDT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 1299, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(249, 238, 232));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblTKSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblTKSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Số lượng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTKSP.setRowHeight(30);
        jScrollPane1.setViewportView(tblTKSP);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Thống kê sản phẩm:");

        jPanel10.setLayout(new java.awt.GridLayout(1, 0));

        jPanel11.setBackground(new java.awt.Color(249, 238, 232));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Loại sản phẩm:");

        cbbLocLoaiSP.setBackground(new java.awt.Color(141, 110, 99));
        cbbLocLoaiSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Từ ngày:");

        dateFromTKSP.setBackground(new java.awt.Color(249, 238, 232));
        dateFromTKSP.setDateFormatString("dd-MM-yyyy");
        ((com.toedter.calendar.JTextFieldDateEditor) dateFromTKSP.getDateEditor()).setEditable(false);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbLocLoaiSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateFromTKSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addGap(0, 129, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateFromTKSP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbLocLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel11);

        jPanel12.setBackground(new java.awt.Color(249, 238, 232));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Trạng thái:");

        cbbLocTrangThai.setBackground(new java.awt.Color(141, 110, 99));
        cbbLocTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbbLocTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TẤT CẢ", "Đang bán", "Ngừng bán" }));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Đến ngày:");

        dateToTKSP.setBackground(new java.awt.Color(249, 238, 232));
        dateToTKSP.setDateFormatString("dd-MM-yyyy");
        ((com.toedter.calendar.JTextFieldDateEditor) dateToTKSP.getDateEditor()).setEditable(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(dateToTKSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(38, 157, Short.MAX_VALUE))
                    .addComponent(cbbLocTrangThai, 0, 231, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateToTKSP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbLocTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel12);

        jPanel9.setBackground(new java.awt.Color(249, 238, 232));

        btnLoc.setBackground(new java.awt.Color(238, 238, 238));
        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.setMargin(new java.awt.Insets(10, 20, 10, 20));
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });
        jPanel9.add(btnLoc);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 96, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel1.add(jPanel3);

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void btnChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonActionPerformed
        this.loadDoanhThuTuyChon();
    }//GEN-LAST:event_btnChonActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:
        String fromDate = DateHelper.toString(dateFromTKSP.getDate(), "MM-dd-yyyy");
        String toDate = DateHelper.toString(dateToTKSP.getDate(), "MM-dd-yyyy");
        Object[] objs = {fromDate, toDate, null, null};
        LoaiSanPham lsp = null;
        if (cbbLocLoaiSP.getSelectedIndex() != 0) {
            lsp = (LoaiSanPham) modelCBB_LocLoaiSP.getSelectedItem();
        }
        int trangThai = cbbLocTrangThai.getSelectedIndex();
        if (trangThai != 0 && lsp != null) {
            objs[2] = lsp.getId();
            objs[3] = trangThai != 2;
        } else if (trangThai != 0) {
            objs[3] = trangThai != 2;
        } else if (lsp != null) {
            objs[2] = lsp.getId();
        }
        try {
            List<Object[]> list = this.daoTK.getSanPham(objs);
            this.fillTableTKSP(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnLocActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChon;
    private javax.swing.JButton btnLoc;
    private javax.swing.JComboBox<String> cbbLocLoaiSP;
    private javax.swing.JComboBox<String> cbbLocTrangThai;
    private com.toedter.calendar.JDateChooser dateFromDT;
    private com.toedter.calendar.JDateChooser dateFromTKSP;
    private com.toedter.calendar.JDateChooser dateToDT;
    private com.toedter.calendar.JDateChooser dateToTKSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDoanhThu7Ngay;
    private javax.swing.JLabel lblDoanhThuHN;
    private javax.swing.JLabel lblDoanhThuTC;
    private javax.swing.JLabel lblDoanhThuThang;
    private javax.swing.JLabel lblHDBan7Ngay;
    private javax.swing.JLabel lblHDBanHN;
    private javax.swing.JLabel lblHDBanTC;
    private javax.swing.JLabel lblHDBanThang;
    private javax.swing.JLabel lblHDGiao7Ngay;
    private javax.swing.JLabel lblHDGiaoHN;
    private javax.swing.JLabel lblHDGiaoTC;
    private javax.swing.JLabel lblHDGiaoThang;
    private javax.swing.JLabel lblHDHuy7Ngay;
    private javax.swing.JLabel lblHDHuyHN;
    private javax.swing.JLabel lblHDHuyTC;
    private javax.swing.JLabel lblHDHuyThang;
    private javax.swing.JTable tblTKSP;
    // End of variables declaration//GEN-END:variables
}
