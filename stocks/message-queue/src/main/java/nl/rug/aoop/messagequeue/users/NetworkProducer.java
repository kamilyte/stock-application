package nl.rug.aoop.messagequeue.users;

import nl.rug.aoop.messagequeue.messages.MQMessage;
import nl.rug.aoop.messagequeue.messages.NetworkMessage;
import nl.rug.aoop.networking.client.NetworkClient;

/**
 * Producer puts messages to server.
 */
public class NetworkProducer implements Producer {
    private NetworkClient client;

    /**
     * Constructor for network producer.
     * @param client is going to send a message to the message queue via network.
     */
    public NetworkProducer(NetworkClient client) {
        this.client = client;
    }

    /**
     * Takes mqMessage, constructs a new command NetworkMessage and sends it to the server.
     * @param mqMessage message to be queued.
     */
    public void put(MQMessage mqMessage){
        if(mqMessage.getHeader() == null || mqMessage.getValue() == null) {
            throw new IllegalArgumentException();
        }
        NetworkMessage putMessage = new NetworkMessage(null, null).createPutMessage(mqMessage);
        client.send(putMessage.convertToJSON());
    }
}
