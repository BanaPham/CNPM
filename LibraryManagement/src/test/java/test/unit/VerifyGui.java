package test.unit;

import view.user.LoginFrm;
import view.user.AdminHomeFrm;
import view.systemuser.SystemUserManagementFrm;
import view.statistic.StatisticReportFrm;
import view.manager.ManagerHomeFrm;
import model.SystemUser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VerifyGui {
    private static final String OUTPUT_DIR = "C:\\Users\\legion\\.gemini\\antigravity\\brain\\6e9e3b39-faac-43e6-99ca-4f104c9945d0\\";

    public static void captureComponent(Component component, String filename) {
        // Ensure layout is done
        component.setSize(component.getPreferredSize());
        if (component instanceof JFrame) {
            JFrame frame = (JFrame) component;
            frame.pack();
        }
        
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        component.paint(g);
        g.dispose();
        
        try {
            File outFile = new File(OUTPUT_DIR + filename);
            ImageIO.write(image, "png", outFile);
            System.out.println("Captured and saved: " + outFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        // Initialize DAO connection
        new dao.DAO();

        System.out.println("Starting GUI verification & screen captures...");

        // 1. Capture LoginFrm
        SwingUtilities.invokeAndWait(() -> {
            LoginFrm loginFrm = new LoginFrm();
            captureComponent(loginFrm, "uat_login.png");
            loginFrm.dispose();
        });

        // Mock a logged in Admin User (user001)
        SystemUser adminUser = new dao.SystemUserDAO().checkLogin("user001", "pass001");
        if (adminUser == null) {
            System.err.println("Could not find admin user001 in database!");
            System.exit(1);
        }

        // 2. Capture AdminHomeFrm
        SwingUtilities.invokeAndWait(() -> {
            AdminHomeFrm adminHome = new AdminHomeFrm(adminUser);
            captureComponent(adminHome, "uat_admin_home.png");
            adminHome.dispose();
        });

        // 3. Capture SystemUserManagementFrm
        SwingUtilities.invokeAndWait(() -> {
            SystemUserManagementFrm userMgmt = new SystemUserManagementFrm(adminUser);
            captureComponent(userMgmt, "uat_user_management.png");
            userMgmt.dispose();
        });

        // 4. Capture StatisticReportFrm
        SwingUtilities.invokeAndWait(() -> {
            StatisticReportFrm statReport = new StatisticReportFrm(adminUser);
            captureComponent(statReport, "uat_statistic_report.png");
            statReport.dispose();
        });

        // 5. Capture ManagerHomeFrm
        SwingUtilities.invokeAndWait(() -> {
            ManagerHomeFrm managerHome = new ManagerHomeFrm(adminUser);
            captureComponent(managerHome, "uat_manager_home.png");
            managerHome.dispose();
        });

        System.out.println("GUI verification finished successfully!");
        System.exit(0);
    }
}
