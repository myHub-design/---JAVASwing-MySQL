package org.example;

import MusicPlayer.Musics;
import Project_PushBox.DataHouse;
import Project_PushBox.SelectFrame;
import Wanf.User;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
//登录界面
public class JavaSwingLogOnAndSignUp {
    static public void select() {
        LogOnAndSignUp logOnAndSignUp = new LogOnAndSignUp();

        JFrame mainFrame = new JFrame("Main Menu");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 600);
        mainFrame.setLayout(null);

        // 使用相对路径加载图片
        URL imageUrl = Main.class.getResource("/img_3.png"); // 确保图片放在 src/main/resources 目录中
        if (imageUrl != null) {
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            JLabel backgroundLabel = new JLabel(imageIcon) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            backgroundLabel.setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
            mainFrame.setContentPane(backgroundLabel);
        } else {
            System.err.println("Image not found. Make sure the file is in the correct path.");
        }

        JButton loginButton = new JButton("");
        JButton registerButton = new JButton("");
        //JButton exitButton = new JButton("Exit");

        // 设置按钮透明，只显示文字
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);

        registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);

        // 设置按钮位置和大小
        loginButton.setBounds(80, 450, 200, 50);
        registerButton.setBounds(300, 450, 200, 50);

        mainFrame.add(loginButton);
        mainFrame.add(registerButton);
        mainFrame.setVisible(true);

        mainFrame.setLocationRelativeTo(null);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(DataHouse.music).start();
                showLoginFrame(logOnAndSignUp, mainFrame);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(DataHouse.music).start();
                showRegisterFrame(logOnAndSignUp, mainFrame);
            }
        });

    }


    private static void showLoginFrame(LogOnAndSignUp logOnAndSignUp, JFrame mainFrame) {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(600, 600);

        // 使用相对路径加载图片
        URL imageUrl = Main.class.getResource("/img_2.png"); // 确保图片放在 src/main/resources 目录中
        JLabel backgroundLabel = new JLabel();
        if (imageUrl != null) {
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            backgroundLabel = new JLabel(imageIcon) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            backgroundLabel.setBounds(0, 0, loginFrame.getWidth(), loginFrame.getHeight());
        } else {
            System.err.println("Image not found. Make sure the file is in the correct path.");
        }

        // 创建JLayeredPane来管理层次
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, loginFrame.getWidth(), loginFrame.getHeight());

        // 添加背景图片到底层
        layeredPane.add(backgroundLabel, new Integer(0));

        // 创建登录面板
        JPanel loginPanel = new JPanel(null); // 使用自由布局
        loginPanel.setOpaque(false); // 确保面板透明
        loginPanel.setBounds(0, 0, loginFrame.getWidth(), loginFrame.getHeight());

        // 创建标签、文本框、密码框、按钮和下拉框
        JTextField idField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("");
        JButton exitButton = new JButton("");
        JComboBox<String> loginTypeComboBox = new JComboBox<>(new String[]{"User Login", "Administrator Login"});

        loginTypeComboBox.setOpaque(false);  // Set JComboBox to be non-opaque
        loginTypeComboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton arrowButton = new JButton();
                arrowButton.setOpaque(false);  // Set arrow button to be non-opaque
                arrowButton.setContentAreaFilled(false);
                arrowButton.setBorderPainted(false);
                return arrowButton;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                // Do not paint background
            }
        });

        // Set the renderer to transparent
        loginTypeComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setOpaque(false);
                return label;
            }
        });

        // Adding some transparency to the JComboBox background
        loginTypeComboBox.setBackground(new Color(0, 0, 0, 0));  // Fully transparent background

        // 设置组件位置和大小
        idField.setBounds(225, 260, 200,40 );
        passwordLabel.setBounds(150, 150, 80, 25);
        passwordField.setBounds(175, 373, 200, 35);
        loginTypeComboBox.setBounds(150, 220, 300, 25);
        loginButton.setBounds(100, 475, 160, 75);
        exitButton.setBounds(325, 475, 160, 75);


        // 设置文本框透明 // 设置文本框透明和无边框
        idField.setOpaque(false);
        idField.setBackground(new Color(0, 0, 0, 0));
        idField.setBorder(BorderFactory.createEmptyBorder());


        passwordField.setOpaque(false);
        passwordField.setBackground(new Color(0, 0, 0, 0));
        passwordField.setBorder(BorderFactory.createEmptyBorder());

        // 设置按钮透明，只显示文字
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);

        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);

        // 设置字体大小与文本框高度一致
        float fontSize = idField.getHeight() * 0.8f;
        idField.setFont(idField.getFont().deriveFont(fontSize));
        passwordField.setFont(passwordField.getFont().deriveFont(fontSize));

        // 添加组件到面板
        loginPanel.add(idField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginTypeComboBox);
        loginPanel.add(loginButton);
        loginPanel.add(exitButton);

        // 将登录面板添加到上层
        layeredPane.add(loginPanel, new Integer(1));

        loginFrame.setLayeredPane(layeredPane);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
        mainFrame.setVisible(false);

        // 登录按钮的事件处理
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Thread(DataHouse.music).start();
                    int id = Integer.parseInt(idField.getText());
                    String password = new String(passwordField.getPassword());
                    String loginType = (String) loginTypeComboBox.getSelectedItem();

                    if ("User Login".equals(loginType)) {
                        User user = logOnAndSignUp.logOn(id, password);
                        if (user != null) {
                            loginFrame.dispose();
                            DataHouse.NowUser = user;
                            //***********************************************
                            Musics.backgroundMusic.get(5).changeState();
                            DataHouse.LoadingGame();//启动游戏
                            //***********************************************
                            JOptionPane.showMessageDialog(loginFrame, "登录成功.欢迎 " + user.getName() + "!");
                        } else {
                            JOptionPane.showMessageDialog(loginFrame, "Invalid ID or Password");
                        }
                    } else if ("Administrator Login".equals(loginType)) {
                        logOnAndSignUp.AdministratorLogin(id, password);
                        JOptionPane.showMessageDialog(loginFrame, "Administrator Login Successful!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid input. Please enter valid data.");
                }
            }
        });

        // 退出按钮的事件处理
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(DataHouse.music).start();
                loginFrame.dispose();
                mainFrame.setVisible(true);
            }
        });
    }
    private static void showRegisterFrame(LogOnAndSignUp logOnAndSignUp, JFrame mainFrame) {
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setSize(400, 300);
        registerFrame.setLocationRelativeTo(null);
        JPanel registerPanel = new JPanel(new GridLayout(7, 2));
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel genderLabel = new JLabel("Gender:");
        JTextField genderField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();

        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");

        registerPanel.add(idLabel);
        registerPanel.add(idField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(nameLabel);
        registerPanel.add(nameField);
        registerPanel.add(genderLabel);
        registerPanel.add(genderField);
        registerPanel.add(ageLabel);
        registerPanel.add(ageField);
        registerPanel.add(registerButton);
        registerPanel.add(exitButton);


        registerFrame.add(registerPanel);
        registerFrame.setVisible(true);
        mainFrame.setVisible(false);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(DataHouse.music).start();
                try {
                    int id = Integer.parseInt(idField.getText());
                    String password = new String(passwordField.getPassword());
                    String name = nameField.getText();
                    String gender = genderField.getText();
                    int age = Integer.parseInt(ageField.getText());

                    User newUser = new User(id, name, password);
                    newUser.setGender(gender);
                    newUser.setAge(age);
                    boolean isSuccessfully = logOnAndSignUp.signUp(newUser);

                    if (isSuccessfully) JOptionPane.showMessageDialog(registerFrame, "User Registered Successfully");
                    // else
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(registerFrame, "Invalid input. Please enter valid data.");
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(DataHouse.music).start();
                select();
                registerFrame.dispose();
            }
        });
    }
}
