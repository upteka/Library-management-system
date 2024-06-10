package main.java.com.library.client.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.java.com.library.client.gui.impl.ToolsIMPL.setFrame;

public class MainPage {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    public static MainPanel mainPanel = null;
    public static JFrame mainFrame = null;
    public static List<List<String>> dataList = new ArrayList<>();

    public void initialize() {
        if (mainFrame != null) deleteAll();

        dataList = getData();
        mainFrame = new JFrame();
        mainPanel = new MainPanel();
        setFrame(mainFrame, WIDTH, HEIGHT, new BorderLayout(), true, JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    public void deleteAll() {
        dataList.clear();
        mainPanel.deleteAll();
        mainFrame.removeAll();
        mainFrame = null;
        mainPanel = null;
        System.gc();
    }

    public static List<List<String>> getData() {
        // get data from sever
        String[][] data = {
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
                {"20", "Book20", "Author20", "ISBN20", "Borrowed"},
                {"21", "Book21", "Author21", "ISBN21", "Available"},
                {"22", "Book22", "Author22", "ISBN22", "Borrowed"},
                {"23", "Book23", "Author23", "ISBN23", "Available"},
                {"24", "Book24", "Author24", "ISBN24", "Borrowed"},
                {"25", "Book25", "Author25", "ISBN25", "Available"},
                {"26", "Book26", "Author26", "ISBN26", "Borrowed"},
                {"27", "Book27", "Author27", "ISBN27", "Available"},
                {"28", "Book28", "Author28", "ISBN28", "Borrowed"},
                {"29", "Book29", "Author29", "ISBN29", "Available"},
                {"30", "Book30", "Author30", "ISBN30", "Borrowed"},
        };
        for (String[] row : data) {
            dataList.add(Arrays.asList(row));
        }
        return dataList;
    }
}