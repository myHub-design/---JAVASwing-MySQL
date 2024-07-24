package org.example;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.net.URL;


public class RankingPanel extends JScrollPane {
    public RankingPanel(String[][] usersData) {
        // 表格列名称
        String[] columnNames = {"Name", "Score"};

        // 创建表格模型
        DefaultTableModel model = new DefaultTableModel(usersData, columnNames);

        // 创建 JTable
        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false); // 设置单元格透明
                }
                return c;
            }
        };

        // 隐藏表头
        table.setTableHeader(null);

        // 设置表格透明
        table.setOpaque(false);
        table.setBackground(new Color(0, 0, 0, 0));
        table.setShowGrid(false); // 禁用网格线

        // 设置单元格渲染器透明
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false); // 设置单元格透明
                }
                return c;
            }
        };
        table.setDefaultRenderer(Object.class, cellRenderer);

        // 将 JTable 添加到 JScrollPane 中
        add(table);//创建JScrollPane对象
        setOpaque(false);
        getViewport().setOpaque(false); // 设置视口透明
        setBorder(BorderFactory.createEmptyBorder()); // 设置边框透明

        // 设置滚动条透明
        getVerticalScrollBar().setOpaque(false);
        getHorizontalScrollBar().setOpaque(false);
        getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(0, 0, 0, 0); // 滑块颜色
                this.trackColor = new Color(0, 0, 0, 0); // 轨道颜色
            }
        });
        getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(0, 0, 0, 0); // 滑块颜色
                this.trackColor = new Color(0, 0, 0, 0); // 轨道颜色
            }
        });

        // 加载背景图片
        URL img1URL = Main.class.getResource("/img_7.png");
        if (img1URL != null) {
            ImageIcon imageIcon = new ImageIcon(img1URL);

            // 创建背景标签
            JLabel backgroundLabel = new JLabel(imageIcon) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            backgroundLabel.setLayout(null);

            // 设置 JScrollPane 位置和大小
            setBounds(50, 0, 200, 400); // 调整表格位置和大小

            // 将 JScrollPane 添加到背景标签
            backgroundLabel.add(this);

            // 设置内容面板
            //setContentPane(backgroundLabel);***************
        } else {
            System.out.println("Image not found");
        }
    }
}
