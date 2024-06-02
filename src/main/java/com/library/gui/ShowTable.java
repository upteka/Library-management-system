package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static main.java.com.library.gui.CreateComponent.setFormat;
import static main.java.com.library.gui.MainPage.*;

public class ShowTable {
    public ShowTable() {
        int width = 1100, height = 800;
        JFrame mainFrame = new JFrame("图书管理系统");
        setFrame(mainFrame, width, height, new BorderLayout(), true, JFrame.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(new Dimension(width, height));

        JTabbedPane tabbedPane = new JTabbedPane();
        setCustomFont(tabbedPane, 12, Font.PLAIN);
        JPanel tablePanel = new JPanel(new GridBagLayout());
        tabbedPane.addTab("Tab 1", tablePanel);

        GridBagLayout layout = new GridBagLayout();
        JPanel topPanel = new JPanel(layout);
        JPanel workPanel = new JPanel(layout);

        int n = 20;
        int startValue = 400;
        JButton[] edit = new JButton[n];
        for (int i = 0; i < n; i++) {
            JPanel p = new JPanel(layout);
            p.setBackground(new Color(255, 255, 255));
            p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            JToggleButton toggleButton = new JToggleButton(" ");
            toggleButton.setBackground(new Color(234, 235, 236));
            setFormat(toggleButton, p, new Insets(0, 10, 0, 0),
                    0, 0, 0, 0);

            JTextArea id = new JTextArea("" + i);
            id.setPreferredSize(new Dimension(60, 20));
            setFormat(id, p, new Insets(0, 20, 0, 0),
                    1, 0, 0, 0);

            JTextArea title = new JTextArea("978753668018897875366801889787536680188");
            title.setPreferredSize(new Dimension(70, 20));
            setFormat(title, p, new Insets(0, 20, 0, 0),
                    2, 0, 0, 0);

            JTextArea author = new JTextArea("9787536680188978753668018897875366801889787536680188");
            author.setPreferredSize(new Dimension(40, 20));
            setFormat(author, p, new Insets(0, 20, 0, 0),
                    3, 0, 0, 0);

            JTextArea isbn = new JTextArea("978753668018897875366801889787536680188");
            isbn.setPreferredSize(new Dimension(100, 20));
            setFormat(isbn, p, new Insets(0, 20, 0, 0),
                    4, 0, 0, 0);

            JPanel statusPanel = new JPanel(new GridBagLayout());
            JLabel status = new JLabel("available");
            status.setForeground(new Color(45, 88, 42));
            statusPanel.setBackground(new Color(222, 234, 221));
            statusPanel.add(status);
            setFormat(statusPanel, p, new Insets(0, 20, 0, 0),
                    5, 0, 10, 10, 0, 0);

            edit[i] = new JButton("编辑");
            edit[i].setBorder(BorderFactory.createEmptyBorder());
            setColor(edit[i], new Color(72, 74, 77), new Color(230, 230, 230));
            setFormat(edit[i], p, new Insets(0, startValue, 0, 0),
                    6, 0, 20, 8, 13, Font.BOLD);

            JButton delete = new JButton("删除");
            delete.setBorder(BorderFactory.createEmptyBorder());
            setColor(delete, new Color(229, 84, 59), new Color(230, 230, 230));
            setFormat(delete, p, new Insets(0, 20, 0, 20),
                    7, 0, 20, 8, 13, Font.BOLD);

            toggleButton.addActionListener(_ -> {
                if (toggleButton.isSelected()) {
                    toggleButton.setText("√");
                    p.setBackground(new Color(230, 232, 234));
                } else {
                    toggleButton.setText(" ");
                    p.setBackground(new Color(255, 255, 255));
                }
            });

            edit[i].addActionListener(_ -> new ShowEditWindow(id.getText(), title.getText(), author.getText(), isbn.getText()));
            delete.addActionListener(_ -> new ShowDeleteWindow(id.getText(), title.getText(), author.getText(), isbn.getText()));

            setFormat(p, workPanel, new Insets(0, 0, 0, 0),
                    0, i, 1, 0, 0, 40,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);
        }

        JScrollPane scrollPane = new JScrollPane(workPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        setFormat(topPanel, tablePanel, new Insets(0, 0, 0, 0),
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, 0, 0);
        setFormat(scrollPane, tablePanel, new Insets(0, 0, 0, 0),
                0, 1, 1, 10, 0, 0,
                GridBagConstraints.SOUTH, GridBagConstraints.BOTH, 0, 0);

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                workPanel.revalidate();
                workPanel.repaint();
                int magicNumber = startValue + mainFrame.getWidth() - width;
                System.out.println("原尺寸 " + mainFrame.getWidth());
                System.out.println("w值 " + magicNumber);
                GridBagConstraints gbc = setFormat(null, null, new Insets(0, magicNumber, 0, 0),
                        6, 0, 20, 8, 0, 0);
                for (int i = 0; i < 20; i++) {
                    layout.setConstraints(edit[i], gbc);
                }
                workPanel.revalidate();
                workPanel.repaint();
            }
        });

        mainFrame.addWindowStateListener(e -> {
            if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                int magicNumber = startValue + mainFrame.getWidth() - width;
                GridBagConstraints gbc = setFormat(null, null, new Insets(0, magicNumber, 0, 0),
                        6, 0, 20, 8, 0, 0);
                for (int i = 0; i < 20; i++) {
                    layout.setConstraints(edit[i], gbc);
                }
                workPanel.revalidate();
                workPanel.repaint();
            }
        });

        mainFrame.add(tabbedPane);
        mainFrame.setVisible(true);
    }
}
