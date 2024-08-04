package nl.rug.aoop.command;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the possible commands.
 */
@Slf4j
public class CommandHandler {
    private final Map<String, Command> commandMap;

    /**
     * Constructor.
     */
    public CommandHandler() {
        commandMap = new HashMap<>();
    }

    /**
     * Registers commands.
     * @param name name of the command
     * @param command passes interface to reduce coupling
     */
    public void registerCommand(String name, Command command) {
        commandMap.put(name, command);
    }

    /**
     * Executes command.
     * @param command command
     * @param params parameters of message
     * @return command
     */
    public String executeCommand(String command, Map<String, Object> params) {
        if(commandMap.containsKey(command)) {
            commandMap.get(command).execute(params);
            return command;
        }
        log.error("Command not found");
        return "Command not found";
    }

}