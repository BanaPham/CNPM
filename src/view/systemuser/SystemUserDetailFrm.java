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
 
import dao.SystemUserDAO;
import model.SystemUser;
 
public class SystemUserDetailFrm extends JFrame implements ActionListener {
	private SystemUser admin;
	private SystemUser selectedUser;
	private JButton btnLockUnlock;
	private JButton btnRole;
	private JButton btnBack;
 
	public SystemUserDetailFrm(SystemUser admin, String userID) {
        super("Chi tiết tài khoản");
    	this.admin = admin;
    	SystemUserDAO sud = new SystemUserDAO();
        this.selectedUser = sud.getSystemUserDetail(userID);
 
    	JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));
 
    	JLabel lblTitle = new JLabel("Chi tiết tài khoản hệ thống");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(22.0f));
        pnMain.add(lblTitle);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));
 
    	if (selectedUser == null) {
            pnMain.add(new JLabel("Không tìm thấy tài khoản."));
    	} else {
        	JPanel pnInfo = new JPanel(new GridLayout(10, 2, 8, 8));
            pnInfo.add(new JLabel("Mã người dùng:"));
            pnInfo.add(new JLabel(selectedUser.getUserID()));
            pnInfo.add(new JLabel("Tên đăng nhập:"));
            pnInfo.add(new JLabel(selectedUser.getUsername()));
            pnInfo.add(new JLabel("Họ tên:"));
            pnInfo.add(new JLabel(selectedUser.getFullName()));
            pnInfo.add(new JLabel("Email:"));
            pnInfo.add(new JLabel(selectedUser.getEmail()));
            pnInfo.add(new JLabel("Vai trò:"));
            pnInfo.add(new JLabel(selectedUser.getRole()));
            pnInfo.add(new JLabel("Trạng thái:"));
            pnInfo.add(new JLabel(selectedUser.getStatus()));
            pnInfo.add(new JLabel("Ngày tạo:"));
            pnInfo.add(new JLabel(String.valueOf(selectedUser.getCreatedDate())));
            pnInfo.add(new JLabel("Lý do khóa:"));
            pnInfo.add(new JLabel(selectedUser.getLockReason() == null ? "" : selectedUser.getLockReason()));
            pnInfo.add(new JLabel("Khóa đến:"));
            pnInfo.add(new JLabel(selectedUser.getLockUntil() == null ? "" : String.valueOf(selectedUser.getLockUntil())));
            pnInfo.add(new JLabel("Mật khẩu:"));
            pnInfo.add(new JLabel(selectedUser.getPassword()));
            pnMain.add(pnInfo);
    	}
 
    	JPanel pnButton = new JPanel();
    	btnLockUnlock = new JButton("Khóa/Mở khóa tài khoản");
    	btnRole = new JButton("Phân quyền tài khoản");
    	btnBack = new JButton("Quay lại");
        btnLockUnlock.addActionListener(this);
        btnRole.addActionListener(this);
        btnBack.addActionListener(this);
        pnButton.add(btnLockUnlock);
        pnButton.add(btnRole);
        pnButton.add(btnBack);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));
        pnMain.add(pnButton);
 
        this.setContentPane(pnMain);
        this.setSize(650, 480);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
 
	@Override
	public void actionPerformed(ActionEvent e) {
    	if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản để xử lý!");
        	return;
    	}
    	if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnLockUnlock)) {
        	new LockUnlockSystemUserFrm(admin, selectedUser.getUserID()).setVisible(true);
            this.dispose();
    	} else if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnRole)) {
        	new SystemUserRoleFrm(admin, selectedUser.getUserID()).setVisible(true);
            this.dispose();
    	} else if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnBack)) {
        	new SystemUserManagementFrm(admin).setVisible(true);
            this.dispose();
    	}
	}
}
