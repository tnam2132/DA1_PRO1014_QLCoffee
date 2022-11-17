package Dao;

import Entity.LoaiSanPham;
import java.sql.ResultSet;

public class LoaiSanPhamDao extends BaseDao<LoaiSanPham> {

    @Override
    public String getQuery(String action) {
        switch (action) {
            case "INSERT":
                return "INSERT INTO LOAISANPHAM (TENLSP) VALUES(?)";
            case "UPDATE":
                return "UPDATE LOAISANPHAM SET TENLSP = ? WHERE MALSP = ?";
            case "SELECT_BY_ID":
                return "SELECT * FROM LOAISANPHAM WHERE ID = ?";
            case "SELECT_BY_MA":
                return "SELECT * FROM LOAISANPHAM WHERE MALSP = ?";
            case "SELECT_ALL":
                return "SELECT * FROM LOAISANPHAM";
        }
        return "";
    }

    @Override
    public Object[] getParams(String action, LoaiSanPham entity) {
        switch (action) {
            case "INSERT":
                return new Object[]{
                    entity.getTenLSP()};
            case "UPDATE":
                return new Object[]{
                    entity.getTenLSP(),
                    entity.getMaLSP()};
            case "SELECT_BY_ID":
                return new Object[]{
                    entity.getId()};
            case "SELECT_BY_MA":
                return new Object[]{
                    entity.getMaLSP()};
        }
        return new Object[0];
    }

    @Override
    public LoaiSanPham createEntity(ResultSet rs) {
        try {
            LoaiSanPham entity = new LoaiSanPham();
            entity.setId(rs.getInt("ID"));
            entity.setMaLSP(rs.getString("MALSP"));
            entity.setTenLSP(rs.getString("TENLSP"));
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
