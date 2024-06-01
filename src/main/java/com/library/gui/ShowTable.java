package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.java.com.library.gui.MainPage.customFont;
import static main.java.com.library.gui.MainPage.setCustomFont;

public class ShowTable extends JPanel {
    public List<JButton> editButtons = new ArrayList<>();
    public Map<JButton, GridBagConstraints> gbcMap = new HashMap<>();

    private static final Color PRIMARY_COLOR = new Color(15, 163, 127);
    private static final Color SECONDARY_COLOR = Color.LIGHT_GRAY;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BUTTON_BG_COLOR = new Color(230, 230, 230);
    private static final Color BUTTON_EDIT_COLOR = new Color(72, 74, 77);
    private static final Color BUTTON_DELETE_COLOR = new Color(229, 84, 59);
    private static final Color STATUS_BG_COLOR = new Color(222, 234, 221);
    private static final Color STATUS_FG_COLOR = new Color(110, 141, 108);
    private static final Color TOGGLE_BG_COLOR = new Color(234, 235, 236);
    private static final Color PANEL_SELECTED_COLOR = new Color(230, 232, 234);
    private static final Color PANEL_DEFAULT_COLOR = new Color(255, 255, 255);

    public ShowTable() {
        this.setLayout(new GridBagLayout());
        JPanel topPanel = createTopPanel();
        JPanel workPanel = createWorkPanel();

        JScrollPane scrollPane = new JScrollPane(workPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        addComponent(this, topPanel, new Insets(0, 0, 0, 0), 0, 0, GridBagConstraints.NORTH, GridBagConstraints.BOTH);
        addComponent(this, scrollPane, new Insets(0, 0, 0, 0), 0, 1, GridBagConstraints.SOUTH, GridBagConstraints.BOTH);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new GridBagLayout());
        setCustomFont(topPanel, 12, Font.BOLD);
        return topPanel;
    }

    private JPanel createWorkPanel() {
        JPanel workPanel = new JPanel(new GridBagLayout());
        int n = 20;

        for (int i = 0; i < n; i++) {
            JPanel panel = createItemPanel(i);
            addComponent(workPanel, panel, new Insets(0, 0, 0, 0), 0, i, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 1, 0, 0, 40);
        }

        return workPanel;
    }

    private JPanel createItemPanel(int i) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(PANEL_DEFAULT_COLOR);
        if (i % 2 == 0) {
            panel.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR));
        }

        JToggleButton toggleButton = createToggleButton(i, panel);
        addComponent(panel, toggleButton, new Insets(0, 20, 0, 0), 0, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

        JLabel idLabel = new JLabel("0" + i);
        addComponent(panel, idLabel, new Insets(0, 30, 0, 0), 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

        JLabel titleLabel = new JLabel("三体");
        addComponent(panel, titleLabel, new Insets(0, 30, 0, 0), 2, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

        JLabel authorLabel = new JLabel("刘慈欣");
        addComponent(panel, authorLabel, new Insets(0, 30, 0, 0), 3, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

        JLabel isbnLabel = new JLabel("9787536680188");
        addComponent(panel, isbnLabel, new Insets(0, 30, 0, 0), 4, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

        JPanel statusPanel = createStatusPanel();
        addComponent(panel, statusPanel, new Insets(0, 30, 0, 0), 5, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

        JButton editButton = createEditButton(idLabel, titleLabel, authorLabel, isbnLabel);
        GridBagConstraints gbc_edit = addComponent(panel, editButton, new Insets(0, 150, 0, 0), 6, 0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL);
        gbcMap.put(editButton, gbc_edit);
        editButtons.add(editButton);

        JButton deleteButton = createDeleteButton(idLabel, titleLabel, authorLabel, isbnLabel);
        addComponent(panel, deleteButton, new Insets(0, 10, 0, 20), 7, 0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL);

        return panel;
    }

    private JToggleButton createToggleButton(int i, JPanel panel) {
        JToggleButton toggleButton = new JToggleButton(" ");
        toggleButton.setBackground(TOGGLE_BG_COLOR);
        toggleButton.addActionListener(e -> {
            boolean selected = toggleButton.isSelected();
            toggleButton.setText(selected ? "√" : " ");
            panel.setBackground(selected ? PANEL_SELECTED_COLOR : PANEL_DEFAULT_COLOR);
        });
        return toggleButton;
    }

    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new GridBagLayout());
        JLabel statusLabel = new JLabel("available");
        statusLabel.setForeground(STATUS_FG_COLOR);
        statusPanel.setBackground(STATUS_BG_COLOR);
        statusPanel.add(statusLabel);
        return statusPanel;
    }

    private JButton createEditButton(JLabel idLabel, JLabel titleLabel, JLabel authorLabel, JLabel isbnLabel) {
        JButton editButton = createButton("编辑", BUTTON_BG_COLOR, BUTTON_EDIT_COLOR);
        editButton.addActionListener(e -> new ShowEditWindow(idLabel.getText(), titleLabel.getText(), authorLabel.getText(), isbnLabel.getText()));
        return editButton;
    }

    private JButton createDeleteButton(JLabel idLabel, JLabel titleLabel, JLabel authorLabel, JLabel isbnLabel) {
        JButton deleteButton = createButton("删除", BUTTON_BG_COLOR, BUTTON_DELETE_COLOR);
        deleteButton.addActionListener(e -> new ShowDeleteWindow(idLabel.getText(), titleLabel.getText(), authorLabel.getText(), isbnLabel.getText()));
        return deleteButton;
    }

    private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(customFont.deriveFont(Font.BOLD, 13));
        return button;
    }

    private GridBagConstraints addComponent(JPanel panel, JComponent component, Insets insets, int gridx, int gridy, int anchor, int fill) {
        return addComponent(panel, component, insets, gridx, gridy, anchor, fill, 1, 1, 0, 0);
    }

    private GridBagConstraints addComponent(JPanel panel, JComponent component, Insets insets, int gridx, int gridy, int anchor, int fill, int gridwidth, int gridheight, int weightx, int weighty) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = insets;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        panel.add(component, gbc);
        return gbc;
    }
}