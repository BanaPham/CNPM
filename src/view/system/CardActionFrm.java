package view.system;
import model.Patron;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardActionFrm extends JFrame implements ActionListener {
    private Patron p;
    private JButton btnRenewCard;
    private JButton btnLockCard;
    private JButton btnUnlockCard;

    public CardActionFrm(Patron p) {
        this.p = p;
        setTitle("Thao tác Thẻ - " + p.getFullName());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        btnRenewCard = new JButton("Gia hạn thẻ");
        btnRenewCard.setBounds(120, 40, 150, 35);
        btnRenewCard.addActionListener(this);
        add(btnRenewCard);

        btnLockCard = new JButton("Khóa thẻ");
        btnLockCard.setBounds(120, 100, 150, 35);
        btnLockCard.addActionListener(this);
        add(btnLockCard);

        btnUnlockCard = new JButton("Mở khóa thẻ");
        btnUnlockCard.setBounds(120, 160, 150, 35);
        btnUnlockCard.addActionListener(this);
        add(btnUnlockCard);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = "";
        if (e.getSource() == btnRenewCard) action = "Gia hạn";
        else if (e.getSource() == btnLockCard) action = "Khóa";
        else if (e.getSource() == btnUnlockCard) action = "Mở khóa";

        // Chuyển sang màn hình xác nhận cùng với hành động đã chọn
        this.dispose();
        new ConfirmFrm(p, action).setVisible(true);
    }
}

