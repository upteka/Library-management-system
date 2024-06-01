package main.java.com.library.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static main.java.com.library.gui.mainPage.setCustomFont;

public class createComponent {
    public static void setFormat(JComponent component, JComponent target, Insets insets, int gridx, int gridy, int weightx, int weighty, int gridwidth, int gridheight, int ipadx, int ipady, int anchor, int font_size, int font_type) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.ipadx = ipadx;
        gbc.ipady = ipady;
        gbc.insets = insets;
        gbc.anchor = anchor;
        if (font_size > 0 && font_type > 0)
            setCustomFont(component, font_size, font_type);
        if (component instanceof JSeparator)
            component.setForeground(Color.LIGHT_GRAY);
        else if (component instanceof JTextField)
            component.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));

        if (target != null)
            target.add(component, gbc);
    }
}