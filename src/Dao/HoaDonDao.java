package Dao;

import Entity.HoaDon;
import java.sql.ResultSet;

public class HoaDonDao extends BaseDao<HoaDon> {

    @Override
    public String getQuery(String action) {
        switch (action) {
            case "INSERT":
                return "INSERT INTO HOADON (IDCA, IDND, TGTAO, TRANGTHAI, GHICHU) VALUES (?,?,?,?,?)";
            case "UPDATE":
                return "UPDATE HOADON "
                        + "SET IDKH =?, TGTHANHTOAN =?, CHIPHIKHAC =?, TONGTIEN =?, DIACHI =?, MALTT =?, GHICHU =?, TRANGTHAI =? "
                        + "WHERE MAHD = ?";
            case "SELECT_BY_ID":
                return "SELECT * FROM HOADON WHERE ID = ?";
            case "SELECT_BY_MA":
                return "SELECT * FROM HOADON WHERE MAHD = ?";
            case "SELECT_BY_INFO": // @TRANGTHAI1, @TRANGTHAI2, @TRANGTHAI3, @THOIGIAN1, @THOIGIAN2, @TYPE
                return "{CALL SP_TIMKIEM_HD(?,?,?,?,?,?)}";
            case "SELECT_BY_KEYWORD":
                return "SELECT * FROM HOADON WHERE MAHD LIKE ?";
            case "SELECT_NEW":
                return "SELECT TOP 1 * FROM HOADON ORDER BY MAHD DESC";
            case "SELECT_ALL":
                return "SELECT * FROM HOADON";
        }
        return "";
    }

    @Override
    public Object[] getParams(String action, HoaDon obj) {
        switch (action) {
            case "INSERT":
                return new Object[]{
                    obj.getIdCa(),
                    obj.getIdND(),
                    obj.getTgTao(),
                    obj.getTrangThai(),
                    obj.getGhiChu()
                };
            case "UPDATE":
                return new Object[]{
                    obj.getIdKH(),
                    obj.getTgThanhToan(),
                    obj.getChiPhiKhac(),
                    obj.getTongTien(),
                    obj.getDiaChi(),
                    obj.getMaLTT(),
                    obj.getGhiChu(),
                    obj.getTrangThai(),
                    obj.getMaHD()
                };
            case "SELECT_BY_ID":
                return new Object[]{
                    obj.getId()
                };
            case "SELECT_BY_MA":
                return new Object[]{
                    obj.getMaHD()
                };
        }
        return new Object[0];
    }

    @Override
    public HoaDon createEntity(ResultSet rs) {
        try {
            HoaDon entity = new HoaDon();
            entity.setId(rs.getInt("ID"));
            entity.setMaHD(rs.getString("MAHD"));
            entity.setIdCa(rs.getInt("IDCA"));
            entity.setIdND(rs.getInt("IDND"));
            entity.setIdKH(rs.getInt("IDKH"));
            entity.setTgTao(rs.getTimestamp("TGTAO"));
            entity.setTgThanhToan(rs.getTimestamp("TGTHANHTOAN"));
            entity.setChiPhiKhac(rs.getDouble("CHIPHIKHAC"));
            entity.setTongTien(rs.getDouble("TONGTIEN"));
            entity.setDiaChi(rs.getString("DIACHI"));
            entity.setMaLTT(rs.getString("MALTT"));
            entity.setGhiChu(rs.getString("GHICHU"));
            entity.setTrangThai(rs.getInt("TRANGTHAI"));
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
