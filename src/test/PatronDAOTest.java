package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Patron;
import dao.PatronDAO;
import java.sql.Connection;
import java.util.Date;
public class PatronDAOTest {
    private PatronDAO patronDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() throws Exception {
        patronDAO = new PatronDAO();
    }

    @AfterEach
    public void tearDown() throws Exception {
    }
    // =========================
    // TEST checkLogin()
    // =========================

    @Test
    public void testCheckLogin_ValidAccount() {
        // Tạo dữ liệu test
        Patron p = new Patron();
        p.setUsername("nguyenvana");
        p.setPassword("123456");
        // Khởi tạo DAO
        PatronDAO dao = new PatronDAO();
        // Gọi hàm check login
        boolean result = dao.checkLogin(p);
        // Kiểm tra kết quả
        assertTrue(result);
    }

    @Test
    public void testCheckLogin_WrongPassword() {
        // Tạo dữ liệu test
        Patron p = new Patron();
        p.setUsername("nguyenvana");
        p.setPassword("abcxyz");
        // Khởi tạo DAO
        PatronDAO dao = new PatronDAO();
        // Gọi hàm check login
        boolean result = dao.checkLogin(p);
        // Kiểm tra kết quả
        assertFalse(result);
    }

    @Test
    public void testCheckLogin_NotExistUsername() {
        // Tạo dữ liệu test
        Patron p = new Patron();
        p.setUsername("khongtontai");
        p.setPassword("123456");
        // Khởi tạo DAO
        PatronDAO dao = new PatronDAO();
        // Gọi hàm check login
        boolean result = dao.checkLogin(p);
        // Kiểm tra kết quả
        assertFalse(result);
    }

    @Test
    public void testCheckLogin_EmptyUsernameOrPassword() {
        // Tạo dữ liệu test
        Patron p = new Patron();
        p.setUsername("");
        p.setPassword("");
        // Khởi tạo DAO
        PatronDAO dao = new PatronDAO();
        // Gọi hàm check login
        boolean result = dao.checkLogin(p);
        // Kiểm tra kết quả
        assertFalse(result);
    }

    // =========================
    // TEST getProfile()
    // ========================
    @Test
    public void testGetProfile_ExistPatronID() {
        // Khởi tạo DAO
        PatronDAO dao = new PatronDAO();
        // Gọi hàm cần test
        Patron p = dao.getProfile("P001");
        // Kiểm tra kết quả
        assertNotNull(p);
        // Kiểm tra dữ liệu trả về đúng
        assertEquals("P001", p.getPatronID());
    }

    @Test
    public void testGetProfile_NotExistPatronID() {
        // Khởi tạo DAO
        PatronDAO dao = new PatronDAO();
        // Gọi hàm cần test
        Patron p = dao.getProfile("XXX");
        // Kiểm tra kết quả
        assertNull(p);
    }

    // =========================
    // TEST updateProfile()
    // =========================

    @Test
    public void testUpdateProfile_ValidData() {
        // Tạo dữ liệu test
        Patron p = new Patron();
        p.setPatronID("P001");
        p.setPassword("123456");
        p.setPhone("0988888888");
        p.setAddress("Cầu Giấy");
        // Khởi tạo DAO
        PatronDAO dao = new PatronDAO();
        // Gọi hàm update
        boolean result = dao.updateProfile(p);
        // Kiểm tra kết quả
        assertTrue(result);
    }

    @Test
    public void testUpdateProfile_EmptyAddress() {
        // Tạo dữ liệu test
        Patron p = new Patron();
        p.setPatronID("P001");
        p.setPassword("123456");
        p.setPhone("0988888888");
        p.setAddress("");
        // Khởi tạo DAO
        PatronDAO dao = new PatronDAO();
        // Gọi hàm update
        boolean result = dao.updateProfile(p);
        // Kiểm tra kết quả mong muốn
        assertFalse(result);
    }

