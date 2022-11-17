package ManagementInterface;

import Dao.BaseDaoInterface;
import Dao.HoaDonChiTietDao;
import Dao.HoaDonDao;
import Dao.KhachHangDao;
import Dao.LoaiThanhToanDao;
import Dao.NguoiDungDao;
import Dao.SanPhamDao;
import Entity.HoaDon;
import Entity.HoaDonChiTiet;
import Entity.KhachHang;
import Entity.LoaiThanhToan;
import Entity.NguoiDung;
import Entity.SanPham;
import Helper.DateHelper;
import Helper.MoneyHelper;
import Helper.DialogHelper;
import Socket.SocketClient;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import Socket.IReadMessage;

public class HoaDonPanel extends javax.swing.JPanel implements IReadMessage {

//    0 - Chờ order
//    1 - Chờ xác nhận
//    2 - Chờ người giao
//    3 - Đang giao
//    4 - Hoàn thành
//    5 - Huỷ
    HoaDonChiTietDao daoHDCT;
    BaseDaoInterface daoKH, daoHD, daoND, daoLTT, daoSP;
    DefaultTableModel model_HD_PhaChe, model_LSHD, model_HDCT, model_LSHDCT;
    HoaDonPanel pn;

    public HoaDonPanel() {
        initComponents();
        this.daoKH = new KhachHangDao();
        this.daoHD = new HoaDonDao();
        this.daoHDCT = new HoaDonChiTietDao();
        this.daoND = new NguoiDungDao();
        this.daoLTT = new LoaiThanhToanDao();
        this.daoSP = new SanPhamDao();
        this.model_HD_PhaChe = (DefaultTableModel) tblHoaDonPhaChe.getModel();
        this.model_LSHD = (DefaultTableModel) tblLSHD.getModel();
        this.model_HDCT = (DefaultTableModel) tblHDCT.getModel();
        this.model_LSHDCT = (DefaultTableModel) tblCTHDLS.getModel();
        this.fillTableHDPhaChe();
        this.loadTableLSHD();
        this.clearFormPhaChe();
        this.clearFormLSHD();
        this.dateFrom.setDate(new Date());
        this.dateTo.setDate(DateHelper.addDay(new Date(), 1));
        SocketClient.setIReadMessage(this);
        SocketClient.connect();
    }

    void clearFormPhaChe() {
        this.model_HDCT.setRowCount(0);
        this.lblMaHD.setText("X");
        this.lblTenKH.setText("X");
        this.lblSDT.setText("X");
        this.lblTgTT.setText("X");
        this.lblTgTao.setText("X");
        this.lblLoaiTT.setText("X");
        this.txtGhiChu.setText("");
        this.lblTrangThai.setText("X");
        this.lblTongTienSP.setText("X");
        this.lblTongTien.setText("X");
        this.lblChiPhiKhac.setText("X");
        this.txtDiaChi.setText("");
    }

    void clearFormLSHD() {
        this.model_LSHDCT.setRowCount(0);
        this.lblMaHDls.setText("X");
        this.lblTenKHls.setText("X");
        this.lblSDTls.setText("X");
        this.lblTGTTls.setText("X");
        this.lblTGtaoLS.setText("X");
        this.lblLoaiTTls.setText("X");
        this.txtGhiChuls.setText("");
        this.lblTrangThails.setText("X");
        this.lblTongTienSPls.setText("X");
        this.lblTongTienls.setText("X");
        this.lblChiPhiKhacls.setText("X");
        this.txtDiaChils.setText("");
    }

    void loadTableLSHD() {
        List<HoaDon> list = daoHD.getAll();
        this.fillTableLSHD(list);
    }

    void fillTableHDPhaChe() {
        this.model_HD_PhaChe.setRowCount(0);
        try {
            List<HoaDon> list = daoHD.selectByInfo(1, null, null, null, null, "TT");
            for (HoaDon hd : list) {
                NguoiDung nd = (NguoiDung) daoND.selectById(hd.getIdND());
                String trangThai = "";
                if (hd.getTrangThai() == 1) {
                    trangThai = "Chờ xác nhận";
                }
                this.model_HD_PhaChe.addRow(new Object[]{
                    hd.getMaHD().trim(),
                    nd.getHoTen(),
                    DateHelper.toString(hd.getTgTao(), "HH:mm dd-MM-yyyy"),
                    MoneyHelper.moneyToString(hd.getTongTien()),
                    trangThai
                });
            }
        } catch (Exception e) {
            DialogHelper.alert(null, "Lỗi fillTablePhaChe!");
        }
    }

