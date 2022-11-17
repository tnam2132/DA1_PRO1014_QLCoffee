package Dao;

import Entity.KhuyenMai;
import java.sql.ResultSet;

public class KhuyenMaiDao extends BaseDao<KhuyenMai> {

    @Override
    public String getQuery(String action) {
        switch (action) {
            case "INSERT":
                return "INSERT INTO KHUYENMAI\n"
                        + "(TENKM, LOAIKM, GIATRIKM, NGAYBD, NGAYKT, TRANGTHAI)\n"
                        + "VALUES (?, ?, ?, ?, ?, ?)";
            case "UPDATE":
                return "UPDATE KHUYENMAI\n"
                        + "SET TENKM = ?, LOAIKM = ?, GIATRIKM = ?, NGAYBD = ?, NGAYKT = ?, TRANGTHAI = ?\n"
                        + "WHERE  (MAKM = ?)";
            case "UPDATE_STATUS":
                return "UPDATE KHUYENMAI SET TRANGTHAI = ? WHERE MAKM = ?";
            case "SELECT_BY_ID":
                return "SELECT * FROM KHUYENMAI WHERE ID = ?";
            case "SELECT_BY_MA":
                return "SELECT * FROM KHUYENMAI WHERE MAKM = ?";
            case "SELECT_BY_INFO": // @TENKM, @LOAIKM, @TRANGTHAI, @TYPE
                return "{CALL SP_TIMKIEM_KM(?,?,?,?)}";
            case "SELECT_ALL":
                return "SELECT * FROM KHUYENMAI";
        }
        return "";
    }

    @Override
    public Object[] getParams(String action, KhuyenMai entity) {
        switch (action) {
            case "INSERT":
                return new Object[]{
                    entity.getTenKM(),
                    entity.isLoaiKM(),
                    entity.getGiaTriKM(),
                    entity.getNgayBD(),
                    entity.getNgayKT(),
                    entity.isTrangThai()};
            case "UPDATE":
                return new Object[]{
                    entity.getTenKM(),
                    entity.isLoaiKM(),
                    entity.getGiaTriKM(),
                    entity.getNgayBD(),
                    entity.getNgayKT(),
                    entity.isTrangThai(),
                    entity.getMaKM()
                };
            case "UPDATE_STATUS":
                return new Object[]{
                    entity.isTrangThai(),
                    entity.getMaKM()
                };
            case "SELECT_BY_ID":
                return new Object[]{
                    entity.getId()
                };
            case "SELECT_BY_MA":
                return new Object[]{
                    entity.getMaKM()
                };
        }
        return new Object[0];
    }

    @Override
    public KhuyenMai createEntity(ResultSet rs) {
        try {
            KhuyenMai entity = new KhuyenMai();
            entity.setId(rs.getInt("ID"));
            entity.setMaKM(rs.getString("MAKM"));
            entity.setTenKM(rs.getString("TENKM"));
            entity.setLoaiKM(rs.getBoolean("LOAIKM"));
            entity.setGiaTriKM(rs.getInt("GIATRIKM"));
            entity.setNgayBD(rs.getDate("NGAYBD"));
            entity.setNgayKT(rs.getDate("NGAYKT"));
            entity.setTrangThai(rs.getBoolean("TRANGTHAI"));
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
