package main.java.com.library.client.gui.view.workspace;

import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static main.java.com.library.client.gui.LoginPage.authResponse;
import static main.java.com.library.client.gui.LoginPage.clientUtil;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.effects.NotificationUtil.Notification;
import static main.java.com.library.client.gui.impl.ToolsIMPL.*;
import static main.java.com.library.client.gui.view.workspace.BottomPanel.refreshPage;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class DeleteDialog extends JDialog {
    public DeleteDialog(JFrame parent, Book data) {
        super(parent, "删除确认", true);
        setDialog(this, 300, 400, new BorderLayout(), false, JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JButton confirmButton = new JButton("删除");
        JButton cancelButton = new JButton("取消");

        setColor(cancelButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(confirmButton, new Color(229, 84, 59), new Color(230, 230, 230), BorderFactory.createEmptyBorder());

        setFormat(new JLabel("确认删除书籍"), panel, new Insets(10, 10, 10, 10), 0, 0, 1, 1, 0, 0, 0, GridBagConstraints.CENTER, 18, Font.BOLD);
        easySetFormat(new JLabel("ID　    　 " + data.getId()), panel, 1);
        easySetFormat(new JLabel("书名  　   " + data.getTitle()), panel, 2);
        easySetFormat(new JLabel("作者    　 " + data.getAuthor()), panel, 3);
        easySetFormat(new JLabel("出版社     " + data.getPublisher()), panel, 4);
        easySetFormat(new JLabel("ISBN 　   " + data.getISBN()), panel, 5);
        easySetFormat(new JLabel("总存量  　" + (data.getCount())), panel, 6);
        easySetFormat(new JLabel("剩余  　   " + (data.getAvailableCount())), panel, 7);
        easySetFormat(new JLabel("状态        " + data.getStatus()), panel, 8);
        setFormat(buttonPanel, panel, new Insets(10, 10, 10, 10), 0, 9, 0, 0);
        setFormat(confirmButton, buttonPanel, new Insets(10, 10, 10, 10), 0, 0, 1, 1, 0, 20, 0, 1, 14, Font.BOLD);
        setFormat(cancelButton, buttonPanel, new Insets(10, 100, 10, 10), 1, 0, 1, 1, 0, 20, 0, 1, 14, Font.BOLD);

        cancelButton.addActionListener(_ -> dispose());

        confirmButton.addActionListener(_ -> {
            try {
                clientUtil.sendRequest(packRequest("delete", data, "delete", authResponse.getJwtToken()));
                ResponsePack<?> deleteResponse = clientUtil.receiveResponse();
                if (deleteResponse.isSuccess()) {
                    Notification(mainFrame, "删除成功！");
                    refreshPage();
                } else
                    Notification(mainFrame, "操作失败 " + deleteResponse.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void easySetFormat(JComponent component, JPanel panel, int gridy) {
        setFormat(component, panel, new Insets(10, 10, 10, 10), 0, gridy, 1, 0, 0, 0, 0, 1, 14, 0);
    }
}