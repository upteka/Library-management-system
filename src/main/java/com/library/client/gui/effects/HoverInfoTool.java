package main.java.com.library.client.gui.effects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverInfoTool {

    public static void addDetailAction(JTextArea textArea, String info) {
        JToolTip toolTip = new JToolTip();
        Popup[] popup = {null};

        textArea.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                showToolTip(textArea, e.getPoint(), wrapText(info, 40));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hideToolTip();
            }

            private void showToolTip(JTextArea textArea, Point p, String info) {
                if (popup[0] == null) {
                    toolTip.setTipText(info);
                    Point locationOnScreen = textArea.getLocationOnScreen();
                    PopupFactory factory = PopupFactory.getSharedInstance();
                    popup[0] = factory.getPopup(textArea, toolTip, locationOnScreen.x + p.x, locationOnScreen.y + p.y + 10);
                    popup[0].show();
                }
            }

            private void hideToolTip() {
                if (popup[0] != null) {
                    popup[0].hide();
                    popup[0] = null;
                }
            }
        });

        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hideToolTip();
            }

            private void hideToolTip() {
                if (popup[0] != null) {
                    popup[0].hide();
                    popup[0] = null;
                }
            }
        });
    }

    private static String wrapText(String text, int maxLineLength) {
        StringBuilder wrappedText = new StringBuilder();
        int index = 0;
        while (index < text.length()) {
            int endIndex = Math.min(index + maxLineLength, text.length());
            wrappedText.append(text, index, endIndex).append("\n");
            index = endIndex;
        }
        return wrappedText.toString().trim();
    }
}
