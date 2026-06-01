-- 1. Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS library_borrow;
USE library_borrow;

-- Xóa bảng cũ nếu tồn tại để tránh xung đột cấu trúc (Theo thứ tự khóa ngoại)
DROP TABLE IF EXISTS tblloandetail;
DROP TABLE IF EXISTS tblloanrecord;
DROP TABLE IF EXISTS tblitem;
DROP TABLE IF EXISTS tblbook;
DROP TABLE IF EXISTS tblpatron;
DROP TABLE IF EXISTS tblsystemuser;

-- 2. Tạo bảng tblsystemuser (Thủ thư / Người dùng hệ thống)
CREATE TABLE tblsystemuser (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- 3. Tạo bảng tblpatron (Độc giả)
CREATE TABLE tblpatron (
    id INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(50) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    outstanding_debt DOUBLE DEFAULT 0
);

-- 4. Tạo bảng tblbook (Sách)
CREATE TABLE tblbook (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(50) NULL UNIQUE,
    author VARCHAR(100) NULL,
    price DOUBLE DEFAULT 0
);

-- 5. Tạo bảng tblitem (Bản sao sách)
CREATE TABLE tblitem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    barcode VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL DEFAULT 'Ready',
    price DOUBLE DEFAULT 0,
    book_id INT,
    FOREIGN KEY (book_id) REFERENCES tblbook(id)
);

-- 6. Tạo bảng tblloanrecord (Phiếu mượn)
CREATE TABLE tblloanrecord (
    id INT AUTO_INCREMENT PRIMARY KEY,
    loan_date DATETIME NOT NULL,
    due_date DATETIME NULL,
    patron_id INT,
    librarian_id INT NULL,
    fine_amount DOUBLE DEFAULT 0,
    FOREIGN KEY (librarian_id) REFERENCES tblsystemuser(id),
    FOREIGN KEY (patron_id) REFERENCES tblpatron(id)
);

-- 7. Tạo bảng tblloandetail (Chi tiết phiếu mượn)
CREATE TABLE tblloandetail (
    id INT AUTO_INCREMENT PRIMARY KEY,
    due_date DATETIME NULL,
    item_id INT,
    loan_record_id INT,
    actual_return_date DATETIME NULL,
    fine_amount DOUBLE DEFAULT 0,
    status_notes VARCHAR(255) NULL,
    FOREIGN KEY (loan_record_id) REFERENCES tblloanrecord(id),
    FOREIGN KEY (item_id) REFERENCES tblitem(id)
);

-- =========================================================================
-- CHÈN DỮ LIỆU MẪU ĐỂ TEST (MỖI BẢNG 20 BẢN GHI)
-- =========================================================================

-- 1. Thêm 20 thủ thư (tblsystemuser)
INSERT INTO tblsystemuser (id, username, password, name, role) VALUES
(1, 'thuthu1', '123456', 'Nguyễn Văn Thủ Thư', 'Librarian'),
(2, 'namlh', '123456', 'Lê Hoàng Nam', 'Librarian'),
(3, 'librarian1', '123456', 'Nguyen Van D', 'Librarian'),
(4, 'thutt', '123456', 'Trần Thị Thu', 'Librarian'),
(5, 'duchm', '123456', 'Phạm Minh Đức', 'Librarian'),
(6, 'lamvh', '123456', 'Vũ Hoài Lâm', 'Librarian'),
(7, 'phuongnb', '123456', 'Nguyễn Bích Phương', 'Librarian'),
(8, 'tuanda', '123456', 'Đặng Anh Tuấn', 'Librarian'),
(9, 'dungbt', '123456', 'Bùi Thị Dung', 'Librarian'),
(10, 'datdt', '123456', 'Đỗ Tiến Đạt', 'Librarian'),
(11, 'hunghs', '123456', 'Hồ Sỹ Hùng', 'Librarian'),
(12, 'anhnv', '123456', 'Ngô Việt Anh', 'Librarian'),
(13, 'quynhdt', '123456', 'Dương Thúy Quỳnh', 'Librarian'),
(14, 'tulc', '123456', 'Lý Cẩm Tú', 'Librarian'),
(15, 'binhtg', '123456', 'Trương Gia Bình', 'Librarian'),
(16, 'tripv', '123456', 'Phan Văn Trị', 'Librarian'),
(17, 'sauvt', '123456', 'Võ Thị Sáu', 'Librarian'),
(18, 'linhdb', '123456', 'Đinh Bộ Lĩnh', 'Librarian'),
(19, 'quatcb', '123456', 'Cao Bá Quát', 'Librarian'),
(20, 'daoth', '123456', 'Trần Hưng Đạo', 'Librarian');

