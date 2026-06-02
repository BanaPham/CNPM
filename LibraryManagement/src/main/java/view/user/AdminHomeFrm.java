package view.user;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.SystemUser;
import view.statistic.StatisticReportFrm;
import view.systemuser.SystemUserManagementFrm;

public class AdminHomeFrm extends JFrame implements ActionListener {
    private JButton btnUserManagement;
    private JButton btnStatisticReport;
    private JButton btnCataloging;
    private JButton btnLogout;
    private SystemUser user;

    public AdminHomeFrm(SystemUser user) {
        super("Admin Home - Hệ thống Quản lý Thư viện");
        this.user = user;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));

        JPanel pnUser = new JPanel();
        pnUser.setLayout(new BoxLayout(pnUser, BoxLayout.LINE_AXIS));
        pnUser.add(Box.createRigidArea(new Dimension(420, 0)));
        JLabel lblUser = new JLabel("Đăng nhập: " + user.getFullName() + " - " + user.getRole());
        lblUser.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pnUser.add(lblUser);
        pnMain.add(pnUser);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lblTitle = new JLabel("Trang chủ Quản trị viên");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(26.0f));
        pnMain.add(lblTitle);
        pnMain.add(Box.createRigidArea(new Dimension(0, 25)));

        btnUserManagement = new JButton("Quản lý tài khoản hệ thống");
        btnUserManagement.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnUserManagement.addActionListener(this);
        pnMain.add(btnUserManagement);
        pnMain.add(Box.createRigidArea(new Dimension(0, 12)));

        btnStatisticReport = new JButton("Thống kê và xuất báo cáo");
        btnStatisticReport.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStatisticReport.addActionListener(this);
        pnMain.add(btnStatisticReport);
        pnMain.add(Box.createRigidArea(new Dimension(0, 12)));

        btnCataloging = new JButton("Biên mục tài liệu (Nhập kho)");
        btnCataloging.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCataloging.addActionListener(this);
        pnMain.add(btnCataloging);
        pnMain.add(Box.createRigidArea(new Dimension(0, 12)));

        btnLogout = new JButton("Đăng xuất");
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.addActionListener(this);
        pnMain.add(btnLogout);

        this.add(pnMain, BorderLayout.CENTER);
        this.setSize(620, 380);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnUserManagement)) {
            new SystemUserManagementFrm(user).setVisible(true);
            this.dispose();
        } else if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnStatisticReport)) {
            new StatisticReportFrm(user).setVisible(true);
            this.dispose();
        } else if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnCataloging)) {
            new view.manager.ManagerHomeFrm(user).setVisible(true);
            this.dispose();
        } else if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnLogout)) {
            new LoginFrm().setVisible(true);
            this.dispose();
        }
    }
}
