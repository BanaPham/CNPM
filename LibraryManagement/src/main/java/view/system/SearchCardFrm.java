package view.system;
import dao.PatronDAO;
import model.Patron;
import model.SystemUser;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchCardFrm extends JFrame implements ActionListener {
    private JTextField txtKey;
    private JButton btnSearch;
    private JButton btnSelect;
    private JButton btnBack;
    private Patron[] results = new Patron[0];
    private JTable tblResult;
    private SystemUser currentUser;

    public SearchCardFrm() {
        this(null);
    }

    public SearchCardFrm(SystemUser user) {
        this.currentUser = user;
        setTitle("Tìm kiếm thẻ người dùng");
        setSize(500, 410);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblKey = new JLabel("Nhập từ khóa:");
        lblKey.setBounds(30, 30, 100, 30);
        add(lblKey);

        txtKey = new JTextField();
        txtKey.setBounds(140, 30, 200, 30);
        add(txtKey);

        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBounds(350, 30, 100, 30);
        btnSearch.addActionListener(this);
        add(btnSearch);

        tblResult = new JTable(createTableModel());
        JScrollPane scrollPane = new JScrollPane(tblResult);
        scrollPane.setBounds(30, 80, 420, 220);
        add(scrollPane);

        btnBack = new JButton("Quay lại");
        btnBack.setBounds(110, 320, 120, 30);
        btnBack.addActionListener(this);
        add(btnBack);

        btnSelect = new JButton("Chọn thẻ");
        btnSelect.setBounds(250, 320, 120, 30);
        btnSelect.addActionListener(this);
        add(btnSelect);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            searchCard();
        } else if (e.getSource() == btnSelect) {
            openSelectedCard();
        } else if (e.getSource() == btnBack) {
            this.dispose();
            if (currentUser != null) {
                new view.borrow.LibrarianHomeFrm(currentUser).setVisible(true);
            } else {
                new view.user.LoginFrm().setVisible(true);
            }
        }
    }

    private void searchCard() {
        String keyword = txtKey.getText().trim();
        Patron searchParam = new Patron();
        searchParam.setFullName(keyword);

        PatronDAO dao = new PatronDAO();
        results = dao.searchCard(searchParam);
        showResults(results);

        JOptionPane.showMessageDialog(this, "Tìm thấy " + results.length + " kết quả.");
    }

    private void openSelectedCard() {
        int row = tblResult.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một thẻ.");
            return;
        }
        this.dispose();
        new CardActionFrm(results[row], currentUser).setVisible(true);
    }

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(
                new Object[]{"Mã thẻ", "Họ tên", "Trạng thái", "Loại thẻ", "Ngày hết hạn"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void showResults(Patron[] patrons) {
        DefaultTableModel model = createTableModel();
        for (Patron patron : patrons) {
            model.addRow(new Object[]{
                    patron.getPatronID(),
                    patron.getFullName(),
                    patron.getStatus(),
                    patron.getCardType(),
                    patron.getExpiryDate()
            });
        }
        tblResult.setModel(model);
    }
}
