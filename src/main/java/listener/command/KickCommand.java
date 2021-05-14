package listener.command;

import listener.AbstractCommand;
import model.CommandContext;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class KickCommand extends AbstractCommand {

    public KickCommand(CommandContext commandContext) {
        super(commandContext);
    }

    public static String getText() {
        return "kick";
    }

    @Override
    public void setOptions() {
        this.getParser().accepts("u", "username").withRequiredArg();
    }

    @Override
    public void handle() {
        VoiceChannel voiceChannel = this.getEvent().getGuild()
                .getVoiceChannelsByName(String.valueOf(this.getOptionSet().valueOf("u")), true).stream()
                .findFirst().orElse(null);

        final AudioManager audioManager = this.getEvent().getGuild().getAudioManager();

        audioManager.openAudioConnection(voiceChannel);
    }
}
