package main.java.com.library.client.gui.view.workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static main.java.com.library.client.gui.impl.ToolsIMPL.*;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.workPanel;

public class TopPanel extends JPanel {
    public TopPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = setFormat(null, null, new Insets(0, 0, 0, 0),
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);


        if (WorkPanel.showType.equals("User")) {
            addButton("用户", gbc, 0, _ -> switchToUserOnly());
            addButton("管理员", gbc, 1, _ -> switchToAdminOnly());
        }
        if (WorkPanel.showType.equals("BorrowRecord")) {
            addButton("正在借阅", gbc, 0, _ -> switchToBorrowing());
            addButton("借阅记录", gbc, 1, _ -> switchToBorrowRecord());
        }
    }

    private void addButton(String text, GridBagConstraints gbc, int gridx, ActionListener action) {
        JButton button = new JButton(text);
        setColor(button, null, new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setCustomFont(button, 16, Font.PLAIN);
        button.addActionListener(action);
        gbc.gridx = gridx;
        add(button, gbc);
    }

    private void switchToUserOnly() {
        WorkPanel.userOnly = true;
        workPanel.updateLayout();
    }

    private void switchToAdminOnly() {
        WorkPanel.userOnly = true;
        workPanel.updateLayout();
    }

    private void switchToBorrowing() {
        WorkPanel.borrowingOnly = true;
        workPanel.updateLayout();
    }

    private void switchToBorrowRecord() {
        WorkPanel.borrowingOnly = false;
        workPanel.updateLayout();
    }
}
