package listener.command.music;

import lavaplayer.PlayerManager;
import listener.AbstractCommand;
import lombok.extern.slf4j.Slf4j;
import model.InputCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Slf4j
public class VolumeCommand extends AbstractCommand {

    public VolumeCommand(InputCommand inputCommand, MessageReceivedEvent event, PlayerManager audioPlayerManager) {
        super(inputCommand, event, audioPlayerManager);
    }

    public static String getText() {
        return "volume";
    }

    @Override
    public void handle() {
        this.getAudioPlayerManager().getMusicManager(this.getEvent().getGuild()).audioPlayer.setVolume(Integer.parseInt(this.getInputCommand().getArgString()));
    }
}
