package main.java.com.library.client.gui.effects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FadeEffect {
    // 更改一个组件的透明度以实现淡入淡出的效果
    // 使用方法 applyFadeEffect(JComponent, boolean)
    // true 表示出现, 透明度从 0 到 255
    // false 表示消失, 透明度从 255 到 0

    private static final int TIMER_DELAY = 1;  // 定时器延迟，单位为毫秒
    private static final float ALPHA_INCREMENT = 0.1f;  // 透明度变化增量

    private static Timer currentTimer = null;

    public static void applyFadeEffect(JComponent component, boolean fadeIn) {
        if (currentTimer != null && currentTimer.isRunning()) {
            currentTimer.stop();
        }

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