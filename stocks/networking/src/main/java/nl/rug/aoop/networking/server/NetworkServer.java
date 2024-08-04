package nl.rug.aoop.networking.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.MessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Accepts connections so we can send data to client.
 */
@Slf4j
public class NetworkServer implements Runnable {
    private ServerSocket serverSocket;
    @Getter
    private boolean running = false;
    private ExecutorService executorService;
    private ArrayList<ClientHandler> clientHandlerList;
    @Getter
    private int threadNum = 0;
    private MessageHandler messageHandler;

    /**
     * Constructor of server.
     * @param port port number of incoming connection
     * @param messageHandler specifies behaviour of how to handle messages
     * @throws IOException catches if thread execution was successful
     */
    public NetworkServer(int port, MessageHandler messageHandler) throws IOException {
        serverSocket = new ServerSocket(port);
        this.executorService = Executors.newCachedThreadPool();
        this.messageHandler = messageHandler;
        clientHandlerList = new ArrayList<>();
    }

    /**
     * Runs the server.
     */
    public void run() {
        log.info("Server started on port " + serverSocket.getLocalPort());
        running = true;
        while(running) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, messageHandler);
                clientHandlerList.add(clientHandler);
                log.info("Size of clientList " + clientHandlerList.size());
                executorService.submit(clientHandler);
            } catch (IOException e) {
                log.error("Client handler was not successful");
            }
        }
    }

    public int getNumberOfConnections() {
        return clientHandlerList.size();
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }
}