    void fillTableLSHD(List<HoaDon> list) {
        this.model_LSHD.setRowCount(0);
        try {
            for (HoaDon hd : list) {
                NguoiDung nd = (NguoiDung) daoND.selectById(hd.getIdND());
                KhachHang kh = (KhachHang) daoKH.selectById(hd.getIdKH());
                LoaiThanhToan ltt = (LoaiThanhToan) daoLTT.selectByMa(hd.getMaLTT());
                String hoTenKH = kh != null ? kh.getHoTen() : "";

                String trangThai = "";
                if (hd.getTrangThai() == 0) {
                    trangThai = "Chờ order";
                } else if (hd.getTrangThai() == 1) {
                    trangThai = "Chờ xác nhận";
                } else if (hd.getTrangThai() == 2) {
                    trangThai = "Chờ người giao";
                } else if (hd.getTrangThai() == 3) {
                    trangThai = "Ðang giao";
                } else if (hd.getTrangThai() == 4) {
                    trangThai = "Hoàn thành";
                } else {
                    trangThai = "Huỷ";
                }
                this.model_LSHD.addRow(new Object[]{
                    hd.getMaHD().trim(),
                    nd.getHoTen(),
                    hoTenKH,
                    DateHelper.toString(hd.getTgTao(), "HH:mm dd-MM-yyyy"),
                    hd.getTgThanhToan() != null ? DateHelper.toString(hd.getTgThanhToan(), "HH:mm dd-MM-yyyy") : "",
                    MoneyHelper.moneyToString(hd.getTongTien()),
                    hd.getDiaChi(),
                    trangThai
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(null, "Lỗi fillTable!");
        }
    }

    double tongTienSP(int idHD) {
        double tongTien = 0;
        try {
            List<HoaDonChiTiet> list = this.daoHDCT.selectByInfo(idHD, true);
            for (HoaDonChiTiet hdct : list) {
                int soLuong = hdct.getSoLuong();
                double donGia = hdct.getDonGia();
                tongTien += donGia * soLuong;
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(null, "Lỗi tongTienSP!");
        }
        return tongTien;
    }

    @Override
    public void readMessage(String message) {
        switch (message) {
            case "UPDATE_HDPC":
                this.fillTableHDPhaChe();
                this.clearFormPhaChe();
                break;
            case "DIALOG_HDPC":
                DialogHelper.alert(null, "Có hoá đơn mới đang chờ xác nhận!");
                break;
            case "UPDATE_LSHD":
                this.loadTableLSHD();
                this.clearFormLSHD();
                break;
            case "UPDATE_HD_ALL":
                this.fillTableHDPhaChe();
                this.loadTableLSHD();
                this.clearFormPhaChe();
                this.clearFormLSHD();
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDonPhaChe = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        btnXacNhanHoaDon = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        lblMaHD = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        lblTenKH = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        lblSDT = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        lblTgTao = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        lblTgTT = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        lblLoaiTT = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        lblChiPhiKhac = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lblTongTienSP = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jPanel41 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLSHD = new javax.swing.JTable() {
            @Override
            public javax.swing.table.TableCellRenderer getCellRenderer(int row, int column) {
                return new TableModelUI.CustomCellRenderer(1);
            }
        };
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        jPanel63 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        dateFrom = new com.toedter.calendar.JDateChooser();
        jPanel9 = new javax.swing.JPanel();
        dateTo = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        cbbTrangThai = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        btnLoc = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel64 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtGhiChuls = new javax.swing.JTextArea();
        jLabel36 = new javax.swing.JLabel();
        jPanel65 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtDiaChils = new javax.swing.JTextArea();
        jPanel48 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jPanel70 = new javax.swing.JPanel();
        jPanel71 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel72 = new javax.swing.JPanel();
        lblMaHDls = new javax.swing.JLabel();
        jPanel67 = new javax.swing.JPanel();
        jPanel68 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel69 = new javax.swing.JPanel();
        lblTenKHls = new javax.swing.JLabel();
        jPanel61 = new javax.swing.JPanel();
        jPanel62 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        a = new javax.swing.JPanel();
        lblSDTls = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel60 = new javax.swing.JPanel();
        lblTGtaoLS = new javax.swing.JLabel();
        jPanel55 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel57 = new javax.swing.JPanel();
        lblTGTTls = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel54 = new javax.swing.JPanel();
        lblLoaiTTls = new javax.swing.JLabel();
        jPanel51 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        lblTrangThails = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblTongTienls = new javax.swing.JLabel();
        lblChiPhiKhacls = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblTongTienSPls = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblCTHDLS = new javax.swing.JTable();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        btnKhoiPhucHD = new javax.swing.JButton();
        jPanel47 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(249, 238, 232));

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel11.setBackground(new java.awt.Color(249, 238, 232));

        tblHoaDonPhaChe.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblHoaDonPhaChe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã HD", "Người tạo", "TG tạo", "Tổng tiền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonPhaChe.setRowHeight(30);
        tblHoaDonPhaChe.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblHoaDonPhaChe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonPhaCheMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDonPhaChe);
        if (tblHoaDonPhaChe.getColumnModel().getColumnCount() > 0) {
            tblHoaDonPhaChe.getColumnModel().getColumn(0).setResizable(false);
            tblHoaDonPhaChe.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblHoaDonPhaChe.getColumnModel().getColumn(1).setResizable(false);
            tblHoaDonPhaChe.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblHoaDonPhaChe.getColumnModel().getColumn(2).setResizable(false);
            tblHoaDonPhaChe.getColumnModel().getColumn(2).setPreferredWidth(50);
            tblHoaDonPhaChe.getColumnModel().getColumn(3).setResizable(false);
            tblHoaDonPhaChe.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblHoaDonPhaChe.getColumnModel().getColumn(4).setResizable(false);
            tblHoaDonPhaChe.getColumnModel().getColumn(4).setPreferredWidth(60);
        }

        jPanel13.setBackground(new java.awt.Color(249, 238, 232));
        jPanel13.setLayout(new java.awt.GridLayout(1, 0));

        jPanel15.setBackground(new java.awt.Color(249, 238, 232));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 149, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel15);

        jPanel16.setBackground(new java.awt.Color(249, 238, 232));

        btnXacNhanHoaDon.setBackground(new java.awt.Color(238, 238, 238));
        btnXacNhanHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnXacNhanHoaDon.setText("Xác nhận HD");
        btnXacNhanHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnXacNhanHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnXacNhanHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel16);

        jPanel17.setBackground(new java.awt.Color(249, 238, 232));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 149, Short.MAX_VALUE)
        );

