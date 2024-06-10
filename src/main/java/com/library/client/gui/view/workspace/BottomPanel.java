package main.java.com.library.client.gui.view.workspace;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.MainPage.dataList;
import static main.java.com.library.client.gui.impl.ToolsIMPL.*;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.*;

public class BottomPanel extends JPanel {
    private static final JButton pageNext = new JButton("下一页");
    private static final JButton pagePrevious = new JButton("上一页");
    private static final JPanel pagePanel = new JPanel(new GridBagLayout());
    private static final JLabel pageLabel = new JLabel();

    public BottomPanel() {
        setLayout(new GridBagLayout());
        initialize();
        setFormat(pagePrevious, this, new Insets(0, 0, 0, 0),
                0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 16, Font.BOLD);
        setFormat(pagePanel, this, new Insets(0, 0, 0, 0),
                1, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);
        setFormat(pageNext, this, new Insets(0, 0, 0, 0),
                2, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 16, Font.BOLD);
    }

    private void initialize() {
        setColor(pageNext, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(pagePrevious, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());

        setCustomFont(pageLabel, 16, Font.BOLD);
        pagePanel.add(pageLabel);

        pageNext.addActionListener(_ -> goToNextPage());
        pagePrevious.addActionListener(_ -> goToPreviousPage());

        updatePageLabel();
    }

    private void goToNextPage() {
        if (dataList.size() > currentPage * pageSize) {
            currentPage++;
            updatePageLabel();
            workPanel.updateLayout();
            SwingUtilities.invokeLater(() -> workPanel.getVerticalScrollBar().setValue(0));
        }
    }

    private void goToPreviousPage() {
        if (currentPage > 1) {
            currentPage--;
            updatePageLabel();
            workPanel.updateLayout();
            SwingUtilities.invokeLater(() -> workPanel.getVerticalScrollBar().setValue(0));
        }
    }

    public void updatePageLabel() {
        System.out.println("current page: " + currentPage + " page size: " + pageSize + " data size: " + dataList.size());
        if (dataList == null) {
            pageLabel.setText("第0页 / 0页");
        } else {
            int totalPages = (int) Math.ceil((double) dataList.size() / pageSize);
            pageLabel.setText("第 " + currentPage + " 页 / " + totalPages + " 页");
        }
        revalidate();
        repaint();
    }
}
