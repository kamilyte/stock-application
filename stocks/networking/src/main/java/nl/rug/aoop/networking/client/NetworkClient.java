package nl.rug.aoop.networking.client;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.networking.MessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/*
Client
A client should be able to connect to a server using an InetAddress
provided in its constructor.
A client should be able to send String
messages to the server.
Once a client is running, it should be able to
continuously receive String messages from the server.
 */

/**
 * Sends messages to server.
 */
@Slf4j
public class NetworkClient implements Runnable, Communicator {
    private final int TIMEOUT = 99999;
    @Getter
    private boolean connected;
    private Socket socket;
    private InetSocketAddress address;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    @Getter
    private boolean running = false;
    private MessageHandler messageHandler;

    /**
     * Constructor.
     * @param address used to connect to server
     * @param messageHandler specifies behaviour of how messages should be handled
     * @throws IOException if socket isnt connected
     */
    public NetworkClient(InetSocketAddress address, MessageHandler messageHandler) throws IOException {
        this.address = address;
        this.messageHandler = messageHandler;
        init(address);
        connected = true;
    }

    private void init(InetSocketAddress address) throws IOException {
        socket = new Socket();
        socket.connect(address, TIMEOUT);
        if(!socket.isConnected()) {
            throw new IOException("Socket is not connected");
        }
        outputStream = new PrintWriter(socket.getOutputStream(), true);
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Sends message.
     * @param message json message
     */
    public void send(String message) {
        if (message == null) {
            throw new IllegalArgumentException();
        }
        outputStream.println(message);
        log.info("Message sent to a server" + message);

    }

    @Override
    public void run() {
        running = true;
        log.info("Client started on port " + socket.getLocalPort());
        while (running) {
            String incomingMessage = null;
            try {
                incomingMessage = inputStream.readLine();
                if (incomingMessage == null) {
                    terminate();
                    break;
                }
            } catch (IOException e) {
                log.error("Failed to read message.");
            }
            messageHandler.handleMessage(incomingMessage, this);
        }
    }

    /**
     * Closes socket connection.
     */
    public void terminate() {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            log.error("Could not close socket.");
        }
    }
}
