package main.java.com.library.client.gui.view;

import main.java.com.library.client.gui.view.workspace.WorkSpace;
import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.common.entity.impl.FavoriteRecord;
import main.java.com.library.common.entity.impl.User;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static main.java.com.library.client.gui.LoginPage.*;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.MainPage.mainPanel;
import static main.java.com.library.client.gui.effects.FadeEffect.applyFadeEffect;
import static main.java.com.library.client.gui.effects.NotificationUtil.Notification;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setCustomFont;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;
import static main.java.com.library.client.gui.view.workspace.WorkPanel.isSearching;
import static main.java.com.library.client.gui.view.workspace.WorkPanel.sortSearching;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.currentPage;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class SideBar extends JPanel {
    private static final GridBagLayout LAYOUT = new GridBagLayout();
    private final List<JButton> buttons = new ArrayList<>();
    private final JToggleButton toggleButton = new JToggleButton("");
    private Timer autoCloseTimer;

    public SideBar() {
        setLayout(LAYOUT);
        initialize();
    }

    public void initialize() {
        for (ButtonEnum buttonEnum : ButtonEnum.values()) {
            createButton(buttonEnum);
        }
        toggleButton.addActionListener(_ -> toggleButtonAction(toggleButton));
        toggleButton.setContentAreaFilled(false);
        add(toggleButton, setToggleButton(0));
    }

    private void createButton(ButtonEnum buttonEnum) {
        JButton button = new JButton(buttonEnum.getTitle());
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(192, 192, 192));
        setCustomFont(button, 18, Font.BOLD);
        setCustomFont(toggleButton, 18, Font.BOLD);
        button.addActionListener(buttonEnum.getAction());
        buttons.add(button);
    }

    private void addAll() {
        GridBagConstraints g = getDefault();
        g.insets = new Insets(30, 0, 30, 0);
        toggleButton.setText("收起");
        add(toggleButton, g);

        for (int i = 0; i < buttons.size(); i++) {
            g.gridy = i + 1;
            if (i == 0) g.insets = new Insets(0, 0, 10, 0);
            else g.insets = new Insets(10, 0, 10, 0);
            buttons.get(i).setForeground(new Color(62, 62, 62, 0));
            add(buttons.get(i), g);

            if (i == buttons.size() - 1) break;

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
            applyFadeEffectSequentially(toggleOnly, true, 1, 0.3f, 0, () -> animateComponent(40, 184, () -> {
                updateSideBar(80, false);
                addAll();
                applyFadeEffectSequentially(all, true, 1, 0.3f, 0, this::refresh);
            }));
        } else {
            applyFadeEffectSequentially(all, false, 1, 0.3f, 0, () -> animateComponent(184, 40, () -> {
                toggleButton.setText("");
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
        return setFormat(null, null, new Insets(0, 0, 0, 0),
                0, 0, 0, 0, 0, 20,
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

    public enum ButtonEnum {
        WORKSPACE("工作区", _ -> {
            try {
                mainPanel.showWorkSpace(null, "KeepWorkSpace", currentPage);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }),
        SEARCH("搜索", _ -> mainPanel.showSearchPage()),
        MY_BORROWINGS("我的借阅", _ -> {
            try {
                fetchDataAndShow(new BorrowRecord(), "BorrowRecord", "借阅列表", currentPage);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }),
        MY_FAVORITES("我的收藏", _ -> {
            try {
                fetchDataAndShow(new FavoriteRecord(), "FavoriteRecord", "收藏列表", currentPage);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }),
        MY_ACCOUNT("我的账户", _ -> mainPanel.showAccountPage()),
        USER_MANAGEMENT("用户管理", _ -> {
            try {
                if (!currentUser.getRole().equals("admin")) {
                    Notification(mainFrame, "权限不足！");
                    return;
                }
                fetchDataAndShow(new User(), "User", "用户列表", currentPage);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }),
        SETTINGS("设置", _ -> mainPanel.showSettingPage()),
        ABOUT("关于", _ -> mainPanel.showAboutPage());

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

        public static <T> void fetchDataAndShow(T entity, String showType, String title, int page) throws IOException, ClassNotFoundException {
            isSearching = false;
            sortSearching = false;
            String value = currentUser.getId();
            if (entity instanceof User) value = "";
            clientUtil.sendRequest(packRequest("search", entity, "search", authResponse.getJwtToken(),
                    "userID", value, "LIKE", "0", "null", "ASC", String.valueOf(page), String.valueOf(WorkSpace.pageSize),
                    "false", "AND", "null", "false", "null", "null", "null", "null"));
            ResponsePack<?> responsePack = clientUtil.receiveResponse();
            if (responsePack.isSuccess())
                mainPanel.showWorkSpace(responsePack, showType, page);
            else {
                if (responsePack.getMessage().equals("未找到符合条件的实体")) {
                    if (page > 1) Notification(mainFrame, "没有更多了");
                    else mainPanel.showWorkSpace(null, showType, page);
                } else Notification(mainFrame, "获取" + title + "失败, 请重试 " + responsePack.getMessage());
            }
        }
    }
}