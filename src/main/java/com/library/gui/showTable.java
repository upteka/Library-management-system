package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.gui.createComponent.setFormat;
import static main.java.com.library.gui.mainPage.setCustomFont;

public class showTable extends JPanel {
    public showTable() {
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new GridBagLayout());

        topPanel.setPreferredSize(new Dimension(800, 60));
        topPanel.add(new JLabel("一些操作"));
        setCustomFont(topPanel, 12, Font.BOLD);

        JPanel workPanel = new JPanel(new GridBagLayout());

        int n = 20;
        JPanel[] panels = new JPanel[n];
        JToggleButton[] toggleButtons = new JToggleButton[n];

        for (int i = 0; i < n; i++) {
            panels[i] = new JPanel(new GridBagLayout());
            panels[i].setBackground(new Color(255, 255, 255));
            if (i % 2 == 0) {
                panels[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            }

            toggleButtons[i] = new JToggleButton(" ");
            toggleButtons[i].setBackground(new Color(234, 235, 236));
            setFormat(toggleButtons[i], panels[i], new Insets(0, 20, 0, 0),
                    0, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.CENTER, 0, 0);

            JLabel id = new JLabel("12345");
            setFormat(id, panels[i], new Insets(0, 30, 0, 0),
                    1, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.CENTER, 0, 0);

            JLabel title = new JLabel("三体");
            setFormat(title, panels[i], new Insets(0, 30, 0, 0),
                    2, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.CENTER, 0, 0);

            JLabel author = new JLabel("刘慈欣");
            setFormat(author, panels[i], new Insets(0, 30, 0, 0),
                    3, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.CENTER, 0, 0);

            JLabel isbn = new JLabel("9787536680188");
            setFormat(isbn, panels[i], new Insets(0, 30, 0, 0),
                    4, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.CENTER, 0, 0);

            JPanel statusPanel = new JPanel(new GridBagLayout());
            JLabel status = new JLabel("available");
            status.setForeground(new Color(110, 141, 108));
            statusPanel.setBackground(new Color(222, 234, 221));
            statusPanel.add(status);
            setFormat(statusPanel, panels[i], new Insets(0, 30, 0, 0),
                    5, 0, 0, 0, 1, 1, 10, 10,
                    GridBagConstraints.CENTER, 0, 0);

            JButton edit = new JButton("编辑");
            edit.setBorder(BorderFactory.createEmptyBorder());
            edit.setBackground(new Color(230, 230, 230));
            edit.setForeground(new Color(72, 74, 77));
            setFormat(edit, panels[i], new Insets(0, 150, 0, 0),
                    6, 0, 0, 0, 1, 1, 20, 8,
                    GridBagConstraints.CENTER, 13, Font.BOLD);

            JButton delete = new JButton("删除");
            delete.setBorder(BorderFactory.createEmptyBorder());
            delete.setBackground(new Color(230, 230, 230));
            delete.setForeground(new Color(229, 84, 59));
            setFormat(delete, panels[i], new Insets(0, 10, 0, 0),
                    7, 0, 0, 0, 1, 1, 20, 8,
                    GridBagConstraints.CENTER, 13, Font.BOLD);

            int finalI = i;
            toggleButtons[i].addActionListener(e -> {
                boolean selected = toggleButtons[finalI].isSelected();
                if (selected) {
                    toggleButtons[finalI].setText("√");
                    panels[finalI].setBackground(new Color(230, 232, 234));
                } else {
                    toggleButtons[finalI].setText(" ");
                    panels[finalI].setBackground(new Color(255, 255, 255));
                }
            });

            setFormat(panels[i], workPanel, new Insets(0, 0, 0, 0),
                    0, i, 1, 0, 1, 1, 0, 40,
                    GridBagConstraints.NORTH, 0, 0);
        }

        JScrollPane scrollPane = new JScrollPane(workPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        this.add(topPanel, BorderLayout.PAGE_START);
        this.add(scrollPane);
    }
}
