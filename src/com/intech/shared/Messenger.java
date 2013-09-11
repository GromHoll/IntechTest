package com.intech.shared;

import com.intech.shared.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

public class Messenger {

    public static final int TIMEOUT = 100000;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Messenger(Socket socket) throws IOException {
        socket.setSoTimeout(TIMEOUT);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public synchronized Message exchangeMessages(Message msg) throws IOException {
        sendMessage(msg);
        return receiveMessage();
    }

    public synchronized void sendMessage(Message msg) throws IOException {
        if(msg != null) {
            oos.writeObject(msg);
        }
    }

    public synchronized Message receiveMessage() throws IOException {
        Message msg = null;
        try {
            msg = (Message) ois.readObject();
        } catch(SocketTimeoutException exc) {
        } catch(ClassNotFoundException exc) {
            exc.printStackTrace();
        }

        return msg;
    }

    public void close() {
        try {
            oos.close();
        } catch(IOException exc) {}

        try {
            ois.close();
        } catch(IOException exc) {}
    }
}
