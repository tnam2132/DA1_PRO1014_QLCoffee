package Dao;

import java.sql.ResultSet;
import Entity.ApDungKM;

public class ApDungKMDao extends BaseDao<ApDungKM> {

    @Override
    public String getQuery(String action) {
        switch (action) {
            case "INSERT":
                return "INSERT INTO APDUNGKM (IDKM, IDSP) VALUES(?, ?)";
            case "DELETE":
                return "DELETE APDUNGKM WHERE IDKM = ? AND IDSP = ?";
            case "SELECT_BY_INFO": // @MAKM, @MASP, @TYPE
                return "{CALL SP_TIMKIEM_ADKM(?,?,?)}";
            case "SELECT_ALL":
                return "SELECT * FROM APDUNGKM";
        }
        return "";
    }

    @Override
    public Object[] getParams(String action, ApDungKM entity) {
        switch (action) {
            case "INSERT":
                return new Object[]{
                    entity.getIdKM(),
                    entity.getIdSP()};
            case "DELETE":
                return new Object[]{
                    entity.getIdKM(),
                    entity.getIdSP()};
            case "SELECT_BY_ID":
                return new Object[]{
                    entity.getIdKM()};
        }
        return new Object[0];
    }

    @Override
    public ApDungKM createEntity(ResultSet rs) {
        try {
            ApDungKM entity = new ApDungKM();
            entity.setIdKM(rs.getInt("IDKM"));
            entity.setIdSP(rs.getInt("IDSP"));
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
