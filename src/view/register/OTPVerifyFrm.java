package view.register;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.PatronDAO;
import model.Patron;

public class OTPVerifyFrm extends JFrame implements ActionListener {
    
    private Patron tempPatron;
    private JTextField txtOTP;
    private JButton btnVerify, btnResend;

    public OTPVerifyFrm(Patron p) {
        super("Xác thực đăng ký thẻ");
        this.tempPatron = p;

        // --- 1. Tiêu đề và thông báo ---
        JPanel pnNorth = new JPanel(new BorderLayout());
        pnNorth.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        
        JLabel lblTitle = new JLabel("XÁC THỰC OTP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        
        JLabel lblMsg = new JLabel("<html><center>Mã OTP đã được gửi đến số điện thoại: <b>" 
                                    + tempPatron.getPhone() + "</b><br>Vui lòng nhập mã để hoàn tất.</center></html>", SwingConstants.CENTER);
        
        pnNorth.add(lblTitle, BorderLayout.NORTH);
        pnNorth.add(lblMsg, BorderLayout.SOUTH);

        // --- 2. Ô nhập OTP ---
        JPanel pnCenter = new JPanel();
        pnCenter.add(new JLabel("Mã OTP: "));
        txtOTP = new JTextField(10);
        txtOTP.setFont(new Font("Arial", Font.PLAIN, 20));
        pnCenter.add(txtOTP);

        // --- 3. Nút bấm ---
        JPanel pnSouth = new JPanel();
        btnResend = new JButton("Gửi lại mã");
        btnResend.addActionListener(this);
        
        btnVerify = new JButton("Xác nhận Đăng ký");
        btnVerify.addActionListener(this);
        
        pnSouth.add(btnResend);
        pnSouth.add(btnVerify);

        // --- Gắn vào JFrame ---
        this.setLayout(new BorderLayout());
        this.add(pnNorth, BorderLayout.NORTH);
        this.add(pnCenter, BorderLayout.CENTER);
        this.add(pnSouth, BorderLayout.SOUTH);

        this.setSize(400, 250);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnResend) {
            JOptionPane.showMessageDialog(this, "Mã OTP mới đã được gửi!");
        } 
        else if (e.getSource() == btnVerify) {
            String otp = txtOTP.getText().trim();
            
            // Giả lập kiểm tra mã OTP (Trong thực tế sẽ check với Server/Firebase)
            if (otp.equals("123456")) {
                
                // 1. Tự sinh mã PatronID (P + timestamp)
                String generatedID = "P" + System.currentTimeMillis() % 1000000;
                tempPatron.setPatronID(generatedID);
                
                // 2. Thiết lập các thông số thẻ mặc định
                tempPatron.setStatus("Active");
                tempPatron.setCardType("Standard");
                
                // 3. Tính ngày hết hạn (1 năm kể từ hôm nay)
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.YEAR, 1);
                tempPatron.setExpiryDate(cal.getTime());

                // 4. GỌI DAO ĐỂ LƯU XUỐNG CSDL (Bước quan trọng nhất)
                PatronDAO patronDAO = new PatronDAO();
                boolean success = patronDAO.createPatron(tempPatron);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Chúc mừng! Bạn đã đăng ký thẻ thành công.\nMã thẻ của bạn là: " + generatedID);
                    new view.opac.OpacHomeFrm().setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi hệ thống! Không thể lưu dữ liệu.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mã OTP không chính xác!");
            }
        }
    }
}