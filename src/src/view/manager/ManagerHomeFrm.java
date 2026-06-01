package view.manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.SystemUser;

public class ManagerHomeFrm extends JFrame implements ActionListener {

    private JButton btnCataloging;
    private JButton btnBack;
    private SystemUser user;

    private boolean isAdmin() {
        if (user == null) return false;
        String role = user.getRole();
        return "admin".equalsIgnoreCase(role) || "Quản trị viên".equalsIgnoreCase(role);
    }

    public ManagerHomeFrm(SystemUser user) {
        super("Hệ thống Quản lý Thư viện - Biên mục tài liệu");
        this.user = user;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        // Hiển thị thông tin người dùng + vai trò
        String roleName = (user != null) ? user.getRole() : "Thủ thư";
        String fullName = (user != null) ? user.getFullName() : "";
        JLabel lblWelcome = new JLabel("Xin chào, " + fullName + " (" + roleName + ")");
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblWelcome.setFont(lblWelcome.getFont().deriveFont(14.0f));
        pnMain.add(lblWelcome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));

        btnCataloging = new JButton("Biên mục tài liệu (Nhập kho)");
        btnCataloging.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCataloging.addActionListener(this);
        pnMain.add(btnCataloging);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        // Admin: "Quay lại trang chủ Admin" | Thủ thư: "Đăng xuất"
        btnBack = new JButton(isAdmin() ? "Quay lại trang chủ Admin" : "Đăng xuất");
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(this);
        pnMain.add(btnBack);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        this.setContentPane(pnMain);
        this.setSize(450, 230);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(isAdmin() ? JFrame.DISPOSE_ON_CLOSE : JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCataloging) {
            new CatalogingFrm(user).setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnBack) {
            if (isAdmin()) {
                new view.user.AdminHomeFrm(user).setVisible(true);
            } else {
                new view.user.LoginFrm().setVisible(true);
            }
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new ManagerHomeFrm(null).setVisible(true);
    }
}