package main.java.com.library.client.gui.view.workspace;

import main.java.com.library.common.entity.impl.Book;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.impl.ToolsIMPL.*;

public class EditBookDialog extends JDialog {
    public EditBookDialog(JFrame parent, Book data) {
        super(parent, "编辑图书信息", true);
        setDialog(this, 300, 400, new BorderLayout(), false, JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JTextField idField = new JTextField(data.getId());
        JTextField titleField = new JTextField(data.getTitle());
        JTextField authorField = new JTextField(data.getAuthor());
        JTextField publisherField = new JTextField(data.getPublisher());
        JTextField isbnField = new JTextField(data.getISBN());
        JButton saveButton = new JButton("提交");
        JButton cancelButton = new JButton("取消");

        setColor(saveButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(cancelButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());

        cancelButton.addActionListener(_ -> dispose());

        easySetFormat(idField, panel, 0);
        easySetFormat(titleField, panel, 1);
        easySetFormat(authorField, panel, 2);
        easySetFormat(publisherField, panel, 3);
        easySetFormat(isbnField, panel, 4);
        easySetFormat(buttonPanel, panel, 7);

        setFormat(saveButton, buttonPanel, new Insets(10, 10, 10, 10), 0, 0, 1, 1, 0, 0, 0, 1, 14, Font.BOLD);
        setFormat(cancelButton, buttonPanel, new Insets(10, 10, 10, 10), 1, 0, 1, 1, 0, 0, 0, 1, 14, Font.BOLD);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void easySetFormat(JComponent component, JComponent target, int gridy) {
        if (component instanceof JTextField) {
            addFocusListenerToField((JTextField) component);
            ((JTextField) component).setMargin(new Insets(5, 5, 5, 5));
        }
        setFormat(component, target, new Insets(10, 10, 10, 10), 0, gridy, 50, 20, 0, 0);
    }
}