-- 2. Thêm 20 độc giả (tblpatron)
INSERT INTO tblpatron (id, card_number, full_name, outstanding_debt) VALUES
(1, 'DG001', 'Trần Văn Độc Giả', 0.0),
(2, 'DG002', 'Lê Thị Nợ Phạt', 60000.0),
(3, 'DG003', 'Nguyễn Văn An', 10000.0),
(4, 'DG004', 'Phạm Thị Bình', 0.0),
(5, 'DG005', 'Hoàng Văn Cường', 25000.0),
(6, 'DG006', 'Vũ Thị Duyên', 75000.0),
(7, 'DG007', 'Đỗ Văn Giang', 0.0),
(8, 'DG008', 'Bùi Thị Hương', 45000.0),
(9, 'DG009', 'Nguyễn Hữu Khánh', 5000.0),
(10, 'DG010', 'Lê Văn Long', 0.0),
(11, 'DG011', 'Phan Thị Mai', 12000.0),
(12, 'DG012', 'Trần Văn Nam', 0.0),
(13, 'DG013', 'Nguyễn Thị Oanh', 52000.0),
(14, 'DG014', 'Phạm Hồng Quân', 0.0),
(15, 'DG015', 'Đặng Thị Sang', 8000.0),
(16, 'DG016', 'Bùi Văn Tuấn', 30000.0),
(17, 'DG017', 'Võ Minh Triết', 0.0),
(18, 'DG018', 'Lương Thế Vinh', 0.0),
(19, 'DG019', 'Nguyễn Trãi', 95000.0),
(20, 'DG020', 'Lê Lợi', 0.0);

-- 3. Thêm 20 sách (tblbook)
INSERT INTO tblbook (id, title, isbn, author, price) VALUES
(1, 'Lập trình Java Swing', 'ISBN001', 'Nguyễn Văn C', 120000.0),
(2, 'Cấu trúc dữ liệu và giải thuật', 'ISBN002', 'Trần Đăng Khoa', 95000.0),
(3, 'Cơ sở dữ liệu nâng cao', 'ISBN003', 'Lê Hoài Nam', 110000.0),
(4, 'Lập trình Web với React và Node', 'ISBN004', 'Phạm Minh Tuấn', 150000.0),
(5, 'Nhập môn Trí tuệ nhân tạo', 'ISBN005', 'Vũ Hải Đăng', 180000.0),
(6, 'Mạng máy tính căn bản', 'ISBN006', 'Đỗ Việt Hùng', 85000.0),
(7, 'Hệ điều hành hiện đại', 'ISBN007', 'Andrew S. Tanenbaum', 130000.0),
(8, 'Kỹ nghệ phần mềm', 'ISBN008', 'Ian Sommerville', 145000.0),
(9, 'Thiết kế mẫu (Design Patterns)', 'ISBN009', 'Erich Gamma', 200000.0),
(10, 'Lập trình hướng đối tượng với C++', 'ISBN010', 'Bjarne Stroustrup', 90000.0),
(11, 'Phân tích thiết kế hệ thống', 'ISBN011', 'Nguyễn Văn Ba', 105000.0),
(12, 'An toàn thông tin', 'ISBN012', 'Trần Văn Ngọc', 125000.0),
(13, 'Điện toán đám mây', 'ISBN013', 'Lê Văn Sơn', 160000.0),
(14, 'Phát triển ứng dụng di động Flutter', 'ISBN014', 'Nguyễn Hoàng Anh', 135000.0),
(15, 'Lý thuyết đồ thị', 'ISBN015', 'Trần Quốc Đạt', 98000.0),
(16, 'Big Data và Hadoop', 'ISBN016', 'Phạm Thế Duy', 220000.0),
(17, 'Kiến trúc máy tính', 'ISBN017', 'David Patterson', 175000.0),
(18, 'Học máy thực hành (Machine Learning)', 'ISBN018', 'Nguyễn Nhật Quang', 190000.0),
(19, 'Tối ưu hóa thuật toán', 'ISBN019', 'Phan Thanh Bình', 115000.0),
(20, 'Quản trị dự án phần mềm', 'ISBN020', 'Đặng Văn Sơn', 100000.0);

