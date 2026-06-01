package view.patron;
import dao.PatronDAO;
import model.Patron;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProfileFrm extends JFrame implements ActionListener {
    private Patron p;
    private JTextField txtPW;
    private JTextField txtAddress;
    private JTextField txtPhone;
    private JButton btnSave;
    private JButton btnReset;

    public EditProfileFrm(Patron p) {
        this.p = p;

        setTitle("Chỉnh sửa thông tin cá nhân");
        setSize(400, 300);
        setLayout(null);

        JLabel lb1 = new JLabel("Mật khẩu");
        lb1.setBounds(30, 30, 100, 25);
        add(lb1);

        txtPW = new JTextField(p.getPassword());
        txtPW.setBounds(140, 30, 180, 25);
        add(txtPW);

        JLabel lb2 = new JLabel("Địa chỉ");
        lb2.setBounds(30, 80, 100, 25);
        add(lb2);

        txtAddress = new JTextField(p.getAddress());
        txtAddress.setBounds(140, 80, 180, 25);
        add(txtAddress);

        JLabel lb3 = new JLabel("SĐT");
        lb3.setBounds(30, 130, 100, 25);
        add(lb3);

        txtPhone = new JTextField(p.getPhone());
        txtPhone.setBounds(140, 130, 180, 25);
        add(txtPhone);

        btnSave = new JButton("Lưu");
        btnSave.setBounds(70, 200, 100, 30);

        btnReset = new JButton("Cài lại");
        btnReset.setBounds(200, 200, 100, 30);

        btnSave.addActionListener(this);
        btnReset.addActionListener(this);

        add(btnSave);
        add(btnReset);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            p.setPassword(txtPW.getText());
            p.setAddress(txtAddress.getText());
            p.setPhone(txtPhone.getText());

            PatronDAO dao = new PatronDAO();
            boolean check = dao.updateProfile(p);

            if (check) {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật thành công!");
                new ManageProfileFrm(p);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật thất bại!");
            }
        }

        if (e.getSource() == btnReset) {
            txtPW.setText("");
            txtAddress.setText("");
            txtPhone.setText("");
        }
    }
}

