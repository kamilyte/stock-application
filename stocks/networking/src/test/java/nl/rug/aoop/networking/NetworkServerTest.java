package nl.rug.aoop.networking;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.server.NetworkServer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class NetworkServerTest {

    @Test
    public void testRunWithSingleConnection() throws IOException {
        MessageHandler mockMessageHandler = Mockito.mock(MessageHandler.class);
        NetworkServer networkServer = new NetworkServer(0, mockMessageHandler);
        new Thread(networkServer).start();
        await().atMost(1, TimeUnit.SECONDS).until(networkServer::isRunning);

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", networkServer.getPort()));
            assertEquals(0, networkServer.getThreadNum());
        }
    }

    @Test
    public void testRunWithMultipleConnections() throws IOException {
        MessageHandler mockMessageHandler = Mockito.mock(MessageHandler.class);
        NetworkServer networkServer = new NetworkServer(0, mockMessageHandler);
        new Thread(networkServer).start();
        await().atMost(1, TimeUnit.SECONDS).until(networkServer::isRunning);

        try {
            for (int i = 0; i <= 4; i++){
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress("localhost", networkServer.getPort()));
                await().atMost(1, TimeUnit.SECONDS).until(socket::isConnected);
                log.info("Checking after " + i + " size is " + networkServer.getNumberOfConnections());
            }
        } catch (IOException e) {
            throw new IOException("Unable to run with multiple connections");
        }
        assertEquals(5, networkServer.getNumberOfConnections());
    }
}
