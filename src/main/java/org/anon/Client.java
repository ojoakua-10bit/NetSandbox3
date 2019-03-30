package org.anon;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter oStream;
    private Scanner iStream;
    private String username;

    private static Client instance = new Client();

    public static Client getInstance() {
        return instance;
    }

    private Client() {
        socket = null;
        oStream = null;
        iStream = null;
    }

    public void connect(String host, int port, String username) throws IOException {
        InetAddress address = InetAddress.getByName(host);
        socket = new Socket(address, port);
        oStream = new PrintWriter(socket.getOutputStream(), true);
        iStream = new Scanner(socket.getInputStream());
        this.username = username;
        oStream.println(username);
    }

    public void disconnect() throws IOException {
        socket.close();
        socket = null;
    }

    protected PrintWriter getOStream() {
        return oStream;
    }

    protected Scanner getIStream() {
        return iStream;
    }

    public boolean isConnected() {
        return socket != null;
    }

    public void sendMessage(String message) {
        oStream.println(message);
    }

    public void renameUsername(String username) {
        sendMessage("/rename " + username);
        this.username = username;
    }
}
