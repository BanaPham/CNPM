package view.statistic;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.ReportDAO;
import dao.StatisticDAO;
import model.SystemUser;

public class ExportReportFrm extends JFrame implements ActionListener {
    private SystemUser admin;
    private StatisticReportFrm statisticReportFrm;
    private JComboBox<String> cboFormat;
    private JCheckBox chkOverview;
    private JCheckBox chkDetail;
    private JButton btnExport;
    private JButton btnClose;

    public ExportReportFrm(SystemUser admin, StatisticReportFrm statisticReportFrm) {
        super("Xuất báo cáo");
        this.admin = admin;
        this.statisticReportFrm = statisticReportFrm;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblTitle = new JLabel("Tùy chọn xuất báo cáo");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(20.0f));
        pnMain.add(lblTitle);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel pnForm = new JPanel(new GridLayout(4, 2, 8, 8));
        cboFormat = new JComboBox<String>(new String[] { "Excel CSV", "Text" });
        chkOverview = new JCheckBox("Thống kê tổng quan", true);
        chkDetail = new JCheckBox("Chi tiết giao dịch", true);
        pnForm.add(new JLabel("Định dạng:"));
        pnForm.add(cboFormat);
        pnForm.add(new JLabel("Phạm vi dữ liệu:"));
        pnForm.add(new JLabel(statisticReportFrm.getStartDate() + " đến " + statisticReportFrm.getEndDate()));
        pnForm.add(new JLabel("Nội dung tổng quan:"));
        pnForm.add(chkOverview);
        pnForm.add(new JLabel("Nội dung chi tiết:"));
        pnForm.add(chkDetail);
        pnMain.add(pnForm);

        JPanel pnButton = new JPanel();
        btnExport = new JButton("Xuất báo cáo");
        btnClose = new JButton("Đóng");
        btnExport.addActionListener(this);
        btnClose.addActionListener(this);
        pnButton.add(btnExport);
        pnButton.add(btnClose);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));
        pnMain.add(pnButton);

        this.setContentPane(pnMain);
        this.setSize(520, 320);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnClose)) {
            this.dispose();
            return;
        }
        if (e.getSource() instanceof JButton && ((JButton) e.getSource()).equals(btnExport)) {
            ArrayList<String[]> statisticRows = chkOverview.isSelected() ? statisticReportFrm.getStatisticRows()
                    : new ArrayList<String[]>();
            ArrayList<String[]> detailRows = new ArrayList<String[]>();
            if (chkDetail.isSelected()) {
                StatisticDAO sd = new StatisticDAO();
                detailRows = sd.getLoanDetailReport(statisticReportFrm.getStartDate(), statisticReportFrm.getEndDate());
            }

            String format = String.valueOf(cboFormat.getSelectedItem());
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new File(format.equals("Excel CSV") ? "bao_cao_thu_vien.csv" : "bao_cao_thu_vien.txt"));
            int result = chooser.showSaveDialog(this);
            if (result != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File file = chooser.getSelectedFile();
            ReportDAO rd = new ReportDAO();
            boolean ok;
            if (format.equals("Excel CSV")) {
                ok = rd.exportExcelCsv(file, statisticRows, detailRows);
            } else {
                ok = rd.exportTextReport(file, statisticReportFrm.getStartDate(), statisticReportFrm.getEndDate(),
                        statisticRows, detailRows);
            }
            if (ok) {
                JOptionPane.showMessageDialog(this, "Xuất báo cáo thành công: " + file.getAbsolutePath());
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể tạo file báo cáo – vui lòng thử lại!");
            }
        }
    }
}
