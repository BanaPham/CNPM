package view.register;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.PatronDAO;
import model.Patron;

public class RegisterFormFrm extends JFrame implements ActionListener {

    private JTextField txtFullName, txtDob, txtCccd, txtPhone, txtEmail, txtAddress;
    private JButton btnSubmit, btnBack;

    public RegisterFormFrm() {
        super("Nhập thông tin đăng ký thẻ");

        // --- 1. Tạo Panel chứa các ô nhập liệu ---
        JPanel pnForm = new JPanel();
        pnForm.setLayout(new GridLayout(6, 2, 10, 15));
        pnForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        pnForm.add(new JLabel("Họ và tên (*):"));
        txtFullName = new JTextField();
        pnForm.add(txtFullName);

        pnForm.add(new JLabel("Căn cước công dân (*):"));
        txtCccd = new JTextField();
        pnForm.add(txtCccd);

        pnForm.add(new JLabel("Ngày sinh (dd/MM/yyyy):"));
        txtDob = new JTextField();
        pnForm.add(txtDob);

        pnForm.add(new JLabel("Số điện thoại (*):"));
        txtPhone = new JTextField();
        pnForm.add(txtPhone);

        pnForm.add(new JLabel("Email (*):"));
        txtEmail = new JTextField();
        pnForm.add(txtEmail);

        pnForm.add(new JLabel("Địa chỉ:"));
        txtAddress = new JTextField();
        pnForm.add(txtAddress);

        // --- 2. Tạo Panel chứa các nút bấm ---
        JPanel pnButtons = new JPanel();

        btnBack = new JButton("Quay lại");
        btnBack.addActionListener(this);
        pnButtons.add(btnBack);

        btnSubmit = new JButton("Xác nhận & Nhận mã OTP");
        btnSubmit.addActionListener(this);
        pnButtons.add(btnSubmit);

        // --- 3. Gắn vào JFrame ---
        this.setLayout(new BorderLayout());
        this.add(pnForm, BorderLayout.CENTER);
        this.add(pnButtons, BorderLayout.SOUTH);

        this.setSize(450, 350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            new RegisterMethodFrm().setVisible(true);
            this.dispose();
        }
        else if (e.getSource() == btnSubmit) {
            String cccd = txtCccd.getText().trim();
            String fullName = txtFullName.getText().trim();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();

            if (cccd.isEmpty() || fullName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường có dấu (*)");
                return;
            }

            PatronDAO patronDAO = new PatronDAO();
            if (patronDAO.checkDuplicateCCCD(cccd)) {
                JOptionPane.showMessageDialog(this, "Lỗi: Số CCCD này đã được đăng ký thẻ thư viện!");
                return;
            }

            Patron newPatron = new Patron();
            newPatron.setFullName(fullName);
            newPatron.setCccd(cccd);
            newPatron.setPhone(phone);
            newPatron.setEmail(email);
            newPatron.setAddress(txtAddress.getText().trim());

            try {
                String dobStr = txtDob.getText().trim();
                if (!dobStr.isEmpty()) {
                    Date dob = new SimpleDateFormat("dd/MM/yyyy").parse(dobStr);
                    newPatron.setDob(dob);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Sai định dạng ngày sinh! Vui lòng nhập dd/MM/yyyy");
                return;
            }

            new OTPVerifyFrm(newPatron).setVisible(true);
            this.dispose();
        }
    }
}