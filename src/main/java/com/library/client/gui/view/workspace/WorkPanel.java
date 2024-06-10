package main.java.com.library.client.gui.view.workspace;

import main.java.com.library.client.gui.MainPage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static main.java.com.library.client.gui.MainPage.dataList;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setColor;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.*;

public class WorkPanel extends JScrollPane {
    private final GridBagLayout LAYOUT = new GridBagLayout();
    public static boolean borrowedOnly = false;
    public static int FONT_SIZE = 14;
    public static JPanel content = null;
    private final List<JPanel> panelList = new ArrayList<>();
    private final List<Boolean> booleanList = new ArrayList<>();


    public WorkPanel() {
        UIManager.put("ScrollBar.showButtons", true);
        content = new JPanel(LAYOUT);
        setViewportView(content);
        getVerticalScrollBar().setUnitIncrement(16);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public void initialize() {
        for (int i = 0; i < dataList.size(); i++) {
            panelList.add(new JPanel(LAYOUT));
            booleanList.add(false);
        }
        updateLayout();
    }

    public void deleteAll() {
        borrowedOnly = false;
        panelList.clear();
        booleanList.clear();
        content.removeAll();
        content = null;
        removeAll();
        System.gc();
    }

    private void createSubPanels(JPanel p, int i, GridBagConstraints gbc, boolean isSelected) {
        setColor(p, null, new Color(255, 255, 255), BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JToggleButton toggleButton = new JToggleButton();
        if (isSelected) {
            toggleButton.setText("√");
            p.setBackground(new Color(230, 232, 234));
            toggleButton.setSelected(true);
        } else {
            toggleButton.setText(" ");
            toggleButton.setBackground(new Color(234, 235, 236));
        }
        setFormat(toggleButton, p, new Insets(0, 15, 0, 0), 0, 0, 5, 0);

        JTextArea idArea = new JTextArea(dataList.get(i).get(0));
        idArea.setEditable(false);
        idArea.setOpaque(false);
        idArea.setPreferredSize(new Dimension(50, 20));
        setFormat(idArea, p, new Insets(0, 40, 0, 0), 1, 0, 0, 0);

        JTextArea titleArea = new JTextArea(dataList.get(i).get(1));
        titleArea.setEditable(false);
        titleArea.setOpaque(false);
        titleArea.setPreferredSize(new Dimension(70, 20));
        setFormat(titleArea, p, new Insets(0, 40, 0, 0), 2, 0, 0, 0);

        JTextArea authorArea = new JTextArea(dataList.get(i).get(2));
        authorArea.setEditable(false);
        authorArea.setOpaque(false);
        authorArea.setPreferredSize(new Dimension(70, 20));
        setFormat(authorArea, p, new Insets(0, 40, 0, 0), 3, 0, 0, 0);

        JTextArea isbnArea = new JTextArea(dataList.get(i).get(3));
        isbnArea.setEditable(false);
        isbnArea.setOpaque(false);
        isbnArea.setPreferredSize(new Dimension(70, 20));
        setFormat(isbnArea, p, new Insets(0, 40, 0, 0), 4, 0, 0, 0);

        JPanel statusPanel = setStatusColor(i);
        setFormat(statusPanel, p, new Insets(0, 0, 0, 0), 5, 0, 10, 10, 0, 0);

        JButton borrowButton = new JButton("借阅");
        setColor(borrowButton, new Color(41, 98, 241), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        if (gbc != null) {
            setFormat(borrowButton, p, gbc, FONT_SIZE, Font.BOLD);
        } else {
            setFormat(borrowButton, p, new Insets(0, START_VALUE, 0, 0), 6, 0, 20, 10, FONT_SIZE, Font.BOLD);
        }

        JButton returnButton = new JButton("归还");
        setColor(returnButton, new Color(41, 98, 241), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setFormat(returnButton, p, new Insets(0, 20, 0, 0), 7, 0, 20, 10, FONT_SIZE, Font.BOLD);

        JButton editButton = new JButton("编辑");
        setColor(editButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setFormat(editButton, p, new Insets(0, 20, 0, 0), 8, 0, 20, 10, FONT_SIZE, Font.BOLD);

        JButton deleteButton = new JButton("删除");
        setColor(deleteButton, new Color(229, 84, 59), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setFormat(deleteButton, p, new Insets(0, 20, 0, 20), 9, 0, 20, 10, FONT_SIZE, Font.BOLD);

        toggleButton.addActionListener(_ -> toggleButtonAction(toggleButton, p, i));
        editButton.addActionListener(_ -> new EditDialog(mainFrame, dataList.get(i)));
        deleteButton.addActionListener(_ -> new DeleteDialog(mainFrame, dataList.get(i)));

        setFormat(p, content, new Insets(0, 0, 0, 0), 0, i, 1, 0, 0, 40, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);
    }

    private JPanel setStatusColor(int i) {
        JPanel statusPanel = new JPanel(LAYOUT);
        JTextArea statusArea = new JTextArea(dataList.get(i).get(4));
        statusArea.setEditable(false);
        statusArea.setOpaque(false);
        if (dataList.get(i).get(4).equals("Available")) {
            statusArea.setForeground(new Color(45, 88, 42));
            statusPanel.setBackground(new Color(222, 234, 221));
        } else {
            statusArea.setForeground(new Color(130, 28, 18));
            statusPanel.setBackground(new Color(251, 228, 224));
        }
        statusPanel.add(statusArea);
        return statusPanel;
    }

    private void toggleButtonAction(JToggleButton toggleButton, JPanel panel, int i) {
        if (toggleButton.isSelected()) {
            toggleButton.setText("√");
            panel.setBackground(new Color(230, 232, 234));
            booleanList.set(i, true);
        } else {
            toggleButton.setText(" ");
            panel.setBackground(new Color(255, 255, 255));
            booleanList.set(i, false);
        }
    }

    public void updateLayout() {
        if (dataList == null) return;
        int newInset = Math.max(START_VALUE + mainFrame.getWidth() - MainPage.WIDTH, 50);
        GridBagConstraints gbc = setFormat(null, null, new Insets(0, newInset, 0, 0), 6, 0, 20, 10, 0, 0);

        content.removeAll();
        for (int i = 0; i < Math.min(pageSize, dataList.size() - (currentPage - 1) * pageSize); i++) {
            int index = (currentPage - 1) * pageSize + i;
            if (borrowedOnly && dataList.get(index).get(4).equals("Available")) {
                continue;
            }
            panelList.get(index).removeAll();
            createSubPanels(panelList.get(index), index, gbc, booleanList.get(index));
        }

        revalidate();
        repaint();
    }
}