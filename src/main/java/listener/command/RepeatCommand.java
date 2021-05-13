package listener.command;

import lavaplayer.PlayerManager;
import listener.AbstractCommand;
import model.InputCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RepeatCommand extends AbstractCommand {

    public RepeatCommand(InputCommand inputCommand, MessageReceivedEvent event, PlayerManager audioPlayerManager) {
        super(inputCommand, event, audioPlayerManager);
    }

    public static String getText() {
        return "repeat";
    }

    @Override
    public void handle() {
        this.sendMessageToAuthorChannel(super.getInputCommand().getArgString());
    }
}
