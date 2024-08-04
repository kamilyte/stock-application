package nl.rug.aoop.networking;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.server.ClientHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.NullInsteadOfMockException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class NetworkClientHandlerTest {
    private int serverPort;
    private boolean serverStarted;
    private Socket serverSideSocket;
    private MessageHandler mockMessageHandler = Mockito.mock(MessageHandler.class);

    private void startServer() {
        new Thread(() -> {
            try {
                ServerSocket s = new ServerSocket(0);
                serverPort = s.getLocalPort();
                serverStarted = true;
                serverSideSocket = s.accept();
            } catch (IOException e) {
                fail("Could not start server.");
            }
        }).start();

        await().atMost(2, TimeUnit.SECONDS).until(() -> serverStarted);
    }


    @Test
    public void testRun() throws IOException {
        startServer();

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", serverPort));
            await().atMost(5, TimeUnit.SECONDS).until(() -> serverSideSocket != null);

            ClientHandler clientHandler = new ClientHandler(serverSideSocket, mockMessageHandler);
            new Thread(clientHandler).start();
            await().atMost(5, TimeUnit.SECONDS).until(clientHandler::isRunning);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String testMessage = "Test message";
            out.println(testMessage);
            mockMessageHandler.handleMessage(testMessage, clientHandler);
            Mockito.verify(mockMessageHandler, Mockito.times(2)).handleMessage(testMessage, clientHandler);
        }


    }
}
