package nl.rug.aoop.command;

import java.util.Map;

/**
 * Command interface.
 */
public interface Command {

    /**
     * executes command without creating dependencies.
     * @param params the parameters of the message
     */
    void execute(Map<String, Object> params);
}
