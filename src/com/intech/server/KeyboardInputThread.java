package com.intech.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyboardInputThread extends Thread {

    public final static String STOP_CMD = "STOP";
    public final static int SLEEP = 1000;

    private final BufferedReader reader;
    private final StopListener stopListener;

    public KeyboardInputThread(StopListener stopListener) {
        this.stopListener = stopListener;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {

        System.out.println("Input 'stop' for stopping server.");

        String line;
        while(!isInterrupted()) {
            try {
                if(reader.ready()) {
                    line = reader.readLine();
                    if(line.toUpperCase().equals(STOP_CMD)) {
                        stopListener.isStopped();
                        System.out.println("Stopping...");
                        interrupt();
                    }
                } else {
                    try {
                        Thread.sleep(SLEEP);
                    } catch(InterruptedException exc) {}
                }
            } catch(IOException exc) {
                exc.printStackTrace();
                interrupt();
            }
        }

        try {
            reader.close();
        } catch(IOException exc) {}
    }

}
