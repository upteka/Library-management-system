package main.java.com.library.client.gui.view.about;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static main.java.com.library.client.gui.impl.ToolsIMPL.setCustomFont;

public class AboutPanel extends JPanel {
    public AboutPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("关于我们");
        setCustomFont(titleLabel, 24, Font.BOLD);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        JLabel teamLabel = new JLabel("团队成员:");
        setCustomFont(teamLabel, 18, Font.BOLD);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(teamLabel, gbc);

        JTextArea teamText = new JTextArea("李锦皓  马浩然  朱文灏");
        setCustomFont(teamText, 16, Font.PLAIN);
        teamText.setEditable(false);
        teamText.setBackground(this.getBackground());
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(teamText, gbc);

        JLabel repoLabel = new JLabel("GitHub 仓库链接:");
        setCustomFont(repoLabel, 18, Font.BOLD);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(repoLabel, gbc);

        JTextField repoLink = new JTextField("https://github.com/upteka/Library-management-system");
        setCustomFont(repoLink, 16, Font.PLAIN);
        repoLink.setForeground(Color.BLUE);
        repoLink.setEditable(false);
        repoLink.setBackground(this.getBackground());
        repoLink.setBorder(BorderFactory.createEmptyBorder());
        repoLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        repoLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(repoLink.getText()));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(repoLink, gbc);
    }
}
