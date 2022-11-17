package Controller;

import Helper.AuthHelper;
import Helper.DialogHelper;
import ManagementInterface.BanHangPanel;
import ManagementInterface.HoaDonPanel;
import ManagementInterfaceDone.KhuyenMaiPanel;
import ManagementInterface.ThongKeDTSPPanel;
import ManagementInterface.ThongKeGiaoCa;
import ManagementInterfaceDone.NhanVienPanel;
import ManagementInterfaceDone.SanPhamPanel;
import ManagementInterface.ThongKePanel;
import ManagementInterfaceDone.TrangChuPanel;
import Socket.SocketClient;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChuyenManController {

    private JPanel jpnmenu;
    private String select = "";

    private List<DanhMucBean> listItem = null;

    public ChuyenManController(JPanel jpnMenu) {
        this.jpnmenu = jpnMenu;
    }

    public void setView(JPanel jpnItem) {
        select = "TrangChu";
        jpnItem.setBackground(new Color(190, 156, 145));

        jpnmenu.removeAll();
        jpnmenu.setLayout(new BorderLayout());
        jpnmenu.add(new TrangChuPanel());
        jpnmenu.validate();
        jpnmenu.repaint();
    }

    public void setEvent(List<DanhMucBean> listItem) {
        this.listItem = listItem;
        for (DanhMucBean item : listItem) {
            item.getJpn().addMouseListener(new LabelEvent(item.getKind(), item.getJpn()));
        }
    }

    class LabelEvent implements MouseListener {

        private JPanel node;
        private String kind;

        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "TrangChu":
                    node = new TrangChuPanel();
                    break;
                case "BanHang":
                    if (AuthHelper.isThuNgan() || AuthHelper.isManager()) {
                        SocketClient.close();
                        node = new BanHangPanel();
                    } else {
                        DialogHelper.alert(null, "Bạn không có quyền vào chức năng này!");
                        return;
                    }
                    break;
                case "HoaDon":
                    if (AuthHelper.isPhaChe() || AuthHelper.isManager()) {
                        SocketClient.close();
                        node = new HoaDonPanel();
                    } else {
                        DialogHelper.alert(null, "Bạn không có quyền vào chức năng này!");
                        return;
                    }
                    break;
                case "SanPham":
                    if (AuthHelper.isManager()) {
                        node = new SanPhamPanel();
                    } else {
                        DialogHelper.alert(null, "Bạn không có quyền vào chức năng này!");
                        return;
                    }
                    break;
                case "KhuyenMai":
                    if (AuthHelper.isManager()) {
                        node = new KhuyenMaiPanel();
                    } else {
                        DialogHelper.alert(null, "Bạn không có quyền vào chức năng này!");
                        return;
                    }
                    break;
                case "ThongKe":
                    if (AuthHelper.isThuNgan() || AuthHelper.isManager()) {
                        node = new ThongKePanel();
                    } else {
                        DialogHelper.alert(null, "Bạn không có quyền vào chức năng này!");
                        return;
                    }
                    break;
                case "NhanVien":
                    if (AuthHelper.isManager()) {
                        node = new NhanVienPanel();
                    } else {
                        DialogHelper.alert(null, "Bạn không có quyền vào chức năng này!");
                        return;
                    }
                    break;
                case "ThongKeDTSP":
                    node = new ThongKeDTSPPanel();
                    break;
                case "ThongKeGiaoCa":
                    node = new ThongKeGiaoCa();
                    break;
                default:
                    node = new TrangChuPanel();
                    break;
            }
            select = kind;
            jpnmenu.removeAll();
            jpnmenu.setLayout(new BorderLayout());
            jpnmenu.add(node);
            jpnmenu.validate();
            jpnmenu.repaint();
            setChangeBackground(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            jpnItem.setBackground(new Color(190, 156, 145));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(190, 156, 145));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!select.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(141, 110, 99));
            }
        }

    }

    private void setChangeBackground(String kind) {
        for (DanhMucBean item : listItem) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(new Color(190, 156, 145));
            } else {
                item.getJpn().setBackground(new Color(141, 110, 99));
            }
        }
    }
}
