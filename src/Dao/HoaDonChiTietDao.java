package Dao;

import Entity.HoaDonChiTiet;
import java.sql.ResultSet;
import java.util.List;

public class HoaDonChiTietDao extends BaseDao<HoaDonChiTiet> {

    @Override
    public String getQuery(String action) {
        switch (action) {
            case "INSERT":
                return "INSERT INTO HDCHITIET "
                        + "(IDHD, IDSP, SOLUONG, DONGIA, TRANGTHAI, GHICHU) "
                        + "VALUES (?,?,?,?,?,?)";
            case "UPDATE":
                return "UPDATE HDCHITIET "
                        + "SET SOLUONG=?, DONGIA=?, GHICHU=?, TRANGTHAI=? "
                        + "WHERE IDHD=? AND IDSP=?";
            case "DELETE":
                return "DELETE HDCHITIET WHERE IDHD=? AND IDSP=? AND SOLUONG=? AND TRANGTHAI=?";
            case "SELECT_BY_STATUS":
                return "SELECT * FROM HDCHITIET WHERE IDHD=?";
            case "SELECT_BY_INFO": // @IDHD, @TRANGTHAI
                return "{CALL SP_TIMKIEM_HDCT(?,?)}";
        }
        return "";
    }

    @Override
    public Object[] getParams(String action, HoaDonChiTiet obj) {
        switch (action) {
            case "INSERT":
                return new Object[]{
                    obj.getIdHD(),
                    obj.getIdSP(),
                    obj.getSoLuong(),
                    obj.getDonGia(),
                    obj.isTrangThai(),
                    obj.getGhiChu()
                };
            case "UPDATE":
                return new Object[]{
                    obj.getSoLuong(),
                    obj.getDonGia(),
                    obj.getGhiChu(),
                    obj.isTrangThai(),
                    obj.getIdHD(),
                    obj.getIdSP()
                };
        }
        return new Object[0];
    }

    @Override
    public HoaDonChiTiet createEntity(ResultSet rs) {
        try {
            HoaDonChiTiet entity = new HoaDonChiTiet();
            entity.setIdHD(rs.getInt("IDHD"));
            entity.setIdSP(rs.getInt("IDSP"));
            entity.setSoLuong(rs.getInt("SOLUONG"));
            entity.setDonGia(rs.getDouble("DONGIA"));
            entity.setGhiChu(rs.getString("GHICHU"));
            entity.setTrangThai(rs.getBoolean("TRANGTHAI"));
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<HoaDonChiTiet> selectByIdHD(int idHD) {
        return this.selectBySQL(this.getQuery("SELECT_BY_INFO"), idHD, true);
    }

}