        jPanel13.add(jPanel17);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Hóa đơn");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel11);

        jPanel12.setBackground(new java.awt.Color(249, 238, 232));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Hóa đơn chi tiết");

        tblHDCT.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên SP", "Đơn giá", "Số lượng", "Thành tiền", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHDCT.setRowHeight(30);
        jScrollPane3.setViewportView(tblHDCT);
        if (tblHDCT.getColumnModel().getColumnCount() > 0) {
            tblHDCT.getColumnModel().getColumn(0).setResizable(false);
            tblHDCT.getColumnModel().getColumn(1).setResizable(false);
            tblHDCT.getColumnModel().getColumn(2).setResizable(false);
            tblHDCT.getColumnModel().getColumn(2).setPreferredWidth(30);
            tblHDCT.getColumnModel().getColumn(3).setResizable(false);
            tblHDCT.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel3.setBackground(new java.awt.Color(249, 238, 232));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jPanel20.setBackground(new java.awt.Color(249, 238, 232));
        jPanel20.setLayout(new java.awt.GridLayout(2, 0));

        jPanel6.setBackground(new java.awt.Color(249, 238, 232));
        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jPanel18.setBackground(new java.awt.Color(249, 238, 232));
        jPanel18.setLayout(new java.awt.GridLayout(6, 0));

        jPanel21.setBackground(new java.awt.Color(249, 238, 232));
        jPanel21.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jPanel22.setBackground(new java.awt.Color(249, 238, 232));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Mã HD:");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel21.add(jPanel22);

        jPanel23.setBackground(new java.awt.Color(249, 238, 232));

        lblMaHD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblMaHD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblMaHD.setText("X");
        lblMaHD.setToolTipText("");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMaHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel21.add(jPanel23);

        jPanel18.add(jPanel21);

        jPanel24.setBackground(new java.awt.Color(249, 238, 232));
        jPanel24.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jPanel25.setBackground(new java.awt.Color(249, 238, 232));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Khách hàng:");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel25);

        jPanel26.setBackground(new java.awt.Color(249, 238, 232));

        lblTenKH.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTenKH.setText("X");
        lblTenKH.setToolTipText("");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel26);

        jPanel18.add(jPanel24);

        jPanel27.setBackground(new java.awt.Color(249, 238, 232));
        jPanel27.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jPanel29.setBackground(new java.awt.Color(249, 238, 232));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel8.setText("SDT:");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel27.add(jPanel29);

        jPanel28.setBackground(new java.awt.Color(249, 238, 232));

        lblSDT.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblSDT.setText("X");
        lblSDT.setToolTipText("");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel27.add(jPanel28);

        jPanel18.add(jPanel27);

        jPanel30.setBackground(new java.awt.Color(249, 238, 232));
        jPanel30.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jPanel31.setBackground(new java.awt.Color(249, 238, 232));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("TG tạo:");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel30.add(jPanel31);

        jPanel32.setBackground(new java.awt.Color(249, 238, 232));

        lblTgTao.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTgTao.setText("X");
        lblTgTao.setToolTipText("");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTgTao, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTgTao, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel30.add(jPanel32);

