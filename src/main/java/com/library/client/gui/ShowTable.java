package main.java.com.library.client.gui;

import main.java.com.library.client.gui.view.ClientPanel;
import main.java.com.library.client.gui.view.SideBar;
import main.java.com.library.client.gui.view.WorkPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static main.java.com.library.client.gui.impl.ToolsIMPL.*;

public class ShowTable {
    public static int START_VALUE = 100;
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    public static int PANEL_COUNT = 20;
    public static int SCROLL_VALUE = 0;
    public static int CURRENT_WIDTH = 0;
    public static JFrame mainFrame = new JFrame("图书管理系统");
    public static SideBar sideBar = new SideBar();
    public static JPanel mainPanel = new JPanel(new GridBagLayout());

    public ShowTable() {
        setFrame(mainFrame, WIDTH, HEIGHT, new BorderLayout(), true, JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        setCustomFont(tabbedPane, 12, Font.PLAIN);

        tabbedPane.addTab("工作区", mainPanel);

        JPanel clientPanel = new ClientPanel(new GridBagLayout());
        tabbedPane.addTab("用户管理", clientPanel);

        WorkPanel workPanel = new WorkPanel(PANEL_COUNT, data);
        JScrollPane scrollPane = new JScrollPane(workPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SCROLL_VALUE = scrollPane.getVerticalScrollBar().getValue();
                if (CURRENT_WIDTH != mainFrame.getWidth()) {
                    CURRENT_WIDTH = mainFrame.getWidth();
                    workPanel.updateLayout(Math.max(START_VALUE + mainFrame.getWidth() - WIDTH, 50));
                }
                SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(SCROLL_VALUE));
            }
        });

        mainFrame.addWindowStateListener(e -> {
            SCROLL_VALUE = scrollPane.getVerticalScrollBar().getValue();
            if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                if (CURRENT_WIDTH != mainFrame.getWidth()) {
                    CURRENT_WIDTH = mainFrame.getWidth();
                    workPanel.updateLayout(Math.max(START_VALUE + mainFrame.getWidth() - WIDTH, 50));
                }
            }
            SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(SCROLL_VALUE));
        });

        setFormat(sideBar, mainPanel, new Insets(0, 0, 0, 0), 0, 0, 0, 0, 20, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);
        SwingUtilities.invokeLater(() -> setFormat(scrollPane, mainPanel, new Insets(0, 0, 0, 0), 1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0));

        mainFrame.revalidate();
        mainFrame.repaint();

        mainFrame.add(tabbedPane);
        mainFrame.setVisible(true);
    }

    public String[][] data = {
            {"1", "Book1", "Author1", "ISBN1", "Available"},
            {"2", "Book2", "Author2", "ISBN2", "Borrowed"},
            {"3", "Book3", "Author3", "ISBN3", "Available"},
            {"4", "Book4", "Author4", "ISBN4", "Borrowed"},
            {"5", "Book5", "Author5", "ISBN5", "Available"},
            {"6", "Book6", "Author6", "ISBN6", "Borrowed"},
            {"7", "Book7", "Author7", "ISBN7", "Available"},
            {"8", "Book8", "Author8", "ISBN8", "Borrowed"},
            {"9", "Book9", "Author9", "ISBN9", "Available"},
            {"10", "Book10", "Author10", "ISBN10", "Borrowed"},
            {"11", "Book11", "Author11", "ISBN11", "Available"},
            {"12", "Book12", "Author12", "ISBN12", "Borrowed"},
            {"13", "Book13", "Author13", "ISBN13", "Available"},
            {"14", "Book14", "Author14", "ISBN14", "Borrowed"},
            {"15", "Book15", "Author15", "ISBN15", "Available"},
            {"16", "Book16", "Author16", "ISBN16", "Borrowed"},
            {"17", "Book17", "Author17", "ISBN17", "Available"},
            {"18", "Book18", "Author18", "ISBN18", "Borrowed"},
            {"19", "Book19", "Author19", "ISBN19", "Available"},
            {"20", "Book20", "Author20", "ISBN20", "Borrowed"}
    };
}