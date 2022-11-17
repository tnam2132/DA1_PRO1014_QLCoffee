package ManagementInterfaceDone;

import Dao.BaseDaoInterface;
import Dao.VaiTroDao;
import Entity.VaiTro;
import Helper.DialogHelper;
import Helper.ImageHelper;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class VaiTroDialog extends java.awt.Dialog {

    BaseDaoInterface daoVT;
    DefaultTableModel modelTable;
    int row = -1;

    public VaiTroDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.setIconImage(ImageHelper.getAppIcon());
        this.setLocationRelativeTo(null);
        this.setTitle("Dev-Coffee: Vai trò người dùng");
        this.daoVT = (BaseDaoInterface) new VaiTroDao();
        this.modelTable = (DefaultTableModel) this.tblVaiTro.getModel();
        this.fillTable();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaVT = new javax.swing.JTextField();
        txtTenVT = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVaiTro = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(249, 238, 232));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Mã vai trò:");

        txtMaVT.setEditable(false);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Tên vai trò:");

        tblVaiTro.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tblVaiTro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã vai trò", "Tên vai trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVaiTro.setRowHeight(25);
        tblVaiTro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVaiTroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVaiTro);
        if (tblVaiTro.getColumnModel().getColumnCount() > 0) {
            tblVaiTro.getColumnModel().getColumn(0).setResizable(false);
            tblVaiTro.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel2.setBackground(new java.awt.Color(249, 238, 232));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnThem.setBackground(new java.awt.Color(238, 238, 238));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel2.add(btnThem);

        btnCapNhat.setBackground(new java.awt.Color(238, 238, 238));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        jPanel2.add(btnCapNhat);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenVT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaVT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaVT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenVT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void fillTable() {
        this.modelTable.setRowCount(0);
        try {
            List<VaiTro> list = daoVT.getAll();
            for (VaiTro vt : list) {
                this.modelTable.addRow(new Object[]{vt.getMaVT(), vt.getTenVT()});
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    VaiTro getForm() {
        VaiTro vt = new VaiTro();
        vt.setMaVT(this.txtMaVT.getText());
        vt.setTenVT(this.txtTenVT.getText());
        return vt;
    }

    void setForm(VaiTro vt) {
        this.txtMaVT.setText(vt.getMaVT());
        this.txtTenVT.setText(vt.getTenVT());
        this.tblVaiTro.setRowSelectionInterval(row, row);
    }

    void edit() {
        String maVT = (String) this.tblVaiTro.getValueAt(this.row, 0);
        VaiTro vt = (VaiTro) this.daoVT.selectByMa(maVT);
        this.setForm(vt);
    }

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closeDialog

    private void tblVaiTroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVaiTroMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = this.tblVaiTro.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblVaiTroMouseClicked

    public boolean check() {
        if (this.txtTenVT.getText().equals("")) {
            DialogHelper.alert(this, "Không được để trống thông tin");
            return false;
        }
        List<VaiTro> listVT = this.daoVT.getAll();
        for (VaiTro vaiTro : listVT) {
            if (vaiTro.getTenVT().trim().equalsIgnoreCase(this.txtTenVT.getText().trim())) {
                DialogHelper.alert(this, "Vai trò này đã tồn tại!");
                return false;
            }
        }
        return true;
    }

    void insert() {
        if (DialogHelper.confirm(this, "Xác nhận thêm vai trò")) {
            VaiTro vt = getForm();
            try {
                this.daoVT.insert(vt);
                this.fillTable();
                DialogHelper.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(this, "Thêm mới thất bại!");
            }
        }
    }

    void update() {
        if (DialogHelper.confirm(this, "Xác nhận sửa vai trò")) {
            VaiTro vt = getForm();
            try {
                this.daoVT.update(vt);
                this.fillTable();
                DialogHelper.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (!check()) {
            return;
        }
        this.update();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (!check()) {
            return;
        }
        this.insert();
    }//GEN-LAST:event_btnThemActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VaiTroDialog dialog = new VaiTroDialog(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblVaiTro;
    private javax.swing.JTextField txtMaVT;
    private javax.swing.JTextField txtTenVT;
    // End of variables declaration//GEN-END:variables
}
