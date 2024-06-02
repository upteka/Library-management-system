package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.gui.CreateComponent.setFormat;
import static main.java.com.library.gui.MainPage.*;

public class ShowEditWindow {
    public ShowEditWindow(String id, String title, String author, String isbn) {
        JFrame editFrame = new JFrame("编辑图书信息");
        setFrame(editFrame, 300, 400, new BorderLayout(), false, JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JTextField idField = new JTextField(id);
        JTextField titleField = new JTextField(title);
        JTextField authorField = new JTextField(author);
        JTextField isbnField = new JTextField(isbn);

        JButton saveButton = new JButton("提交");
        JButton cancelButton = new JButton("取消");
        setColor(saveButton, new Color(72, 74, 77), new Color(230, 230, 230));
        setColor(cancelButton, new Color(72, 74, 77), new Color(230, 230, 230));
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        cancelButton.setBorder(BorderFactory.createEmptyBorder());

        addFocusListenerToField(idField);
        addFocusListenerToField(titleField);
        addFocusListenerToField(authorField);
        addFocusListenerToField(isbnField);
        cancelButton.addActionListener(e -> editFrame.dispose());

        setFormat(idField, panel, new Insets(10, 10, 10, 10), 0, 0, 50, 20, 0, 0);
        setFormat(titleField, panel, new Insets(10, 10, 10, 10), 0, 1, 50, 20, 0, 1);
        setFormat(authorField, panel, new Insets(10, 10, 10, 10), 0, 2, 50, 20, 1, 0);
        setFormat(isbnField, panel, new Insets(10, 10, 10, 10), 0, 3, 50, 20, 0, 0);

        setFormat(saveButton, panel, new Insets(10, 10, 10, 10), 0, 4, 40, 15, 14, Font.BOLD);
        setFormat(cancelButton, panel, new Insets(10, 10, 10, 10), 1, 4, 40, 15, 14, Font.BOLD);

        editFrame.add(panel);
        editFrame.setVisible(true);
    }
}
