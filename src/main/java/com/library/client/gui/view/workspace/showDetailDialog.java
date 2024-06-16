package main.java.com.library.client.gui.view.workspace;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static main.java.com.library.client.gui.impl.ToolsIMPL.setColor;
import static main.java.com.library.client.gui.impl.ToolsIMPL.setFormat;
import static main.java.com.library.client.gui.view.workspace.WorkPanel.setTextArea;

public class showDetailDialog extends JDialog {
    public showDetailDialog(JFrame owner, List<String[]> details) {
        super(owner);
        setTitle("详细信息");
        setLayout(new GridBagLayout());
        JPanel panel = new JPanel(new GridBagLayout());

        for (int i = 0; i < details.size(); i++) {
            putIntoThis(details.get(i)[1], details.get(i)[0], panel, i);
        }

        JButton okButton = new JButton("确定");
        okButton.addActionListener(_ -> dispose());
        setColor(okButton, new Color(72, 74, 77), new Color(230, 230, 230), BorderFactory.createEmptyBorder());

        setFormat(okButton, panel, new Insets(10, 0, 0, 0), 0, details.size(), 0, 15, 14, Font.BOLD);

        add(panel);
        setSize(500, 500);
        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void putIntoThis(String title, String detail, JComponent target, int index) {
        JPanel p = new JPanel(new GridBagLayout());
        setFormat(setTextArea(title, 70, 99), p, new Insets(5, 5, 5, 5), 0, 0, 14, Font.BOLD);
        setFormat(setTextArea(detail, 300, 40), p, new Insets(5, 5, 5, 5), 1, 0, 14, 0);
        setFormat(p, target, new Insets(5, 5, 5, 5), 0, index, 1, 0, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0);
    }
}
