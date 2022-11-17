package Dao;

import Entity.SanPham;
import java.sql.ResultSet;

public class SanPhamDao extends BaseDao<SanPham> {

    @Override
    public String getQuery(String action) {
        switch (action) {
            case "INSERT":
                return "INSERT INTO SANPHAM "
                        + "(TENSP,IDLSP,DONGIA,HINH,MOTA,GHICHU,TRANGTHAI)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            case "UPDATE":
                return "UPDATE SANPHAM SET TENSP = ?,IDLSP = ?, DONGIA = ?, HINH = ?, "
                        + "MOTA = ?, GHICHU = ?, TRANGTHAI =? WHERE MASP = ?";
            case "SELECT_BY_ID":
                return "SELECT * FROM SANPHAM WHERE ID = ?";
            case "SELECT_BY_MA":
                return "SELECT * FROM SANPHAM WHERE MASP = ?";
            case "SELECT_BY_INFO": // @TENSP, @MALOAISP, @TRANGTHAI, @TYPE
                return "{CALL SP_TIMKIEM_SP(?,?,?,?)}";
            case "SELECT_ALL":
                return "SELECT * FROM SANPHAM";
        }
        return "";
    }

    @Override
    public Object[] getParams(String action, SanPham entity) {
        switch (action) {
            case "INSERT":
                return new Object[]{
                    entity.getTenSP(),
                    entity.getIdLSP(),
                    entity.getDonGia(),
                    entity.getHinh(),
                    entity.getMoTa(),
                    entity.getGhiChu(),
                    entity.isTrangThai()};
            case "UPDATE":
                return new Object[]{
                    entity.getTenSP(),
                    entity.getIdLSP(),
                    entity.getDonGia(),
                    entity.getHinh(),
                    entity.getMoTa(),
                    entity.getGhiChu(),
                    entity.isTrangThai(),
                    entity.getMaSP()};
            case "SELECT_BY_ID":
                return new Object[]{
                    entity.getId()};
            case "SELECT_BY_MA":
                return new Object[]{
                    entity.getMaSP()};
        }
        return new Object[0];
    }

    @Override
    public SanPham createEntity(ResultSet rs) {
        try {
            SanPham entity = new SanPham();
            entity.setId(rs.getInt("ID"));
            entity.setMaSP(rs.getString("MASP"));
            entity.setTenSP(rs.getString("TENSP"));
            entity.setIdLSP(rs.getInt("IDLSP"));
            entity.setDonGia(rs.getDouble("DONGIA"));
            entity.setHinh(rs.getString("HINH"));
            entity.setMoTa(rs.getString("MOTA"));
            entity.setGhiChu(rs.getString("GHICHU"));
            entity.setTrangThai(rs.getBoolean("TRANGTHAI"));
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