        jPanel18.add(jPanel30);

        jPanel33.setBackground(new java.awt.Color(249, 238, 232));
        jPanel33.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jPanel34.setBackground(new java.awt.Color(249, 238, 232));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("TG thanh toán:");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel33.add(jPanel34);

        jPanel35.setBackground(new java.awt.Color(249, 238, 232));

        lblTgTT.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTgTT.setText("X");
        lblTgTT.setToolTipText("");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTgTT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTgTT, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel33.add(jPanel35);

        jPanel18.add(jPanel33);

        jPanel36.setBackground(new java.awt.Color(249, 238, 232));
        jPanel36.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jPanel37.setBackground(new java.awt.Color(249, 238, 232));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel17.setText("Loại thanh toán:");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel36.add(jPanel37);

        jPanel38.setBackground(new java.awt.Color(249, 238, 232));

        lblLoaiTT.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblLoaiTT.setText("X");
        lblLoaiTT.setToolTipText("");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLoaiTT, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLoaiTT, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel36.add(jPanel38);

        jPanel18.add(jPanel36);

        jPanel6.add(jPanel18);

        jPanel19.setBackground(new java.awt.Color(249, 238, 232));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel15.setText("Chi phí khác:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel14.setText("Tổng tiền HD:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel18.setText("Trạng thái:");

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 1, 33)); // NOI18N
        lblTrangThai.setText("X");

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongTien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTongTien.setText("0 ₫");

        lblChiPhiKhac.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblChiPhiKhac.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChiPhiKhac.setText("0 ₫");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel29.setText("Tổng tiền SP:");

        lblTongTienSP.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongTienSP.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTongTienSP.setText("0 ₫");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(lblTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblChiPhiKhac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addGap(24, 24, 24)
                                .addComponent(lblTongTienSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(10, 10, 10))))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTongTienSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblChiPhiKhac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTongTien)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel6.add(jPanel19);

        jPanel20.add(jPanel6);

        jPanel39.setBackground(new java.awt.Color(249, 238, 232));
        jPanel39.setLayout(new java.awt.GridLayout(1, 0));

        jPanel40.setBackground(new java.awt.Color(249, 238, 232));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel26.setText("Ghi chú:");

        txtGhiChu.setEditable(false);
        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        txtGhiChu.setText(",");
        jScrollPane5.setViewportView(txtGhiChu);

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jPanel39.add(jPanel40);

        jPanel41.setBackground(new java.awt.Color(249, 238, 232));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel28.setText("Địa chỉ:");

        txtDiaChi.setEditable(false);
        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane4.setViewportView(txtDiaChi);

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(0, 205, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        jPanel39.add(jPanel41);

        jPanel20.add(jPanel39);

        jPanel3.add(jPanel20);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel12);

        jTabbedPane1.addTab("HD pha chế", jPanel1);

        jPanel2.setBackground(new java.awt.Color(249, 238, 232));

        tblLSHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblLSHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã HD", "Người tạo", "Khách hàng", "TG tạo", "TG thanh toán", "Tổng tiền", "Địa chỉ", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLSHD.setRowHeight(30);
        tblLSHD.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblLSHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLSHDMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblLSHD);
        if (tblLSHD.getColumnModel().getColumnCount() > 0) {
            tblLSHD.getColumnModel().getColumn(0).setResizable(false);
            tblLSHD.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblLSHD.getColumnModel().getColumn(1).setResizable(false);
            tblLSHD.getColumnModel().getColumn(2).setResizable(false);
            tblLSHD.getColumnModel().getColumn(3).setResizable(false);
            tblLSHD.getColumnModel().getColumn(4).setResizable(false);
            tblLSHD.getColumnModel().getColumn(5).setResizable(false);
            tblLSHD.getColumnModel().getColumn(6).setResizable(false);
            tblLSHD.getColumnModel().getColumn(7).setResizable(false);
        }

        jPanel5.setBackground(new java.awt.Color(249, 238, 232));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jPanel7.setBackground(new java.awt.Color(249, 238, 232));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Mã HD:");

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTimKiem.setText("Tìm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jPanel63.setBackground(new java.awt.Color(255, 255, 255));
        jPanel63.setLayout(new java.awt.GridLayout());

        jLabel13.setForeground(java.awt.Color.red);
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("+ HD huỷ");
        jPanel63.add(jLabel13);

        jLabel30.setForeground(java.awt.Color.blue);
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("+ HD giao");
        jPanel63.add(jLabel30);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                    .addContainerGap(181, Short.MAX_VALUE)
                    .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(127, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(56, Short.MAX_VALUE)))
        );

        jPanel5.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(249, 238, 232));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0));

        jPanel10.setBackground(new java.awt.Color(249, 238, 232));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Từ ngày:");

        dateFrom.setBackground(new java.awt.Color(249, 238, 232));
        dateFrom.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(dateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel10);

        jPanel9.setBackground(new java.awt.Color(249, 238, 232));

        dateTo.setBackground(new java.awt.Color(249, 238, 232));
        dateTo.setDateFormatString("dd/MM/yyyy");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Đến ngày:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(dateTo, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel9);

        jPanel14.setBackground(new java.awt.Color(249, 238, 232));

        cbbTrangThai.setBackground(new java.awt.Color(141, 110, 99));
        cbbTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chờ order", "Chờ xác nhận", "Chờ người giao", "Đang giao", "Hoàn thành", "Huỷ" }));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Trạng thái:");

        btnLoc.setBackground(new java.awt.Color(238, 238, 238));
        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel12))
                .addGap(24, 24, 24))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel14);

        jPanel5.add(jPanel8);

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        jPanel42.setBackground(new java.awt.Color(249, 238, 232));

        jPanel49.setBackground(new java.awt.Color(249, 238, 232));
        jPanel49.setLayout(new java.awt.GridLayout(1, 0));

        jPanel64.setBackground(new java.awt.Color(249, 238, 232));

        txtGhiChuls.setEditable(false);
        txtGhiChuls.setColumns(20);
        txtGhiChuls.setRows(5);
        txtGhiChuls.setText(",");
        jScrollPane7.setViewportView(txtGhiChuls);

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel36.setText("Ghi chú:");

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                    .addGroup(jPanel64Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jPanel49.add(jPanel64);

        jPanel65.setBackground(new java.awt.Color(249, 238, 232));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel37.setText("Địa chỉ:");

        txtDiaChils.setEditable(false);
        txtDiaChils.setColumns(20);
        txtDiaChils.setRows(5);
        jScrollPane8.setViewportView(txtDiaChils);

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(0, 193, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel49.add(jPanel65);

        jPanel48.setBackground(new java.awt.Color(249, 238, 232));
        jPanel48.setLayout(new java.awt.GridLayout(1, 0));

        jPanel50.setBackground(new java.awt.Color(249, 238, 232));
        jPanel50.setLayout(new java.awt.GridLayout(0, 1));

        jPanel70.setBackground(new java.awt.Color(249, 238, 232));
        jPanel70.setLayout(new java.awt.GridLayout(1, 0));

        jPanel71.setBackground(new java.awt.Color(249, 238, 232));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel20.setText("Mã HD:");

        javax.swing.GroupLayout jPanel71Layout = new javax.swing.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel71Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20)
        );

        jPanel70.add(jPanel71);

        jPanel72.setBackground(new java.awt.Color(249, 238, 232));

        lblMaHDls.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblMaHDls.setText("X");

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMaHDls, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMaHDls, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel70.add(jPanel72);

        jPanel50.add(jPanel70);

        jPanel67.setBackground(new java.awt.Color(249, 238, 232));
        jPanel67.setLayout(new java.awt.GridLayout(1, 0));

        jPanel68.setBackground(new java.awt.Color(249, 238, 232));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel23.setText("Khách hàng:");

        javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel67.add(jPanel68);

        jPanel69.setBackground(new java.awt.Color(249, 238, 232));

        lblTenKHls.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTenKHls.setText("X");

        javax.swing.GroupLayout jPanel69Layout = new javax.swing.GroupLayout(jPanel69);
        jPanel69.setLayout(jPanel69Layout);
        jPanel69Layout.setHorizontalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTenKHls, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
        );
        jPanel69Layout.setVerticalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTenKHls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel67.add(jPanel69);

        jPanel50.add(jPanel67);

        jPanel61.setBackground(new java.awt.Color(249, 238, 232));
        jPanel61.setLayout(new java.awt.GridLayout(1, 0));

        jPanel62.setBackground(new java.awt.Color(249, 238, 232));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel24.setText("SDT:");

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel61.add(jPanel62);

        a.setBackground(new java.awt.Color(249, 238, 232));

        lblSDTls.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblSDTls.setText("X");

        javax.swing.GroupLayout aLayout = new javax.swing.GroupLayout(a);
        a.setLayout(aLayout);
        aLayout.setHorizontalGroup(
            aLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSDTls, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
        );
        aLayout.setVerticalGroup(
            aLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSDTls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel61.add(a);

        jPanel50.add(jPanel61);

        jPanel58.setBackground(new java.awt.Color(249, 238, 232));
        jPanel58.setLayout(new java.awt.GridLayout(1, 0));

        jPanel59.setBackground(new java.awt.Color(249, 238, 232));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel25.setText("TG tạo:");

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel58.add(jPanel59);

        jPanel60.setBackground(new java.awt.Color(249, 238, 232));

        lblTGtaoLS.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTGtaoLS.setText("X");

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTGtaoLS, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTGtaoLS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel58.add(jPanel60);

        jPanel50.add(jPanel58);

        jPanel55.setBackground(new java.awt.Color(249, 238, 232));
        jPanel55.setLayout(new java.awt.GridLayout(1, 0));

        jPanel56.setBackground(new java.awt.Color(249, 238, 232));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel22.setText("TG thanh toán:");

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel55.add(jPanel56);

        jPanel57.setBackground(new java.awt.Color(249, 238, 232));

        lblTGTTls.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTGTTls.setText("X");

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTGTTls, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addComponent(lblTGTTls)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        jPanel55.add(jPanel57);

        jPanel50.add(jPanel55);

        jPanel52.setBackground(new java.awt.Color(249, 238, 232));
        jPanel52.setLayout(new java.awt.GridLayout(1, 0));

        jPanel53.setBackground(new java.awt.Color(249, 238, 232));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel27.setText("Loại thanh toán:");

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addComponent(jLabel27)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        jPanel52.add(jPanel53);

        jPanel54.setBackground(new java.awt.Color(249, 238, 232));

        lblLoaiTTls.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblLoaiTTls.setText("X");

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLoaiTTls, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addComponent(lblLoaiTTls)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        jPanel52.add(jPanel54);

        jPanel50.add(jPanel52);

        jPanel48.add(jPanel50);

        jPanel51.setBackground(new java.awt.Color(249, 238, 232));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel19.setText("Trạng thái:");

        lblTrangThails.setFont(new java.awt.Font("Segoe UI", 1, 33)); // NOI18N
        lblTrangThails.setText("Chờ giao hàng");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel16.setText("Tổng tiền HD:");

        lblTongTienls.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongTienls.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTongTienls.setText("0 ₫");

        lblChiPhiKhacls.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblChiPhiKhacls.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChiPhiKhacls.setText("0 ₫");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel35.setText("Chi phí khác:");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel38.setText("Tổng tiền SP:");

        lblTongTienSPls.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongTienSPls.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTongTienSPls.setText("0 ₫");

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTrangThails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19)
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel35)
                            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblChiPhiKhacls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTongTienls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTongTienSPls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(26, 26, 26))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTrangThails)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTongTienSPls)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChiPhiKhacls)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongTienls)))
        );

        jPanel48.add(jPanel51);

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                    .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel42);

        jPanel43.setBackground(new java.awt.Color(249, 238, 232));

        tblCTHDLS.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblCTHDLS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên SP", "Đơn giá ", "Số lượng", "Thành tiền", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCTHDLS.setRowHeight(30);
        jScrollPane6.setViewportView(tblCTHDLS);
        if (tblCTHDLS.getColumnModel().getColumnCount() > 0) {
            tblCTHDLS.getColumnModel().getColumn(0).setResizable(false);
            tblCTHDLS.getColumnModel().getColumn(1).setResizable(false);
            tblCTHDLS.getColumnModel().getColumn(2).setResizable(false);
            tblCTHDLS.getColumnModel().getColumn(3).setResizable(false);
            tblCTHDLS.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel44.setBackground(new java.awt.Color(249, 238, 232));
        jPanel44.setLayout(new java.awt.GridLayout(1, 0));

        jPanel45.setBackground(new java.awt.Color(249, 238, 232));

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 185, Short.MAX_VALUE)
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 92, Short.MAX_VALUE)
        );

        jPanel44.add(jPanel45);

        jPanel46.setBackground(new java.awt.Color(249, 238, 232));

        btnKhoiPhucHD.setBackground(new java.awt.Color(238, 238, 238));
        btnKhoiPhucHD.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnKhoiPhucHD.setText("Khôi phục HD");
        btnKhoiPhucHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnKhoiPhucHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnKhoiPhucHD, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel44.add(jPanel46);

        jPanel47.setBackground(new java.awt.Color(249, 238, 232));

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 185, Short.MAX_VALUE)
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 92, Short.MAX_VALUE)
        );

        jPanel44.add(jPanel47);

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                    .addComponent(jScrollPane6))
                .addContainerGap())
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel43);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Chi tiết hóa đơn");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 1121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Lịch sử HD", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1145, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXacNhanHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanHoaDonActionPerformed
        int row = tblHoaDonPhaChe.getSelectedRow();
        if (row == -1) {
            DialogHelper.alert(null, "Chưa chọn hóa đơn nào");
            return;
        }
        if (DialogHelper.confirm(null, "Bạn muốn xác nhận hoá đơn?")) {
            String maHD = tblHoaDonPhaChe.getValueAt(row, 0).toString();
            try {
                HoaDon hd = (HoaDon) daoHD.selectByMa(maHD);
                if (hd.getDiaChi() == null) {
                    hd.setTrangThai(4);
                } else {
                    hd.setTrangThai(2);
                }

                if (hd.getIdKH() == 0) {
                    hd.setIdKH(null);
                }
                daoHD.update(hd);
                DialogHelper.alert(null, "Xác nhận thành công!");
                this.fillTableHDPhaChe();
                this.loadTableLSHD();
                if (hd.getTrangThai() == 2) {
                    SocketClient.sendMessage("UPDATE_BH_CHO");
                    SocketClient.sendMessage("DIALOG_BH_GIAO");
                }
            } catch (Exception e) {
                DialogHelper.alert(null, "Xác nhận thất bại!");
            }
        }
        this.clearFormPhaChe();
    }//GEN-LAST:event_btnXacNhanHoaDonActionPerformed

    private void tblHoaDonPhaCheMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonPhaCheMouseClicked
        if (evt.getClickCount() == 1) {
            model_HDCT.setRowCount(0);
            int row = tblHoaDonPhaChe.getSelectedRow();
            String maHD = tblHoaDonPhaChe.getValueAt(row, 0).toString();

            HoaDon hoaDon = (HoaDon) daoHD.selectByMa(maHD);
            KhachHang khachHang = (KhachHang) daoKH.selectById(hoaDon.getIdKH());
            LoaiThanhToan ltt = (LoaiThanhToan) daoLTT.selectByMa(hoaDon.getMaLTT());

            List<HoaDonChiTiet> list = this.daoHDCT.selectByIdHD(hoaDon.getId());
            for (HoaDonChiTiet hdct : list) {
                int soLuong = hdct.getSoLuong();
                double donGia = hdct.getDonGia();
                SanPham sp = (SanPham) daoSP.selectById(hdct.getIdSP());
                model_HDCT.addRow(new Object[]{
                    sp.getTenSP(),
                    MoneyHelper.moneyToString(donGia),
                    soLuong,
                    MoneyHelper.moneyToString(donGia * soLuong),
                    hdct.getGhiChu()
                });

            }
            if (khachHang != null) {
                this.lblTenKH.setText(khachHang.getHoTen());
                this.lblSDT.setText(khachHang.getSdt());
            } else {
                this.lblTenKH.setText("");
                this.lblSDT.setText("");
            }
            this.lblMaHD.setText(maHD);
            this.lblTgTao.setText(DateHelper.toString(hoaDon.getTgTao(), "HH:mm dd-MM-yyyy"));
            this.lblTgTT.setText(hoaDon.getTgThanhToan() != null ? DateHelper.toString(hoaDon.getTgThanhToan(), "HH:mm dd-MM-yyyy") : "");
            this.lblLoaiTT.setText(ltt.getTenLTT());
            this.txtGhiChu.setText(hoaDon.getGhiChu());
            String trangThai = "";
            if (hoaDon.getTrangThai() == 0) {
                trangThai = "Chờ order";
            } else if (hoaDon.getTrangThai() == 1) {
                trangThai = "Chờ xác nhận";
            } else if (hoaDon.getTrangThai() == 2) {
                trangThai = "Chờ người giao";
            } else if (hoaDon.getTrangThai() == 3) {
                trangThai = "Ðang giao";
            } else if (hoaDon.getTrangThai() == 4) {
                trangThai = "Hoàn thành";
            } else {
                trangThai = "Huỷ";
            }
            this.lblTrangThai.setText(trangThai);
            this.lblTongTienSP.setText(MoneyHelper.moneyToString(this.tongTienSP(hoaDon.getId())));
            this.lblTongTien.setText(MoneyHelper.moneyToString(hoaDon.getTongTien()));
            this.lblChiPhiKhac.setText(MoneyHelper.moneyToString(hoaDon.getChiPhiKhac()));
            this.txtDiaChi.setText(hoaDon.getDiaChi());
        }
    }//GEN-LAST:event_tblHoaDonPhaCheMouseClicked

    private void btnKhoiPhucHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucHDActionPerformed
        int row = tblLSHD.getSelectedRow();
        if (row == -1) {
            DialogHelper.alert(null, "Chưa chọn hóa đơn");
            return;
        }
        String maHD = tblLSHD.getValueAt(row, 0).toString();
        HoaDon hd = (HoaDon) daoHD.selectByMa(maHD);
        if (hd.getTrangThai() == 5
                || hd.getTrangThai() == 0) {
            DialogHelper.confirm(null, "Không thể khôi phục khi hóa đơn ở trạng thái này!");
            return;
        }
        if (DialogHelper.confirm(null, "Bạn muốn khôi phục hoá đơn?")) {
            try {
                new KhoiPhucHDDialog(null, true, this, hd).setVisible(true);
            } catch (Exception e) {
                DialogHelper.alert(null, "Lỗi!");
            }
        }
    }//GEN-LAST:event_btnKhoiPhucHDActionPerformed

    private void tblLSHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLSHDMouseClicked
        if (evt.getClickCount() == 2) {
            int row = tblLSHD.getSelectedRow();
            String maHD = tblLSHD.getValueAt(row, 0).toString();
            model_LSHDCT.setRowCount(0);

            HoaDon hoaDon = (HoaDon) daoHD.selectByMa(maHD);
            KhachHang khachHang = (KhachHang) daoKH.selectById(hoaDon.getIdKH());
            LoaiThanhToan ltt = (LoaiThanhToan) daoLTT.selectByMa(hoaDon.getMaLTT());

            List<HoaDonChiTiet> list = this.daoHDCT.selectByInfo(hoaDon.getId(), null);
            for (HoaDonChiTiet hdct : list) {
                int soLuong = hdct.getSoLuong();
                double donGia = hdct.getDonGia();
                SanPham sp = (SanPham) daoSP.selectById(hdct.getIdSP());
                model_LSHDCT.addRow(new Object[]{
                    sp.getTenSP(),
                    MoneyHelper.moneyToString(donGia),
                    soLuong,
                    MoneyHelper.moneyToString(donGia * soLuong),
                    hdct.getGhiChu()
                });

            }
            if (khachHang != null) {
                this.lblTenKHls.setText(khachHang.getHoTen());
                this.lblSDTls.setText(khachHang.getSdt());
            } else {
                this.lblTenKHls.setText("");
                this.lblSDTls.setText("");
            }
            this.lblMaHDls.setText(maHD.trim());
            this.lblTGtaoLS.setText(DateHelper.toString(hoaDon.getTgTao(), "HH:mm dd-MM-yyyy"));
            this.lblTGTTls.setText(hoaDon.getTgThanhToan() != null ? DateHelper.toString(hoaDon.getTgThanhToan(), "HH:mm dd-MM-yyyy") : "");
            if (ltt == null) {
                this.lblLoaiTTls.setText("X");
            } else {
                this.lblLoaiTTls.setText(ltt.getTenLTT());
            }
            this.txtGhiChuls.setText(hoaDon.getGhiChu());
            String trangThai = "";
            if (hoaDon.getTrangThai() == 0) {
                trangThai = "Chờ order";
            } else if (hoaDon.getTrangThai() == 1) {
                trangThai = "Chờ xác nhận";
            } else if (hoaDon.getTrangThai() == 2) {
                trangThai = "Chờ người giao";
            } else if (hoaDon.getTrangThai() == 3) {
                trangThai = "Ðang giao";
            } else if (hoaDon.getTrangThai() == 4) {
                trangThai = "Hoàn thành";
            } else {
                trangThai = "Huỷ";
            }
            this.lblTrangThails.setText(trangThai);
            this.lblTongTienSPls.setText(MoneyHelper.moneyToString(this.tongTienSP(hoaDon.getId())));
            this.lblTongTienls.setText(MoneyHelper.moneyToString(hoaDon.getTongTien()));
            this.lblChiPhiKhacls.setText(MoneyHelper.moneyToString(hoaDon.getChiPhiKhac()));
            this.txtDiaChils.setText(hoaDon.getDiaChi());
        }
    }//GEN-LAST:event_tblLSHDMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String keyword = txtTimKiem.getText();
        List<HoaDon> list = daoHD.selectByKeyword(keyword);
        this.fillTableLSHD(list);
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        int trangThai = cbbTrangThai.getSelectedIndex();
        System.out.println(trangThai);
        try {
            List<HoaDon> list = daoHD.selectByInfo(trangThai, null, null, dateFrom.getDate(), dateTo.getDate(), "TT1_TG1_TG2");
            this.fillTableLSHD(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnLocActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel a;
    private javax.swing.JButton btnKhoiPhucHD;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXacNhanHoaDon;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
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
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
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
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblChiPhiKhac;
    private javax.swing.JLabel lblChiPhiKhacls;
    private javax.swing.JLabel lblLoaiTT;
    private javax.swing.JLabel lblLoaiTTls;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblMaHDls;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblSDTls;
    private javax.swing.JLabel lblTGTTls;
    private javax.swing.JLabel lblTGtaoLS;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblTenKHls;
    private javax.swing.JLabel lblTgTT;
    private javax.swing.JLabel lblTgTao;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTongTienSP;
    private javax.swing.JLabel lblTongTienSPls;
    private javax.swing.JLabel lblTongTienls;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JLabel lblTrangThails;
    private javax.swing.JTable tblCTHDLS;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblHoaDonPhaChe;
    private javax.swing.JTable tblLSHD;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextArea txtDiaChils;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextArea txtGhiChuls;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
