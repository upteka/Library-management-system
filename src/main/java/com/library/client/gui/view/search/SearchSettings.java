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
    private static int currentField = 3;
    private static final String[][] fieldParams = {{"bookID", "书本ID"}, {"title", "书名"}, {"author", "作者"}, {"publisher", "出版社"}, {"ISBN", "ISBN"}};
    private static final JButton[] buttons = {new JButton("全部"), new JButton("ASC"), new JButton("不区分"), new JButton("不去重")};
    private static final JLabel[] labels = {new JLabel("搜索字段"), new JLabel("排序顺序"), new JLabel("是否区分大小写"), new JLabel("是否去重")};

    SearchSettings() {
        setLayout(LAYOUT);

        JPanel fieldNamePanel = new JPanel(LAYOUT);
        JPanel searchOrderPanel = new JPanel(LAYOUT);
        JPanel caseInsensitivePanel = new JPanel(LAYOUT);
        JPanel distinctPanel = new JPanel(LAYOUT);

        areaSelection();
        putIntoPanel(buttons[0], fieldNamePanel, labels[0]);

        switchAction(buttons[1], "ASC", "DESC");
        putIntoPanel(buttons[1], searchOrderPanel, labels[1]);

        switchAction(buttons[2], "区分", "不区分");
        putIntoPanel(buttons[2], caseInsensitivePanel, labels[2]);

        switchAction(buttons[3], "去重", "不去重");
        putIntoPanel(buttons[3], distinctPanel, labels[3]);


        setFormat(fieldNamePanel, this, new Insets(0, 0, 20, 0), 0, 0, 0, 0);
        setFormat(searchOrderPanel, this, new Insets(0, 0, 20, 0), 0, 1, 0, 0);
        setFormat(caseInsensitivePanel, this, new Insets(0, 0, 20, 0), 0, 2, 0, 0);
        setFormat(distinctPanel, this, new Insets(0, 0, 0, 0), 0, 3, 0, 0);
    }

    private void areaSelection() {
        buttons[0].putClientProperty("JButton.buttonType", "roundRect");
        JPopupMenu dropdownMenu = new JPopupMenu();
        for (int i = 0; i < 4; i++) {
            JMenuItem item = new JMenuItem(fieldParams[i][1]);
            int finalI = i;
            item.addActionListener(_ -> {
                buttons[0].setText(fieldParams[finalI][1]);
                currentField = finalI;
            });
            dropdownMenu.add(item);
        }

        buttons[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dropdownMenu.show(buttons[0], e.getX(), e.getY());
            }
        });
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
            System.out.println(button.getText());
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
        return fieldParams[currentField][0];
    }

    public String getSearchOrder() {
        return buttons[1].getText();
    }

    public boolean isCaseInsensitive() {
        return !buttons[2].getText().equals("不区分");
    }

    public boolean isDistinct() {
        return !buttons[3].getText().equals("不去重");
    }
}
