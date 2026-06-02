package view.user;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.SystemUserDAO;
import model.SystemUser;

public class LoginFrm extends JFrame implements ActionListener {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrm() {
        super("Đăng nhập hệ thống");
        txtUsername = new JTextField(18);
        txtPassword = new JPasswordField(18);
        txtPassword.setEchoChar('*');
        btnLogin = new JButton("Đăng nhập");
        btnLogin.addActionListener(this);

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblTitle = new JLabel("Đăng nhập");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(22.0f));
        pnMain.add(lblTitle);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel pnUsername = new JPanel(new FlowLayout());
        pnUsername.add(new JLabel("Tên đăng nhập:"));
        pnUsername.add(txtUsername);
        pnMain.add(pnUsername);

        JPanel pnPassword = new JPanel(new FlowLayout());
        pnPassword.add(new JLabel("Mật khẩu:"));
        pnPassword.add(txtPassword);
        pnMain.add(pnPassword);

        JPanel pnButton = new JPanel(new FlowLayout());
        pnButton.add(btnLogin);
        pnMain.add(pnButton);

        this.setContentPane(pnMain);
        this.setSize(420, 230);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnLogin)) {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            if (username.length() == 0 || password.length() == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
                return;
            }

            SystemUserDAO sud = new SystemUserDAO();
            SystemUser user = sud.checkLogin(username, password);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!");
                return;
            }
            if (!"active".equalsIgnoreCase(user.getStatus())) {
                JOptionPane.showMessageDialog(this, "Tài khoản đang bị khóa, không thể đăng nhập!");
                return;
            }
            if (isAdminRole(user.getRole())) {
                new AdminHomeFrm(user).setVisible(true);
                this.dispose();
            } else if (isLibrarianRole(user.getRole())) {
                new view.borrow.LibrarianHomeFrm(user).setVisible(true);
                this.dispose();
            } else if (isManagerRole(user.getRole())) {
                new view.manager.ManagerHomeFrm(user).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Tài khoản không có quyền truy cập hệ thống!\nChỉ Admin, Librarian và Manager mới được phép đăng nhập.");
            }
        }
    }

    private boolean isAdminRole(String role) {
        return "admin".equalsIgnoreCase(role) || "Quản trị viên".equalsIgnoreCase(role);
    }

    private boolean isLibrarianRole(String role) {
        return "librarian".equalsIgnoreCase(role) || "Thủ thư".equalsIgnoreCase(role);
    }

    private boolean isManagerRole(String role) {
        return "manager".equalsIgnoreCase(role) || "Quản lý".equalsIgnoreCase(role);
    }

    public static void main(String[] args) {
        LoginFrm frm = new LoginFrm();
        frm.setVisible(true);
    }
}
