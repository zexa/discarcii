package xyz.zexa.discarcii.command;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;
import xyz.zexa.discarcii.exception.CommandException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ReactifyCommand implements Command{
    private final HashMap<Character, String> reactionMap = new HashMap<Character, String>();

    public ReactifyCommand addReaction(Character character, String reaction) throws Exception {
        if (reactionMap.containsKey(character)) {
            throw new Exception("Attempted to register reaction '" + reaction + "' for character '" + character + "' but a reaction for this character already exists");
        }

        reactionMap.put(character, reaction);

        return this;
    }

    @Override
    public String getCommandDescription() {
        return "Example: !_reactify <id> gay";
    }

    private void applyNextReaction(Iterator<String> reactions, Message targetMessage, MessageReceivedEvent event) {
        if (reactions.hasNext()) {
            String reaction = reactions.next();
            targetMessage.addReaction(reaction).queue(response -> {
                waitForReaction(
                    reaction,
                    targetMessage,
                    0,
                    reactions,
                    event
                );
            });
        }
    }

    private void waitForReaction(
        String reaction,
        Message targetMessage,
        int iterations,
        Iterator<String> nextReactions,
        MessageReceivedEvent event
    ) {
        if (iterations > 9) {
            System.out.println("Stopping the spam, something went wrong");
        }

        getTargetMessageRestAction(targetMessage.getId(), event).queue(updatedTargetMessage -> {
            if (mapReactionsToStringArrayList(updatedTargetMessage.getReactions()).contains(reaction)) {
                System.out.println(targetMessage.getId() + " contains reaction " + reaction);
                applyNextReaction(
                    nextReactions,
                    updatedTargetMessage,
                    event
                );
            } else {
                System.out.println(targetMessage.getId() + " doesnt contain reaction " + reaction);
                waitForReaction(
                    reaction,
                    updatedTargetMessage,
                    iterations + 1,
                    nextReactions,
                    event
                );
            }
        });
    }

    private ArrayList<String> mapReactionsToStringArrayList(List<MessageReaction> reactions) {
        ArrayList<String> result = new ArrayList<String>();
        reactions.forEach(reaction -> {
            result.add(reaction.getReactionEmote().getAsCodepoints());
        });

        return result;
    }

    @Override
    public void execute(String commandArgument, MessageReceivedEvent event) throws CommandException {
        String[] splitCommand = commandArgument.split(" ", 2);

        if (splitCommand.length < 2) {
            throw new CommandException("Missing required argument");
        }

        applyNextReaction(
            mapToReactions(processInput(splitCommand[1])),
            getTargetMessageRestAction(splitCommand[0], event).complete(),
            event
        );
    }

    private RestAction<Message> getTargetMessageRestAction(String targetId, MessageReceivedEvent event) {
        return event.getChannel().retrieveMessageById(targetId);
    }

    private Character[] processInput(String argument) throws CommandException {
        HashMap<Character, Boolean> letterHash = new HashMap<Character, Boolean>();
        ArrayList<Character> result = new ArrayList<Character>();

        for(Character character : argument.toCharArray()) {
            if (letterHash.containsKey(character)) {
                throw new CommandException("Cannot reactify argument with duplicate characters");
            }

            letterHash.put(character, true);
            result.add(character);
        }

        return result.toArray(Character[]::new);
    }

    private Iterator<String> mapToReactions(Character[] processedArgument) throws CommandException{
        ArrayList<String> result = new ArrayList<String>();
        for (Character character : processedArgument) {
            if (!reactionMap.containsKey(character)) {
                throw new CommandException("Cannot map character '" + character + "' to reaction - no reaction for such character registered");
            }

            result.add(reactionMap.get(character));
        }

        return result.iterator();
    }
}
