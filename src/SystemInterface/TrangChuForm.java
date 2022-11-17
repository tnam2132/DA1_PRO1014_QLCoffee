package SystemInterface;

import Controller.ChuyenManController;
import Controller.DanhMucBean;
import Dao.BaseDaoInterface;
import Dao.HoaDonDao;
import Dao.NguoiDungDao;
import Dao.VaiTroDao;
import Entity.HoaDon;
import Entity.VaiTro;
import Helper.AuthHelper;
import Helper.DateHelper;
import Helper.DialogHelper;
import Helper.ImageHelper;
import ManagementInterface.BanHangPanel;
import Socket.SocketClient;
import java.util.ArrayList;
import java.util.List;

public class TrangChuForm extends javax.swing.JFrame implements Runnable {

    BaseDaoInterface daoND, daoVT, daoHD;

    public TrangChuForm() {
        initComponents();
        this.setIconImage(ImageHelper.getAppIcon());
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setTitle("Dev-Coffee");
        this.daoND = (BaseDaoInterface) new NguoiDungDao();
        this.daoVT = (BaseDaoInterface) new VaiTroDao();
        this.daoHD = (BaseDaoInterface) new HoaDonDao();
        this.setVaiTro();
        new Thread(this).start();

        ChuyenManController controller = new ChuyenManController(jpnView);
        controller.setView(jpnTrangChu);

        List<DanhMucBean> listItem = new ArrayList<>();
        listItem.add(new DanhMucBean("TrangChu", jpnTrangChu));
        listItem.add(new DanhMucBean("BanHang", jpnBanHang));
        listItem.add(new DanhMucBean("HoaDon", jpnHoaDon));
        listItem.add(new DanhMucBean("SanPham", jpnSanPham));
        listItem.add(new DanhMucBean("KhuyenMai", jpnKhuyenMai));
        listItem.add(new DanhMucBean("ThongKe", jpnThongKe));
        listItem.add(new DanhMucBean("NhanVien", jpnNhanVien));
        controller.setEvent(listItem);
    }

