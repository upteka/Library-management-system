package main.java.com.library.client.gui.view;

import main.java.com.library.client.gui.ShowTable;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static main.java.com.library.client.gui.ShowTable.START_VALUE;
import static main.java.com.library.client.gui.impl.ToolsIMPL.*;

public class WorkPanel extends JPanel {
    private final GridBagLayout LAYOUT = new GridBagLayout();
    private final List<JPanel> subPanels = new ArrayList<>();
    private final Map<JPanel, List<JComponent>> AllComponents = new HashMap<>();
    private final int panelCount;
    private final String[][] data;

    public WorkPanel(int panelCount, String[][] data) {
        this.panelCount = panelCount;
        this.data = data;
        setLayout(LAYOUT);
        initializePanels();
    }

    private void initializePanels() {
        for (int i = 0; i < panelCount; i++) {
            JPanel panel = new JPanel(LAYOUT);
            createSubPanels(panel, i, null);
            subPanels.add(panel);
            add(panel);
        }
    }

    private void createSubPanels(JPanel p, int i, GridBagConstraints gbc) {
        setColor(p, null, new Color(255, 255, 255), BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        List<JComponent> components = new ArrayList<>();

        JToggleButton toggleButton = new JToggleButton(" ");
        toggleButton.setBackground(new Color(234, 235, 236));
        setFormat(toggleButton, p, new Insets(0, 10, 0, 0), 0, 0, 5, 0);
        toggleButton.addActionListener(_ -> toggleButtonAction(toggleButton, p));

        JTextArea idArea = new JTextArea(data[i][0]);
        idArea.setPreferredSize(new Dimension(60, 20));
        idArea.setEditable(false);
        idArea.setBackground(new Color(255, 255, 255, 0));
        setFormat(idArea, p, new Insets(0, 30, 0, 0), 1, 0, 14, Font.PLAIN);

        JTextArea titleArea = new JTextArea(data[i][1]);
        titleArea.setEditable(false);
        titleArea.setPreferredSize(new Dimension(70, 20));
        titleArea.setBackground(new Color(255, 255, 255, 0));
        setFormat(titleArea, p, new Insets(0, 20, 0, 0), 2, 0, 14, Font.PLAIN);

        JTextArea authorArea = new JTextArea(data[i][2]);
        authorArea.setEditable(false);
        authorArea.setPreferredSize(new Dimension(40, 20));
        authorArea.setBackground(new Color(255, 255, 255, 0));
        setFormat(authorArea, p, new Insets(0, 20, 0, 0), 3, 0, 14, Font.PLAIN);

        JTextArea isbnArea = new JTextArea(data[i][3]);
        isbnArea.setEditable(false);
        isbnArea.setPreferredSize(new Dimension(100, 20));
        isbnArea.setBackground(new Color(255, 255, 255, 0));
        setFormat(isbnArea, p, new Insets(0, 20, 0, 0), 4, 0, 14, Font.PLAIN);

        JPanel statusPanel = new JPanel(new GridBagLayout());
        JLabel statusLabel = new JLabel(data[i][4]);
        statusLabel.setForeground(new Color(45, 88, 42));
        statusPanel.setBackground(new Color(222, 234, 221));
        statusPanel.add(statusLabel);
        setFormat(statusPanel, p, new Insets(0, 20, 0, 0), 5, 0, 10, 10, 0, 0);

        JButton borrowButton = new JButton("借阅");
        setColor(borrowButton, new Color(41, 98, 241), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        if (gbc != null) { setFormat(borrowButton, p, gbc, 14, Font.BOLD); }
        else { setFormat(borrowButton, p, new Insets(0, START_VALUE, 0, 0), 6, 0, 20, 10, 14, Font.BOLD); }

        JButton returnButton = new JButton("归还");
        setColor(returnButton, new Color(41, 98, 241), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setFormat(returnButton, p, new Insets(0, 20, 0, 0), 7, 0, 20, 10, 14, Font.BOLD);

        JButton editButton = new JButton("编辑");
        setColor(editButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setFormat(editButton, p, new Insets(0, 20, 0, 0), 8, 0, 20, 10, 14, Font.BOLD);

        JButton deleteButton = new JButton("删除");
        setColor(deleteButton, new Color(229, 84, 59), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setFormat(deleteButton, p, new Insets(0, 20, 0, 20), 9, 0, 20, 10, 14, Font.BOLD);

        editButton.addActionListener(_ -> new EditDialog(ShowTable.mainFrame, data[i][0], data[i][1], data[i][2], data[i][3]));
        deleteButton.addActionListener(_ -> new DeleteDialog(ShowTable.mainFrame, data[i][0], data[i][1], data[i][2], data[i][3]));

        components.add(toggleButton);
        components.add(idArea);
        components.add(titleArea);
        components.add(authorArea);
        components.add(isbnArea);
        components.add(borrowButton);
        components.add(returnButton);
        components.add(editButton);
        components.add(deleteButton);
        AllComponents.put(p, components);

        setFormat(p, this, new Insets(0, 0, 0, 0), 0, i, 1, 0, 0, 40, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);
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
        GridBagConstraints gbc = setFormat(null, null, new Insets(0, magicNumber, 0, 0), 6, 0, 20, 10, 0, 0);

        for (int i = 0; i < subPanels.size(); i++) {
            JPanel panel = subPanels.get(i);
            panel.removeAll();
            createSubPanels(panel, i, gbc);
        }
        revalidate();
        repaint();
    }
}