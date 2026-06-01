package view.patron;

import model.Patron;
import view.opac.OpacHomeFrm;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserHomeFrm extends JFrame implements ActionListener {
    private JButton btnManageProfile, btnOpac;
    private Patron patron;

    public UserHomeFrm(Patron p) {
        this.patron = p;

        setTitle("Trang chủ");
        setSize(420, 360);
        setLayout(null);

        btnManageProfile = new JButton("Quản lý thông tin cá nhân");
        btnManageProfile.setBounds(110, 80, 200, 40);
        btnManageProfile.addActionListener(this);
        add(btnManageProfile);

        btnOpac = new JButton("Tra cứu OPAC");
        btnOpac.setBounds(110, 140, 200, 40);
        btnOpac.addActionListener(this);
        add(btnOpac);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnManageProfile) {
            new ManageProfileFrm(patron).setVisible(true);
        } else if (e.getSource() == btnOpac) {
            new OpacHomeFrm(patron).setVisible(true);
            this.dispose();
        }
    }
}
