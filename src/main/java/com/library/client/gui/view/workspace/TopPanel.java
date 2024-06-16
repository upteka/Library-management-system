package main.java.com.library.client.gui.view.workspace;

import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static main.java.com.library.client.gui.LoginPage.*;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.MainPage.mainPanel;
import static main.java.com.library.client.gui.effects.NotificationUtil.Notification;
import static main.java.com.library.client.gui.impl.ToolsIMPL.*;
import static main.java.com.library.client.gui.view.workspace.WorkPanel.*;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.currentPage;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class TopPanel extends JPanel {
    public TopPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = setFormat(null, null, new Insets(0, 0, 0, 0),
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);

        if (showType.equals("BorrowRecord")) {
            addButton("正在借阅", gbc, 0, _ -> {
                try {
                    if (borrowingOnly) return;
                    borrowingOnly = true;
                    sortedBorrowRequest(1);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            addButton("归还记录", gbc, 1, _ -> {
                try {
                    if (!borrowingOnly) return;
                    borrowingOnly = false;
                    sortedBorrowRequest(1);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        } else if (showType.equals("Book"))
            addButton("添加书籍", gbc, 0, _ -> new EditBookDialog(mainFrame, new Book(), true));
    }

    private void addButton(String text, GridBagConstraints gbc, int gridx, ActionListener action) {
        JButton button = new JButton(text);
        setColor(button, null, new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setCustomFont(button, 16, Font.PLAIN);
        button.addActionListener(action);
        gbc.gridx = gridx;
        add(button, gbc);
    }

    public void sortedBorrowRequest(int page) throws IOException, ClassNotFoundException {
        sortSearching = true;
        isSearching = false;
        String order = borrowingOnly ? "ASC" : "DESC";
        clientUtil.sendRequest(packRequest("search", new BorrowRecord(), "search", response.getJwtToken(),
                "userID", currentUser.getId(), "LIKE", "0", "returned", order, String.valueOf(page), String.valueOf(WorkSpace.pageSize),
                "false", "AND", "null", "false", "null", "null", "null", "null"));
        ResponsePack<?> response = clientUtil.receiveResponse();
        if (response.isSuccess())
            mainPanel.showWorkSpace(response, showType, currentPage);
        else {
            if (response.getMessage().equals("未找到符合条件的实体")) {
                if (currentPage > 1) Notification(mainFrame, "没有更多了");
                else mainPanel.showWorkSpace(null, showType, currentPage);
            } else Notification(mainFrame, "获取失败, 请重试！\n" + response.getMessage());
        }
    }
}
