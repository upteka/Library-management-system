package main.java.com.library.client.gui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static main.java.com.library.client.gui.MainPage.mainPanel;
import static main.java.com.library.client.gui.effects.FadeEffect.applyFadeEffect;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setCustomFont;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;

public class SideBar extends JPanel {
    private static final GridBagLayout LAYOUT = new GridBagLayout();
    private final List<JButton> buttons = new ArrayList<>();
    private final JToggleButton toggleButton = new JToggleButton("");

    public SideBar() {
        setLayout(LAYOUT);
        initialize();
    }

    public void initialize() {
        for (ButtonEnum buttonEnum : ButtonEnum.values()) {
            createButton(buttonEnum);
        }
        toggleButton.addActionListener(_ -> toggleButtonAction(toggleButton));
        add(toggleButton, setToggleButton(0));
    }

    private void createButton(ButtonEnum buttonEnum) {
        JButton button = new JButton(buttonEnum.getTitle());
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(192, 192, 192));
        setCustomFont(button, 18, Font.BOLD);
        button.addActionListener(buttonEnum.getAction());
        buttons.add(button);
    }

    private void addAll() {
        GridBagConstraints g = getDefault();
        g.insets = new Insets(40, 0, 40, 0);
        add(toggleButton, g);

        g.fill = GridBagConstraints.BOTH;
        for (int i = 0; i < buttons.size(); i++) {
            g.gridy = i + 1;
            g.insets = new Insets(10, 0, 10, 0);
            buttons.get(i).setForeground(new Color(62, 62, 62, 0));
            add(buttons.get(i), g);

            g.gridy = i + 2;
            g.insets = new Insets(0, 0, 0, 0);
            JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
            add(separator, g);
        }
    }

    private void toggleButtonAction(JToggleButton toggleButton) {
        JComponent[] toggleOnly = {toggleButton};
        JComponent[] all = new JComponent[buttons.size() + 1];
        all[0] = toggleButton;
        for (int i = 0; i < buttons.size(); i++) {
            all[i + 1] = buttons.get(i);
        }

        if (toggleButton.isSelected()) {
            applyFadeEffectSequentially(toggleOnly, true, 1, 0.15f, 0, () -> animateComponent(40, 194, () -> {
                updateSideBar(80, false);
                addAll();
                applyFadeEffectSequentially(all, true, 1, 0.15f, 0, this::refresh);
            }));
        } else {
            applyFadeEffectSequentially(all, false, 1, 0.2f, 0, () -> animateComponent(194, 40, () -> {
                updateSideBar(0, true);
                refresh();
            }));
        }
    }

    private void applyFadeEffectSequentially(JComponent[] components, boolean fadeIn, int timerDelay, float alphaIncrement, int index, Runnable callback) {
        if (index < components.length) {
            applyFadeEffect(components[index], fadeIn, timerDelay, alphaIncrement, () -> {
                applyFadeEffectSequentially(components, fadeIn, timerDelay, alphaIncrement, index + 1, callback);
                refresh();
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
                increment = end < start ? -increment : increment;

                g.ipadx = start + (int) increment;
                mainPanel.remove(this);
                if (currentStep[0] == 1) {
                    removeAll();
                }
                mainPanel.add(this, g);
                refresh();
                currentStep[0]++;
            } else {
                ((Timer) e.getSource()).stop();
                if (callback != null) {
                    callback.run();
                }
            }
        });
        timer.start();
    }

    private void updateSideBar(int ipadx, boolean addToggleButton) {
        GridBagConstraints g = getThis();
        g.ipadx = ipadx;
        mainPanel.remove(this);
        if (addToggleButton) {
            add(toggleButton, setToggleButton(114514));
        }
        mainPanel.add(this, g);
    }

    private void refresh() {
        revalidate();
        repaint();
    }

    private GridBagConstraints getDefault() {
        return setFormat(null, null, new Insets(20, 0, 0, 0),
                0, 0, 0, 0, 10, 30,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, 0, 0);
    }

    private GridBagConstraints getThis() {
        return setFormat(null, null, new Insets(0, 0, 0, 0),
                0, 0, 20, 0, 0, 0);
    }

    private GridBagConstraints setToggleButton(int ipady) {
        return setFormat(null, null, new Insets(0, 0, 0, 0),
                0, 0, 1, 1, 10, ipady,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, 0, 0);
    }

    private enum ButtonEnum {
        WORKSPACE("工作区", _ -> System.out.println("工作区 clicked")),
        MY_BORROWINGS("我的借阅", _ -> System.out.println("我的借阅 clicked")),
        MY_FAVORITES("我的收藏", _ -> System.out.println("我的收藏 clicked")),
        MY_ACCOUNT("我的账户", _ -> System.out.println("我的账户 clicked")),
        SETTINGS("设置", _ -> System.out.println("设置 clicked")),
        ABOUT("关于", _ -> System.out.println("关于 clicked"));

        private final String title;
        private final ActionListener action;

        ButtonEnum(String title, ActionListener action) {
            this.title = title;
            this.action = action;
        }

        public String getTitle() {
            return title;
        }

        public ActionListener getAction() {
            return action;
        }
    }
}