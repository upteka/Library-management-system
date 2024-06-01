package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FadeEffectUtils {

    // 可调整的动画参数
    private static final int TIMER_DELAY = 1;  // 定时器延迟，单位为毫秒
    private static final float ALPHA_INCREMENT = 0.1f;  // 透明度变化增量

    public static void applyFadeEffect(JComponent component, boolean fadeIn) {
        Timer timer = createFadeTimer(component, fadeIn);
        timer.start();
    }

    private static Timer createFadeTimer(JComponent component, boolean fadeIn) {
        return new Timer(TIMER_DELAY, new ActionListener() {
            private float alpha = fadeIn ? 0.0f : 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha = fadeIn ? alpha + ALPHA_INCREMENT : alpha - ALPHA_INCREMENT;
                if (fadeIn && alpha >= 1.0f) {
                    alpha = 1.0f;
                    ((Timer) e.getSource()).stop();
                } else if (!fadeIn && alpha <= 0.0f) {
                    alpha = 0.0f;
                    ((Timer) e.getSource()).stop();
                }
                setComponentAlpha(component, alpha);
            }
        });
    }

    private static void setComponentAlpha(JComponent component, float alpha) {
        Color foreground = component.getForeground();
        component.setForeground(new Color(
                foreground.getRed(),
                foreground.getGreen(),
                foreground.getBlue(),
                (int) (255 * alpha)
        ));
        component.repaint();
    }
}