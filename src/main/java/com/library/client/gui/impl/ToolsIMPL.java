package main.java.com.library.client.gui.impl;

import main.java.com.library.client.gui.Tools;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

/**
 * ToolsIMPL 的工具方法可以快速设置组件的字体、边框、颜色、布局、边距、位置、关闭操作等。
 */
public class ToolsIMPL implements Tools {
    public static Font customFont;

    /**
     * 加载自定义字体。
     * 从当前目录下的 lib 文件夹中加载名为 pf.ttf 的字体文件，并注册到 GraphicsEnvironment 中。
     */
    public static void loadCustomFont() {
        try {
            File fontFile = new File(".\\lib\\pf.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调整组件的字体，默认字体已经是 pf.ttf 字体。
     *
     * @param component 需要设置字体的组件
     * @param size      字体大小
     * @param style     字体样式 (如 Font.PLAIN, Font.BOLD)
     */
    public static void setCustomFont(JComponent component, float size, int style) {
        if (size == 0 || style == -1) return;
        Font resizedFont = customFont.deriveFont(style, size);
        component.setFont(resizedFont);
    }

    /**
     * 给文本框添加焦点监听器。
     * 当文本框获得焦点时，边框颜色变为绿色；失去焦点时，边框颜色变为浅灰色。
     *
     * @param field 需要添加焦点监听器的文本框
     */
    public static void addFocusListenerToField(JTextField field) {
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createLineBorder(new Color(15, 163, 127), 1, true));
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
            }
        });
    }

    /**
     * 快速设置 JFrame 的参数。
     *
     * @param frame          需要设置的 JFrame
     * @param width          窗口宽度
     * @param height         窗口高度
     * @param layout         布局管理器
     * @param resizable      是否可调整大小
     * @param closeOperation 关闭操作 (如 JFrame.EXIT_ON_CLOSE)
     */
    public static void setFrame(JFrame frame, int width, int height, LayoutManager layout, boolean resizable, int closeOperation) {
        frame.setSize(width, height);
        frame.setLayout(layout);
        frame.setResizable(resizable);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(closeOperation);
    }

    /**
     * 快速设置 JDialog 的参数。
     *
     * @param dialog         需要设置的 JDialog
     * @param width          对话框宽度
     * @param height         对话框高度
     * @param layout         布局管理器
     * @param resizable      是否可调整大小
     * @param closeOperation 关闭操作 (如 JDialog.DISPOSE_ON_CLOSE)
     */
    public static void setDialog(JDialog dialog, int width, int height, LayoutManager layout, boolean resizable, int closeOperation) {
        dialog.setSize(width, height);
        dialog.setLayout(layout);
        dialog.setResizable(resizable);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(closeOperation);
    }

    /**
     * 设置组件的颜色和边框。
     * 若参数为 null，则不设置对应的属性。
     *
     * @param component  需要设置的组件
     * @param foreground 前景色
     * @param background 背景色
     * @param border     边框
     */
    public static void setColor(JComponent component, Color foreground, Color background, Border border) {
        if (foreground != null) {
            component.setForeground(foreground);
        }
        if (background != null) {
            component.setBackground(background);
        }
        if (border != null) {
            component.setBorder(border);
        }
    }

    /**
     * 设置组件的 GridBagConstraints 布局参数，并添加到目标组件中。
     * 返回值是 GridBagConstraints 对象，包含了设置的参数。
     * 源组件和目标组件填 null 表示不添加到任何组件。
     * 字体大小和字体样式填 0 表示不调整字体。
     * 布局参数填 0 表示设为默认值。
     *
     * @param component  需要设置的组件
     * @param target     目标组件
     * @param insets     内边距
     * @param gridx      网格的 x 位置
     * @param gridy      网格的 y 位置
     * @param weightx    x 方向上的权重
     * @param weighty    y 方向上的权重
     * @param ipadx      内部填充的宽度
     * @param ipady      内部填充的高度
     * @param anchor     对齐方式
     * @param fill       填充方式
     * @param font_size  字体大小
     * @param font_style 字体样式 (如 Font.PLAIN, Font.BOLD)
     * @return GridBagConstraints 对象
     */
    public static GridBagConstraints setFormat(JComponent component, JComponent target, Insets insets, int gridx, int gridy, int weightx, int weighty, int ipadx, int ipady, int anchor, int fill, int font_size, int font_style) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        if (fill > 0) gbc.fill = fill;
        if (anchor > 0) gbc.anchor = anchor;
        if (weightx > 0) gbc.weightx = weightx;
        if (weighty > 0) gbc.weighty = weighty;
        if (ipadx > 0) gbc.ipadx = ipadx;
        if (ipady > 0) gbc.ipady = ipady;
        if (insets != null) gbc.insets = insets;
        if (font_size > 0) setCustomFont(component, font_size, font_style);

        if (component != null && target != null)
            target.add(component, gbc);
        return gbc;
    }

    /**
     * 设置组件的 GridBagConstraints 布局参数，并添加到目标组件中。
     * 返回值是 GridBagConstraints 对象，包含了设置的参数。
     * 源组件和目标组件填 null 表示不添加到任何组件。
     * 字体大小和字体样式填 0 表示不调整字体。
     * 布局参数填 0 表示设为默认值。
     *
     * @param component  需要设置的组件
     * @param target     目标组件
     * @param insets     内边距
     * @param gridx      网格的 x 位置
     * @param gridy      网格的 y 位置
     * @param font_size  字体大小
     * @param font_style 字体样式 (如 Font.PLAIN, Font.BOLD)
     * @return GridBagConstraints 对象
     */
    public static GridBagConstraints setFormat(JComponent component, JComponent target, Insets insets, int gridx, int gridy, int font_size, int font_style) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = insets;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if (font_size > 0)
            setCustomFont(component, font_size, font_style);

        if (component != null && target != null)
            target.add(component, gbc);
        return gbc;
    }

    /**
     * 设置组件的 GridBagConstraints 布局参数，并添加到目标组件中。
     * 返回值是 GridBagConstraints 对象，包含了设置的参数。
     * 源组件和目标组件填 null 表示不添加到任何组件。
     * 字体大小和字体样式填 0 表示不调整字体。
     * 布局参数填 0 表示设为默认值。
     *
     * @param component  需要设置的组件
     * @param target     目标组件
     * @param insets     内边距
     * @param gridx      网格的 x 位置
     * @param gridy      网格的 y 位置
     * @param ipadx      内部填充的宽度
     * @param ipady      内部填充的高度
     * @param font_size  字体大小
     * @param font_style 字体样式 (如 Font.PLAIN, Font.BOLD)
     * @return GridBagConstraints 对象
     */
    public static GridBagConstraints setFormat(JComponent component, JComponent target, Insets insets, int gridx, int gridy, int ipadx, int ipady, int font_size, int font_style) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.ipadx = ipadx;
        gbc.ipady = ipady;
        gbc.insets = insets;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        if (font_size > 0)
            setCustomFont(component, font_size, font_style);

        if (component != null && target != null)
            target.add(component, gbc);
        return gbc;
    }

    /**
     * 设置组件的 GridBagConstraints 布局参数，并添加到目标组件中。
     * 返回值是 GridBagConstraints 对象，包含了设置的参数。
     * 源组件和目标组件填 null 表示不添加到任何组件。
     * 字体大小和字体样式填 0 表示不调整字体。
     * 布局参数填 null 表示不设置直接添加。
     *
     * @param component  需要设置的组件
     * @param target     目标组件
     * @param gbc        布局参数对象
     * @param font_size  字体大小
     * @param font_style 字体样式 (如 Font.PLAIN, Font.BOLD)
     * @return GridBagConstraints 对象
     */
    public static GridBagConstraints setFormat(JComponent component, JComponent target, GridBagConstraints gbc, int font_size, int font_style) {
        if (font_size > 0 && font_style > 0)
            setCustomFont(component, font_size, font_style);
        if (component != null && target != null && gbc != null)
            target.add(component, gbc);
        return gbc;
    }
}