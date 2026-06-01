package view.system;
import dao.PatronDAO;
import model.Patron;
import model.SystemUser;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class ConfirmFrm extends JFrame implements ActionListener {
    private Patron p;
    private JTextField txtReason;
    private JButton btnConfirm;
    private JButton btnBack;
    private JButton btnCancel;
    private String actionType; // Thuộc tính bổ sung để nhận diện hành động từ Form trước

    public ConfirmFrm(Patron p) {
        this(p, "Thao tác");
    }

    // Constructor mở rộng để xử lý hành động cụ thể
    public ConfirmFrm(Patron p, String actionType) {
        this.p = p;
        this.actionType = actionType;

        setTitle("Xác nhận: " + actionType);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblReason = new JLabel("Lý do / Ghi chú:");
        lblReason.setBounds(30, 40, 100, 30);
        add(lblReason);

        txtReason = new JTextField();
        txtReason.setBounds(140, 40, 200, 30);
        add(txtReason);

        btnConfirm = new JButton("Xác nhận");
        btnConfirm.setBounds(40, 150, 100, 35);
        btnConfirm.addActionListener(this);
        add(btnConfirm);

        btnBack = new JButton("Quay lại");
        btnBack.setBounds(150, 150, 100, 35);
        btnBack.addActionListener(this);
        add(btnBack);

        btnCancel = new JButton("Hủy");
        btnCancel.setBounds(260, 150, 100, 35);
        btnCancel.addActionListener(this);
        add(btnCancel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SystemUser user = new SystemUser("US01", "admin", "123456", "Manager");

        if (e.getSource() == btnConfirm) {
            PatronDAO dao = new PatronDAO();

            // Xử lý thay đổi dữ liệu của Patron dựa trên hành động được chọn
            if (actionType.equals("Khóa")) {
                p.setStatus("Locked");
            } else if (actionType.equals("Mở khóa")) {
                p.setStatus("Active");
            } else if (actionType.equals("Gia hạn")) {
                p.setStatus("Active");
                // Thêm 1 năm gia hạn
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.YEAR, 1);
                p.setExpiryDate(cal.getTime());
            }

            boolean success = dao.updateStatus(p);
            if (success) {
                JOptionPane.showMessageDialog(this, "Thực hiện thành công!");
                this.dispose();
                new SystemHomeFrm(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Thất bại. Vui lòng kiểm tra lại kết nối.");
            }

        } else if (e.getSource() == btnBack) {
            this.dispose();
            new CardActionFrm(p).setVisible(true);


        } else if (e.getSource() == btnCancel) {
            this.dispose();
            new SystemHomeFrm(user).setVisible(true);
        }
    }
}

