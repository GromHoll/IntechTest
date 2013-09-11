package com.intech.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

public class ConsoleFrame extends JFrame {

    private JTextArea textArea;

    public ConsoleFrame(String name) {
        super(name);
        init();
    }

    private void init() {
        this.textArea = new JTextArea();
        textArea.setDisabledTextColor(Color.BLACK);
        textArea.setEnabled(false);
        this.add(new JScrollPane(textArea));

        this.setSize(new Dimension(500, 500));
        this.setResizable(false);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }



    public void setTextContent(String content) {
        textArea.setText(content);
    }

}
