package listener.command;

import lavaplayer.PlayerManager;
import listener.AbstractCommand;
import model.InputCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand extends AbstractCommand {

    public LeaveCommand(InputCommand inputCommand, MessageReceivedEvent event, PlayerManager audioPlayerManager) {
        super(inputCommand, event, audioPlayerManager);
    }

    public static String getText() {
        return "leave";
    }

    @Override
    public void handle() {
        final AudioManager audioManager = this.getEvent().getGuild().getAudioManager();

        audioManager.closeAudioConnection();
    }
}
