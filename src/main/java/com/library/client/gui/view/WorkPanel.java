package main.java.com.library.client.gui.view;

import main.java.com.library.client.gui.ShowTable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static main.java.com.library.client.gui.impl.ToolsIMPL.setColor;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;

public class WorkPanel extends JPanel {
    private final List<JPanel> panels = new ArrayList<>();
    private final List<JButton> edgeButtons = new ArrayList<>();
    private final GridBagLayout layout = new GridBagLayout();

    public WorkPanel(int panelCount, int startValue) {
        setLayout(layout);
        initializePanels(panelCount, startValue);
    }

    private void initializePanels(int panelCount, int startValue) {
        for (int i = 0; i < panelCount; i++) {
            JPanel p = new JPanel(layout);
            setColor(p, null, new Color(255, 255, 255), BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            JToggleButton toggleButton = new JToggleButton(" ");
            toggleButton.setBackground(new Color(234, 235, 236));
            setFormat(toggleButton, p, new Insets(0, 10, 0, 0), 0, 0, 5, 0);

            JTextArea id = new JTextArea("" + i + 1);
            id.setPreferredSize(new Dimension(60, 20));
            id.setEditable(false);
            id.setBackground(new Color(255, 255, 255, 0));
            setFormat(id, p, new Insets(0, 30, 0, 0), 1, 0, 14, Font.PLAIN);

            JTextArea title = new JTextArea("114");
            title.setEditable(false);
            title.setPreferredSize(new Dimension(70, 20));
            title.setBackground(new Color(255, 255, 255, 0));
            setFormat(title, p, new Insets(0, 20, 0, 0), 2, 0, 14, Font.PLAIN);

            JTextArea author = new JTextArea("514");
            author.setEditable(false);
            author.setPreferredSize(new Dimension(40, 20));
            author.setBackground(new Color(255, 255, 255, 0));
            setFormat(author, p, new Insets(0, 20, 0, 0), 3, 0, 14, Font.PLAIN);

            JTextArea isbn = new JTextArea("1919810");
            isbn.setEditable(false);
            isbn.setPreferredSize(new Dimension(100, 20));
            isbn.setBackground(new Color(255, 255, 255, 0));
            setFormat(isbn, p, new Insets(0, 20, 0, 0), 4, 0, 14, Font.PLAIN);

            JPanel statusPanel = new JPanel(new GridBagLayout());
            JLabel status = new JLabel("available");
            status.setForeground(new Color(45, 88, 42));
            statusPanel.setBackground(new Color(222, 234, 221));
            statusPanel.add(status);
            setFormat(statusPanel, p, new Insets(0, 20, 0, 0), 5, 0, 10, 10, 0, 0);

            JButton borrowButton = new JButton("借阅");
            setColor(borrowButton, new Color(41, 98, 241), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
            setFormat(borrowButton, p, new Insets(0, startValue, 0, 0), 6, 0, 20, 10, 14, Font.BOLD);

            JButton returnButton = new JButton("归还");
            setColor(returnButton, new Color(41, 98, 241), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
            setFormat(returnButton, p, new Insets(0, 20, 0, 0), 7, 0, 20, 10, 14, Font.BOLD);

            JButton editButton = new JButton("编辑");
            setColor(editButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
            setFormat(editButton, p, new Insets(0, 20, 0, 0), 8, 0, 20, 10, 14, Font.BOLD);

            JButton deleteButton = new JButton("删除");
            setColor(deleteButton, new Color(229, 84, 59), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
            setFormat(deleteButton, p, new Insets(0, 20, 0, 20), 9, 0, 20, 10, 14, Font.BOLD);

            toggleButton.addActionListener(_ -> toggleButtonAction(toggleButton, p));
            editButton.addActionListener(_ -> new EditDialog(ShowTable.mainFrame, id.getText(), title.getText(), author.getText(), isbn.getText()));
            deleteButton.addActionListener(_ -> new DeleteDialog(ShowTable.mainFrame, id.getText(), title.getText(), author.getText(), isbn.getText()));

            panels.add(p);
            edgeButtons.add(borrowButton);
            setFormat(p, this, new Insets(0, 0, 0, 0), 0, i, 1, 0, 0, 40, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);
        }
    }

    private void toggleButtonAction(JToggleButton toggleButton, JPanel panel) {
        if (toggleButton.isSelected()) {
            toggleButton.setText("√");
            panel.setBackground(new Color(230, 232, 234));
        } else {
            toggleButton.setText(" ");
            panel.setBackground(new Color(255, 255, 255));
        }
    }

    public void updateLayout(int magicNumber) {
        GridBagConstraints gbc = setFormat(null, null, new Insets(0, magicNumber, 0, 0), 6, 0, 20, 8, 0, 0);
        edgeButtons.clear();
        for (JButton edgeButton : edgeButtons) {
            layout.setConstraints(edgeButton, gbc);
        }
    }

    public JPanel getPanel(int index) {
        return panels.get(index);
    }

    public JButton getEditButton(int index) {
        return edgeButtons.get(index);
    }
}