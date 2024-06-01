package main.java.com.library.gui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

import static main.java.com.library.gui.FadeEffectUtils.applyFadeEffect;

public class mainPage {
    static Font customFont;

    public static void main(String[] args) {
        FlatLightLaf.setup();
        try {
            File fontFile = new File("C:\\Users\\Administrator\\IdeaProjects\\pf.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return;
        }

        JFrame frame = new JFrame("图书管理系统");
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(255, 255, 255));
        frame.setLayout(null);

        JLabel welcomeLabel = new JLabel("欢迎回来", JLabel.CENTER);
        setCustomFont(welcomeLabel, 28, Font.PLAIN);
        welcomeLabel.setBounds(100, 130, 200, 50);

        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(60, 0, 280, 350);
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setOpaque(false);

        JTextField usernameField = new JTextField(50);
        createComponent.setFormat(usernameField, loginPanel, new Insets(190, 20, 10, 20),
                0, 0, 1, 1, 1, 1, 40, 15,
                GridBagConstraints.CENTER, 13, Font.PLAIN);

        JPasswordField passwordField = new JPasswordField(30);
        createComponent.setFormat(passwordField, loginPanel, new Insets(0, 20, 10, 20),
                0, 1, 1, 1, 1, 1, 40, 15,
                GridBagConstraints.CENTER, 13, Font.PLAIN);

        JButton loginButton = new JButton("继续");
        loginButton.setBackground(new Color(15, 163, 127));
        loginButton.setForeground(Color.WHITE);
        createComponent.setFormat(loginButton, loginPanel, new Insets(0, 20, 20, 20),
                0, 2, 1, 1, 1, 1, 40, 15,
                GridBagConstraints.CENTER, 14, Font.BOLD);

        JLabel noAccountLabel = new JLabel("还没有账户?", SwingConstants.CENTER);
        setCustomFont(noAccountLabel, 12, Font.BOLD);
        noAccountLabel.setBounds(50, 368, 300, 20);
        noAccountLabel.setForeground(new Color(48, 48, 48));

        JPanel loginPanel1 = new JPanel();
        loginPanel1.setBounds(60, 380, 280, 100);
        loginPanel1.setLayout(new GridBagLayout());
        loginPanel1.setOpaque(false);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        createComponent.setFormat(separator, loginPanel1, new Insets(5, 20, 0, 20),
                0, 0, 1, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, 0, 0);

        JButton signupButton = new JButton("注册");
        signupButton.setBackground(new Color(15, 163, 127));
        signupButton.setForeground(Color.WHITE);
        createComponent.setFormat(signupButton, loginPanel1, new Insets(0, 20, 20, 20),
                0, 1, 0, 1, 1, 1, 40, 15,
                GridBagConstraints.CENTER, 14, Font.BOLD);

        loginButton.addActionListener(e -> {
            frame.dispose();
            showMainFrame();
        });

        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                usernameField.setBorder(BorderFactory.createLineBorder(new Color(15, 163, 127), 1, true));
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
            }
        });

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(new Color(15, 163, 127), 1, true));
            }

            @Override
            public void focusLost(FocusEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
            }
        });

        signupButton.addActionListener(e -> {
            if (signupButton.getText().equals("注册")) {
                applyFadeEffect(welcomeLabel, false);
                welcomeLabel.setText("注册");
                signupButton.setText("登录");
                applyFadeEffect(welcomeLabel, true);
            } else {
                applyFadeEffect(welcomeLabel, false);
                welcomeLabel.setText("欢迎回来");
                signupButton.setText("注册");
                applyFadeEffect(welcomeLabel, true);
            }
        });

        frame.add(loginPanel);
        frame.add(loginPanel1);
        frame.add(welcomeLabel);
        frame.add(noAccountLabel);
        frame.setVisible(true);
    }


    public static void setCustomFont(JComponent component, float size, int style) {
        Font resizedFont = customFont.deriveFont(style, size);
        component.setFont(resizedFont);
    }

    public static void showMainFrame() {
        JFrame mainFrame = new JFrame("Main Application");
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setBackground(new Color(255, 255, 255));
        mainFrame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addTab("Tab 1", new showTable());

        mainFrame.add(tabbedPane, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }
}