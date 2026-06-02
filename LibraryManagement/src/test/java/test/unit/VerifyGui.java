package test.unit;

import view.user.LoginFrm;
import view.user.AdminHomeFrm;
import view.systemuser.SystemUserManagementFrm;
import view.statistic.StatisticReportFrm;
import view.manager.ManagerHomeFrm;
import view.borrow.LibrarianHomeFrm;
import view.system.SearchCardFrm;
import view.system.CardActionFrm;
import view.system.ConfirmFrm;
import model.SystemUser;
import model.Patron;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class VerifyGui {
    private static final String OUTPUT_DIR = "C:\\Users\\legion\\.gemini\\antigravity\\brain\\6e9e3b39-faac-43e6-99ca-4f104c9945d0\\";

    public static void captureComponent(Component component, String filename) {
        if (component instanceof JFrame) {
            JFrame frame = (JFrame) component;
            if (frame.getWidth() == 0 || frame.getHeight() == 0) {
                frame.pack();
            }
            frame.setVisible(true);
            try {
                Thread.sleep(300); // Give GUI thread time to render peer
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            component.setSize(component.getPreferredSize());
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
        new dao.DAO();

        System.out.println("Starting GUI verification & screen captures...");

        // 1. Capture LoginFrm
        SwingUtilities.invokeAndWait(() -> {
            LoginFrm loginFrm = new LoginFrm();
            captureComponent(loginFrm, "uat_login.png");
            loginFrm.dispose();
        });

        // Load users from DB
        SystemUser adminUser = new dao.SystemUserDAO().checkLogin("user001", "pass001");
        SystemUser librarianUser = new dao.SystemUserDAO().checkLogin("user002", "pass002");

        if (adminUser == null || librarianUser == null) {
            System.err.println("Could not load users from database!");
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

        // 5. Capture ManagerHomeFrm (Manager portal)
        SwingUtilities.invokeAndWait(() -> {
            ManagerHomeFrm managerHome = new ManagerHomeFrm(adminUser);
            captureComponent(managerHome, "uat_manager_home.png");
            managerHome.dispose();
        });

        // 6. Capture LibrarianHomeFrm (Librarian portal with new card management option)
        SwingUtilities.invokeAndWait(() -> {
            LibrarianHomeFrm librarianHome = new LibrarianHomeFrm(librarianUser);
            captureComponent(librarianHome, "uat_librarian_home.png");
            librarianHome.dispose();
        });

        // 7. Capture SearchCardFrm
        SwingUtilities.invokeAndWait(() -> {
            SearchCardFrm searchCard = new SearchCardFrm(librarianUser);
            captureComponent(searchCard, "uat_search_card.png");
            searchCard.dispose();
        });

        // Create a mock Patron for card details
        Patron mockPatron = new Patron();
        mockPatron.setPatronID("P001");
        mockPatron.setFullName("Nguyễn Văn Độc Giả");
        mockPatron.setStatus("Active");
        mockPatron.setCardType("Standard");
        mockPatron.setExpiryDate(new Date());

        // 8. Capture CardActionFrm
        SwingUtilities.invokeAndWait(() -> {
            CardActionFrm cardAction = new CardActionFrm(mockPatron, librarianUser);
            captureComponent(cardAction, "uat_card_action.png");
            cardAction.dispose();
        });

        // 9. Capture ConfirmFrm
        SwingUtilities.invokeAndWait(() -> {
            ConfirmFrm confirmAction = new ConfirmFrm(mockPatron, "Gia hạn", librarianUser);
            captureComponent(confirmAction, "uat_confirm_action.png");
            confirmAction.dispose();
        });

        System.out.println("GUI verification finished successfully!");
        System.exit(0);
    }
}
