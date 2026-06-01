import model.SystemUser;
import view.borrow.LibrarianHomeFrm;

public class Main {
    public static void main(String[] args) {
        // Tạo tài khoản thủ thư mẫu (đã insert ở database)
        SystemUser librarian = new SystemUser();
        librarian.setId(1);
        librarian.setFullName("Nguyễn Văn Thủ Thư");

        // Khởi chạy màn hình chính của thủ thư
        new LibrarianHomeFrm(librarian);
    }
}
