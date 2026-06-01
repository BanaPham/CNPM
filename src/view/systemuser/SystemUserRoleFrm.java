package view.systemuser;
 
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
 
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
 
import dao.SystemUserDAO;
import model.SystemUser;
 
public class SystemUserRoleFrm extends JFrame implements ActionListener {
	private SystemUser admin;
	private SystemUser selectedUser;
	private JComboBox<String> cboRole;
	private JButton btnUpdate;
	private JButton btnBack;
 
	public SystemUserRoleFrm(SystemUser admin, String userID) {
        super("Phân quyền tài khoản");
    	this.admin = admin;
    	SystemUserDAO sud = new SystemUserDAO();
        this.selectedUser = sud.getSystemUserDetail(userID);
 
    	JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));
 
    	JLabel lblTitle = new JLabel("Phân quyền tài khoản");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(22.0f));
        pnMain.add(lblTitle);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));
 
    	JPanel pnInfo = new JPanel(new GridLayout(4, 2, 8, 8));
    	if (selectedUser != null) {
            pnInfo.add(new JLabel("Tài khoản:"));
            pnInfo.add(new JLabel(selectedUser.getUsername() + " - " + selectedUser.getFullName()));
            pnInfo.add(new JLabel("Vai trò hiện tại:"));
            pnInfo.add(new JLabel(selectedUser.getRole()));
            pnInfo.add(new JLabel("Vai trò mới:"));
            ArrayList<String> roles = sud.getRoleList();
        	cboRole = new JComboBox<String>();
        	for (String role : roles) {
                cboRole.addItem(role);
        	}
            cboRole.setSelectedItem(selectedUser.getRole());
            pnInfo.add(cboRole);
            pnInfo.add(new JLabel("Trạng thái:"));
            pnInfo.add(new JLabel(selectedUser.getStatus()));
    	}
        pnMain.add(pnInfo);
 
    	JPanel pnButton = new JPanel();
    	btnUpdate = new JButton("Cập nhật vai trò");
    	btnBack = new JButton("Quay lại");
        btnUpdate.addActionListener(this);
        btnBack.addActionListener(this);
        pnButton.add(btnUpdate);
        pnButton.add(btnBack);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));
        pnMain.add(pnButton);
 
        this.setContentPane(pnMain);
        this.setSize(560, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
 
	@Override
	public void actionPerformed(ActionEvent e) {
    	if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản!");
        	return;
    	}
    	if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnBack)) {
        	new SystemUserDetailFrm(admin, selectedUser.getUserID()).setVisible(true);
            this.dispose();
    	} else if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnUpdate)) {
        	String newRole = String.valueOf(cboRole.getSelectedItem());
            SystemUserDAO sud = new SystemUserDAO();
        	if (sud.updateRole(selectedUser.getUserID(), newRole)) {
                JOptionPane.showMessageDialog(this, "Cập nhật vai trò thành công!");
            	new SystemUserManagementFrm(admin).setVisible(true);
                this.dispose();
        	} else {
                JOptionPane.showMessageDialog(this, "Cập nhật vai trò không thành công!");
        	}
    	}
	}
}
