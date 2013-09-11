package com.intech.server;

import com.intech.server.db.DBHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server extends Thread {

    public static final int TIMEOUT = 5000;
    public static final int MAX_SESSION = 100;

    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private KeyboardInputThread keyboardInputThread;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(TIMEOUT);
        threadPool = Executors.newFixedThreadPool(MAX_SESSION);
        keyboardInputThread = new KeyboardInputThread(new StopListener() {
            @Override
            public void isStopped() {
                interrupt();
            }
        });
        keyboardInputThread.start();

        DBHelper.getInstance();
    }

    @Override
    public void run() {
        try {
            Socket socket;
            while(!isInterrupted()) {
                try {
                    socket = serverSocket.accept();
                    threadPool.submit(new UserSession(socket));
                } catch(SocketTimeoutException exc) {}
            }
        } catch(IOException exc) {
            exc.printStackTrace();
        }

        threadPool.shutdownNow();
        try {
            threadPool.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch(InterruptedException exc) {}
    }
}
