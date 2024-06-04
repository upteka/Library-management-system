package main.java.com.library.client.gui;

import main.java.com.library.client.gui.view.ClientPanel;
import main.java.com.library.client.gui.view.WorkPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static main.java.com.library.client.gui.impl.ToolsIMPL.*;

public class ShowTable {
    public static final int startValue = 300;
    public static JFrame mainFrame = new JFrame("图书管理系统");

    public ShowTable() {
        int width = 1100, height = 800;
        setFrame(mainFrame, width, height, new BorderLayout(), true, JFrame.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(new Dimension(width, height));

        JTabbedPane tabbedPane = new JTabbedPane();
        setCustomFont(tabbedPane, 12, Font.PLAIN);

        JPanel tablePanel = new JPanel(new GridBagLayout());
        tabbedPane.addTab("工作区", tablePanel);

        JPanel clientPanel = new ClientPanel(new GridBagLayout());
        tabbedPane.addTab("用户管理", clientPanel);

        JPanel topPanel = new JPanel(null);
        topPanel.setBounds(0, 0, width, 100);
        JTextField searchField = new JTextField();
        searchField.setColumns(10);
        searchField.setBounds(20, 20, 200, 25);
        topPanel.add(searchField, BorderLayout.WEST);

        WorkPanel workPanel = new WorkPanel(20, startValue);
        JScrollPane scrollPane = new JScrollPane(workPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));

        setFormat(topPanel, tablePanel, new Insets(0, 0, 0, 0), 0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(scrollPane, tablePanel, new Insets(0, 0, 0, 0), 0, 1, 1, 10, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, 0, 0);

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int magicNumber = startValue + mainFrame.getWidth() - width;
                System.out.println("" + magicNumber);
                workPanel.updateLayout(Math.max(magicNumber, 30));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });

        mainFrame.addWindowStateListener(e -> {
            if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                int magicNumber = startValue + mainFrame.getWidth() - width;
                workPanel.updateLayout(Math.max(magicNumber, 30));
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });

        mainFrame.add(tabbedPane);
        mainFrame.setVisible(true);
    }
}