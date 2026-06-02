import model.SystemUser;
import view.borrow.LibrarianHomeFrm;
import view.Home;
import view.system.SystemHomeFrm;
import view.user.LoginFrm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {
    private JButton btnPatron, btnLibrarian, btnCardAdmin, btnSystemAdmin;

    public Main() {
        setTitle("Hệ thống Quản lý Thư viện");
        setSize(420, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header label
        JLabel lblTitle = new JLabel("Vui lòng chọn cổng đăng nhập", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        // Buttons Panel - Grid 2x2
        JPanel pnButtons = new JPanel();
        pnButtons.setLayout(new GridLayout(2, 2, 10, 10));
        pnButtons.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        btnPatron = new JButton("Cổng Độc giả");
        btnPatron.addActionListener(this);
        pnButtons.add(btnPatron);

        btnLibrarian = new JButton("Cổng Thủ thư");
        btnLibrarian.addActionListener(this);
        pnButtons.add(btnLibrarian);

        btnCardAdmin = new JButton("Quản lý thẻ độc giả");
        btnCardAdmin.addActionListener(this);
        pnButtons.add(btnCardAdmin);

        btnSystemAdmin = new JButton("Quản trị hệ thống");
        btnSystemAdmin.addActionListener(this);
        pnButtons.add(btnSystemAdmin);

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
        } else if (e.getSource() == btnSystemAdmin) {
            new LoginFrm().setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
