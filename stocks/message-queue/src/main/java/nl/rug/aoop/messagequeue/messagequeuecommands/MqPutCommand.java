package nl.rug.aoop.messagequeue.messagequeuecommands;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.messages.MQMessage;
import nl.rug.aoop.messagequeue.queues.MessageQueue;

import java.util.Map;

/**
 * Puts messages into queue.
 */
@Slf4j
public class MqPutCommand extends AbstractMessageQueueCommand {

    /**
     * Constructor.
     * @param messageQueue queue to be operated on
     */
    public MqPutCommand(MessageQueue messageQueue) {
        super(messageQueue);
    }

    /**
     * Executes command.
     * @param params the parameters of the message
     */
    @Override
    public void execute(Map<String, Object> params) {
        MQMessage message = new MQMessage(null, null);
        String JSonString = (String)params.get("body");
        log.info("JSON String in mqPutCommand: " + JSonString);
        messageQueue.enqueue(message.convertToMessage(JSonString));
    }
}
