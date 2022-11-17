package SystemInterface;

import Dao.BaseDaoInterface;
import Dao.GiaoCaDao;
import Dao.HoaDonDao;
import Dao.NguoiDungDao;
import Entity.GiaoCa;
import Entity.HoaDon;
import Helper.AuthHelper;
import Helper.DateHelper;
import Helper.DialogHelper;
import Helper.ImageHelper;
import Helper.MoneyHelper;
import java.util.Date;
import java.util.List;

public class GiaoCaDialog extends javax.swing.JDialog {

    BaseDaoInterface daoND, daoGC, daoHD;
    TrangChuForm trangChuForm;
    double tienMat = 0;
    double tienCK = 0;
    double tongTienCa = 0;
    double tienBanDau = 0;
    double tienChuThu = 0;

    public GiaoCaDialog(java.awt.Frame parent, boolean modal, TrangChuForm trangChuForm) {
        super(parent, modal);
        this.initComponents();
        this.setLocationRelativeTo(null);
        this.setIconImage(ImageHelper.getAppIcon());
        this.setTitle("Dev-Coffee - Giao ca");
        this.trangChuForm = trangChuForm;
        this.daoND = (BaseDaoInterface) new NguoiDungDao();
        this.daoGC = (BaseDaoInterface) new GiaoCaDao();
        this.daoHD = (BaseDaoInterface) new HoaDonDao();
        this.setForm();
    }

    void setForm() {
        this.tienMat = 0;
        this.tienCK = 0;
        GiaoCa ca = (GiaoCa) daoGC.selectNew();
        List<HoaDon> listHD = daoHD.getAll();
        this.tienBanDau = ca.getTienBanDau();
        this.tienChuThu = ca.getTienChuThu();
        if (listHD.isEmpty()) {
            this.tongTienCa = ca.getTienBanDau();
        } else {
            for (HoaDon hd : listHD) {
                if (hd.getMaLTT().equalsIgnoreCase("LTT001")
                        && hd.getIdCa() == ca.getId()
                        && hd.getTrangThai() == 4) {
                    this.tienMat += hd.getTongTien();
                } else if (hd.getMaLTT().equalsIgnoreCase("LTT002")
                        && hd.getIdCa() == ca.getId()
                        && hd.getTrangThai() == 4) {
                    tienCK += hd.getTongTien();
                }
                this.tongTienCa = this.tienCK + this.tienMat + ca.getTienBanDau();
            }
        }

        this.lblMaCa.setText(ca.getMaCa().trim());
//        this.lblMaNV.setText(AuthHelper.user.getHoTen().trim());
        this.lblThoiGianBD.setText(DateHelper.toString(ca.getTgBatDau(), "HH:mm dd-MM-yyyy"));
        this.lblThoiGianKT.setText(DateHelper.toString(new Date(), "HH:mm dd-MM-yyyy"));
        this.lblTienBanDau.setText(MoneyHelper.moneyToString(ca.getTienBanDau()));
        this.lblTienDoanhThu.setText(MoneyHelper.moneyToString(this.tienMat + this.tienCK));
        this.lblTienCK.setText(MoneyHelper.moneyToString(this.tienCK));
        this.lblTienMat.setText(MoneyHelper.moneyToString(this.tienMat));
        this.lblTongTienCa.setText(MoneyHelper.moneyToString(this.tongTienCa - ca.getTienChuThu()));
        this.lblTienChuThu.setText(MoneyHelper.moneyToString(ca.getTienChuThu()));
    }

