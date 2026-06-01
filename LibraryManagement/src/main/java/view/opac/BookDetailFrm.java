package view.opac;

import java.awt.BorderLayout;
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
import model.Patron;

public class BookDetailFrm extends JFrame implements ActionListener {

    private Book book;
    private Patron patron;
    private JButton btnBack;
    private JTable tblItem;
    private DefaultTableModel tableModel;

    public BookDetailFrm(Book b) {
        this(b, null);
    }

    public BookDetailFrm(Book b, Patron p) {
        super("Chi tiết sách và Tình trạng mượn");
        this.book = b;
        this.patron = p;

        // 1. Panel thông tin sách
        JPanel pnInfo = new JPanel();
        pnInfo.setLayout(new GridLayout(4, 2, 10, 10));
        pnInfo.setBorder(BorderFactory.createTitledBorder("Thông tin đầu sách"));

        pnInfo.add(new JLabel("Mã sách: " + book.getBookID()));
        pnInfo.add(new JLabel("Tựa sách: " + book.getTitle()));
        pnInfo.add(new JLabel("Tác giả: " + book.getAuthor()));
        pnInfo.add(new JLabel("Nhà XB: " + book.getPublisher()));
        pnInfo.add(new JLabel("Năm XB: " + book.getPublishYear()));
        pnInfo.add(new JLabel("Mã DDC: " + book.getDdcCode()));
        pnInfo.add(new JLabel("Giá: " + book.getPrice() + " VNĐ"));

        // 2. Bảng bản sao
        String[] columnNames = {"Mã Bản Sao", "Mã Vạch (Barcode)", "Trạng Thái", "Vị Trí Kệ (Phòng - Dãy)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblItem = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tblItem);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách các bản sao hiện có"));

        ItemDAO itemDAO = new ItemDAO();
        System.out.println("BookDetailFrm: Loading items for bookID: " + book.getBookID());
        ArrayList<Item> listItem = itemDAO.checkAvailability(book.getBookID());

        for (Item item : listItem) {
            String viTri = "Chưa rõ";
            if (item.getShelf() != null) {
                viTri = item.getShelf().getRoom() + " - " + item.getShelf().getRow();
            }
            tableModel.addRow(new Object[]{
                    item.getItemID(),
                    item.getBarcode(),
                    item.getStatus(),
                    viTri
            });
        }

        // 3. Nút quay lại
        JPanel pnBottom = new JPanel();
        btnBack = new JButton("Quay lại");
        btnBack.addActionListener(this);
        pnBottom.add(btnBack);

        this.setLayout(new BorderLayout(10, 10));
        this.add(pnInfo, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(pnBottom, BorderLayout.SOUTH);

        this.setSize(750, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            new OpacHomeFrm(patron).setVisible(true);
            this.dispose();
        }
    }
}
