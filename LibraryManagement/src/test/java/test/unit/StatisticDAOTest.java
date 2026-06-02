package test.unit;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import dao.StatisticDAO;

public class StatisticDAOTest {
    private StatisticDAO sd = new StatisticDAO();

    private String findValue(ArrayList<String[]> rows, String label) {
        for (String[] row : rows) {
            if (row[0].equalsIgnoreCase(label)) {
                return row[1];
            }
        }
        return null;
    }

    @Test
    public void testGetStatisticByMonth() {
        Date startDate = Date.valueOf("2026-03-01");
        Date endDate = Date.valueOf("2026-03-31");

        ArrayList<String[]> rows = sd.getStatistic(startDate, endDate);
        Assert.assertNotNull(rows);
        Assert.assertEquals(7, rows.size());
        Assert.assertNotNull(findValue(rows, "Tổng số thẻ đang hoạt động"));
        Assert.assertNotNull(findValue(rows, "Số lượt mượn trong khoảng thời gian"));
        Assert.assertNotNull(findValue(rows, "Doanh thu phí phạt"));
    }

    @Test
    public void testGetStatisticByYear() {
        Date startDate = Date.valueOf("2026-01-01");
        Date endDate = Date.valueOf("2026-12-31");

        ArrayList<String[]> rows = sd.getStatistic(startDate, endDate);
        Assert.assertNotNull(rows);
        Assert.assertEquals(7, rows.size());
        Assert.assertTrue(Integer.parseInt(findValue(rows,
                "Số lượt mượn trong khoảng thời gian")) >= 0);
    }

    @Test
    public void testGetStatisticNoTransactionPeriod() {
        Date startDate = Date.valueOf("2030-01-01");
        Date endDate = Date.valueOf("2030-01-31");

        ArrayList<String[]> rows = sd.getStatistic(startDate, endDate);
        Assert.assertNotNull(rows);
        Assert.assertEquals("0", findValue(rows,
                "Số lượt mượn trong khoảng thời gian"));
        Assert.assertEquals("0", findValue(rows, "Doanh thu phí phạt"));
        Assert.assertEquals("0", findValue(rows, "Số lượt đặt trước"));
    }

    @Test
    public void testGetLoanDetailReportEmpty() {
        Date startDate = Date.valueOf("2030-01-01");
        Date endDate = Date.valueOf("2030-01-31");

        ArrayList<String[]> rows = sd.getLoanDetailReport(startDate, endDate);
        Assert.assertNotNull(rows);
        Assert.assertEquals(0, rows.size());
    }
}
