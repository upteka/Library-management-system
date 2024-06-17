package main.java.com.library.client.gui.view.workspace;

import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.common.entity.impl.FavoriteRecord;
import main.java.com.library.common.entity.impl.User;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.MainPage.mainPanel;
import static main.java.com.library.client.gui.effects.NotificationUtil.Notification;
import static main.java.com.library.client.gui.impl.ToolsIMPL.*;
import static main.java.com.library.client.gui.view.SideBar.ButtonEnum.fetchDataAndShow;
import static main.java.com.library.client.gui.view.search.SearchPanel.searchRequest;
import static main.java.com.library.client.gui.view.workspace.WorkPanel.*;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.currentPage;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.topPanel;

public class BottomPanel extends JPanel {
    private final JButton firstPage = new JButton("首页");
    private final JButton pageNext = new JButton("下一页");
    private final JButton pagePrevious = new JButton("上一页");
    private final JPanel pagePanel = new JPanel(new GridBagLayout());
    private final JLabel pageLabel = new JLabel();
    private static final HashMap<String, ShowTypeData<?>> showTypeMap = new HashMap<>();

    public record ShowTypeData<T>(T entity, String title) {
    }

    public BottomPanel() {
        super(new GridBagLayout());

        showTypeMap.put("Book", new ShowTypeData<>(new Book(), "书籍"));
        showTypeMap.put("BorrowRecord", new ShowTypeData<>(new BorrowRecord(), "借阅记录"));
        showTypeMap.put("FavoriteRecord", new ShowTypeData<>(new FavoriteRecord(), "收藏列表"));
        showTypeMap.put("User", new ShowTypeData<>(new User(), "用户信息"));

        initialize();
        setFormat(firstPage, this, new Insets(0, 0, 0, 0),
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 16, Font.BOLD);
        setFormat(pagePrevious, this, new Insets(0, 0, 0, 0),
                1, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 16, Font.BOLD);
        setFormat(pagePanel, this, new Insets(0, 0, 0, 0),
                2, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);
        setFormat(pageNext, this, new Insets(0, 0, 0, 0),
                3, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 16, Font.BOLD);
    }

    private void initialize() {
        setColor(pageNext, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(pagePrevious, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(firstPage, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());

        setCustomFont(pageLabel, 16, Font.BOLD);
        pagePanel.add(pageLabel);

        firstPage.addActionListener(_ -> {
            currentPage = 1;
            try {
                if (isSearching)
                    searchNextOrPrevious(1);
                else if (sortSearching)
                    topPanel.sortedBorrowRequest(1);
                else if (!showType.isEmpty())
                    fetchDataAndShow(showTypeMap.get(showType).entity, showType, showTypeMap.get(showType).title, 1);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        pageNext.addActionListener(_ -> {
            try {
                if (isSearching)
                    searchNextOrPrevious(currentPage + 1);
                else if (sortSearching)
                    topPanel.sortedBorrowRequest(currentPage + 1);
                else if (!showType.isEmpty())
                    fetchDataAndShow(showTypeMap.get(showType).entity, showType, showTypeMap.get(showType).title, currentPage + 1);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        pagePrevious.addActionListener(_ -> {
            try {
                if (currentPage == 1) {
                    Notification(mainFrame, "已经是第一页了！");
                    return;
                }
                if (isSearching) searchNextOrPrevious(currentPage - 1);
                else if (sortSearching)
                    topPanel.sortedBorrowRequest(currentPage - 1);
                else if (!showType.isEmpty()) {
                    fetchDataAndShow(showTypeMap.get(showType).entity, showType, showTypeMap.get(showType).title, currentPage - 1);
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePageLabel() {
        if (bookList != null || userList != null)
            pageLabel.setText("第 " + currentPage + " 页");
        else
            pageLabel.setText("");
        revalidate();
        repaint();
    }

    private static void searchNextOrPrevious(int page) throws IOException, ClassNotFoundException {
        ResponsePack<?> response = searchRequest(page);
        if (response.isSuccess())
            mainPanel.showWorkSpace(response, "Book", page);
        else {
            if (response.getMessage().equals("未找到符合条件的实体"))
                Notification(mainFrame, "已经到顶了");
            else
                Notification(mainFrame, "搜索出错 " + response.getMessage());
        }
    }

    public static void refreshPage() throws IOException, ClassNotFoundException {
        if (isSearching)
            searchNextOrPrevious(currentPage);
        else if (sortSearching)
            topPanel.sortedBorrowRequest(currentPage);
        else if (!showType.isEmpty())
            fetchDataAndShow(showTypeMap.get(showType).entity, showType, showTypeMap.get(showType).title, currentPage);
    }
}
