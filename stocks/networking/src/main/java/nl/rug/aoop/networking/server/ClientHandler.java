package nl.rug.aoop.networking.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.MessageHandler;
import nl.rug.aoop.networking.Communicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles connection between client and server.
 */
@Slf4j
public class ClientHandler implements Runnable, Communicator {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    @Getter
    private boolean running = false;
    private MessageHandler messageHandler;

    /**
     * Constructor of ClientHandler.
     * @param socket creates port to connect to
     * @param messageHandler specifies how messages should be handled
     * @throws IOException if input couldnt be processed
     */
    public ClientHandler(Socket socket, MessageHandler messageHandler) throws IOException {
        this.socket = socket;
        this.messageHandler = messageHandler;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Runs the client handler.
     */
    public void run() {
        running = true;
        while (running) {
            try {
                String inputLine = in.readLine();
                if (inputLine == null) {
                    terminate();
                    break;
                }
                messageHandler.handleMessage(inputLine,this);
                log.info("Received a message from client " + inputLine);
            } catch (IOException e) {
                log.error("Could not process input");
            }

        }
    }

    /**
     * Prints out message.
     * @param message json message
     */
    @Override
    public void send(String message) {
        out.println(message);
    }

    /**
     * Closes the socket.
     */
    public void terminate() {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            log.error("Cannot close the socket", e);
        }
    }
}
