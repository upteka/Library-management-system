package main.java.com.library.client.gui.view.workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;

public class WorkSpace extends JPanel {
    public static final int START_VALUE = 60;
    public static int scrollValue = 0;
    public static int currentWidth = 0;
    public static int currentPage = 1;
    public static int pageSize = 20;
    public static final WorkPanel workPanel = new WorkPanel();
    public static final TopPanel topPanel = new TopPanel();
    public static final BottomPanel bottomPanel = new BottomPanel();

    public WorkSpace() {
        setLayout(new GridBagLayout());
        workPanel.initialize();

        SwingUtilities.invokeLater(() -> {
            setFormat(topPanel, this, new Insets(0, 0, 0, 0), 0, 0,
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
