package main.java.com.library.client.gui.effects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FadeEffect {

    private static int animationCount = 0;
    private static Runnable onComplete;

    public static void applyFadeEffect(JComponent component, boolean fadeIn, int timerDelay, float alphaIncrement, Runnable callback) {
        animationCount++;
        Timer timer = fadeIn ? createFadeInTimer(component, timerDelay, alphaIncrement, callback) : createFadeOutTimer(component, timerDelay, alphaIncrement, callback);
        timer.start();
    }

    private static Timer createFadeInTimer(JComponent component, int timerDelay, float alphaIncrement, Runnable callback) {
        return new Timer(timerDelay, new ActionListener() {
            private float alpha = 0.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += alphaIncrement;
                if (alpha >= 1.0f) {
                    alpha = 1.0f;
                    ((Timer) e.getSource()).stop();
                    animationCount--;
                    if (callback != null) {
                        callback.run();
                    }
                }
                setComponentAlpha(component, alpha);
            }
        });
    }

    private static Timer createFadeOutTimer(JComponent component, int timerDelay, float alphaIncrement, Runnable callback) {
        return new Timer(timerDelay, new ActionListener() {
            private float alpha = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= alphaIncrement;
                if (alpha <= 0.0f) {
                    alpha = 0.0f;
                    ((Timer) e.getSource()).stop();
                    animationCount--;
                    if (callback != null) {
                        callback.run();
                    }
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

    public static void setOnComplete(Runnable onComplete) {
        FadeEffect.onComplete = onComplete;
    }

    public static void checkAllAnimationsComplete() {
        if (animationCount <= 0 && onComplete != null) {
            onComplete.run();
        }
    }
}