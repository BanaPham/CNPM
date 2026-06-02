import model.SystemUser;
import view.Home;
import view.user.LoginFrm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {
    private JButton btnPatron, btnSystemAdmin;

    public Main() {
        setTitle("Hệ thống Quản lý Thư viện");
        setSize(420, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header label
        JLabel lblTitle = new JLabel("Vui lòng chọn cổng đăng nhập", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        // Buttons Panel - Grid 1x2 (side by side)
        JPanel pnButtons = new JPanel();
        pnButtons.setLayout(new GridLayout(1, 2, 15, 10));
        pnButtons.setBorder(BorderFactory.createEmptyBorder(15, 20, 25, 20));

        btnPatron = new JButton("Cổng Độc giả");
        btnPatron.setFont(new Font("Arial", Font.BOLD, 14));
        btnPatron.addActionListener(this);
        pnButtons.add(btnPatron);

        btnSystemAdmin = new JButton("Quản trị hệ thống");
        btnSystemAdmin.setFont(new Font("Arial", Font.BOLD, 14));
        btnSystemAdmin.addActionListener(this);
        pnButtons.add(btnSystemAdmin);

        add(pnButtons, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPatron) {
            new Home().setVisible(true);
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
