package view.statistic;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.SystemUser;

public class FilterStatisticFrm extends JFrame implements ActionListener {
    private SystemUser admin;
    private StatisticReportFrm statisticReportFrm;
    private JComboBox<String> cboFilterType;
    private JTextField txtDay;
    private JTextField txtMonth;
    private JTextField txtQuarter;
    private JTextField txtYear;
    private JButton btnApply;
    private JButton btnClose;

    public FilterStatisticFrm(SystemUser admin, StatisticReportFrm statisticReportFrm) {
        super("Lọc thống kê");
        this.admin = admin;
        this.statisticReportFrm = statisticReportFrm;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblTitle = new JLabel("Lọc dữ liệu thống kê");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(20.0f));
        pnMain.add(lblTitle);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel pnForm = new JPanel(new GridLayout(5, 2, 8, 8));
        cboFilterType = new JComboBox<String>(new String[] { "Ngày", "Tháng", "Quý", "Năm" });
        txtDay = new JTextField("1");
        txtMonth = new JTextField(String.valueOf(LocalDate.now().getMonthValue()));
        txtQuarter = new JTextField("1");
        txtYear = new JTextField(String.valueOf(LocalDate.now().getYear()));
        pnForm.add(new JLabel("Kiểu lọc:"));
        pnForm.add(cboFilterType);
        pnForm.add(new JLabel("Ngày:"));
        pnForm.add(txtDay);
        pnForm.add(new JLabel("Tháng:"));
        pnForm.add(txtMonth);
        pnForm.add(new JLabel("Quý:"));
        pnForm.add(txtQuarter);
        pnForm.add(new JLabel("Năm:"));
        pnForm.add(txtYear);
        pnMain.add(pnForm);

        JPanel pnButton = new JPanel();
        btnApply = new JButton("Áp dụng");
        btnClose = new JButton("Đóng");
        btnApply.addActionListener(this);
        btnClose.addActionListener(this);
        pnButton.add(btnApply);
        pnButton.add(btnClose);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));
        pnMain.add(pnButton);

        this.setContentPane(pnMain);
        this.setSize(430, 330);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnClose)) {
            this.dispose();
            return;
        }
        if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnApply)) {
            try {
                String type = String.valueOf(cboFilterType.getSelectedItem());
                int day = Integer.parseInt(txtDay.getText().trim());
                int month = Integer.parseInt(txtMonth.getText().trim());
                int quarter = Integer.parseInt(txtQuarter.getText().trim());
                int year = Integer.parseInt(txtYear.getText().trim());

                LocalDate start;
                LocalDate end;
                if ("Ngày".equals(type)) {
                    start = LocalDate.of(year, month, day);
                    end = start;
                } else if ("Tháng".equals(type)) {
                    start = LocalDate.of(year, month, 1);
                    end = start.withDayOfMonth(start.lengthOfMonth());
                } else if ("Quý".equals(type)) {
                    int startMonth = (quarter - 1) * 3 + 1;
                    start = LocalDate.of(year, startMonth, 1);
                    end = start.plusMonths(2).withDayOfMonth(start.plusMonths(2).lengthOfMonth());
                } else {
                    start = LocalDate.of(year, 1, 1);
                    end = LocalDate.of(year, 12, 31);
                }
                statisticReportFrm.loadStatistic(Date.valueOf(start), Date.valueOf(end));
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu lọc không hợp lệ!");
            }
        }
    }
}
