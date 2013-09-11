package com.intech.client;

import javax.swing.*;

public class LoginFrame extends JFrame {

    private Icon red_icon;
    private Icon yellow_icon;
    private Icon green_icon;

    private JPanel panel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton loginButton;
    private JLabel serverStateLabel;
    private JLabel serverInfoLabel;

    public LoginFrame(String name) {
        super(name);
        init();
    }

    private void init() {
        red_icon    = loadIcon("/com/intech/client/images/red.png");
        yellow_icon = loadIcon("/com/intech/client/images/yellow.png");
        green_icon  = loadIcon("/com/intech/client/images/green.png");

        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setWaited();

        this.pack();
        this.setResizable(false);
    }

    private Icon loadIcon(String path) {
        try {
            return new ImageIcon(getClass().getResource(path));
        } catch(Exception exc) {
            return null;
        }
    }

    private void setServerState(Icon icon, String info) {
        serverStateLabel.setIcon(icon);
        serverInfoLabel.setText(info);
    }

    public void setWaited() {
        setServerState(yellow_icon, "Wait connection...");
        setEnabledFields(false);
    }

    public void setFailed() {
        setServerState(red_icon, "Connection failed.");
        setEnabledFields(false);
    }

    public void setConnected() {
        setServerState(green_icon, "Success connection.");
        setEnabledFields(true);
    }

    private void setEnabledFields(boolean state) {
        loginButton.setEnabled(state);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    @SuppressWarnings("deprecation")
    public String getPassword() {
        return passwordField.getText();
    }

    public void setMessage(String message) {
        serverInfoLabel.setText(message);
    }
}
