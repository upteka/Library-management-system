package main.java.com.library.client.gui;

import main.java.com.library.client.gui.view.SideBar;
import main.java.com.library.client.gui.view.about.AboutPanel;
import main.java.com.library.client.gui.view.search.SearchPage;
import main.java.com.library.client.gui.view.settings.SettingPanel;
import main.java.com.library.client.gui.view.workspace.WorkSpace;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;

public class MainPanel extends JPanel {
    public static final WorkSpace workSpace = new WorkSpace();
    public static final SettingPanel settingPanel = new SettingPanel();
    public static final AboutPanel aboutPanel = new AboutPanel();

    public MainPanel() {
        setLayout(new GridBagLayout());
        setFormat(new SideBar(), this,
                new Insets(0, 0, 0, 0), 0, 0, 0, 0, 10, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);
        setFormat(workSpace, this,
                new Insets(0, 0, 0, 0), 1, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);
    }

    public void showWorkSpace() {
        removeComponents();
        mainFrame.setTitle("图书管理系统 - 工作区");
        WorkSpace.workPanel.updateLayout();

        setFormat(workSpace, this,
                new Insets(0, 0, 0, 0), 1, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);
        refresh();
    }

    public void showSearchPage() {
        removeComponents();
        mainFrame.setTitle("图书管理系统 - 搜索");
        setFormat(SearchPage.searchPanel, this, getDefault(), 0, 0);
        refresh();
    }

    public void showSettingPage() {
        removeComponents();
        mainFrame.setTitle("图书管理系统 - 设置");
        setFormat(settingPanel, this, getDefault(), 0, 0);
        refresh();
    }

    public void showAboutPage() {
        removeComponents();
        mainFrame.setTitle("图书管理系统 - 关于");
        setFormat(aboutPanel, this, getDefault(), 0, 0);
        refresh();
    }

    public void removeComponents() {
        for (Component component : getComponents()) {
            if (!(component instanceof SideBar)) {
                remove(component);
            }
        }
    }

    public GridBagConstraints getDefault() {
        return setFormat(null, null,
                new Insets(0, 0, 0, 0), 1, 0, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, 0, 0);
    }

    public void refresh() {
        revalidate();
        repaint();
    }
}