-- 4. Thêm 20 bản sao sách (tblitem)
INSERT INTO tblitem (id, barcode, status, price, book_id) VALUES
(1, 'BC001', 'Ready', 120000.0, 1),
(2, 'BC002', 'Ready', 120000.0, 1),
(3, 'BC003', 'Ready', 95000.0, 2),
(4, 'BC004', 'Ready', 110000.0, 3),
(5, 'BC005', 'Ready', 150000.0, 4),
(6, 'BC006', 'Ready', 180000.0, 5),
(7, 'BC007', 'Ready', 85000.0, 6),
(8, 'BC008', 'Borrowed', 130000.0, 7),
(9, 'BC009', 'Ready', 145000.0, 8),
(10, 'BC010', 'Ready', 200000.0, 9),
(11, 'BC011', 'Borrowed', 90000.0, 10),
(12, 'BC012', 'Ready', 105000.0, 11),
(13, 'BC013', 'Ready', 125000.0, 12),
(14, 'BC014', 'Ready', 160000.0, 13),
(15, 'BC015', 'Ready', 135000.0, 14),
(16, 'BC016', 'Ready', 98000.0, 15),
(17, 'BC017', 'Ready', 220000.0, 16),
(18, 'BC018', 'Ready', 175000.0, 17),
(19, 'BC019', 'Ready', 190000.0, 18),
(20, 'BC020', 'Ready', 115000.0, 19);

-- 5. Thêm 20 phiếu mượn (tblloanrecord)
INSERT INTO tblloanrecord (id, loan_date, due_date, patron_id, librarian_id, fine_amount) VALUES
(1, '2026-05-01 09:00:00', '2026-05-31 09:00:00', 1, 1, 0.0),
(2, '2026-05-02 10:15:00', '2026-06-01 10:15:00', 3, 1, 0.0),
(3, '2026-05-03 14:30:00', '2026-06-02 14:30:00', 4, 2, 0.0),
(4, '2026-05-04 08:45:00', '2026-06-03 08:45:00', 5, 2, 0.0),
(5, '2026-05-05 11:00:00', '2026-06-04 11:00:00', 7, 3, 0.0),
(6, '2026-05-06 16:20:00', '2026-06-05 16:20:00', 8, 4, 0.0),
(7, '2026-05-07 09:30:00', '2026-06-06 09:30:00', 9, 5, 0.0),
(8, '2026-05-08 13:10:00', '2026-06-07 13:10:00', 10, 6, 0.0),
(9, '2026-05-09 15:40:00', '2026-06-08 15:40:00', 11, 7, 0.0),
(10, '2026-05-10 10:00:00', '2026-06-09 10:00:00', 12, 8, 0.0),
(11, '2026-05-11 14:15:00', '2026-06-10 14:15:00', 14, 9, 0.0),
(12, '2026-05-12 08:30:00', '2026-06-11 08:30:00', 15, 10, 0.0),
(13, '2026-05-13 11:45:00', '2026-06-12 11:45:00', 16, 11, 0.0),
(14, '2026-05-14 16:00:00', '2026-06-13 16:00:00', 17, 12, 0.0),
(15, '2026-05-15 09:15:00', '2026-06-14 09:15:00', 18, 13, 0.0),
(16, '2026-05-16 13:50:00', '2026-06-15 13:50:00', 20, 14, 0.0),
(17, '2026-05-17 15:00:00', '2026-06-16 15:00:00', 1, 15, 0.0),
(18, '2026-05-18 10:30:00', '2026-06-17 10:30:00', 3, 16, 0.0),
(19, '2026-05-19 14:20:00', '2026-06-18 14:20:00', 4, 17, 0.0),
(20, '2026-05-20 08:50:00', '2026-06-19 08:50:00', 5, 18, 0.0);

