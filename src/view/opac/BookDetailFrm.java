package view.opac;

import java.awt.BorderLayout;
// import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.ItemDAO;
import model.Book;
import model.Item;

public class BookDetailFrm extends JFrame implements ActionListener {
    
    private Book book;
    private JButton btnBack;
    private JTable tblItem;
    private DefaultTableModel tableModel;

    // Khởi tạo nhận vào 1 đối tượng Book từ trang chủ truyền sang
    public BookDetailFrm(Book b) {
        super("Chi tiết sách và Tình trạng mượn");
        this.book = b;
        
        // --- 1. Panel phía trên: Hiển thị thông tin sách ---
        JPanel pnInfo = new JPanel();
        pnInfo.setLayout(new GridLayout(4, 2, 10, 10)); // Lưới 4 hàng 2 cột
        pnInfo.setBorder(BorderFactory.createTitledBorder("Thông tin đầu sách"));
        
        pnInfo.add(new JLabel("Mã sách: " + book.getBookID()));
        pnInfo.add(new JLabel("Tựa sách: " + book.getTitle()));
        pnInfo.add(new JLabel("Tác giả: " + book.getAuthor()));
        pnInfo.add(new JLabel("Nhà XB: " + book.getPublisher()));
        pnInfo.add(new JLabel("Năm XB: " + book.getPublishYear()));
        pnInfo.add(new JLabel("Mã DDC: " + book.getDdcCode()));
        pnInfo.add(new JLabel("Giá: " + book.getPrice() + " VNĐ"));
        
        // --- 2. Panel ở giữa: Bảng danh sách bản sao (Item) và vị trí Kệ ---
        String[] columnNames = {"Mã Bản Sao", "Mã Vạch (Barcode)", "Trạng Thái", "Vị Trí Kệ (Phòng - Dãy)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblItem = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(tblItem);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách các bản sao hiện có"));
        
        // --- GỌI DAO ĐỂ ĐỔ DỮ LIỆU TỰ ĐỘNG ---
        // Đúng như kịch bản động: Form mở lên là tự động gọi ItemDAO
        ItemDAO itemDAO = new ItemDAO();
        ArrayList<Item> listItem = itemDAO.checkAvailability(book.getBookID());
        
        for (Item item : listItem) {
            String viTri = item.getShelf().getRoom() + " - " + item.getShelf().getRow();
            tableModel.addRow(new Object[]{
                item.getItemID(),
                item.getBarcode(),
                item.getStatus(),
                viTri
            });
        }
        
        // --- 3. Panel phía dưới: Nút Quay lại ---
        JPanel pnBottom = new JPanel();
        btnBack = new JButton("Quay lại");
        btnBack.addActionListener(this);
        pnBottom.add(btnBack);
        
        // --- 4. Gom tất cả vào JFrame ---
        this.setLayout(new BorderLayout(10, 10));
        this.add(pnInfo, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(pnBottom, BorderLayout.SOUTH);
        
        this.setSize(750, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            // Tắt trang chi tiết và mở lại trang chủ
            new OpacHomeFrm().setVisible(true);
            this.dispose();
        }
    }
}