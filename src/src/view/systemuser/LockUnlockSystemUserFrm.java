package view.systemuser;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.SystemUserDAO;
import model.SystemUser;

public class LockUnlockSystemUserFrm extends JFrame implements ActionListener {
    private SystemUser admin;
    private SystemUser selectedUser;
    private JTextArea txtReason;
    private JTextField txtLockUntil;
    private JButton btnConfirm;
    private JButton btnBack;

    public LockUnlockSystemUserFrm(SystemUser admin, String userID) {
        super("Khóa/Mở khóa tài khoản");
        this.admin = admin;
        SystemUserDAO sud = new SystemUserDAO();
        this.selectedUser = sud.getSystemUserDetail(userID);

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblTitle = new JLabel("Xử lý trạng thái tài khoản");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(22.0f));
        pnMain.add(lblTitle);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel pnInfo = new JPanel(new GridLayout(6, 2, 8, 8));
        if (selectedUser != null) {
            pnInfo.add(new JLabel("Tài khoản:"));
            pnInfo.add(new JLabel(selectedUser.getUsername() + " - " + selectedUser.getFullName()));
            pnInfo.add(new JLabel("Vai trò:"));
            pnInfo.add(new JLabel(selectedUser.getRole()));
            pnInfo.add(new JLabel("Trạng thái hiện tại:"));
            pnInfo.add(new JLabel(selectedUser.getStatus()));
            pnInfo.add(new JLabel("Hành động:"));
            pnInfo.add(new JLabel("Active".equalsIgnoreCase(selectedUser.getStatus()) ? "Khóa tài khoản" : "Mở khóa tài khoản"));
            pnInfo.add(new JLabel("Lý do khóa:"));
            txtReason = new JTextArea(3, 20);
            pnInfo.add(txtReason);
            pnInfo.add(new JLabel("Khóa đến (yyyy-mm-dd, bỏ trống nếu vô thời hạn):"));
            txtLockUntil = new JTextField(15);
            pnInfo.add(txtLockUntil);
        }
        pnMain.add(pnInfo);

        JPanel pnButton = new JPanel();
        btnConfirm = new JButton("Xác nhận");
        btnBack = new JButton("Quay lại");
        btnConfirm.addActionListener(this);
        btnBack.addActionListener(this);
        pnButton.add(btnConfirm);
        pnButton.add(btnBack);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));
        pnMain.add(pnButton);

        this.setContentPane(pnMain);
        this.setSize(720, 360);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnBack)) {
            new SystemUserDetailFrm(admin, selectedUser.getUserID()).setVisible(true);
            this.dispose();
            return;
        }
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản!");
            return;
        }
        if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnConfirm)) {
            SystemUserDAO sud = new SystemUserDAO();
            boolean result = false;
            if ("Active".equalsIgnoreCase(selectedUser.getStatus())) {
                String reason = txtReason.getText().trim();
                if (reason.length() == 0) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập lý do khóa tài khoản!");
                    return;
                }
                java.sql.Date lockUntil = null;
                if (txtLockUntil.getText().trim().length() > 0) {
                    try {
                        lockUntil = java.sql.Date.valueOf(txtLockUntil.getText().trim());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Thời hạn khóa phải có định dạng yyyy-mm-dd!");
                        return;
                    }
                }
                result = sud.lockUser(selectedUser.getUserID(), reason, lockUntil);
                if (!result) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể khóa tài khoản Quản trị viên duy nhất đang hoạt động hoặc tài khoản không hợp lệ!");
                    return;
                }
                JOptionPane.showMessageDialog(this, "Khóa tài khoản thành công!");
            } else {
                result = sud.unlockUser(selectedUser.getUserID());
                if (!result) {
                    JOptionPane.showMessageDialog(this, "Mở khóa tài khoản không thành công!");
                    return;
                }
                JOptionPane.showMessageDialog(this, "Mở khóa tài khoản thành công!");
            }
            new SystemUserManagementFrm(admin).setVisible(true);
            this.dispose();
        }
    }
}
