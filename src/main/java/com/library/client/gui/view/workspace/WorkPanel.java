package main.java.com.library.client.gui.view.workspace;

import main.java.com.library.client.gui.MainPage;
import main.java.com.library.common.entity.EntityList;
import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.common.entity.impl.ReturnRecord;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static main.java.com.library.client.gui.LoginPage.clientUtil;
import static main.java.com.library.client.gui.LoginPage.response;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.effects.HoverInfoTool.addDetailAction;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setColor;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.*;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class WorkPanel extends JScrollPane {
    private final GridBagLayout LAYOUT = new GridBagLayout();
    public static int FONT_SIZE = 14;
    public static JPanel content = null;
    private final List<JPanel> panelList = new ArrayList<>();
    private final List<Boolean> booleanList = new ArrayList<>();
    static List dataList = null;
    private static boolean borrowedOnly = false;


    public WorkPanel() {
        UIManager.put("ScrollBar.showButtons", true);
        content = new JPanel(LAYOUT);
        setViewportView(content);
        getVerticalScrollBar().setUnitIncrement(16);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public void initialize() {
        if (dataList == null) return;
        for (int i = 0; i < dataList.size(); i++) {
            panelList.add(new JPanel(LAYOUT));
            booleanList.add(false);
        }
        updateLayout();
    }

    public void deleteAll() {
        panelList.clear();
        booleanList.clear();
        content.removeAll();
        content = null;
        removeAll();
        System.gc();
    }

    private void createSubPanels(JPanel p, int i, GridBagConstraints gbc, boolean isSelected) {
        setColor(p, null, new Color(255, 255, 255), BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JToggleButton toggleButton = new JToggleButton();
        if (isSelected) {
            toggleButton.setText("√");
            p.setBackground(new Color(230, 232, 234));
            toggleButton.setSelected(true);
        } else {
            toggleButton.setText(" ");
            toggleButton.setBackground(new Color(234, 235, 236));
        }
        setFormat(toggleButton, p, new Insets(0, 15, 0, 0), 0, 0, 12, 0);

        Book data = (Book) dataList.get(i);

        setFormat(setTextArea(data.getTitle(), 150, 21), p, new Insets(0, 20, 0, 0), 1, 0, 0, 0);
        setFormat(setTextArea(data.getAuthor(), 100, 13), p, new Insets(0, 20, 0, 0), 2, 0, 0, 0);
        setFormat(setTextArea(data.getPublisher(), 100, 13), p, new Insets(0, 20, 0, 0), 3, 0, 0, 0);
        setFormat(setTextArea("余量 " + data.getCount(), 50, 21), p, new Insets(0, 20, 0, 0), 4, 0, 0, 0);

        JPanel statusPanel = setStatusColor(data.getStatus());
        setFormat(statusPanel, p, new Insets(0, 40, 0, 0), 5, 0, 10, 10, 0, 0);

        JButton borrowButton = new JButton("借阅");
        setColor(borrowButton, new Color(41, 98, 241), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        if (gbc != null) {
            setFormat(borrowButton, p, gbc, FONT_SIZE, Font.BOLD);
        } else {
            setFormat(borrowButton, p, new Insets(0, START_VALUE, 0, 0), 6, 0, 20, 10, FONT_SIZE, Font.BOLD);
        }

        if (borrowedOnly) {
            //显示我的借阅时才会出现归还按钮
            JButton returnButton = new JButton("归还");
            setColor(returnButton, new Color(41, 98, 241), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
            setFormat(returnButton, p, new Insets(0, 20, 0, 0), 7, 0, 20, 10, FONT_SIZE, Font.BOLD);

            // 获取borrowID
            // 根据borrowID发送归还请求
//            returnButton.addActionListener(_ -> {
//                try {
//                    BorrowRecord record = null;
//                    ResponsePack<?> responsePack = returnRequest(record);
//                    JOptionPane.showMessageDialog(this, responsePack.getMessage());
//                } catch (IOException | ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            });
        }

        JButton editButton = new JButton("编辑 ▼");
        setColor(editButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setFormat(editButton, p, new Insets(0, 20, 0, 0), 8, 0, 20, 10, FONT_SIZE, Font.BOLD);

        JPopupMenu dropdownMenu = new JPopupMenu();
        JMenuItem showDetail = new JMenuItem("详细信息");
        JMenuItem editInfo = new JMenuItem("编辑信息");
        JMenuItem deleteThis = new JMenuItem("删除此项");
        dropdownMenu.add(showDetail);
        dropdownMenu.add(editInfo);
        dropdownMenu.add(deleteThis);

        borrowButton.addActionListener(_ -> new BorrowDialog(mainFrame, data.getBookID()));
        showDetail.addActionListener(_ -> {
        });
        editInfo.addActionListener(_ -> new EditDialog(mainFrame, data));
        deleteThis.addActionListener(_ -> new DeleteDialog(mainFrame, data));
        toggleButton.addActionListener(_ -> toggleButtonAction(toggleButton, p, i));
        editButton.addActionListener(_ -> dropdownMenu.show(editButton, 0, editButton.getHeight()));

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

    private void toggleButtonAction(JToggleButton toggleButton, JPanel panel, int i) {
        if (toggleButton.isSelected()) {
            toggleButton.setText("√");
            panel.setBackground(new Color(230, 232, 234));
            booleanList.set(i, true);
        } else {
            toggleButton.setText(" ");
            panel.setBackground(new Color(255, 255, 255));
            booleanList.set(i, false);
        }
    }

    public void updateLayout() {
        if (dataList == null) return;
        int newInset = Math.max(START_VALUE + mainFrame.getWidth() - MainPage.WIDTH, 50);
        GridBagConstraints gbc = setFormat(null, null, new Insets(0, newInset, 0, 0), 6, 0, 20, 10, 0, 0);

        content.removeAll();
        for (int i = 0; i < Math.min(pageSize, dataList.size() - (currentPage - 1) * pageSize); i++) {
            int index = (currentPage - 1) * pageSize + i;
            panelList.get(index).removeAll();
            createSubPanels(panelList.get(index), index, gbc, booleanList.get(index));
        }

        revalidate();
        repaint();
    }

    public void unpackResponse(ResponsePack<?> responsePack, String action) {
        dataList = null;
        System.gc();

        EntityList data = (EntityList) responsePack.getData();
        dataList = data.entities();

        borrowedOnly = action.equals("BORROWED");
    }

    private ResponsePack<?> returnRequest(ReturnRecord record) throws IOException, ClassNotFoundException {
        clientUtil.sendRequest(packRequest("return", record, "return", response.getJwtToken()));
        return clientUtil.receiveResponse();
    }
}