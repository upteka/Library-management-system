package main.java.com.library.client.gui.view.search;

import main.java.com.library.client.gui.effects.GradientLabel;
import main.java.com.library.client.gui.view.workspace.WorkSpace;
import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static main.java.com.library.client.gui.LoginPage.clientUtil;
import static main.java.com.library.client.gui.LoginPage.response;
import static main.java.com.library.client.gui.MainPage.mainPanel;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class SearchPanel extends JPanel {
    private static SearchSettings settingPanel = null;
    private static JTextField searchField = null;
    private JPanel emptyTopPanel = new JPanel();
    private JLabel emptyBottomPanel = new JLabel();
    private ResponsePack<?> searchResponse;

    public SearchPanel() {
        setLayout(new GridBagLayout());
        fillEmptySpace();

        JLabel title = new GradientLabel("Search Everywhere", new Color(74, 144, 226), new Color(144, 19, 254));
        setFormat(title, this, new Insets(0, 0, 0, 0), 0, 1, 0, 150,
                0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, 48, Font.BOLD);

        searchField = new JTextField(40);
        searchField.putClientProperty("JComponent.roundRect", true);
        searchField.setMargin(new Insets(10, 14, 10, 14));
        settingPanel = new SearchSettings();
        settingPanel.setVisible(false);

        JToggleButton settingButton = new JToggleButton(new ImageIcon(".\\lib\\setting.png"));
        settingButton.putClientProperty("JButton.buttonType", "roundRect");

        JPanel textPanel = new JPanel(new GridBagLayout());
        setFormat(searchField, textPanel, new Insets(0, 0, 0, 0), 0, 0, 18, 0);
        setFormat(settingButton, textPanel, new Insets(4, 10, 0, 0), 1, 0, 0, 0, 0, 0);
        setFormat(textPanel, this, new Insets(0, 0, 0, 0), 0, 2, 0, 0,
                0, 0, GridBagConstraints.CENTER, 1, 0, 0);
        setFormat(settingPanel, this, new Insets(0, 0, 0, 0), 0, 3, 1, 294,
                0, 0, GridBagConstraints.CENTER, 1, 0, 0);
        setFormat(emptyBottomPanel, this, new Insets(0, 0, 0, 0), 0, 3, 1, 600,
                0, 0, GridBagConstraints.CENTER, 1, 0, 0);

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        searchResponse = searchRequest();
                        if (searchResponse.isSuccess()) {
                            mainPanel.showWorkSpace(searchResponse, "NORMAL");
                        } else {
                            JOptionPane.showMessageDialog(null, searchResponse.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        settingButton.addActionListener(_ -> toggleButtonAction(settingButton));
    }

    private void toggleButtonAction(JToggleButton button) {
        if (button.isSelected()) {
            settingPanel.fadeIn();
            settingPanel.setVisible(true);
            emptyBottomPanel.setVisible(false);
        } else {
            settingPanel.fadeOut();
            settingPanel.setVisible(false);
            emptyBottomPanel.setVisible(true);
        }
    }

    private ResponsePack<?> searchRequest() throws IOException, ClassNotFoundException {
        clientUtil.sendRequest(packRequest("search", new Book(), "search", response.getJwtToken(),
                settingPanel.getFieldName(), searchField.getText(), "LIKE", "0", "null", settingPanel.getSearchOrder(),
                "1", String.valueOf(WorkSpace.pageSize), String.valueOf(settingPanel.isCaseInsensitive()), "AND",
                "null", String.valueOf(settingPanel.isDistinct()), "null", "null", "null", "null"));
        return clientUtil.receiveResponse();
    }

    private void fillEmptySpace() {
        emptyTopPanel.setOpaque(false);
        emptyBottomPanel.setOpaque(false);
        setFormat(emptyTopPanel, this, new Insets(0, 0, 0, 0), 0, 0, 1, 400,
                0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);
    }

    public void deleteAll() {
        settingPanel.removeAll();
        settingPanel = null;
        searchField = null;
    }
}