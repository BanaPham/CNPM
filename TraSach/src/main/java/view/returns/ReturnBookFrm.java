package view.returns;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import dao.*;

public class ReturnBookFrm extends JFrame implements ActionListener {
    private LoanRecordDAO loanRecordDAO;
    private ItemDAO itemDAO;
    private JTextField txtBarcode;
    private JButton btnConfirm, btnCancel;
    private JTable tblInfo;
    private DefaultTableModel tableModel;
    private SystemUser librarian;
    private Item currentItem;
    private LoanDetail currentLoanDetail;

    public ReturnBookFrm(SystemUser librarian) {
        super("Giao diện Trả sách");
        this.librarian = librarian;
        this.loanRecordDAO = new LoanRecordDAO();
        this.itemDAO = new ItemDAO();

        txtBarcode = new JTextField(20);
        btnConfirm = new JButton("Xác nhận trả");
        btnCancel = new JButton("Hủy bỏ");

        txtBarcode.addActionListener(this);
        btnConfirm.addActionListener(this);
        btnCancel.addActionListener(this);

        JPanel pnlNorth = new JPanel();
        pnlNorth.add(new JLabel("Nhập mã vạch sách:"));
        pnlNorth.add(txtBarcode);

        String[] columnNames = {"Mục thông tin", "Chi tiết"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblInfo = new JTable(tableModel);

        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnConfirm);
        pnlSouth.add(btnCancel);

        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(new JScrollPane(tblInfo), BorderLayout.CENTER);
        this.add(pnlSouth, BorderLayout.SOUTH);

        this.setSize(500, 350);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        btnConfirm.setEnabled(false);
    }

    public void txtBarcodeActionPerformed(ActionEvent e) {
        String barcode = txtBarcode.getText().trim();
        if (barcode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã vạch!");
            return;
        }

        currentItem = itemDAO.searchItemByBarcode(barcode);
        if (currentItem == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy bản sao sách!");
            return;
        }

        currentLoanDetail = loanRecordDAO.getActiveLoanDetail(currentItem);
        if (currentLoanDetail == null) {
            JOptionPane.showMessageDialog(this, "Sách này hiện không ở trạng thái được mượn!");
            return;
        }

        // Hiển thị thông tin lên bảng
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        LoanRecord record = currentLoanDetail.getLoanRecord();
        tableModel.addRow(new Object[]{"Tên sách", currentItem.getBook().getTitle()});
        tableModel.addRow(new Object[]{"Mã vạch", currentItem.getBarcode()});
        tableModel.addRow(new Object[]{"Độc giả mượn", record.getPatron().getFullName()});
        tableModel.addRow(new Object[]{"Ngày mượn", sdf.format(record.getBorrowDate())});
        tableModel.addRow(new Object[]{"Hạn trả", sdf.format(record.getDueDate())});

        // Tính tiền phạt trễ hạn
        double fine = record.calculateFine(currentItem.getBook().getCoverPrice());
        displayFine(fine);
        btnConfirm.setEnabled(true);
    }

    public void displayFine(double fine) {
        tableModel.addRow(new Object[]{"Phí phạt trễ hạn (20% giá bìa)", fine > 0 ? fine + " VND (Trễ hạn)" : "0 VND (Đúng hạn)"});
        if (fine > 0) {
            JOptionPane.showMessageDialog(this, "Sách trả trễ hạn! Phí phạt áp dụng: " + fine + " VND.");
        }
    }

    public void btnConfirmActionPerformed(ActionEvent e) {
        if (currentLoanDetail == null || currentItem == null) {
            JOptionPane.showMessageDialog(this, "Không có thông tin để xác nhận!");
            return;
        }

        // Cập nhật ngày trả thực tế
        currentLoanDetail.setReturnDate(new Date());
        boolean resReturn = loanRecordDAO.returnLoanDetail(currentLoanDetail);

        if (!resReturn) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật ngày trả sách!");
            return;
        }

        // Nếu có tiền phạt thì lưu tiền phạt
        double fine = currentLoanDetail.getLoanRecord().calculateFine(currentItem.getBook().getCoverPrice());
        if (fine > 0) {
            boolean resFine = loanRecordDAO.updateFine(currentLoanDetail.getLoanRecord(), fine);
            if (!resFine) {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật tiền phạt!");
                return;
            }
        }

        // Cập nhật trạng thái sách thành "Ready"
        boolean resStatus = itemDAO.updateItemStatus(currentItem, "Ready");
        if (resStatus) {
            showSuccessMessage();
            new LibrarianHomeFrm(librarian);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật trạng thái sách!");
        }
    }

    public void showSuccessMessage() {
        JOptionPane.showMessageDialog(this, "Ghi nhận trả sách thành công!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == txtBarcode) {
            txtBarcodeActionPerformed(e);
        } else if (e.getSource() == btnConfirm) {
            btnConfirmActionPerformed(e);
        } else if (e.getSource() == btnCancel) {
            new LibrarianHomeFrm(librarian);
            this.dispose();
        }
    }
}
