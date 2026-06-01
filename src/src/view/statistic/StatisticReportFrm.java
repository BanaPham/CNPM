package view.statistic;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.StatisticDAO;
import model.SystemUser;
import view.user.AdminHomeFrm;

public class StatisticReportFrm extends JFrame implements ActionListener {
    private SystemUser admin;
    private JTable tblStatistic;
    private JButton btnFilter;
    private JButton btnExport;
    private JButton btnBack;
    private Date startDate;
    private Date endDate;
    private ArrayList<String[]> statisticRows;

    public StatisticReportFrm(SystemUser admin) {
        super("Thống kê và xuất báo cáo");
        this.admin = admin;
        LocalDate now = LocalDate.now();
        this.startDate = Date.valueOf(now.withDayOfMonth(1));
        this.endDate = Date.valueOf(now.withDayOfMonth(now.lengthOfMonth()));
        this.statisticRows = new ArrayList<String[]>();

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblTitle = new JLabel("Thống kê và xuất báo cáo");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(22.0f));
        pnMain.add(lblTitle);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        tblStatistic = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblStatistic);
        scrollPane.setPreferredSize(new Dimension(720, 290));
        pnMain.add(scrollPane);

        JPanel pnButton = new JPanel();
        btnFilter = new JButton("Lọc");
        btnExport = new JButton("Xuất báo cáo");
        btnBack = new JButton("Quay lại");
        btnFilter.addActionListener(this);
        btnExport.addActionListener(this);
        btnBack.addActionListener(this);
        pnButton.add(btnFilter);
        pnButton.add(btnExport);
        pnButton.add(btnBack);
        pnMain.add(pnButton);

        this.add(pnMain, BorderLayout.CENTER);
        this.setSize(780, 430);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadStatistic(startDate, endDate);
    }

    public void loadStatistic(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        StatisticDAO sd = new StatisticDAO();
        statisticRows = sd.getStatistic(startDate, endDate);
        String[] columnNames = { "Chỉ số", "Giá trị" };
        String[][] values = new String[statisticRows.size()][2];
        for (int i = 0; i < statisticRows.size(); i++) {
            values[i][0] = statisticRows.get(i)[0];
            values[i][1] = statisticRows.get(i)[1];
        }
        DefaultTableModel model = new DefaultTableModel(values, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblStatistic.setModel(model);
        this.setTitle("Thống kê và xuất báo cáo: " + startDate + " đến " + endDate);
    }

    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public ArrayList<String[]> getStatisticRows() { return statisticRows; }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnFilter)) {
            new FilterStatisticFrm(admin, this).setVisible(true);
        } else if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnExport)) {
            new ExportReportFrm(admin, this).setVisible(true);
        } else if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnBack)) {
            new AdminHomeFrm(admin).setVisible(true);
            this.dispose();
        }
    }
}
