package main.java.com.library.client.gui.view.workspace;

import main.java.com.library.common.entity.impl.User;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static main.java.com.library.client.gui.LoginPage.*;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.effects.NotificationUtil.Notification;
import static main.java.com.library.client.gui.impl.ToolsIMPL.*;
import static main.java.com.library.client.gui.view.workspace.BottomPanel.refreshPage;
import static main.java.com.library.client.gui.view.workspace.WorkPanel.setTextArea;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class EditUserDialog extends JDialog {
    EditUserDialog(JFrame parent, User user) {
        super(parent, "编辑用户信息", true);
        setDialog(this, 300, 400, new BorderLayout(), false, JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JTextField usernameField = setArea(user.getUsername(), 200);
        JTextField passwordField = setArea(user.getPassword(), 200);
        JTextField emailField = setArea(user.getEmail(), 200);
        JTextField phoneField = setArea(user.getPhone(), 200);
        JButton saveButton = new JButton("提交");
        JButton cancelButton = new JButton("取消");

        setColor(saveButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(cancelButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());

        easySetFormat(usernameField, panel, 0, "用户名");
        easySetFormat(passwordField, panel, 1, "密码");
        easySetFormat(emailField, panel, 2, "邮箱");
        easySetFormat(phoneField, panel, 3, "手机号");
        setFormat(buttonPanel, panel, new Insets(5, 5, 5, 5), 0, 4, 1, 1, 0, 20, 0, 1, 14, Font.BOLD);

        setFormat(saveButton, buttonPanel, new Insets(10, 10, 10, 10), 0, 0, 1, 1, 0, 15, 0, 1, 14, Font.BOLD);
        setFormat(cancelButton, buttonPanel, new Insets(10, 10, 10, 10), 1, 0, 1, 1, 0, 15, 0, 1, 14, Font.BOLD);

        cancelButton.addActionListener(_ -> dispose());
        saveButton.addActionListener(_ -> {
            try {
                if (!isEmail(emailField.getText(), mainFrame)) return;
                if (!isPhone(phoneField.getText(), mainFrame)) return;
                user.setUsername(usernameField.getText());
                user.setPassword(passwordField.getText());
                user.setEmail(emailField.getText());
                user.setPhone(phoneField.getText());
                clientUtil.sendRequest(packRequest("update", user, "update", authResponse.getJwtToken()));
                ResponsePack<?> response = clientUtil.receiveResponse();
                if (response.isSuccess()) {
                    Notification(this, "修改成功！");
                    refreshPage();
                } else
                    Notification(this, "修改失败 " + response.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void easySetFormat(JComponent component, JComponent target, int gridy, String title) {
        JPanel p = new JPanel(new GridBagLayout());
        setFormat(setTextArea(title, 70, 99), p, new Insets(5, 5, 5, 5), 0, 0, 14, Font.BOLD);
        setFormat(component, p, new Insets(5, 5, 5, 5), 1, 0, 14, 0);
        setFormat(p, target, new Insets(5, 5, 5, 5), 0, gridy, 1, 0, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
    }

    private JTextField setArea(String content, int width) {
        JTextField area = new JTextField(content);
        area.setPreferredSize(new Dimension(width, 20));
        return area;
    }
}
