package main.java.com.library.client.gui.view.workspace;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static main.java.com.library.client.gui.impl.ToolsIMPL.*;

public class DeleteDialog extends JDialog {
    public DeleteDialog(JFrame parent, List<String> data) {
        super(parent, "删除确认", true);
        setDialog(this, 300, 400, new BorderLayout(), false, JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JLabel warningLabel = new JLabel("确认删除书籍?");
        JLabel idLabel = new JLabel("ID: " + data.get(0));
        JLabel titleLabel = new JLabel("书名: " + data.get(1));
        JLabel authorLabel = new JLabel("作者: " + data.get(2));
        JLabel isbnLabel = new JLabel("ISBN: " + data.get(3));
        JButton confirmButton = new JButton("删除");
        JButton cancelButton = new JButton("取消");

        setColor(cancelButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(confirmButton, new Color(229, 84, 59), new Color(230, 230, 230), BorderFactory.createEmptyBorder());

        setFormat(warningLabel, panel, new Insets(10, 10, 10, 10), 0, 0, 0, 0, 20, 20, GridBagConstraints.NORTH, GridBagConstraints.CENTER, 18, Font.BOLD);
        setFormat(idLabel, panel, new Insets(10, 10, 10, 10), 0, 1, 14, 0);
        setFormat(titleLabel, panel, new Insets(10, 10, 10, 10), 0, 2, 14, 0);
        setFormat(authorLabel, panel, new Insets(10, 10, 10, 10), 0, 3, 14, 0);
        setFormat(isbnLabel, panel, new Insets(10, 10, 10, 10), 0, 4, 14, 0);
        setFormat(confirmButton, panel, new Insets(10, 10, 10, 10), 0, 5, 40, 15, 14, Font.BOLD);
        setFormat(cancelButton, panel, new Insets(10, 10, 10, 10), 1, 5, 40, 15, 14, Font.BOLD);

        cancelButton.addActionListener(_ -> dispose());

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}