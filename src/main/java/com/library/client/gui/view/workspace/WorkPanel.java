package main.java.com.library.client.gui.view.workspace;

import main.java.com.library.client.gui.MainPage;
import main.java.com.library.common.entity.EntityList;
import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.common.entity.impl.User;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static main.java.com.library.client.gui.LoginPage.clientUtil;
import static main.java.com.library.client.gui.LoginPage.response;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.effects.HoverInfoTool.addDetailAction;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setColor;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.currentPage;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.pageSize;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class WorkPanel extends JScrollPane {
    private static final GridBagLayout LAYOUT = new GridBagLayout();
    public static final int[] START_VALUE = {150, 300, 300};
    private List<JPanel> panelList = new ArrayList<>();
    private static JPanel content = null;
    public static String showType = "Book";
    public static int totalCount = 0;
    public static List<Book> bookList = null;
    public static List<User> userList = null;
    public static List<BorrowRecord> borrowRecordList = null;
    public static boolean userOnly = true;
    public static boolean borrowingOnly = true;

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
        System.gc();
    }

    private void createSubPanels(JPanel p, int i, GridBagConstraints gbc) {
        setColor(p, null, new Color(255, 255, 255), BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        switch (showType) {
            case "Book", "Favorite" -> showBook(p, i, gbc);
            case "BorrowRecord" -> {
                if (!showBorrowRecord(p, i, gbc)) return;
            }
            case "User" -> {
                if (!showUser(p, i, gbc)) return;
            }
        }
        setFormat(p, content, new Insets(0, 0, 0, 0), 0, i, 1, 0, 0, 40, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 0, 0);
    }

    private JComponent setTextArea(String data, int width, int limit) {
        JTextArea area = new JTextArea(data);
        area.setOpaque(false);
        area.setEditable(false);
        area.setFocusable(false);
        area.setPreferredSize(new Dimension(width, 20));
        if (data.length() >= limit) {
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
        if (bookList == null && userList == null) return;

        totalCount = bookList == null ? userList.size() : bookList.size();

        for (int i = 0; i < totalCount; i++) panelList.add(new JPanel(LAYOUT));

        int startIndex = 0;
        switch (showType) {
            case "Book" -> startIndex = START_VALUE[0] + mainFrame.getWidth() - MainPage.WIDTH;
            case "BorrowRecord" -> startIndex = START_VALUE[1] + mainFrame.getWidth() - MainPage.WIDTH;
            case "User" -> startIndex = START_VALUE[2] + mainFrame.getWidth() - MainPage.WIDTH;
        }
        int newInset = Math.max(startIndex, 50);
        GridBagConstraints gbc = setFormat(null, null, new Insets(0, newInset, 0, 0), 6, 0, 20, 10, 0, 0);
        content.removeAll();
        for (int i = 0; i < Math.min(pageSize, totalCount - (currentPage - 1) * pageSize); i++) {
            int index = (currentPage - 1) * pageSize + i;
            panelList.get(index).removeAll();
            createSubPanels(panelList.get(index), index, gbc);
        }

        revalidate();
        repaint();
    }

    public void unpackResponse(ResponsePack<?> responsePack, String action) throws IOException, ClassNotFoundException {
        EntityList<?> entityList = (EntityList<?>) responsePack.getData();

        if (action.equals("BorrowRecord")) {
            borrowRecordList = (List<BorrowRecord>) entityList.entities();
            for (BorrowRecord borrowRecord : borrowRecordList) {
                clientUtil.sendRequest(packRequest("get", new Book(), borrowRecord.getBookID(), response.getJwtToken()));
                @SuppressWarnings("unchecked")
                ResponsePack<Book> response = (ResponsePack<Book>) clientUtil.receiveResponse();
                bookList.add(response.getData());
            }
        } else if (action.equals("Favourite")) {
            for (BorrowRecord borrowRecord : borrowRecordList) {
                clientUtil.sendRequest(packRequest("get", new Book(), borrowRecord.getBookID(), response.getJwtToken()));
                @SuppressWarnings("unchecked")
                ResponsePack<Book> response = (ResponsePack<Book>) clientUtil.receiveResponse();
                bookList.add(response.getData());
            }
        } else if (action.equals("User")) userList = (List<User>) entityList.entities();
        else if (action.equals("Book")) bookList = (List<Book>) entityList.entities();
        showType = action;
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

        JButton detailButton = new JButton("详细信息");
        setColor(detailButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setFormat(detailButton, p, new Insets(0, 20, 0, 0), 7, 0, 20, 10, 14, Font.BOLD);

        JButton editButton = new JButton("管理 ▼");
        setColor(editButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setFormat(editButton, p, new Insets(0, 20, 0, 0), 8, 0, 20, 10, 14, Font.BOLD);

        JPopupMenu dropdownMenu = new JPopupMenu();
        JMenuItem editInfo = new JMenuItem("编辑信息");
        JMenuItem deleteThis = new JMenuItem("删除此项");
        dropdownMenu.add(editInfo);
        dropdownMenu.add(deleteThis);

        detailButton.addActionListener(_ -> {
        }); //TODO: 显示详细信息
        editInfo.addActionListener(_ -> new EditBookDialog(mainFrame, data));
        deleteThis.addActionListener(_ -> new DeleteDialog(mainFrame, data));
        editButton.addActionListener(_ -> dropdownMenu.show(editButton, 0, editButton.getHeight()));
    }

    private boolean showBorrowRecord(JPanel p, int i, GridBagConstraints gbc) {
        BorrowRecord borrowData = borrowRecordList.get(i);
        Book bookData = bookList.get(i);
        if (borrowingOnly && borrowData.isReturned()) return false;

        Instant instant = borrowData.getBorrowDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.M.d HH:mm:ss");
        String formattedDateTime = formatter.format(instant.atZone(ZoneId.systemDefault()));

        JButton returnButton = new JButton("归还");
        JButton detailButton = new JButton("详细信息");
        setColor(returnButton, new Color(41, 98, 241), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        returnButton.addActionListener(_ -> new BorrowDialog(mainFrame, bookData.getBookID()));

        setFormat(setTextArea(bookData.getTitle(), 150, 21), p, new Insets(0, 20, 0, 0), 0, 0, 0, 0);
        setFormat(setTextArea(bookData.getAuthor(), 100, 13), p, new Insets(0, 20, 0, 0), 1, 0, 0, 0);
        setFormat(setTextArea(bookData.getPublisher(), 100, 13), p, new Insets(0, 20, 0, 0), 2, 0, 0, 0);
        setFormat(setTextArea(formattedDateTime, 100, 13), p, new Insets(0, 20, 0, 0), 3, 0, 0, 0);
        if (gbc != null) setFormat(returnButton, p, new Insets(0, START_VALUE[1], 0, 0), 6, 0, 0, 0);
        else setFormat(returnButton, p, gbc, 0, 0);
        setFormat(returnButton, p, new Insets(0, 20, 0, 0), 7, 0, 0, 0);

        detailButton.addActionListener(_ -> {
        }); //TODO: 显示详细信息

        return true;
    }

    private boolean showUser(JPanel p, int i, GridBagConstraints gbc) {
        User userData = userList.get(i);
        if (userOnly && userData.getRole().equals("admin")) return false;

        setFormat(setTextArea(userData.getUserID(), 150, 21), p, new Insets(0, 20, 0, 0), 0, 0, 0, 0);
        setFormat(setTextArea(userData.getRole(), 100, 13), p, new Insets(0, 20, 0, 0), 1, 0, 0, 0);
        setFormat(setTextArea(userData.getUsername(), 100, 13), p, new Insets(0, 20, 0, 0), 2, 0, 0, 0);
        setFormat(setTextArea(userData.getPassword(), 100, 13), p, new Insets(0, 20, 0, 0), 3, 0, 0, 0);
        setFormat(setTextArea(userData.getEmail(), 100, 13), p, new Insets(0, 20, 0, 0), 4, 0, 0, 0);
        setFormat(setTextArea(userData.getPhone(), 100, 13), p, new Insets(0, 20, 0, 0), 5, 0, 0, 0);

        JButton editButton = new JButton("编辑信息");
        JButton roleButton = new JButton("更改权限");
        JButton deleteButton = new JButton("删除用户");

        if (gbc != null) setFormat(editButton, p, gbc, 0, 0);
        else setFormat(editButton, p, new Insets(0, START_VALUE[2], 0, 0), 6, 0, 0, 0);

        setFormat(roleButton, p, new Insets(0, 20, 0, 0), 7, 0, 0, 0);
        setFormat(deleteButton, p, new Insets(0, 20, 0, 0), 8, 0, 0, 0);

        editButton.addActionListener(_ -> new EditUserDialog(mainFrame, userData));
        return true;
    }
}