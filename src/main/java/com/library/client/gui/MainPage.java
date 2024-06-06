package main.java.com.library.client.gui;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.impl.ToolsIMPL.setFrame;

public class MainPage {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    public static MainPanel mainPanel = new MainPanel();
    public static JFrame mainFrame = new JFrame("图书管理系统");
    public static String[][] data = {
            {"1", "Book1", "Author1", "ISBN1", "Available"},
            {"2", "Book2", "Author2", "ISBN2", "Borrowed"},
            {"3", "Book3", "Author3", "ISBN3", "Available"},
            {"4", "Book4", "Author4", "ISBN4", "Borrowed"},
            {"5", "Book5", "Author5", "ISBN5", "Available"},
            {"6", "Book6", "Author6", "ISBN6", "Borrowed"},
            {"7", "Book7", "Author7", "ISBN7", "Available"},
            {"8", "Book8", "Author8", "ISBN8", "Borrowed"},
            {"9", "Book9", "Author9", "ISBN9", "Available"},
            {"10", "Book10", "Author10", "ISBN10", "Borrowed"},
            {"11", "Book11", "Author11", "ISBN11", "Available"},
            {"12", "Book12", "Author12", "ISBN12", "Borrowed"},
            {"13", "Book13", "Author13", "ISBN13", "Available"},
            {"14", "Book14", "Author14", "ISBN14", "Borrowed"},
            {"15", "Book15", "Author15", "ISBN15", "Available"},
            {"16", "Book16", "Author16", "ISBN16", "Borrowed"},
            {"17", "Book17", "Author17", "ISBN17", "Available"},
            {"18", "Book18", "Author18", "ISBN18", "Borrowed"},
            {"19", "Book19", "Author19", "ISBN19", "Available"},
            {"20", "Book20", "Author20", "ISBN20", "Borrowed"}
    };

    public MainPage() {
        setFrame(mainFrame, WIDTH, HEIGHT, new BorderLayout(), true, JFrame.EXIT_ON_CLOSE);
        mainPanel.initializeWorkSpace(20, data);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    public String[][] getData() {
        // get data from sever
        return null;
    }
}