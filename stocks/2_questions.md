<!-- You can find the questions on Brightspace under Assignments or in the 2022_Assignments repository -->

# Question 1: Describe the benefits of using this `MessageHandler` interface. (~50-100 words)
A message handler interface is meant to receive incoming messages during a web socket conversation, where each message
communicated uses one thread at a time to call the methods within the interface. It allows us to separate the networking 
and message queue modules, which decouples and reduces the dependencies of modules on each other. This is because the 
interface specifies the behaviour as to how the messages should be handled, rather than specifying the implementations.

# Question 2
It makes me angry because it is inefficient as it uses more paths that have to be tested, which can increase response 
time.
```java
import java.util.ArrayList;

public class BetterImplementation {
    private Car car;
    private CommandHandler commandHandler;
    
    public BetterImplementation(Car car){
        this.car = car;
        this.commandHandler = carCommandFactory.create();
    }
    
    public String execution(String command) {
        commandHandler.executeCommand(command);
    }
}

public class CommandHandler {
    private final Map<String, Command> commandMap;

    public CommandHandler() {
        commandMap = new HashMap<>();
    }
    
    public void registerCommand(String name, Command command) {
        commandMap.put(name, command);
    }
    
    public String executeCommand(String command) {
        if(commandMap.containsKey(command)) {
            commandMap.get(command).execute();
            return command;
        }
        log.error("Command not found");
        return "Command not found";
    }
}

public class CarCommandFactory {
    private Car car;
    
    public CarCommandFactory(Car car) {
        this.car = car;
    }
    
    @Override
    public CommandHandler create() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("steer.left", new SteerLeftCommand(car));
        commandHandler.registerCommand("steer.right", new SteerRightCommand(car));
        commandHandler.registerCommand("engine.start", new EngineStartCommand(car));
        commandHandler.registerCommand("engine.stop", new EngineStopCommand(car));
        commandHandler.registerCommand("pedal.gas", new PedalGasCommand(car));
        commandHandler.registerCommand("pedal.brake", new PedalBrakeCommand(car));
        return commandHandler;
    }
}


```

# Question 3
# 1. What is this other pattern? What advantage does it provide to the solution? (~50-100 words)
The other pattern is Chain of Responsibility, because it has a set of different commmand objects that are handled 
separately by a handler. The advantages it provides is that it promotes loose coupling between the handler and what will 
process the commands. It also makes it easier to maintain as you can update the handler without worrying about what is 
executing the commands, which also adds flexibility as you can add as many commands as you want to the chain. 

# 2.

```java

public abstract class CommandHandler {
    private ArrayList<Command> commands;

    public CommandHandler() {
        this.commands = new ArrayList<>();
    }

    public CommandHandler setNext(CommmandHandler next) {
        this.add(next);
        return this;
    }

    public void handle(Config config) {
        Iterator<Command> itr = commands.iterator();
        while (itr.hasNext()){
            itr.execute(config);
        }
    }

    public abstract boolean canHandle(Config config) {
        boolean bool = config.get("broadcast");
        return bool;
    }

    public abstract void execute(Config config) {
        this.canHandle(config);
    }
}
```