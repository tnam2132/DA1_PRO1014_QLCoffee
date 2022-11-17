package Dao;

import Entity.NguoiDung;
import java.sql.ResultSet;

public class NguoiDungDao extends BaseDao<NguoiDung> {

    @Override
    public String getQuery(String action) {
        switch (action) {
            case "INSERT":
                return "INSERT INTO NGUOIDUNG "
                        + "(MATKHAU,HOTEN,NGAYSINH,GIOITINH,EMAIL,SDT,DIACHI,IDVT,GHICHU,HINH,TRANGTHAI) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            case "UPDATE":
                return "UPDATE NGUOIDUNG SET MATKHAU = ?, HOTEN = ?, NGAYSINH = ?, GIOITINH = ?, "
                        + "EMAIL = ?, SDT = ?, DIACHI = ?, IDVT = ?, GHICHU= ?,HINH = ?, TRANGTHAI = ?  WHERE MAND = ?";
            case "SELECT_BY_ID":
                return "SELECT * FROM NGUOIDUNG WHERE ID = ?";
            case "SELECT_BY_MA":
                return "SELECT * FROM NGUOIDUNG WHERE MAND = ?";
            case "SELECT_BY_INFO": // @HOTEN, @MAVT, @TRANGTHAI, @TYPE
                return "{CALL SP_TIMKIEM_ND(?,?,?,?)}";
            case "SELECT_ALL":
                return "SELECT * FROM NGUOIDUNG";
        }
        return "";
    }

    @Override
    public Object[] getParams(String action, NguoiDung entity) {
        switch (action) {
            case "INSERT":
                return new Object[]{
                    entity.getMatKhau(),
                    entity.getHoTen(),
                    entity.getNgaySinh(),
                    entity.isGioiTinh(),
                    entity.getEmail(),
                    entity.getSdt(),
                    entity.getDiaChi(),
                    entity.getIdVT(),
                    entity.getGhiChu(),
                    entity.getHinh(),
                    entity.isTrangThai()};
            case "UPDATE":
                return new Object[]{
                    entity.getMatKhau(),
                    entity.getHoTen(),
                    entity.getNgaySinh(),
                    entity.isGioiTinh(),
                    entity.getEmail(),
                    entity.getSdt(),
                    entity.getDiaChi(),
                    entity.getIdVT(),
                    entity.getGhiChu(),
                    entity.getHinh(),
                    entity.isTrangThai(),
                    entity.getMaND()};
            case "SELECT_BY_ID":
                return new Object[]{
                    entity.getId()};
            case "SELECT_BY_MA":
                return new Object[]{
                    entity.getMaND()};
        }
        return new Object[0];
    }

    @Override
    public NguoiDung createEntity(ResultSet rs) {
        try {
            NguoiDung entity = new NguoiDung();
            entity.setId(rs.getInt("ID"));
            entity.setMaND(rs.getString("MAND"));
            entity.setMatKhau(rs.getString("MATKHAU"));
            entity.setHoTen(rs.getString("HOTEN"));
            entity.setNgaySinh(rs.getDate("NGAYSINH"));
            entity.setGioiTinh(rs.getBoolean("GIOITINH"));
            entity.setEmail(rs.getString("EMAIL"));
            entity.setSdt(rs.getString("SDT"));
            entity.setDiaChi(rs.getString("DIACHI"));
            entity.setIdVT(rs.getInt("IDVT"));
            entity.setGhiChu(rs.getString("GHICHU"));
            entity.setHinh(rs.getString("HINH"));
            entity.setTrangThai(rs.getBoolean("TRANGTHAI"));
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
