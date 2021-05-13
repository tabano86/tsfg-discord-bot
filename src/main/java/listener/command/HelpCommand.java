package listener.command;

import lavaplayer.PlayerManager;
import listener.AbstractCommand;
import model.InputCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand extends AbstractCommand {

    public HelpCommand(InputCommand inputCommand, MessageReceivedEvent event, PlayerManager audioPlayerManager) {
        super(inputCommand, event, audioPlayerManager);
    }

    public static String getText() {
        return "help";
    }

    @Override
    public void handle() {
        String msg =
                """
                        [TSFG Bot Commands]
                        play - play a song
                        stop - stop current song
                        volume - set volume (0-100)
                        pm - private message a user
                        repeat - repeat text
                        help""";

        this.sendMessageToAuthorChannel(msg);
    }
}