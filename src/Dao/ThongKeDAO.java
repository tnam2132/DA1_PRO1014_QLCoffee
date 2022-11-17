package Dao;

import Helper.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.util.Date;

public class ThongKeDAO {

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                Object[] objs = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    objs[i] = rs.getObject(cols[i]);
                }
                list.add(objs);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object[] getDoanhThu(String fromDate, String toDate) {
        String sql = "{CALL SP_TKDT(?,?)}";
        String[] cols = {"TONG_TIEN", "HD_BAN", "HD_GIAO", "HD_HUY"};
        return getListOfArray(sql, cols, fromDate, toDate).get(0);
    }
    
    public List<Object[]> getSanPham(Object... args) {
        String sql = "{CALL SP_TKSP(?,?,?,?)}";
        String[] cols = {"MASP", "TENSP", "SL", "TRANGTHAI"};
        return getListOfArray(sql, cols, args);
    }
    
}
