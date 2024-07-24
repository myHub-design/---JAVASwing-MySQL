package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ForumApp extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> messageList;
    private JTextArea messageArea;
    private JButton postButton;

    public ForumApp() {
        setTitle("Forum App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create components
        listModel = new DefaultListModel<>();
        messageList = new JList<>(listModel);
        messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        messageList.setVisibleRowCount(-1);
        JScrollPane listScrollPane = new JScrollPane(messageList);

        messageArea = new JTextArea(3, 30);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);

        postButton = new JButton("Post");

        // Panel for posting new messages
        JPanel postPanel = new JPanel(new BorderLayout());
        postPanel.add(new JLabel("New Message:"), BorderLayout.NORTH);
        postPanel.add(messageScrollPane, BorderLayout.CENTER);
        postPanel.add(postButton, BorderLayout.SOUTH);

        // Add components to the frame
        add(listScrollPane, BorderLayout.CENTER);
        add(postPanel, BorderLayout.SOUTH);

        // Add action listeners
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postMessage();
            }
        });

        setVisible(true);
    }

    private void postMessage() {
        String message = messageArea.getText().trim();
        if (!message.isEmpty()) {
            listModel.addElement("Post: " + message);
            messageArea.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ForumApp();
            }
        });
    }
}
