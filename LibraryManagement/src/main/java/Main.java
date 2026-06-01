import model.SystemUser;
import view.borrow.LibrarianHomeFrm;
import view.Home;
import view.system.SystemHomeFrm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {
    private JButton btnPatron, btnLibrarian, btnCardAdmin;

    public Main() {
        setTitle("Hệ thống Quản lý Thư viện");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header label
        JLabel lblTitle = new JLabel("Vui lòng chọn cổng đăng nhập", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitle, BorderLayout.NORTH);

        // Buttons Panel
        JPanel pnButtons = new JPanel();
        pnButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        btnPatron = new JButton("Cổng Độc giả (OPAC)");
        btnPatron.setPreferredSize(new Dimension(140, 40));
        btnPatron.addActionListener(this);
        pnButtons.add(btnPatron);

        btnLibrarian = new JButton("Cổng Thủ thư");
        btnLibrarian.setPreferredSize(new Dimension(140, 40));
        btnLibrarian.addActionListener(this);
        pnButtons.add(btnLibrarian);

        btnCardAdmin = new JButton("Quản lý thẻ độc giả");
        btnCardAdmin.setPreferredSize(new Dimension(140, 40));
        btnCardAdmin.addActionListener(this);
        pnButtons.add(btnCardAdmin);

        add(pnButtons, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPatron) {
            new Home().setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnLibrarian) {
            SystemUser librarian = new SystemUser();
            librarian.setId(1);
            librarian.setFullName("Nguyễn Văn Thủ Thư");
            new LibrarianHomeFrm(librarian);
            this.dispose();
        } else if (e.getSource() == btnCardAdmin) {
            SystemUser mockUser = new SystemUser("SU001", "admin", "123456", "Manager");
            new SystemHomeFrm(mockUser).setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
