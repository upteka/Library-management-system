package main.java.com.library.client.gui.view.workspace;

import main.java.com.library.common.entity.impl.User;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.impl.ToolsIMPL.*;

public class EditUserDialog extends JDialog {
    EditUserDialog(JFrame parent, User user) {
        super(parent, "编辑图书信息", true);
        setDialog(this, 300, 400, new BorderLayout(), false, JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JTextField idField = new JTextField(user.getId());
        JTextField roleField = new JTextField(user.getRole());
        JTextField usernameField = new JTextField(user.getUsername());
        JTextField passwordField = new JTextField(user.getPassword());
        JTextField emailField = new JTextField(user.getEmail());
        JTextField phoneField = new JTextField(user.getPhone());
        JButton saveButton = new JButton("提交");
        JButton cancelButton = new JButton("取消");

        setColor(saveButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(cancelButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());

        cancelButton.addActionListener(_ -> dispose());

        easySetFormat(idField, panel, 0);
        easySetFormat(roleField, panel, 1);
        easySetFormat(usernameField, panel, 2);
        easySetFormat(passwordField, panel, 3);
        easySetFormat(emailField, panel, 4);
        easySetFormat(phoneField, panel, 5);
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
