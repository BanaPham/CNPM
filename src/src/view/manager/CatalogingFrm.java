package view.manager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.BookDAO;
import dao.ItemDAO;
import model.Book;
import model.Item;

public class CatalogingFrm extends JFrame implements ActionListener {

    private JTextField txtTitle, txtAuthor, txtIsbn, txtPublisher, txtPublishYear, txtDdc, txtPrice, txtQuantity;
    private JComboBox<String> cbxShelf;
    private JButton btnSave, btnBack;

    public CatalogingFrm() {
        super("Biên mục tài liệu mới");

        // --- 1. Tạo Panel Nhập liệu ---
        JPanel pnInput = new JPanel(new GridLayout(9, 2, 10, 10));
        pnInput.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        pnInput.add(new JLabel("Tiêu đề sách (*):"));
        txtTitle = new JTextField(); pnInput.add(txtTitle);

        pnInput.add(new JLabel("Tác giả (*):"));
        txtAuthor = new JTextField(); pnInput.add(txtAuthor);

        pnInput.add(new JLabel("Mã ISBN (*):"));
        txtIsbn = new JTextField(); pnInput.add(txtIsbn);

        pnInput.add(new JLabel("Nhà xuất bản:"));
        txtPublisher = new JTextField(); pnInput.add(txtPublisher);

        pnInput.add(new JLabel("Năm xuất bản:"));
        txtPublishYear = new JTextField(); pnInput.add(txtPublishYear);

        pnInput.add(new JLabel("Chuẩn DDC:"));
        txtDdc = new JTextField(); pnInput.add(txtDdc);

        pnInput.add(new JLabel("Giá tiền (VNĐ):"));
        txtPrice = new JTextField(); pnInput.add(txtPrice);

        pnInput.add(new JLabel("Vị trí Kệ lưu trữ (*):"));
        // Trong thực tế sẽ lấy danh sách Kệ từ DB, ở đây giả lập ID Kệ gắn kèm mô tả
        String[] shelfList = {"SH001 - Kệ A1", "SH002 - Kệ B2", "SH003 - Kệ C1"};
        cbxShelf = new JComboBox<>(shelfList);
        pnInput.add(cbxShelf);

        pnInput.add(new JLabel("Số lượng bản sao (*):"));
        txtQuantity = new JTextField("1"); pnInput.add(txtQuantity);

        // --- 2. Tạo Panel Nút bấm ---
        JPanel pnButtons = new JPanel();
        btnSave = new JButton("Lưu & Sinh Mã Vạch");
        btnSave.addActionListener(this);
        btnBack = new JButton("Quay lại");
        btnBack.addActionListener(this);

        pnButtons.add(btnSave);
        pnButtons.add(btnBack);

        // --- 3. Đưa vào Frame ---
        this.setLayout(new BorderLayout());
        this.add(pnInput, BorderLayout.CENTER);
        this.add(pnButtons, BorderLayout.SOUTH);

        this.setSize(550, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            new ManagerHomeFrm().setVisible(true);
            this.dispose();
        }
        else if (e.getSource() == btnSave) {
            try {
                // Bước 1: Validate dữ liệu
                if(txtTitle.getText().isEmpty() || txtAuthor.getText().isEmpty() || txtIsbn.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ các trường bắt buộc (*)");
                    return;
                }
                int quantity = Integer.parseInt(txtQuantity.getText());
                if(quantity <= 0) throw new NumberFormatException();

                // Bước 2: Đóng gói dữ liệu vào Model Book
                Book b = new Book();
                b.setTitle(txtTitle.getText().trim());
                b.setAuthor(txtAuthor.getText().trim());
                b.setIsbn(txtIsbn.getText().trim());
                b.setPublisher(txtPublisher.getText().trim());
                b.setPublishYear(txtPublishYear.getText().isEmpty() ? 0 : Integer.parseInt(txtPublishYear.getText().trim()));
                b.setDdcCode(txtDdc.getText().trim());
                b.setPrice(txtPrice.getText().isEmpty() ? 0 : Double.parseDouble(txtPrice.getText().trim()));

                // Bước 3: Gọi DAO lưu thông tin Sách
                BookDAO bookDAO = new BookDAO();
                String bookID = bookDAO.addBook(b);

                if (bookID != null) {
                    // Lấy mã Kệ (Cắt 5 ký tự đầu tiên, VD: "SH001")
                    String selectedShelfID = cbxShelf.getSelectedItem().toString().substring(0, 5);

                    // Bước 4: Gọi DAO tạo các Bản sao vật lý
                    ItemDAO itemDAO = new ItemDAO();
                    ArrayList<Item> listGeneratedItems = itemDAO.createItems(bookID, selectedShelfID, quantity);

                    // Bước 5: Cập nhật View, hiển thị danh sách mã vạch
                    StringBuilder msg = new StringBuilder("Thêm mới thành công!\nDanh sách mã vạch đã tự động sinh:\n");
                    for (Item item : listGeneratedItems) {
                        msg.append("- ").append(item.getBarcode()).append("\n");
                    }

                    JTextArea textArea = new JTextArea(msg.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new java.awt.Dimension(300, 150));

                    JOptionPane.showMessageDialog(this, scrollPane, "Hoàn tất Biên mục", JOptionPane.INFORMATION_MESSAGE);

                    // Xóa trắng form để nhập cuốn tiếp theo
                    txtTitle.setText(""); txtAuthor.setText(""); txtIsbn.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi kết nối hoặc lưu CSDL thất bại!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng, Năm XB và Giá tiền phải là số hợp lệ!");
            }
        }
    }
}
