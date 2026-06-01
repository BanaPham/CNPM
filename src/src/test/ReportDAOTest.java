package test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Date;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import dao.ReportDAO;

public class ReportDAOTest {
    private ArrayList<String[]> createStatisticRows() {
        ArrayList<String[]> rows = new ArrayList<String[]>();
        rows.add(new String[] { "Tổng số thẻ đang hoạt động", "50" });
        rows.add(new String[] { "Số lượt mượn trong khoảng thời gian", "12" });
        rows.add(new String[] { "Số sách quá hạn chưa trả", "3" });
        rows.add(new String[] { "Doanh thu phí phạt", "120000" });
        return rows;
    }

    private ArrayList<String[]> createDetailRows() {
        ArrayList<String[]> rows = new ArrayList<String[]>();
        rows.add(new String[] { "L001", "2026-03-02", "2026-03-16",
                "Độc giả 001", "Người dùng 001", "I001", "BC001",
                "Sách mẫu 001", "A - 1", "Chưa trả", "Tốt", "0" });
        return rows;
    }

    @Test
    public void testExportExcelCsvStandard() throws Exception {
        ReportDAO rd = new ReportDAO();
        File file = File.createTempFile("library_report_test", ".csv");
        file.deleteOnExit();

        boolean result = rd.exportExcelCsv(file, createStatisticRows(), createDetailRows());
        Assert.assertTrue(result);
        Assert.assertTrue(file.exists());
        Assert.assertTrue(file.length() > 0);

        String content = new String(Files.readAllBytes(file.toPath()),
                StandardCharsets.UTF_8);
        Assert.assertTrue(content.contains("Nội dung,Giá trị"));
        Assert.assertTrue(content.contains("Mã phiếu"));
    }

    @Test
    public void testExportTextReportStandard() throws Exception {
        ReportDAO rd = new ReportDAO();
        File file = File.createTempFile("library_report_test", ".txt");
        file.deleteOnExit();

        boolean result = rd.exportTextReport(file, Date.valueOf("2026-03-01"),
                Date.valueOf("2026-03-31"), createStatisticRows(), createDetailRows());
        Assert.assertTrue(result);
        Assert.assertTrue(file.exists());
        Assert.assertTrue(file.length() > 0);

        String content = new String(Files.readAllBytes(file.toPath()),
                StandardCharsets.UTF_8);
        Assert.assertTrue(content.contains("BÁO CÁO THỐNG KÊ HOẠT ĐỘNG THƯ VIỆN"));
        Assert.assertTrue(content.contains("Phạm vi dữ liệu"));
    }

    @Test
    public void testExportExcelCsvInvalidPath() {
        ReportDAO rd = new ReportDAO();
        File file = new File("/folder_not_exist/report.csv");
        boolean result = rd.exportExcelCsv(file, createStatisticRows(), createDetailRows());
        Assert.assertFalse(result);
    }
}
