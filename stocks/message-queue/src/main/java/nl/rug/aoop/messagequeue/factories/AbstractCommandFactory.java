package nl.rug.aoop.messagequeue.factories;

import nl.rug.aoop.command.CommandHandler;

/**
 * Command factory.
 */
public interface AbstractCommandFactory {
    /**
     * Creates command factory.
     * @return command handler for commands.
     */
    CommandHandler create();
}
