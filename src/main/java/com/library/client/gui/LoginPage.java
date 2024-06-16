package main.java.com.library.client.gui;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.com.library.client.ClientUtil;
import main.java.com.library.client.gui.effects.FadeEffect;
import main.java.com.library.common.entity.impl.User;
import main.java.com.library.common.network.ResponsePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.regex.Pattern;

import static main.java.com.library.client.gui.effects.FadeEffect.applyFadeEffect;
import static main.java.com.library.client.gui.effects.NotificationUtil.Notification;
import static main.java.com.library.client.gui.impl.ToolsIMPL.*;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class LoginPage {
    public static MainPage mainPage = new MainPage();
    public static JFrame frame = null;
    private static Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PHONE_PATTERN = "^(\\+\\d{1,3}[- ]?)?\\d{10,13}$";
    public static ResponsePack<?> response = null;
    public static ClientUtil clientUtil = null;
    public static User currentUser = null;
    public static String password = null;

    public static void main(String[] args) {
        loadCustomFont();
        FlatLightLaf.setup();
        startUp();
    }

    public static void startUp() {
        response = null;
        clientUtil = null;
        currentUser = null;
        frame = new JFrame("图书管理系统");
        setFrame(frame, 400, 600, null, false, JFrame.EXIT_ON_CLOSE);

        JTextField EmailOrPhone = new JTextField(13);
        JTextField usernameField = new JTextField(13);
        JPasswordField passwordField = new JPasswordField(13);
        setTextField(usernameField);
        setTextField(passwordField);
        setTextField(EmailOrPhone);
        passwordField.setEchoChar('*');
        JPanel namePanel = new JPanel();
        JPanel passwordPanel = new JPanel();
        namePanel.add(usernameField);
        passwordPanel.add(passwordField);

        JLabel welcomeLabel = new JLabel("欢迎回来", JLabel.CENTER);
        setCustomFont(welcomeLabel, 28, Font.PLAIN);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBounds(60, 0, 280, 350);
        loginPanel.setOpaque(false);

        JButton continueButton = new JButton("继续");
        setColor(continueButton, Color.WHITE, new Color(15, 163, 127), null);
        setFormat(namePanel, loginPanel, new Insets(190, 0, 0, 0),
                0, 0, 0, 0, 0, 0);
        setFormat(passwordPanel, loginPanel, new Insets(15, 0, 0, 0),
                0, 1, 0, 0, 0, 0);
        setFormat(continueButton, loginPanel, new Insets(15, 27, 0, 27),
                0, 2, 1, 0, 0, 15,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 14, Font.BOLD);

        JPanel loginPanel1 = new JPanel(new GridBagLayout());
        loginPanel1.setBounds(60, 350, 280, 200);
        loginPanel1.setOpaque(false);

        JLabel noAccountLabel = new JLabel("还没有账户?", SwingConstants.CENTER);
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        JButton signupButton = new JButton("注册");
        setCustomFont(noAccountLabel, 12, Font.BOLD);
        setColor(noAccountLabel, new Color(48, 48, 48), null, null);
        setColor(signupButton, Color.WHITE, new Color(15, 163, 127), null);
        setFormat(noAccountLabel, loginPanel1, new Insets(20, 0, 0, 0),
                0, 0, 0, 0, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 14, Font.BOLD);
        setFormat(separator, loginPanel1, new Insets(10, 0, 10, 0),
                0, 1, 1, 0, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(signupButton, loginPanel1, new Insets(0, 27, 100, 27),
                0, 2, 0, 0, 0, 15,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 14, Font.BOLD);

        addFocusListenerToField(usernameField);
        addFocusListenerToField(passwordField);
        addFocusListenerToField(EmailOrPhone);

        EmailOrPhone.setText("请输入手机号或邮箱");
        welcomeLabel.setBounds(100, 130, 200, 50);
        EmailOrPhone.setBounds(88, 145, 223, 31);

        signupButton.addActionListener(_ -> {
            if (signupButton.getText().equals("注册")) {
                signupButton.setText("登录");
                applyFadeEffect(welcomeLabel, false, 1, 0.1f, () -> {
                    welcomeLabel.setText("注册");
                    welcomeLabel.setBounds(100, 80, 200, 30);
                    applyFadeEffect(welcomeLabel, true, 1, 0.1f, null);
                });
                frame.add(EmailOrPhone);
                applyFadeEffect(EmailOrPhone, true, 1, 0.1f, null);
            } else {
                signupButton.setText("注册");
                applyFadeEffect(welcomeLabel, false, 1, 0.1f, () -> {
                    welcomeLabel.setText("欢迎回来");
                    welcomeLabel.setBounds(100, 130, 200, 50);
                    applyFadeEffect(welcomeLabel, true, 1, 0.1f, null);
                });
                applyFadeEffect(EmailOrPhone, false, 1, 0.1f, () -> {
                    frame.remove(EmailOrPhone);
                    frame.repaint();
                });
            }
            FadeEffect.checkAllAnimationsComplete();
        });

        continueButton.addActionListener(_ -> {
            try {
                if (clientUtil == null) {
                    clientUtil = new ClientUtil();
                    LOGGER.info("ClientUtil initialized in static block");
                }
                if (signupButton.getText().equals("登录")) {
                    String email = isEmail(EmailOrPhone.getText(), null) ? EmailOrPhone.getText() : "";
                    String phone = isPhone(EmailOrPhone.getText(), null) ? EmailOrPhone.getText() : "";
                    if (email.isEmpty() && phone.isEmpty()) {
                        Notification(frame, "请输入有效的手机号或邮箱");
                        return;
                    }
                    User user = new User(usernameField.getText(), passwordField.getText(), "user", email, phone);
                    ResponsePack<?> response = register(user);
                    if (response.isSuccess())
                        Notification(frame, "注册成功");
                    else
                        Notification(frame, "注册失败 " + response.getMessage());
                } else {
                    User user = new User(usernameField.getText(), passwordField.getText(), "user", "1");
                    response = login(user);
                    if (response.isSuccess()) {
                        currentUser = (User) response.getData();
                        password = passwordField.getText();
                        frame.dispose();
                        frame.removeAll();
                        frame = null;
                        System.gc();
                        mainPage = new MainPage();
                        mainPage.initialize();
                    } else {
                        Notification(frame, "登录失败 " + response.getMessage());
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        frame.add(loginPanel);
        frame.add(loginPanel1);
        frame.add(welcomeLabel);
        frame.setVisible(true);
    }

    private static void setTextField(JTextField component) {
        setCustomFont(component, 20, Font.PLAIN);
        component.setMargin(new Insets(0, 0, 0, 0));
    }

    private static ResponsePack<?> register(User user) throws IOException, ClassNotFoundException {
        clientUtil.sendRequest(packRequest("register", user, "register", ""));
        return clientUtil.receiveResponse();
    }

    private static ResponsePack<?> login(User user) throws IOException, ClassNotFoundException {
        clientUtil.sendRequest(packRequest("auth", user, "login", ""));
        return clientUtil.receiveResponse();
    }

    public static <T extends Window> boolean isEmail(String input, T parent) {
        boolean result = Pattern.matches(EMAIL_PATTERN, input);
        if (!result && parent != null) Notification(parent, "请输入有效的邮箱");
        return result;
    }

    public static <T extends Window> boolean isPhone(String input, T parent) {
        boolean result = Pattern.matches(PHONE_PATTERN, input);
        if (!result && parent != null) Notification(parent, "请输入有效的手机号");
        return result;
    }
}