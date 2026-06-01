package view.system;
import model.SystemUser;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemHomeFrm extends JFrame implements ActionListener {
    private SystemUser u;
    private JButton btnCardManagement;

    public SystemHomeFrm(SystemUser u) {
        this.u = u;
        setTitle("Hệ thống quản lý");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        btnCardManagement = new JButton("Quản lý thẻ người dùng");
        btnCardManagement.setBounds(100, 100, 200, 40);
        btnCardManagement.addActionListener(this);
        add(btnCardManagement);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCardManagement) {
            // Chuyển sang màn hình tìm kiếm thẻ
            this.dispose();
            new SearchCardFrm().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SystemUser mockUser = new SystemUser("SU001", "admin", "123456", "Manager");

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SystemHomeFrm(mockUser).setVisible(true);
            }
        });
    }
}
