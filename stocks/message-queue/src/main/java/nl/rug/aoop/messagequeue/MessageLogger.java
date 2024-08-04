package nl.rug.aoop.messagequeue;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.factories.AbstractCommandFactory;
import nl.rug.aoop.messagequeue.messages.NetworkMessage;
import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.networking.MessageHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles messages over network.
 */
@Slf4j
public class MessageLogger implements MessageHandler {
    private CommandHandler commandHandler;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor.
     * @param messageQueueFactory handles messages/commands
     */
    public MessageLogger(AbstractCommandFactory messageQueueFactory) {
        commandHandler = messageQueueFactory.create();
    }

    @Override
    public void handleMessage(String jsonString, Communicator communicator) {
        NetworkMessage networkMessage = new NetworkMessage(null, null).convertToMessage(jsonString);
        Map<String, Object> params = new HashMap<>();
        if(networkMessage != null) {
            params.put("header", networkMessage.getHeader());
            params.put("body", networkMessage.getBody());
            params.put("communicator", communicator);
            commandHandler.executeCommand(networkMessage.getHeader(), params);
        }
        assert networkMessage != null;
    }
}
