package xyz.zexa.discarcii.command;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class WhoAmICommand implements Command{
    @Override
    public String getCommandDescription() {
        return "Posts info about who the bot perceives you to be.";
    }

    @Override
    public void execute(String commandArgument, MessageReceivedEvent event) {
        User user = event.getAuthor();
        event.getChannel().sendMessage("User ID: " + user.getId() + "\n").queue();
    }
}
