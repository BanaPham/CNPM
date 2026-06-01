package view.register;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class RegisterMethodFrm extends JFrame implements ActionListener {

    private JButton btnManualForm;
    private JButton btnScanCCCD;

    public RegisterMethodFrm() {
        super("Đăng ký thẻ Độc giả mới");

        // --- 1. Tiêu đề ---
        JLabel lblTitle = new JLabel("CHỌN PHƯƠNG THỨC ĐĂNG KÝ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // --- 2. Panel chứa 2 nút lựa chọn ---
        JPanel pnButtons = new JPanel();
        pnButtons.setLayout(new GridLayout(1, 2, 20, 0)); // 1 hàng 2 cột, khoảng cách 20px
        pnButtons.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        btnManualForm = new JButton("Điền thông tin thủ công");
        btnManualForm.setPreferredSize(new Dimension(200, 100));
        btnManualForm.addActionListener(this);

        btnScanCCCD = new JButton("Quét thẻ CCCD (Đang phát triển)");
        btnScanCCCD.setPreferredSize(new Dimension(200, 100));
        btnScanCCCD.addActionListener(this);

        pnButtons.add(btnManualForm);
        pnButtons.add(btnScanCCCD);

        // --- 3. Gắn vào JFrame ---
        this.setLayout(new BorderLayout());
        this.add(lblTitle, BorderLayout.NORTH);
        this.add(pnButtons, BorderLayout.CENTER);

        this.setSize(500, 250);
        this.setLocationRelativeTo(null); // Giữa màn hình
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnManualForm) {
            // Mở form điền thông tin và đóng form hiện tại
            new RegisterFormFrm().setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnScanCCCD) {
            JOptionPane.showMessageDialog(this, "Tính năng đang được phát triển, vui lòng chọn điền thủ công!");
        }
    }
}