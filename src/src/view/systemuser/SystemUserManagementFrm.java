package view.systemuser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.SystemUserDAO;
import model.SystemUser;
import view.user.AdminHomeFrm;

public class SystemUserManagementFrm extends JFrame implements ActionListener {
    private SystemUser admin;
    private JTable tblUser;
    private JButton btnBack;
    private JButton btnRefresh;
    private ArrayList<SystemUser> listUser;
    private SystemUserManagementFrm mainFrm;

    public SystemUserManagementFrm(SystemUser admin) {
        super("Quản lý tài khoản hệ thống");
        this.admin = admin;
        this.mainFrm = this;
        this.listUser = new ArrayList<SystemUser>();

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblTitle = new JLabel("Quản lý tài khoản hệ thống");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(22.0f));
        pnMain.add(lblTitle);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));

        tblUser = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblUser);
        scrollPane.setPreferredSize(new Dimension(780, 330));
        pnMain.add(scrollPane);

        tblUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblUser.getSelectedRow();
                if (row >= 0 && row < listUser.size()) {
                    SystemUser selected = listUser.get(row);
                    new SystemUserDetailFrm(admin, selected.getUserID()).setVisible(true);
                    mainFrm.dispose();
                }
            }
        });

        JPanel pnButton = new JPanel();
        btnRefresh = new JButton("Tải lại");
        btnBack = new JButton("Quay lại");
        btnRefresh.addActionListener(this);
        btnBack.addActionListener(this);
        pnButton.add(btnRefresh);
        pnButton.add(btnBack);
        pnMain.add(pnButton);

        this.add(pnMain, BorderLayout.CENTER);
        this.setSize(820, 460);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadUserList();
    }

    private void loadUserList() {
        SystemUserDAO sud = new SystemUserDAO();
        listUser = sud.getSystemUserList();
        String[] columnNames = { "Mã", "Tên đăng nhập", "Họ tên", "Email", "Vai trò", "Trạng thái", "Ngày tạo" };
        String[][] values = new String[listUser.size()][7];
        for (int i = 0; i < listUser.size(); i++) {
            SystemUser u = listUser.get(i);
            values[i][0] = u.getUserID();
            values[i][1] = u.getUsername();
            values[i][2] = u.getFullName();
            values[i][3] = u.getEmail();
            values[i][4] = u.getRole();
            values[i][5] = u.getStatus();
            values[i][6] = String.valueOf(u.getCreatedDate());
        }
        DefaultTableModel model = new DefaultTableModel(values, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUser.setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnRefresh)) {
            loadUserList();
        } else if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnBack)) {
            new AdminHomeFrm(admin).setVisible(true);
            this.dispose();
        }
    }
}
