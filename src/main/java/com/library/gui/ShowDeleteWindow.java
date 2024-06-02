package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.gui.CreateComponent.setFormat;
import static main.java.com.library.gui.MainPage.setColor;
import static main.java.com.library.gui.MainPage.setFrame;

public class ShowDeleteWindow {
    public ShowDeleteWindow(String id, String title, String author, String isbn) {
        JFrame deleteFrame = new JFrame("删除确认");
        setFrame(deleteFrame, 300, 400, new BorderLayout(), false, JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JLabel warningLabel = new JLabel("确认删除书籍?");
        JLabel idLabel = new JLabel("ID: " + id);
        JLabel titleLabel = new JLabel("书名: " + title);
        JLabel authorLabel = new JLabel("作者: " + author);
        JLabel isbnLabel = new JLabel("ISBN: " + isbn);
        JButton confirmButton = new JButton("删除");
        JButton cancelButton = new JButton("取消");

        setColor(cancelButton, new Color(72, 74, 77), new Color(230, 230, 230));
        setColor(confirmButton, new Color(229, 84, 59), new Color(230, 230, 230));
        cancelButton.setBorder(BorderFactory.createEmptyBorder());
        confirmButton.setBorder(BorderFactory.createEmptyBorder());

        setFormat(warningLabel, panel, new Insets(10, 10, 10, 10),
                0, 0, 0, 0, 20, 20,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER, 18, Font.BOLD);

        setFormat(idLabel, panel, new Insets(10, 10, 10, 10), 0, 1, 14, 0);
        setFormat(titleLabel, panel, new Insets(10, 10, 10, 10), 0, 2, 14, 0);
        setFormat(authorLabel, panel, new Insets(10, 10, 10, 10), 0, 3, 14, 0);
        setFormat(isbnLabel, panel, new Insets(10, 10, 10, 10), 0, 4, 14, 0);
        setFormat(confirmButton, panel, new Insets(10, 10, 10, 10), 0, 5, 40, 15, 14, Font.BOLD);
        setFormat(cancelButton, panel, new Insets(10, 10, 10, 10), 1, 5, 40, 15, 14, Font.BOLD);

        cancelButton.addActionListener(e -> deleteFrame.dispose());

        deleteFrame.add(panel);
        deleteFrame.setVisible(true);
    }
}