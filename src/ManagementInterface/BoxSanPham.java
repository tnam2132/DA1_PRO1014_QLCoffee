package ManagementInterface;

import Dao.ApDungKMDao;
import Dao.BaseDaoInterface;
import Dao.KhuyenMaiDao;
import Entity.ApDungKM;
import Entity.KhuyenMai;
import Entity.SanPham;
import Helper.DateHelper;
import Helper.MoneyHelper;
import Helper.DialogHelper;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

public class BoxSanPham extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    BaseDaoInterface daoKM, daoADKM;
    SanPham sp;
    JTabbedPane tabs;

    public BoxSanPham(JTabbedPane tabs, SanPham sp) {
        initComponents();
        this.addMouseListener(this);
        this.daoKM = new KhuyenMaiDao();
        this.daoADKM = new ApDungKMDao();
        this.tabs = tabs;
        this.sp = sp;
        this.setHinhSP();
        this.setTenSP();
        this.setGiaSP();
    }

    void setGiaSP() {
        double donGia = this.sp.getDonGia();
        List<ApDungKM> listADKM = daoADKM.selectByInfo(null, sp.getId(), "SP");
        for (ApDungKM adkm : listADKM) {
            KhuyenMai km = (KhuyenMai) daoKM.selectById(adkm.getIdKM());
            long now = System.currentTimeMillis();
            if (now >= km.getNgayBD().getTime() && now <= km.getNgayKT().getTime()) {
                if (km.isLoaiKM()) {
                    donGia = this.sp.getDonGia() - (this.sp.getDonGia() / 100 * km.getGiaTriKM());
                } else {
                    donGia = this.sp.getDonGia() - km.getGiaTriKM();
                }
                String text = "<html>"
                        + MoneyHelper.moneyToString(donGia)
                        + " - <strike title=\"HTML tutorial\" style=\"font-size: 9px;\">"
                        + MoneyHelper.moneyToString(sp.getDonGia())
                        + "</strike>"
                        + "</html>";
                lblGiaSP.setText(text);
                lblGiaSP.setToolTipText(
                        "Khuyến mại: " + km.getTenKM()
                        + ". Từ " + DateHelper.toString(km.getNgayBD(), "dd-MM")
                        + " đến " + DateHelper.toString(km.getNgayKT(), "dd-MM")
                );
                return;
            }
        }
        lblGiaSP.setText(MoneyHelper.moneyToString(donGia));
    }

    void setTenSP() {
//        String tenSP = sp.getTenSP().length() > 14 ? sp.getTenSP().substring(0, 14) + "..." : sp.getTenSP();
        lblTenSP.setText(sp.getTenSP());
        lblTenSP.setToolTipText(sp.getTenSP());
    }

    void setHinhSP() {
        ImageIcon i = new ImageIcon("Images\\" + sp.getHinh());
        Image img = i.getImage();
        ImageIcon icon = new ImageIcon(img.getScaledInstance(125, 120, img.SCALE_SMOOTH));
        lblHinh.setIcon(icon);
//        lblHinh.setToolTipText("Mô tả: " + this.sp.getMoTa());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pmnSanPham = new javax.swing.JPopupMenu();
        mniThemSP = new javax.swing.JMenuItem();
        lblHinh = new javax.swing.JLabel();
        lblTenSP = new javax.swing.JLabel();
        lblGiaSP = new javax.swing.JLabel();

        mniThemSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mniThemSP.setText("+ Thêm vào HD");
        mniThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniThemSPActionPerformed(evt);
            }
        });
        pmnSanPham.add(mniThemSP);

        setBackground(new java.awt.Color(141, 110, 99));

        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTenSP.setForeground(new java.awt.Color(255, 255, 255));
        lblTenSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblGiaSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblGiaSP.setForeground(new java.awt.Color(255, 255, 255));
        lblGiaSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGiaSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGiaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void mniThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniThemSPActionPerformed
        // TODO add your handling code here:
        BoxHoaDon boxHD = (BoxHoaDon) this.tabs.getSelectedComponent();
        if (boxHD == null) {
            DialogHelper.alert(null, "Không có hoá đơn!");
            return;
        }
        boxHD.addSanPham(this.sp);
    }//GEN-LAST:event_mniThemSPActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblGiaSP;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JMenuItem mniThemSP;
    private javax.swing.JPopupMenu pmnSanPham;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        pmnSanPham.show(this, e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBackground(new Color(190, 156, 145));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(new Color(141, 110, 99));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
