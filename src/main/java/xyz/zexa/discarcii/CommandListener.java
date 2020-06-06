package xyz.zexa.discarcii;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.zexa.discarcii.command.Command;
import xyz.zexa.discarcii.exception.CommandException;
import xyz.zexa.discarcii.exception.UnknownCommandException;

import javax.annotation.Nonnull;

public class CommandListener extends ListenerAdapter {
    private static final String COMMAND_ASCIFY = "ascify";

    private final String botPrefix;
    private final CommandRegistry commandRegistry;

    public CommandListener(
        String botPrefix,
        CommandRegistry commandRegistry
    ) {
        this.botPrefix = botPrefix;
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();

        if (!message.startsWith(botPrefix)) {
            return;
        }

        String[] commandString = message.split(botPrefix, 2)[1].split(" ", 2);
        String commandName = commandString[0];
        String commandArgument = (commandString.length == 1) ? "" : commandString[1];

        Command command;

        try {
            command = commandRegistry.getCommand(commandName);
        } catch (UnknownCommandException exception) {
            event.getChannel().sendMessage(exception.getMessage()).queue();

            return;
        }

        try {
            command.execute(commandArgument, event);
        } catch (CommandException exception) {
            event.getChannel().sendMessage(exception.getMessage()).queue();
        }

        event.getMessage().delete().queue();
    }
}
