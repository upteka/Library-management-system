package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;

public class ShowEditWindow {
    public ShowEditWindow(String id, String title, String author, String isbn) {
        JFrame editFrame = new JFrame();
        editFrame.setTitle("编辑图书信息");
        editFrame.setSize(300, 400);
        editFrame.setLocationRelativeTo(null);
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JTextField idField = createTextField(id);
        addComponent(panel, idField, gbc, 0, 0, 2);

        JTextField titleField = createTextField(title);
        addComponent(panel, titleField, gbc, 0, 1, 2);

        JTextField authorField = createTextField(author);
        addComponent(panel, authorField, gbc, 0, 2, 2);

        JTextField isbnField = createTextField(isbn);
        addComponent(panel, isbnField, gbc, 0, 3, 2);

        JButton saveButton = createButton("提交", new Color(72, 74, 77));
        addComponent(panel, saveButton, gbc, 0, 4, 1);

        JButton cancelButton = createButton("取消", new Color(72, 74, 77));
        cancelButton.addActionListener(e -> editFrame.dispose());
        addComponent(panel, cancelButton, gbc, 1, 4, 1);

        editFrame.add(panel);
        editFrame.setVisible(true);
    }

    private JTextField createTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setFont(new Font("Serif", Font.PLAIN, 14));
        return textField;
    }

    private JButton createButton(String text, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(new Color(230, 230, 230));
        button.setForeground(fgColor);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFont(new Font("Serif", Font.BOLD, 14));
        return button;
    }

    private void addComponent(JPanel panel, JComponent component, GridBagConstraints gbc, int gridx, int gridy, int gridwidth) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        panel.add(component, gbc);
    }
}