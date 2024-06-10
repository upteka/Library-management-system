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
        setOpaque(false); // 设置不绘制背景色
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 先调用父类的方法绘制背景和边框
        Graphics2D g2d = (Graphics2D) g;

        // 启用抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // 创建渐变效果的画笔
        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, width, 0, endColor);
        g2d.setPaint(gradientPaint);

        // 获取文本的字体和内容
        FontMetrics fontMetrics = g2d.getFontMetrics(getFont());
        String text = getText();

        // 计算文本绘制位置
        int x = (width - fontMetrics.stringWidth(text)) / 2;
        int y = ((height - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();

        // 绘制渐变色文本
        g2d.drawString(text, x, y);
    }
}
