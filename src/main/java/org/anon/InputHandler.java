package org.anon;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputHandler implements Runnable {
    private Client client;
    private Scanner iStream;
    private MainForm form;

    public InputHandler() throws NullPointerException {
        client = Client.getInstance();
        iStream = client.getIStream();
        form = MainForm.getInstance();
    }

    @Override
    public void run() {
        try {
            while (client.isConnected()) {
                String message = iStream.nextLine();
                String[] token = message.trim().split(" ");
                switch (token[0]) {
                    case "/auth":
                        new PassDialog().show();
                        break;
                    case "/userlist":
                        if (token.length > 1) form.updateUserList(token);
                        break;
                    default:
                        form.addMessage(message);
                        break;
                }
            }
        }
        catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            try {
                form.setDisconnect();
                form.showErrorMessage("Connection to server disconnected.");
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            catch (NullPointerException ex) {
                System.out.println("Client already disconnected.");
            }
        }
    }
}
