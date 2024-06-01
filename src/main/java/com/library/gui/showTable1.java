package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static main.java.com.library.gui.createComponent.setFormat;
import static main.java.com.library.gui.mainPage.setCustomFont;

public class showTable1 {
    public showTable1() {
        JFrame mainFrame = new JFrame("图书管理系统");
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setBackground(new Color(255, 255, 255));
        mainFrame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        JPanel tablePanel = new JPanel(new GridBagLayout());
        tabbedPane.addTab("Tab 1", tablePanel);
        setCustomFont(tabbedPane, 12, Font.PLAIN);

        GridBagLayout layout = new GridBagLayout();
        JPanel topPanel = new JPanel(layout);
        JPanel workPanel = new JPanel(layout);

        int n = 20;
        JButton[] edit = new JButton[n];

        for (int i = 0; i < n; i++) {
            JPanel p = new JPanel(layout);
            p.setBackground(new Color(255, 255, 255));
            p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            JToggleButton toggleButton = new JToggleButton(" ");
            toggleButton.setBackground(new Color(234, 235, 236));
            setFormat(toggleButton, p, new Insets(0, 10, 0, 0),
                    0, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

            JLabel id = new JLabel("0" + i);
            setFormat(id, p, new Insets(0, 30, 0, 0),
                    1, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

            JLabel title = new JLabel("三体");
            setFormat(title, p, new Insets(0, 30, 0, 0),
                    2, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

            JLabel author = new JLabel("刘慈欣");
            setFormat(author, p, new Insets(0, 30, 0, 0),
                    3, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

            JLabel isbn = new JLabel("9787536680188");
            setFormat(isbn, p, new Insets(0, 30, 0, 0),
                    4, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

            JPanel statusPanel = new JPanel(new GridBagLayout());
            JLabel status = new JLabel("available");
            status.setForeground(new Color(45, 88, 42));
            statusPanel.setBackground(new Color(222, 234, 221));
            statusPanel.add(status);
            setFormat(statusPanel, p, new Insets(0, 30, 0, 0),
                    5, 0, 0, 0, 1, 1, 10, 10,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

            edit[i] = new JButton("编辑");
            edit[i].setBorder(BorderFactory.createEmptyBorder());
            edit[i].setBackground(new Color(230, 230, 230));
            edit[i].setForeground(new Color(72, 74, 77));
            setFormat(edit[i], p, new Insets(0, 150, 0, 0),
                    6, 0, 0, 0, 1, 1, 20, 8,
                    GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, 13, Font.BOLD);

            JButton delete = new JButton("删除");
            delete.setBorder(BorderFactory.createEmptyBorder());
            delete.setBackground(new Color(230, 230, 230));
            delete.setForeground(new Color(229, 84, 59));
            setFormat(delete, p, new Insets(0, 20, 0, 20),
                    7, 0, 0, 0, 1, 1, 20, 8,
                    GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, 13, Font.BOLD);

            toggleButton.addActionListener(_ -> {
                if (toggleButton.isSelected()) {
                    toggleButton.setText("√");
                    p.setBackground(new Color(230, 232, 234));
                } else {
                    toggleButton.setText(" ");
                    p.setBackground(new Color(255, 255, 255));
                }
            });

            edit[i].addActionListener(_ -> new showEditWindow(id.getText(), title.getText(), author.getText(), isbn.getText()));
            delete.addActionListener(_ -> new showDeleteWindow(id.getText(), title.getText(), author.getText(), isbn.getText()));

            setFormat(p, workPanel, new Insets(0, 0, 0, 0),
                    0, i, 1, 0, 1, 1, 0, 40,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);
        }

        JScrollPane scrollPane = new JScrollPane(workPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        setFormat(topPanel, tablePanel, new Insets(0, 0, 0, 0),
                0, 0, 1, 1, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, 0, 0);
        setFormat(scrollPane, tablePanel, new Insets(0, 0, 0, 0),
                0, 1, 1, 10, 1, 1, 0, 0,
                GridBagConstraints.SOUTH, GridBagConstraints.BOTH, 0, 0);

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = mainFrame.getWidth() + 150 - 800;
                if (w > 20) {
                    System.out.println(w);
                    GridBagConstraints gbc = setFormat(null, null,
                            new Insets(0, w, 0, 0),
                            6, 0, 0, 0, 1, 1, 20, 8,
                            GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, 13, Font.BOLD);
                    for (int i = 0; i < 20; i++) {
                        layout.setConstraints(edit[i], gbc);
                    }
                    workPanel.revalidate();
                    workPanel.repaint();
                }
            }
        });

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (mainFrame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    System.out.println("Full screen mode entered.");
                } else {
                    if (mainFrame.getWidth() + 150 - 800 <= 20) {
                        System.out.println("Full screen mode exited.");
                    }
                }
            }
        });

        mainFrame.add(tabbedPane);
        mainFrame.setVisible(true);
    }
}
