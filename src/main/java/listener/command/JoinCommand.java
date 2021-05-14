package listener.command;

import listener.AbstractCommand;
import model.CommandContext;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand extends AbstractCommand {

    public JoinCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "join";
    }

    @Override
    public void handle() {
        VoiceChannel voiceChannel = this.getEvent().getGuild()
                .getVoiceChannelsByName(this.getCommand().getParameter(), true).stream()
                .findFirst().orElse(null);

        final AudioManager audioManager = this.getEvent().getGuild().getAudioManager();

        audioManager.openAudioConnection(voiceChannel);
    }
}
