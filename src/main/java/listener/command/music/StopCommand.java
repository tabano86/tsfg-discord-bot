package listener.command.music;

import lavaplayer.PlayerManager;
import listener.AbstractCommand;
import lombok.extern.slf4j.Slf4j;
import model.InputCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Slf4j
public class StopCommand extends AbstractCommand {

    public StopCommand(InputCommand inputCommand, MessageReceivedEvent event, PlayerManager audioPlayerManager) {
        super(inputCommand, event, audioPlayerManager);
    }

    public static String getText() {
        return "stop";
    }

    @Override
    public void handle() {
        this.getAudioPlayerManager().getMusicManager(this.getEvent().getGuild()).audioPlayer.stopTrack();
    }
}
