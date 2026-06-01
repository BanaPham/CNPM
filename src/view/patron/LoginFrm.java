
package view.patron;

import dao.PatronDAO;
import model.Patron;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrm extends JFrame implements ActionListener {
    private JTextField txtUN;
    private JPasswordField txtPW;
    private JButton btnLogin;

    public LoginFrm() {
        setTitle("Đăng nhập");
        setSize(300, 200);
        setLayout(null);

        JLabel lb1 = new JLabel("Tên đăng nhập");
        lb1.setBounds(30, 30, 100, 25);
        add(lb1);

        txtUN = new JTextField();
        txtUN.setBounds(120, 30, 120, 25);
        add(txtUN);

        JLabel lb2 = new JLabel("Mật khẩu");
        lb2.setBounds(30, 70, 100, 25);
        add(lb2);

        txtPW = new JPasswordField();
        txtPW.setBounds(120, 70, 120, 25);
        add(txtPW);

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(100, 120, 100, 30);
        btnLogin.addActionListener(this);
        add(btnLogin);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Patron p = new Patron();
        p.setUsername(txtUN.getText());
        p.setPassword(String.valueOf(txtPW.getPassword()));
        PatronDAO dao = new PatronDAO();
        boolean check = dao.checkLogin(p);
        if (check) {
            JOptionPane.showMessageDialog(this,
                    "Đăng nhập thành công!");
            new UserHomeFrm(p);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Tên đăng nhập/Mật khẩu không đúng");
        }
    }
}

