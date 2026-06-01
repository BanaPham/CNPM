package view.opac;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.BookDAO;
import model.Book;

// Kế thừa JFrame và implements ActionListener (Y hệt sơ đồ lớp thiết kế MVC)
public class OpacHomeFrm extends JFrame implements ActionListener {
    
    private JTextField txtKeyword;
    private JButton btnSearch;
    private JButton btnRegister;
    private JTable tblBook;
    private DefaultTableModel tableModel;
    private ArrayList<Book> listBook; // Danh sách lưu tạm để truyền sang trang chi tiết

    public OpacHomeFrm() {
        super("Hệ thống Quản lý Thư viện - OPAC");
        
        // --- TẠO GIAO DIỆN (UI) ---
        
        // 1. Panel phía trên (chứa ô tìm kiếm và nút)
        JPanel pnTop = new JPanel();
        pnTop.setLayout(new FlowLayout());
        
        pnTop.add(new JLabel("Từ khóa (Tên sách/Tác giả): "));
        txtKeyword = new JTextField(20);
        pnTop.add(txtKeyword);
        
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(this);
        pnTop.add(btnSearch);
        
        // --- THÊM NÚT ĐĂNG KÝ VÀO ĐÂY ---
        btnRegister = new JButton("Đăng ký thẻ");
        btnRegister.addActionListener(this);
        pnTop.add(btnRegister);
        
        // --- 2. Panel ở giữa (chứa bảng JTable) ---
        String[] columnNames = {"Mã Sách", "Tựa Sách", "Tác Giả", "NXB", "Năm XB", "Giá"};
        
        // Ghi đè phương thức isCellEditable để KHÓA chỉnh sửa ô
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Trả về false cho tất cả các ô
            }
        };
        
        tblBook = new JTable(tableModel);
        
        // Xử lý sự kiện Double-click vào một dòng trong bảng (Để xem chi tiết)
        tblBook.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) { // Nếu nhấp đúp
                    JTable target = (JTable) me.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        Book selectedBook = listBook.get(row);
                        // Mở trang chi tiết và truyền đối tượng Book sang
                        new BookDetailFrm(selectedBook).setVisible(true);
                        dispose(); // Tắt trang hiện tại đi
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tblBook);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        
        // 3. Đưa tất cả vào JFrame
        this.setLayout(new BorderLayout());
        this.add(pnTop, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        
        // 4. Thiết lập chung cho Form
        this.setSize(750, 400);
        this.setLocationRelativeTo(null); // Hiển thị ra giữa màn hình
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // --- XỬ LÝ SỰ KIỆN CLICK (actionPerformed) ---
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            String keyword = txtKeyword.getText().trim();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa để tìm kiếm!");
                return;
            }
            
            // Gọi tầng DAO (Chính xác kịch bản thiết kế động)
            BookDAO bookDAO = new BookDAO();
            listBook = bookDAO.searchBook(keyword);
            
            // Xóa dữ liệu cũ trên bảng
            tableModel.setRowCount(0);
            
            // Đổ dữ liệu mới lên bảng
            for (Book b : listBook) {
                tableModel.addRow(new Object[]{
                    b.getBookID(), 
                    b.getTitle(), 
                    b.getAuthor(), 
                    b.getPublisher(), 
                    b.getPublishYear(), 
                    b.getPrice()
                });
            }
        }
        else if (e.getSource() == btnRegister) {
            // Nhảy sang luồng Đăng ký thẻ và đóng trang OPAC
            new view.register.RegisterMethodFrm().setVisible(true);
            this.dispose();
        }
    }

    // --- HÀM MAIN ĐỂ CHẠY CHƯƠNG TRÌNH ---
    public static void main(String[] args) {
        new OpacHomeFrm().setVisible(true);
    }
}