package main.java.com.library.gui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static main.java.com.library.gui.FadeEffectUtils.applyFadeEffect;

public class MainPage {
    static Font customFont;
    private static final Color PRIMARY_COLOR = new Color(15, 163, 127);
    private static final Color SECONDARY_COLOR = Color.LIGHT_GRAY;
    private static final Color BACKGROUND_COLOR = Color.WHITE;

    public static void main(String[] args) {
        FlatLightLaf.setup();
        loadCustomFont();

        JFrame frame = createFrame("图书管理系统", 400, 600);
        frame.setLayout(new BorderLayout());

        JLabel welcomeLabel = createLabel("欢迎回来", 28, Font.PLAIN, JLabel.CENTER);
        frame.add(welcomeLabel, BorderLayout.NORTH);

        JPanel loginPanel = createLoginPanel();
        frame.add(loginPanel, BorderLayout.CENTER);

        JPanel bottomPanel = createBottomPanel(welcomeLabel);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void loadCustomFont() {
        try {
            File fontFile = new File(".\\lib\\pf.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCustomFont(JComponent component, float size, int style) {
        Font resizedFont = customFont.deriveFont(style, size);
        component.setFont(resizedFont);
    }

    private static JFrame createFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        return frame;
    }

    private static JLabel createLabel(String text, float size, int style, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setFont(customFont.deriveFont(style, size));
        return label;
    }

    private static JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false);

        JTextField usernameField = createTextField(50);
        addComponentToPanel(usernameField, loginPanel, new Insets(190, 20, 10, 20), 0, 0);

        JPasswordField passwordField = createPasswordField(30);
        addComponentToPanel(passwordField, loginPanel, new Insets(0, 20, 10, 20), 0, 1);

        JButton loginButton = createButton("继续", PRIMARY_COLOR, Color.WHITE);
        addComponentToPanel(loginButton, loginPanel, new Insets(0, 20, 20, 20), 0, 2);

        loginButton.addActionListener(e -> showMainFrame());

        addFocusListener(usernameField);
        addFocusListener(passwordField);

        return loginPanel;
    }

    private static JPanel createBottomPanel(JLabel welcomeLabel) {
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setOpaque(false);

        JLabel noAccountLabel = createLabel("还没有账户?", 12, Font.BOLD, SwingConstants.CENTER);
        noAccountLabel.setForeground(new Color(48, 48, 48));
        bottomPanel.add(noAccountLabel, new GridBagConstraints());

        JButton signupButton = createButton("注册", PRIMARY_COLOR, Color.WHITE);
        addComponentToPanel(signupButton, bottomPanel, new Insets(0, 20, 20, 20), 0, 1);

        signupButton.addActionListener(createSignupButtonListener(welcomeLabel, signupButton));

        return bottomPanel;
    }

    private static ActionListener createSignupButtonListener(JLabel welcomeLabel, JButton signupButton) {
        return e -> {
            boolean isSignup = signupButton.getText().equals("注册");
            applyFadeEffect(welcomeLabel, false);
            welcomeLabel.setText(isSignup ? "注册" : "欢迎回来");
            signupButton.setText(isSignup ? "登录" : "注册");
            applyFadeEffect(welcomeLabel, true);
        };
    }

    private static void addComponentToPanel(JComponent component, JPanel panel, Insets insets, int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = insets;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    private static JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(customFont.deriveFont(Font.PLAIN, 13));
        textField.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR, 1, true));
        return textField;
    }

    private static JPasswordField createPasswordField(int columns) {
        JPasswordField passwordField = new JPasswordField(columns);
        passwordField.setFont(customFont.deriveFont(Font.PLAIN, 13));
        passwordField.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR, 1, true));
        return passwordField;
    }

    private static JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(customFont.deriveFont(Font.BOLD, 14));
        return button;
    }

    private static void addFocusListener(JTextField textField) {
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true));
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR, 1, true));
            }
        });
    }

    public static void showMainFrame() {
        JFrame mainFrame = createFrame("Main Application", 800, 600);
        mainFrame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        ShowTable tablePanel = new ShowTable();
        tabbedPane.addTab("Tab 1", tablePanel);
        mainFrame.add(tabbedPane, BorderLayout.CENTER);

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustEditButtons(mainFrame, tablePanel);
            }
        });

        mainFrame.setVisible(true);
    }

    private static void adjustEditButtons(JFrame mainFrame, ShowTable tablePanel) {
        if (mainFrame.getWidth() > 800) {
            for (JButton editButton : tablePanel.editButtons) {
                GridBagConstraints gbc = tablePanel.gbcMap.get(editButton);
                gbc.insets = new Insets(0, 150 + mainFrame.getWidth() - 800, 0, 0);
                GridBagLayout layout = (GridBagLayout) editButton.getParent().getLayout();
                layout.setConstraints(editButton, gbc);
                editButton.getParent().revalidate();
                editButton.getParent().repaint();
            }
        }
    }
}