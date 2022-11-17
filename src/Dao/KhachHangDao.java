package Dao;

import Entity.KhachHang;
import java.sql.ResultSet;

public class KhachHangDao extends BaseDao<KhachHang> {

    @Override
    public String getQuery(String action) {
        switch (action) {
            case "INSERT":
                return "INSERT INTO KHACHHANG (HOTEN, SDT, DIACHI, DIEM, TRANGTHAI) VALUES (?,?,?,0,?)";
            case "UPDATE":
                return "UPDATE KHACHHANG SET HOTEN =?, SDT =?, DIACHI =?, DIEM =? WHERE MAKH=?";
            case "SELECT_BY_ID":
                return "SELECT * FROM KHACHHANG WHERE ID=?";
            case "SELECT_BY_MA":
                return "SELECT * FROM KHACHHANG WHERE MAKH=?";
            case "SELECT_BY_INFO":
                return "SELECT * FROM KHACHHANG WHERE SDT=? AND TRANGTHAI=?";
            case "SELECT_ALL":
                return "SELECT * FROM KHACHHANG WHERE TRANGTHAI=1";
            case "SELECT_NEW":
                return "SELECT TOP 1 * FROM KHACHHANG ORDER BY ID DESC";
            case "SELECT_BY_KEYWORD":
                return "SELECT * FROM KHACHHANG WHERE SDT LIKE ?";
        }
        return "";
    }

    @Override
    public Object[] getParams(String action, KhachHang obj) {
        switch (action) {
            case "INSERT":
                return new Object[]{
                    obj.getHoTen(),
                    obj.getSdt(),
                    obj.getDiaChi(),
                    obj.isTrangThai()
                };
            case "UPDATE":
                return new Object[]{
                    obj.getHoTen(),
                    obj.getSdt(),
                    obj.getDiaChi(),
                    obj.getDiem(),
                    obj.getMaKH()
                };
            case "SELECT_BY_INFO":
                return new Object[]{
                    obj.getSdt()
                };
            case "SELECT_BY_ID":
                return new Object[]{
                    obj.getId()
                };
            case "SELECT_BY_MA":
                return new Object[]{
                    obj.getMaKH()
                };
        }
        return new Object[0];
    }

    @Override
    public KhachHang createEntity(ResultSet rs) {
        try {
            KhachHang entity = new KhachHang();
            entity.setId(rs.getInt("ID"));
            entity.setMaKH(rs.getString("MAKH"));
            entity.setHoTen(rs.getString("HOTEN"));
            entity.setSdt(rs.getString("SDT"));
            entity.setDiaChi(rs.getString("DIACHI"));
            entity.setDiem(rs.getInt("DIEM"));
            entity.setTrangThai(rs.getBoolean("TRANGTHAI"));
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
