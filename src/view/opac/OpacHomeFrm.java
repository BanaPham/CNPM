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
import model.Patron;
import view.patron.UserHomeFrm;

public class OpacHomeFrm extends JFrame implements ActionListener {

    private JTextField txtKeyword;
    private JButton btnSearch, btnBack;
    private JTable tblBook;
    private DefaultTableModel tableModel;
    private ArrayList<Book> listBook;
    private Patron patron;

    public OpacHomeFrm() {
        this(null);
    }

    public OpacHomeFrm(Patron p) {
        super("Hệ thống Quản lý Thư viện - OPAC");
        this.patron = p;
        this.listBook = new ArrayList<>();

        // 1. Panel phía trên (chứa ô tìm kiếm và nút)
        JPanel pnTop = new JPanel();
        pnTop.setLayout(new FlowLayout());

        if (patron != null) {
            btnBack = new JButton("Quay lại");
            btnBack.addActionListener(this);
            pnTop.add(btnBack);
        }

        pnTop.add(new JLabel("Từ khóa (Tên sách/Tác giả): "));
        txtKeyword = new JTextField(20);
        pnTop.add(txtKeyword);

        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(this);
        pnTop.add(btnSearch);

        // --- 2. Panel ở giữa (chứa bảng JTable) ---
        String[] columnNames = {"Mã Sách", "Tựa Sách", "Tác Giả", "NXB", "Năm XB", "Giá"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tblBook = new JTable(tableModel);

        tblBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) { 
                    int row = tblBook.getSelectedRow();
                    if (row != -1 && row < listBook.size()) {
                        Book selectedBook = listBook.get(row);
                        new BookDetailFrm(selectedBook, patron).setVisible(true);
                        dispose(); 
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblBook);
        scrollPane.setPreferredSize(new Dimension(700, 300));

        this.setLayout(new BorderLayout());
        this.add(pnTop, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(750, 400);
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            new UserHomeFrm(patron).setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnSearch) {
            search();
        }
    }

    private void search() {
        String keyword = txtKeyword.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa để tìm kiếm!");
            return;
        }

        BookDAO bookDAO = new BookDAO();
        listBook = bookDAO.searchBook(keyword);

        tableModel.setRowCount(0);

        if (listBook.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sách nào với từ khóa này.");
        } else {
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
    }
}
