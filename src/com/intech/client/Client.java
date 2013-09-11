package com.intech.client;

import com.intech.shared.Messenger;
import com.intech.shared.message.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {

    private LoginFrame loginFrame;
    private ConsoleFrame consoleFrame;

    private String serverName;
    private final String address;
    private final int port;

    private Socket socket;
    private Messenger messenger;

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
        init();
    }

    private void init() {
        serverName = address + ":" + port;
        loginFrame = new LoginFrame(serverName);
        consoleFrame = new ConsoleFrame(serverName);

        loginFrame.getLoginButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        consoleFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                processKey(e.getKeyChar());
            }
        });

        WindowAdapter windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        };
        loginFrame.addWindowListener(windowAdapter);
        consoleFrame.addWindowListener(windowAdapter);
    }

    @Override
    public void run() {
        loginFrame.setVisible(true);
        connect();
    }

    private void connect() {
        try {
            socket = new Socket(address, port);
            messenger = new Messenger(socket);
            loginFrame.setConnected();
        } catch(IOException exc) {
            loginFrame.setFailed();
        }
    }

    private void login() {
        try {
            String username = loginFrame.getUsername();
            String password = loginFrame.getPassword();
            if(username.isEmpty() || password.isEmpty()) {
                return;
            }

            Message request = new LoginMessage(username, password);
            Message response = messenger.exchangeMessages(request);

            if(response.getCode() == MessageCode.OK) {
                loginFrame.setVisible(false);
                consoleFrame.setTitle(serverName + " : " + loginFrame.getUsername());
                consoleFrame.setVisible(true);
                getContent();
            } else if(response.getCode() == MessageCode.FAIL_LOGIN) {
                loginFrame.setMessage("Login failed. Retry.");
            }
        } catch(IOException exc) {
            exc.printStackTrace();
            loginFrame.setFailed();
        }
    }

    private void getContent() {
        try {
            Message request = new Message(MessageCode.GET_CONTENT);
            Message response = messenger.exchangeMessages(request);
            consoleFrame.setTextContent(response.toString());
        } catch(IOException exc) {
            consoleFrame.setTitle(serverName + " : " + "connection lost.");
        }
    }

    private void processKey(char key) {
        try {
            Message request = new KeyMessage(key);
            Message response = messenger.exchangeMessages(request);
            consoleFrame.setTextContent(response.toString());
        } catch(IOException exc) {
            exc.printStackTrace();
            consoleFrame.setTitle(serverName + " : " + "connection lost.");
        }
    }

    private void exit() {
        try {
            messenger.sendMessage(new Message(MessageCode.EXIT));
            messenger.close();
            socket.close();
        } catch(IOException exc) {
        } finally {
            System.exit(0);
        }
    }

}
