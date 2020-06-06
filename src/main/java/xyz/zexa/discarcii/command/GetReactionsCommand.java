package xyz.zexa.discarcii.command;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.zexa.discarcii.exception.CommandException;

public class GetReactionsCommand implements Command {
    @Override
    public String getCommandDescription() {
        return "Gets the reactions from the specified message id";
    }

    @Override
    public void execute(String commandArgument, MessageReceivedEvent event) throws CommandException {
        String[] splitCommandArgument = commandArgument.split(" ", 2);

        checkPermissions(event.getAuthor());

        getTargetMessage(splitCommandArgument[0], event).getReactions().forEach(reaction -> {
            MessageReaction.ReactionEmote reactionEmote = reaction.getReactionEmote();
            output(
                reactionEmote.toString() + "\n"
                    + reactionEmote.getName() + "\n"
                    + reactionEmote.getAsCodepoints() + "\n"
                    + reaction.toString() + "\n",
                event
            );
        });

    }

    private void checkPermissions(User user) {
        if (!user.getId().equals("151000187274395648")) {
            throw new CommandException("You do not have permission to execute this command.");
        }
    }

    private void output(String out, MessageReceivedEvent event) {
        System.out.println(out);
        event.getChannel().sendMessage(out).queue();
    }

    private Message getTargetMessage(String targetId, MessageReceivedEvent event) {
        return event.getChannel().retrieveMessageById(targetId).complete();
    }
}
