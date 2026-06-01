package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.patron.LoginFrm;
import view.register.RegisterFormFrm;
import view.opac.OpacHomeFrm;

public class Home extends JFrame implements ActionListener {
    private JButton btnLogin, btnRegister, btnOpac;

    public Home() {
        setTitle("Hệ thống mượn trả sách quốc gia");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tiêu đề chào mừng
        JLabel lblTitle = new JLabel("Chào mừng bạn đến với hệ thống mượn trả sách quốc gia!", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        add(lblTitle, BorderLayout.NORTH);

        // Panel chứa các nút
        JPanel pnButtons = new JPanel();
        pnButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setPreferredSize(new Dimension(130, 45));
        btnLogin.addActionListener(this);
        pnButtons.add(btnLogin);

        btnRegister = new JButton("Đăng ký");
        btnRegister.setPreferredSize(new Dimension(130, 45));
        btnRegister.addActionListener(this);
        pnButtons.add(btnRegister);

        btnOpac = new JButton("Tra cứu OPAC");
        btnOpac.setPreferredSize(new Dimension(130, 45));
        btnOpac.addActionListener(this);
        pnButtons.add(btnOpac);

        add(pnButtons, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            new LoginFrm().setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnRegister) {
            new RegisterFormFrm().setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnOpac) {
            new OpacHomeFrm().setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Home().setVisible(true);
        });
    }
}
