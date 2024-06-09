package main.java.com.library.client.gui.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClientPanel extends JPanel {
    private final List<Component> components;

    public ClientPanel(GridBagLayout layout) {
        setLayout(layout);
        components = new ArrayList<>();

        JButton button = new JButton("Button");
        GridBagConstraints gbc = new GridBagConstraints();

    }

    // 额外添加组件
    public void addComponent(Component component, GridBagConstraints constraints) {
        components.add(component);
        this.add(component, constraints);
        this.revalidate();
        this.repaint();
    }

    // 删除组件
    public void removeComponent(Component component) {
        components.remove(component);
        this.remove(component);
        this.revalidate();
        this.repaint();
    }

    // 获取所有组件
    public List<Component> getAllComponents() {
        return new ArrayList<>(components);
    }

    // 更新组件
    public void updateComponent(Component oldComponent, Component newComponent, GridBagConstraints constraints) {
        int index = components.indexOf(oldComponent);
        if (index != -1) {
            components.set(index, newComponent);
            this.remove(oldComponent);
            this.add(newComponent, constraints);
            this.revalidate();
            this.repaint();
        }
    }

    // 清空所有组件
    public void clearAllComponents() {
        components.clear();
        this.removeAll();
        this.revalidate();
        this.repaint();
    }
}