package com.intech.client;

public class ClientEntry {

    public final static String DEFAULT_ADDRESS = "localhost";
    public final static int DEFAULT_PORT = 505;

    public static void main(String args[]) {
        String address = DEFAULT_ADDRESS;
        int port = DEFAULT_PORT;

        if(args.length >= 1) {
            address = args[0];
        }
        if(args.length >= 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch(NumberFormatException exc) {
                exc.printStackTrace();
            }
        }

        Client client = new Client(address, port);
        client.start();
    }

}
