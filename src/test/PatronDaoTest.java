package test;

import org.junit.Assert;
import org.junit.Test;
import dao.PatronDAO;
import model.Patron;

public class PatronDaoTest {

    // ---------------------------------------------------------
    // TEST HÀM KIỂM TRA TRÙNG LẶP CCCD
    // ---------------------------------------------------------

    // Test trường hợp 1: CCCD ĐÃ tồn tại (Giả sử số 123456789 đã có trong DB)
    @Test
    public void testCheckDuplicateCCCD_Found() {
        PatronDAO patronDAO = new PatronDAO();
        // Trả về TRUE nghĩa là đã bị trùng
        boolean isDuplicate = patronDAO.checkDuplicateCCCD("123456789"); 
        Assert.assertTrue(isDuplicate); 
    }

    // Test trường hợp 2: CCCD CHƯA tồn tại
    @Test
    public void testCheckDuplicateCCCD_NotFound() {
        PatronDAO patronDAO = new PatronDAO();
        // Trả về FALSE nghĩa là không trùng, hợp lệ để đăng ký
        boolean isDuplicate = patronDAO.checkDuplicateCCCD("999999999999");
        Assert.assertFalse(isDuplicate); 
    }

    // ---------------------------------------------------------
    // TEST HÀM LƯU THÔNG TIN ĐĂNG KÝ
    // ---------------------------------------------------------

    // Test trường hợp 3: Lưu thành công (Dữ liệu chuẩn chỉnh)
    @Test
    public void testCreatePatron_Success() {
        PatronDAO patronDAO = new PatronDAO();
        Patron p = new Patron();
        
        // Mẹo: Dùng thời gian hệ thống làm ID và CCCD để chạy test nhiều lần không bị lỗi trùng lặp Khóa chính
        String uniqueSuffix = String.valueOf(System.currentTimeMillis()).substring(5); 
        
        p.setPatronID("P" + uniqueSuffix);
        p.setFullName("Nguyen Van Test");
        p.setCccd("C" + uniqueSuffix);
        p.setPhone("0987654321");
        p.setEmail("test" + uniqueSuffix + "@gmail.com");
        p.setStatus("Active");
        p.setCardType("Standard");

        boolean result = patronDAO.createPatron(p);
        Assert.assertTrue(result); // Đảm bảo hàm trả về TRUE (lưu thành công)
    }

    // Test trường hợp 4: Lưu thất bại (Cố tình để null trường Bắt buộc - fullName)
    @Test
    public void testCreatePatron_Fail() {
        PatronDAO patronDAO = new PatronDAO();
        Patron p = new Patron();
        
        p.setPatronID("P_FAIL_001");
        // Cố tình để fullName là NULL (Trong khi SQL setup là NOT NULL)
        p.setFullName(null); 
        p.setCccd("111122223333");

        boolean result = patronDAO.createPatron(p);
        Assert.assertFalse(result); // Đảm bảo hàm bắt được Exception và trả về FALSE
    }
}