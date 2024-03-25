package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

public class ClientHandler {
    Socket clientSocket = null;
    CellarManager cellarManager = null;

    public ClientHandler(Socket clientSocket, CellarManager cellarManager) {
        this.clientSocket = clientSocket;
        this.cellarManager = cellarManager;
    }

    void handle(){
        BufferedReader in;
        in = getBufferedReader();
        PrintWriter out = null;
        out = getPrintWriter(out);
        readerLoop(in, out);

    }
    private BufferedReader getBufferedReader() {
        BufferedReader in;
        try {
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return in;
    }
    private PrintWriter getPrintWriter(PrintWriter out) {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }
    private void readerLoop(BufferedReader in, PrintWriter out) {
        String s = "";
        try {
            while ((s = in.readLine()) != null) {
                String response = getWines(s);
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String getWines(String s) {
        switch (s.toLowerCase()) {
            case "red":
                return listWinesByType("red");
            case "white":
                return listWinesByType("white");
            case "sorted_by_name":
                return listWinesSortedByName();
            case "sorted_by_price":
                return listWinesSortedByPrice();
            default:
                return "No found command.";
        }
    }
    private String listWinesByType(String type) {
        StringBuilder sb = new StringBuilder();
        for (Wine wine : cellarManager.wines) {
            if (wine.type.equalsIgnoreCase(type)) {
                sb.append(wine.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    private String listWinesSortedByName() {
        List<Wine> sortedList = new ArrayList<>(cellarManager.wines);
        Collections.sort(sortedList, new Comparator<Wine>() {
            @Override
            public int compare(Wine w1, Wine w2) {
                return w1.getName().compareTo(w2.getName());
            }
        });
        return winesToString(sortedList);
    }

    private String listWinesSortedByPrice() {
        List<Wine> sortedList = new ArrayList<>(cellarManager.wines);
        Collections.sort(sortedList, new Comparator<Wine>() {
            @Override
            public int compare(Wine w1, Wine w2) {
                return Double.compare(w1.getPrz(), w2.getPrz());
            }
        });
        return winesToString(sortedList);
    }

    private String winesToString(List<Wine> wines) {
        StringBuilder sb = new StringBuilder();
        for (Wine wine : wines) {
            sb.append(wine.toString()).append("\n");
        }
        return sb.toString();
    }
}
