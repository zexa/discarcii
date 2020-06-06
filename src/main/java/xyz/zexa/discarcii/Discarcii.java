package xyz.zexa.discarcii;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import xyz.zexa.discarcii.command.*;

public class Discarcii {
    public static void main(String[] args) throws Exception {
        Dotenv dotenv = Dotenv.configure().load();

        JDA jda = new JDABuilder(dotenv.get("TOKEN"))
            .addEventListeners(
                new CommandListener(
                    dotenv.get("PREFIX"),
                    new CommandRegistry()
                        .registerCommand("ping", new PingCommand())
                        .registerCommand(
                            "reactify",
                            new ReactifyCommand()
                                .addReaction('a', "U+1f1e6")
                                .addReaction('b', "U+1f1e7")
                                .addReaction('c', "U+1f1e8")
                                .addReaction('d', "U+1f1e9")
                                .addReaction('e', "U+1f1ea")
                                .addReaction('f', "U+1f1eb")
                                .addReaction('g', "U+1f1ec")
                                .addReaction('h', "U+1f1ed")
                                .addReaction('i', "U+1f1ee")
                                .addReaction('j', "U+1f1ef")
                                .addReaction('k', "U+1f1f0")
                                .addReaction('l', "U+1f1f1")
                                .addReaction('m', "U+1f1f2")
                                .addReaction('n', "U+1f1f3")
                                .addReaction('o', "U+1f1f4")
                                .addReaction('p', "U+1f1f5")
                                .addReaction('q', "U+1f1f6")
                                .addReaction('r', "U+1f1f7")
                                .addReaction('s', "U+1f1f8")
                                .addReaction('t', "U+1f1f9")
                                .addReaction('u', "U+1f1fa")
                                .addReaction('v', "U+1f1fb")
                                .addReaction('w', "U+1f1fc")
                                .addReaction('x', "U+1f1fd")
                                .addReaction('y', "U+1f1fe")
                                .addReaction('z', "U+1f1ff")
                                .addReaction('A', "U+1f170U+fe0f️")
                                .addReaction('B', "U+1f171U+fe0f️")
                                .addReaction('0', "U+30U+fe0fU+20e3")
                                .addReaction('1', "U+31U+fe0fU+20e3")
                                .addReaction('2', "U+32U+fe0fU+20e3")
                                .addReaction('3', "U+33U+fe0fU+20e3")
                                .addReaction('4', "U+34U+fe0fU+20e3")
                                .addReaction('5', "U+35U+fe0fU+20e3")
                                .addReaction('6', "️U+36U+fe0fU+20e3") // Doesn't work - why?
                                .addReaction('7', "U+37U+fe0fU+20e3")
                                .addReaction('8', "U+38U+fe0fU+20e3")
                                .addReaction('9', "U+39U+fe0fU+20e3")
                        )
                        .registerCommand("getReactions", new GetReactionsCommand())
                        .registerCommand("whoAmI", new WhoAmICommand())
                )
            )
            .build()
            .awaitReady()
        ;
    }
}
