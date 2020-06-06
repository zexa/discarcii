package xyz.zexa.discarcii;

import xyz.zexa.discarcii.command.Command;
import xyz.zexa.discarcii.exception.UnknownCommandException;

import javax.naming.ConfigurationException;
import java.util.HashMap;

public class CommandRegistry {
    private static final HashMap<String, Command> commands = new HashMap<String, Command>();

    public CommandRegistry registerCommand(String commandName, Command command) throws ConfigurationException {
        if (commands.containsKey(commandName)) {
            throw new ConfigurationException(
                "Attempted to register command with prefix " + commandName + " but such command already exists."
            );
        }

        commands.put(commandName, command);

        return this;
    }

    public Command getCommand(String commandName) throws UnknownCommandException {
        if (!commands.containsKey(commandName)) {
            throw new UnknownCommandException("Command with name " + commandName + " does not exist.");
        }

        return commands.get(commandName);
    }
}
