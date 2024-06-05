package main.java.com.library.client.gui.view;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.ShowTable.mainPanel;
import static main.java.com.library.client.gui.ShowTable.sideBar;
import static main.java.com.library.client.gui.effects.FadeEffect.applyFadeEffect;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setCustomFont;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;

public class SideBar extends JPanel {
    private static final GridBagLayout LAYOUT = new GridBagLayout();
    private final JButton[] buttons = new JButton[4];
    private final JToggleButton toggleButton = new JToggleButton("");

    public SideBar() {
        setLayout(LAYOUT);
        initialize();
    }

    public void initialize() {
        createButton("Borrowed Books", 0);
        createButton("My Books", 1);
        createButton("My Account", 2);
        createButton("Settings", 3);

        toggleButton.addActionListener(_ -> toggleButtonAction(toggleButton));

        buttons[0].addActionListener(_ -> {
            System.out.println("WIDTH: " + this.getWidth());
        });

        GridBagConstraints g = getDefault();
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(0, 0, 0, 0);
        g.ipadx = 0;
        g.ipady = 0;
        g.weightx = 1;
        g.weighty = 1;
        add(toggleButton, g);
    }

    private void createButton(String title, int index) {
        JButton button = new JButton(title);
        button.setForeground(new Color(62, 62, 62, 0));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);
        button.setForeground(Color.BLACK);
        setCustomFont(button, 18, Font.BOLD);
        buttons[index] = button;
    }

    private void addAll() {
        GridBagConstraints g = getDefault();
        g.insets = new Insets(40, 0, 40, 0);
        toggleButton.setForeground(new Color(62, 62, 62, 0));
        add(toggleButton, g);
        g.insets = new Insets(0, 0, 20, 0);
        for (int i = 0; i < 4; i++) {
            g.gridy = i + 1;
            buttons[i].setForeground(new Color(62, 62, 62, 0));
            add(buttons[i], g);
        }
    }

    private void toggleButtonAction(JToggleButton toggleButton) {
        JComponent[] toggleOnly = {toggleButton};
        JComponent[] all = {toggleButton, buttons[0], buttons[1], buttons[2], buttons[3]};
        if (toggleButton.isSelected()) {
            applyFadeEffectSequentially(toggleOnly, true, 1, 0.15f, 0, () -> {
                animateComponent(40, 266, () -> {
                    mainPanel.remove(sideBar);
                    GridBagConstraints g1 = getThis();
                    g1.ipadx = 80;
                    mainPanel.add(sideBar, g1);
                    addAll();
                    applyFadeEffectSequentially(all, true, 1, 0.15f, 0, () -> {
                        revalidate();
                        repaint();
                    });
                });
            });
        } else {
            applyFadeEffectSequentially(all, false, 1, 0.2f, 0, () -> {
                animateComponent(266, 40, () -> {
                    mainPanel.remove(sideBar);
                    GridBagConstraints g1 = getThis();
                    g1.ipadx = 0;

                    GridBagConstraints g = getDefault();
                    g.ipady = 114514;
                    add(toggleButton, g);

                    mainPanel.add(sideBar, g1);
                    revalidate();
                    repaint();
                });

            });
        }
    }

    private void applyFadeEffectSequentially(JComponent[] components, boolean fadeIn, int timerDelay, float alphaIncrement, int index, Runnable callback) {
        if (index < components.length) {
            applyFadeEffect(components[index], fadeIn, timerDelay, alphaIncrement, () -> {
                applyFadeEffectSequentially(components, fadeIn, timerDelay, alphaIncrement, index + 1, callback);
                revalidate();
                repaint();
            });
        } else {
            if (callback != null) {
                callback.run();
            }
        }
    }

    private void animateComponent(int start, int end, Runnable callback) {
        GridBagConstraints g = getThis();

        int duration = 100;
        int interval = 10;
        int totalSteps = duration / interval;
        int distance = Math.abs(start - end);
        Timer timer = new Timer(interval, null);
        final int[] currentStep = {1};

        timer.addActionListener(e -> {
            if (currentStep[0] <= totalSteps) {
                double x = (double) currentStep[0] / totalSteps;
                double increment = distance * (2 * x - x * x);
                if (end < start) {
                    increment = -increment;
                }

                g.ipadx = start + (int) increment;
                mainPanel.remove(sideBar);
                if (currentStep[0] == 1) {
                    removeAll();
                }
                mainPanel.add(sideBar, g);
                revalidate();
                repaint();
                currentStep[0]++;
            } else {
                System.out.println("结束");
                ((Timer) e.getSource()).stop();
                if (callback != null) {
                    callback.run();
                }
            }
        });
        timer.start();
    }

    private GridBagConstraints getDefault() {
        return setFormat(null, null, new Insets(20, 0, 0, 0),
                0, 0, 0, 0, 10, 30,
                GridBagConstraints.NORTH, GridBagConstraints.NONE, 0, 0);
    }

    private GridBagConstraints getThis() {
        return setFormat(null, null, new Insets(0, 0, 0, 0),
                0, 0, 20, 0, 0, 0);
    }
}