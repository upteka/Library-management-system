package main.java.com.library.client.gui.view.workspace;

import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static main.java.com.library.client.gui.LoginPage.clientUtil;
import static main.java.com.library.client.gui.LoginPage.response;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.effects.NotificationUtil.Notification;
import static main.java.com.library.client.gui.impl.ToolsIMPL.*;
import static main.java.com.library.client.gui.view.workspace.BottomPanel.refreshPage;
import static main.java.com.library.client.gui.view.workspace.WorkPanel.setTextArea;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class EditBookDialog extends JDialog {
    public EditBookDialog(JFrame parent, Book data, boolean isAdd) {
        super(parent, "编辑图书信息", true);
        setDialog(this, 300, 400, new BorderLayout(), false, JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JTextField idField = setArea(data.getId(), 200);
        JTextField titleField = setArea(data.getTitle(), 200);
        JTextField authorField = setArea(data.getAuthor(), 200);
        JTextField publisherField = setArea(data.getPublisher(), 200);
        JTextField isbnField = setArea(data.getISBN(), 200);
        JTextField introductionField = setArea(data.getIntroduction(), 200);
        JTextField countField = setArea(String.valueOf(data.getCount()), 200);
        JButton saveButton = new JButton("提交");
        JButton cancelButton = new JButton("取消");

        setColor(saveButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());
        setColor(cancelButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());


        easySetFormat(idField, panel, 0, "书籍ID");
        easySetFormat(titleField, panel, 1, "书名");
        easySetFormat(authorField, panel, 2, "作者");
        easySetFormat(publisherField, panel, 3, "出版社");
        easySetFormat(isbnField, panel, 4, "ISBN");
        easySetFormat(introductionField, panel, 5, "简介");
        easySetFormat(countField, panel, 6, "数量");
        setFormat(buttonPanel, panel, new Insets(10, 10, 10, 10), 0, 6, 1, 1, 0, 20, 0, 1, 14, Font.BOLD);

        setFormat(saveButton, buttonPanel, new Insets(10, 10, 10, 10), 0, 0, 1, 1, 15, 0, 0, 1, 14, Font.BOLD);
        setFormat(cancelButton, buttonPanel, new Insets(10, 10, 10, 10), 1, 0, 1, 1, 15, 0, 0, 1, 14, Font.BOLD);

        cancelButton.addActionListener(_ -> dispose());
        saveButton.addActionListener(_ -> {
            data.setTitle(titleField.getText());
            data.setAuthor(authorField.getText());
            data.setPublisher(publisherField.getText());
            data.setISBN(isbnField.getText());
            data.setIntroduction(introductionField.getText());
            data.setCount(Integer.parseInt(countField.getText()));
            try {
                if (isAdd)
                    clientUtil.sendRequest(packRequest("add", data, "add", response.getJwtToken()));
                else
                    clientUtil.sendRequest(packRequest("update", data, "update", response.getJwtToken()));
                ResponsePack<?> response = clientUtil.receiveResponse();
                if (response.isSuccess()) {
                    if (isAdd)
                        Notification(mainFrame, "添加成功！");
                    else
                        Notification(mainFrame, "修改成功！");
                    refreshPage();
                } else
                    Notification(this, "操作失败 " + response.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void easySetFormat(JComponent component, JComponent target, int gridy, String title) {
        JPanel p = new JPanel(new GridBagLayout());
        setFormat(setTextArea(title, 70, 99), p, new Insets(5, 5, 5, 5), 0, 0, 14, Font.BOLD);
        setFormat(component, p, new Insets(5, 5, 5, 5), 1, 0, 14, 0);
        setFormat(p, target, new Insets(5, 5, 5, 5), 0, gridy, 1, 0, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
    }

    private JTextField setArea(String content, int width) {
        JTextField area = new JTextField(content);
        area.setPreferredSize(new Dimension(width, 40));
        return area;
    }
}