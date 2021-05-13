package listener.command;

import lavaplayer.PlayerManager;
import listener.AbstractCommand;
import model.InputCommand;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand extends AbstractCommand {

    public JoinCommand(InputCommand inputCommand, MessageReceivedEvent event, PlayerManager audioPlayerManager) {
        super(inputCommand, event, audioPlayerManager);
    }

    public static String getText() {
        return "join";
    }

    @Override
    public void handle() {
        VoiceChannel voiceChannel = this.getEvent().getGuild()
                .getVoiceChannelsByName(this.getInputCommand().getArgString(), true).stream()
                .findFirst().orElse(null);

        final AudioManager audioManager = this.getEvent().getGuild().getAudioManager();


        audioManager.openAudioConnection(voiceChannel);
    }
}
