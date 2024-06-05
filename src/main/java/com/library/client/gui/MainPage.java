package main.java.com.library.client.gui;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.com.library.client.gui.effects.FadeEffect;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.effects.FadeEffect.applyFadeEffect;
import static main.java.com.library.client.gui.impl.ToolsIMPL.*;

public class MainPage {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        loadCustomFont();
        UIManager.put("ScrollBar.showButtons", true);
        UIManager.put("TabbedPane.selectedBackground", Color.white);

        JFrame frame = new JFrame("图书管理系统");
        setFrame(frame, 400, 600, null, false, JFrame.EXIT_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("欢迎回来", JLabel.CENTER);
        setCustomFont(welcomeLabel, 28, Font.PLAIN);
        welcomeLabel.setBounds(100, 130, 200, 50);

        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(60, 0, 280, 350);
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setOpaque(false);

        JTextField usernameField = new JTextField(50);
        setFormat(usernameField, loginPanel, new Insets(190, 20, 12, 20),
                0, 0, 40, 15, 20, Font.PLAIN);

        JPasswordField passwordField = new JPasswordField(50);
        setFormat(passwordField, loginPanel, new Insets(0, 20, 12, 20),
                0, 1, 40, 15, 20, Font.PLAIN);

        JButton loginButton = new JButton("继续");
        setColor(loginButton, Color.WHITE, new Color(15, 163, 127), null);
        setFormat(loginButton, loginPanel, new Insets(0, 20, 20, 20),
                0, 2, 1, 1, 40, 15,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 14, Font.BOLD);

        JLabel noAccountLabel = new JLabel("还没有账户?", SwingConstants.CENTER);
        setColor(noAccountLabel, new Color(48, 48, 48), null, null);
        setCustomFont(noAccountLabel, 12, Font.BOLD);
        noAccountLabel.setBounds(50, 368, 300, 20);

        JPanel loginPanel1 = new JPanel();
        loginPanel1.setBounds(60, 380, 280, 100);
        loginPanel1.setLayout(new GridBagLayout());
        loginPanel1.setOpaque(false);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        setFormat(separator, loginPanel1, new Insets(5, 20, 0, 20),
                0, 0, 1, 0, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0);

        JButton signupButton = new JButton("注册");
        setColor(signupButton, Color.WHITE, new Color(15, 163, 127), null);
        setFormat(signupButton, loginPanel1, new Insets(0, 20, 20, 20),
                0, 1, 0, 1, 40, 15,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 14, Font.BOLD);

        addFocusListenerToField(usernameField);
        addFocusListenerToField(passwordField);

        loginButton.addActionListener(_ -> {
            frame.dispose();
            new ShowTable();
        });

        signupButton.addActionListener(_ -> {
            if (signupButton.getText().equals("注册")) {
                signupButton.setText("登录");
                Runnable fadeOutCallback = () -> {
                    welcomeLabel.setText("注册");
                    Runnable fadeInCallback = FadeEffect::checkAllAnimationsComplete;
                    applyFadeEffect(welcomeLabel, true, 1, 0.1f, fadeInCallback);
                };
                applyFadeEffect(welcomeLabel, false, 1, 0.1f, fadeOutCallback);
            } else {
                Runnable fadeOutCallback = () -> {
                    welcomeLabel.setText("欢迎回来");
                    Runnable fadeInCallback = FadeEffect::checkAllAnimationsComplete;
                    applyFadeEffect(welcomeLabel, true, 1, 0.1f, fadeInCallback);
                };
                signupButton.setText("注册");
                applyFadeEffect(welcomeLabel, false, 1, 0.1f, fadeOutCallback);
            }
        });

        frame.add(loginPanel);
        frame.add(loginPanel1);
        frame.add(welcomeLabel);
        frame.add(noAccountLabel);
        frame.setVisible(true);
    }
}