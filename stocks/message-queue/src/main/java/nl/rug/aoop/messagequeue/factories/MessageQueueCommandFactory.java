package nl.rug.aoop.messagequeue.factories;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.messagequeuecommands.MqPutCommand;
import nl.rug.aoop.messagequeue.queues.MessageQueue;

/**
 * Factory for commands.
 */
public class MessageQueueCommandFactory implements AbstractCommandFactory {
    private MessageQueue messageQueue;

    /**
     * Constructor.
     * @param messageQueue queue to be operated on
     */
    public MessageQueueCommandFactory(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    /**
     * Creates command handler for commands.
     * @return command handler
     */
    @Override
    public CommandHandler create() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("MQPut", new MqPutCommand(messageQueue));
        return commandHandler;
    }
}
