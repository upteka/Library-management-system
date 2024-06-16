package main.java.com.library.client.gui.view.settings;

import main.java.com.library.client.gui.LoginPage;
import main.java.com.library.client.gui.effects.IntegerTextField;
import main.java.com.library.client.gui.view.workspace.WorkSpace;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.LoginPage.*;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.effects.NotificationUtil.Notification;
import static main.java.com.library.client.gui.effects.NotificationUtil.delayTime;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.bottomPanel;

public class SettingPanel extends JPanel {
    private static final GridBagLayout LAYOUT = new GridBagLayout();

    public SettingPanel() {
        setLayout(LAYOUT);
        JPanel pagePanel = new JPanel(LAYOUT);
        JPanel setPagePanel = new JPanel(LAYOUT);
        JPanel setTimePanel = new JPanel(LAYOUT);
        JPanel timePanelSettings = new JPanel(LAYOUT);
        JPanel otherPanel = new JPanel(LAYOUT);
        JPanel setOtherPanel = new JPanel(LAYOUT);

        JLabel pageLabel = new JLabel("每页显示数量");
        JSeparator separator = new JSeparator();

        IntegerTextField itemsPerPage = new IntegerTextField(1, Integer.MAX_VALUE, 10);
        JButton savePageNumber = new JButton("保存");
        savePageNumber.putClientProperty("JButton.buttonType", "roundRect");
        JSlider pageSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 10);

        JLabel notificationLabel = new JLabel("弹框时长设置 (单位：毫秒)");
        JSeparator separator0 = new JSeparator();

        IntegerTextField timeDelay = new IntegerTextField(1, Integer.MAX_VALUE, 2000);
        JButton saveTimeDelay = new JButton("保存");
        JButton testNotification = new JButton("测试弹框");
        testNotification.putClientProperty("JButton.buttonType", "roundRect");
        saveTimeDelay.putClientProperty("JButton.buttonType", "roundRect");
        JSlider timeSlider = new JSlider(JSlider.HORIZONTAL, 1, 10000, 2000);

        JLabel otherLabel = new JLabel("用户设置");
        JSeparator separator1 = new JSeparator();

        JButton logout = new JButton("注销");
        JButton deleteAccount = new JButton("删除账户");
        logout.putClientProperty("JButton.buttonType", "roundRect");
        deleteAccount.putClientProperty("JButton.buttonType", "roundRect");
        deleteAccount.setForeground(Color.RED);
        JLabel userType = new JLabel("当前用户类型: " + currentUser.getRole());


        setFormat(pageLabel, pagePanel, new Insets(0, 0, 0, 0),
                0, 0, 16, Font.BOLD);
        setFormat(separator, pagePanel, new Insets(0, 0, 0, 0),
                1, 0, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(pagePanel, this, new Insets(0, 20, 20, 0),
                0, 1, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);

        setFormat(pageSlider, setPagePanel, new Insets(0, 20, 0, 0),
                0, 0, 0, 0);
        setFormat(itemsPerPage, setPagePanel, new Insets(0, 20, 0, 0),
                1, 0, 0, 0);
        setFormat(savePageNumber, setPagePanel, new Insets(0, 20, 0, 0),
                2, 0, 0, 0);
        setFormat(new JPanel(), setPagePanel, new Insets(0, 20, 0, 0),
                3, 0, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(setPagePanel, this, new Insets(0, 0, 20, 0),
                0, 2, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);

        setFormat(notificationLabel, setTimePanel, new Insets(0, 0, 0, 0),
                0, 0, 16, Font.BOLD);
        setFormat(separator0, setTimePanel, new Insets(0, 0, 0, 0),
                1, 0, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(setTimePanel, this, new Insets(0, 20, 20, 0),
                0, 3, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);

        setFormat(timeSlider, timePanelSettings, new Insets(0, 20, 0, 0),
                0, 0, 0, 0);
        setFormat(timeDelay, timePanelSettings, new Insets(0, 20, 0, 0),
                1, 0, 0, 0);
        setFormat(saveTimeDelay, timePanelSettings, new Insets(0, 20, 0, 0),
                2, 0, 0, 0);
        setFormat(testNotification, timePanelSettings, new Insets(0, 20, 0, 0),
                3, 0, 0, 0);
        setFormat(new JPanel(), timePanelSettings, new Insets(0, 20, 0, 0),
                4, 0, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(timePanelSettings, this, new Insets(0, 0, 20, 0),
                0, 4, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);

        setFormat(otherLabel, otherPanel, new Insets(0, 0, 0, 0),
                0, 0, 16, Font.BOLD);
        setFormat(separator1, otherPanel, new Insets(0, 0, 0, 0),
                1, 0, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(otherPanel, this, new Insets(0, 20, 20, 0),
                0, 5, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);


        setFormat(userType, setOtherPanel, new Insets(0, 20, 0, 10),
                0, 0, 16, 0);
        setFormat(logout, setOtherPanel, new Insets(0, 20, 0, 10),
                1, 0, 0, 0);
        setFormat(deleteAccount, setOtherPanel, new Insets(0, 20, 0, 10),
                2, 0, 0, 0);
        setFormat(new JPanel(), setOtherPanel, new Insets(0, 20, 0, 0),
                4, 0, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(setOtherPanel, this, new Insets(0, 20, 20, 0),
                0, 6, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);


        savePageNumber.addActionListener(_ -> {
            if (itemsPerPage.isValidInput()) {
                int value = Integer.parseInt(itemsPerPage.getText());
                WorkSpace.pageSize = value;
                WorkSpace.currentPage = 1;
                bottomPanel.updatePageLabel();
                Notification(mainFrame, "每页数目已设为 " + value);
            } else Notification(mainFrame, "请输入一个正整数");
        });
        saveTimeDelay.addActionListener(_ -> {
            if (timeDelay.isValidInput()) {
                int value = Integer.parseInt(timeDelay.getText());
                Notification(mainFrame, "弹框时长已设为 " + value / 1000.0 + " 秒");
                delayTime = value;
            } else Notification(mainFrame, "请输入一个正整数");
        });
        pageSlider.addChangeListener(_ -> itemsPerPage.setText("" + pageSlider.getValue()));
        timeSlider.addChangeListener(_ -> timeDelay.setText("" + timeSlider.getValue()));
        logout.addActionListener(_ -> {
            Notification(mainFrame, "注销成功");
            mainFrame.dispose();
            mainPage.deleteAll();
            mainFrame = null;
            mainPage = null;
            currentUser = null;
            password = null;
            System.gc();
            LoginPage.startUp();
        });
        testNotification.addActionListener(_ -> Notification(mainFrame, "目前弹框时长 " + delayTime / 1000.0 + " 秒"));
        deleteAccount.addActionListener(_ -> new DeleteAccountDialog(mainFrame));
    }
}
