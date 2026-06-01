package view.borrow;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.SystemUser;

public class LibrarianHomeFrm extends JFrame implements ActionListener {
    private JButton btnBorrow, btnReturn;
    private SystemUser user;

    public LibrarianHomeFrm(SystemUser user) {
        super("Trang chủ Thủ thư");
        this.user = user;

        btnBorrow = new JButton("Mượn sách");
        btnReturn = new JButton("Trả sách");

        btnBorrow.addActionListener(this);
        btnReturn.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Xin chào, " + user.getFullName()));
        panel.add(btnBorrow);
        panel.add(btnReturn);

        this.add(panel, BorderLayout.CENTER);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBorrow) {
            new BorrowBookFrm(user);
            this.dispose();
        } else if (e.getSource() == btnReturn) {
            // Nghiệp vụ trả sách
        }
    }
}