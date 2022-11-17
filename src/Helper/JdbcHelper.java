package Helper;

import java.sql.*;

public class JdbcHelper {

    private static String user = "sa";
    private static String pass = "07022002";
    private static String url = "jdbc:sqlserver://192.168.117.191;databaseName=QLCF";//192.168.0.102:1433
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    /*
    *Nạp driver
     */
    static {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /*
    *Kết nối với DB
    *Tạo prepareStatement gọi hàm hoặc các câu truy vấn SQL 
    *Gán giá trị cho các "?" trong trường hợp có tham số args
    *Trả về ps đã được gán tham số
     */
    public static PreparedStatement getPS(String sql, Object... args) throws SQLException {
        Connection cn = DriverManager.getConnection(url, user, pass);
        PreparedStatement ps = null;
        if (sql.trim().startsWith("{")) {
            ps = cn.prepareCall(sql);
        } else {
            ps = cn.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        return ps;
    }

    /*
    *Gọi preparedStatement đã tạo 
    *Thực hiện câu lệnh thêm, sửa, xóa DB
    *Đóng kết nối
     */
    public static int update(String sql, Object... args) throws SQLException {
        try {
            PreparedStatement ps = getPS(sql, args);
            try {
                return ps.executeUpdate();
            } finally {
                ps.getConnection().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    *Gọi preparedStatement đã tạo 
    *Thực thi các câu truy vấn SELECT của SQL 
    *Hoặc thực thi các hàm bên SQL
    *Trả về các dữ liệu đã được truy vấn 
     */
    public static ResultSet query(String sql, Object... args) throws SQLException {
        PreparedStatement ps = getPS(sql, args);
        return ps.executeQuery();
    }

    /*
    *Truy vấn câu lệnh lấy 1 giá trị
    *Không có trả về null
     */
    public static Object value(String sql, Object... args) {
        try {
            ResultSet rs = query(sql, args);
            if (rs.next()) {
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
