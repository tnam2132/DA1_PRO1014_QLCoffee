package Helper;

import Dao.BaseDaoInterface;
import Dao.GiaoCaDao;
import Dao.HoaDonDao;
import Dao.KhachHangDao;
import Dao.NguoiDungDao;
import Dao.ThongKeDAO;
import Entity.GiaoCa;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.NguoiDung;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {

    static BaseDaoInterface daoGC = new GiaoCaDao(),
            daoND = new NguoiDungDao(),
            daoKH = new KhachHangDao(),
            daoHD = new HoaDonDao();
    static ThongKeDAO daoTK = new ThongKeDAO();

    public static void xuatThongKe(String fromDate, String toDate) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Doanh thu");
            xuatTKDoanhThu(sheet, fromDate, toDate);
            sheet = workbook.createSheet("Hoá đơn");
            xuatTKHoaDon(sheet, fromDate, toDate);
            sheet = workbook.createSheet("Giao Ca");
            xuatTKGiaoCa(sheet, fromDate, toDate);
            sheet = workbook.createSheet("Sản phẩm");
            xuatTKSanPham(sheet, fromDate, toDate);
            fromDate = DateHelper.toString(DateHelper.toDate(fromDate, "MM-dd-yyyy"), "dd-MM-yyyy");
            toDate = DateHelper.toString(DateHelper.toDate(toDate, "MM-dd-yyyy"), "dd-MM-yyyy");
            FileOutputStream outputStream = new FileOutputStream("../ThongKe_" + fromDate + "_" + toDate + ".xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void xuatTKDoanhThu(Sheet sheet, String fromDate, String toDate) {
        int rowIndex = 0;
        String[] headerNames = new String[]{
            "Tổng tiền", "HD đã bán", "HD giao", "HD huỷ"
        };
        try {
            Object[] obj = daoTK.getDoanhThu(fromDate, toDate);
            writeHeader(sheet, headerNames, rowIndex++);
            Row row = sheet.createRow(rowIndex);
            Cell cell = row.createCell(0);
            if (obj[0] != null) {
                cell.setCellValue(obj[0].toString());
            } else {
                cell.setCellValue(0);
            }

            cell = row.createCell(1);
            cell.setCellValue((int) obj[1]);

            cell = row.createCell(2);
            cell.setCellValue((int) obj[2]);
            
            cell = row.createCell(3);
            cell.setCellValue((int) obj[3]);
            autosizeColumn(sheet, headerNames.length);
        } catch (Exception e) {
        }
    }

    private static void xuatTKHoaDon(Sheet sheet, String fromDate, String toDate) {
        int rowIndex = 0;
        String[] headerNames = new String[]{
            "Mã HD", "Người tạo", "Khách hàng", "TG tạo", "TG thanh toán", "Tiền khác", "Tổng tiền", "Địa chỉ", "Trạng thái", "Ghi chú"
        };
        try {
            List<HoaDon> list = daoHD.selectByInfo(null, null, null, fromDate, toDate, "TG1_TG2");
            writeHeader(sheet, headerNames, rowIndex++);
            for (HoaDon hoaDon : list) {
                Row row = sheet.createRow(rowIndex);
                writeHoaDon(hoaDon, row);
                rowIndex++;
            }
            autosizeColumn(sheet, headerNames.length);
        } catch (Exception e) {
        }
    }

    private static void writeHoaDon(HoaDon hoaDon, Row row) {
        try {
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            Workbook workbook = row.getSheet().getWorkbook();
            CellStyle cellStyleDefault = workbook.createCellStyle();
            cellStyleDefault.setBorderTop(BorderStyle.THIN);
            cellStyleDefault.setBorderBottom(BorderStyle.THIN);
            cellStyleDefault.setBorderLeft(BorderStyle.THIN);
            cellStyleDefault.setBorderRight(BorderStyle.THIN);

            CellStyle cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setBorderTop(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderBottom(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderLeft(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderRight(BorderStyle.THIN);
            cellStyleFormatNumber.setDataFormat(format);

            Cell cell = row.createCell(0);
            cell.setCellValue(hoaDon.getMaHD());
            cell.setCellStyle(cellStyleDefault);

            NguoiDung nd = (NguoiDung) daoND.selectById(hoaDon.getIdND());
            cell = row.createCell(1);
            cell.setCellValue(nd != null ? nd.getHoTen() : "");
            cell.setCellStyle(cellStyleDefault);

            KhachHang kh = (KhachHang) daoKH.selectById(hoaDon.getIdKH());
            cell = row.createCell(2);
            cell.setCellValue(kh != null ? kh.getHoTen() : "");
            cell.setCellStyle(cellStyleDefault);

            cell = row.createCell(3);
            cell.setCellValue(DateHelper.toString(hoaDon.getTgTao(), "HH:mm dd-MM-yyyy"));
            cell.setCellStyle(cellStyleDefault);

            cell = row.createCell(4);
            cell.setCellValue(hoaDon.getTgThanhToan() != null ? DateHelper.toString(hoaDon.getTgThanhToan(), "HH:mm dd-MM-yyyy") : "");
            cell.setCellStyle(cellStyleDefault);

            cell = row.createCell(5);
            cell.setCellValue(hoaDon.getChiPhiKhac());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(6);
            cell.setCellValue(hoaDon.getTongTien());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(7);
            cell.setCellValue(hoaDon.getDiaChi());
            cell.setCellStyle(cellStyleDefault);

            String trangThai;
            if (hoaDon.getTrangThai() == 0) {
                trangThai = "Chờ order";
            } else if (hoaDon.getTrangThai() == 1) {
                trangThai = "Chờ xác nhận";
            } else if (hoaDon.getTrangThai() == 2) {
                trangThai = "Chờ người giao";
            } else if (hoaDon.getTrangThai() == 3) {
                trangThai = "Ðang giao";
            } else if (hoaDon.getTrangThai() == 4) {
                trangThai = "Hoàn thành";
            } else {
                trangThai = "Huỷ";
            }
            cell = row.createCell(8);
            cell.setCellValue(trangThai);
            cell.setCellStyle(cellStyleDefault);

            cell = row.createCell(9);
            cell.setCellValue(hoaDon.getGhiChu());
            cell.setCellStyle(cellStyleDefault);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void xuatTKGiaoCa(Sheet sheet, String fromDate, String toDate) {
        int rowIndex = 0;
        String[] headerNames = {
            "Mã ca", "Nhân viên", "TG bắt đầu", "TG kết thúc", "Tiền ban đầu",
            "Tiền doanh thu", "Tiền chuyển khoản", "Tiền mặt", "Tổng tiền ca",
            "Tiền chủ thu", "Tiền phát sinh", "Ghi chú"
        };
        try {
            List<GiaoCa> list = daoGC.selectByInfo(fromDate, toDate, "TYPE1");
            writeHeader(sheet, headerNames, rowIndex++);
            for (GiaoCa giaoCa : list) {
                Row row = sheet.createRow(rowIndex);
                writeGiaoCa(giaoCa, row);
                rowIndex++;
            }
            autosizeColumn(sheet, headerNames.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeGiaoCa(GiaoCa giaoCa, Row row) {
        try {
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            Workbook workbook = row.getSheet().getWorkbook();
            CellStyle cellStyleDefault = workbook.createCellStyle();
            cellStyleDefault.setBorderTop(BorderStyle.THIN);
            cellStyleDefault.setBorderBottom(BorderStyle.THIN);
            cellStyleDefault.setBorderLeft(BorderStyle.THIN);
            cellStyleDefault.setBorderRight(BorderStyle.THIN);

            CellStyle cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setBorderTop(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderBottom(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderLeft(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderRight(BorderStyle.THIN);
            cellStyleFormatNumber.setDataFormat(format);

            Cell cell = row.createCell(0);
            cell.setCellValue(giaoCa.getMaCa());
            cell.setCellStyle(cellStyleDefault);

            NguoiDung nd = (NguoiDung) daoND.selectById(giaoCa.getIdND());
            cell = row.createCell(1);
            cell.setCellValue(nd != null ? nd.getHoTen() : "");
            cell.setCellStyle(cellStyleDefault);

            cell = row.createCell(2);
            cell.setCellValue(DateHelper.toString(giaoCa.getTgBatDau(), "HH:mm dd-MM-yyyy"));
            cell.setCellStyle(cellStyleDefault);

            cell = row.createCell(3);
            cell.setCellValue(giaoCa.getTgKetThuc() != null ? DateHelper.toString(giaoCa.getTgKetThuc(), "HH:mm dd-MM-yyyy") : "");
            cell.setCellStyle(cellStyleDefault);

            cell = row.createCell(4);
            cell.setCellValue(giaoCa.getTienBanDau());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(5);
            cell.setCellValue(giaoCa.getTienDoanhThu());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(6);
            cell.setCellValue(giaoCa.getTienChuyenKhoan());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(7);
            cell.setCellValue(giaoCa.getTienMat());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(8);
            cell.setCellValue(giaoCa.getTongTienCa());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(9);
            cell.setCellValue(giaoCa.getTienChuThu());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(10);
            cell.setCellValue(giaoCa.getTienPhatSinh());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(11);
            cell.setCellValue(giaoCa.getGhiChu());
            cell.setCellStyle(cellStyleDefault);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void xuatTKSanPham(Sheet sheet, String fromDate, String toDate) {
        int rowIndex = 0;
        String[] headerNames = {"Mã SP", "Tên SP", "Số lượng", "Trạng thái"};
        try {
            List<Object[]> list = daoTK.getSanPham(fromDate, toDate, null, null);
            writeHeader(sheet, headerNames, rowIndex++);
            for (Object[] objs : list) {
                Row row = sheet.createRow(rowIndex);
                writeSanPham(objs, row);
                rowIndex++;
            }
            autosizeColumn(sheet, headerNames.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeSanPham(Object[] objs, Row row) {
        try {
            Workbook workbook = row.getSheet().getWorkbook();
            CellStyle cellStyleDefault = workbook.createCellStyle();
            cellStyleDefault.setBorderTop(BorderStyle.THIN);
            cellStyleDefault.setBorderBottom(BorderStyle.THIN);
            cellStyleDefault.setBorderLeft(BorderStyle.THIN);
            cellStyleDefault.setBorderRight(BorderStyle.THIN);

            Cell cell = row.createCell(0);
            cell.setCellValue(objs[0].toString());
            cell.setCellStyle(cellStyleDefault);

            cell = row.createCell(1);
            cell.setCellValue(objs[1].toString());
            cell.setCellStyle(cellStyleDefault);

            cell = row.createCell(2);
            cell.setCellValue((int) objs[2]);
            cell.setCellStyle(cellStyleDefault);

            String trangThaiSP = ((boolean) objs[3]) ? "Đang bán" : "Ngừng bán";
            cell = row.createCell(3);
            cell.setCellValue(trangThaiSP);
            cell.setCellStyle(cellStyleDefault);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeHeader(Sheet sheet, String[] headerNames, int rowIndex) {
        // tạo CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // tạo row
        Row row = sheet.createRow(rowIndex);

        // tạo cells
        for (int i = 0; i < headerNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headerNames[i]);
        }
    }

    private static CellStyle createStyleForHeader(Sheet sheet) {
        // tạo font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // tạo CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        if (cellStyle instanceof XSSFCellStyle) {
            XSSFCellStyle xssfCellStyle = (XSSFCellStyle) cellStyle;
            xssfCellStyle.setFillForegroundColor(new XSSFColor(new Color(141, 110, 99)));
        }
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        return cellStyle;
    }

    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
}
