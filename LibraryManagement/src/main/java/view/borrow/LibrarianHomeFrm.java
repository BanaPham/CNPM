package view.borrow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.SystemUser;
import view.returns.ReturnBookFrm;
import view.system.SearchCardFrm;
import view.user.LoginFrm;

public class LibrarianHomeFrm extends JFrame implements ActionListener {
    private JButton btnBorrow, btnReturn, btnCardManagement, btnLogout;
    private SystemUser user;

    public LibrarianHomeFrm(SystemUser user) {
        super("Hệ thống Quản lý Thư viện - Cổng Thủ thư");
        this.user = user;

        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel (Greeting and Logout)
        JPanel pnHeader = new JPanel(new BorderLayout());
        pnHeader.setBackground(new Color(230, 240, 250));
        pnHeader.setBorder(new EmptyBorder(12, 20, 12, 20));

        JLabel lblWelcome = new JLabel("Xin chào, " + (user != null ? user.getFullName() : "Thủ thư") + " (Thủ thư)");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 15));
        lblWelcome.setForeground(new Color(30, 60, 90));
        pnHeader.add(lblWelcome, BorderLayout.WEST);

        btnLogout = new JButton("Đăng xuất");
        btnLogout.addActionListener(this);
        pnHeader.add(btnLogout, BorderLayout.EAST);

        add(pnHeader, BorderLayout.NORTH);

        // Center Panel (Action Buttons)
        JPanel pnButtons = new JPanel(new GridLayout(3, 1, 12, 12));
        pnButtons.setBorder(new EmptyBorder(25, 40, 25, 40));

        btnBorrow = new JButton("Mượn sách");
        btnBorrow.setFont(new Font("Arial", Font.PLAIN, 15));
        btnBorrow.addActionListener(this);
        pnButtons.add(btnBorrow);

        btnReturn = new JButton("Trả sách");
        btnReturn.setFont(new Font("Arial", Font.PLAIN, 15));
        btnReturn.addActionListener(this);
        pnButtons.add(btnReturn);

        btnCardManagement = new JButton("Quản lý thẻ người dùng");
        btnCardManagement.setFont(new Font("Arial", Font.PLAIN, 15));
        btnCardManagement.addActionListener(this);
        pnButtons.add(btnCardManagement);

        add(pnButtons, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBorrow) {
            new BorrowBookFrm(user);
            this.dispose();
        } else if (e.getSource() == btnReturn) {
            new ReturnBookFrm(user);
            this.dispose();
        } else if (e.getSource() == btnCardManagement) {
            new SearchCardFrm(user).setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnLogout) {
            new LoginFrm().setVisible(true);
            this.dispose();
        }
    }
}