    void setVaiTro() {
        try {
            VaiTro vt = (VaiTro) daoVT.selectById(AuthHelper.user.getIdVT());
            this.lblNguoiDung.setText(AuthHelper.user.getHoTen() + " - " + vt.getTenVT());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        jpnMenu = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Jmenu = new javax.swing.JPanel();
        jpnTrangChu = new javax.swing.JPanel();
        lblTrangChu = new javax.swing.JLabel();
        jpnBanHang = new javax.swing.JPanel();
        lblBanHang = new javax.swing.JLabel();
        jpnHoaDon = new javax.swing.JPanel();
        lblHoaDon = new javax.swing.JLabel();
        jpnSanPham = new javax.swing.JPanel();
        lblSanPham = new javax.swing.JLabel();
        jpnKhuyenMai = new javax.swing.JPanel();
        lblKhuyenMai = new javax.swing.JLabel();
        jpnThongKe = new javax.swing.JPanel();
        lblThongKe = new javax.swing.JLabel();
        jpnNhanVien = new javax.swing.JPanel();
        lblNhanVien = new javax.swing.JLabel();
        jpnBar = new javax.swing.JPanel();
        lblTime = new javax.swing.JLabel();
        lblNguoiDung = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnGiaoCa = new javax.swing.JButton();
        btnDoiMK = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        jpnView = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        background.setBackground(new java.awt.Color(249, 238, 232));

        jpnMenu.setBackground(new java.awt.Color(141, 110, 99));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/LogoBig.png"))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("<Dev-Coffee>");

        Jmenu.setBackground(new java.awt.Color(141, 110, 99));
        Jmenu.setLayout(new java.awt.GridLayout(7, 0));

        jpnTrangChu.setBackground(new java.awt.Color(141, 110, 99));
        jpnTrangChu.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTrangChu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        lblTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/TrangChu.png"))); // NOI18N
        lblTrangChu.setText("  Trang chủ");

        javax.swing.GroupLayout jpnTrangChuLayout = new javax.swing.GroupLayout(jpnTrangChu);
        jpnTrangChu.setLayout(jpnTrangChuLayout);
        jpnTrangChuLayout.setHorizontalGroup(
            jpnTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnTrangChuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTrangChu, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnTrangChuLayout.setVerticalGroup(
            jpnTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnTrangChuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTrangChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Jmenu.add(jpnTrangChu);

        jpnBanHang.setBackground(new java.awt.Color(141, 110, 99));
        jpnBanHang.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblBanHang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblBanHang.setForeground(new java.awt.Color(255, 255, 255));
        lblBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/BanHang.png"))); // NOI18N
        lblBanHang.setText("  Bán hàng");

        javax.swing.GroupLayout jpnBanHangLayout = new javax.swing.GroupLayout(jpnBanHang);
        jpnBanHang.setLayout(jpnBanHangLayout);
        jpnBanHangLayout.setHorizontalGroup(
            jpnBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnBanHangLayout.setVerticalGroup(
            jpnBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Jmenu.add(jpnBanHang);

        jpnHoaDon.setBackground(new java.awt.Color(141, 110, 99));
        jpnHoaDon.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        lblHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/HoaDon.png"))); // NOI18N
        lblHoaDon.setText("  Hóa đơn");

        javax.swing.GroupLayout jpnHoaDonLayout = new javax.swing.GroupLayout(jpnHoaDon);
        jpnHoaDon.setLayout(jpnHoaDonLayout);
        jpnHoaDonLayout.setHorizontalGroup(
            jpnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnHoaDonLayout.setVerticalGroup(
            jpnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Jmenu.add(jpnHoaDon);

        jpnSanPham.setBackground(new java.awt.Color(141, 110, 99));
        jpnSanPham.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblSanPham.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSanPham.setForeground(new java.awt.Color(255, 255, 255));
        lblSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/SanPham.png"))); // NOI18N
        lblSanPham.setText("  Sản phẩm");

        javax.swing.GroupLayout jpnSanPhamLayout = new javax.swing.GroupLayout(jpnSanPham);
        jpnSanPham.setLayout(jpnSanPhamLayout);
        jpnSanPhamLayout.setHorizontalGroup(
            jpnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnSanPhamLayout.setVerticalGroup(
            jpnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Jmenu.add(jpnSanPham);

        jpnKhuyenMai.setBackground(new java.awt.Color(141, 110, 99));
        jpnKhuyenMai.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblKhuyenMai.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        lblKhuyenMai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/KhuyenMai.png"))); // NOI18N
        lblKhuyenMai.setText("  Khuyến mại");

        javax.swing.GroupLayout jpnKhuyenMaiLayout = new javax.swing.GroupLayout(jpnKhuyenMai);
        jpnKhuyenMai.setLayout(jpnKhuyenMaiLayout);
        jpnKhuyenMaiLayout.setHorizontalGroup(
            jpnKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnKhuyenMaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblKhuyenMai, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnKhuyenMaiLayout.setVerticalGroup(
            jpnKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnKhuyenMaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblKhuyenMai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Jmenu.add(jpnKhuyenMai);

        jpnThongKe.setBackground(new java.awt.Color(141, 110, 99));
        jpnThongKe.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblThongKe.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblThongKe.setForeground(new java.awt.Color(255, 255, 255));
        lblThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/ThongKe.png"))); // NOI18N
        lblThongKe.setText("  Thống kê");

        javax.swing.GroupLayout jpnThongKeLayout = new javax.swing.GroupLayout(jpnThongKe);
        jpnThongKe.setLayout(jpnThongKeLayout);
        jpnThongKeLayout.setHorizontalGroup(
            jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnThongKeLayout.setVerticalGroup(
            jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Jmenu.add(jpnThongKe);

        jpnNhanVien.setBackground(new java.awt.Color(141, 110, 99));
        jpnNhanVien.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lblNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/NhanVien.png"))); // NOI18N
        lblNhanVien.setText("  Nhân viên");

        javax.swing.GroupLayout jpnNhanVienLayout = new javax.swing.GroupLayout(jpnNhanVien);
        jpnNhanVien.setLayout(jpnNhanVienLayout);
        jpnNhanVienLayout.setHorizontalGroup(
            jpnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnNhanVienLayout.setVerticalGroup(
            jpnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Jmenu.add(jpnNhanVien);

        javax.swing.GroupLayout jpnMenuLayout = new javax.swing.GroupLayout(jpnMenu);
        jpnMenu.setLayout(jpnMenuLayout);
        jpnMenuLayout.setHorizontalGroup(
            jpnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Jmenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpnMenuLayout.setVerticalGroup(
            jpnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnMenuLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Jmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpnBar.setBackground(new java.awt.Color(141, 110, 99));

        lblTime.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setText("22:22 pm - 22/22/2022 ");

        lblNguoiDung.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNguoiDung.setForeground(new java.awt.Color(255, 255, 255));
        lblNguoiDung.setText("Người dùng:");

        jPanel3.setBackground(new java.awt.Color(141, 110, 99));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0, 10, 10));

        btnGiaoCa.setBackground(new java.awt.Color(238, 238, 238));
        btnGiaoCa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnGiaoCa.setText("Giao ca");
        btnGiaoCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGiaoCaActionPerformed(evt);
            }
        });
        jPanel3.add(btnGiaoCa);

        btnDoiMK.setBackground(new java.awt.Color(238, 238, 238));
        btnDoiMK.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDoiMK.setText("Đổi mật khẩu");
        btnDoiMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMKActionPerformed(evt);
            }
        });
        jPanel3.add(btnDoiMK);

        btnDangXuat.setBackground(new java.awt.Color(238, 238, 238));
        btnDangXuat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        jPanel3.add(btnDangXuat);

        javax.swing.GroupLayout jpnBarLayout = new javax.swing.GroupLayout(jpnBar);
        jpnBar.setLayout(jpnBarLayout);
        jpnBarLayout.setHorizontalGroup(
            jpnBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNguoiDung)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                .addComponent(lblTime)
                .addContainerGap())
        );
        jpnBarLayout.setVerticalGroup(
            jpnBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnBarLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblNguoiDung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jpnView.setBackground(new java.awt.Color(238, 238, 238));

        javax.swing.GroupLayout jpnViewLayout = new javax.swing.GroupLayout(jpnView);
        jpnView.setLayout(jpnViewLayout);
        jpnViewLayout.setHorizontalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpnViewLayout.setVerticalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 545, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(jpnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(jpnBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jpnMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(background);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoiMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMKActionPerformed
        new DoiMKDialog(null, true).setVisible(true);
    }//GEN-LAST:event_btnDoiMKActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        if (DialogHelper.confirm(this, "Bạn muốn đăng xuất!")) {
            AuthHelper.clear();
            this.dispose();
            new LoginForm().setVisible(true);
        }
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnGiaoCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGiaoCaActionPerformed
        if (AuthHelper.isThuNgan() || AuthHelper.isManager()) {
            List<HoaDon> listHD = daoHD.getAll();
            for (HoaDon hd : listHD) {
                if (hd.getTrangThai() != 3
                        && hd.getTrangThai() != 4
                        && hd.getTrangThai() != 5) {
                    DialogHelper.alert(this, "Còn hóa đơn chưa xử lí");
                    return;
                }
            }
            new GiaoCaDialog(null, true, this).setVisible(true);
        } else {
            DialogHelper.alert(null, "Bạn không có quyền vào chức năng này!");
        }
    }//GEN-LAST:event_btnGiaoCaActionPerformed

    @Override
    public void run() {
        while (true) {
            try {
                this.lblTime.setText(DateHelper.getDate());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Jmenu;
    private javax.swing.JPanel background;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnDoiMK;
    private javax.swing.JButton btnGiaoCa;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jpnBanHang;
    private javax.swing.JPanel jpnBar;
    private javax.swing.JPanel jpnHoaDon;
    private javax.swing.JPanel jpnKhuyenMai;
    private javax.swing.JPanel jpnMenu;
    private javax.swing.JPanel jpnNhanVien;
    private javax.swing.JPanel jpnSanPham;
    private javax.swing.JPanel jpnThongKe;
    private javax.swing.JPanel jpnTrangChu;
    private javax.swing.JPanel jpnView;
    private javax.swing.JLabel lblBanHang;
    private javax.swing.JLabel lblHoaDon;
    private javax.swing.JLabel lblKhuyenMai;
    private javax.swing.JLabel lblNguoiDung;
    private javax.swing.JLabel lblNhanVien;
    private javax.swing.JLabel lblSanPham;
    private javax.swing.JLabel lblThongKe;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTrangChu;
    // End of variables declaration//GEN-END:variables
}
