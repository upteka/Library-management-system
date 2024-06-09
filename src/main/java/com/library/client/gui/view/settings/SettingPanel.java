package main.java.com.library.client.gui.view.settings;

import main.java.com.library.client.gui.view.workspace.WorkSpace;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;
import static main.java.com.library.client.gui.view.workspace.WorkSpace.bottomPanel;

public class SettingPanel extends JPanel {
    private static final GridBagLayout LAYOUT = new GridBagLayout();

    public SettingPanel() {
        setLayout(LAYOUT);

        JPanel pagePanel = new JPanel(LAYOUT);
        JSeparator separator = new JSeparator();
        JLabel pageLabel = new JLabel("每页显示数量");
        setFormat(pageLabel, pagePanel, new Insets(0, 0, 0, 0),
                0, 0, 0, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 16, Font.BOLD);
        setFormat(separator, pagePanel, new Insets(0, 0, 0, 0),
                1, 0, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(pagePanel, this, new Insets(0, 20, 20, 0),
                0, 0, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);


        JPanel setPagePanel = new JPanel(LAYOUT);
        IntegerTextField itemsPerPage = new IntegerTextField(1, Integer.MAX_VALUE);
        JButton savePageNumber = new JButton("保存");
        JSlider pageSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 20);
        setFormat(pageSlider, setPagePanel, new Insets(0, 20, 0, 0),
                0, 0, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(itemsPerPage, setPagePanel, new Insets(0, 20, 0, 0),
                1, 0, 0, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(savePageNumber, setPagePanel, new Insets(0, 20, 0, 300),
                2, 0, 0, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(setPagePanel, this, new Insets(0, 0, 20, 0),
                0, 1, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);

        savePageNumber.addActionListener(_ -> {
            if (itemsPerPage.isValidInput()) {
                int value = Integer.parseInt(itemsPerPage.getText());
                WorkSpace.pageSize = value;
                WorkSpace.currentPage = 1;
                bottomPanel.updatePageLabel();
                JOptionPane.showMessageDialog(this, "已将每页显示数目设置为 " + value);
            } else {
                JOptionPane.showMessageDialog(this, "请输入一个正整数");
            }
        });
        pageSlider.addChangeListener(_ -> itemsPerPage.setText("" + pageSlider.getValue()));


        JPanel otherPanel = new JPanel(LAYOUT);
        JLabel otherLabel = new JLabel("其他设置");
        JSeparator separator1 = new JSeparator();
        setFormat(otherLabel, otherPanel, new Insets(0, 0, 0, 0),
                0, 0, 0, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 16, Font.BOLD);
        setFormat(separator1, otherPanel, new Insets(0, 0, 0, 0),
                1, 0, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(otherPanel, this, new Insets(0, 20, 20, 0),
                0, 2, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);


        JPanel setOtherPanel = new JPanel(LAYOUT);
        JButton borrowRecord = new JButton("借阅记录");
        JButton logout = new JButton("注销");
        JButton deleteAccount = new JButton("删除账户");
        deleteAccount.setForeground(Color.RED);
        setFormat(borrowRecord, setOtherPanel, new Insets(0, 20, 0, 0),
                0, 0, 0, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(logout, setOtherPanel, new Insets(0, 20, 0, 0),
                1, 0, 0, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(deleteAccount, setOtherPanel, new Insets(0, 20, 0, 0),
                2, 0, 0, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(new JPanel(), setOtherPanel, new Insets(0, 20, 0, 0),
                3, 0, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
        setFormat(setOtherPanel, this, new Insets(0, 20, 20, 0),
                0, 3, 1, 0, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);

    }
}
