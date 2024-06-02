package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.gui.MainPage.setCustomFont;

public class CreateComponent {
    public static GridBagConstraints setFormat(JComponent component, JComponent target, Insets insets, int gridx, int gridy, int weightx, int weighty, int ipadx, int ipady, int anchor, int fill, int font_size, int font_type) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        if (fill > 0) gbc.fill = fill;
        if (anchor > 0) gbc.anchor = anchor;
        if (weightx > 0) gbc.weightx = weightx;
        if (weighty > 0) gbc.weighty = weighty;
        if (ipadx > 0) gbc.ipadx = ipadx;
        if (ipady > 0) gbc.ipady = ipady;
        if (insets != null) gbc.insets = insets;
        if (font_size > 0 && font_type > 0) setCustomFont(component, font_size, font_type);

        if (component != null && target != null)
            target.add(component, gbc);
        return gbc;
    }

    public static GridBagConstraints setFormat(JComponent component, JComponent target, Insets insets, int gridx, int gridy, int font_size, int font_type) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = insets;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if (font_size > 0 && font_type > 0)
            setCustomFont(component, font_size, font_type);

        if (component != null && target != null)
            target.add(component, gbc);
        return gbc;
    }

    public static GridBagConstraints setFormat(JComponent component, JComponent target, Insets insets, int gridx, int gridy, int ipadx, int ipady, int font_size, int font_type) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.ipadx = ipadx;
        gbc.ipady = ipady;
        gbc.insets = insets;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        if (font_size > 0 && font_type > 0)
            setCustomFont(component, font_size, font_type);

        if (component != null && target != null)
            target.add(component, gbc);
        return gbc;
    }
}
