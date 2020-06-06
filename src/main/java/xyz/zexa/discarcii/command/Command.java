package xyz.zexa.discarcii.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.zexa.discarcii.exception.CommandException;

public interface Command {
    public String getCommandDescription();
    public void execute(String commandArgument, MessageReceivedEvent event) throws CommandException;
}
