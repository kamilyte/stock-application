package nl.rug.aoop;

import nl.rug.aoop.messagequeue.messages.MQMessage;
import nl.rug.aoop.messagequeue.users.NetworkProducer;
import nl.rug.aoop.networking.client.NetworkClient;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * Class for individual trader bot (client side of a server).
 */
public class TraderBot implements Runnable {
    private Trader trader;
    private NetworkProducer producer;
    private Boolean running;
    private Random r;
    private RandomOrderGenerator randomOrderGenerator;

    /**
     * Constructor for each trader bot.
     * @param trader assigns a trader.
     * @param client assigns a client, so that we can have a producer to put messages
     *               in queue.
     * @throws IOException exception.
     */
    public TraderBot(Trader trader, NetworkClient client) throws IOException {
        r = new Random();
        this.trader = trader;
        this.producer = new NetworkProducer(client);
        randomOrderGenerator = new RandomOrderGenerator();
        new Thread(client).start();
        await().atMost(1, TimeUnit.SECONDS).until(client::isRunning);
    }

    /**
     * Overrides runnable interface run() method, so that each trader bot could
     * be started on separate thread.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Thread.sleep(r.nextInt(100, 4000));

                String id = trader.getId().toString();
                String command;
                String order = null;
                order = randomOrderGenerator.get();
                if (order.charAt(0) == 'B') {
                    command = "Buy";
                } else {
                    command = "Sell";
                }
                producer.put(new MQMessage(command, order + id));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
