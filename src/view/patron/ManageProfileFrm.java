package view.patron;
import model.Patron;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageProfileFrm extends JFrame implements ActionListener {
    private JButton btnEditProfile;
    private Patron patron;

    public ManageProfileFrm(Patron p) {
        this.patron = p;

        setTitle("Quản lý thông tin cá nhân");
        setSize(420, 360);
        setLayout(null);

        btnEditProfile = new JButton("Chỉnh sửa thông tin cá nhân");
        btnEditProfile.setBounds(110, 120, 200, 40);
        btnEditProfile.addActionListener(this);
        add(btnEditProfile);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new EditProfileFrm(patron);
    }

    private void addProfileRow(String label, String value, int y) {
        JLabel lb = new JLabel(label);
        lb.setBounds(30, y, 100, 25);
        add(lb);

        JTextField txt = new JTextField(value);
        txt.setBounds(140, y, 220, 25);
        txt.setEditable(false);
        add(txt);
    }

    private String valueOf(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}