-- 6. Thêm 20 chi tiết phiếu mượn (tblloandetail)
-- Chỉ có phiếu mượn 8 (sách BC008) và phiếu mượn 11 (sách BC011) đang ở trạng thái chưa trả (actual_return_date IS NULL)
-- Các bản ghi khác được thiết lập đã trả trước đó để dữ liệu được nhất quán với trạng thái 'Ready' của sách
INSERT INTO tblloandetail (id, due_date, item_id, loan_record_id, actual_return_date, fine_amount, status_notes) VALUES
(1, '2026-05-31 09:00:00', 1, 1, '2026-05-28 09:00:00', 0.0, 'Đã trả đúng hạn'),
(2, '2026-06-01 10:15:00', 2, 2, '2026-05-29 10:15:00', 0.0, 'Đã trả đúng hạn'),
(3, '2026-06-02 14:30:00', 3, 3, '2026-05-30 14:30:00', 0.0, 'Đã trả đúng hạn'),
(4, '2026-06-03 08:45:00', 4, 4, '2026-05-31 08:45:00', 0.0, 'Đã trả đúng hạn'),
(5, '2026-06-04 11:00:00', 5, 5, '2026-06-01 11:00:00', 0.0, 'Đã trả đúng hạn'),
(6, '2026-06-05 16:20:00', 6, 6, '2026-06-02 16:20:00', 0.0, 'Đã trả đúng hạn'),
(7, '2026-06-06 09:30:00', 7, 7, '2026-06-03 09:30:00', 0.0, 'Đã trả đúng hạn'),
(8, '2026-06-07 13:10:00', 8, 8, NULL, 0.0, 'Chưa trả sách'),
(9, '2026-06-08 15:40:00', 9, 9, '2026-06-05 15:40:00', 0.0, 'Đã trả đúng hạn'),
(10, '2026-06-09 10:00:00', 10, 10, '2026-06-06 10:00:00', 0.0, 'Đã trả đúng hạn'),
(11, '2026-06-10 14:15:00', 11, 11, NULL, 0.0, 'Chưa trả sách'),
(12, '2026-06-11 08:30:00', 12, 12, '2026-06-08 08:30:00', 0.0, 'Đã trả đúng hạn'),
(13, '2026-06-12 11:45:00', 13, 13, '2026-06-09 11:45:00', 0.0, 'Đã trả đúng hạn'),
(14, '2026-06-13 16:00:00', 14, 14, '2026-06-10 16:00:00', 0.0, 'Đã trả đúng hạn'),
(15, '2026-06-14 09:15:00', 15, 15, '2026-06-11 09:15:00', 0.0, 'Đã trả đúng hạn'),
(16, '2026-06-15 13:50:00', 16, 16, '2026-06-12 13:50:00', 0.0, 'Đã trả đúng hạn'),
(17, '2026-06-16 15:00:00', 17, 17, '2026-06-13 15:00:00', 0.0, 'Đã trả đúng hạn'),
(18, '2026-06-17 10:30:00', 18, 18, '2026-06-14 10:30:00', 0.0, 'Đã trả đúng hạn'),
(19, '2026-06-18 14:20:00', 19, 19, '2026-06-15 14:20:00', 0.0, 'Đã trả đúng hạn'),
(20, '2026-06-19 08:50:00', 20, 20, '2026-06-16 08:50:00', 0.0, 'Đã trả đúng hạn');
