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
        Timer timer = fadeIn ? createFadeInTimer(component) : createFadeOutTimer(component);
        timer.start();
    }

    private static Timer createFadeInTimer(JComponent component) {
        return new Timer(TIMER_DELAY, new ActionListener() {
            private float alpha = 0.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += ALPHA_INCREMENT;
                if (alpha >= 1.0f) {
                    alpha = 1.0f;
                    ((Timer) e.getSource()).stop();
                }
                setComponentAlpha(component, alpha);
            }
        });
    }

    private static Timer createFadeOutTimer(JComponent component) {
        return new Timer(TIMER_DELAY, new ActionListener() {
            private float alpha = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= ALPHA_INCREMENT;
                if (alpha <= 0.0f) {
                    alpha = 0.0f;
                    ((Timer) e.getSource()).stop();
                }
                setComponentAlpha(component, alpha);
            }
        });
    }

    private static void setComponentAlpha(JComponent component, float alpha) {
        component.setForeground(new Color(
                component.getForeground().getRed(),
                component.getForeground().getGreen(),
                component.getForeground().getBlue(),
                (int) (255 * alpha)
        ));
        component.repaint();
    }
}