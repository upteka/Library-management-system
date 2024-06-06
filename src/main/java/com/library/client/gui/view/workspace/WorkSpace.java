package main.java.com.library.client.gui.view.workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static main.java.com.library.client.gui.MainPage.WIDTH;
import static main.java.com.library.client.gui.MainPage.mainFrame;

public class WorkSpace {
    public static int SCROLL_VALUE = 0;
    public static int CURRENT_WIDTH = 0;
    public static int START_VALUE = 100;
    public static WorkPanel workPanel = new WorkPanel();

    public WorkSpace(int panelCount, String[][] data) {
        workPanel.initialize(panelCount, data);

        addActions();
    }

    public void addActions() {
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SCROLL_VALUE = workPanel.getVerticalScrollBar().getValue();
                if (CURRENT_WIDTH != mainFrame.getWidth()) {
                    CURRENT_WIDTH = mainFrame.getWidth();
                    workPanel.updateLayout(Math.max(START_VALUE + mainFrame.getWidth() - WIDTH, 50));
                }
                SwingUtilities.invokeLater(() -> workPanel.getVerticalScrollBar().setValue(SCROLL_VALUE));
            }
        });

        mainFrame.addWindowStateListener(e -> {
            SCROLL_VALUE = workPanel.getVerticalScrollBar().getValue();
            if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                if (CURRENT_WIDTH != mainFrame.getWidth()) {
                    CURRENT_WIDTH = mainFrame.getWidth();
                    workPanel.updateLayout(Math.max(START_VALUE + mainFrame.getWidth() - WIDTH, 50));
                }
            }
            SwingUtilities.invokeLater(() -> workPanel.getVerticalScrollBar().setValue(SCROLL_VALUE));
        });
    }
}
