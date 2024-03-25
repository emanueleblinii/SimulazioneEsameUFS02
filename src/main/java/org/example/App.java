package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App {

    static int portNumber = 1234;
    private static CellarManager cm = new CellarManager();
    public static void main( String[] args ) {
        cm.add(new Wine("Chianti", "red", 12.50));
        cm.add(new Wine("Barolo", "red", 30.00));
        cm.add(new Wine("Montepulciano", "red", 18.00));
        cm.add(new Wine("Sauvignon Blanc", "white", 15.00));
        cm.add(new Wine("Chardonnay", "white", 25.00));
        cm.add(new Wine("Pinot Grigio", "white", 12.50));

        System.out.println("Server started!");

        ServerSocket serverSocket = getServerSocket();
        while (true){
            Socket clientSocket = accept(serverSocket);
            ClientHandler ch = new ClientHandler(clientSocket, cm);
            ch.handle();
        }
    }
    private static Socket accept(ServerSocket serverSocket) {
        Socket clientSocket;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clientSocket;
    }

    private static ServerSocket getServerSocket() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return serverSocket;
    }
}
