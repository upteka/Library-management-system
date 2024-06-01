package main.java.com.library.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static main.java.com.library.gui.MainPage.setCustomFont;

public class createComponent {
    public static GridBagConstraints setFormat(JComponent component, JComponent target, Insets insets, int gridx, int gridy, int weightx, int weighty, int gridwidth, int gridheight, int ipadx, int ipady, int anchor, int fill, int font_size, int font_type) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = fill;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        if (weightx > 0) gbc.weightx = weightx;
        if (weighty > 0) gbc.weighty = weighty;
        if (gridwidth > 0) gbc.gridwidth = gridwidth;
        if (gridheight > 0) gbc.gridheight = gridheight;
        if (ipadx > 0) gbc.ipadx = ipadx;
        if (ipady > 0) gbc.ipady = ipady;
        if (insets != null) gbc.insets = insets;
        if (anchor > 0) gbc.anchor = anchor;
        if (font_size > 0 && font_type > 0)
            setCustomFont(component, font_size, font_type);
        if (component instanceof JSeparator)
            component.setForeground(Color.LIGHT_GRAY);
        else if (component instanceof JTextField)
            component.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));

        if (target != null)
            target.add(component, gbc);

        return gbc;
    }
}
