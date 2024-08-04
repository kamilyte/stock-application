package nl.rug.aoop.networking;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.client.NetworkClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class NetworkClientTest {
    private int serverPort;
    private boolean serverStarted = false;

    private PrintWriter serverSideOut;
    private BufferedReader serverSideIn;

    private void startServer() {
        new Thread(() -> {
            try {
                ServerSocket servSocket = new ServerSocket(0);
                serverPort = servSocket.getLocalPort();
                serverStarted = true;

                Socket serverSideSocket = servSocket.accept();
                serverSideOut = new PrintWriter(serverSideSocket.getOutputStream());
                serverSideIn = new BufferedReader(new InputStreamReader(serverSideSocket.getInputStream()));

                log.info("worked");
            } catch (IOException e) {
                log.error("StartServer test did not work");
            }
        }).start();

        await().atMost(2, TimeUnit.SECONDS).until(() -> serverStarted);

    }

    @Test
    public void testClientConstructorWithRunningServer() throws IOException {
        startServer();
        InetSocketAddress address = new InetSocketAddress("localhost", serverPort);
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        NetworkClient networkClient = new NetworkClient(address, mockHandler);
        assertTrue(networkClient.isConnected());
    }
}