    GiaoCa getForm() {
        this.tienMat = 0;
        this.tienCK = 0;
        GiaoCa ca = (GiaoCa) daoGC.selectNew();
        ca.setTgKetThuc(DateHelper.toDate(this.lblThoiGianKT.getText(), "HH:mm dd-MM-yyyy"));
        List<HoaDon> listHD = daoHD.getAll();

        if (listHD.isEmpty()) {
            this.tongTienCa = ca.getTienBanDau();
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
                this.tongTienCa = tienCK + tienMat + ca.getTienBanDau();
            }
        }
        ca.setIdND(AuthHelper.user.getId());
        ca.setTienBanDau(ca.getTienBanDau());
        ca.setTienDoanhThu(tienCK + tienMat);
        ca.setTienChuyenKhoan(tienCK);
        ca.setTienMat(tienMat);
        ca.setTongTienCa(tienMat + tienCK + ca.getTienBanDau() + Double.parseDouble(this.txtTienPhatSinh.getText()) - ca.getTienChuThu());
        ca.setTienChuThu(ca.getTienChuThu());
        ca.setTienPhatSinh(Double.parseDouble(this.txtTienPhatSinh.getText()));
        ca.setGhiChu(this.txtGhiChu.getText());
        ca.setTrangThai(1);
        return ca;
    }

    void xacNhan() {
//        GiaoCa ca = (GiaoCa) daoGC.selectNew();

        //Xử lí giờ
//        int gioBD = Integer.parseInt(ca.getTgBatDau().substring(0, 2));
//        int phutBD = Integer.parseInt(ca.getTgBatDau().substring(3, 5));
//        int gioKT = Integer.parseInt(this.lblThoiGianKT.getText().substring(0, 2));
//        int phutKT = Integer.parseInt(this.lblThoiGianKT.getText().substring(3, 5));
//
//        int gioCa = gioKT - gioBD;
//        int phutCa = phutKT - phutBD;
//        if (phutCa < 0) {
//            phutCa = 60 - phutBD + phutKT;
//        }
//        int tongGioCa = gioCa * 60 + phutCa;
//        if (tongGioCa < 1) {
//            if (DialogHelper.confirm(null, "Chưa làm đủ thời gian 1 ca, bạn vẫn muốn giao ca?")) {
//
//            }
//        } else {
        if (DialogHelper.confirm(null, "Xác nhận giao ca")) {
            try {
                GiaoCa gc = this.getForm();
                this.daoGC.update(gc);
                DialogHelper.alert(this, "Giao ca thành công!");
                this.dispose();
                AuthHelper.clear();
                this.trangChuForm.dispose();
                new LoginForm().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(this, "Giao ca thất bại!");
            }

        }
//        }
    }

    boolean check() {
        try {
            double tienPhatSinh = Double.parseDouble(this.txtTienPhatSinh.getText());
            if ((this.tienMat + this.tienBanDau) + tienPhatSinh < 0) {
                DialogHelper.alert(this, "Tiền phải lấy phải nhỏ hơn tổng tiền ban đầu với tiền mặt!");
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Tiền phải là một số!");
            e.printStackTrace();
            return false;
        }

        try {
            if (Double.parseDouble(this.txtTienPhatSinh.getText()) != 0
                    && this.txtGhiChu.getText().isEmpty()) {
                DialogHelper.alert(this, "Phải có ghi chú cho tiền phát sinh!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnXacNhan = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtTienPhatSinh = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        lblMaCa = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        lblThoiGianBD = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        lblThoiGianKT = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        lblTienBanDau = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        lblTienDoanhThu = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        lblTienCK = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        lblTienMat = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblTongTienCa = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        lblTienChuThu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("     Ghi chú:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("GIAO CA");

        jPanel2.setBackground(new java.awt.Color(249, 238, 232));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 15, 5));

        btnReset.setBackground(new java.awt.Color(238, 238, 238));
        btnReset.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnReset.setText("Reset ca");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel2.add(btnReset);

        btnXacNhan.setBackground(new java.awt.Color(238, 238, 238));
        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });
        jPanel2.add(btnXacNhan);

        btnHuy.setBackground(new java.awt.Color(238, 238, 238));
        btnHuy.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel2.add(btnHuy);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("     Tiền phát sinh:");

        txtTienPhatSinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTienPhatSinh.setText("0");
        txtTienPhatSinh.setVerifyInputWhenFocusTarget(false);
        txtTienPhatSinh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienPhatSinhKeyReleased(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(249, 238, 232));
        jPanel3.setLayout(new java.awt.GridLayout(10, 0));

        jPanel25.setBackground(new java.awt.Color(249, 238, 232));
        jPanel25.setLayout(new java.awt.GridLayout(1, 0));

        jPanel26.setBackground(new java.awt.Color(249, 238, 232));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("     Mã ca:");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 180, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel25.add(jPanel26);

        jPanel27.setBackground(new java.awt.Color(249, 238, 232));

        lblMaCa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblMaCa.setText("CA101");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(lblMaCa, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 165, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(lblMaCa)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel25.add(jPanel27);

        jPanel3.add(jPanel25);

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
                .addGap(0, 88, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 2, Short.MAX_VALUE))
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
                .addGap(0, 61, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(lblThoiGianBD)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel19.add(jPanel21);

        jPanel3.add(jPanel19);

        jPanel13.setBackground(new java.awt.Color(249, 238, 232));
        jPanel13.setLayout(new java.awt.GridLayout(1, 0));

        jPanel14.setBackground(new java.awt.Color(249, 238, 232));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("     Thời gian kết thúc:");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 86, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel14);

        jPanel15.setBackground(new java.awt.Color(249, 238, 232));

        lblThoiGianKT.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblThoiGianKT.setText("22:22 pm - 22/22/2022 ");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(lblThoiGianKT)
                .addGap(0, 61, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(lblThoiGianKT)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel15);

        jPanel3.add(jPanel13);

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
                .addGap(0, 125, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
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
                .addGap(0, 132, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(lblTienBanDau)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel16.add(jPanel18);

        jPanel3.add(jPanel16);

        jPanel10.setBackground(new java.awt.Color(249, 238, 232));
        jPanel10.setLayout(new java.awt.GridLayout(1, 0));

        jPanel11.setBackground(new java.awt.Color(249, 238, 232));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("     Tiền doanh thu:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 108, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel11);

        jPanel12.setBackground(new java.awt.Color(249, 238, 232));

        lblTienDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTienDoanhThu.setText("9.000.000 VND");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(lblTienDoanhThu)
                .addGap(0, 132, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(lblTienDoanhThu)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel12);

        jPanel3.add(jPanel10);

        jPanel7.setBackground(new java.awt.Color(249, 238, 232));
        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        jPanel8.setBackground(new java.awt.Color(249, 238, 232));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("     Tiền chuyển khoản:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 80, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(249, 238, 232));

        lblTienCK.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTienCK.setText("3.000.000 VND");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(lblTienCK)
                .addGap(0, 132, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(lblTienCK)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel9);

        jPanel3.add(jPanel7);

        jPanel28.setBackground(new java.awt.Color(249, 238, 232));
        jPanel28.setLayout(new java.awt.GridLayout(1, 0));

        jPanel29.setBackground(new java.awt.Color(249, 238, 232));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("     Tiền mặt:");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addGap(0, 159, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel28.add(jPanel29);

        jPanel30.setBackground(new java.awt.Color(249, 238, 232));

        lblTienMat.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTienMat.setText("6.000.000 VND");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(lblTienMat)
                .addGap(0, 132, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(lblTienMat)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel28.add(jPanel30);

        jPanel3.add(jPanel28);

        jPanel4.setBackground(new java.awt.Color(249, 238, 232));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        jPanel5.setBackground(new java.awt.Color(249, 238, 232));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("     Tổng tiền ca hiện có:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 68, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(249, 238, 232));

        lblTongTienCa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongTienCa.setText("10.000.000 VND");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(lblTongTienCa)
                .addGap(0, 122, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(lblTongTienCa)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel6);

        jPanel3.add(jPanel4);

        jPanel31.setBackground(new java.awt.Color(249, 238, 232));
        jPanel31.setLayout(new java.awt.GridLayout(1, 0));

        jPanel32.setBackground(new java.awt.Color(249, 238, 232));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("     Tiền chủ thu:");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 131, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel31.add(jPanel32);

        jPanel33.setBackground(new java.awt.Color(249, 238, 232));

        lblTienChuThu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTienChuThu.setText("10.000.000 VND");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addComponent(lblTienChuThu)
                .addGap(0, 122, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addComponent(lblTienChuThu)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel31.add(jPanel33);

        jPanel3.add(jPanel31);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTienPhatSinh)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(txtTienPhatSinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        if (!this.check()) {
            return;
        }
        this.xacNhan();
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void txtTienPhatSinhKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienPhatSinhKeyReleased
        try {
            double tienPhatSinh = Double.parseDouble(this.txtTienPhatSinh.getText());
            if ((this.tienMat + this.tienBanDau - this.tienChuThu) + tienPhatSinh <= 0) {
                this.lblTongTienCa.setText(MoneyHelper.moneyToString(this.tienCK));
            } else {
                this.lblTongTienCa.setText(MoneyHelper.moneyToString(this.tongTienCa + tienPhatSinh - this.tienChuThu));
            }
        } catch (Exception e) {
            this.lblTongTienCa.setText(MoneyHelper.moneyToString(this.tongTienCa - this.tienChuThu));
        }
    }//GEN-LAST:event_txtTienPhatSinhKeyReleased

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        new ResetCaDialog(null, true, this).setVisible(true);
    }//GEN-LAST:event_btnResetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMaCa;
    private javax.swing.JLabel lblThoiGianBD;
    private javax.swing.JLabel lblThoiGianKT;
    private javax.swing.JLabel lblTienBanDau;
    private javax.swing.JLabel lblTienCK;
    private javax.swing.JLabel lblTienChuThu;
    private javax.swing.JLabel lblTienDoanhThu;
    private javax.swing.JLabel lblTienMat;
    private javax.swing.JLabel lblTongTienCa;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtTienPhatSinh;
    // End of variables declaration//GEN-END:variables

}
