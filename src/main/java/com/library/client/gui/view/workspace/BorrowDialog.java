package main.java.com.library.client.gui.view.workspace;

import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.common.network.JwtUtil;
import main.java.com.library.common.network.ResponsePack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import static main.java.com.library.client.gui.LoginPage.clientUtil;
import static main.java.com.library.client.gui.LoginPage.response;
import static main.java.com.library.client.gui.MainPage.mainFrame;
import static main.java.com.library.client.gui.effects.NotificationUtil.Notification;
import static main.java.com.library.client.gui.view.workspace.BottomPanel.refreshPage;
import static main.java.com.library.common.network.handlers.RequestHelper.packRequest;

public class BorrowDialog extends JDialog {
    private final JComboBox<Integer> dayComboBox;
    private final JComboBox<Integer> hourComboBox;
    private final JComboBox<Integer> minuteComboBox;
    private final JSlider timeSlider;
    private String BookID = null;

    public BorrowDialog(JFrame parent, String bookID) {
        super(parent, "借阅", true);
        BookID = bookID;
        setTitle("借书时间选择");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel label = new JLabel("请填写借阅时长");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.CENTER;
        add(label, gbc);

        JLabel dayLabel = new JLabel("天数:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(dayLabel, gbc);

        dayComboBox = new JComboBox<>();
        for (int i = 0; i <= 29; i++) {
            dayComboBox.addItem(i);
        }
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(dayComboBox, gbc);

        JLabel hourLabel = new JLabel("小时:");
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(hourLabel, gbc);

        hourComboBox = new JComboBox<>();
        for (int i = 0; i <= 23; i++) {
            hourComboBox.addItem(i);
        }
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(hourComboBox, gbc);

        JLabel minuteLabel = new JLabel("分钟:");
        gbc.gridx = 4;
        gbc.gridy = 1;
        add(minuteLabel, gbc);

        minuteComboBox = new JComboBox<>();
        for (int i = 0; i <= 59; i++) {
            minuteComboBox.addItem(i);
        }
        gbc.gridx = 5;
        gbc.gridy = 1;
        add(minuteComboBox, gbc);

        JButton okButton = new JButton("确认");
        JButton cancelButton = new JButton("取消");
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(okButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(cancelButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 6;
        add(buttonPanel, gbc);

        timeSlider = new JSlider(0, 43199, 0);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.ipadx = 100;
        add(timeSlider, gbc);

        okButton.addActionListener(_ -> {
            try {
                Instant instant = getInstant();
                Instant oneHourLater = Instant.now().plusSeconds(3600);

                if (instant.isBefore(oneHourLater)) {
                    Notification(mainFrame, "借阅时间不能小于1小时");
                    return;
                }
                ResponsePack<?> responsePack = borrowRequest();
                if (responsePack.isSuccess()) {
                    Notification(mainFrame, "借阅成功！");
                    dispose();
                    refreshPage();
                } else
                    Notification(mainFrame, "借阅失败 " + responsePack.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        cancelButton.addActionListener(_ -> dispose());

        dayComboBox.addActionListener(_ -> updateSlider());
        hourComboBox.addActionListener(_ -> updateSlider());
        minuteComboBox.addActionListener(_ -> updateSlider());

        timeSlider.addChangeListener(_ -> updateComboBoxes());

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void updateSlider() {
        int days = (Integer) Objects.requireNonNull(dayComboBox.getSelectedItem());
        int hours = (Integer) Objects.requireNonNull(hourComboBox.getSelectedItem());
        int minutes = (Integer) Objects.requireNonNull(minuteComboBox.getSelectedItem());

        int totalMinutes = days * 24 * 60 + hours * 60 + minutes;
        timeSlider.setValue(totalMinutes);
    }

    private void updateComboBoxes() {
        int totalMinutes = timeSlider.getValue();

        int days = totalMinutes / (24 * 60);
        totalMinutes %= (24 * 60);
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        dayComboBox.removeActionListener(_ -> updateSlider());
        hourComboBox.removeActionListener(_ -> updateSlider());
        minuteComboBox.removeActionListener(_ -> updateSlider());

        dayComboBox.setSelectedItem(days);
        hourComboBox.setSelectedItem(hours);
        minuteComboBox.setSelectedItem(minutes);

        dayComboBox.addActionListener(_ -> updateSlider());
        hourComboBox.addActionListener(_ -> updateSlider());
        minuteComboBox.addActionListener(_ -> updateSlider());
    }

    private ResponsePack<?> borrowRequest() throws IOException, ClassNotFoundException {
        clientUtil.sendRequest(packRequest("borrow", new BorrowRecord(JwtUtil.extractUserId(response.getJwtToken()),
                BookID, getInstant(), false), "borrow", response.getJwtToken()));
        return clientUtil.receiveResponse();
    }

    public Instant getInstant() {
        int days = (Integer) Objects.requireNonNull(dayComboBox.getSelectedItem());
        int hours = (Integer) Objects.requireNonNull(hourComboBox.getSelectedItem());
        int minutes = (Integer) Objects.requireNonNull(minuteComboBox.getSelectedItem());

        return LocalDateTime.now()
                .plusDays(days)
                .plusHours(hours)
                .plusMinutes(minutes)
                .atZone(ZoneId.systemDefault())
                .toInstant();
    }
}
