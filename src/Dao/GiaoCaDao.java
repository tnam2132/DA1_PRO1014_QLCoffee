package Dao;

import Entity.GiaoCa;
import java.sql.ResultSet;

public class GiaoCaDao extends BaseDao<GiaoCa> {

    @Override
    public String getQuery(String action) {
        switch (action) {
            case "INSERT":
                return "INSERT INTO GIAOCA (TGBATDAU, TIENBANDAU, TRANGTHAI) VALUES (?,?,?)";
            case "UPDATE":
                return "UPDATE GIAOCA SET IDND = ?, TGKETTHUC = ?, TIENBANDAU=?, DOANHTHU = ?,"
                        + " TIENCK = ?, TIENMAT = ?, TONGTIENCA = ?, TIENCHUTHU = ?,\n" +
"                        TIENPHATSINH = ?, GHICHU = ?, TRANGTHAI = ?\n" +
"                         WHERE MACA = ?";
            case "SELECT_BY_ID":
                return "SELECT * FROM GIAOCA WHERE IDND = ? AND TRANGTHAI = 0";
            case "SELECT_NEW":
                return "SELECT TOP 1 * FROM GIAOCA ORDER BY TGBATDAU DESC";
            case "SELECT_BY_MA":
                return "SELECT * FROM GIAOCA WHERE MACA = ?";
            case "SELECT_BY_INFO": // @DATE1, @DATE2, @TYPE
                return "{CALL SP_TIMKIEM_GC(?,?,?)}";
            case "SELECT_ALL":
                return "SELECT * FROM GIAOCA";
        }
        return "";
    }

    @Override
    public Object[] getParams(String action, GiaoCa obj) {
        switch (action) {
            case "INSERT":
                return new Object[]{
                    obj.getTgBatDau(),
                    obj.getTienBanDau(),
                    obj.getTrangThai()
                };
            case "UPDATE":
                return new Object[]{
                    obj.getIdND(),
                    obj.getTgKetThuc(),
                    obj.getTienBanDau(),
                    obj.getTienDoanhThu(),
                    obj.getTienChuyenKhoan(),
                    obj.getTienMat(),
                    obj.getTongTienCa(),
                    obj.getTienChuThu(),
                    obj.getTienPhatSinh(),
                    obj.getGhiChu(),
                    obj.getTrangThai(),
                    obj.getMaCa()};
            case "SELECT_BY_ID":
                return new Object[]{
                    obj.getId()};
            case "SELECT_BY_MA":
                return new Object[]{
                    obj.getMaCa()};
        }
        return new Object[0];
    }

    @Override
    public GiaoCa createEntity(ResultSet rs) {
        try {
            GiaoCa entity = new GiaoCa();
            entity.setId(rs.getInt("ID"));
            entity.setMaCa(rs.getString("MACA"));
            entity.setIdND(rs.getInt("IDND"));
            entity.setTgBatDau(rs.getTimestamp("TGBATDAU"));
            entity.setTgKetThuc(rs.getTimestamp("TGKETTHUC"));
            entity.setTienBanDau(rs.getDouble("TIENBANDAU"));
            entity.setTienDoanhThu(rs.getDouble("DOANHTHU"));
            entity.setTienChuyenKhoan(rs.getDouble("TIENCK"));
            entity.setTienMat(rs.getDouble("TIENMAT"));
            entity.setTongTienCa(rs.getDouble("TONGTIENCA"));
            entity.setTienChuThu(rs.getDouble("TIENCHUTHU"));
            entity.setTienPhatSinh(rs.getDouble("TIENPHATSINH"));
            entity.setGhiChu(rs.getString("GHICHU"));
            entity.setTrangThai(rs.getInt("TRANGTHAI"));
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
