package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Date;
import java.util.ArrayList;

public class ReportDAO extends DAO {
    public ReportDAO() {
        super();
    }

    public boolean exportExcelCsv(File file, ArrayList<String[]> statisticRows, ArrayList<String[]> detailRows) {
        try {
            BufferedWriter bw = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8);
            bw.write("Nội dung,Giá trị");
            bw.newLine();
            for (String[] row : statisticRows) {
                bw.write(escape(row[0]) + "," + escape(row[1]));
                bw.newLine();
            }
            bw.newLine();
            bw.write("Mã phiếu,Ngày mượn,Hạn trả,Độc giả,Nhân viên,Item,Barcode,Tên sách,Vị trí,Ngày trả,Tình trạng,Phí phạt");
            bw.newLine();
            for (String[] row : detailRows) {
                for (int i = 0; i < row.length; i++) {
                    if (i > 0) {
                        bw.write(",");
                    }
                    bw.write(escape(row[i]));
                }
                bw.newLine();
            }
            bw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean exportTextReport(File file, Date startDate, Date endDate, ArrayList<String[]> statisticRows,
            ArrayList<String[]> detailRows) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
            bw.write("BÁO CÁO THỐNG KÊ HOẠT ĐỘNG THƯ VIỆN");
            bw.newLine();
            bw.write("Phạm vi dữ liệu: " + startDate + " đến " + endDate);
            bw.newLine();
            bw.newLine();
            bw.write("I. Thống kê tổng quan");
            bw.newLine();
            for (String[] row : statisticRows) {
                bw.write("- " + row[0] + ": " + row[1]);
                bw.newLine();
            }
            bw.newLine();
            bw.write("II. Chi tiết giao dịch");
            bw.newLine();
            for (String[] row : detailRows) {
                bw.write(row[0] + " | " + row[1] + " | " + row[3] + " | " + row[7] + " | " + row[9]);
                bw.newLine();
            }
            bw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        String text = value.replace("\"", "\"\"");
        if (text.contains(",") || text.contains("\n") || text.contains("\r")) {
            return "\"" + text + "\"";
        }
        return text;
    }
}
