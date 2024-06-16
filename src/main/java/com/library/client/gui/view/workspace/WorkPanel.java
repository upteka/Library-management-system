package main.java.com.library.client.gui.view.workspace;

import main.java.com.library.client.gui.MainPage;
import main.java.com.library.common.entity.EntityList;
import main.java.com.library.common.entity.impl.*;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static main.java.com.library.client.gui.LoginPage.*;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.effects.HoverInfoTool.addDetailAction;
import static main.java.com.library.client.gui.effects.NotificationUtil.Notification;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setColor;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;
import static main.java.com.library.client.gui.view.workspace.BottomPanel.refreshPage;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.bottomPanel;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.pageSize;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class WorkPanel extends JScrollPane {
    private static final GridBagLayout LAYOUT = new GridBagLayout();
    public static final int[] START_VALUE = {100, 100, 100};
    private List<JPanel> panelList = new ArrayList<>();
    private static JPanel content;
    public static String showType = "";
    public static int totalCount = 0;
    public static List<Book> bookList = null;
    public static List<User> userList = null;
    public static List<BorrowRecord> borrowRecordList = null;
    HashMap<String, String> favoriteMap = new HashMap<>();
    public static boolean theresNothing = true;
    public static boolean borrowingOnly = false;
    public static boolean sortSearching = false;
    public static boolean isSearching = false;

    public WorkPanel() {
        UIManager.put("ScrollBar.showButtons", true);
        content = new JPanel(LAYOUT);
        setViewportView(content);
        getVerticalScrollBar().setUnitIncrement(16);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public void initialize() {
        updateLayout();
    }

    public void deleteAll() {
        content.removeAll();
        removeAll();
        panelList = null;
        content = null;
        bookList = null;
        userList = null;
        borrowRecordList = null;
        favoriteMap.clear();
        System.gc();
    }

    private void createSubPanels(JPanel p, int i, GridBagConstraints gbc) {
        setColor(p, null, new Color(255, 255, 255), BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        switch (showType) {
            case "Book", "FavoriteRecord" -> showBook(p, i, gbc);
            case "BorrowRecord" -> {
                if (!showBorrowRecord(p, i, gbc)) return;
            }
            case "User" -> {
                if (!showUser(p, i, gbc)) return;
            }
        }
        setFormat(p, content, new Insets(0, 0, 0, 0), 0, i, 1, 0, 0, 40, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);
    }

    public static JComponent setTextArea(String data, int width, int limit) {
        JTextArea area = new JTextArea(data);
        area.setOpaque(false);
        area.setEditable(false);
        area.setFocusable(false);
        area.setPreferredSize(new Dimension(width, 20));
        if (data != null && data.length() >= limit) {
            area.setText(data.substring(0, limit) + "...");
            addDetailAction(area, data);
        }
        return area;
    }

    private JPanel setStatusColor(String data) {
        JPanel statusPanel = new JPanel(LAYOUT);
        JLabel statusArea = new JLabel();
        statusArea.setPreferredSize(new Dimension(55, 20));
        if (data.equals("available")) {
            statusArea.setText("Available");
            statusArea.setForeground(new Color(45, 88, 42));
            statusPanel.setBackground(new Color(222, 234, 221));
        } else {
            statusArea.setText("Borrowed");
            statusArea.setForeground(new Color(130, 28, 18));
            statusPanel.setBackground(new Color(251, 228, 224));
        }
        statusPanel.add(statusArea);
        return statusPanel;
    }

    public void updateLayout() {
        if (theresNothing) {
            content.removeAll();
            content.add(new JLabel("There's nothing here."));
            return;
        }
        if (bookList == null && userList == null) return;

        if (userList != null) {
            totalCount = userList.size();
        } else if (borrowRecordList != null) {
            totalCount = borrowRecordList.size();
        } else totalCount = bookList.size();

        panelList.clear();
        for (int i = 0; i < totalCount; i++) panelList.add(new JPanel(LAYOUT));

        int startIndex = 0;
        switch (showType) {
            case "Book", "FavoriteRecord" -> startIndex = START_VALUE[0] + mainFrame.getWidth() - MainPage.WIDTH;
            case "BorrowRecord" -> startIndex = START_VALUE[1] + mainFrame.getWidth() - MainPage.WIDTH;
            case "User" -> startIndex = START_VALUE[2] + mainFrame.getWidth() - MainPage.WIDTH;
        }
        int newInset = Math.max(startIndex, 50);
        GridBagConstraints gbc = setFormat(null, null, new Insets(0, newInset, 0, 0), 6, 0, 20, 10, 0, 0);
        content.removeAll();
        for (int i = 0; i < totalCount; i++) {
            panelList.get(i).removeAll();
            createSubPanels(panelList.get(i), i, gbc);
        }

        bottomPanel.updatePageLabel();
        revalidate();
        repaint();
    }

    public void unpackResponse(ResponsePack<?> responsePack, String action) throws IOException, ClassNotFoundException {
        if (responsePack == null) return;

        EntityList<?> entityList = (EntityList<?>) responsePack.getData();

        if (action.equals("User")) userList = new ArrayList<>();
        if (action.equals("BorrowRecord")) borrowRecordList = new ArrayList<>();
        if (action.equals("Book") || action.equals("FavoriteRecord") || action.equals("BorrowRecord"))
            bookList = new ArrayList<>();

        switch (action) {
            case "BorrowRecord" -> {
                borrowRecordList = (List<BorrowRecord>) entityList.entities();
                for (BorrowRecord borrowRecord : borrowRecordList) {
                    Book book = new Book();
                    book.setBookID(borrowRecord.getBookID());
                    clientUtil.sendRequest(packRequest("get", book, "get", response.getJwtToken()));
                    @SuppressWarnings("unchecked")
                    ResponsePack<Book> bookResponse = (ResponsePack<Book>) clientUtil.receiveResponse();
                    bookList.add(bookResponse.getData());
                }
            }
            case "FavoriteRecord" -> {
                @SuppressWarnings("unchecked")
                List<FavoriteRecord> favoriteList = (List<FavoriteRecord>) entityList.entities();
                for (FavoriteRecord favoriteRecord : favoriteList) {
                    Book book = new Book();
                    book.setBookID(favoriteRecord.getBookID());
                    clientUtil.sendRequest(packRequest("get", book, "get", response.getJwtToken()));
                    @SuppressWarnings("unchecked")
                    ResponsePack<Book> response = (ResponsePack<Book>) clientUtil.receiveResponse();
                    bookList.add(response.getData());
                    favoriteMap.put(response.getData().getBookID(), favoriteRecord.getId());
                }
            }
            case "Book" -> {
                bookList = (List<Book>) entityList.entities();
                clientUtil.sendRequest(packRequest("search", new FavoriteRecord(), "search", response.getJwtToken(),
                        "userID", currentUser.getId(), "LIKE", "0", "null", "ASC", "1", String.valueOf(pageSize),
                        "false", "AND", "null", "false", "null", "null", "null", "null"));
                EntityList<?> list = (EntityList<?>) clientUtil.receiveResponse().getData();
                if (list == null) break;
                @SuppressWarnings("unchecked")
                List<FavoriteRecord> favoriteList = (List<FavoriteRecord>) list.entities();
                for (FavoriteRecord record : favoriteList)
                    favoriteMap.put(record.getBookID(), record.getId());
            }
            case "User" -> userList = (List<User>) entityList.entities();
        }

        showType = action;
        theresNothing = false;
    }

    private void showBook(JPanel p, int i, GridBagConstraints gbc) {
        Book data = bookList.get(i);

        setFormat(setTextArea(data.getTitle(), 130, 20), p, new Insets(0, 20, 0, 0), 0, 0, 0, 0);
        setFormat(setTextArea(data.getAuthor(), 100, 13), p, new Insets(0, 20, 0, 0), 1, 0, 0, 0);
        setFormat(setTextArea(data.getPublisher(), 100, 13), p, new Insets(0, 20, 0, 0), 2, 0, 0, 0);
        setFormat(setTextArea("余量 " + data.getCount(), 60, 6), p, new Insets(0, 20, 0, 0), 3, 0, 0, 0);
        setFormat(setStatusColor(data.getStatus()), p, new Insets(0, 40, 0, 0), 4, 0, 10, 10, 0, 0);

        JButton borrowButton = new JButton("借阅");
        if (data.getStatus().equals("available")) {
            setColor(borrowButton, new Color(41, 98, 241), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
            borrowButton.addActionListener(_ -> new BorrowDialog(mainFrame, data.getBookID()));
        } else {
            setColor(borrowButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
            borrowButton.setEnabled(false);
        }
        if (gbc != null) setFormat(borrowButton, p, gbc, 14, Font.BOLD);
        else setFormat(borrowButton, p, new Insets(0, START_VALUE[0], 0, 0), 6, 0, 20, 10, 14, Font.BOLD);

        JButton favoriteButton = favoriteMap.get(data.getBookID()) == null ? new JButton("收藏") : new JButton("已收藏");
        favoriteButton.setPreferredSize(new Dimension(35, 20));
        setColor(favoriteButton, new Color(255, 179, 0), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setFormat(favoriteButton, p, new Insets(0, 20, 0, 0), 7, 0, 20, 10, 14, Font.BOLD);

        JButton detailButton = new JButton("详细信息");
        setColor(detailButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setFormat(detailButton, p, new Insets(0, 20, 0, 0), 8, 0, 20, 10, 14, Font.BOLD);

        JButton editButton = new JButton("管理 ▼");
        setColor(editButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setFormat(editButton, p, new Insets(0, 20, 0, 0), 9, 0, 20, 10, 14, Font.BOLD);

        JPopupMenu dropdownMenu = new JPopupMenu();
        JMenuItem editInfo = new JMenuItem("编辑信息");
        JMenuItem deleteThis = new JMenuItem("删除此项");
        dropdownMenu.add(editInfo);
        dropdownMenu.add(deleteThis);
        List<String[]> details = new ArrayList<>();
        details.add(new String[]{data.getId(), "ID"});
        details.add(new String[]{data.getTitle(), "书名"});
        details.add(new String[]{data.getAuthor(), "作者"});
        details.add(new String[]{data.getPublisher(), "出版社"});
        details.add(new String[]{data.getIntroduction(), "简介"});
        details.add(new String[]{data.getISBN(), "ISBN"});
        details.add(new String[]{data.getStatus(), "状态"});
        details.add(new String[]{String.valueOf(data.getCount()), "总量"});
        details.add(new String[]{String.valueOf(data.getAvailableCount()), "余量"});

        detailButton.addActionListener(_ -> new showDetailDialog(mainFrame, details));
        editInfo.addActionListener(_ -> new EditBookDialog(mainFrame, data, false));
        deleteThis.addActionListener(_ -> new DeleteDialog(mainFrame, data));
        editButton.addActionListener(_ -> dropdownMenu.show(editButton, 0, editButton.getHeight()));

        favoriteButton.addActionListener(_ -> {
            try {
                FavoriteRecord favoriteRecord = new FavoriteRecord(data.getBookID());
                String action = favoriteButton.getText().equals("收藏") ? "favorite" : "unfavorite";
                String message = favoriteButton.getText().equals("收藏") ? "收藏" : "取消收藏";
                if (favoriteButton.getText().equals("已收藏"))
                    favoriteRecord.setFavoriteID(favoriteMap.get(data.getBookID()));
                clientUtil.sendRequest(packRequest("favorite", favoriteRecord, action, response.getJwtToken(), action));
                ResponsePack<?> favoriteResponse = clientUtil.receiveResponse();
                if (favoriteResponse.isSuccess()) {
                    if (action.equals("favorite")) favoriteMap.put(data.getBookID(), favoriteRecord.getId());
                    Notification(mainFrame, "已" + message);
                    favoriteButton.setText(favoriteButton.getText().equals("收藏") ? "已收藏" : "收藏");
                } else
                    Notification(mainFrame, "操作失败！\n" + response.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        deleteThis.addActionListener(_ -> {
            try {
                clientUtil.sendRequest(packRequest("delete", data, "delete", response.getJwtToken()));
                ResponsePack<?> deleteResponse = clientUtil.receiveResponse();
                if (deleteResponse.isSuccess()) {
                    Notification(mainFrame, "删除成功！");
                    refreshPage();
                } else
                    Notification(mainFrame, "操作失败！\n" + response.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean showBorrowRecord(JPanel p, int i, GridBagConstraints gbc) {
        BorrowRecord borrowData = borrowRecordList.get(i);
        Book bookData = new Book();
        for (Book book : bookList)
            if (book.getBookID().equals(borrowData.getBookID())) {
                bookData = book;
                break;
            }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日  HH:mm:ss");
        String borrowTime = formatter.format(borrowData.getBorrowDate().atZone(ZoneId.systemDefault()));
        String returnTime = formatter.format(borrowData.getReturnDate().atZone(ZoneId.systemDefault()));
        borrowTime = "于" + borrowTime + "借阅";
        if (!borrowData.isReturned()) returnTime = "请在" + returnTime + "前归还";
        else returnTime = "已于" + returnTime + "归还";

        JButton returnButton = new JButton("归还");
        JButton detailButton = new JButton("详细信息");
        returnButton.setPreferredSize(new Dimension(35, 20));
        setColor(returnButton, new Color(41, 98, 241), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(detailButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        if (borrowData.isReturned()) {
            returnButton.setEnabled(false);
            returnButton.setText("已归还");
            setColor(returnButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        }

        setFormat(setTextArea(bookData.getTitle(), 120, 22), p, new Insets(0, 0, 0, 0), 0, 0, 0, 0);
        setFormat(setTextArea(bookData.getAuthor(), 120, 22), p, new Insets(0, 0, 0, 0), 1, 0, 0, 0);
        setFormat(setTextArea(borrowTime, 200, 99), p, new Insets(0, 20, 0, 0), 3, 0, 0, 0);
        setFormat(setTextArea(returnTime, 220, 99), p, new Insets(0, 20, 0, 0), 4, 0, 0, 0);
        if (gbc != null) setFormat(returnButton, p, gbc, 14, Font.BOLD);
        else setFormat(returnButton, p, new Insets(0, START_VALUE[1], 0, 0), 6, 0, 20, 10, 14, Font.BOLD);
        setFormat(detailButton, p, new Insets(0, 20, 0, 0), 8, 0, 20, 10, 14, Font.BOLD);

        List<String[]> details = new ArrayList<>();
        details.add(new String[]{bookData.getId(), "ID"});
        details.add(new String[]{bookData.getTitle(), "书名"});
        details.add(new String[]{bookData.getAuthor(), "作者"});
        details.add(new String[]{bookData.getPublisher(), "出版社"});
        details.add(new String[]{bookData.getIntroduction(), "简介"});
        details.add(new String[]{bookData.getISBN(), "ISBN"});
        details.add(new String[]{borrowData.getBorrowID(), "借阅ID"});
        details.add(new String[]{getTimeString(borrowData.getBorrowDate()), "借阅时间"});
        details.add(new String[]{getTimeString(borrowData.getReturnDate()), "归还时间"});
        detailButton.addActionListener(_ -> new showDetailDialog(mainFrame, details));

        returnButton.addActionListener(_ -> {
            try {
                clientUtil.sendRequest(packRequest("return", new ReturnRecord(borrowData.getBorrowID()), "return", response.getJwtToken()));
                ResponsePack<?> returnResponse = clientUtil.receiveResponse();
                if (returnResponse.isSuccess()) {
                    Notification(mainFrame, "归还成功！");
                    borrowData.setReturned(true);
                    returnButton.setText("已归还");
                    returnButton.setEnabled(false);
                    setColor(returnButton, new Color(0, 255, 32), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
                } else
                    Notification(mainFrame, "归还失败！\n" + returnResponse.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }

    private boolean showUser(JPanel p, int i, GridBagConstraints gbc) {
        User userData = userList.get(i);
        String userName = userData.getUsername();
        if (userData.getUserID().equals(currentUser.getUserID()))
            userName = "(您) " + userData.getUsername();

        setFormat(setTextArea(userData.getUserID(), 100, 13), p, new Insets(0, 20, 0, 0), 0, 0, 0, 0);
        setFormat(setTextArea(userData.getRole(), 100, 13), p, new Insets(0, 20, 0, 0), 1, 0, 0, 0);
        setFormat(setTextArea(userName, 100, 13), p, new Insets(0, 20, 0, 0), 2, 0, 0, 0);
        setFormat(setTextArea(userData.getEmail(), 100, 13), p, new Insets(0, 20, 0, 0), 4, 0, 0, 0);
        setFormat(setTextArea(userData.getPhone(), 100, 13), p, new Insets(0, 20, 0, 0), 5, 0, 0, 0);

        JButton editButton = new JButton("编辑信息");
        JButton roleButton = new JButton("更改权限");
        JButton deleteButton = new JButton("删除用户");

        if (userData.getUserID().equals(currentUser.getUserID())) {
            editButton.setEnabled(false);
            roleButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }

        setColor(editButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(roleButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(deleteButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());

        if (gbc != null) setFormat(editButton, p, gbc, 14, Font.BOLD);
        else setFormat(editButton, p, new Insets(0, START_VALUE[2], 0, 0), 6, 0, 20, 10, 14, Font.BOLD);

        setFormat(roleButton, p, new Insets(0, 20, 0, 0), 7, 0, 20, 10, 14, Font.BOLD);
        setFormat(deleteButton, p, new Insets(0, 20, 0, 0), 8, 0, 20, 10, 14, Font.BOLD);

        editButton.addActionListener(_ -> {
            new EditUserDialog(mainFrame, userData);
        });
        roleButton.addActionListener(_ -> {
            try {
                String newRole = userData.getRole().equals("admin") ? "user" : "admin";
                User u = new User();
                u.setRole(newRole);
                clientUtil.sendRequest(packRequest("update", u, "update", response.getJwtToken()));
                ResponsePack<?> response = clientUtil.receiveResponse();
                if (response.isSuccess()) {
                    userData.setRole(newRole);
                    Notification(mainFrame, "修改成功！");
                    refreshPage();
                } else
                    Notification(mainFrame, "修改失败！\n" + response.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        deleteButton.addActionListener(_ -> {
            try {
                if (userData.getUserID().equals(currentUser.getUserID())) {
                    Notification(mainFrame, "请在设置页面删除您的账户");
                    return;
                }
                User u = new User();
                u.setUserID(userData.getUserID());
                clientUtil.sendRequest(packRequest("delete", u, "delete", response.getJwtToken()));
                ResponsePack<?> response = clientUtil.receiveResponse();
                if (response.isSuccess()) {
                    Notification(mainFrame, "删除成功！");
                    refreshPage();
                } else
                    Notification(mainFrame, "删除失败\n" + response.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }

    public static void showNull(String action) {
        showType = action;
        theresNothing = true;
    }

    public static String getTimeString(Instant instant) {
        var zonedDateTime = instant.atZone(ZoneId.systemDefault());
        var formatter = DateTimeFormatter.ofPattern("yyyy年M月d日, HH:mm:ss", Locale.CHINESE);
        return zonedDateTime.format(formatter);
    }
}