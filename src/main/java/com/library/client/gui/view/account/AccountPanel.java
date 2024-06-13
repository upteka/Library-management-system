package main.java.com.library.client.gui.view.account;

import main.java.com.library.client.gui.LoginPage;
import main.java.com.library.common.entity.impl.User;
import main.java.com.library.common.network.JwtUtil;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static main.java.com.library.client.gui.LoginPage.*;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setCustomFont;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class AccountPanel extends JPanel {
    private static final GridBagLayout LAYOUT = new GridBagLayout();
    private static int passwordStatus;
    private static String confirmPassword;

    private static JTextField usernameField = createTextField(currentUser.getUsername());
    private static JTextField emailField = createTextField(currentUser.getEmail());
    private static JTextField phoneField = createTextField(currentUser.getPhone());
    private static JTextField passwordField = createTextField("请输入旧密码");

    public AccountPanel() {
        setLayout(LAYOUT);

        JPanel titlePanel = new JPanel(LAYOUT);
        JPanel editAccountPanel = new JPanel(LAYOUT);

        JButton passwordButton = setPasswordButton(passwordField);
        JButton saveButton = new JButton("保存更改");
        saveButton.putClientProperty("JButton.buttonType", "roundRect");
        JPanel userPanel = new JPanel(LAYOUT);
        JPanel emailPanel = new JPanel(LAYOUT);
        JPanel phonePanel = new JPanel(LAYOUT);
        JPanel passwordPanel = new JPanel(LAYOUT);

        setFormat(new JLabel("用户名"), editAccountPanel, new Insets(0, 50, 10, 0),
                0, 0, 18, Font.BOLD);
        setFormat(userPanel, editAccountPanel, new Insets(0, 0, 0, 430),
                0, 1, 0, 0);
        setFormat(new JLabel("邮箱"), editAccountPanel, new Insets(0, 50, 10, 0),
                0, 2, 18, Font.BOLD);
        setFormat(emailPanel, editAccountPanel, new Insets(0, 0, 0, 430),
                0, 3, 0, 0);
        setFormat(new JLabel("手机号"), editAccountPanel, new Insets(0, 50, 10, 0),
                0, 4, 18, Font.BOLD);
        setFormat(phonePanel, editAccountPanel, new Insets(0, 0, 0, 250),
                0, 5, 0, 0);
        setFormat(new JLabel("修改密码"), editAccountPanel, new Insets(0, 50, 10, 0),
                0, 6, 18, Font.BOLD);
        setFormat(passwordPanel, editAccountPanel, new Insets(0, 0, 0, 0),
                0, 7, 1, 0, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, 0, 0);


        setFormat(new JLabel("管理个人信息"), titlePanel, new Insets(0, 50, 0, 0),
                0, 0, 22, Font.BOLD);
        setFormat(new JSeparator(), titlePanel, new Insets(0, 10, 0, 0),
                1, 0, 1, 0, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, 22, Font.BOLD);
        putIntoPanel(usernameField, userPanel);
        putIntoPanel(emailField, emailPanel);
        putIntoPanel(phoneField, phonePanel);
        putIntoPanel(passwordField, passwordPanel);
        setFormat(passwordButton, passwordPanel, new Insets(0, 50, 20, 200),
                1, 0, 0, 0, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, 0, 0);


        setFormat(titlePanel, this, new Insets(0, 0, 40, 0),
                0, 0, 1, 0, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(editAccountPanel, this, new Insets(0, 0, 0, 0),
                0, 1, 0, 0, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);
        setFormat(saveButton, phonePanel, new Insets(0, 50, 20, 50),
                1, 0, 0, 0, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, 0, 0);

        saveButton.addActionListener(_ -> {
            try {
                ResponsePack<?> responsePack = sendAccountRequest();
                if (responsePack.isSuccess()) {
                    JOptionPane.showMessageDialog(this, "用户信息更新成功！");
                } else {
                    JOptionPane.showMessageDialog(this, "用户信息更新失败！" + responsePack.getMessage());
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void addTextFieldEffect(JTextField field) {
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!field.isEditable()) {
                    field.setForeground(Color.GRAY);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!field.isEditable()) {
                    field.setForeground(Color.LIGHT_GRAY);
                }
            }
        });
    }

    private static JTextField createTextField(String text) {
        JTextField field = new JTextField(20);
        field.setText(text);
        setCustomFont(field, 14, Font.PLAIN);
        field.putClientProperty("JComponent.roundRect", true);
        field.setMargin(new Insets(5, 7, 5, 7));
        addTextFieldEffect(field);
        return field;
    }

    private void putIntoPanel(JTextField field, JPanel panel) {
        setFormat(field, panel, new Insets(0, 50, 20, 0),
                0, 0, 1, 0, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, 0, 0);
    }

    private static ResponsePack<?> sendAccountRequest() throws IOException, ClassNotFoundException {
        User user = new User(usernameField.getText(), passwordField.getText(), "user", emailField.getText(), password);
        user.setUserID(JwtUtil.extractUserId(response.getJwtToken()));
        clientUtil.sendRequest(packRequest("update", user, "update", response.getJwtToken()));
        return clientUtil.receiveResponse();
    }

    private JButton setPasswordButton(JTextField field) {
        JButton button = new JButton("编辑");
        setCustomFont(button, 14, Font.PLAIN);
        button.putClientProperty("JButton.buttonType", "roundRect");
        field.setEditable(false);
        field.setForeground(Color.LIGHT_GRAY);
        button.setMargin(new Insets(0, 14, 0, 14));

        passwordStatus = 0;
        button.addActionListener(_ -> {
            if (field.isEditable()) {
                if (passwordStatus == 0) {
                    if (field.getText().equals(currentUser.getPassword())) {
                        passwordStatus = 1;
                        field.setText("请输入新密码");
                    } else {
                        passwordStatus = 0;
                        JOptionPane.showMessageDialog(this, "旧密码错误, 请重试");
                    }
                } else if (passwordStatus == 1) {
                    if (field.getText().equals(currentUser.getPassword())) {
                        passwordStatus = 1;
                        JOptionPane.showMessageDialog(this, "新密码不能与旧密码相同");
                    } else {
                        confirmPassword = field.getText();
                        field.setText("请再次输入新密码");
                        passwordStatus = 2;
                    }
                } else if (passwordStatus == 2) {
                    if (field.getText().equals(confirmPassword)) {
                        try {
                            ResponsePack<?> responsePack = sendAccountRequest();
                            if (responsePack.isSuccess()) {
                                JOptionPane.showMessageDialog(this, "密码修改成功, 请重新登录");
                                mainFrame.dispose();
                                mainFrame = null;
                                LoginPage.startUp();
                            } else {
                                JOptionPane.showMessageDialog(this, "密码更改失败！" + responsePack.getMessage());
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "两次输入的密码不一致, 请重试");
                        field.setText("请输入旧密码");
                        passwordStatus = 1;
                    }
                }
            } else {
                field.setForeground(Color.BLACK);
                button.setText("提交");
                field.setEditable(true);
            }
        });
        return button;
    }
}