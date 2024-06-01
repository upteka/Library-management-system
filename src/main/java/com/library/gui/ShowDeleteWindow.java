package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;

public class ShowDeleteWindow {
    public ShowDeleteWindow(String id, String title, String author, String isbn) {
        JFrame deleteFrame = new JFrame();
        deleteFrame.setTitle("删除确认");
        deleteFrame.setSize(300, 400);
        deleteFrame.setLocationRelativeTo(null);
        deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel warningLabel = createLabel("确认删除书籍?", 18, Font.BOLD);
        addComponent(panel, warningLabel, gbc, 0, 0);

        JLabel idLabel = createLabel("ID: " + id, 14, Font.PLAIN);
        addComponent(panel, idLabel, gbc, 0, 1);

        JLabel titleLabel = createLabel("书名: " + title, 14, Font.PLAIN);
        addComponent(panel, titleLabel, gbc, 0, 2);

        JLabel authorLabel = createLabel("作者: " + author, 14, Font.PLAIN);
        addComponent(panel, authorLabel, gbc, 0, 3);

        JLabel isbnLabel = createLabel("ISBN: " + isbn, 14, Font.PLAIN);
        addComponent(panel, isbnLabel, gbc, 0, 4);

        JButton confirmButton = createButton("确认删除", new Color(229, 84, 59));
        addComponent(panel, confirmButton, gbc, 0, 5);

        JButton cancelButton = createButton("取消", new Color(72, 74, 77));
        cancelButton.addActionListener(e -> deleteFrame.dispose());
        addComponent(panel, cancelButton, gbc, 1, 5);

        deleteFrame.add(panel);
        deleteFrame.setVisible(true);
    }

    private JLabel createLabel(String text, float size, int style) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", style, (int) size));
        return label;
    }

    private JButton createButton(String text, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(new Color(230, 230, 230));
        button.setForeground(fgColor);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFont(new Font("Serif", Font.BOLD, 14));
        return button;
    }

    private void addComponent(JPanel panel, JComponent component, GridBagConstraints gbc, int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(component, gbc);
    }
}