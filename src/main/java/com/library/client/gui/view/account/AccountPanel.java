package main.java.com.library.client.gui.view.account;

import com.github.f4b6a3.ulid.Ulid;
import main.java.com.library.client.gui.LoginPage;
import main.java.com.library.common.entity.impl.User;
import main.java.com.library.common.network.JwtUtil;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import static main.java.com.library.client.gui.LoginPage.*;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setCustomFont;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class AccountPanel extends JPanel {
    private static final GridBagLayout LAYOUT = new GridBagLayout();
    private static int passwordStatus;
    private static String confirmPassword;

    private static JTextField usernameField = null;
    private static JTextField emailField = null;
    private static JTextField phoneField = null;
    private static JTextField passwordField = null;
    private static JLabel passwordLabel = null;

    public AccountPanel() {
        setLayout(LAYOUT);

        JPanel welcomePanel = new JPanel(LAYOUT);
        JPanel titlePanel = new JPanel(LAYOUT);
        JPanel editAccountPanel = new JPanel(LAYOUT);

        JButton passwordButton = setPasswordButton(passwordField);
        JButton saveButton = new JButton("提交");
        saveButton.putClientProperty("JButton.buttonType", "roundRect");
        JButton refreshButton = new JButton("刷新");
        refreshButton.putClientProperty("JButton.buttonType", "roundRect");
        JPanel userPanel = new JPanel(LAYOUT);
        JPanel emailPanel = new JPanel(LAYOUT);
        JPanel phonePanel = new JPanel(LAYOUT);
        JPanel passwordPanel = new JPanel(LAYOUT);

        usernameField = createTextField(currentUser.getUsername());
        emailField = createTextField(currentUser.getEmail());
        phoneField = createTextField(currentUser.getPhone());
        passwordField = createTextField("");
        passwordLabel = new JLabel("请输入旧密码");

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hour < 6) {
            greeting = "夜深了，注意身体";
        } else if (hour < 9) {
            greeting = "早上好";
        } else if (hour < 12) {
            greeting = "上午好";
        } else if (hour < 14) {
            greeting = "中午好";
        } else if (hour < 18) {
            greeting = "下午好";
        } else if (hour < 21) {
            greeting = "傍晚好";
        } else {
            greeting = "晚上好";
        }

        int days = (int) (ChronoUnit.NANOS.between(Ulid.getInstant(currentUser.getUserID()), Instant.now()) / (24 * 60 * 60 * 1_000_000_000L));
        if (days == 0) days = 1;

        JLabel title = new JLabel(greeting + "，" + currentUser.getUsername());
        JLabel title2 = new JLabel("今天是您加入我们的第 " + days + " 天, 祝您生活愉快！");
        setFormat(title, welcomePanel, new Insets(0, 50, 10, 0),
                0, 0, 1, 0, 0, 0, GridBagConstraints.WEST, 1, 36, 0);
        setFormat(title2, welcomePanel, new Insets(0, 50, 0, 0),
                0, 1, 18, 0);


        setFormat(new JLabel("用户名"), editAccountPanel, new Insets(0, 50, 10, 0),
                0, 0, 18, Font.BOLD);
        setFormat(userPanel, editAccountPanel, new Insets(0, 0, 0, 425),
                0, 1, 0, 0);
        setFormat(new JLabel("邮箱"), editAccountPanel, new Insets(0, 50, 10, 0),
                0, 2, 18, Font.BOLD);
        setFormat(emailPanel, editAccountPanel, new Insets(0, 0, 0, 425),
                0, 3, 0, 0);
        setFormat(new JLabel("手机号"), editAccountPanel, new Insets(0, 50, 10, 0),
                0, 4, 18, Font.BOLD);
        setFormat(phonePanel, editAccountPanel, new Insets(0, 0, 0, 179),
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
        setFormat(passwordLabel, passwordPanel, new Insets(0, 50, 5, 0),
                0, 0, 1, 0, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, 15, 0);
        setFormat(passwordField, passwordPanel, new Insets(0, 50, 20, 0),
                0, 1, 1, 0, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, 0, 0);
        setFormat(passwordButton, passwordPanel, new Insets(0, 50, 20, 180),
                1, 1, 0, 0, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, 0, 0);


        setFormat(welcomePanel, this, new Insets(0, 0, 40, 0),
                0, 0, 1, 0, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(titlePanel, this, new Insets(0, 0, 40, 0),
                0, 1, 1, 0, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(editAccountPanel, this, new Insets(0, 0, 0, 0),
                0, 2, 0, 0, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);
        setFormat(saveButton, phonePanel, new Insets(0, 50, 20, 0),
                1, 0, 0, 0, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, 14, 0);
        setFormat(refreshButton, phonePanel, new Insets(0, 50, 20, 0),
                2, 0, 0, 0, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, 14, 0);

        refreshButton.addActionListener(_ -> {
            usernameField.setText(currentUser.getUsername());
            emailField.setText(currentUser.getEmail());
            phoneField.setText(currentUser.getPhone());
        });

        saveButton.addActionListener(_ -> {
            try {
                if (!isEmail(emailField.getText())) {
                    JOptionPane.showMessageDialog(this, "请输入有效的邮箱");
                    return;
                }
                if (!isPhone(phoneField.getText())) {
                    JOptionPane.showMessageDialog(this, "请输入有效的手机号");
                    return;
                }
                ResponsePack<?> responsePack = sendAccountRequest(false);
                if (responsePack.isSuccess()) {
                    JOptionPane.showMessageDialog(this, "用户信息更新成功！");
                    currentUser.setUsername(usernameField.getText());
                    currentUser.setEmail(emailField.getText());
                    currentUser.setPhone(phoneField.getText());
                } else {
                    JOptionPane.showMessageDialog(this, "用户信息更新失败！\n" + responsePack.getMessage());
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static JTextField createTextField(String text) {
        JTextField field = new JTextField(20);
        field.setText(text);
        setCustomFont(field, 14, Font.PLAIN);
        field.putClientProperty("JComponent.roundRect", true);
        field.setMargin(new Insets(5, 7, 5, 7));
        return field;
    }

    private void putIntoPanel(JTextField field, JPanel panel) {
        setFormat(field, panel, new Insets(0, 50, 20, 0),
                0, 0, 1, 0, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, 0, 0);
    }

    private static ResponsePack<?> sendAccountRequest(boolean isChangePassword) throws IOException, ClassNotFoundException {
        User user = new User();
        if (isChangePassword) user.setPassword(passwordField.getText());
        else user = new User(currentUser.getUsername(), "", "user", emailField.getText(), phoneField.getText());
        user.setUserID(JwtUtil.extractUserId(response.getJwtToken()));
        clientUtil.sendRequest(packRequest("update", user, "update", response.getJwtToken()));
        return clientUtil.receiveResponse();
    }

    private JButton setPasswordButton(JTextField field) {
        JButton button = new JButton("提交");
        setCustomFont(button, 14, Font.PLAIN);
        button.putClientProperty("JButton.buttonType", "roundRect");

        passwordStatus = 0;
        button.addActionListener(_ -> {
            if (passwordStatus == 0) {
                if (field.getText().equals(password)) {
                    passwordStatus = 1;
                    passwordLabel.setText("请输入新密码");
                    passwordField.setText("");
                } else JOptionPane.showMessageDialog(this, "旧密码错误, 请重试");
            } else if (passwordStatus == 1) {
                if (field.getText().equals(password)) JOptionPane.showMessageDialog(this, "新密码不能与旧密码相同");
                else if (field.getText().isEmpty()) JOptionPane.showMessageDialog(this, "密码不能为空");
                else {
                    confirmPassword = field.getText();
                    passwordLabel.setText("请验证新密码");
                    passwordField.setText("");
                    passwordStatus = 2;
                }
            } else if (passwordStatus == 2) {
                if (field.getText().equals(confirmPassword)) {
                    try {
                        ResponsePack<?> responsePack = sendAccountRequest(true);
                        if (responsePack.isSuccess()) {
                            JOptionPane.showMessageDialog(this, "密码修改成功, 请重新登录");
                            mainPage.deleteAll();
                            mainFrame.dispose();
                            mainFrame = null;
                            mainPage = null;
                            currentUser = null;
                            password = null;
                            LoginPage.startUp();
                        } else JOptionPane.showMessageDialog(this, "密码修改失败！" + responsePack.getMessage());
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "两次输入的密码不一致, 请重试");
                    passwordLabel.setText("请输入新密码");
                    passwordStatus = 1;
                }
            }
        });
        return button;
    }

    public void deleteAll() {
        passwordStatus = 0;
        usernameField = null;
        emailField = null;
        phoneField = null;
        passwordField = null;
        passwordLabel = null;
        System.gc();
    }
}