package com.intech.server;

public class ServerEntry {

    public final static int DEFAULT_PORT = 505;

    public static void main(String args[]) {
        int port = DEFAULT_PORT;

        if(args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch(NumberFormatException exc) {
                exc.printStackTrace();
            }
        }

        try {
            Server server = new Server(port);
            server.start();
        } catch(Exception exc) {
            exc.printStackTrace();
        }
    }
}
