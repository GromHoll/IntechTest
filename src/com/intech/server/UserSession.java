package com.intech.server;

import com.intech.server.db.DBHelper;
import com.intech.server.db.models.Reader;
import com.intech.server.menu.UserMenu;
import com.intech.shared.Messenger;
import com.intech.shared.message.ContentMessage;
import com.intech.shared.message.LoginMessage;
import com.intech.shared.message.Message;
import com.intech.shared.message.MessageCode;

import java.io.IOException;
import java.net.Socket;

public class UserSession implements Runnable {

    private final Socket socket;
    private Messenger messenger;

    private final DBHelper dbHelper = DBHelper.getInstance();

    private Reader reader;
    private UserMenu menu;

    public UserSession(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            messenger = new Messenger(socket);

            Message request, response;
            while(!Thread.currentThread().isInterrupted()) {
                request = messenger.receiveMessage();
                response = processMessage(request);
                messenger.sendMessage(response);
            }
        } catch(Exception exc) {
            exc.printStackTrace();
        } finally {
            messenger.close();
            try {
                socket.close();
            } catch(IOException exc) {}
        }
    }

    private Message processMessage(Message msg) {
        Message res = null;
        if(msg != null) {
            switch(msg.getCode()) {
                case LOGIN:
                    LoginMessage loginMsg = (LoginMessage) msg;
                    res = login(loginMsg.getUsername(), loginMsg.getPassword());
                    break;
                case GET_CONTENT:
                    res = getContent();
                    break;
                case KEY:
                    res = processKey(msg.toString().charAt(0));
                    break;
                case EXIT:
                    Thread.currentThread().interrupt();
                    break;
                default:
            }
        }
        return res;
    }

    private Message processKey(char key) {
        menu.processKey(key);
        return new ContentMessage(menu.getCurrentMenuText());
    }

    private Message getContent() {
        return new ContentMessage(menu.getCurrentMenuText());
    }

    private Message login(String username, String password) {
        Message res;
        Reader newReader = dbHelper.findReader(username);

        if(newReader == null) {
            newReader = dbHelper.createReader(username, password);
        }
        if(newReader.getPassword().equals(password)) {
            reader = newReader;
            menu = new UserMenu(reader);
            res = new Message(MessageCode.OK);
        }  else {
            res = new Message(MessageCode.FAIL_LOGIN);
        }
        return res;
    }
}
