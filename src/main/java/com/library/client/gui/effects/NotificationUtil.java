package main.java.com.library.client.gui.effects;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.impl.ToolsIMPL.setCustomFont;

public class NotificationUtil {
    public static int delayTime = 2000;
    private static int notificationCount = 0;
    private static int lastNotificationY = -1;

    public static <T extends Window> void Notification(T parent, String message) {
        String title = "Notification";
        String body = message;

        JWindow window = new JWindow(parent);
        window.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        setCustomFont(titleLabel, 16, Font.BOLD);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        JButton closeButton = new JButton("X");
        closeButton.setMargin(new Insets(0, 0, 0, 0));
        closeButton.setPreferredSize(new Dimension(20, 20));
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setForeground(Color.RED);

        closeButton.addActionListener(_ -> {
            window.setVisible(false);
            window.dispose();
            notificationCount--;
            if (notificationCount == 0) {
                lastNotificationY = -1;
            }
        });

        titlePanel.add(closeButton, BorderLayout.EAST);
        panel.add(titlePanel, BorderLayout.NORTH);

        JTextArea bodyArea = new JTextArea(body);
        setCustomFont(bodyArea, 14, Font.PLAIN);
        bodyArea.setWrapStyleWord(true);
        bodyArea.setLineWrap(true);
        bodyArea.setOpaque(false);
        bodyArea.setEditable(false);
        bodyArea.setFocusable(false);
        bodyArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(bodyArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        panel.add(scrollPane, BorderLayout.CENTER);
        window.add(panel);

        Rectangle parentBounds = parent.getBounds();
        int width = 300;
        int height = 150;

        window.setSize(width, height);

        int x = parentBounds.x + parentBounds.width - width - 10;
        int y;
        if (lastNotificationY == -1) {
            y = parentBounds.y + parentBounds.height - height - 60;
        } else {
            y = lastNotificationY - height - 10;
        }
        lastNotificationY = y;

        window.setLocation(x, y);
        window.setVisible(true);
        notificationCount++;

        Timer timer = new Timer(delayTime, e -> {
            if (window.isVisible()) {
                window.setVisible(false);
                window.dispose();
                notificationCount--;
                if (notificationCount == 0) {
                    lastNotificationY = -1;
                }
            }
            ((Timer) e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();
    }
}