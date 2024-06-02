package main.java.com.library.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FadeEffectUtils {

    // 可调整的动画参数
    private static final int TIMER_DELAY = 1;  // 定时器延迟，单位为毫秒
    private static final float ALPHA_INCREMENT = 0.1f;  // 透明度变化增量

    private static Timer currentTimer = null;

    public static void applyFadeEffect(JComponent component, boolean fadeIn) {
        // 如果当前有渐变效果正在执行，则取消之前的渐变
        if (currentTimer != null && currentTimer.isRunning()) {
            currentTimer.stop();
        }

        // 创建新的渐变效果定时器
        Timer timer = fadeIn ? createFadeInTimer(component) : createFadeOutTimer(component);
        timer.start();
        currentTimer = timer;
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