package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.java.com.library.gui.createComponent.setFormat;
import static main.java.com.library.gui.mainPage.setCustomFont;

public class showTable extends JPanel {
    public List<JButton> editButtons = new ArrayList<>();
    public Map<JButton, GridBagConstraints> gbcMap = new HashMap<>();

    public showTable() {
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        JPanel topPanel = new JPanel(new GridBagLayout());

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
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

            JLabel id = new JLabel("0" + i);
            setFormat(id, panels[i], new Insets(0, 30, 0, 0),
                    1, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

            JLabel title = new JLabel("三体");
            setFormat(title, panels[i], new Insets(0, 30, 0, 0),
                    2, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

            JLabel author = new JLabel("刘慈欣");
            setFormat(author, panels[i], new Insets(0, 30, 0, 0),
                    3, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

            JLabel isbn = new JLabel("9787536680188");
            setFormat(isbn, panels[i], new Insets(0, 30, 0, 0),
                    4, 0, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

            JPanel statusPanel = new JPanel(new GridBagLayout());
            JLabel status = new JLabel("available");
            status.setForeground(new Color(110, 141, 108));
            statusPanel.setBackground(new Color(222, 234, 221));
            statusPanel.add(status);
            setFormat(statusPanel, panels[i], new Insets(0, 30, 0, 0),
                    5, 0, 0, 0, 1, 1, 10, 10,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

            JButton edit = new JButton("编辑");
            edit.setBorder(BorderFactory.createEmptyBorder());
            edit.setBackground(new Color(230, 230, 230));
            edit.setForeground(new Color(72, 74, 77));
            GridBagConstraints gbc_edit = setFormat(edit, panels[i], new Insets(0, 150, 0, 0),
                    6, 0, 0, 0, 1, 1, 20, 8,
                    GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, 13, Font.BOLD);
            gbcMap.put(edit, gbc_edit);
            editButtons.add(edit);

            JButton delete = new JButton("删除");
            delete.setBorder(BorderFactory.createEmptyBorder());
            delete.setBackground(new Color(230, 230, 230));
            delete.setForeground(new Color(229, 84, 59));
            setFormat(delete, panels[i], new Insets(0, 10, 0, 20),
                    7, 0, 0, 0, 1, 1, 20, 8,
                    GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, 13, Font.BOLD);

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

            edit.addActionListener(e -> {
                new showEditWindow(id.getText(), title.getText(), author.getText(), isbn.getText());
            });
            delete.addActionListener(e -> {
                new showDeleteWindow(id.getText(), title.getText(), author.getText(), isbn.getText());
            });

            setFormat(panels[i], workPanel, new Insets(0, 0, 0, 0),
                    0, i, 1, 0, 1, 1, 0, 40,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);
        }

        JScrollPane scrollPane = new JScrollPane(workPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        setFormat(topPanel, this, new Insets(0, 0, 0, 0),
                0, 0, 1, 1, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, 0, 0);
        setFormat(scrollPane, this, new Insets(0, 0, 0, 0),
                0, 1, 1, 10, 1, 1, 0, 0,
                GridBagConstraints.SOUTH, GridBagConstraints.BOTH, 0, 0);
    }
}
