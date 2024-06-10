package main.java.com.library.client.gui.view.workspace;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static main.java.com.library.client.gui.impl.ToolsIMPL.*;

public class EditDialog extends JDialog {
    public EditDialog(JFrame parent, List<String> data) {
        super(parent, "编辑图书信息", true);
        setDialog(this, 300, 400, new BorderLayout(), false, JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JTextField idField = new JTextField(data.get(0));
        JTextField titleField = new JTextField(data.get(1));
        JTextField authorField = new JTextField(data.get(2));
        JTextField isbnField = new JTextField(data.get(3));
        JButton saveButton = new JButton("提交");
        JButton cancelButton = new JButton("取消");

        setColor(saveButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(cancelButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());

        addFocusListenerToField(idField);
        addFocusListenerToField(titleField);
        addFocusListenerToField(authorField);
        addFocusListenerToField(isbnField);
        cancelButton.addActionListener(_ -> dispose());

        setFormat(idField, panel, new Insets(10, 10, 10, 10), 0, 0, 50, 20, 0, 0);
        setFormat(titleField, panel, new Insets(10, 10, 10, 10), 0, 1, 50, 20, 0, 1);
        setFormat(authorField, panel, new Insets(10, 10, 10, 10), 0, 2, 50, 20, 1, 0);
        setFormat(isbnField, panel, new Insets(10, 10, 10, 10), 0, 3, 50, 20, 0, 0);
        setFormat(saveButton, panel, new Insets(10, 10, 10, 10), 0, 4, 40, 15, 14, Font.BOLD);
        setFormat(cancelButton, panel, new Insets(10, 10, 10, 10), 1, 4, 40, 15, 14, Font.BOLD);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}