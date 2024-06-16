package main.java.com.library.client.gui.view.settings;

import main.java.com.library.client.gui.LoginPage;
import main.java.com.library.common.entity.impl.User;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static main.java.com.library.client.gui.LoginPage.*;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.effects.NotificationUtil.Notification;
import static main.java.com.library.client.gui.view.SideBar.ButtonEnum.fetchDataAndShow;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.currentPage;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class DeleteAccountDialog extends JDialog {
    private JButton confirmButton;
    private JLabel timerLabel;
    private Timer timer;
    private int countdown = 5;

    public DeleteAccountDialog(JFrame parentFrame) {
        super(parentFrame, "删除账户", true);
        setLocationRelativeTo(parentFrame);
        initialize();
    }

    private void initialize() {
        setSize(300, 150);
        setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("确定要删除你的账户吗");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        confirmButton = new JButton("确认");
        confirmButton.setEnabled(false);
        JButton cancelButton = new JButton("取消");

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        timerLabel = new JLabel("请在 5 秒后确认");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(timerLabel, BorderLayout.NORTH);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                countdown--;
                timerLabel.setText("请在 " + countdown + " 秒后确认");
                if (countdown <= 0) {
                    confirmButton.setEnabled(true);
                    timerLabel.setText("请确认删除账户");
                    timer.cancel();
                }
            }
        }, 1000, 1000);

        confirmButton.addActionListener(_ -> {
            User u = new User();
            u.setUserID(currentUser.getUserID());
            try {
                clientUtil.sendRequest(packRequest("delete", u, "delete", response.getJwtToken()));
                ResponsePack<?> response = clientUtil.receiveResponse();
                if (response.isSuccess()) {
                    Notification(mainFrame, "删除成功！");
                    fetchDataAndShow(new User(), "User", "用户列表", currentPage);
                    JOptionPane.showMessageDialog(DeleteAccountDialog.this, "账户已删除，请重新登录。");
                    dispose();
                    mainFrame.dispose();
                    mainPage.deleteAll();
                    mainFrame = null;
                    mainPage = null;
                    currentUser = null;
                    password = null;
                    LoginPage.startUp();
                } else JOptionPane.showMessageDialog(mainFrame, "删除失败！请检查您是否仍有未归还书籍。");
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        cancelButton.addActionListener(_ -> dispose());
        setVisible(true);
        setLocationRelativeTo(getParent());
    }
}
