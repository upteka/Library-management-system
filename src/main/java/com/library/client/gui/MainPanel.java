package main.java.com.library.client.gui;

import main.java.com.library.client.gui.view.BorrowedBooks;
import main.java.com.library.client.gui.view.SideBar;
import main.java.com.library.client.gui.view.workspace.WorkSpace;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;

public class MainPanel extends JPanel {
    public static WorkSpace workSpace = null;
    public static BorrowedBooks borrowedBooks = null;

    public MainPanel() {
        setLayout(new GridBagLayout());
        setFormat(new SideBar(), this,
                new Insets(0, 0, 0, 0), 0, 0, 0, 0, 20, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);
    }

    public void initializeWorkSpace(int panelCount, String[][] data) {
        workSpace = new WorkSpace(panelCount, data);
        mainFrame.setTitle("图书管理系统 - 工作区");
        SwingUtilities.invokeLater(() -> setFormat(WorkSpace.workPanel, this,
                new Insets(0, 0, 0, 0), 1, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0));
    }

    public void showWorkSpace() {
        WorkSpace.workPanel.setVisible(true);
    }

    public void initializeBorrowedBooks(int panelCount, String[][] data) {

    }

    public void showBorrowedBooks() {

    }

    public void hideAllComponents() {
        for (Component component : getComponents()) {
            component.setVisible(false);
        }
    }
}