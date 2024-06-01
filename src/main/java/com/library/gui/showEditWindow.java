package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.gui.createComponent.setFormat;
import static main.java.com.library.gui.mainPage.addFocusListenerToField;

public class showEditWindow {
    public showEditWindow(String id, String title, String author, String isbn) {
        JFrame editFrame = new JFrame();
        editFrame.setTitle("编辑图书信息");
        editFrame.setSize(300, 400);
        editFrame.setLocationRelativeTo(null);
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JTextField idField = new JTextField(id);
        JTextField titleField = new JTextField(title);
        JTextField authorField = new JTextField(author);
        JTextField isbnField = new JTextField(isbn);

        setFormat(idField, panel, new Insets(10, 10, 10, 10),
                0, 0, 0, 0, 2, 0, 50, 20,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(titleField, panel, new Insets(10, 10, 10, 10),
                0, 1, 0, 0, 2, 0, 50, 20,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 1);
        setFormat(authorField, panel, new Insets(10, 10, 10, 10),
                0, 2, 0, 0, 2, 0, 50, 20,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 1, 0);
        setFormat(isbnField, panel, new Insets(10, 10, 10, 10),
                0, 3, 0, 0, 2, 0, 50, 20,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);

        JButton saveButton = new JButton("提交");
        JButton cancelButton = new JButton("取消");
        saveButton.setBackground(new Color(230, 230, 230));
        saveButton.setForeground(new Color(72, 74, 77));
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        cancelButton.setBackground(new Color(230, 230, 230));
        cancelButton.setForeground(new Color(72, 74, 77));
        cancelButton.setBorder(BorderFactory.createEmptyBorder());

        cancelButton.addActionListener(e -> editFrame.dispose());
        addFocusListenerToField(idField);
        addFocusListenerToField(titleField);
        addFocusListenerToField(authorField);
        addFocusListenerToField(isbnField);

        setFormat(saveButton, panel, new Insets(10, 10, 10, 10),
                0, 4, 0, 0, 0, 0, 40, 15,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 14, Font.BOLD);
        setFormat(cancelButton, panel, new Insets(10, 10, 10, 10),
                1, 4, 0, 0, 0, 0, 40, 15,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 14, Font.BOLD);

        editFrame.add(panel);
        editFrame.setVisible(true);
    }
}
