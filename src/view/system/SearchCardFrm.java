package view.system;
import dao.PatronDAO;
import model.Patron;
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
    private JTable tblResult;
    private Patron[] results = new Patron[0];

    public SearchCardFrm() {
        setTitle("Tim kiem the nguoi dung");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblKey = new JLabel("Nhap tu khoa:");
        lblKey.setBounds(30, 30, 100, 30);
        add(lblKey);

        txtKey = new JTextField();
        txtKey.setBounds(140, 30, 200, 30);
        add(txtKey);

        btnSearch = new JButton("Tim kiem");
        btnSearch.setBounds(350, 30, 100, 30);
        btnSearch.addActionListener(this);
        add(btnSearch);

        tblResult = new JTable(createTableModel());
        JScrollPane scrollPane = new JScrollPane(tblResult);
        scrollPane.setBounds(30, 80, 420, 220);
        add(scrollPane);

        btnSelect = new JButton("Chon the");
        btnSelect.setBounds(180, 320, 120, 30);
        btnSelect.addActionListener(this);
        add(btnSelect);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            searchCard();
        }
        if (e.getSource() == btnSelect) {
            openSelectedCard();
        }
    }

    private void searchCard() {
        String keyword = txtKey.getText().trim();
        Patron searchParam = new Patron();
        searchParam.setFullName(keyword);

        PatronDAO dao = new PatronDAO();
        results = dao.searchCard(searchParam);
        showResults(results);

        JOptionPane.showMessageDialog(this, "Tim thay " + results.length + " ket qua.");
    }

    private void openSelectedCard() {
        int row = tblResult.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon mot the.");
            return;
        }
        this.dispose();
        new CardActionFrm(results[row]).setVisible(true);
    }

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(
                new Object[]{"Ma the", "Ho ten", "Trang thai", "Loai the", "Ngay het han"},
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