    @Test
    public void testUpdateProfile_EmptyPhone() {
        // Tạo dữ liệu test
        Patron p = new Patron();
        p.setPatronID("P001");
        p.setPassword("123456");
        p.setPhone("");
        p.setAddress("Cầu Giấy");

        // Khởi tạo DAO
        PatronDAO dao = new PatronDAO();
        // Gọi hàm update
        boolean result = dao.updateProfile(p);
        // Kiểm tra kết quả mong muốn
        assertFalse(result);
    }

    // =========================
    // TEST searchCard()
    // =========================
    /**
     * Case 1.1: CardID/fullName khớp với CSDL
     */
    @Test
    public void testSearchCard_ValidCriteria_ReturnsMatchedPatrons() {
        // Khởi tạo đối tượng chứa thông tin tìm kiếm hợp lệ
        Patron searchParam = new Patron();
        searchParam.setFullName("Nguyen Van A");
        // Gọi hàm cần kiểm thử
        Patron[] result = patronDAO.searchCard(searchParam);
        // Kiểm tra kết quả
        assertNotNull(result, "Kết quả trả về không được phép null (nên là mảng)");
        assertTrue(result.length > 0, "Phải tìm thấy ít nhất 1 thẻ khớp với dữ liệu đầu vào");
        assertEquals("Nguyen Van A", result[0].getFullName(), "Tên của thẻ tìm thấy phải trùng khớp");
    }

    /**
     * Case 1.2: CardID/fullName không tồn tại trong CSDL
     */
    @Test
    public void testSearchCard_InvalidCriteria_ReturnsNullOrEmpty() {
        // Khởi tạo thông tin tìm kiếm một chuỗi ngẫu nhiên không thể có trong DB
        Patron searchParam = new Patron();
        searchParam.setFullName("Nguyễn Văn B");
        // Gọi hàm cần kiểm thử
        Patron[] result = patronDAO.searchCard(searchParam);
        // Kiểm tra kết quả đối chiếu theo đúng mong muốn của bảng: "trả về null"
        // (Nếu code thực tế trả về mảng rỗng, hãy sửa thành: assertEquals(0, result.length);)
        assertNull(result, "Hàm phải trả về null khi không tìm thấy bất kỳ thẻ nào khớp");
    }

    // =========================
    // TEST updateStatus()
    // =========================
    /**
     * Case 2.1: Cập nhật trạng thái cho patronID hợp lệ.
     */
    @Test
    public void testUpdateStatus_ValidPatronID_ReturnsTrue() {
        // Khởi tạo đối tượng Patron với ID hợp lệ đang có sẵn trong hệ thống
        Patron targetPatron = new Patron();
        targetPatron.setPatronID("P001");
        targetPatron.setStatus("Locked");  // Trạng thái mới muốn cập nhật
        targetPatron.setExpiryDate(new Date()); // Ngày gia hạn mới
        // Gọi hàm cần kiểm thử
        boolean isSuccess = patronDAO.updateStatus(targetPatron);
        // Kiểm tra kết quả
        assertTrue(isSuccess, "Hàm phải trả về true khi cập nhật thành công cho một patronID hợp lệ");
    }
    /**
     * Case 2.2: Thẻ đã bị xóa/Không tồn tại -> Cập nhật thất bại.
     */
    @Test
    public void testUpdateStatus_NonExistentPatronID_ReturnsFalse() {
        // Khởi tạo đối tượng Patron với một ID không tồn tại
        Patron nonExistentPatron = new Patron();
        nonExistentPatron.setPatronID("ID_KHONG_TON_TAI_123");
        nonExistentPatron.setStatus("Active");
        nonExistentPatron.setExpiryDate(new Date());
        // Gọi hàm cần kiểm thử
        boolean isSuccess = patronDAO.updateStatus(nonExistentPatron);
        // Kiểm tra kết quả
        assertFalse(isSuccess, "Hàm phải trả về false khi không tìm thấy thẻ cần cập nhật trong CSDL");
    }
}


