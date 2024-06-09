package main.java.com.library.client.gui.view.search;

import main.java.com.library.client.gui.effects.GradientLabel;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.impl.ToolsIMPL.setCustomFont;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;

public class SearchPanel extends JPanel {
    public SearchPanel() {
        initialize();
    }

    public void initialize() {
        setLayout(new GridBagLayout());
        fillEmptySpace(400, 600);

        // 标题
        JLabel title1 = new GradientLabel("Search Everywhere", new Color(74, 144, 226), new Color(144, 19, 254));
        JLabel title2 = new GradientLabel("Search Everywhere", new Color(86, 204, 242), new Color(47, 128, 237));
        JLabel title3 = new GradientLabel("Search Everywhere", new Color(127, 127, 213), new Color(134, 168, 231));
        setFormat(title1, this, new Insets(0, 0, 0, 0), 0, 1, 0, 150,
                0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, 48, Font.BOLD);

        JPanel textPanel = new JPanel();
        JTextField searchArea = new JTextField(40);
        searchArea.putClientProperty("JComponent.roundRect", true);
        setCustomFont(searchArea, 18, Font.PLAIN);
        searchArea.setMargin(new Insets(10, 14, 10, 14));
        textPanel.add(searchArea);
        setFormat(textPanel, this, new Insets(0, 0, 0, 0), 0, 2, 0, 0,
                0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);
    }

    private void fillEmptySpace(int top, int bottom) {
        JPanel emptyTopPanel = new JPanel();
        JLabel emptyBottomLabel = new JLabel();
        emptyTopPanel.setOpaque(false);
        emptyBottomLabel.setOpaque(false);
        setFormat(new JPanel(null), this, new Insets(0, 0, 0, 0), 0, 0, 1, top,
                0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, 24, Font.BOLD);
        setFormat(new JPanel(null), this, new Insets(0, 0, 0, 0), 0, 4, 1, bottom,
                0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, 48, Font.BOLD);
    }
}