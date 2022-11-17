package Dao;

import Entity.VaiTro;
import java.sql.ResultSet;

public class VaiTroDao extends BaseDao<VaiTro> {

    @Override
    public String getQuery(String action) {
        switch (action) {
            case "INSERT":
                return "INSERT INTO VAITRO (TENVT) VALUES (?)";
            case "UPDATE":
                return "UPDATE VAITRO SET TENVT = ? WHERE MAVT = ?";
            case "SELECT_BY_ID":
                return "SELECT * FROM VAITRO WHERE ID = ?";
            case "SELECT_BY_MA":
                return "SELECT * FROM VAITRO WHERE MAVT = ?";
            case "SELECT_ALL":
                return "SELECT * FROM VAITRO";
        }
        return "";
    }

    @Override
    public Object[] getParams(String action, VaiTro entity) {
        switch (action) {
            case "INSERT":
                return new Object[]{
                    entity.getTenVT()};
            case "UPDATE":
                return new Object[]{
                    entity.getTenVT(),
                    entity.getMaVT()};
            case "SELECT_BY_ID":
                return new Object[]{
                    entity.getId()};
            case "SELECT_BY_MA":
                return new Object[]{
                    entity.getMaVT()};
        }
        return new Object[0];
    }

    @Override
    public VaiTro createEntity(ResultSet rs) {
        try {
            VaiTro entity = new VaiTro();
            entity.setId(rs.getInt("ID"));
            entity.setMaVT(rs.getString("MAVT"));
            entity.setTenVT(rs.getString("TENVT"));
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
