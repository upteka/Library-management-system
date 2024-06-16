package main.java.com.library.client.gui.effects;

import javax.swing.*;
import java.awt.*;

public class GradientLabel extends JLabel {
    private Color startColor;
    private Color endColor;

    public GradientLabel(String text, Color startColor, Color endColor) {
        super(text);
        this.startColor = startColor;
        this.endColor = endColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, width, 0, endColor);
        g2d.setPaint(gradientPaint);

        FontMetrics fontMetrics = g2d.getFontMetrics(getFont());
        String text = getText();

        int x = (width - fontMetrics.stringWidth(text)) / 2;
        int y = ((height - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();

        g2d.drawString(text, x, y);
    }
}
