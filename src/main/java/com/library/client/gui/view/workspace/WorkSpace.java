package main.java.com.library.client.gui.view.workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;

public class WorkSpace extends JPanel {
    public static int currentWidth = 0;
    public static int scrollValue = 0;
    public static int currentPage = 1;
    public static int pageSize = 20;
    public static WorkPanel workPanel = null;
    public static TopPanel topPanel = null;
    public static BottomPanel bottomPanel = null;
    public static boolean showTopPanel = false;

    public WorkSpace() {
        setLayout(new GridBagLayout());

        workPanel = new WorkPanel();
        topPanel = new TopPanel();
        bottomPanel = new BottomPanel();
        workPanel.initialize();

        SwingUtilities.invokeLater(() -> {
            if (showTopPanel) setFormat(topPanel, this, new Insets(0, 0, 0, 0), 0, 0,
                    0, 0, 0, 20, GridBagConstraints.NORTH, GridBagConstraints.BOTH, 0, 0);
            setFormat(WorkSpace.workPanel, this, new Insets(0, 0, 0, 0), 0, 1,
                    1, 1, 0, 0, GridBagConstraints.SOUTH, GridBagConstraints.BOTH, 0, 0);
            setFormat(bottomPanel, this, new Insets(0, 0, 0, 0), 0, 2,
                    0, 0, 0, 20, GridBagConstraints.NORTH, GridBagConstraints.BOTH, 0, 0);
            revalidate();
            repaint();
        });

        addActions();
    }

    public static void deleteAll() {
        scrollValue = 0;
        currentPage = 1;
        pageSize = 20;

        topPanel.removeAll();
        bottomPanel.removeAll();
        workPanel.deleteAll();

        workPanel = null;
        topPanel = null;
        bottomPanel = null;

        System.gc();
    }

    public void addActions() {
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scrollValue = workPanel.getVerticalScrollBar().getValue();
                if (currentWidth != mainFrame.getWidth()) {
                    currentWidth = mainFrame.getWidth();
                    workPanel.updateLayout();
                }
                SwingUtilities.invokeLater(() -> workPanel.getVerticalScrollBar().setValue(scrollValue));
            }
        });

        mainFrame.addWindowStateListener(e -> {
            scrollValue = workPanel.getVerticalScrollBar().getValue();
            if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                if (currentWidth != mainFrame.getWidth()) {
                    currentWidth = mainFrame.getWidth();
                    workPanel.updateLayout();
                }
            }
            SwingUtilities.invokeLater(() -> workPanel.getVerticalScrollBar().setValue(scrollValue));
        });
    }
}
