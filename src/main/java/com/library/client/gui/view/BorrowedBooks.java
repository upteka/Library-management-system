package main.java.com.library.client.gui.view;

import javax.swing.*;
import java.awt.*;

import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.MainPage.mainPanel;

public class BorrowedBooks extends JPanel {
    private static final GridBagLayout LAYOUT = new GridBagLayout();

    public BorrowedBooks() {
        setLayout(LAYOUT);
        mainFrame.setTitle("Borrowed Books");

        JLabel title = new JLabel("Borrowed Books");
        GridBagConstraints constraints = new GridBagConstraints();
        add(title, constraints);

        mainPanel.add(this);
    }
}
