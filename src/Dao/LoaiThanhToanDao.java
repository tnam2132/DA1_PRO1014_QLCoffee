package Dao;

import Entity.LoaiThanhToan;
import java.sql.ResultSet;

public class LoaiThanhToanDao extends BaseDao<LoaiThanhToan> {

    @Override
    public String getQuery(String action) {
        switch (action) {
            case "SELECT_BY_MA":
                return "SELECT * FROM LOAITHANHTOAN WHERE MALTT=?";
            case "SELECT_ALL":
                return "SELECT * FROM LOAITHANHTOAN";
        }
        return "";
    }

    @Override
    public Object[] getParams(String action, LoaiThanhToan obj) {
        switch (action) {
            case "SELECT_BY_MA":
                return new Object[]{
                    obj.getMaLTT()};
        }
        return new Object[0];
    }

    @Override
    public LoaiThanhToan createEntity(ResultSet rs) {
        try {
            LoaiThanhToan entity = new LoaiThanhToan();
            entity.setMaLTT(rs.getString("MALTT"));
            entity.setTenLTT(rs.getString("TENLTT"));
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
