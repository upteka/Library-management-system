package main.java.com.library.client.gui;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.impl.ToolsIMPL.setFrame;

public class MainPage {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    public static MainPanel mainPanel = null;
    public static JFrame mainFrame = null;

    public void initialize() {
        if (mainFrame != null) deleteAll();

        mainFrame = new JFrame();
        mainPanel = new MainPanel();
        setFrame(mainFrame, WIDTH, HEIGHT, new BorderLayout(), true, JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    public void deleteAll() {
        mainPanel.deleteAll(true);
        mainFrame.removeAll();
        mainFrame = null;
        mainPanel = null;
        System.gc();
    }
}