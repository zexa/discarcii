package xyz.zexa.discarcii.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand implements Command{
    @Override
    public String getCommandDescription() {
        return null;
    }

    @Override
    public void execute(String commandArgument, MessageReceivedEvent event) {
        event.getChannel().sendMessage("pong").queue();
    }
}
