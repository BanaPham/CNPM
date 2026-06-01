package view.manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerHomeFrm extends JFrame implements ActionListener {

    private JButton btnCataloging;

    public ManagerHomeFrm() {
        super("Hệ thống Quản lý Thư viện - Dành cho Thủ thư");
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        btnCataloging = new JButton("Biên mục tài liệu (Nhập kho)");
        btnCataloging.addActionListener(this);
        this.add(btnCataloging);

        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCataloging) {
            // Mở giao diện Biên mục
            new CatalogingFrm().setVisible(true);
            this.dispose(); // Đóng form trang chủ
        }
    }

    public static void main(String[] args) {
        new ManagerHomeFrm().setVisible(true);
    }
}