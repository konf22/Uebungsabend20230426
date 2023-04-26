package org.campus02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PersonServer {

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(1111)) {
            // server laufe bitte ewig
            while (true) {
                System.out.println("warte auf client");
                // warte bis client sich anmeldet
                Socket client = ss.accept();
                System.out.println("client ist da");
                ClientCommunication clientCommunication = new ClientCommunication(client);
                new Thread(clientCommunication).start(); // starte communication handler
                // ruft run() in ClientCommunication auf
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
