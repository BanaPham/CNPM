package view.borrow;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import dao.*;

public class ConfirmLoanFrm extends JFrame implements ActionListener {
    private BorrowBookFrm parentFrame;
    private LoanRecord loanRecord;
    private JTable tblDetails;
    private DefaultTableModel tableModel;
    private JButton btnSave, btnBack;
    private LoanRecordDAO loanRecordDAO;

    public ConfirmLoanFrm(BorrowBookFrm parentFrame, LoanRecord loanRecord) {
        super("Xác nhận thông tin Phiếu mượn");
        this.parentFrame = parentFrame;
        this.loanRecord = loanRecord;
        this.loanRecordDAO = new LoanRecordDAO();

        JPanel pnlInfo = new JPanel(new GridLayout(4, 2));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        pnlInfo.add(new JLabel("Độc giả:"));
        pnlInfo.add(new JLabel(loanRecord.getPatron().getFullName()));
        pnlInfo.add(new JLabel("Thủ thư lập phiếu:"));
        pnlInfo.add(new JLabel(loanRecord.getLibrarian().getFullName()));
        pnlInfo.add(new JLabel("Ngày mượn:"));
        pnlInfo.add(new JLabel(sdf.format(loanRecord.getBorrowDate())));
        pnlInfo.add(new JLabel("Hạn trả:"));
        pnlInfo.add(new JLabel(sdf.format(loanRecord.getDueDate())));

        String[] columnNames = {"STT", "Mã vạch", "Tên sách", "Giá bìa"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblDetails = new JTable(tableModel);

        int index = 1;
        for (LoanDetail detail : loanRecord.getLoanDetails()) {
            Item item = detail.getItem();
            tableModel.addRow(new Object[]{
                index++, item.getBarcode(), item.getBook().getTitle(), item.getBook().getPrice()
            });
        }

        btnSave = new JButton("Hoàn tất lập phiếu");
        btnBack = new JButton("Quay lại chỉnh sửa");

        btnSave.addActionListener(this);
        btnBack.addActionListener(this);

        JPanel pnlButtons = new JPanel();
        pnlButtons.add(btnSave);
        pnlButtons.add(btnBack);

        this.add(pnlInfo, BorderLayout.NORTH);
        this.add(new JScrollPane(tblDetails), BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.SOUTH);

        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            boolean success = loanRecordDAO.addLoanRecord(loanRecord);
            if (success) {
                JOptionPane.showMessageDialog(this, "Lập phiếu mượn sách thành công!");
                new LibrarianHomeFrm(loanRecord.getLibrarian());
                parentFrame.dispose();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu phiếu mượn sách!");
            }
        } else if (e.getSource() == btnBack) {
            this.dispose();
        }
    }
}
