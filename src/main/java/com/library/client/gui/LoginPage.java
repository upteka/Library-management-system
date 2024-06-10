package main.java.com.library.client.gui;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.com.library.client.gui.effects.FadeEffect;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.effects.FadeEffect.applyFadeEffect;
import static main.java.com.library.client.gui.impl.ToolsIMPL.*;

public class LoginPage {
    public static MainPage mainPage = new MainPage();
    public static JFrame frame = null;
    public static void main(String[] args) {
        loadCustomFont();
        FlatLightLaf.setup();
        startUp();
    }

    public static void startUp() {
        frame = new JFrame("图书管理系统");
        setFrame(frame, 400, 600, null, false, JFrame.EXIT_ON_CLOSE);

        JTextField usernameField = new JTextField(14);
        JPasswordField passwordField = new JPasswordField(14);
        setTextField(usernameField);
        setTextField(passwordField);
        passwordField.setEchoChar('*');
        JPanel namePanel = new JPanel();
        JPanel passwordPanel = new JPanel();
        namePanel.add(usernameField);
        passwordPanel.add(passwordField);

        JLabel welcomeLabel = new JLabel("欢迎回来", JLabel.CENTER);
        setCustomFont(welcomeLabel, 28, Font.PLAIN);
        welcomeLabel.setBounds(100, 130, 200, 50);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBounds(60, 0, 280, 350);
        loginPanel.setOpaque(false);

        JButton loginButton = new JButton("继续");
        setColor(loginButton, Color.WHITE, new Color(15, 163, 127), null);
        setFormat(namePanel, loginPanel, new Insets(190, 0, 0, 0),
                0, 0, 0, 0, 0, 0);
        setFormat(passwordPanel, loginPanel, new Insets(15, 0, 0, 0),
                0, 1, 0, 0, 0, 0);
        setFormat(loginButton, loginPanel, new Insets(15, 27, 0, 27),
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

        //TODO 任意组件进行setText后布局会被意外更改, 暂时不知道原因
        signupButton.addActionListener(_ -> {
            if (signupButton.getText().equals("注册")) {
                Runnable fadeOutCallback = () -> {
                    signupButton.setText("登录");
                    welcomeLabel.setText("注册");
                    Runnable fadeInCallback = FadeEffect::checkAllAnimationsComplete;
                    applyFadeEffect(welcomeLabel, true, 1, 0.1f, fadeInCallback);
                };
                applyFadeEffect(welcomeLabel, false, 1, 0.1f, fadeOutCallback);
            } else {
                signupButton.setText("注册");
                Runnable fadeOutCallback = () -> {
                    welcomeLabel.setText("欢迎回来");
                    Runnable fadeInCallback = FadeEffect::checkAllAnimationsComplete;
                    applyFadeEffect(welcomeLabel, true, 1, 0.1f, fadeInCallback);
                };
                applyFadeEffect(welcomeLabel, false, 1, 0.1f, fadeOutCallback);
            }
        });

        loginButton.addActionListener(_ -> {
            frame.dispose();
            frame.removeAll();
            frame = null;
            mainPage.initialize();
        });

        frame.add(loginPanel);
        frame.add(loginPanel1);
        frame.add(welcomeLabel);
        frame.setVisible(true);
    }

    private static void setTextField(JTextField component) {
        setCustomFont(component, 18, Font.PLAIN);
        component.setMargin(new Insets(5, 7, 5, 7));
    }
}