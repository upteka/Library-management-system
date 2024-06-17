package main.java.com.library.client.gui.view.search;

import main.java.com.library.client.gui.effects.FadeEffect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;

public class SearchSettings extends JPanel {
    private static final GridBagLayout LAYOUT = new GridBagLayout();
    private static int currentField = 0;
    private static int currentSort = 0;
    private static final String[][] fieldNameParams = {{"title", "书名"}, {"bookID", "书本ID"}, {"author", "作者"}, {"publisher", "出版社"}, {"ISBN", "ISBN"}};
    private static final String[][] sortFieldParams = {{"", "搜索字段"}, {"count", "总量"}, {"availableCount", "余量"}, {"status", "可借出"}};
    private static final JButton[] buttons = {
            new JButton("书名"),
            new JButton("排序字段"),
            new JButton("ASC"),
            new JButton("区分"),
            new JButton("去重")
    };
    private static final JLabel[] labels = {
            new JLabel("搜索字段"),
            new JLabel("排序字段"),
            new JLabel("排序顺序"),
            new JLabel("是否区分大小写"),
            new JLabel("是否去重")
    };

    public SearchSettings() {
        setLayout(LAYOUT);

        JPanel fieldNamePanel = new JPanel(LAYOUT);
        JPanel searchFieldPanel = new JPanel(LAYOUT);
        JPanel searchOrderPanel = new JPanel(LAYOUT);
        JPanel caseInsensitivePanel = new JPanel(LAYOUT);
        JPanel distinctPanel = new JPanel(LAYOUT);

        createDropdown(buttons[0], fieldNameParams, 0, fieldNamePanel, labels[0]);
        createDropdown(buttons[1], sortFieldParams, 1, searchFieldPanel, labels[1]);

        switchAction(buttons[2], "ASC", "DESC");
        putIntoPanel(buttons[2], searchOrderPanel, labels[2]);

        switchAction(buttons[3], "区分", "不区分");
        putIntoPanel(buttons[3], caseInsensitivePanel, labels[3]);

        switchAction(buttons[4], "去重", "不去重");
        putIntoPanel(buttons[4], distinctPanel, labels[4]);

        setFormat(fieldNamePanel, this, new Insets(0, 0, 20, 0), 0, 0, 0, 0);
        setFormat(searchFieldPanel, this, new Insets(0, 0, 20, 0), 0, 1, 0, 0);
        setFormat(searchOrderPanel, this, new Insets(0, 0, 20, 0), 0, 2, 0, 0);
        setFormat(caseInsensitivePanel, this, new Insets(0, 0, 20, 0), 0, 3, 0, 0);
        setFormat(distinctPanel, this, new Insets(0, 0, 0, 0), 0, 4, 0, 0);
    }

    private void createDropdown(JButton button, String[][] params, int buttonIndex, JPanel panel, JLabel label) {
        button.putClientProperty("JButton.buttonType", "roundRect");
        JPopupMenu dropdownMenu = new JPopupMenu();
        for (int i = 0; i < params.length; i++) {
            JMenuItem item = new JMenuItem(params[i][1]);
            int finalI = i;
            item.addActionListener(_ -> {
                button.setText(params[finalI][1]);
                if (buttonIndex == 0) {
                    currentField = finalI;
                } else {
                    currentSort = finalI;
                }
            });
            dropdownMenu.add(item);
        }

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dropdownMenu.show(button, e.getX(), e.getY());
            }
        });

        putIntoPanel(button, panel, label);
    }

    private void putIntoPanel(JComponent component, JPanel panel, JLabel label) {
        setFormat(label, panel, new Insets(0, 0, 0, 0),
                0, 0, 1, 0, 0, 0, 0, 1, 18, Font.BOLD);
        setFormat(component, panel, new Insets(0, 200, 0, 0), 1, 0, 0, 0);
    }

    private void switchAction(JButton button, String status1, String status2) {
        button.putClientProperty("JButton.buttonType", "roundRect");
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
        button.addActionListener(_ -> {
            if (button.getText().equals(status1)) {
                button.setText(status2);
            } else {
                button.setText(status1);
            }
        });
    }

    public void fadeIn() {
        for (JButton button : buttons) {
            FadeEffect.applyFadeEffect(button, true, 1, 0.2f, null);
        }
        for (JLabel label : labels) {
            FadeEffect.applyFadeEffect(label, true, 1, 0.2f, null);
        }
    }

    public void fadeOut() {
        for (JButton button : buttons) {
            FadeEffect.applyFadeEffect(button, false, 1, 0.2f, null);
        }
        for (JLabel label : labels) {
            FadeEffect.applyFadeEffect(label, false, 1, 0.2f, null);
        }
    }

    public String getFieldName() {
        return fieldNameParams[currentField][0];
    }

    public String getSortField() {
        if (currentSort == 0) return fieldNameParams[currentField][0];
        return sortFieldParams[currentSort][0];
    }

    public boolean isAscendingOrder() {
        return buttons[2].getText().equals("ASC");
    }

    public boolean isCaseInsensitive() {
        return buttons[3].getText().equals("不区分");
    }

    public boolean isDistinct() {
        return buttons[4].getText().equals("去重");
    }
}