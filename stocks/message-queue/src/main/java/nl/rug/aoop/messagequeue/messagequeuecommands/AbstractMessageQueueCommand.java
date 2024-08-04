package nl.rug.aoop.messagequeue.messagequeuecommands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.queues.MessageQueue;

/**
 * Operates on queue based on command.
 */
public abstract class AbstractMessageQueueCommand implements Command {
    /**
     * Queue for messages.
     */
    protected final MessageQueue messageQueue;

    /**
     * Constructor.
     * @param messageQueue queue to put messages in
     */
    protected AbstractMessageQueueCommand(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }
}
