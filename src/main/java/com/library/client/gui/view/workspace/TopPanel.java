package main.java.com.library.client.gui.view.workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static main.java.com.library.client.gui.impl.ToolsIMPL.*;

public class TopPanel extends JPanel {
    public TopPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = setFormat(null, null, new Insets(0, 0, 0, 0),
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);

        addButton("批量借阅", gbc, 0, _ -> batchBorrowAction());
        addButton("批量归还", gbc, 1, _ -> batchReturnAction());
        addButton("批量编辑", gbc, 2, _ -> batchEditAction());
        addButton("批量删除", gbc, 3, _ -> batchDeleteAction());
    }

    private void addButton(String text, GridBagConstraints gbc, int gridx, ActionListener action) {
        JButton button = new JButton(text);
        setColor(button, null, new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setCustomFont(button, 16, Font.PLAIN);
        button.addActionListener(action);
        gbc.gridx = gridx;
        add(button, gbc);
    }

    private void batchBorrowAction() {
        System.out.println("批量借阅");
    }

    private void batchReturnAction() {
        System.out.println("批量归还");
    }

    private void batchEditAction() {
        System.out.println("批量编辑");
    }

    private void batchDeleteAction() {
        System.out.println("批量删除");
    }
}
