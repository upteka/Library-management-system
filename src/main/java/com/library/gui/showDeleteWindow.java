package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.gui.createComponent.setFormat;

public class showDeleteWindow {
    public showDeleteWindow(String id, String title, String author, String isbn) {
        JFrame deleteFrame = new JFrame();
        deleteFrame.setTitle("删除确认");
        deleteFrame.setSize(300, 400);
        deleteFrame.setLocationRelativeTo(null);
        deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JLabel warningLabel = new JLabel("确认删除书籍?");
        JLabel idLabel = new JLabel("ID: " + id);
        JLabel titleLabel = new JLabel("书名: " + title);
        JLabel authorLabel = new JLabel("作者: " + author);
        JLabel isbnLabel = new JLabel("ISBN: " + isbn);
        JButton confirmButton = new JButton("确认删除");
        JButton cancelButton = new JButton("取消");
        confirmButton.setBackground(new Color(230, 230, 230));
        confirmButton.setForeground(new Color(229, 84, 59));
        confirmButton.setBorder(BorderFactory.createEmptyBorder());
        cancelButton.setBackground(new Color(230, 230, 230));
        cancelButton.setForeground(new Color(72, 74, 77));
        cancelButton.setBorder(BorderFactory.createEmptyBorder());

        cancelButton.addActionListener(e -> deleteFrame.dispose());

        setFormat(warningLabel, panel, new Insets(10, 10, 10, 10),
                0, 0, 0, 0, 0, 0, 20, 20,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER, 18, Font.BOLD);
        setFormat(idLabel, panel, new Insets(10, 10, 10, 10),
                0, 1, 0, 0, 2, 0, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 14, 0);
        setFormat(titleLabel, panel, new Insets(10, 10, 10, 10),
                0, 2, 0, 0, 2, 0, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 14, 0);
        setFormat(authorLabel, panel, new Insets(10, 10, 10, 10),
                0, 3, 0, 0, 2, 0, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 14, 0);
        setFormat(isbnLabel, panel, new Insets(10, 10, 10, 10),
                0, 4, 0, 0, 2, 0, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 14, 0);
        setFormat(confirmButton, panel, new Insets(10, 10, 10, 10),
                0, 5, 0, 0, 0, 0, 40, 15,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 14, Font.BOLD);
        setFormat(cancelButton, panel, new Insets(10, 10, 10, 10),
                1, 5, 0, 0, 0, 0, 40, 15,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 14, Font.BOLD);

        deleteFrame.add(panel);
        deleteFrame.setVisible(true);
    }
}
