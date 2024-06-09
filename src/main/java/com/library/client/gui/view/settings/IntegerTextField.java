package main.java.com.library.client.gui.view.settings;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class IntegerTextField extends JTextField {
    final int minValue;
    final int maxValue;

    public IntegerTextField(int minValue, int maxValue) {
        super(10);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.setText("20");
        init();
    }

    private void init() {
        this.getDocument().addDocumentListener(new DocumentListener() {
            private void validate() {
                SwingUtilities.invokeLater(() -> {
                    String text = getText();
                    if (text.length() > 9) {
                        setText(text.substring(0, 9));
                    } else if (!text.matches("\\d*")) {
                        setText(text.replaceAll("[^\\d]", ""));
                    }
                });
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                validate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validate();
            }
        });
    }

    public boolean isValidInput() {
        try {
            int value = Integer.parseInt(getText());
            return value >= minValue && value <= maxValue;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
