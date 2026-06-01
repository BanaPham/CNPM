package view.borrow;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import dao.*;

public class BorrowBookFrm extends JFrame implements ActionListener {
    private JTextField txtCardNumber, txtBarcode;
    private JButton btnSearchPatron, btnAddItem, btnConfirm, btnCancel;
    private JLabel lblPatronInfo;
    private JTable tblItems;
    private DefaultTableModel tableModel;

    private SystemUser librarian;
    private Patron patron;
    private LoanRecord loanRecord;
    private PatronDAO patronDAO;
    private ItemDAO itemDAO;

    public BorrowBookFrm(SystemUser librarian) {
        super("Giao diện Mượn sách");
        this.librarian = librarian;
        this.patronDAO = new PatronDAO();
        this.itemDAO = new ItemDAO();

        this.loanRecord = new LoanRecord();
        this.loanRecord.setLibrarian(librarian);
        this.loanRecord.setBorrowDate(new Date());

        Calendar cal = Calendar.getInstance();
        cal.setTime(this.loanRecord.getBorrowDate());
        cal.add(Calendar.DAY_OF_YEAR, 30);
        this.loanRecord.setDueDate(cal.getTime());

        txtCardNumber = new JTextField(15);
        txtBarcode = new JTextField(15);
        btnSearchPatron = new JButton("Tìm độc giả");
        btnAddItem = new JButton("Thêm sách");
        btnConfirm = new JButton("Xác nhận mượn");
        btnCancel = new JButton("Hủy bỏ");
        lblPatronInfo = new JLabel("Thông tin độc giả: Chưa nhập");

        btnSearchPatron.addActionListener(this);
        btnAddItem.addActionListener(this);
        btnConfirm.addActionListener(this);
        btnCancel.addActionListener(this);

        JPanel pnlNorth = new JPanel(new GridLayout(2, 3));
        pnlNorth.add(new JLabel("Mã thẻ độc giả:"));
        pnlNorth.add(txtCardNumber);
        pnlNorth.add(btnSearchPatron);
        pnlNorth.add(new JLabel("Mã vạch sách:"));
        pnlNorth.add(txtBarcode);
        pnlNorth.add(btnAddItem);

        String[] columnNames = {"STT", "Mã vạch", "Tên sách", "Tác giả", "Giá bìa"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblItems = new JTable(tableModel);

        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnConfirm);
        pnlSouth.add(btnCancel);

        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(new JScrollPane(tblItems), BorderLayout.CENTER);
        this.add(pnlSouth, BorderLayout.SOUTH);

        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearchPatron) {
            String cardNum = txtCardNumber.getText().trim();
            patron = patronDAO.searchPatron(cardNum);
            if (patron != null) {
                if (patron.getOutstandingDebt() > 50000) {
                    JOptionPane.showMessageDialog(this, "Độc giả có nợ phạt vượt quá 50,000 VND. Không thể mượn!");
                    patron = null;
                } else {
                    lblPatronInfo.setText("Độc giả: " + patron.getFullName() + " | Nợ phạt: " + patron.getOutstandingDebt() + " VND");
                    loanRecord.setPatron(patron);
                    JOptionPane.showMessageDialog(this, "Tìm thấy độc giả: " + patron.getFullName());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy độc giả!");
            }
        } else if (e.getSource() == btnAddItem) {
            if (patron == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn độc giả trước!");
                return;
            }
            String barcode = txtBarcode.getText().trim();
            Item item = itemDAO.searchItem(barcode);
            if (item != null) {
                if (!"Ready".equalsIgnoreCase(item.getStatus())) {
                    JOptionPane.showMessageDialog(this, "Bản sao sách không sẵn sàng để mượn!");
                } else {
                    boolean exists = false;
                    for (LoanDetail d : loanRecord.getLoanDetails()) {
                        if (d.getItem().getBarcode().equals(barcode)) {
                            exists = true;
                            break;
                        }
                    }
                    if (exists) {
                        JOptionPane.showMessageDialog(this, "Sách đã có trong danh sách!");
                    } else {
                        LoanDetail detail = new LoanDetail();
                        detail.setItem(item);
                        detail.setFineAmount(0);
                        detail.setStatusNotes("Mượn mới");
                        loanRecord.addLoanDetail(detail);

                        int index = tableModel.getRowCount() + 1;
                        tableModel.addRow(new Object[]{
                            index, item.getBarcode(), item.getBook().getTitle(), item.getBook().getAuthor(), item.getBook().getPrice()
                        });
                        txtBarcode.setText("");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy bản sao sách!");
            }
        } else if (e.getSource() == btnConfirm) {
            if (patron == null || loanRecord.getLoanDetails().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Thông tin mượn sách chưa hợp lệ!");
                return;
            }
            new ConfirmLoanFrm(this, loanRecord);
        } else if (e.getSource() == btnCancel) {
            new LibrarianHomeFrm(librarian);
            this.dispose();
        }
    }
}
