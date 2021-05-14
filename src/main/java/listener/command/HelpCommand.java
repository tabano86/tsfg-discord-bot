package listener.command;

import listener.AbstractCommand;
import model.CommandContext;

public class HelpCommand extends AbstractCommand {

    public HelpCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "help";
    }

    @Override
    public void setOptions() {
    }

    @Override
    public void handle() {
        this.help();
    }

    @Override
    public void help() {
        String msg =
                """
                                                
                        **[TSFG Bot Commands]**
                        ```
                        play        play a song
                        stop        stop current song
                        volume      set volume (0-100)
                        pm          private message a user
                        repeat      repeat text
                        stock       get a stock quote based on symbol
                        help        get help (duh..)```""";

        this.sendMessageToAuthorChannel(msg);
    }
}