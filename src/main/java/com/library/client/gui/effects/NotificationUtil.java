package main.java.com.library.client.gui.effects;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.impl.ToolsIMPL.setCustomFont;

public class NotificationUtil {
    public static int delayTime = 2000;
    private static int notificationCount = 0;
    private static int lastNotificationY = -1;

    public static <T extends Window> void Notification(T parent, String message) {
        JWindow window = new JWindow(parent);
        window.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        setCustomFont(label, 14, 0);
        panel.add(label, BorderLayout.CENTER);

        JButton closeButton = new JButton("X");
        closeButton.setMargin(new Insets(20, 0, 0, 0));
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

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        topPanel.setOpaque(false);
        topPanel.add(closeButton);

        panel.add(topPanel, BorderLayout.NORTH);

        window.add(panel);

        Rectangle parentBounds = parent.getBounds();
        int width = 200;
        int height = 100;

        window.setSize(width, height);

        int x = parentBounds.x + parentBounds.width - width - 10;
        int y;
        if (lastNotificationY == -1) {
            y = parentBounds.y + parentBounds.height - height - 60; // 上移 50 像素
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
